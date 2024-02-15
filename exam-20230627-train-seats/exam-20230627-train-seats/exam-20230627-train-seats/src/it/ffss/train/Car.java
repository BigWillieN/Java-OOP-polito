package it.ffss.train;

import java.util.LinkedList;
import java.util.List;

public class Car {
	String id;
	int rows; 
	char lastSeat; 
	String klass;
	List<String> allCarSeats;
	
	public Car(String id, int rows, char lastSeat, String klass) {
		super();
		this.id = id;
		this.rows = rows;
		this.lastSeat = lastSeat;
		this.klass = klass;
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
	public int getSeatCount() {
		int number = this.lastSeat - 'A' + 1;
		number = number*rows;
		return number;
	}
	public List<String> generateAllCarSeats(){
		List<String> list = new LinkedList<>();
		for (int i = 1; i <= this.rows; i++) {
			for (char c = 'A'; c <= this.lastSeat; c++) {
				String s = String.valueOf(i);
				s = s+c;
				list.add(s);
			}
		}
		this.allCarSeats = list;
		return this.allCarSeats;
		
	}
	public List<String> getAllCarSeats() {
		return allCarSeats;
	}
	public void setAllCarSeats(List<String> allCarSeats) {
		this.allCarSeats = allCarSeats;
	}
	
	
}
