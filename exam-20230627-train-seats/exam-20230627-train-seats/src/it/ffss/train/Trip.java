package it.ffss.train;

public class Trip {
	private String bookedSeat;
	private String carID;
	private int beginStopID;
	private int endStopID;
	
	public Trip(String carID, int beginStopID, int endStopID , String bookedSeat) {
		this.bookedSeat = bookedSeat;
		this.beginStopID = beginStopID;
		this.endStopID = endStopID;
		this.carID = carID;
	}
	public String getBookedSeat() {
		return bookedSeat;
	}
	public int getBeginStopID() {
		return beginStopID;
	}
	public int getEndStopID() {
		return endStopID;
	}
	
	public String getCarID() {
		return this.carID;
	}
}
