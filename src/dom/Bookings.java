package dom;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class Bookings {
	
	private Hashtable<Integer, Booking> bookings;
	
	public Bookings(){
		bookings = new Hashtable<Integer, Booking>();
	}
	
	public Bookings(Bookings bookings) {
		this.bookings = new Hashtable<Integer, Booking>(bookings.clone());
	}
	
	public void AddBooking(Booking booking) {
		if(bookings.containsKey(booking.Hash())) {
			Booking current = bookings.get(booking.Hash());
			current.SetPax(current.Pax()+booking.Pax());
		}else {
			bookings.put(booking.Hash(), booking);
		}
		
		if(bookings.get(booking.Hash()).Pax()>199) {
			System.out.println(bookings.toString());
			throw new RuntimeException("Bookings");
		}
	}
	
	public Booking RandomBooking(Random rn) {
		int random_booking_key = (int) bookings.keySet().toArray()[rn.nextInt(bookings.size()-1)];
		return bookings.get(random_booking_key);
	}
	
	public Booking GetBooking(int city1, int city2) {
		return bookings.get(new Booking(city1, city2,0).Hash());
	}
	
	public Booking RemoveBooking(int city1, int city2) {
		return bookings.remove(new Booking(city1, city2, 0).Hash());
	}
	
	public Vector<Booking> RemoveBookingsCity(int city){
		Vector<Booking> result = new Vector<Booking>();
		
		Enumeration<Integer> enumKey = bookings.keys();
		while(enumKey.hasMoreElements()) {
		    Integer key = enumKey.nextElement();
		    Booking booking = bookings.get(key);
		    if(booking.From()==city || booking.To()==city) {
		    	//Remove booking as departing or arriving city
		    	result.add(booking);
		    	bookings.remove(booking.Hash());

		    } 
		}
		return result;
	}
	
	public Vector<Booking> BookingsList(){
		Vector<Booking> result = new Vector<Booking>();
		Enumeration<Integer> enumKey = bookings.keys();
		while(enumKey.hasMoreElements()) {
		    result.add(bookings.get(enumKey.nextElement()));
		}
		return result;
	}
	
	public int size(){
		return bookings.size();
	}
	
	public String toString() {
		return bookings.toString();	
	}

	public void AdjustPassengerMatrix(Matrix matrix) {
		Enumeration<Integer> enumKey = bookings.keys();
		while(enumKey.hasMoreElements()) {
		    Integer key = enumKey.nextElement();
		    Booking booking = bookings.get(key);
		    
		    matrix.UpdatePassengers(booking.From(), booking.To(), -booking.Pax());
		}
	}
	
	public Hashtable<Integer, Booking> clone() {
		Hashtable<Integer, Booking> clone = new Hashtable<Integer, Booking>();
		Enumeration<Integer> enumKey = bookings.keys();
		while(enumKey.hasMoreElements()) {
		    Booking booking = bookings.get(enumKey.nextElement());
		    clone.put(booking.Hash(), booking);
		}
		return clone;
	}
	
	public int TotalBookedPax() {
		int result=0;
		Enumeration<Integer> enumKey = bookings.keys();
		while(enumKey.hasMoreElements()) {
		    Booking booking = bookings.get(enumKey.nextElement());
		    result+=booking.Pax();
		}
		return result;
	}

}
