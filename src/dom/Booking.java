package dom;

public class Booking {
	
	private int cityid_from;
	private int cityid_to;
	private int nr_passengers;
	
	
	public Booking(int cityid_from, int cityid_to, int nr_passengers) {
		this.cityid_from = cityid_from;
		this.cityid_to = cityid_to;
		this.nr_passengers = nr_passengers;
	}
	
	public Booking(Booking booking) {
		this.cityid_from = booking.cityid_from;
		this.cityid_to = booking.cityid_to;
		this.nr_passengers = booking.nr_passengers;
	}
}
