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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	public static final int 	NUM_INITIAL_SCHEDULES = 20,
								NUM_RANDOM_ROUTES = 10000,
								NO_IMPROVEMENT_ITERATIONS = 100,
								NUM_ITERATIONS = 100000;

	public static final double	COOLING_RATE = 0.003;

	public static final long 	SEED = 441287210;

	//tests
	PrintStream out;
	Random rn;
	//Random rn2;

	Main() {
		//Initialize variables
		out = new PrintStream(System.out);   
		rn = new Random();
		//rn2 = new Random(SEED);
	}

	double acceptanceProbability(double current_profit, double new_profit, double temperature) {
		// if new solution is better, accept it with prob. 1
		if(new_profit > current_profit) {
			return 1.0;
		}
		// if new solution is worse, calculate acceptance probability
		return Math.exp(new_profit-current_profit/temperature);
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
			// Initialize 
			Schedule current_schedule = new Schedule(schedules.get(k));
			Schedule best_schedule = new Schedule(current_schedule); 

			// TODO: find good values for temperature and the constant RATE
			// TODO: plot temperature (y) versus number of mutations??
			// TODO: plot profit (y) against number of mutations (x)
			double temp = 10000;

			int num_mutations = 0;
			int tries = 0;

			// Loop until system has cooled
			while(temp>1) {
				//tries++;
				// Create new neighbour schedule
				Schedule newSchedule = new Schedule(current_schedule);

				// Perform a single random mutation on the neighbour schedule
				if(newSchedule.Mutate(rn, cities)) {
					newSchedule.CheckValidity();
					num_mutations++;
					out.printf("Mutation: %d, profit: €%f\n", num_mutations, newSchedule.Profit());

					// Decide whether the neighbour should be accepted or not
					if(acceptanceProbability(newSchedule.Profit(), current_schedule.Profit(), temp) > Math.random()) {
						current_schedule = new Schedule(newSchedule);
					}

					// Keep track of best schedule found so far
					if(current_schedule.Profit() > best_schedule.Profit()) {
						best_schedule = new Schedule(current_schedule);
					}
					
					// Cool the system
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

	// TODO: write to text files for different values of temp and COOLING_RATE to test which to choose.
	Schedule SimulatedAnnealingToFiles(Cities cities, Matrix matrix) {
		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();

		try {

			Vector<FileWriter> filewriters = new Vector<FileWriter>();
			
			// create NUM_INITIAL_SCHEDULES initial random schedules
			for(int i = 0; i<NUM_INITIAL_SCHEDULES; i++) {
				Matrix m = new Matrix(matrix);
				//schedules.add(RandomModel(0,cities, m));
				schedules.add(RandomModel(cities, m));
			}

			for(int k =0;k<schedules.size();k++) {
				// Initialize initial solution
				Schedule current_schedule = new Schedule(schedules.get(k));
				
				// Set the initial solution as current best
				Schedule best_schedule = new Schedule(current_schedule); 

				// TODO: find good values for temperature and the constant RATE
				// TODO: plot temperature (y) versus number of mutations??
				// TODO: plot profit (y) against number of mutations (x)
				double temp = 100000;
				String filename = "C:\\workspace\\heuristic\\SimulatedAnnealing\\Schedule_" 
				+ (k+1) + "_Temp_" + temp +"_CR_" + COOLING_RATE + ".txt";
				filewriters.add(new FileWriter(new File(filename)));
				FileWriter filewriter = filewriters.lastElement();

				int num_accepted_mutations = 0;

				// Loop until system has cooled
				while(temp>1) {

					// Create new neighbour schedule
					Schedule newSchedule = new Schedule(current_schedule);

					// Perform a single random mutation on the neighbour schedule
					//if(newSchedule.Mutate(rn2, cities)) {
					if(newSchedule.Mutate(rn, cities)) {
						newSchedule.CheckValidity();
						
						// TODO: print to text file and/or create graph
						//out.printf("Mutation: %d, profit: €%f\n", num_mutations, newSchedule.Profit());
						//String newDataPoint = num_mutations + "\t" + newSchedule.Profit() + "\n";
						//out.printf(newDataPoint);
						//filewriter.write(newDataPoint); 
						
						// Decide whether the neighbour should be accepted or not
						if(acceptanceProbability(newSchedule.Profit(), current_schedule.Profit(), temp) > Math.random()) {
							current_schedule = new Schedule(newSchedule);
							// kan ook denk ik: current_schedule = newSchedule;
							num_accepted_mutations++;
							String newDataPoint = num_accepted_mutations + "\t" + newSchedule.Profit() + "\n";
							out.printf(newDataPoint);
							filewriter.write(newDataPoint); // writes data point as mutation\tprofit (thus x tab y)
							// bij mij in kladblok lijkt het alsof er geen enter tussen de regels staat,
							// maar als je het document opent in word, zitten er wel regels tussen dus dat moet goed zijn.
						}

						// Keep track of best schedule found so far
						if(current_schedule.Profit() > best_schedule.Profit()) {
							best_schedule = new Schedule(current_schedule);
						}
						
						// Cool the system
						temp *= 1 - COOLING_RATE;	
					}			
				}

				optimal_schedules.add(best_schedule);
				//out.printf("tries: %d\n", tries);
				//out.println();
				//filewriter.flush();
				filewriter.close();
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

	Schedule HillClimber(Cities cities, Matrix matrix) { 

		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();

		// create NUM_INITIAL_SCHEDULES initial random schedules
		for(int i = 0; i<NUM_INITIAL_SCHEDULES; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(cities, m));
		}
		
		// (run all initial schedules to find the local optima)
		for(int k =0;k<schedules.size();k++) {
			// Hill climbing algorithm 
			Schedule current_schedule = new Schedule(schedules.get(k));
			out.printf("Schedule: %d\n", k+1);

			int num_mutations = 0;
			int tries = 0;

			//TODO: Determine stop criteria. Now hardcoded to 100.000 mutations
			for(int j=0;j< NUM_ITERATIONS;j++) {
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
			}
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
	
	Schedule HillClimberRestartModel(Cities cities, Matrix matrix) { 

		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();

		// create NUM_INITIAL_SCHEDULES initial random schedules
		for(int i = 0; i<NUM_INITIAL_SCHEDULES; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(cities, m));
		}

		// (run all initial schedules to find the local optima)
		for(int k =0;k<schedules.size();k++) {
			// Hill climbing algorithm 
			Schedule current_schedule = new Schedule(schedules.get(k));
			//out.printf("Schedule: %d\n", k+1);

			int num_mutations = 0;
			//int tries = 0;
			int no_improvement_iterations = 0;

			Schedule s = new Schedule(current_schedule);
			if(s.Mutate(rn, cities)) {
				s.CheckValidity();
				num_mutations++;

				//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
				if(s.Profit()>current_schedule.Profit()) { 
					current_schedule = new Schedule(s); 
				}
			}
			
			// if no improvement found for NO_IMPROVEMENT_ITERATIONS iterations, stop searching for a better solution
			while(s.Profit() > current_schedule.Profit() || no_improvement_iterations<NO_IMPROVEMENT_ITERATIONS) {
				//tries++;
				s = new Schedule(current_schedule);
				if(s.Mutate(rn, cities)) {
					s.CheckValidity();
					num_mutations++;
					// TODO: print to text file and/or create graph
					//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
					if(s.Profit()>current_schedule.Profit()) { // als mutate succesvol, print statement voor grafiek
						current_schedule = new Schedule(s); // x-as is teller hoe veel succesvolle mutates
						no_improvement_iterations=0;
					} else {
						no_improvement_iterations++;
					}
				}
			}
			
			optimal_schedules.add(current_schedule);
			//out.printf("tries: %d\n", tries);
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

	Schedule HillClimberRestart2(Random rngen, Cities cities, Matrix matrix) { 		
		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();

		// create NUM_INITIAL_SCHEDULES initial random schedules
		for(int i = 0; i<NUM_INITIAL_SCHEDULES; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(rngen, 0, cities, m));
		}

		// (run all initial schedules to find the local optima)
		for(int k=0; k<schedules.size(); k++) {
			// Hill climbing algorithm 
			Schedule current_schedule = new Schedule(schedules.get(k));
			//out.printf("Schedule: %d\n", k+1);

			int num_mutations = 0;
			//int tries = 0;
			int no_improvement_iterations = 0;

			Schedule s = new Schedule(current_schedule);
			if(s.Mutate(rngen, cities)) {
				s.CheckValidity();
				num_mutations++;
				// TODO: print to text file and create graph
				//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
				if(s.Profit()>current_schedule.Profit()) { // als mutate succesvol, print statement voor grafiek
					current_schedule = new Schedule(s); // x-as is teller hoe veel succesvolle mutates
				}
			}
			// if no improvement found for NO_IMPROVEMENT_ITERATIONS iterations, stop searching for a better solution
			while(s.Profit() > current_schedule.Profit() || no_improvement_iterations<NO_IMPROVEMENT_ITERATIONS) {
				//tries++;
				s = new Schedule(current_schedule);
				if(s.Mutate(rngen, cities)) {
					s.CheckValidity();
					num_mutations++;

					//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
					if(s.Profit()>current_schedule.Profit()) { 
						current_schedule = new Schedule(s); 
						no_improvement_iterations=0;
					} else {
						no_improvement_iterations++;
					}
				}
			}

			optimal_schedules.add(current_schedule);
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
	
	Schedule HillClimberRestartToFiles(Cities cities, Matrix matrix) { 

		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();
		
		try {

			Vector<FileWriter> filewriters = new Vector<FileWriter>();

			// create NUM_INITIAL_SCHEDULES initial random schedules
			for(int i = 0; i<NUM_INITIAL_SCHEDULES; i++) {
				Matrix m = new Matrix(matrix);
				schedules.add(RandomModel(cities, m));
			}

			// (run all initial schedules to find the local optima)
			for(int k =0;k<schedules.size();k++) {
				// Hill climbing algorithm 
				Schedule current_schedule = new Schedule(schedules.get(k));
				//out.printf("Schedule: %d\n", k+1);

				int num_mutations = 0;
				int num_accepted_mutations = 0;
				//int tries = 0;
				int no_improvement_iterations = 0;
				
				String filename = "C:\\workspace\\heuristic\\HillClimberRestart\\HCRestart_Schedule_" 
						+ (k+1) + "_initialschedules_" + NUM_INITIAL_SCHEDULES + "_randomroutes_" 
						+ NUM_RANDOM_ROUTES + ".txt";
				filewriters.add(new FileWriter(new File(filename)));
				FileWriter filewriter = filewriters.lastElement();

				Schedule s = new Schedule(current_schedule);
				if(s.Mutate(rn, cities)) {
					s.CheckValidity();
					num_mutations++;
					String newDataPoint = num_mutations + "\t" + s.Profit() + "\n";
					out.printf(newDataPoint);
					filewriter.write(newDataPoint);

					//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
					if(s.Profit()>current_schedule.Profit()) { 
						current_schedule = new Schedule(s); 
						
					}
				}

				// if no improvement found for NO_IMPROVEMENT_ITERATIONS iterations, stop searching for a better solution
				while(s.Profit() > current_schedule.Profit() || no_improvement_iterations<NO_IMPROVEMENT_ITERATIONS) {
					//tries++;
					s = new Schedule(current_schedule);
					if(s.Mutate(rn, cities)) {
						s.CheckValidity();
						num_mutations++;
						// TODO: print to text file and/or create graph
						
						String newDataPoint = num_mutations + "\t" + s.Profit() + "\n";
						out.printf(newDataPoint);
						filewriter.write(newDataPoint);
						
						//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
						if(s.Profit()>current_schedule.Profit()) { 
							current_schedule = new Schedule(s); 
							no_improvement_iterations=0;
						} else {
							no_improvement_iterations++;
						}
					}
				}

				optimal_schedules.add(current_schedule);
				//out.printf("tries: %d\n", tries);
				out.println();
				filewriter.close();
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

	void printSchedule(Schedule schedule) {
		for(int i=0; i<schedule.Routes().size(); i++) {
			out.printf(schedule.Routes().get(i).toString());
			out.println();
		}
		out.printf("Total profit\t€%.2f\n", schedule.Profit());
	}

	Schedule RandomModel(Cities cities, Matrix inputMatrix) {
		Schedule schedule = new Schedule(inputMatrix);

		for(int k=0; k<Schedule.NR_PLANES;k++) {
			Route optimalRoute =new Route();
			for(int j = 0; j<NUM_RANDOM_ROUTES; j++){ // create multiple routes to find best

				int randomStartCity = rn.nextInt(cities.size());
				Route route = new Route(cities.getCity(randomStartCity));
				route.setHomeTownID(0); // set hometown to amsterdam, TEMPORARILY
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
						//route = cur_route;
						//matrix = cur_matrix;
						route = new Route(cur_route);
						matrix = new Matrix(cur_matrix);

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
		return schedule;
	}

	Schedule RandomModel(Random rngen, int hometownID, Cities cities, Matrix inputMatrix) {
		Schedule schedule = new Schedule(inputMatrix);

		for(int k=0; k<Schedule.NR_PLANES;k++) {
			Route optimalRoute =new Route();
			for(int j = 0; j<NUM_RANDOM_ROUTES; j++){ // create multiple routes to find best

				int randomStartCity = rngen.nextInt(cities.size());
				Route route = new Route(cities.getCity(randomStartCity));
				route.setHomeTownID(hometownID);
				Matrix matrix = new Matrix(schedule.Matrix());

				for(int i = 0; i<200; i++){ // to create 1 route
					Route cur_route= new Route(route);
					Matrix cur_matrix = new Matrix(matrix);
					City city = cities.getCity(rngen.nextInt(cities.size()));

					if(cur_route.getCities().size()<3){
						if(randomStartCity!=hometownID){
							//Set to AMS
							city = cities.getCity(hometownID);
						}
					}

					if(cur_route.CityPresent(city)) {
						continue;
					}

					boolean valid_city_insert = cur_route.AddCity(city, cur_matrix, rngen);

					//If the city insertion in route is valid. Then update the route
					if(valid_city_insert) {
						//route = cur_route;
						//matrix = cur_matrix;
						route = new Route(cur_route);
						matrix = new Matrix(cur_matrix);

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

	Schedule bestHometownSchedule(Cities cities, Matrix matrix) {
		Schedule best_schedule = new Schedule(matrix);

		for(int i=0; i<cities.size(); i++) {
			out.println(i);
			Random rngen = new Random(SEED);
			Schedule current_schedule = HillClimberRestart2(rngen, cities, matrix);
			//Schedule current_schedule = HillClimberRestart2(rn, cities, matrix);
			if(current_schedule.Profit()>best_schedule.Profit()) {
				best_schedule = current_schedule;
				//out.println("The new best schedule: ");
				//printSchedule(current_schedule);
			}					
		}
		return best_schedule;
	}

	void start() {
		//Parse
		System.out.println("Parsing the input data");
		Parser parser = new Parser();
		Cities cities = parser.ParseCities();
		Matrix matrix = parser.ParseMatrices(cities.size());

		// TODO: use the schedule from the random model for the next algorithms?
		// so remove the 'create random schedules' inside the algorithm methods?
		
		//Random Model
		//System.out.println("Running the random model");
		//Schedule schedule = RandomModel(cities, matrix);
		//printSchedule(schedule);
		//visualizeSchedule(schedule);
		
		//Hill climber (without restart)
//		out.println("Running the hill climbing model");
//		Schedule hillclimber_schedule = HillClimber(cities, matrix);
//		printSchedule(hillclimber_schedule);
		//visualizeSchedule(hillclimber_schedule);

		//Hill climbing model with restart
//		out.println("Running the hill climbing model with restart");
//		Schedule hillclimber_restart_schedule = HillClimberRestartModel(cities, matrix);
//		printSchedule(hillclimber_restart_schedule);
		//visualizeSchedule(hillclimber_restart_schedule);

		//Hill climbing model (simulated annealing)
		out.println("Running the simulated annealing model");
		Schedule simulated_annealing_schedule = SimulatedAnnealing(cities, matrix);
		printSchedule(simulated_annealing_schedule);
		//visualizeSchedule(simulated_annealing_schedule);

		//Advanced questions: which hometown is best?
//		out.println("Searching for the best hometown...\n");
//		Schedule best_hometown_schedule = bestHometownSchedule(cities, matrix);
//		int best_hometown_id = best_hometown_schedule.Routes().get(0).hometownID();
//		printSchedule(best_hometown_schedule;
//		out.printf("The best hometown is %s\n", cities.getCity(best_hometown_id).toString());
//		out.printf("The profit corresponding to the schedule is €%f", best_hometown_schedule.Profit());
		// The best hometown is Amsterdam (20 initial schedules and 1000 NUM_RANDOM_ROUTES)

		// To find better values of temperature and cooling rate
		// this randomModel so we compare temp and rate with the same sequence of random numbers		
//		out.println("Writing the profit after mutations data during simulated annealing to textfiles...");
//		SimulatedAnnealing2(cities, matrix); // op een of andere manier zijn de profits heel laag..
		
//		out.println("Writing the profit after mutations data during hill climber with restart to textfiles...");
//		Schedule HillClimberRestartToFiles = HillClimberRestartToFiles(cities, matrix);
//		printSchedule(HillClimberRestartToFiles);
		
		//plots in R
	}

	public static void main(String[] argv) {
		new Main().start();

	}

}
