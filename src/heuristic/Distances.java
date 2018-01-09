package heuristic;

public class Distances {

	private int[][] distances;
	
	Distances(int number_of_cities){
		distances = new int[number_of_cities][number_of_cities];
	}
	
	public void InsertDistance(int id1, int id2, int distance) {
		distances[id1][id2]=distance;
		distances[id2][id1]=distance;
	}
	
	public int Distance(int id1, int id2) {
		return distances[id1][id2];
	}
}
