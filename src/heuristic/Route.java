package heuristic;

import java.util.Vector;

public class Route {
	
	private static final int DAY_LENGTH=20;
	private static final int MAX_FUEL_DISTANCE=3199;
	
	private Vector<City> cities;
	private Vector<Integer> refuel;
	private Vector<Integer> tank;
	double current_time=0;
	double profit=0;
	Vector<Integer> passengers;
	
	Route(City city){
		cities.add(city);
		cities.add(city);
		passengers.add(0);
		refuel.add(0);
		refuel.add(0);
		tank.add(0);
		tank.add(0);
	}
	
	/**
	 * 
	 * @param city
	 * @param index
	 * @param refuel
	 * @param passenger1
	 * @param passenger2
	 */
	public void AddCity(City city, int index, boolean refuel, int passenger1,
			int passenger2, double profit_increment, double distance1, double distance2){
		cities.add(index, city);
		
		//Boarding time

		//TODO: replace refuel by vector of refuel moments

		
		int passenger_first_edge= passenger1 + passengers.elementAt(index-1);
		int passenger_second_edge = passenger2 + passengers.elementAt(index);
		
		passengers.setElementAt(passenger_second_edge, index-1);
		passengers.add(index-1, passenger_first_edge);
		
		profit+= profit_increment;
		
		
		//TODO: minus distance of broken edge & plus two new distances
		
		//TODO: adjust tank
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
		
		
		
		
		
		
		
		return result;
	}

	public void isValidNumberPax(int i, int j, int k, int l) {
		// TODO Auto-generated method stub
		
		
		
		
	}
	

}
