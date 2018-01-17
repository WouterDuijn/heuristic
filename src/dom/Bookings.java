package dom;

import java.util.Vector;

public class Bookings {
	
	private Vector<Booking> bookings;
	
	public Bookings(){
		bookings = new Vector<Booking>();
	}
	
	public Bookings(Bookings bookings) {
		this.bookings =  new Vector<Booking>(bookings.bookings);
	}
	
	public void AddBooking(Booking booking) {
		bookings.add(booking);
	}

}
