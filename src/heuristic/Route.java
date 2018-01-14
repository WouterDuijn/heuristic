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
	
	public boolean needsRefuel(int index, double distance1, double distance2) {
		if(tank.get(index-1)-distance1<=0 || tank.get(index)-distance2<=0){
			return true;
		}
		return false;
	}
	
	// heb deze methode (en in City copy()) aangemaakt omdat ik het makkelijker vond om te werken met een copy in isValidCityInsert
	// anders kan ik moeilijker loopen over de indexen als ik bij tank, refuel wel de city 'toevoeg' en bij de route niet.
	// clone() bij vector werkt alleen voor primitive types, dus moest zelf nog City copyen.
	Vector<City> getCitiesCopy() {
		Vector<City> copy = new Vector<City>(cities.size());
		for(int i=0; i<=cities.size(); i++) {
			copy.setElementAt(cities.get(i).copy(), i);
		}
		return copy;
	}
	
	/**
	 * 
	 * @param city_id
	 * @param index
	 * @return
	 */	
	// TODO result class met refuel vector, tank vector, current_time
	RefuelTankTime isValidCityInsert(City city, int index, double distance1, double distance2) {
		RefuelTankTime result= new RefuelTankTime(refuel, tank, distances, current_time);
		Vector<City> cities_copy = getCitiesCopy();
		cities_copy.add(index, city);
		
		// index in cities vector is the index at which we check if the new city can be placed, but it is not yet added to the Route itself
		// so a different city might be on this index at the moment
		
		// if city to be inserted is the same as a neighbour (city): not valid
		
		/* 	can be replaced by: (using copy)
		if(cities_copy.get(index-1).ID() == city.ID() || cities_copy.get(index+1).ID() == city.ID()) {
			return result;
		  	...
		 */
		if(cities.get(index-1).ID() == city.ID() || cities.get(index).ID() == city.ID()) {
			return result;
		} else {
			// add un-, boarding time
			result.current_time+=1;
			
			// subtract flying time of current flight (that will be replaced by the newly created flights)
			result.current_time-= distances.get(index-1)/SPEED;
			
			// add flying times of the new flights created by adding the new city to the route
			result.current_time = result.current_time + distance1/SPEED + distance2/SPEED;
			
			result.current_time-=distances.get(index-1)/SPEED;
			
			result.distances.setElementAt(distance2, index-1);
			result.distances.add(index-1, distance1);
			
			if(needsRefuel(index, distance1, distance2)) {
				if(tank.get(index-1)-distance1<=0 && tank.get(index)-distance2<=0){
					result.refuel.add(index-1,1);
					result.tank.add(index-1,MAX_FUEL_DISTANCE);
					result.refuel.add(index,1);
					result.tank.add(index,MAX_FUEL_DISTANCE);
					result.current_time+=2;
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
			
			// if total time (auxiliary var. for current_time) does not exceed the day_length, it is a valid city insert.
			if(result.current_time <= DAY_LENGTH) {
				result.valid=true;
				return result;
			} else {

			}
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
	

}
