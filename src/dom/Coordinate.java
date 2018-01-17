package dom;

public class Coordinate {
	
	public int x;
	public int y;
	
	public Coordinate(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public void Test(Coordinate x) {
		x.x=0;
		x.y=0;
	}

}
