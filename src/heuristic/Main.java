package heuristic;

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
	
	Vector<City> RandomModel(Cities cities, Matrix matrix){
		double maxProfit=0;
		Vector<City> optimalRoute = new Vector<City>();
		double time=0;
		Vector<Integer> passenger = new Vector<Integer>(); 
		//TODO: think of a good stop condition
		//TODO: include passenger input matrix
		for(int j = 0; j<100; j++){
			Route route = new Route(cities.getCity(0));
		
			for(int i = 0; i<100; i++){
				int randomCity = rn.nextInt(cities.size());
				int randomIndex = rn.nextInt(route.size()-1)+1;
				int maxPassenger1 = Math.max(route.MAX_PASSENGERS-route.passengers.get(randomIndex-1), matrix.Passengers(route.cities.get(randomIndex-1).ID(), cities.getID(randomCity)));
				int maxPassenger2 = Math.max(route.MAX_PASSENGERS-route.passengers.get(randomIndex-1), matrix.Passengers(cities.getID(randomCity), route.cities.get(randomIndex).ID()));
				
				int randomPassenger1 = rn.nextInt(maxPassenger1+1);
				int randomPassenger2 = rn.nextInt(maxPassenger2+1);
					
				//int randomPassenger1 = rn.nextInt(route.MAX_PASSENGERS-route.passengers.get(randomIndex-1)+1);
				//int randomPassenger2 = rn.nextInt(route.MAX_PASSENGERS-route.passengers.get(randomIndex-1)+1);
					
				double distance1=matrix.Distance(route.cities.get(randomIndex-1).ID(), cities.getID(randomCity));
				double distance2=matrix.Distance(cities.getID(randomCity), route.cities.get(randomIndex).ID());

				RefuelTankTime x= route.isValidCityInsert(cities.getCity(randomCity), randomIndex, distance1, distance2);
				if(x.valid==true){
					if(route.isValidNumberPax(randomIndex, randomPassenger1, randomPassenger2)){
						route.AddCity(cities.getCity(randomCity), randomIndex, x, randomPassenger1, randomPassenger2, distance1, distance2);
					}	
				}
			}
			//out.println(route.cities.toString());
			//out.printf("Current time: %.2f	", route.current_time);
			//out.printf("Profit: €%.2f\n", route.profit);
			if(route.profit > maxProfit){
				maxProfit=route.profit;
				optimalRoute=route.cities;
				time=route.current_time;
				passenger=route.passengers;
			}	
		}
		out.printf("Optimal route: %s\n", optimalRoute.toString());
		out.printf("Maximum profit: €%.2f\n", maxProfit);
		out.printf("Current time: %.2f\n", time);
		out.printf("Passengers: %s", passenger);
		return optimalRoute;
	}
	
	void visualizeRandomModel(Vector<City> cities){
		Vector<Coordinate> coor = new Vector<Coordinate>();
		for(int i =0; i<cities.size();i++){
			coor.add(new Coordinate(cities.get(i).X(), cities.get(i).Y()));
		}
		Map randomMap = new Map();
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
		Vector<City> optimal = RandomModel(cities, matrix);
		visualizeRandomModel(optimal);
		
		//Model
		Model(cities, matrix);
		
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
		map.ColourRoute(c, Color.BLACK);
		//map.Show();
	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
