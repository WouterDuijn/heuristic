package heuristic;

public class Cities {
	
	private static final int MAX_CITIES=50;
	
	private City[] cities;
	private int nr_cities;
	
	Cities(){
		cities = new City[MAX_CITIES];
		nr_cities=0;
	}
	
	
	/**
	 * Adds the city to the cities Object.
	 * @param city to add
	 * @return whether the id was correct. If not it doesnt add the city
	 */
	public boolean AddCity(City city) {
		if(city.ID()==nr_cities) {
			cities[nr_cities]=city;
			nr_cities++;
			return true;
		}
		return false;
	}
	
	public int size() {
		return nr_cities;
	}

}
