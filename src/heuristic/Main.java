package heuristic;

import java.io.PrintStream;

public class Main {
	
	public static final int NR_PLANES = 6;
	public static final String INPUT_PATH = "C:\\workspace\\heuristic\\inp\\";
	

     Main() {
    	 //Initialize variables
                 
     }
     
     void start() {
    	 //Parse input data
    	 Parser parser = new Parser(INPUT_PATH);
    	 parser.ParseCities();
    	 parser.ParseDistances();
    	 parser.ParsePassengers();
    	 
    	 //Run model
    	 
    	 
    	 //Visualize results
    	 
    	 
     }
     
     public static void main(String[] argv) {

         new Main().start();

}

}
