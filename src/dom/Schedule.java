package dom;

import java.util.Vector;

public class Schedule {
	
	private double profit=0;
	private Vector<Route> routes;
	private Matrix matrix;
	
	
	public Schedule(Matrix matrix){
		this.routes = new Vector<Route>();
		this.matrix = new Matrix(matrix);
	}
	
	public void AddRoute(Route route) {
		routes.add(route);
		profit+= route.profit;
		route.AdjustPassengerMatrixFromBookings(matrix);
	}
	
	public double Profit() {
		return profit;
	}
	
	public Vector<Route> Routes(){
		return routes;
	}
	
	public Matrix Matrix() {
		return matrix;
	}

}
