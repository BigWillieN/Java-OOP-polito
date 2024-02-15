package it.ffss.train;

public class Stop {
	String stopName;
	Integer stopPosition;
	public Stop(String stopName, Integer stopPosition) {
		super();
		this.stopName = stopName;
		this.stopPosition = stopPosition;
	}
	public String getStopName() {
		return stopName;
	}
	public Integer getStopPosition() {
		return stopPosition;
	}
	public void setStopName(String stopName) {
		this.stopName = stopName;
	}
	public void setStopPosition(Integer stopPosition) {
		this.stopPosition = stopPosition;
	}
	
	
}
