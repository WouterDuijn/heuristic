package model;

import dom.Cities;
import dom.City;
import dom.Visualization;
import dom.Matrix;
import dom.Route;
import dom.Schedule;
import dom.Parser;
import java.io.PrintStream;
import java.util.Vector;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	
	public enum Algorithm {Random, HillClimber, HillClimberRestart, SimulatedAnnealing,
		HomeBase}
	
	//Input path of the input files
	public static final String INPUT_PATH = "C:\\workspace\\heuristic\\inp\\";
	
	//Specify the algorithm, nr of runs and the total iterations
	public static final Algorithm ALGORITHM = Algorithm.HillClimber;
	public static final int 	NR_RUNS = 50,
								TOTAL_ITERATIONS=2000000;
	
	//For Hillcimber Restart: specifies the number of iterations without improvement 
	//to start new schedule
	public static final int NO_IMPROVEMENT_ITERATIONS = 1000;
	
	//Specifies the number of random routes for the random algorithm generated
	//(best is drawn for these routes)
	public static final int	NUM_RANDOM_SCHEDULES = 1000;
	
	
	//option to speicify whether the results should be printen to file
	public static final boolean WRITE_TO_FILE = false;
	
	//options when initial schedule needs to be withing boundary
	public static final boolean RANDOM_SCHEDULE_RANGE=false;
	public static final int LOWERBOUND = 3000000;
	public static final int UPPERBOUND = 3500000;
								
	//Simulated Annealing parameters
	public static final int TEMPERATURE = 163048;
	public static final double	COOLING_RATE = 0.0003;

	public static final long 	SEED = 441287210;

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
		return Math.exp((new_profit-current_profit)/temperature);
	}

	Schedule SimulatedAnnealing(Cities cities, Matrix matrix, City homebase, boolean write_to_file,
			boolean range, int temperature, double cooling_rate) {
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();

		try {
			Vector<FileWriter> filewriters = new Vector<FileWriter>();

			for(int k =0;k<NR_RUNS;k++) {
				//File writer stuff
				FileWriter filewriter=null;
				if(write_to_file) {
					String filename = "C:\\workspace\\heuristic\\SimulatedAnnealing\\Schedule_" 
							+ (k+1) + "_Temp_" + temperature +"_CR_" + cooling_rate + ".txt";
							filewriters.add(new FileWriter(new File(filename)));
							filewriter = filewriters.lastElement();
				}
				
				// Initialize initial solution
				Schedule current_schedule = new Schedule(RandomSchedule(rn, homebase, cities, 
						new Matrix(matrix), range, LOWERBOUND, UPPERBOUND));
				// Set the initial solution as current best
				Schedule best_schedule = new Schedule(current_schedule); 

				double temp = temperature;
	
				//int num_accepted_mutations = 0;
				int iteration=0;

				// Loop until system has cooled
				while(temp>1) {
					// Create new neighbor schedule
					Schedule newSchedule = new Schedule(current_schedule);

					if(newSchedule.Mutate(rn, cities)) {
						newSchedule.CheckValidity();
						iteration++;
						
						// Decide whether the neighbor should be accepted or not
						if(acceptanceProbability(current_schedule.Profit(), newSchedule.Profit(), temp) > Math.random()) {
							current_schedule = new Schedule(newSchedule);
						}
						
						// Keep track of best schedule found so far
						if(current_schedule.Profit() > best_schedule.Profit()) {
							best_schedule = new Schedule(current_schedule);
						}
						
						if(write_to_file) {
							String newDataPoint = iteration + "\t" + current_schedule.Profit()/1000000 + "\n";
							filewriter.write(newDataPoint);
						}
						
						// Cool the system
						temp *= 1 - cooling_rate;	
					}			
				}
				optimal_schedules.add(best_schedule);
				if(write_to_file) {
					filewriter.close();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}

		Schedule best = new Schedule(optimal_schedules.get(0));

		for(Schedule s: optimal_schedules) {
			if(s.Profit()>best.Profit()) {
				best = new Schedule(s);
			}

		}
		return best;
	}

	Schedule HillClimber(Cities cities, City homebase, Matrix matrix, boolean write_to_file,
			boolean range) { 

		Vector<Schedule> optimal_schedules = new Vector<Schedule>();
		Vector<FileWriter> filewriters = new Vector<FileWriter>();

		for(int k =0;k<NR_RUNS;k++) {
			try {
				//Initialize file writer stuff
				FileWriter filewriter=null;
				if(write_to_file) {
					String filename = "C:\\workspace\\heuristic\\HillClimber\\HC_Schedule_"
							+ (k+1) + "_runs_" + NR_RUNS + "_total_iterations_"
							+ TOTAL_ITERATIONS + "_randomroutes_" + NUM_RANDOM_SCHEDULES + ".txt";
					filewriters.add(new FileWriter(new File(filename)));
					filewriter = filewriters.lastElement();
				}
				
				//Set starting schedule for hill climb run
				Schedule current_schedule = new Schedule(RandomSchedule(rn, homebase, cities, new Matrix(matrix),
						range, LOWERBOUND, UPPERBOUND));
				int iteration = 0;
				while(iteration< TOTAL_ITERATIONS/NR_RUNS) {
					Schedule s = new Schedule(current_schedule);
					if(s.Mutate(rn, cities)) {
						s.CheckValidity();
						iteration++;
						
						//Update schedule if profit is higher
						if(s.Profit()>current_schedule.Profit()) {
							current_schedule = new Schedule(s); 
						}
						
						//Write to file
						if(write_to_file) {
							String newDataPoint = iteration + "\t" + current_schedule.Profit()/1000000 + "\n";
							filewriter.write(newDataPoint);
						}
					}
				}
				optimal_schedules.add(current_schedule);
				if(write_to_file) {
					filewriter.close();
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		//Determine best run of all runs
		Schedule best = new Schedule(optimal_schedules.get(0));
		for(Schedule s: optimal_schedules) {
			if(s.Profit()>best.Profit()) {
				best = new Schedule(s);
			}
		}
		return best;
	}
	
	Schedule HillClimberRestartModel(Random random, Cities cities, City homebase, Matrix matrix, 
			boolean write_to_file, boolean range) { 

		Vector<Schedule> optimal_schedules = new Vector<Schedule>();
		Vector<FileWriter> filewriters = new Vector<FileWriter>();
		for(int k =0;k<NR_RUNS;k++) {
			try {
				//Filewriter initialization
				FileWriter filewriter = null;
				if(write_to_file) {
					String filename = "C:\\workspace\\heuristic\\HillClimberRestart\\HCRestart_" + (k+1)
							+ "_runs_" + NR_RUNS + "_total_iterations_"
							+ TOTAL_ITERATIONS + "_randomroutes_" + NUM_RANDOM_SCHEDULES + ".txt";
							filewriters.add(new FileWriter(new File(filename)));
					filewriter = filewriters.lastElement();
				}
				
				// Hill climbing algorithm 
				Schedule current_schedule = new Schedule(RandomSchedule(random, homebase, 
						cities, new Matrix(matrix), range, LOWERBOUND, UPPERBOUND));
				Schedule best_schedule = new Schedule(current_schedule);
				int iteration = 0;
				int no_improvement_iterations = 0;
				
				while(iteration<(TOTAL_ITERATIONS/NR_RUNS)) {
					Schedule s = new Schedule(current_schedule);
					if(s.Mutate(rn, cities)) {
						s.CheckValidity();
						iteration++;

						//Determine whether current schedule should be updated
						if(s.Profit()>current_schedule.Profit()) { 
							current_schedule = new Schedule(s); 
							no_improvement_iterations=0;
						}else {
							no_improvement_iterations++;
						}
						
						//Write to file
						if(write_to_file) {
							String newDataPoint = iteration + "\t" + current_schedule.Profit()/1000000 + "\n";
							filewriter.write(newDataPoint);
						}
						
						//Update best known schedule of the run
						if(current_schedule.Profit()>best_schedule.Profit()) {
							best_schedule = new Schedule(current_schedule);
						}
					}
					
					if(no_improvement_iterations>NO_IMPROVEMENT_ITERATIONS) {
						no_improvement_iterations=0;
						current_schedule = new Schedule(RandomSchedule(random, homebase, cities, 
								new Matrix(matrix), range, LOWERBOUND, UPPERBOUND));
					}
				}
				optimal_schedules.add(best_schedule);
				if(write_to_file) {
					filewriter.close();
				}
				
			} catch(IOException e) {
				e.printStackTrace();
			}
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
			out.println();
		}
		out.printf("Total profit\t€%.2f\n", schedule.Profit());
	}

	Schedule RandomSchedule(Random rngen, City homebase, Cities cities, Matrix inputMatrix,
			boolean range, int lower, int upper) {
		boolean schedule_found=false;
		
		while(!schedule_found) {
			Schedule schedule = new Schedule(inputMatrix);
			for(int k=0; k<Schedule.NR_PLANES;k++) {
				Route optimalRoute =new Route();
				for(int j = 0; j<NUM_RANDOM_SCHEDULES; j++){ 
					int randomStartCity = rngen.nextInt(cities.size());
					Route route = new Route(cities.getCity(randomStartCity), homebase.ID());
					Matrix matrix = new Matrix(schedule.Matrix());

					for(int i = 0; i<200; i++){ // to create 1 route
						Route cur_route= new Route(route);
						Matrix cur_matrix = new Matrix(matrix);
						City city = cities.getCity(rngen.nextInt(cities.size()));

						if(cur_route.getCities().size()<3){
							if(randomStartCity!=homebase.ID()){
								//Set to homebase
								city = cities.getCity(homebase.ID());
							}
						}

						if(cur_route.CityPresent(city)) {
							continue;
						}

						boolean valid_city_insert = cur_route.AddCity(city, cur_matrix, rngen);

						//If the city insertion in route is valid. Then update the route
						if(valid_city_insert) {
							route = new Route(cur_route);
							matrix = new Matrix(cur_matrix);
							j++;

						}
						route.CheckValidity();
					}

					//If the constructed route is better than current optimalRoute update the optimalRoute
					if(route.profit > optimalRoute.profit){
						//optimalRoute = route;
						optimalRoute = new Route(route);
					}
				}			
				schedule.AddRoute(optimalRoute);			
			}
			if(!range) {
				return schedule;
			}else if(schedule.Profit() > lower && schedule.Profit() < upper) {
				return schedule;
			}
		}
		return new Schedule();
	}

	Schedule bestHometownSchedule(Cities cities, Matrix matrix) {
		Schedule best_schedule = new Schedule(matrix);

		for(int i=0; i<cities.size(); i++) {
			City homebase = cities.getCity(i);
			Random rngen = new Random(SEED);
			Schedule current_schedule = HillClimberRestartModel(rngen, cities, homebase, matrix, 
					WRITE_TO_FILE, RANDOM_SCHEDULE_RANGE);
			// TODO: use simulatedAnnealing (with parameter rngen) to determine best hometown?
			//Schedule current_schedule = SimulatedAnnealing(rngen, ...)
			if(current_schedule.Profit()>best_schedule.Profit()) {
				best_schedule = current_schedule;
			}					
		}
		return best_schedule;
	}

	void start() {
		//Parse input files
		Parser parser = new Parser(INPUT_PATH);
		Cities cities = parser.ParseCities();
		Matrix matrix = parser.ParseMatrices(cities.size());
		
		//Execute the algorithm and retrieve the best schedule
		Schedule schedule = new Schedule();
		switch(ALGORITHM) {
		case Random:
			System.out.println("Running the random model");
			schedule = RandomSchedule(rn, cities.getCity(0), cities, matrix, 
					RANDOM_SCHEDULE_RANGE, LOWERBOUND,UPPERBOUND);
			break;
		case HillClimber:
			out.println("Running the hill climbing model");
			schedule = HillClimber(cities, cities.getCity(0), matrix,WRITE_TO_FILE, 
					RANDOM_SCHEDULE_RANGE);
			break;
		case HillClimberRestart:
			out.println("Running the hill climbing model with restart");
			schedule = HillClimberRestartModel(rn, cities, cities.getCity(0),matrix, WRITE_TO_FILE,
					RANDOM_SCHEDULE_RANGE);
			break;
		case SimulatedAnnealing:
			out.println("Running the simulated annealing model");
			schedule = SimulatedAnnealing(cities, matrix, cities.getCity(0), WRITE_TO_FILE,
					RANDOM_SCHEDULE_RANGE, TEMPERATURE, COOLING_RATE);
			break;
		case HomeBase:
			out.println("Searching for the best hometown...\n");
			schedule = bestHometownSchedule(cities, matrix);
			City best_homebase = cities.getCity(schedule.Routes().get(0).hometownID());
			out.printf("The best hometown is %s\n", best_homebase.toString());
			break;
		}
		
		//Print and visualize best schedule
		printSchedule(schedule);
		new Visualization(schedule);
	}

	public static void main(String[] argv) {
		new Main().start();

	}

}

