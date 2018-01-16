package dom;

import java.io.PrintStream;
import java.util.Vector;

public class Route {

	PrintStream out;

	private static final int DAY_LENGTH=20;
	private static final double MAX_FUEL_DISTANCE=3199;
	private static final int SPEED=800;
	private static final int MAX_PASSENGERS = 199;

	
	private Vector<City> cities;
	private Vector<Integer> refuel; //
	private Vector<Double> tank; //number of kilometers the plane can still travel after arrival at a city
	public double current_time;
	public double profit;
	private Vector<Integer> passengers; 
	Vector<Double> distances;
	
	/**
	 * Default Constructor. Set current time and profit to zero
	 */
	public Route(){
		setCities(new Vector<City>());
		setPassengers(new Vector<Integer>());
		distances = new Vector<Double>();
		refuel = new Vector<Integer>();
		tank = new Vector<Double>();
		current_time=0;
		profit=0;
	}
	
	
	/**
	 * Copy constructor of route
	 * @param route = route to be copied from
	 */
	public Route(Route route){
		this.setCities(route.getCities());
		this.refuel=route.refuel;
		this.tank=route.tank;
		this.current_time=route.current_time;
		this.profit = route.profit;
		this.setPassengers(route.getPassengers());
		this.distances=route.distances;
	}
	
	
	public Route(City city){
		setCities(new Vector<City>());
		getCities().add(city);
		getCities().add(city);
		setPassengers(new Vector<Integer>());
		getPassengers().add(0);
		distances = new Vector<Double>();
		distances.add(0.);
		refuel = new Vector<Integer>();
		refuel.add(0);
		refuel.add(0);
		tank = new Vector<Double>();
		tank.add(MAX_FUEL_DISTANCE);
		tank.add(0.);
		current_time=0;
		profit=0;
		out = new PrintStream(System.out);
	}
	
	

	/**
	 * 
	 * @param city
	 * @param index
	 * @param refuel
	 * @param passenger1
	 * @param passenger2
	 */
	public void AddCity(City city, int index,int passenger1,int passenger2, double distance1, double distance2){
		getCities().add(index, city);

		int passenger_first_edge= passenger1 + getPassengers().elementAt(index-1);
		int passenger_second_edge = passenger2 + getPassengers().elementAt(index-1);
		
		getPassengers().setElementAt(passenger_second_edge, index-1);
		getPassengers().add(index-1, passenger_first_edge); 		
		//out.println(passengers);
		
		profit+= passenger1*distance1+passenger2*distance2;
	}

	public int size(){
		return getCities().size();
	}

	public boolean needsRefuel(int index, double distance1, double distance2) {
		if(tank.get(index-1)-distance1<=0 || tank.get(index-1)-distance1-distance2<=0){
			return true;
		}
		return false;
	}

	Vector<City> getCitiesCopy() {
		Vector<City> copy = new Vector<City>(getCities().size());
		for(int i=0; i<getCities().size(); i++) {
			copy.add(i,getCities().get(i).copy());
		}
		return copy;
	}

	public boolean isValidCityInsert(City city, int index, double distance1, double distance2) {

		//Makes deepcopy of cities vector
		Vector<City> cities_copy = new Vector<City>(getCities());
		
		// if current_time above or equal to day length - 1 hour, no new flights can be added
		// since only boarding and unboarding will already take an extra hour on top of flight
		// particularly useful for the last to be added flight, instead of calculating everything first
		if(current_time >= DAY_LENGTH-1) {
			return false; 
		}
		
		if(distance1 > MAX_FUEL_DISTANCE || distance2 > MAX_FUEL_DISTANCE) {
			return false; 
		}

		cities_copy.add(index, city); 
		
		if(cities_copy.get(index-1).ID() == city.ID() || cities_copy.get(index+1).ID() == city.ID()) {
			return false;
		} 
		
		// add un-, boarding time
		current_time+=1;
		//out.println(result.current_time);
		
		// subtract flying time of current flight (that will be replaced by the newly created flights)
		current_time-= distances.get(index-1)/SPEED;
		//out.println(result.current_time);
		
		// add flying times of the new flights
		current_time = current_time + distance1/SPEED + distance2/SPEED;
		//out.printf("%.2f\n",result.current_time);
		
		distances.setElementAt(distance2, index-1);
		distances.add(index-1, distance1);
		//out.println(result.distances);
		
		if(needsRefuel(index, distance1, distance2)) {
			// if refuel is required before distance1 can be traveled
			if(tank.get(index-1)-distance1<=0) {
				if(refuel.elementAt(index-1)==0) {
					refuel.setElementAt(1, index-1);
					tank.setElementAt(MAX_FUEL_DISTANCE, index-1);
					current_time+=1;
				} 
			} // no else since these data remain the same (already exists)
			
			// if refuel is required after distance1, before distance2
			if(tank.get(index-1)-distance1-distance2<=0) {
				refuel.add(index, 1);
				tank.add(index, MAX_FUEL_DISTANCE);
				current_time+=1;
			} else {
				refuel.add(index, 0);
				tank.add(index, tank.get(index-1)-distance1);
			}
			//out.println(result.tank);
			//out.println(result.current_time);
			//out.println(result.refuel);
		} else {
			refuel.add(index,0);
			tank.add(index, tank.get(index-1)-distance1);
			//out.println(result.tank);
			//out.println(result.current_time);
			//out.println(result.refuel);
		}
		// set new tank value at index+1 after flying distance2
		tank.setElementAt(tank.get(index)-distance2, index+1); 
		//out.println(result.tank);
		
		// Recompute required refuel moments and remaining tank values of remaining route
		for(int i=index+2; i<cities_copy.size(); i++) {
			if(tank.get(i-1) - distances.get(i-1) <= 0) {
				if(refuel.get(i-1) == 0){
					refuel.setElementAt(1, i-1);
					//result.tank.setElementAt(MAX_FUEL_DISTANCE, i-1); //verplaatst naar hieronder, als er al getankt werd en dat moet nog steeds gebeuren, moet de tank weer terug naar 3199
					current_time+=1;
				} 
				tank.setElementAt(MAX_FUEL_DISTANCE, i-1);
			} else {
				if(refuel.get(i-1) == 1) {
					refuel.setElementAt(0, i-1);
					current_time-=1;
				}
			}
			// Recompute tank value according to new previous tank values
			tank.setElementAt(tank.get(i-1)-distances.get(i-1), i);
		}	
		//out.println(result.tank);
		//out.printf("%.2f\n",result.current_time);;
		//out.println(result.refuel);
		
		// if total time does not exceed the day length, it is a valid city insert
		if(current_time <= DAY_LENGTH) {
			return true; 
		}
		
		return false; 

	}

	public boolean isValidNumberPax(int index, int passengers1, int passengers2) {
		if(getPassengers().get(index-1) + passengers1 <= getMaxPassengers()) {
			if(getPassengers().get(index-1) + passengers2 <= getMaxPassengers()) {
				return true;
			}
		}
		return false;	
	}


	public static int getMaxPassengers() {
		return MAX_PASSENGERS;
	}


	public Vector<Integer> getPassengers() {
		return passengers;
	}


	public void setPassengers(Vector<Integer> passengers) {
		this.passengers = passengers;
	}


	public Vector<City> getCities() {
		return cities;
	}


	public void setCities(Vector<City> cities) {
		this.cities = cities;
	}
	
	public String toString() { 
		return ("Cities\t" + this.cities.toString()+ "\n" + 
				"Refuel\t" + this.refuel.toString() + "\n" +
				"Passengers\t" + this.passengers.toString());
	}
}
