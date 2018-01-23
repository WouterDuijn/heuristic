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

	//tests
	PrintStream out;
	Random rn;

	Main() {
		//Initialize variables
		out = new PrintStream(System.out);   
		rn = new Random();
	}
	
	//TODO: change name 'number' 
	double acceptanceProbability(double current_profit, double new_profit, double number) {
		// if current (new) solution is better, accept it
		if(new_profit > current_profit) {
			return 1.0;
		}
		// if current (new) solution is worse, calculate acceptance probability
		return Math.exp(new_profit-current_profit/number);
	}
	
	double randomDouble() {
		Random r = new Random();
		return r.nextInt(1000)/1000.0;
	}
	
	Schedule HillClimberSimulatedAnnealing(Cities cities, Matrix matrix) {
		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();
		
		// create 5 initial random schedules
		for(int i = 0; i<5; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(cities, m));
//			out.printf("Schedule: %d\n", i+1);
//			out.printf("Profit random schedule: €%f\n", schedules.get(i).Profit());
		}

		for(int k =0;k<schedules.size()-3;k++) {
			// Hill climbing algorithm (run all 5 schedules to find 5 local optima)
			Schedule current_schedule = new Schedule(schedules.get(k));
//			out.printf("Schedule: %d\n", k+1);
//			out.printf("Profit current schedule bef. mut.: €%f\n", current_schedule.Profit());
			Schedule best_schedule = new Schedule(current_schedule); 
//			out.printf("Schedule: %d\n", k+1);
//			out.printf("Profit best schedule bef. mut.: €%f\n", best_schedule.Profit());
			
			double number = 10000;
			double profitRate = 0.005;
			
			//Schedule current_schedule = new Schedule(schedules.get(k));
			//out.printf("Schedule: %d\n", k+1);
			//out.printf("Profit without mutations: €%f\n", current_schedule.Profit());
			
			int num_mutations = 0;
			int tries = 0;
			//TODO: Determine stop criteria. Now hardcoded to 100.000 mutations
			
			while(number>1) {
				tries++;
				Schedule newSchedule = new Schedule(current_schedule);
//				out.printf("Schedule: %d\n", k+1);
//				out.printf("Profit newSchedule bef. mut.: €%f\n", newSchedule.Profit());
				if(newSchedule.Mutate(rn, cities)) {
					newSchedule.CheckValidity();
					num_mutations++;
					// TODO: print to text file and create graph
					//out.printf("Mutation: %d, profit: €%f\n", num_mutations, newSchedule.Profit());
					
					double randomNumber = randomDouble();
					if(acceptanceProbability(newSchedule.Profit(), current_schedule.Profit(), number) > randomNumber) {
						current_schedule = new Schedule(newSchedule);
//						out.printf("Schedule: %d\n", k+1);
//						out.printf("Profit current schedule aft. mut.: €%f\n", current_schedule.Profit());
					}
					
					if(current_schedule.Profit() > best_schedule.Profit()) {
						best_schedule = new Schedule(current_schedule);
//						out.printf("Schedule: %d\n", k+1);
//						out.printf("Profit best schedule aft. mut.: €%f\n", best_schedule.Profit());
					}
					number *= 1 - profitRate;	
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
	
	// moeten hier deep copies gemaakt worden van de Schedules of kan het zo?
	Schedule HillClimberRestartModel(Cities cities, Matrix matrix) { // boolean restart, of geen restart
		//boolean cont = true;
		Vector<Schedule> schedules = new Vector<Schedule>();
		Vector<Schedule> optimal_schedules = new Vector<Schedule>();
		double profit_improvement = 0; // if diff between optimal and current profit <= 10, stop?
		// or if X number of iterations reached, stop?
		// profit_improvement may be larger than 0. 
		
		// create 5 initial random schedules
		for(int i = 0; i<5; i++) {
			Matrix m = new Matrix(matrix);
			schedules.add(RandomModel(cities, m));
		}

		for(int k =0;k<schedules.size();k++) {
			// Hill climbing algorithm (run all 5 schedules to find 5 local optima)
			Schedule current_schedule = new Schedule(schedules.get(k));
			out.printf("Schedule: %d\n", k+1);
			
			int num_mutations = 0;
			int tries = 0;
			int no_improvement_iterations = 0;
			//TODO: Determine stop criteria. Now hardcoded to 100.000 mutations
			
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
			while(s.Profit()-current_schedule.Profit()>profit_improvement || no_improvement_iterations<10000) {
				tries++;
				s = new Schedule(current_schedule);
				if(s.Mutate(rn, cities)) {
					s.CheckValidity();
					num_mutations++;
					// TODO: print to text file and create graph
					//out.printf("Mutation: %d, profit: €%f\n", num_mutations, s.Profit());
					if(s.Profit()>current_schedule.Profit()) { // als mutate succesvol, print statement voor grafiek
						current_schedule = new Schedule(s); // x-as is teller hoe veel succesvolle mutates
						no_improvement_iterations=0;
					}
					if(s.Profit() - current_schedule.Profit() < profit_improvement) {
						no_improvement_iterations++;
					}
				}
			}
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

	// added printing method so RandomModel wouldn't print when used in HillClimbingModel
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
			for(int j = 0; j<100; j++){ // create 10000 routes to find best

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
		//Route route = RandomModel(cities, matrix);
		//Schedule schedule = RandomModel(cities, matrix);
		//printSchedule(schedule);
		//visualizeSchedule(schedule);

		//Hill climbing model with restart
		out.println("Running the hill climbing model with restart");
		Schedule optimal_schedule = HillClimberRestartModel(cities, matrix);
		printSchedule(optimal_schedule);
		//visualizeSchedule(optimal_schedule);
		
		//Hill climbing model (simulated annealing)
		/*out.println("Running the hill climbing model (simulated annealing)");
		Schedule optimal = HillClimberSimulatedAnnealing(cities, matrix);
		printSchedule(optimal);*/
		//visualizeSchedule(optimal);
	}

	public static void main(String[] argv) {

		new Main().start();

	}

}
