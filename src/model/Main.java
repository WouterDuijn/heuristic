package model;

import dom.Cities;
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
		for(int i = 0; i<5; i++) {
			schedules.add(RandomModel(cities, matrix));
		}

		// Hill climbing algorithm (run all 5 schedules to find 5 local optima)
		Schedule optimal_schedule;
		Schedule current_schedule;
		double highest_profit;
		double current_profit;
		
		for(int i = 0; i<schedules.size(); i++) {
			optimal_schedule = schedules.get(i); // only necessary if no while loop executed
			current_schedule = schedules.get(i);
			highest_profit = 0;
			current_profit = current_schedule.Profit();
			
			while(current_profit>highest_profit && current_profit-highest_profit>profit_improvement) {
				optimal_schedule = current_schedule;
				highest_profit = current_profit;

				// perform mutations
			}
			optimal_schedules.add(optimal_schedule);
		}
		
		// Determine best schedule of local optima
		Schedule best_schedule = new Schedule(matrix);
		for(int j = 0; j<optimal_schedules.size(); j++) {
			current_schedule = optimal_schedules.get(j);
			if(current_schedule.Profit() > best_schedule.Profit()) {
				best_schedule = current_schedule;
			}
		}
		return best_schedule;
	}
	
	// added printing method so RandomModel wouldn't print when used in HillClimbingModel
	void printSchedule(Schedule schedule) {
		for(int i=0; i<schedule.Routes().size(); i++) {
			out.printf(schedule.Routes().get(i).toString());
			out.println('\n');
		}
	}

	Schedule RandomModel(Cities cities, Matrix inputMatrix) {
		Schedule schedule = new Schedule(inputMatrix);

		for(int k=0; k<NR_PLANES;k++) {
			Route optimalRoute =new Route();

			//TODO: think of a good stop condition
			for(int j = 0; j<1000; j++){ // create 10000 routes to find best

				int randomStartCity = rn.nextInt(cities.size());
				Route route = new Route(cities.getCity(randomStartCity));
				Matrix matrix = new Matrix(schedule.Matrix());

				for(int i = 0; i<200; i++){ // to create 1 route
					Route cur_route= new Route(route);
					int randomCity = rn.nextInt(cities.size());

					if(cur_route.getCities().size()<3){
						if(randomStartCity!=0){
							randomCity = 0;
						}
					}

					//Is the index to insert the city in the route.
					int randomIndex =rn.nextInt(cur_route.size()-1)+1;

					int before = cur_route.getCities().get(randomIndex-1).ID();
					int beyond = cur_route.getCities().get(randomIndex).ID();

					int available_passengers1= matrix.Passengers(before, randomCity);
					int available_passengers2= matrix.Passengers(randomCity, beyond);
					
					int randomPassenger1 = rn.nextInt(Math.min(available_passengers1, (Route.getMaxPassengers()-
							cur_route.getPassengers().get(randomIndex-1)))+1);

					int randomPassenger2 = rn.nextInt(Math.min(available_passengers2, (Route.getMaxPassengers()-
							cur_route.getPassengers().get(randomIndex-1)))+1);

					//Are the two new distances of the two new edges created by inserting a new city
					double distance1=inputMatrix.Distance(cur_route.getCities().get(randomIndex-1).ID(), cities.getID(randomCity));
					double distance2=inputMatrix.Distance(cities.getID(randomCity), cur_route.getCities().get(randomIndex).ID());

					double incr_profit = distance1*randomPassenger1 + distance2*randomPassenger2;

					cur_route.AddPassengers(randomIndex, randomPassenger1, randomPassenger2);

					boolean valid_city_insert = cur_route.AddCity(cities.getCity(randomCity), randomIndex, distance1, distance2);

					//If the city insertion in route is valid. Then update the route
					if(valid_city_insert) {
						cur_route.IncrementProfit(incr_profit);
						cur_route.AddBooking(before, randomCity, randomPassenger1);
						cur_route.AddBooking(randomCity, beyond, randomPassenger2);
						route = new Route(cur_route);

						//Adjust passenger matrix
						matrix.UpdatePassengers(before, randomCity, -randomPassenger1);
						matrix.UpdatePassengers(randomCity, beyond, -randomPassenger2);
						//matrix = new Matrix(current_matrix);
						
					}
					route.CheckValidity();
				}

				//If the constructed route is better than current optimalRoute update the optimalRoute
				if(route.profit > optimalRoute.profit){
					optimalRoute = new Route(route);
				}	
			}			
			schedule.AddRoute(optimalRoute);			
			//out.printf("Optimal route: %s\n", optimalRoute.toString());
		}

		return schedule;

	}

	void visualizeSchedule(Schedule schedule){
		System.out.println("Visualizing the schedule");
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
		Schedule schedule = RandomModel(cities, matrix);
		printSchedule(schedule);
		visualizeSchedule(schedule);
		
		out.println("Running the hill climbing model");
		Schedule optimal_schedule = HillClimbingModel(cities, matrix);
		printSchedule(optimal_schedule);
		visualizeSchedule(optimal_schedule);

	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
