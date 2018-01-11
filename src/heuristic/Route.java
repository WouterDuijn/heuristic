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

		for(int i=index+2; i<=tank.size(); i++){
			if(tank.get(i-1) - distances.get(i) <= 0){
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
	
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public int isValidCityInsert(int i, int j) {
		int result=0;
		// TODO fix way to return vector of refuels and return whether it is valid
		//TODO adjust refuel moments if later refuel moments no longer required
		//Deze TODO's kunnen niet in deze methode, omdat isValid niet betekent dat we de city ook daadwerkelijk toevoegen.
		
		return result;
	}

	public boolean isValidNumberPax(int index, int passengers1, int passengers2) {
		//TODO adjust for detours
		if(passengers.get(index-1) + passengers1 <= MAX_PASSENGERS){
			if(passengers.get(index-1) + passengers2 <= MAX_PASSENGERS) {
				return true;
			}
		}
		return false;
		
		
		
		
	}
	

}
