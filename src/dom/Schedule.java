package dom;

import java.util.Random;
import java.util.Vector;

public class Schedule {
	
	private Vector<Route> routes;
	private Matrix matrix;
	private City homeBase;
	
	public Schedule(Schedule schedule) {
		
		this.routes = new Vector<Route>();
		for(int i=0;i<schedule.routes.size();i++) {
			this.routes.add(new Route(schedule.routes.get(i)));
		}
		
		//this.routes = new Vector<Route>(schedule.routes);
		
		
		this.matrix = new Matrix(schedule.matrix);
		this.homeBase = schedule.homeBase;
	}
	
	public Schedule(Matrix matrix){
		this.routes = new Vector<Route>();
		this.matrix = new Matrix(matrix);
		//this.homeBase = new City();
	}
	
	public void AddRoute(Route route) {
		routes.add(route);
		route.AdjustPassengerMatrixFromBookings(matrix);
	}
	
	public double Profit() {
		double profit=0;
		for(int i=0;i< routes.size();i++) {
			profit+=routes.get(i).profit;
		}
		return profit;
	}
	
	public Vector<Route> Routes(){
		return routes;
	}
	
	public Matrix Matrix() {
		return matrix;
	}

	public boolean Mutate(Random rn, Cities cities_list) {
		return routes.get(rn.nextInt(routes.size())).Mutate(rn, matrix, cities_list);
	}
	
	public String toString() {
		String result="";
		double total_profit = 0;
		for(int i=0;i<routes.size();i++) {
			result += routes.get(i).toString();
			total_profit+=routes.get(i).profit;
		}
		result+= "Total Profit: $" + total_profit + "\n";
		return result;
	}
	
	public void CheckValidity() {
		for(Route r: routes) {
			r.CheckValidity();
		}
	}
}
