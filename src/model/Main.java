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
		
		/*double distance1=0;
		double distance2=0;
		
		distance1=matrix.Distance(cities.getID(0), cities.getID(1));
		distance2=matrix.Distance(cities.getID(1), cities.getID(0));

		RefuelTankTime example = route.isValidCityInsert(cities.getCity(1), 1, distance1, distance2);
		route.isValidNumberPax(1, 100, 100);
		route.AddCity(cities.getCity(1), 1, example, 100, 100, 100*distance1+100*distance2, distance1, distance2);
		
		double distance3=matrix.Distance(cities.getID(0), cities.getID(2));
		double distance4=matrix.Distance(cities.getID(2), cities.getID(1));
		
		RefuelTankTime example2 = route.isValidCityInsert(cities.getCity(2), 1, distance3,distance4);
		route.isValidNumberPax(1, 50, 50);
		route.AddCity(cities.getCity(2),1,example2,50,50,50*distance3+50*distance4,distance3,distance4);
		
		double distance5=matrix.Distance(cities.getID(2), cities.getID(5));
		double distance6=matrix.Distance(cities.getID(5), cities.getID(1));
		route.isValidCityInsert(cities.getCity(5), 2, distance5,distance6);*/
		
		/*while(cont){
			double distance1=0;
			double distance2=0;

			double profitIncrement=0;

			//Check for correct city, index and number of passengers
			for(int i=0; i<cities.size();i++){
				for(int j=1; j<route.size()-1;j++){

					distance1=matrix.Distance(cities.getID(j-1), cities.getID(i));
					distance2=matrix.Distance(cities.getID(i), cities.getID(j));

					//Check if insert is valid for time and fuel

					route.isValidCityInsert(cities.getCity(i),j,distance1,distance2);

					for(int k=9;k<199;k+=10){
						for(int l=9;l<199;l+=10){
							//Check if city can be added using  j, k & l

							route.isValidNumberPax(j,k,l);


							profitIncrement=k*distance1+l*distance2;
							out.printf("Profit: %.2f",profitIncrement);
							//Check for demand pax

						}


					}


				}

			}

		}*/
	}
	
	Route RandomModel(Cities cities, Matrix matrix){
		
		Route optimalRoute =new Route();
		
		//TODO: think of a good stop condition
		for(int j = 0; j<100000; j++){
			
			Matrix current_matrix = new Matrix(matrix);
			
			//TODO: deepcopy matrix
			
			Route route = new Route(cities.getCity(0));
		
			for(int i = 0; i<100; i++){
				Route cur_route= new Route(route);
				int randomCity = rn.nextInt(cities.size());
				
				//Is the index to insert the city in the route.
				int randomIndex =rn.nextInt(cur_route.size()-1)+1;
				
				//TODO: random passenger should bear in mind that limited nr of passengers given city pair
				int before = cur_route.getCities().get(randomIndex-1).ID();
				int beyond = cur_route.getCities().get(randomIndex).ID();
				
				int available_passengers1= current_matrix.Passengers(before, randomCity);
				int available_passengers2= current_matrix.Passengers(randomCity, beyond);
				
				int randomPassenger1 = rn.nextInt(Math.min(available_passengers1, (Route.getMaxPassengers()-
						cur_route.getPassengers().get(randomIndex-1)))+1);
				
				int randomPassenger2 = rn.nextInt(Math.min(available_passengers2, (Route.getMaxPassengers()-
						cur_route.getPassengers().get(randomIndex-1)))+1);
			
				
				//Are the two new distances of the two new edges created by inserting a new city
				double distance1=current_matrix.Distance(cur_route.getCities().get(randomIndex-1).ID(), cities.getID(randomCity));
				double distance2=current_matrix.Distance(cities.getID(randomCity), cur_route.getCities().get(randomIndex).ID());
				
				
				double incr_profit = distance1*randomPassenger1 + distance2*randomPassenger2;
				
				cur_route.AddPassengers(randomIndex, randomPassenger1, randomPassenger2);
				
				boolean valid_city_insert =  cur_route.isValidCityInsert(cities.getCity(randomCity), randomIndex, 
						distance1, distance2);

				//If the city insertion in route is valid. Then update the route
				if(valid_city_insert) {
					cur_route.IncrementProfit(incr_profit);
					route = new Route(cur_route);
					
					//Adjust passenger matrix
					current_matrix.DecreasePassengers(before, randomCity, randomPassenger1);
					current_matrix.DecreasePassengers(randomCity, beyond, randomPassenger2);

				}
				
				route.CheckValidity();
			}
			
			//If the constructed route is better than current optimalRoute update the optimalRoute
			if(route.profit > optimalRoute.profit){
				optimalRoute = new Route(route);
			}	
			
		}
		
		out.printf("Optimal route: %s\n", optimalRoute.toString());
		out.printf("Maximum profit: €%.2f\n", optimalRoute.profit);
		out.printf("Current time: %.2f", optimalRoute.current_time);
		return optimalRoute;
	}
	
	void visualizeRandomModel(Route route){
		Vector<Coordinate> coor = new Vector<Coordinate>();
		//System.out.println(route.getCities().size());
		//System.out.println(route.getCities().size());
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
		Route route = RandomModel(cities, matrix);
		visualizeRandomModel(route);
		
		//Model
		/*Model(cities, matrix);
		
		Vector<Coordinate> c = new Vector<Coordinate>();
		c.add(new Coordinate(205, 320));
		c.add(new Coordinate(425, 535));
		c.add(new Coordinate(160, 490));
		c.add(new Coordinate(280, 315));
		c.add(new Coordinate(445, 420));
		c.add(new Coordinate(350, 395));
		c.add(new Coordinate(275, 270));
		c.add(new Coordinate(100, 290));
		c.add(new Coordinate(205, 320));

		//Visualize results
		Map map = new Map();
		map.ColourRoute(c, Color.BLACK);*/
		//map.Show();
	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
