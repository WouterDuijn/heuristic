package heuristic;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Parser {
	
	private static final String CITIES_FILE_NAME = "MokumAirwaysCities.txt";
	private static final String DISTANCES_FILE_NAME = "MokumAirwaysCities.txt";
	private static final String PASSENGERS_FILE_NAME = "MokumAirwaysCities.txt";
			
	
	private String input_path;
	PrintStream out;
	
	Parser(String input_path){
		this.input_path = input_path;
		
		out = new PrintStream(System.out);
	}
	
	public Cities ParseCities(){
		//Initliaze empty Cities
		Cities cities =new Cities();
		
		//Parse the input data here
		File file = new File(input_path+CITIES_FILE_NAME);
		try {
			Scanner in = new Scanner(file);
			out.println(input_path+CITIES_FILE_NAME);
			//Skip first three header lines
			in.nextLine();
			in.nextLine();
			in.nextLine();
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				Scanner line_scanner = new Scanner(line);
				line_scanner.useDelimiter(",");
				int id = Integer.parseInt(line_scanner.next().trim());
				int x = Integer.parseInt(line_scanner.next().trim());
				int y = Integer.parseInt(line_scanner.next().trim());
				
				//Set delimiter for city name
				line_scanner.useDelimiter("\"");
				line_scanner.next();
				String name = line_scanner.next().trim();
				
				//Add city to cities structure
				cities.AddCity(new City(id, name, x, y));
				
			}
			in.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return cities;
		
	}
	
	public Matrix ParseMatrices(int number_of_cities) {
		Matrix matrix = new Matrix(number_of_cities);
		//Parse distance matrix
		
		
		
		//Parse passenger matrix
		
		
		
		return matrix;
	}
	

}
