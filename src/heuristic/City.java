package heuristic;

public class City {
	
	private String name;
	private int id;
	private int x;
	private int y;
	
	City(int id, String name, int x, int y){
		this.name=name;
		this.id=id;
		this.x=x;
		this.y=y;
	}
	
	int ID(){
		return id;
	}
	
	int X() {
		return x;
	}
	
	int Y() {
		return y;
	}
	
	String Name() {
		return name;
	}
	

}
