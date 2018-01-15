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
		Route route = new Route(cities.getCity(0));
		
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
		
		
		
		
		int randomCity = rn.nextInt(cities.size());
		int randomIndex = rn.nextInt(route.size())+1;
		int randomPassenger1 = rn.nextInt(200);
		int randomPassenger2 = rn.nextInt(200);
			
		double distance1=matrix.Distance(cities.getID(randomIndex-1), cities.getID(randomCity));
		double distance2=matrix.Distance(cities.getID(randomCity), cities.getID(randomIndex-1));
		RefuelTankTime first= route.isValidCityInsert(cities.getCity(randomCity), randomIndex, distance1, distance2);
		if(first.valid==true){
			route.isValidNumberPax(randomIndex, randomPassenger1, randomPassenger2);
			route.AddCity(cities.getCity(randomCity), randomIndex, first, randomPassenger1, randomPassenger2,randomPassenger1*distance1+randomPassenger2*distance2, distance1, distance2);
		}
			
		for(int i = 0; i<10;i++){
			int ranC = rn.nextInt(cities.size());
			int randomP1 = rn.nextInt(200);
			int randomP2 = rn.nextInt(200);
				
		}
	
		
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



	void start() {
		//Parse
		System.out.println("Parsing the input data");
		Parser parser = new Parser(INPUT_PATH);
		Cities cities = parser.ParseCities();
		Matrix matrix = parser.ParseMatrices(cities.size());

		
		
		//Model
		System.out.println("Running the model");
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
