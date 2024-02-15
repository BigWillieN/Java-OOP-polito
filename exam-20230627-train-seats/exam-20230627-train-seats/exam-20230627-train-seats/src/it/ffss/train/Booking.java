package it.ffss.train;

public class Booking {
	String ssn; String name; String surname;
	   String begin; String end; String car; String seat;
	   String bookingID;

	public Booking(String ssn, String name, String surname, String begin, String end, String car, String seat) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.surname = surname;
		this.begin = begin;
		this.end = end;
		this.car = car;
		this.seat = seat;
	}


	public String getSsn() {
		return ssn;
	}


	public String getName() {
		return name;
	}


	public String getSurname() {
		return surname;
	}


	public String getBegin() {
		return begin;
	}


	public String getEnd() {
		return end;
	}


	public String getCar() {
		return car;
	}


	public String getSeat() {
		return seat;
	}


	public void setSsn(String ssn) {
		this.ssn = ssn;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public void setBegin(String begin) {
		this.begin = begin;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public void setCar(String car) {
		this.car = car;
	}


	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getBookingID() {
		return bookingID;
	}
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}  
	   
}
