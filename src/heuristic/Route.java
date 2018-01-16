package heuristic;

import java.io.PrintStream;
import java.util.Vector;

public class Route {

	PrintStream out;

	private static final int DAY_LENGTH=20;
	private static final double MAX_FUEL_DISTANCE=3199;
	private static final int SPEED=800;
	static final int MAX_PASSENGERS = 199;

	Vector<City> cities;
	private Vector<Integer> refuel; //
	private Vector<Double> tank; //number of kilometers the plane can still travel after arrival at a city
	double current_time;
	double profit;
	Vector<Integer> passengers; 
	Vector<Double> distances;
	
	
	/**
	 * Copy constructor of route
	 * @param route = route to be copied from
	 */
	Route(Route route){
		this.cities=route.cities;
		this.refuel=route.refuel;
		this.tank=route.tank;
		this.current_time=route.current_time;
		this.profit = route.profit;
		this.passengers=route.passengers;
		this.distances=route.distances;
	}
	
	
	Route(City city){
		cities = new Vector<City>();
		cities.add(city);
		cities.add(city);
		passengers = new Vector<Integer>();
		passengers.add(0);
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
	public void AddCity(City city, int index, RefuelTankTime city_results, int passenger1,
			int passenger2, double distance1, double distance2){
		cities.add(index, city);
		current_time = city_results.current_time;
		refuel = city_results.refuel; 
		tank = city_results.tank;
		distances = city_results.distances;

		int passenger_first_edge= passenger1 + passengers.elementAt(index-1);
		int passenger_second_edge = passenger2 + passengers.elementAt(index-1);
		
		passengers.setElementAt(passenger_second_edge, index-1);
		passengers.add(index-1, passenger_first_edge); 		
		//out.println(passengers);
		
		profit+= passenger1*distance1+passenger2*distance2;
	}

	public int size(){
		return cities.size();
	}

	public boolean needsRefuel(int index, double distance1, double distance2) {
		if(tank.get(index-1)-distance1<=0 || tank.get(index-1)-distance1-distance2<=0){
			return true;
		}
		return false;
	}

	Vector<City> getCitiesCopy() {
		Vector<City> copy = new Vector<City>(cities.size());
		for(int i=0; i<cities.size(); i++) {
			copy.add(i,cities.get(i).copy());
		}
		return copy;
	}

	RefuelTankTime isValidCityInsert(City city, int index, double distance1, double distance2) {
		RefuelTankTime result = new RefuelTankTime(refuel, tank, distances, current_time); // boolean result.valid is false by default
		Vector<City> cities_copy = getCitiesCopy();
		
		// if current_time above or equal to day length - 1 hour, no new flights can be added
		// since only boarding and unboarding will already take an extra hour on top of flight
		// particularly useful for the last to be added flight, instead of calculating everything first
		if(current_time >= DAY_LENGTH-1) {
			return result; 
		}
		
		if(distance1 > MAX_FUEL_DISTANCE || distance2 > MAX_FUEL_DISTANCE) {
			return result; 
		}

		cities_copy.add(index, city); 
		
		if(cities_copy.get(index-1).ID() == city.ID() || cities_copy.get(index+1).ID() == city.ID()) {
			return result;
		} else {
			// add un-, boarding time
			result.current_time+=1;
			//out.println(result.current_time);
			
			// subtract flying time of current flight (that will be replaced by the newly created flights)
			result.current_time-= distances.get(index-1)/SPEED;
			//out.println(result.current_time);
			
			// add flying times of the new flights
			result.current_time = result.current_time + distance1/SPEED + distance2/SPEED;
			//out.printf("%.2f\n",result.current_time);
			
			result.distances.setElementAt(distance2, index-1);
			result.distances.add(index-1, distance1);
			//out.println(result.distances);
			
			if(needsRefuel(index, distance1, distance2)) {
				// if refuel is required before distance1 can be traveled
				if(result.tank.get(index-1)-distance1<=0) {
					if(result.refuel.elementAt(index-1)==0) {
						result.refuel.setElementAt(1, index-1);
						result.tank.setElementAt(MAX_FUEL_DISTANCE, index-1);
						result.current_time+=1;
					} 
				} // no else since these data remain the same (already exists)
				
				// if refuel is required after distance1, before distance2
				if(result.tank.get(index-1)-distance1-distance2<=0) {
					result.refuel.add(index, 1);
					result.tank.add(index, MAX_FUEL_DISTANCE);
					result.current_time+=1;
				} else {
					result.refuel.add(index, 0);
					result.tank.add(index, result.tank.get(index-1)-distance1);
				}
				//out.println(result.tank);
				//out.println(result.current_time);
				//out.println(result.refuel);
			} else {
				result.refuel.add(index,0);
				result.tank.add(index, result.tank.get(index-1)-distance1);
				//out.println(result.tank);
				//out.println(result.current_time);
				//out.println(result.refuel);
			}
			// set new tank value at index+1 after flying distance2
			result.tank.setElementAt(result.tank.get(index)-distance2, index+1); 
			//out.println(result.tank);
			
			// Recompute required refuel moments and remaining tank values of remaining route
			for(int i=index+2; i<cities_copy.size(); i++) {
				if(result.tank.get(i-1) - result.distances.get(i-1) <= 0) {
					if(result.refuel.get(i-1) == 0){
						result.refuel.setElementAt(1, i-1);
						//result.tank.setElementAt(MAX_FUEL_DISTANCE, i-1); //verplaatst naar hieronder, als er al getankt werd en dat moet nog steeds gebeuren, moet de tank weer terug naar 3199
						result.current_time+=1;
					} 
					result.tank.setElementAt(MAX_FUEL_DISTANCE, i-1);
				} else {
					if(result.refuel.get(i-1) == 1) {
						result.refuel.setElementAt(0, i-1);
						result.current_time-=1;
					}
				}
				// Recompute tank value according to new previous tank values
				result.tank.setElementAt(result.tank.get(i-1)-result.distances.get(i-1), i);
			}	
			//out.println(result.tank);
			//out.printf("%.2f\n",result.current_time);;
			//out.println(result.refuel);
			
			// if total time does not exceed the day length, it is a valid city insert
			if(result.current_time <= DAY_LENGTH) {
				result.valid=true;
				return result;
			} else {
				return result;
			}
		}
	}

	public boolean isValidNumberPax(int index, int passengers1, int passengers2) {
		if(passengers.get(index-1) + passengers1 <= MAX_PASSENGERS) {
			if(passengers.get(index-1) + passengers2 <= MAX_PASSENGERS) {
				return true;
			}
		}
		return false;	
	}


}
