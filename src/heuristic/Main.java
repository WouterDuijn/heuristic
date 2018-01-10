package heuristic;

import java.io.PrintStream;

public class Main {
	
	public static final int NR_PLANES = 6;
	public static final String INPUT_PATH = "C:\\workspace\\heuristic\\inp\\";
	
	//tests
	

     Main() {
    	 //Initialize variables
                 
     }
     
     void start() {
    	 //Parse input data
    	 Parser parser = new Parser(INPUT_PATH);
    	 Cities cities = parser.ParseCities();
    	 Matrix matrix = parser.ParseMatrices(cities.size());
    	 
    	 //Run model
    	 Route route = new Route(cities.getCity(0));
    	 boolean cont = true;
    	 
    	 while(cont){
    		 //Check for correct city, index and number of passengers
    		 for(int i=0; i<cities.size();i++){
    			 for(int j=1; j<route.size()-1;j++){
    				 
    				 //Check city is not same as neighbours
    				 
    				 
    				 //Check if insert is valid for time and fuel
    				 
    				 route.isValidCityInsert(i,j);
    				 
    				 
    				 for(int k=9;k<199;k+=10){
    					 for(int l=9;l<199;l+=10){
    						 
    						 
    						 //Check if city can be added using i, j, k & l
    						 
    						 route.isValidNumberPax(j,k,l);
    						 
    						 
    						 
    						 
    						 
    					 }
    					 
    					 
    					 
    				 }
    				 
    				 
    				 
    				 
    			 }
    			 
    			 
    			 
    		 }
    		 
    		 
    		 
    	 }
    	 
    	 
    	 
    	 
    	 //Visualize results
    	 
    	 
     }
     
     public static void main(String[] argv) {

         new Main().start();

}

}
