package model;

import dom.Cities;
import dom.City;
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

	public static final int 	NUM_INITIAL_SCHEDULES = 20,
								NO_IMPROVEMENT_ITERATIONS = 10000;
	
	public static final double	COOLING_RATE = 0.005,
								MIN_PROFIT_IMPROVEMENT = 0;
	
	//tests
	PrintStream out;
	Random rn;

	Main() {
		//Initialize variables
		out = new PrintStream(System.out);   
		rn = new Random();
	}
	
	double acceptanceProbability(double current_profit, double new_profit, double temperature) {
		// if new solution is better, accept it with prob. 1
		if(new_profit > current_profit) {
			return 1.0;
		}
		// if new solution is worse, calculate acceptance probability
		return Math.exp(new_profit-current_profit/temperature);
	}
	
	double randomDouble() {
		Random r = new Random();
		return r.nextInt(1000)/1000.0;
	}
	
	Schedule SimulatedAnnealing(Cities cities, Matrix matrix) {
		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();
		
		// create NUM_INITIAL_SCHEDULES initial random schedules
		for(int i = 0; i<NUM_INITIAL_SCHEDULES; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(cities, m));
		}

		for(int k =0;k<schedules.size();k++) {
			// Hill climbing algorithm (run all initial schedules to find the local optima)
			Schedule current_schedule = new Schedule(schedules.get(k));
			Schedule best_schedule = new Schedule(current_schedule); 
			
			// TODO: find good values for number and the constant RATE
			double temp = 10000;
			
			int num_mutations = 0;
			int tries = 0;
			
			while(temp>1) {
				tries++;
				Schedule newSchedule = new Schedule(current_schedule);

				if(newSchedule.Mutate(rn, cities)) {
					newSchedule.CheckValidity();
					num_mutations++;
					// TODO: print to text file and/or create graph
					//out.printf("Mutation: %d, profit: €%f\n", num_mutations, newSchedule.Profit());
					
					double randomNumber = randomDouble();
					if(acceptanceProbability(newSchedule.Profit(), current_schedule.Profit(), temp) > randomNumber) {
						current_schedule = new Schedule(newSchedule);
					}
					
					// keep track of best schedule
					if(current_schedule.Profit() > best_schedule.Profit()) {
						best_schedule = new Schedule(current_schedule);
					}
					temp *= 1 - COOLING_RATE;	
				}			
			}

			optimal_schedules.add(best_schedule);
			//out.printf("tries: %d\n", tries);
			//out.println();
		}

		Schedule best = new Schedule(optimal_schedules.get(0));

		for(Schedule s: optimal_schedules) {
			if(s.Profit()>best.Profit()) {
				best = new Schedule(s);
			}

		}
		return best;
	}
	
	Schedule HillClimberRestartModel(Cities cities, Matrix matrix) { 

		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();
		
		// create NUM_INITIAL_SCHEDULES initial random schedules
		for(int i = 0; i<NUM_INITIAL_SCHEDULES; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(cities, m));
		}

		for(int k =0;k<schedules.size();k++) {
			// Hill climbing algorithm (run all initial schedules to find the local optima)
			Schedule current_schedule = new Schedule(schedules.get(k));
			out.printf("Schedule: %d\n", k+1);
			
			int num_mutations = 0;
			int tries = 0;
			int no_improvement_iterations = 0;
			
			Schedule s = new Schedule(current_schedule);
			if(s.Mutate(rn, cities)) {
				s.CheckValidity();
				num_mutations++;
				// TODO: print to text file and create graph
				//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
				if(s.Profit()>current_schedule.Profit()) { // als mutate succesvol, print statement voor grafiek
					current_schedule = new Schedule(s); // x-as is teller hoe veel succesvolle mutates
				}
			}
			// if newly found profit - best_profit < MIN_PROFIT_IMPROVEMENT (barely any improvement) 
			// for NO_IMPROVEMENT_ITERATIONS iterations, stop searching for a better solution
			while(s.Profit()-current_schedule.Profit()>MIN_PROFIT_IMPROVEMENT || no_improvement_iterations<NO_IMPROVEMENT_ITERATIONS) {
				tries++;
				s = new Schedule(current_schedule);
				if(s.Mutate(rn, cities)) {
					s.CheckValidity();
					num_mutations++;
					// TODO: print to text file and/or create graph
					//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
					if(s.Profit()>current_schedule.Profit()) { // als mutate succesvol, print statement voor grafiek
						current_schedule = new Schedule(s); // x-as is teller hoe veel succesvolle mutates
						no_improvement_iterations=0;
					}
					if(s.Profit() - current_schedule.Profit() < MIN_PROFIT_IMPROVEMENT) {
						no_improvement_iterations++;
					}
				}
			}
			//TODO: Determine stop criteria. Now hardcoded to 100.000 mutations
/*			for(int j=0;j< 100000;j++) {
				Schedule s = new Schedule(current_schedule);
				if(s.Mutate(rn, cities)) {
					s.CheckValidity();
					num_mutations++;
					// TODO: print to text file and create graph
					out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
					if(s.Profit()>current_schedule.Profit()) { // als mutate succesvol, print statement voor grafiek
						current_schedule = new Schedule(s); // x-as is teller hoe veel succesvolle mutates
					}
				}
			}*/
			optimal_schedules.add(current_schedule);
			out.printf("tries: %d\n", tries);
			out.println();
		}

		Schedule best = new Schedule(optimal_schedules.get(0));

		for(Schedule s: optimal_schedules) {
			if(s.Profit()>best.Profit()) {
				best = new Schedule(s);
			}

		}
		return best;
	}

	void printSchedule(Schedule schedule) {
		for(int i=0; i<schedule.Routes().size(); i++) {
			out.printf(schedule.Routes().get(i).toString());
			out.println('\n');
		}
		out.printf("Total profit\t€%.2f\n", schedule.Profit());
	}

	Schedule RandomModel(Cities cities, Matrix inputMatrix) {
		Schedule schedule = new Schedule(inputMatrix);
		
		for(int k=0; k<Schedule.NR_PLANES;k++) {
			Route optimalRoute =new Route();
			for(int j = 0; j<1000; j++){ // create multiple routes to find best

				int randomStartCity = rn.nextInt(cities.size());
				Route route = new Route(cities.getCity(randomStartCity));
				Matrix matrix = new Matrix(schedule.Matrix());

				for(int i = 0; i<200; i++){ // to create 1 route
					Route cur_route= new Route(route);
					Matrix cur_matrix = new Matrix(matrix);
					City city = cities.getCity(rn.nextInt(cities.size()));

					if(cur_route.getCities().size()<3){
						if(randomStartCity!=0){
							//Set to AMS
							city = cities.getCity(0);
						}
					}

					if(cur_route.CityPresent(city)) {
						continue;
					}

					boolean valid_city_insert = cur_route.AddCity(city, cur_matrix, rn);

					//If the city insertion in route is valid. Then update the route
					if(valid_city_insert) {
						route = new Route(cur_route);
						matrix = new Matrix(cur_matrix);

					}
					route.CheckValidity();
				}

				//If the constructed route is better than current optimalRoute update the optimalRoute
				if(route.profit > optimalRoute.profit){
					optimalRoute = new Route(route);
				}	
			}			
			schedule.AddRoute(optimalRoute);			
		}
		return schedule;
	}

	void visualizeSchedule(Schedule schedule){
		System.out.println("Visualizing the schedule\n");
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
		Parser parser = new Parser();
		Cities cities = parser.ParseCities();
		Matrix matrix = parser.ParseMatrices(cities.size());

		//Random Model
		//System.out.println("Running the random model");
		//Schedule schedule = RandomModel(cities, matrix);
		//printSchedule(schedule);
		//visualizeSchedule(schedule);

		//Hill climbing model with restart
//		out.println("Running the hill climbing model with restart");
//		Schedule optimal_schedule = HillClimberRestartModel(cities, matrix);
//		printSchedule(optimal_schedule);
		//visualizeSchedule(optimal_schedule);
		
		//Hill climbing model (simulated annealing)
		out.println("Running the hill climbing model (simulated annealing)");
		Schedule optimal = SimulatedAnnealing(cities, matrix);
		printSchedule(optimal);
		//visualizeSchedule(optimal);
	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
