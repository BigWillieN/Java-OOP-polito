package it.polito.ski;

public class Slope {
	String name; String difficulty; String lift;


	public Slope(String name, String difficulty, String lift) {
		super();
		this.name = name;
		this.difficulty = difficulty;
		this.lift = lift;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}


	public String getLift() {
		return lift;
	}


	public void setLift(String lift) {
		this.lift = lift;
	}
	
	
}
