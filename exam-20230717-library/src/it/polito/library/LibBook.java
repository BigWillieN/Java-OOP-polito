package it.polito.library;

import java.util.LinkedList;
import java.util.List;

public class LibBook {
	String title;
	String bookID;
	Boolean available;
	List<Rental> rentals = new LinkedList<>();
	public LibBook(String title, String bookID) {
		super();
		this.title = title;
		this.bookID = bookID;
		this.available = true;
	}

	public String getTitle() {
		return title;
	}

	public String getBookID() {
		return bookID;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public void addRental(Rental r) {
		this.rentals.add(r);
		this.available = false;
	}
	public void endRental(String end) {
		this.rentals.get(rentals.size()-1).setEndingDate(end);
		this.available = true;
	}

	
}
