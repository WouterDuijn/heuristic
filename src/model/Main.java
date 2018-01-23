package model;

import dom.Cities;
import dom.City;
import dom.Visualization;
import dom.Matrix;
import dom.Route;
import dom.Schedule;
import dom.Parser;
import dom.Coordinate;
import java.awt.Color;
import java.io.PrintStream;
import java.util.Vector;
import java.util.Random;

public class Main {

	public static final int NR_PLANES = 6;
	public static final String INPUT_PATH = "C:\\workspace\\heuristic\\inp\\";

	//tests
	PrintStream out;
	Random rn;

	Main() {
		//Initialize variables
		out = new PrintStream(System.out);   
		rn = new Random();
	}

	// moeten hier deep copies gemaakt worden van de Schedules of kan het zo?
	Schedule HillClimbingModel(Cities cities, Matrix matrix) {
		//boolean cont = true;
		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();
		double profit_improvement = 10; // if diff between optimal and current profit <= 10, stop?
		// or if X number of iterations reached, stop?
			
		// create 5 initial random schedules
		for(int i = 0; i<10; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(cities, m));
		}
		
		for(int k =0;k<schedules.size();k++) {
			// Hill climbing algorithm (run all 5 schedules to find 5 local optima)
			Schedule current_schedule = new Schedule(schedules.get(k));
			
			
			//TODO: Determine stop criteria. Now hardcoded to 100.000 mutations
			for(int j=0;j< 100000;j++) {
				Schedule s = new Schedule(current_schedule);
				if(s.Mutate(rn, cities) && s.Profit()>current_schedule.Profit()) {
					s.CheckValidity();
					current_schedule = new Schedule(s);
				}
			}
			optimal_schedules.add(current_schedule);
		}
		
		Schedule best = new Schedule(optimal_schedules.get(0));
		
		for(Schedule s: optimal_schedules) {
			if(s.Profit()>best.Profit()) {
				best = new Schedule(s);
			}
			
		}

		return best;
	}
	
	// added printing method so RandomModel wouldn't print when used in HillClimbingModel
	void printSchedule(Schedule schedule) {
		for(int i=0; i<schedule.Routes().size(); i++) {
			out.printf(schedule.Routes().get(i).toString());
			out.println('\n');
		}
		out.printf("Total profit\t€%.2f\n", schedule.Profit());
	}

	Schedule RandomModel(Cities cities, Matrix inputMatrix) {
		Schedule schedule = new Schedule(inputMatrix);
		for(int k=0; k<NR_PLANES;k++) {
			Route optimalRoute =new Route();
			for(int j = 0; j<100; j++){ // create 10000 routes to find best

				int randomStartCity = rn.nextInt(cities.size());
				Route route = new Route(cities.getCity(randomStartCity));
				Matrix matrix = new Matrix(schedule.Matrix());

				for(int i = 0; i<200; i++){ // to create 1 route
					Route cur_route= new Route(route);
					Matrix cur_matrix = new Matrix(matrix);
					City city = cities.getCity(rn.nextInt(cities.size()));
					
					if(cur_route.getCities().size()<3){
						if(randomStartCity!=0){
							//Set to AMS
							city = cities.getCity(0);
						}
					}

					if(cur_route.CityPresent(city)) {
						continue;
					}

					

					boolean valid_city_insert = cur_route.AddCity(city, cur_matrix, rn);

					//If the city insertion in route is valid. Then update the route
					if(valid_city_insert) {
						route = new Route(cur_route);
						matrix = new Matrix(cur_matrix);

					}
					route.CheckValidity();
				}

				//If the constructed route is better than current optimalRoute update the optimalRoute
				if(route.profit > optimalRoute.profit){
					optimalRoute = new Route(route);
				}	
			}			
			schedule.AddRoute(optimalRoute);			
		}

		return schedule;

	}

	void visualizeSchedule(Schedule schedule){
		System.out.println("Visualizing the schedule\n");
		Vector<Route> routes = schedule.Routes();
		
		Vector<Color>colors = new Vector<Color>();
		colors.add(Color.BLUE);
		colors.add(Color.BLACK);
		colors.add(Color.RED);
		colors.add(Color.YELLOW);
		colors.add(Color.GREEN);
		colors.add(Color.PINK);
		
		Visualization randomMap = new Visualization();
		
		for(int i=0;i<routes.size();i++) {
			Route route = routes.get(i);
			Vector<Coordinate> coor = new Vector<Coordinate>();
			for(int j =0; j<route.getCities().size();j++){
				coor.add(new Coordinate(route.getCities().get(j).X(), route.getCities().get(j).Y()));
			}
			
			randomMap.ColourRoute(coor, colors.get(i));			
		}
		
		randomMap.Show();

	}

	void start() {
		//Parse
		System.out.println("Parsing the input data");
		Parser parser = new Parser(INPUT_PATH);
		Cities cities = parser.ParseCities();
		Matrix matrix = parser.ParseMatrices(cities.size());

		//Random Model
		System.out.println("Running the random model");
		//Route route = RandomModel(cities, matrix);
		//Schedule schedule = RandomModel(cities, matrix);
		//printSchedule(schedule);
		//visualizeSchedule(schedule);
		
		out.println("Running the hill climbing model");
		
		/*int x=0;
		for(int i=1; i<cities.size(); i++) {
			Schedule schedule = HillClimbingModel(cities, matrix, cities.getID(i));
			if(schedule.Profit()>optimal_schedule.Profit()){
				optimal_schedule=new Schedule(schedule);
				x=i;
			}
		}*/
		Schedule optimal_schedule = HillClimbingModel(cities, matrix);
		printSchedule(optimal_schedule);
		visualizeSchedule(optimal_schedule);
		
		

	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
