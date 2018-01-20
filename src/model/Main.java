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

	void Model(Cities cities, Matrix matrix) {
		//Run model
		boolean cont = true;
	}

	Schedule RandomModel(Cities cities, Matrix inputMatrix) {
		Schedule schedule = new Schedule(inputMatrix);

		for(int k=0; k<1;k++) {
			Route optimalRoute =new Route();

			//TODO: think of a good stop condition
			for(int j = 0; j<100; j++){ // create 10000 routes to find best

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

					cur_route.AddPassengers(randomIndex, randomPassenger1, randomPassenger2);

					boolean valid_city_insert = cur_route.AddCity(cities.getCity(randomCity), randomIndex, inputMatrix,
							randomPassenger1, randomPassenger2);

					//If the city insertion in route is valid. Then update the route
					if(valid_city_insert) {
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
			out.printf("Optimal route: %s\n", optimalRoute.toString());
			
			Matrix m = new Matrix(schedule.Matrix());
			boolean check = optimalRoute.Mutate(rn, m, cities);
			System.out.println(check);
			if(check) {
				System.out.println(optimalRoute.toString());
			}

		}

		return schedule;

	}

	void visualizeRandomModel(Schedule schedule){
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
		visualizeRandomModel(schedule);

	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
