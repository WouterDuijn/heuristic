package model;

import dom.Cities;
import dom.Visualization;
import dom.Matrix;
import dom.Route;
import dom.Parser;
import dom.Coordinate;
import dom.RefuelTankTime;
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

	Vector<Route> RandomModel(Cities cities, Matrix inputMatrix) {

		Vector<Route> schedule = new Vector<Route>();
		Matrix updatedMatrix = new Matrix(inputMatrix);
		
		for(int k=0; k<NR_PLANES;k++) {

			Route optimalRoute =new Route();
			//Matrix updated = new Matrix(updatedMatrix);
			//Matrix updated = updatedMatrix.deepCopy();

			//TODO: think of a good stop condition
			for(int j = 0; j<10000; j++){ // create 10000 routes to find best

				Matrix matrix = new Matrix(updatedMatrix);

				//TODO: deepcopy matrix
				int randomStartCity = rn.nextInt(cities.size());
				Route route = new Route(cities.getCity(randomStartCity));

				for(int i = 0; i<200; i++){ // to create 1 route
					Route cur_route= new Route(route);
					Matrix current_matrix = new Matrix(matrix);
					int randomCity = rn.nextInt(cities.size());

					if(cur_route.getCities().size()<3){
						if(randomStartCity!=0){
							randomCity = 0;
						}
					}

					//Is the index to insert the city in the route.
					int randomIndex =rn.nextInt(cur_route.size()-1)+1;

					//TODO: random passenger should bear in mind that limited nr of passengers given city pair
					int before = cur_route.getCities().get(randomIndex-1).ID();
					int beyond = cur_route.getCities().get(randomIndex).ID();

					int available_passengers1= current_matrix.Passengers(before, randomCity);
					//out.println(available_passengers1);
					int available_passengers2= current_matrix.Passengers(randomCity, beyond);
					//out.println(available_passengers2);
					//out.println();
					if(available_passengers1<0 || available_passengers2<0) {
						out.printf("%d, %d",available_passengers1,available_passengers2);
					}
					
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
						current_matrix.UpdatePassengers(before, randomCity, -randomPassenger1);
						current_matrix.UpdatePassengers(randomCity, beyond, -randomPassenger2);
						//matrix = new Matrix(current_matrix);
						
					}
					route.CheckValidity();
				}

				//If the constructed route is better than current optimalRoute update the optimalRoute
				if(route.profit > optimalRoute.profit){
					optimalRoute = new Route(route);
				}	

			}
			schedule.add(optimalRoute);
			
			for(int n=1; n<optimalRoute.getCities().size();n++) {
				int departureCity = optimalRoute.getCities().get(n-1).ID();
				int arrivalCity = optimalRoute.getCities().get(n).ID();
				int passengersOnFlight = optimalRoute.getPassengers().get(n-1);
				updatedMatrix.UpdatePassengers(departureCity, arrivalCity, -passengersOnFlight);
				
			for (int p =0; p<28; p++){
				for(int q =0; q<28; q++){
					if(updatedMatrix.Passengers(p,q)<0) {
						out.printf("%d, index(%d,%d) ",updatedMatrix.Passengers(p, q),p,q);
					}
					//out.printf("%d ",updatedMatrix.Passengers(p, q));
				}
			//	out.println();
			}
			out.println();
			}
	
			out.printf("Optimal route: %s\n", optimalRoute.toString());
			/*out.printf("Maximum profit: €%.2f\n", optimalRoute.profit);
			out.printf("Current time: %.2f\n", optimalRoute.current_time);
			out.println();*/
			//return optimalRoute;
			
			/*for (int p =0; p<28; p++){
				for(int q =0; q<28; q++){
					out.printf("%d ",updatedMatrix.Passengers(p, q));
				}
				out.println();
			}
			out.println();*/
		}

		return schedule;

	}

	void visualizeRandomModel(Route route){
		Vector<Coordinate> coor = new Vector<Coordinate>();
		for(int i =0; i<route.getCities().size();i++){
			coor.add(new Coordinate(route.getCities().get(i).X(), route.getCities().get(i).Y()));
		}
		Visualization randomMap = new Visualization();
		randomMap.ColourRoute(coor, Color.BLUE);
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
		Vector<Route> schedule = RandomModel(cities, matrix);
		//visualizeRandomModel(route);

	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
