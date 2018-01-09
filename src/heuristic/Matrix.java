package heuristic;

public class Matrix {

	private int[][] distances;
	private int[][] passengers;
	
	Matrix(int number_of_cities){
		distances = new int[number_of_cities][number_of_cities];
		passengers = new int[number_of_cities][number_of_cities];
	}
	
	public void InsertDistance(int id1, int id2, int distance) {
		distances[id1][id2]=distance;
		distances[id2][id1]=distance;
	}
	
	public void InsertPassengers(int id1, int id2, int passenger) {
		passengers[id1][id2]=passenger;
		passengers[id2][id1]=passenger;
	}
	
	public int Distance(int id1, int id2) {
		return distances[id1][id2];
	}
	
	public int Passengers(int id1, int id2) {
		return passengers[id1][id2];
	}
}
