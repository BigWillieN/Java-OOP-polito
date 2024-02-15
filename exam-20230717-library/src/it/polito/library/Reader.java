package it.polito.library;

import java.util.LinkedList;
import java.util.List;

public class Reader {
	String name; String surname;
	String readerID;
	Boolean available;
	List<Rental> rentals = new LinkedList<>();
	Rental currentRental;
	public Reader(String name, String surname, String readerID) {
		super();
		this.name = name;
		this.surname = surname;
		this.readerID = readerID;
		this.available = true;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getReaderID() {
		return readerID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public List<Rental> getRentals() {
		return rentals;
	}
	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}
	public void addRental(Rental r) {
		this.rentals.add(r);
		this.available = false;
		this.currentRental = r;
	}
	public void endRental(String end) {
		this.rentals.get(rentals.size()-1).setEndingDate(end);
		this.available = true;
		this.currentRental = null;
	}
	
	
}
