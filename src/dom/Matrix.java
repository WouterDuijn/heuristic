package dom;

public class Matrix {

	private double[][] distances;
	private int[][] passengers;
	
	public Matrix() {
		passengers = new int[0][0];
		distances = new double[0][0];
	}
	
	public Matrix(Matrix matrix) {
		this.passengers = matrix.DeepCopyPassengers();
		this.distances = matrix.distances;
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
		if(passengers[id1][id2]<0) {
			throw new RuntimeException("Passengers below zero");
		}
	}
		
	public int[][] DeepCopyPassengers() {
		if(passengers == null) {
			return null;
		}
		
		int[][] copy = new int[passengers.length][passengers[0].length];
		for (int i = 0; i < copy.length; i++) {
			copy[i] = passengers[i].clone();
		}
		return copy;
		
	}
	
	public String PassengertoString() {
		String result ="";
		
		for(int i=0;i<passengers.length;i++) {
			result += "[ ";
			for(int j=0;j<passengers.length;j++) {
				result += passengers[i][j] + " ";
			}
			result +=  " ]\n";
		}
		
		return result;
	}
	
	public int TotalPassengers() {
		int result=0;
		for(int i=0;i<passengers.length;i++) {
			for(int j=0;j<passengers.length;j++) {
				result += passengers[i][j];
			}
		}
		return result;
	}
}
