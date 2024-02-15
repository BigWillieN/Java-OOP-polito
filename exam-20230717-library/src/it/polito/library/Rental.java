package it.polito.library;

public class Rental {
	String bookID; String readerID; String startingDate; String endingDate;


	public Rental(String bookID, String readerID, String startingDate) {
		super();
		this.bookID = bookID;
		this.readerID = readerID;
		this.startingDate = startingDate;
	}


	public String getBookID() {
		return bookID;
	}


	public String getReaderID() {
		return readerID;
	}


	public String getStartingDate() {
		return startingDate;
	}


	public String getEndingDate() {
		return endingDate;
	}


	public void setBookID(String bookID) {
		this.bookID = bookID;
	}


	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}


	public void setStartingDate(String startingDate) {
		this.startingDate = startingDate;
	}


	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}
	
	
}
