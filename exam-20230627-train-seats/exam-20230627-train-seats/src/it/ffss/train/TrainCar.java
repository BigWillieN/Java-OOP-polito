package it.ffss.train;

import java.util.ArrayList;
import java.util.List;

public class TrainCar {
	String id;
	int rows;
	char lastSeat;
	String klass;
	int numberOfSeats;
	

	public TrainCar(String id, int rows, char lastSeat, String klass) {
		super();
		this.id = id;
		this.rows = rows;
		this.lastSeat = lastSeat;
		this.klass = klass;
		this.numberOfSeats = rows * (lastSeat - 64);
	}
	
	public String getId() {
		return id;
	}
	public int getRows() {
		return rows;
	}
	public char getLastSeat() {
		return lastSeat;
	}
	public String getKlass() {
		return klass;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setLastSeat(char lastSeat) {
		this.lastSeat = lastSeat;
	}
	public void setKlass(String klass) {
		this.klass = klass;
	}
	public int getNumOfSeats() {
		
		return numberOfSeats;
	}
	
	public String formatSeat() {
    	return rows + "" + lastSeat;
    }
	
	
	public List<String> getSeats(){
		ArrayList<String> carSeats = new ArrayList<>();
		for (int i = 1; i <= rows; i++) {
			for (char j = 'A'; j <= this.getLastSeat(); j++) {
				carSeats.add(i+""+j);
			}
		}
		return carSeats;
	}
	
	
	
}
