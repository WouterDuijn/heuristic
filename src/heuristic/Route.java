package heuristic;

import java.util.Vector;

public class Route {
	
	private static final int DAY_LENGTH=20;
	private static final double MAX_FUEL_DISTANCE=3199;
	private static final int SPEED=800;
	private static final int MAX_PASSENGERS = 199;
	
	private Vector<City> cities;
	private Vector<Integer> refuel; //
	private Vector<Double> tank; //number of kilometers the plane can still travel without refuel at arrival
	double current_time=0;
	double profit=0;
	Vector<Integer> passengers;
	Vector<Double> distances;
	
	Route(City city){
		cities.add(city);
		cities.add(city);
		passengers.add(0);
		distances.add(0.);
		refuel.add(0);
		refuel.add(0);
		tank.add(MAX_FUEL_DISTANCE);
		tank.add(0.);
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
		
		//replace refuel by vector of refuel moments and adjust tank
		if(ref){
			refuel.add(index,1);
			current_time+=1;
			tank.add(index,MAX_FUEL_DISTANCE);			
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
		
		//TODO keep track of the detours, otherwise isValidNumberPax and the profit cannot be determined 		
		
		profit+= profit_increment;
	}
	
	public int size(){
		return cities.size();
	}
	
	public boolean needsRefuel(int city_id, int index) {
		// TODO we need the distances d1 and d2 to check if we can make the flight to that city
		return false;
	}
	
	/**
	 * 
	 * @param city_id
	 * @param index
	 * @return
	 */
	public int isValidCityInsert(int city_id, int index) {
		int result=0;
		// TODO fix way to return vector of refuels and return whether it is valid
		//TODO adjust refuel moments if later refuel moments no longer required

		//Deze TODO's kunnen niet in deze methode, omdat isValid niet betekent dat we de city ook daadwerkelijk toevoegen.
		

		//Deze 2 TODO's hierboven kunnen niet in deze methode, omdat isValid niet betekent dat we de city ook daadwerkelijk toevoegen.
		
		// index in Route is the index at which we check if the new city can be placed, but it is not yet added to the Route itself
		// so a different city might be on this index at the moment
		
		// if city to be inserted is the same as a neighbour (city): not valid
		if(cities.get(index-1).ID() == city_id || cities.get(index).ID() == city_id) {
			return result;
		} else {
			double time = current_time;
			if(needsRefuel(city_id, index)) {
				time+=1;
			}
			// add un-, boarding time
			time+=1;
			
			// subtract flying time of current flight (that will be replaced by the newly created flights)
			time-= distances.get(index-1)/SPEED;
			
			// TODO we need the distances d1 and d2 here to check if we can make the flight to that city
			
			// add flying times of the new flights created by adding the new city to the route
			// time = time + d1/SPEED + d2/SPEED;
			
			// if total time (auxiliary var. for current_time) does not exceed the day_length, it is a valid city insert.
			if(time <= DAY_LENGTH) {
				return result;
			}			
		}
		
		// TODO adjust value of result inside if-statements

		return result;
	}

	public boolean isValidNumberPax(int index, int passengers1, int passengers2) {
		//TODO adjust for detours
		if(passengers.get(index-1) + passengers1 <= MAX_PASSENGERS) {
			if(passengers.get(index-1) + passengers2 <= MAX_PASSENGERS) {
				return true;
			}
		}
		return false;
		
		
		
		
	}
	

}
