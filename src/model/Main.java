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

	public static final int NR_PLANES = 1;
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
		for(int i = 0; i<1; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(cities, m));
		}

		// Hill climbing algorithm (run all 5 schedules to find 5 local optima)
		Schedule current_schedule = new Schedule(schedules.get(0));
		
		for(int j=0;j< 100000;j++) {
			Schedule s = new Schedule(current_schedule);
			System.out.println(s.toString());
			if(s.Mutate(rn, cities) && s.Profit()>current_schedule.Profit()) {
				System.out.println(current_schedule.toString());
				current_schedule = new Schedule(s);
			}
		}
		return current_schedule;
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
					int randomCity = rn.nextInt(cities.size());
					
					City c = cities.getCity(randomCity);
					if(cur_route.CityPresent(c)) {
						continue;
					}

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

					cur_route.AddPassengers(randomIndex, randomPassenger1, randomPassenger2);

					boolean valid_city_insert = cur_route.AddCity(cities.getCity(randomCity), randomIndex, inputMatrix,
							randomPassenger1, randomPassenger2);

					//If the city insertion in route is valid. Then update the route
					if(valid_city_insert) {
						route = new Route(cur_route);
						//Adjust passenger matrix
						matrix.UpdatePassengers(before, randomCity, -randomPassenger1);
						matrix.UpdatePassengers(randomCity, beyond, -randomPassenger2);
						
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
		/*System.out.println("Running the random model");
		//Route route = RandomModel(cities, matrix);
		Schedule schedule = RandomModel(cities, matrix);
		printSchedule(schedule);
		visualizeSchedule(schedule);*/
		
		out.println("Running the hill climbing model");
		Schedule optimal_schedule = HillClimbingModel(cities, matrix);
		printSchedule(optimal_schedule);
		visualizeSchedule(optimal_schedule);
		
		

	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
