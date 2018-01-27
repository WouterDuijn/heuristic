package dom;

public class City {
	
	private String name;
	private int id;
	private int x;
	private int y;
	
	//Er is nu een coordinate class, misschien kunnen we die dan ook hier gebruiken, of beter niet?
	public City(int id, String name, int x, int y){
		this.name=name;
		this.id=id;
		this.x=x;
		this.y=y;
	}
	
	public City(City city) {
		name=city.name;
		id=city.id;
		x=city.x;
		y=city.y;
	}
	
	public int ID(){
		return id;
	}
	
	public int X() {
		return x;
	}
	
	public int Y() {
		return y;
	}
	
	String Name() {
		return name;
	}
	
	public String toString(){ 
		  return  name;
	}
	
	City copy() {
		City copy = new City(this.id, this.name, this.x, this.y);
		return copy;
	}
}
