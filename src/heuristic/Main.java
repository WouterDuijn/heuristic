package heuristic;

import java.awt.Color;
import java.io.PrintStream;
import java.util.Vector;

public class Main {

	public static final int NR_PLANES = 6;
	public static final String INPUT_PATH = "C:\\workspace\\heuristic\\inp\\";

	//tests
	PrintStream out;

	Main() {
		//Initialize variables
		out = new PrintStream(System.out);       
	}

	void Model(Cities cities, Matrix matrix) {
		//Run model
		Route route = new Route(cities.getCity(0));
		boolean cont = true;

		while(cont){
			double distance1=0;
			double distance2=0;

			double profitIncrement=0;

			//Check for correct city, index and number of passengers
			for(int i=0; i<cities.size();i++){
				for(int j=1; j<route.size()-1;j++){

					distance1=matrix.Distance(cities.getID(j-1), cities.getID(i));
					distance2=matrix.Distance(cities.getID(i), cities.getID(j));

					//Check if insert is valid for time and fuel

					route.isValidCityInsert(cities.getID(i),j,distance1,distance2);

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

		}
	}



	void start() {
		//Parse
		System.out.println("Parsing the input data");
		Parser parser = new Parser(INPUT_PATH);
		Cities cities = parser.ParseCities();
		Matrix matrix = parser.ParseMatrices(cities.size());

		
		
		//Model
		System.out.println("Running the model");
		//Model(cities, matrix);
		
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
		map.Show();

	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
