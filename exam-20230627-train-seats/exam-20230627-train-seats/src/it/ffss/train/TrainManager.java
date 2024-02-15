package it.ffss.train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TrainManager {
	Set<String> trainClasses = new HashSet<>();
	Map<String, TrainCar> trainCars = new HashMap<>();
	private TreeMap<Integer,Stop> stops = new TreeMap<>();
	Map<String, Booking> trainBookings = new HashMap<>();
	Set<Trip> trips = new HashSet<>();
	String lastStop;
//R1
	/**
	 * add a set of travel classes to the list of classes
	 * offered in the train.
	 * Method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param classes the classes
	 */
	public void addClasses(String... classes) {
		List<String> classList = Arrays.asList(classes);
		trainClasses.addAll(classList);
		
	}

	/**
	 * retrieves the list of classes defined for the train
	 * 
	 * @return list of classes
	 */
	public Collection<String> getClasses() {
		return trainClasses;
	}
	
	/**
	 * adds a new car to train
	 * The car has a unique id, a given number of rows
	 * and for each row the seats go from 'A' to the {@code lastSeat}
	 * 
	 * @param id		unique id of car
	 * @param rows		name of car
	 * @param lastSeat	lastLetter of car
	 * @param klass 	class of the car
	 * @return number of available seats on the train car
	 * @throws TrainException in case of duplicate id or non-existing class
	 */
	public int addCar(String id, int rows, char lastSeat, String klass) throws TrainException {
		if (trainCars.containsKey(id)) throw new TrainException("duplicate id: " + id);
		if (!trainClasses.contains(klass)) throw new TrainException("non existing class: "+ klass);
		TrainCar c = new TrainCar(id, rows, lastSeat, klass);
		trainCars.put(id, c);
		return c.getNumOfSeats();
	}

	/**
	 * retrieves the list of cars with the given class
	 * 
	 * @param klass required class
	 * @return the list of car ids
	 */
	public Collection<String> getCarsByClass(String klass) {
		return this.trainCars.values().stream().filter(o -> o.getKlass().equals(klass)).map(o -> o.getId()).collect(Collectors.toList());
	}

	/**
	 * retrieves the number of seats in the car with the given code
	 * 
	 * @param id id of the car 
	 * @return number of seats
	 */
	public int getNumSeats(String id) {
		if (trainCars.get(id) != null) {
			return this.trainCars.get(id).getNumOfSeats();
		}
		else {
			return 0;
		}
	}

	/**
	 * Define the stop stations for a train.
	 * Stops define the segments that are the part of the
	 * train path between two consecutive stops. 
	 * 
	 * @param stops		stops of the train
	 * @return the number of segments defined
	 */
	public int defineStops(String... stops) {
		for(int i = 0; i < stops.length; i++)
			this.stops.put(i+1, new Stop(i+1, stops[i]));
		return stops.length -1;
	}
	
	
	// custom method
	private int getStopID(String name ) {
		return this.stops.values().stream()
				.filter(x -> x.getName()
				.equals(name))
				.map(x -> x.getID())
				.findFirst().get();	
		}
	
	/**
	 * retrieves the available seats on a given trip from source to destination.
	 * The returned map contains an entry for each car that has seats
	 * available for the specific class and in all the segments from begin to end.
	 * The map contains a list of seats "#X", where # is the row number and X is the seat
	 * e.g. "8A" is the seat in row 8 position A.
	 * The method return only the available seats (i.e. those that are not booked)
	 * 
	 * @param begin		initial stop
	 * @param end		final stop
	 * @param klass 	car class
	 * @return the available seats by car
	 */
	//public Map<String, List<String>> findSeats(String begin, String end, String klass) {
//		return trainCars.values().stream().filter(p -> p.getKlass().equals(klass)).collect(Collectors.toMap(x -> x.getId(), x ->{ 
//			List<String> list = x.getSeats();
//			list.removeAll(this.trips.stream()
//					.filter(z -> z.getCarID().equals(x.getId()))
//					.filter(z -> z.getBeginStopID() <= this.getStopID(begin) && this.getStopID(end) <= z.getEndStopID() 
//					||  z.getBeginStopID() >= this.getStopID(begin) && z.getBeginStopID() <= this.getStopID(end)
//					||  z.getEndStopID() >= this.getStopID(begin) && z.getEndStopID() <= this.getStopID(end)
//					)
//					.map(z -> z.getBookedSeat())
//					.collect(Collectors.toList()));
//				
//					return list;
//					} , (v1,v2) -> v1 , () -> new HashMap<>()));
//}
		
		public Map<String, List<String>> findSeats(String begin, String end, String klass) {
		    return trainCars.values().stream()
		            .filter(car -> car.getKlass().equals(klass))
		            .collect(Collectors.toMap(
		                    TrainCar::getId,
		                    car -> {
		                        List<String> seats = new ArrayList<>(car.getSeats());
		                        List<String> bookedSeats = trips.stream()
		                                .filter(trip -> trip.getCarID().equals(car.getId()))
		                                .filter(trip -> isOverlap(this.getStopID(begin), this.getStopID(end), trip.getBeginStopID(), trip.getEndStopID()))
		                                .map(Trip::getBookedSeat)
		                                .collect(Collectors.toList());
		                        seats.removeAll(bookedSeats);
		                        return seats;
		                    },
		                    (v1, v2) -> v1,
		                    HashMap::new
		            ));
		}

	/**
	 * Book a seat for a person from begin station to end station
	 * on a given car
	 * 
	 * @param ssn		SSN of the passenger
	 * @param name		name of the passenger
	 * @param surname	surname of the passenger
	 * @param begin		initial stop
	 * @param end		final stop
	 * @param car		car id
	 * @param seat		seat number
	 * @return a unique booking code
	 * @throws TrainException in case the car or seat are not valid,
	 * 						  the stops are not valid, 
	 * 						  or the seat is not available for all the segments of the trip
	 */
//	public String bookSeat(String ssn, String name, String surname, 
//			   String begin, String end, String car, String seat) throws TrainException {
////		if(!this.trainCars.keySet().contains(car)) throw new TrainException("Not valid car ID");
//		if(this.trainBookings.values().stream().filter(x -> x.getCar().equals(car))
//				.filter(x -> x.getSeat().equals(seat))
//				.filter( x ->
//						this.getStopID(begin) <= this.getStopID(x.getBegin())
//							&&
//						this.getStopID(end) >= this.getStopID(x.getBegin()))
//				.filter(x ->
//						this.getStopID(begin) <= this.getStopID(x.getEnd())
//							&&
//						this.getStopID(end) >= this.getStopID(x.getEnd()))
//				.count() > 0)
//		throw new TrainException("error");
//
//
//		if(this.stops.values().stream().noneMatch(x -> x.getName().equals(begin)) 
//							||
//		   this.stops.values().stream().noneMatch(x -> x.getName().equals(end)))
//		throw new TrainException("Not valid stops");
//
//		if(!this.findSeats(begin, end, this.trainCars.get(car).getKlass()).get(car).contains(seat)) 
//			throw new TrainException("Seat is taken ");
//
//
//
//		this.trainBookings.put(String.valueOf(this.trainBookings.size() +1),
//								new Booking(String.valueOf(this.trainBookings.size() +1), ssn , name , surname,
//			begin , end , car , seat , this.trainCars.get(car).getKlass()));
//
//		this.trips.add(new Trip(car, this.getStopID(begin) , this.getStopID(end), seat));
//		return String.valueOf(this.trainBookings.size()); 
		
		public String bookSeat(String ssn, String name, String surname, String begin, String end, String car, String seat) throws TrainException {
		    
			
			if (!trainCars.containsKey(car)) {
		        throw new TrainException("Not valid car ID");
		    }
		    
		    if (!stops.containsKey(this.getStopID(begin)) || (!stops.containsKey(this.getStopID(end)))) {
		        throw new TrainException("Not valid stops");
		    }
		    
		    Map<String, List<String>> availableSeats = findSeats(begin, end, trainCars.get(car).getKlass());
		    if (!availableSeats.containsKey(car) || !availableSeats.get(car).contains(seat)) {
		        throw new TrainException("Seat is taken");
		    }
		    

		    
		    trainBookings.put(String.valueOf(trainBookings.size() + 1),
		            new Booking(String.valueOf(trainBookings.size() + 1), ssn, name, surname, begin, end, car, seat, trainCars.get(car).getKlass()));

		    trips.add(new Trip(car, getStopID(begin), getStopID(end), seat));
		    return String.valueOf(trainBookings.size());
		}

private boolean isOverlap(Integer begin1, Integer end1, Integer begin2, Integer end2) {

	return (begin1 < end2 && begin2 < end1);
}
        


	/**
	 * retrieves the car of a given booking
	 * 
	 * @param booking 	id of booking
	 * @return car id
	 */
	public String getBookingCar(String booking) {
		return trainBookings.get(booking).getCar();
	}

	/**
	 * retrieves the SSN of the booked person
	 * 
	 * @param bookingID id of booking
	 * @return  booked person's SSN
	 */
	public String getBookingPassenger(String bookingID) {
		return trainBookings.get(bookingID).getSsn();
	}

	/**
	 * retrieves the seat for a booking
	 * 
	 * @param bookingID id of booking
	 * @return the seat
	 */
	public String getBookingSeat(String bookingID) {
		return trainBookings.get(bookingID).getSeat();
	}

	/**
	 * retrieves the trip for a booking.
	 * A trip is described as the initial and final stop, separated by a "-",
	 * e.g. "Turin-Milan"
	 * 
	 * @param bookingID id of booking
	 * @return trip
	 */
	public String getBookingTrip(String bookingID) {
		return trainBookings.get(bookingID).getBegin()+"-"+trainBookings.get(bookingID).getEnd();
	}

	/**
	 * retrieves the list bookings for a given seat.
	 * Bookings are reported as string with the format "begin-end:SSN"
	 * 
	 * @param car car id
	 * @param seat seat
	 * @return list of bookings
	 */
	public Collection<String> listBookings(String car, String seat) {
		return trainBookings.values().stream()
				.filter(p -> p.getCar().equals(car) && p.getSeat().equals(seat))
				.map(p -> p.getBegin()+"-"+p.getEnd()+":"+p.getSsn())
				.collect(Collectors.toList());
	}

	/**
	 * Define the last station the train stopped at.
	 * this information will be used to understand which bookings
	 * are valid for a seat.
	 * 
	 * @param stop name of the latest stop
	 * @return the number of total people booked on the train after the stop
	 */
	public int setLastStop(String stop) {
		this.lastStop = stop;
		return (int)this.trainBookings.values().stream()
				.filter(x -> this.getStopID(x.getBegin()) <= this.getStopID(stop)
						&& this.getStopID(stop) < this.getStopID(x.getEnd()))
				.count();
	}


	/**
	 * check the booking id of a given seat.
	 * It takes into consideration the last stop, for a given
	 * seat the booking starting at or before the last stop
	 * and not yet terminated is considered.
	 * Returns null if no such booking is available.
	 * Mark the specific booking as checked.
	 * 
	 * @param car	id of the car
	 * @param seat 	seat code
	 * @return booking id
	 */
	public String checkSeat(String car, String seat) {
		List<Booking>  booking = this.trainBookings.values().stream()
				.filter(x -> this.getStopID(x.getBegin()) <= this.getStopID(this.lastStop)
				&& this.getStopID(this.lastStop) < this.getStopID(x.getEnd()))
				.filter(x -> x.getCar().equals(car) && x.getSeat().equals(seat))
				.collect(Collectors.toList());
		
		if(booking.size() < 1) return null;
		
		booking.get(0).check();
		return booking.get(0).getId();
	}


	/**
	 * computes the fill ratio for all the seat of the given class.
	 * It is computed dividing the number of seats (of the class in any car)
	 * that have at least a booking, by the number of seats (of the class in any car)
	 *  
	 * @param klass		class
	 * @return	fill ratio
	 */
	public double showFillRatio(String klass) {
		return ((double) this.trainBookings.values()
				.stream().filter(x -> x.getKlass().equals(klass))
				.map(x -> x.getSeat()).distinct().collect(Collectors.summingInt(x -> 1))
				/ 
				(double) this.trainCars.values().stream()
				.filter(x -> x.getKlass().equals(klass))
				.map(x -> x.getNumOfSeats()).reduce(0, (x1,x2) -> x1 + x2));
	}

	/**
	 * computes the check count per class.
	 * The result map reports, for each class, the number of
	 * booking that have been checked.
	 *
	 * @return the map class : check count
	 */
	public Map<String, Long> checkCoverage() {
		
		Map<String, Long> map = trainBookings.values().stream()
				.filter(p -> p.getChecked() == true)
				.collect(Collectors.groupingBy(
						Booking::getKlass, Collectors.counting()
						));
		
		for(String element : this.trainClasses)
			map.putIfAbsent(element, (long)0);
		return map;
	}

	/**
	 * computes the occupation ratio for all the seat of the given class and for each segment of the train path.
	 * It is computed dividing the number of seats occupied in each segment by 
	 * the number of seats multiplied by number of segments.
	 * This method is similar to {@link #showFillRatio} but consider all the slots represented by
	 * a seat in a given segment (path between two consecutive stops), the result is the proportion
	 * of slots covered by a booking (divided per class).
	 *  
	 * @param klass		class
	 * @return	occupation ratio
	 */
	public double showOccupationRatio(String klass) {
		int totalSegments = stops.size() - 1;
	    int totalOccupiedSeats = 0;
	    
	    for (int segment = 1; segment <= totalSegments; segment++) {
	        int startStop = segment;
	        int endStop = segment + 1;

	        totalOccupiedSeats += trainBookings.values().stream()
	            .filter(booking -> booking.getKlass().equals(klass))
	            .filter(booking -> getStopID(booking.getBegin()) <= startStop && endStop <= getStopID(booking.getEnd()))
	            .count();
	           
	    }

	    int totalSeatsInClass = trainCars.values().stream()
	        .filter(car -> car.getKlass().equals(klass))
	        .mapToInt(TrainCar::getNumOfSeats)
	        .sum();

	    double occupationRatio = (double) totalOccupiedSeats / (totalSegments * totalSeatsInClass);
	    return occupationRatio;
	}

}
