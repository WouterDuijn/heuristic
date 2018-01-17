package dom;

import java.util.Hashtable;
import java.util.Random;

public class Bookings {
	
	private Hashtable<Integer, Booking> bookings;
	
	public Bookings(){
		bookings = new Hashtable<Integer, Booking>();
	}
	
	public Bookings(Bookings bookings) {
		this.bookings =  new Hashtable<Integer, Booking>(bookings.bookings);
	}
	
	public void AddBooking(Booking booking) {
		if(bookings.containsKey(booking.Hash())) {
			Booking current = bookings.get(booking.Hash());
			current.SetPax(current.Pax()+booking.Pax());
		}else {
			bookings.put(booking.Hash(), booking);
		}
	}
	
	public Booking RandomBooking(Random rn) {
		int random_booking_key = (int) bookings.keySet().toArray()[rn.nextInt(bookings.size()-1)];
		return bookings.get(random_booking_key);
	}
	
	public Booking GetBooking(int city1, int city2) {
		return bookings.get(new Booking(city1, city2,0).Hash());
	}
	
	public void RemoveBooking(int city1, int city2) {
		bookings.remove(new Booking(city1, city2, 0).Hash());
	}
	
	public int size(){
		return bookings.size();
	}
	
	public String toString() {
		return bookings.toString();
		
	}

}
