package dom;

import java.util.Arrays;

public class Matrix {

	private double[][] distances;
	private int[][] passengers;
	
	public Matrix(Matrix matrix) {
		this.passengers = matrix.passengers.clone();
		this.distances = matrix.distances.clone();
	}
	
	public Matrix() {
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
	
	public void DecreasePassengers(int id1, int id2, int amount) {
		passengers[id1][id2]-=amount;
	}
	
	// added
	public double[][] distancesCopy() {
		if(distances == null) {
			return null;
		}
		
		double[][] copy = new double[distances.length][distances[0].length];
		for (int i = 0; i < copy.length; i++)
		     copy[i] = Arrays.copyOf(distances[i], distances[i].length);
		return copy;
	}
	
	// added
	public int[][] passengersCopy() {
		if(passengers == null) {
			return null;
		}
		
		int[][] copy = new int[passengers.length][passengers[0].length];
		for (int i = 0; i < copy.length; i++)
		     copy[i] = Arrays.copyOf(passengers[i], passengers[i].length);
		return copy;
	}
	
	// added
	public Matrix deepCopy() {
		Matrix copy = new Matrix();
		copy.distances = distancesCopy();
		copy.passengers = passengersCopy();
		return copy;
	}
	
	// added, 1 method for all.
	
	public Matrix deepCopyMatrix() {
	    // what about matrix = null?
		
		Matrix result = new Matrix();
		double[][] distancesCopy = new double[distances.length][];
	    int[][] passengersCopy = new int[passengers.length][];
	    
	    
	    for (int j = 0; j < distances.length; j++) {
	    	distancesCopy[j] = distancesCopy[j].clone();
	    }
	    
	    for (int i = 0; i < passengers.length; i++) {
	        passengersCopy[i] = passengersCopy[i].clone();
	    }
	    
	    return result;
	}
}
