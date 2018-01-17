package dom;

public class Matrix {

	private double[][] distances;
	private int[][] passengers;
	
	public Matrix(Matrix matrix) {
		this.passengers = matrix.passengers.clone();
		this.distances = matrix.distances.clone();
	}
	
	public int[][] Passengers() {
		return passengers;
	}
	
	Matrix(int number_of_cities){
		distances = new double[number_of_cities][number_of_cities];
		passengers = new int[number_of_cities][number_of_cities];
	}
	
	public void InsertDistance(int id1, int id2, int distance) {
		distances[id1][id2]=distance;
		distances[id2][id1]=distance;
	}
	
	public void InsertPassengers(int id1, int id2, int passenger) {
		passengers[id1][id2]=passenger;
	}
	
	public double Distance(int id1, int id2) {
		return distances[id1][id2];
	}
	
	public int Passengers(int id1, int id2) {
		return passengers[id1][id2];
	}
	
	public void UpdatePassengers(int id1, int id2, int amount) {
		passengers[id1][id2]+=amount;
	}
}
