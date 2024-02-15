package it.polito.ski;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Parking {
	String name; int slots;
	List<Lift> liftsServed = new ArrayList<>();


	public Parking(String name, int slots) {
		super();
		this.name = name;
		this.slots = slots;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getSlots() {
		return slots;
	}


	public void setSlots(int slots) {
		this.slots = slots;
	}


	public List<Lift> getLiftsServed() {
		return liftsServed;
	}


	public void setLiftsServed(List<Lift> liftsServed) {
		this.liftsServed = liftsServed;
	}
	
	public void addLiftServed(Lift liftServed) {
		this.liftsServed.add(liftServed);
	}
	
	
	
}
