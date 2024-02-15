package it.ffss.train;

public class Stop {
	
	private int id;
	private String name;
	
	public Stop(int id , String name) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
}
