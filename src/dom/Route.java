package dom;

import java.io.PrintStream;
import java.util.Random;
import java.util.Vector;

public class Route {

	PrintStream out;

	public static final int DAY_LENGTH=20;
	private static final double MAX_FUEL_DISTANCE=3199;
	private static final int SPEED=800;
	private static final int MAX_PASSENGERS = 199;

	private Vector<City> cities;
	private Vector<Double> tank;
	public double current_time;
	
	public double profit;
	private Vector<Integer> passengers; 
	Vector<Double> distances;
	private Bookings bookings;
	private int hometownID;
	
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
		this.hometownID = 0; // temporarily added
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
		this.hometownID = route.hometownID; // temporarily added
	}

	public Route(City city, int home_base_id){
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
		this.hometownID = home_base_id;
		// if this constructor is called, setHomeTownID needs to be called to set hometown! TEMPORARILY
	}

	public int hometownID() {
		return this.hometownID;
	}
	
	public int size(){
		return cities.size();
	}
	
	public boolean CityPresent(City city) {
		for(City c: cities) {
			if(c.ID()==city.ID()) {
				return true;
			}
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
	public boolean AddCity(City city, Matrix matrix, Random rn) {
		
		//Is the index to insert the city in the route.
		int index =rn.nextInt(this.size()-1)+1;
		
		City before = new City(cities.get(index-1));
		City beyond = new City(cities.get(index));
	
		int available_passengers1= matrix.Passengers(before.ID(), city.ID());
		int available_passengers2= matrix.Passengers(city.ID(), beyond.ID());
		int randomPassenger1 = rn.nextInt(Math.min(available_passengers1, (Route.getMaxPassengers()-
				passengers.get(index-1)))+1);
		int randomPassenger2 = rn.nextInt(Math.min(available_passengers2, (Route.getMaxPassengers()-
				passengers.get(index-1)))+1);

		// if current_time above or equal to day length - 1 hour, no new flights can be added
		// since only boarding and unboarding will already take an extra hour on top of flight
		// particularly useful for the last to be added flight, instead of calculating everything first
		if(current_time >= DAY_LENGTH-1) {
			return false; 
		}
		
		double distance1 = matrix.Distance(before.ID(), city.ID());
		double distance2 = matrix.Distance(city.ID(), beyond.ID());
		
		if(distance1 > MAX_FUEL_DISTANCE || distance2 > MAX_FUEL_DISTANCE) {
			return false; 
		}

		cities.add(index, city); 

		//Newly calculate the distance vector
		double fly_time = this.SetNewDistances(matrix);
		//Determine new tank moments
		int tank_moments = this.SetTankValues();
		int board_time = cities.size()-2;

		current_time = fly_time + tank_moments + board_time;
		if(current_time > DAY_LENGTH) {
			return false;
		}
		
		AddBooking(before.ID(), city.ID(), randomPassenger1, matrix);
		AddBooking(city.ID(), beyond.ID(), randomPassenger2, matrix);
		return true; 

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
				"Time\t" + this.current_time + "\n" + "Profit\t" + this.profit + "\n";
	}

	private boolean AddBooking(int cityid_from, int cityid_to, int nr_passengers, Matrix matrix) {
		if(nr_passengers>0) {
			bookings.AddBooking(new Booking(cityid_from, cityid_to, nr_passengers));
			matrix.UpdatePassengers(cityid_from, cityid_to, -nr_passengers);
			profit+=matrix.Distance(cityid_from, cityid_to)*nr_passengers;
		}
		return PassengerSetByBookings();
		
	}
	
	private void RemoveBooking(int city1, int city2, Matrix matrix) {
		Booking b = bookings.RemoveBooking(city1, city2);
		if(b!=null) {
			matrix.UpdatePassengers(b.From(), b.To(), b.Pax());
			profit-=matrix.Distance(city1, city2)*b.Pax();
		}
		
		PassengerSetByBookings();
	}
	
	private void RemoveBookingsCity(int city, Matrix matrix) {
		for(Booking booking: bookings.RemoveBookingsCity(city)) {
	    	matrix.UpdatePassengers(booking.From(), booking.To(), booking.Pax());
	    	profit-=matrix.Distance(booking.From(), booking.To())*booking.Pax();
		}
		PassengerSetByBookings();
	}
	
	private boolean PassengerSetByBookings() {
		Vector<Integer> passengers= new Vector<Integer>();
		for(int i=0;i<cities.size()-1;i++) {
			passengers.add(0);
		}
		
		for(Booking b : bookings.BookingsList()) {
			int i1=0;
			int i2=0;
			
			for(int i=0;i<cities.size()-1;i++) {
				if(b.From()==cities.get(i).ID()) {
					i1=i;
					break;
				}
			}
			
			for(int i=i1+1;i<cities.size();i++) {
				if(b.To()==cities.get(i).ID()) {
					i2=i;
					break;
				}
			}
			
			//Booking is from city at index i1 to i2
			for(int i=i1;i<i2;i++) {
				if(passengers.get(i) + b.Pax()>MAX_PASSENGERS) {
					return false;
				}
				
				passengers.set(i, passengers.get(i) + b.Pax());
				
			}
		}
		this.passengers=new Vector<Integer>(passengers);
		return true;
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
		int pas=0;
		int book=bookings.TotalBookedPax();
		for(int i: passengers) {
			pas+=i;
		}
		if(pas<book) {
			throw new RuntimeException("Route invalid");
		}
	}
	
	private double SetNewDistances(Matrix matrix) {
		double fly_time=0;
		Vector<Double> temp = new Vector<Double>();
		
		for(int i=0; i< cities.size()-1;i++) {
			temp.add(matrix.Distance(cities.get(i).ID(), cities.get(i+1).ID()));
			fly_time += temp.lastElement()/SPEED;
		}
		
		this.distances = temp;
		return fly_time;
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
	public boolean Mutate(Random rn, Matrix matrix, Cities cities_list) {
		int choice = rn.nextInt(4);
		if(choice==0) {
			return SwapDetour(rn, matrix);
		}else if(choice==1) {
			return CitySwap(rn, matrix);
		}else if(choice==2) {
			return PassengerAdjustment(rn, matrix);
		}else if(choice==3) {
			return NewCity(rn, matrix, cities_list);
		}
		return false;
	}	
	
	private boolean NewCity(Random rn, Matrix matrix, Cities cities_list) {
		int index = rn.nextInt(cities.size()-2)+1;
		while(cities.get(index).ID()==hometownID) { // ==0
			index = rn.nextInt(cities.size()-2)+1;
		}
		
		City city2replace = cities.get(index);
		
		City before = cities.get(index-1);
		City beyond = cities.get(index+1);
		
		City city2insert = cities_list.getCity(rn.nextInt(cities_list.size()));
		
		int occurence=0;
		for(int i=0;i<cities.size()-1;i++) {
			if(cities.get(i).ID() == city2insert.ID()) {
				occurence++;
			}
		}
		
		//Check for non Amsterdam as well
		while(before.ID() == city2insert.ID() || beyond.ID() == city2insert.ID() ||
				city2insert.ID() == hometownID || occurence>0) { // city2insert.ID() == 0
			//Search for new city without equal neighbours
			city2insert = cities_list.getCity(rn.nextInt(cities_list.size()));
			
			occurence = 0;
			for(int i=0;i<cities.size()-1;i++) {
				if(cities.get(i).ID() == city2insert.ID()) {
					occurence++;
				}
			}
			
		}

		//Remove the bookings
		RemoveBookingsCity(city2replace.ID(), matrix);
		
		//Insert new city in cities vector
		cities.set(index, city2insert);
		//Newly calculate the distance vector
		double fly_time = this.SetNewDistances(matrix);
		//Determine new tank moments
		int tank_moments = this.SetTankValues();
		int board_time = cities.size()-2;
		
		current_time = fly_time + tank_moments + board_time;
		
		if(current_time > DAY_LENGTH) {
			return false;
		}
		
		//Add Passenger & Bookings & update profit
		int to_random_city_passengers = Math.min(MAX_PASSENGERS - passengers.get(index-1), 
				matrix.Passengers(before.ID(), city2insert.ID()));
		int from_random_city_passengers = Math.min(MAX_PASSENGERS - passengers.get(index), 
				matrix.Passengers(city2insert.ID(), beyond.ID()));
		AddBooking(before.ID(), city2insert.ID(), to_random_city_passengers, matrix);
		AddBooking(city2insert.ID(), beyond.ID(), from_random_city_passengers, matrix);
		return true;
	}
	
	
	private int SetTankValues() {
		int tank_moments=0;
		Vector<Double> temp = new Vector<Double>();
		
		for(int i=1;i<cities.size();i++) {
			Double current_tank = MAX_FUEL_DISTANCE;
			if(i>1) {
				current_tank= temp.lastElement()-distances.get(i-2);
			}
			
			Double dist_current_edge = distances.get(i-1);
			Double tank_at_arrival = current_tank - dist_current_edge;
			
			if(tank_at_arrival<0) {
				//Needs refuel at current city
				temp.add(MAX_FUEL_DISTANCE);
				tank_moments++;
			}else {
				temp.add(current_tank);
			}
		}
		temp.add(temp.lastElement()- distances.lastElement());

		this.tank = temp;
		return tank_moments;
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
			if(index+1==cities.size()) { // city at index is final destination
				//Every possible departure city has no passengers available from departure city (always including detours)
				return false;
			}
			
			arrivalcity = cities.get(index+1);
			
			//Free capacity is minimum of all the legs capacity
			free_capacity = Math.min(free_capacity, MAX_PASSENGERS - passengers.get(index));
		}
		
		if(free_capacity==0) {
			//No capacity over all legs
			return false;
		}
		
		//Determine the number of passengers to book
		int extra_booked_passengers = Math.min(free_capacity, matrix.Passengers(departcity.ID(), arrivalcity.ID()));

		//Add booking and update passenger matrix and passenger vector
		AddBooking(departcity.ID(), arrivalcity.ID(), extra_booked_passengers, matrix);
		
		//Newly calculate the distance vector
		double fly_time = this.SetNewDistances(matrix);
		//Determine new tank moments
		int tank_moments = this.SetTankValues();
		int board_time = cities.size()-2;

		current_time = fly_time + tank_moments + board_time;
		if(current_time > DAY_LENGTH) {
			return false;
		}
		
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
		int city_index = rn.nextInt(cities.size()-3)+1;
		City c1 = cities.get(city_index);
		City c2 = cities.get(city_index+1);

		//Remove current booking and passengers
		RemoveBooking(c1.ID(), c2.ID(), matrix);
		
		//Swap cities in list
		cities.set(city_index, c2);
		cities.set(city_index+1, c1);

		if(!PassengerSetByBookings()) {
			//Not possible because city swapping leads to overcapacity on leg
			return false;
		}
		
		//Set new passengers on flight
		int free_capacity = MAX_PASSENGERS -passengers.get(city_index);
		int available_passengers = matrix.Passengers(c2.ID(), c1.ID());
		int new_direct_passengers = rn.nextInt(Math.min(available_passengers, free_capacity)+1);
		//Set/Updates the booking for the new passengers
		AddBooking(c2.ID(),c1.ID(), new_direct_passengers, matrix);
		
		//Newly calculate the distance vector
		double fly_time = this.SetNewDistances(matrix);
		//Determine new tank moments
		int tank_moments = this.SetTankValues();
		int board_time = cities.size()-2;

		current_time = fly_time + tank_moments + board_time;
		if(current_time > DAY_LENGTH) {
			return false;
		}

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
		if(bookings.size()==1) {
			return false;
		}

		Booking random_booking = bookings.RandomBooking(rn);
		
		int cityid_from = random_booking.From();
		int cityid_to = random_booking.To();
		
		int city_index1=0;
		int city_index2=0;
		
		boolean found=false;
		
		for(int i=0;i< cities.size()-1 && (!found);i++) {	
			if(cityid_from==cities.get(i).ID()) {
				//Booking starting point found
				for(int j=i+1;j<cities.size()&& (!found);j++) {
					if(cityid_to == cities.get(j).ID()) {
						//Found ending point
						if(i+1==j) {
							//Booking has no detour
							return false;
						}else {
							city_index1=i;
							city_index2=j;
							found=true;
							break; // why break if for-loop already stops when found is true?
						}
					}
				}
			}
		}
		
		RemoveBooking(cityid_from, cityid_to, matrix);
		
		//Between these two cities make new bookings with no detours
		for(int i=city_index1;i<city_index2;i++) {
			
			City c1=cities.get(i);
			City c2=cities.get(i+1);
			
			//Check for capacity and available passengers on leg
			int free_capacity = MAX_PASSENGERS -passengers.get(i);
			
			int available_passengers = matrix.Passengers(c1.ID(), c2.ID());

			//Update the new passengers
			int new_direct_passengers = rn.nextInt(Math.min(available_passengers, free_capacity)+1);
			

			//Set/Updates the booking for the new passengers
			Booking b = bookings.GetBooking(c1.ID(),c2.ID());
			
			if(b!=null) {
				if(b.Pax() + new_direct_passengers>199) {
					System.out.println(this.toString());
					System.out.println(c1.toString());
					System.out.println(c2.toString());
					System.out.println(random_booking.toString());
					System.out.println(passengers.get(i));
					System.out.println(new_direct_passengers);
					throw new RuntimeException("Free");
				}
			}
			
			AddBooking(c1.ID(),c2.ID(), new_direct_passengers, matrix);
		}
		
		//Remove detour booking and passengers
		if(null!=bookings.GetBooking(cityid_from, cityid_to)) {
			
			System.out.println(cityid_from);
			System.out.println(cityid_to);
			throw new RuntimeException("weierd");
		};

		return true;
	}

	public Bookings Bookings() {
		return bookings;
	}
	
	public int TotalPassengersBooked() {
		return bookings.TotalBookedPax();
	}
}
