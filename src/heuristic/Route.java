package heuristic;

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
	private Vector<Double> tank; //number of kilometers the plane can still travel without refuel at arrival
	double current_time=0;
	double profit=0;
	Vector<Integer> passengers; // initialize/declare vectors
	Vector<Double> distances;
	
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
	public void AddCity(City city, int index, boolean ref, int passenger1,
			int passenger2, double profit_increment, double distance1, double distance2){
		cities.add(index, city);
		
		//Boarding time
		current_time+=1;
		
		//Flying time
		current_time-=distances.get(index-1)/SPEED;
		
		distances.setElementAt(distance2, index-1);
		distances.add(index-1, distance1);
		
		current_time = current_time + distance1/SPEED + distance2/SPEED;
		
		//Adjust refuel moments and adjust tank
		//check where to refuel, either in newly added city or in previous city
		if(ref){
			if(tank.get(index-1)-distance1<=0 && tank.get(index)-distance2<=0){
				refuel.add(index-1,1);
				tank.add(index-1,MAX_FUEL_DISTANCE);
				refuel.add(index,1);
				tank.add(index,MAX_FUEL_DISTANCE);
				current_time+=2;
			} else if(tank.get(index-1)-distance1<=0){
				refuel.add(index-1,1);
				tank.add(index-1,MAX_FUEL_DISTANCE);
				current_time+=1;
			} else {
				refuel.add(index,1);
				tank.add(index,MAX_FUEL_DISTANCE);
				current_time+=1;
			}			
		} else {
			refuel.add(index,0);
			tank.add(index,tank.get(index-1)-distance1);
		}
		
		tank.setElementAt(tank.get(index)-distance2,index+1);

		for(int i=index+2; i<=tank.size(); i++) {
			if(tank.get(i-1) - distances.get(i) <= 0) {
				if(refuel.get(i-1) == 0){
					refuel.setElementAt(1, i-1);
					tank.setElementAt(MAX_FUEL_DISTANCE, i-1);
					current_time+=1;
				}				
			} else {
				if(refuel.get(i-1) == 1) {
					refuel.setElementAt(0, i-1);
					current_time-=1;
				}
				tank.setElementAt(tank.get(i-1)-distances.get(i), i);
			}
		}
		
		int passenger_first_edge= passenger1 + passengers.elementAt(index-1);
		int passenger_second_edge = passenger2 + passengers.elementAt(index);
		
		passengers.setElementAt(passenger_second_edge, index-1);
		passengers.add(index-1, passenger_first_edge); 		
		
		profit+= profit_increment;
	}
	
	
	public int size(){
		return cities.size();
	}
	
	public boolean needsRefuel(int city_id, int index, double distance1, double distance2) {
		if(tank.get(index-1)-distance1<=0 || tank.get(index)-distance2<=0){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param city_id
	 * @param index
	 * @return
	 * result=0 => no valid insert
	 * result=1 => valid insert without refuel
	 * result=2 => valid insert only with refuel
	 */
	
	// TODO result class met refuel vector, tank vector, current_time
	RefuelTankTime isValidCityInsert(int city_id, int index, double distance1, double distance2) {
		RefuelTankTime result= new RefuelTankTime();
		
		double time = current_time;
		

		// index in Route is the index at which we check if the new city can be placed, but it is not yet added to the Route itself
		// so a different city might be on this index at the moment
		
		// if city to be inserted is the same as a neighbour (city): not valid
		if(cities.get(index-1).ID() == city_id || cities.get(index).ID() == city_id) {
			return result;
		} else {
			/*if(needsRefuel(city_id, index, distance1, distance2)) {
				if(tank.get(index-1)-distance1<=0 && tank.get(index)-distance2<=0){
					time+=2;
				} else {
					time+=1;
				}					
				result=2;
			} else {
				result=1;
			}	*/		
			
			// add un-, boarding time
			time+=1;
			
			// subtract flying time of current flight (that will be replaced by the newly created flights)
			time-= distances.get(index-1)/SPEED;
			
			// add flying times of the new flights created by adding the new city to the route
			time = time + distance1/SPEED + distance2/SPEED;
			
			// if total time (auxiliary var. for current_time) does not exceed the day_length, it is a valid city insert.
			/*if(time <= DAY_LENGTH) {
				return result;
			} else {
				result=0;
			}*/
			return result;
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
	
	public String toString(){ 
		  return "Cities: " +cities+"\n" +"Refuel: "+ refuel +"\n"
				  + "Passengers: " + passengers;  
		 } 
	
	/**
	 * 
	 * @return Return vector of coordinates of all cities
	 */
	public Vector<Coordinate> GetCityCoodinates() {
		Vector<Coordinate> results = new Vector<Coordinate>();
		for(int i=0;i<cities.size();i++) {
			results.add(new Coordinate(cities.get(i).X(), cities.get(i).Y()));
		}
		return results;
	}
}
