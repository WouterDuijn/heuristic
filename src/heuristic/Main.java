package heuristic;

import java.io.PrintStream;

public class Main {
	
	public static final int NR_PLANES = 6;
	public static final String INPUT_PATH = "C:\\workspace\\heuristic\\inp\\";
	
	//tests
	

     Main() {
    	 //Initialize variables
                 
     }
     
     void Model(Cities cities, Matrix matrix) {
    	 //Run model
    	 Route route = new Route(cities.getCity(0));
    	 boolean cont = true;
    	 
    	 while(cont){
    		 double distance1=0;
    		 double distance2=0;
    		 
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
    					 }

    				 }
    			 }
    		 }
    	 }
     }
     
     void start() {
    	 //Parse input data
    	 System.out.println("Parsing the input data");
    	 Parser parser = new Parser(INPUT_PATH);
    	 Cities cities = parser.ParseCities();
    	 Matrix matrix = parser.ParseMatrices(cities.size());

    	 System.out.println("Running the model");
    	 //Model(cities, matrix);
    	 
    	
    	 
    	 
    	 
    	 
    	 //Visualize results
    	 
    	 
     }
     
     public static void main(String[] argv) {

         new Main().start();

}

}
