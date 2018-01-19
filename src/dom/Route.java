package dom;

import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class Route {

	PrintStream out;

	public static final int DAY_LENGTH=20;
	private static final double MAX_FUEL_DISTANCE=3199;
	private static final int SPEED=800;
	private static final int MAX_PASSENGERS = 199;

	
	private Vector<City> cities;
	private Vector<Double> tank; //number of kilometers the plane can still travel after arrival at a city
	public double current_time;
	public double profit;
	private Vector<Integer> passengers; 
	Vector<Double> distances;
	private Bookings bookings;
	
	/**
	 * Default Constructor. Set current time and profit to zero
	 */
	public Route(){
		setCities(new Vector<City>());
		setPassengers(new Vector<Integer>());
		distances = new Vector<Double>();
		bookings = new Bookings();
		tank = new Vector<Double>();
		current_time=0;
		profit=0;
	}
	
	
	/**
	 * Copy constructor of route
	 * @param route = route to be copied from
	 */
	public Route(Route route){
		this.cities=new Vector<City>(route.cities);
		this.tank=new Vector<Double>(route.tank);
		this.passengers=new Vector<Integer>(route.passengers);
		this.distances=new Vector<Double>(route.distances);
		this.bookings = new Bookings(route.bookings);
		this.current_time=route.current_time;
		this.profit = route.profit;
	}
	
	
	public Route(City city){
		setCities(new Vector<City>());
		getCities().add(city);
		getCities().add(city);
		setPassengers(new Vector<Integer>());
		getPassengers().add(0);
		distances = new Vector<Double>();
		distances.add(0.);
		bookings = new Bookings();
		tank = new Vector<Double>();
		tank.add(MAX_FUEL_DISTANCE);
		tank.add(0.);
		current_time=0;
		profit=0;
		out = new PrintStream(System.out);
	}

	public int size(){
		return cities.size();
	}

	public boolean needsRefuel(int index, double distance1, double distance2) {
		if(tank.get(index-1)-distance1<=0 || tank.get(index-1)-distance1-distance2<=0){
			return true;
		}
		return false;
	}

	Vector<City> getCitiesCopy() {
		Vector<City> copy = new Vector<City>(getCities().size());
		for(int i=0; i<getCities().size(); i++) {
			copy.add(i,getCities().get(i).copy());
		}
		return copy;
	}
	
	
	/**
	 * 
	 * @param city to insert
	 * @param index is position to insert the city
	 * @param distance1 is the distance flown from city before to city to insert
	 * @param distance2  is the distance flown from city to insert to city beyond
	 * @return whether the city insert is valid and doesnt exceed the time limit
	 */
	public boolean AddCity(City city, int index, double distance1, double distance2) {

		// if current_time above or equal to day length - 1 hour, no new flights can be added
		// since only boarding and unboarding will already take an extra hour on top of flight
		// particularly useful for the last to be added flight, instead of calculating everything first
		if(current_time >= DAY_LENGTH-1) {
			return false; 
		}
		
		if(distance1 > MAX_FUEL_DISTANCE || distance2 > MAX_FUEL_DISTANCE) {
			return false; 
		}

		
		cities.add(index, city); 
		
		//City is same as neighbouring city
		if(cities.get(index-1).ID() == city.ID() || cities.get(index+1).ID() == city.ID()) {
			return false;
		} 
		
		// add un-, boarding time
		current_time+=1;
		
		// subtract flying time of current flight (that will be replaced by the newly created flights)
		current_time-= (distances.get(index-1)/SPEED);

		// add flying times of the new flights
		current_time += (distance1/SPEED + distance2/SPEED);
		
		if(current_time > DAY_LENGTH) {
			return false;
		}

		//Adjust distances
		distances.setElementAt(distance2, index-1);
		distances.add(index-1, distance1);
		
		//Declare empty refuel vectors
		Vector<Double>tank = new Vector<Double>();

		for(int i=1;i<cities.size();i++) {
			
			Double current_tank = MAX_FUEL_DISTANCE;
			if(i>1) {
				current_tank= tank.lastElement()-distances.get(i-2);
			}
			
			Double dist_current_edge = distances.get(i-1);
			Double tank_at_arrival = current_tank - dist_current_edge;
			
			if(tank_at_arrival<0) {
				//Needs refuel at current city
				tank.add(MAX_FUEL_DISTANCE);
				current_time+=1;
			}else {
				tank.add(current_tank);
			}
		}
		tank.add(tank.lastElement()- distances.lastElement());
		
		this.tank=tank;

		// if total time does not exceed the day length, it is a valid city insert
		if(current_time > DAY_LENGTH) {
			return false; 
		}
		
		return true; 

	}

	public void AddPassengers(int index, int passengers1, int passengers2) {
		int passengers_on_edge = passengers.get(index-1);
		passengers.set(index-1, passengers_on_edge+passengers2);
		passengers.add(index-1, passengers_on_edge+passengers1);
	}


	public static int getMaxPassengers() {
		return MAX_PASSENGERS;
	}


	public Vector<Integer> getPassengers() {
		return passengers;
	}


	public void setPassengers(Vector<Integer> passengers) {
		this.passengers = passengers;
	}


	public Vector<City> getCities() {
		return cities;
	}


	public void setCities(Vector<City> cities) {
		this.cities = cities;
	}
	
	public String toString() { 
		return ("Cities\t" + this.cities.toString()+ "\n" + 
				"Passengers\t" + this.passengers.toString() +"\n" +
				"Tank\t" + this.tank.toString() +"\n" +
				"Distances\t" + this.distances.toString() + "\n" +
				"Bookings\t" + this.bookings.toString()) + "\n" +
				"Time\t" + this.current_time + "\n" + "Profit\t" + this.profit;
	}


	public void IncrementProfit(double incr_profit) {
		profit+=incr_profit;	
	}
	
	public void AddBooking(int cityid_from, int cityid_to, int nr_passengers) {
		bookings.AddBooking(new Booking(cityid_from, cityid_to, nr_passengers));
	}


	public Vector<Double> getDistances() {
		return this.distances;
	}


	public void CheckValidity() {
		if(this.passengers.size()!=this.distances.size()) {
			throw new RuntimeException("Route invalid");
		}
		if(this.distances.size()!=this.passengers.size()) {
			throw new RuntimeException("Route invalid");
		}
		
		if(this.cities.size()!=this.tank.size()) {
			throw new RuntimeException("Route invalid");
		}
	}
	
	public void AdjustPassengerMatrixFromBookings(Matrix matrix) {
		bookings.AdjustPassengerMatrix(matrix);
	}
	
	
	
	/**
	 * 
	 * @param rn is random variable
	 * @param matrix is the matrix where the passenger values get adjusted.
	 * @return whether the mutation is succesfull. Note: the matrix is also adjusted and should be beared in mind
	 */
	public boolean Mutate(Random rn, Matrix matrix) {
		int choice = rn.nextInt(4);
		
		if(choice==0) {
			return SwapDetour(rn, matrix);
		}else if(choice==1) {
			return CitySwap(rn, matrix);
		}else if(choice==2) {
			return PassengerAdjustment(rn, matrix);
		}else if(choice==3) {
			//TODO:City add/remove
		}
		
		return false;
	}
	
	
	/**
	 * Adds passengers to random departing and arrival city
	 * @param rn is random object
	 * @param matrix is the matrix that gets updated
	 * @return whether new passengers have been updated
	 */
	private boolean PassengerAdjustment(Random rn, Matrix matrix) {
		
		//Pick random flight leg
		int index = rn.nextInt(cities.size()-1);
		City departcity = cities.get(index);
		City arrivalcity = cities.get(index+1);
		
		//Determine capacity left and available passengers
		int free_capacity = MAX_PASSENGERS - passengers.get(index);
		
		
		//No room or passengers available thus return false
		if(free_capacity==0) {
			return false;
		}
		
		
		//Search for arrivalcity with available passengers
		while(matrix.Passengers(departcity.ID(),arrivalcity.ID())==0) {
			index++;
			if(index+1==cities.size()) {
				//Every possible departure city has no passengers available from departure city (always including detours)
				return false;
			}
			
			arrivalcity = cities.get(index+1);
			
			//Free capacity is minimum of all the legs capacity
			free_capacity = Math.min(free_capacity, passengers.get(index));
		}
		
		if(free_capacity==0) {
			//No capacity over all legs
			return false;
		}
		
		//Determine the number of passengers to book
		int extra_booked_passengers = Math.min(free_capacity, matrix.Passengers(departcity.ID(), arrivalcity.ID()));
		
		
		//Add booking and update passenger matrix
		bookings.AddBooking(new Booking(departcity.ID(), arrivalcity.ID(), extra_booked_passengers));
		matrix.UpdatePassengers(departcity.ID(), arrivalcity.ID(), -extra_booked_passengers);
		
		return true;
	}
	
	
	/**
	 * 
	 * @param rn
	 * @param matrix is the matrix that gets adjusted
	 * @returns whether the cityswap is valid
	 */
	private boolean CitySwap(Random rn, Matrix matrix) {
		
		//Pick random city pair
		int city_index = rn.nextInt(cities.size()-2)+1;
		City before =cities.get(city_index-1);
		City c1 = cities.get(city_index);
		City c2 = cities.get(city_index+1);
		City beyond = cities.get(city_index+2);
		
		//So City c1 and c2 are being swapped
		
		//Remove current booking and passengers
		Booking booking = bookings.GetBooking(c1.ID(), c2.ID());
		bookings.RemoveBooking(c1.ID(), c2.ID());
		profit-= matrix.Distance(c1.ID(),c2.ID())*booking.Pax();
		matrix.UpdatePassengers(c1.ID(), c2.ID(), booking.Pax());
		
		//Set new passengers on flight
		int free_capacity = MAX_PASSENGERS - passengers.get(city_index) - booking.Pax();
		int available_passengers = matrix.Passengers(c1.ID(), c2.ID());
		int new_direct_passengers = rn.nextInt(Math.min(available_passengers, free_capacity))+1;
		matrix.UpdatePassengers(c1.ID(), c2.ID(), -new_direct_passengers);
		passengers.set(city_index, passengers.get(city_index) - booking.Pax() + new_direct_passengers);
		
		//Set/Updates the booking for the new passengers
		bookings.AddBooking(new Booking(c1.ID(),c2.ID(), new_direct_passengers));
		profit+= matrix.Distance(c2.ID(),c1.ID())*new_direct_passengers;
		
		//Swap cities in list
		cities.set(city_index, c2);
		cities.set(city_index+1, c1);
		
		//Set new distances
		distances.set(city_index, matrix.Distance(before.ID(), c1.ID()));
		distances.set(city_index+1, matrix.Distance(c1.ID(), c2.ID()));
		distances.set(city_index+2, matrix.Distance(c2.ID(), beyond.ID()));
		
		current_time =0;
		
		for(int i=0; i< distances.size();i++) {
			//Add fly time
			current_time += (distances.get(i)/SPEED);
			
			//Add boarding time
			current_time +=1;

		}
		
		//Last stop has no boarding time
		current_time -=1;

		//Update tank vector
		tank = new Vector<Double>();
		for(int i=1;i<cities.size();i++) {
			
			Double current_tank = MAX_FUEL_DISTANCE;
			if(i>1) {
				current_tank= tank.lastElement()-distances.get(i-2);
			}
			
			Double dist_current_edge = distances.get(i-1);
			Double tank_at_arrival = current_tank - dist_current_edge;
			
			if(tank_at_arrival<0) {
				//Needs refuel at current city
				tank.add(MAX_FUEL_DISTANCE);
				current_time+=1;
			}else {
				tank.add(current_tank);
			}
		}
		tank.add(tank.lastElement()- distances.lastElement());
		
		if(current_time > DAY_LENGTH) {
			return false;
		}
		
		//TODO: find a way to return the adjusted matrix
		
		return true;
	}
	
	/**
	 * 
	 * @param rn
	 * @param matrix is the matrix that gets adjusted
	 * @return whether the swap is valid
	 */
	private boolean SwapDetour(Random rn, Matrix matrix) {
		//Pick random booking
		Booking random_booking = bookings.RandomBooking(rn);
		
		int cityid_from = random_booking.From();
		int cityid_to = random_booking.To();
		
		int city_index1=0;
		int city_index2=0;
		
		for(int i=0;i< cities.size()-1;i++) {	
			if(cityid_from==cities.get(i).ID()) {
				//Booking starting point found
				
				for(int j=i+1;j<cities.size()-1;i++) {

					if(cityid_to == cities.get(j).ID()) {
						//Found ending point
						
						if(i+1==j) {
							//Booking has no detour
							return false;
						}else {
							city_index1=i;
							city_index2=j;
						}
					}
				}
			}
		}
		
		//Remove detour booking and passengers
		bookings.RemoveBooking(cityid_from, cityid_to);
		profit-= matrix.Distance(cityid_from, cityid_to)*random_booking.Pax();
		matrix.UpdatePassengers(cityid_from, cityid_to, random_booking.Pax());
		
		//Between these two cities make new bookings with no detours
		for(int i=city_index1;i<city_index2;i++) {
			
			City c1=cities.get(i);
			City c2=cities.get(i+1);
			
			//Check for capacity and available passengers on leg
			int free_capacity = MAX_PASSENGERS - passengers.get(i) - random_booking.Pax();
			int available_passengers = matrix.Passengers(c1.ID(), c2.ID());
			
			//Update the new passengers
			int new_direct_passengers = rn.nextInt(Math.min(available_passengers, free_capacity))+1;
			passengers.set(i, passengers.get(i) - random_booking.Pax() + new_direct_passengers);
			
			//Set/Updates the booking for the new passengers
			bookings.AddBooking(new Booking(c1.ID(),c2.ID(), new_direct_passengers));
			profit+= matrix.Distance(c1.ID(), c2.ID())*new_direct_passengers;
			matrix.UpdatePassengers(c1.ID(), c2.ID(), -new_direct_passengers);
		}
		
		//TODO: find a way to return matrix
		return true;
	}
}
