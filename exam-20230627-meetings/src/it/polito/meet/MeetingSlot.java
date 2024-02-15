package it.polito.meet;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class MeetingSlot {
	String meetingId; String date; String start; String end; 
	List<SlotPreference> preferences = new LinkedList<>();
	
	


	public MeetingSlot(String meetingId, String date, String start, String end) {
		super();
		this.meetingId = meetingId;
		this.date = date;
		this.start = start;
		this.end = end;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public String getDate() {
		return date;
	}

	public LocalTime getStart() {
		LocalTime start2 = LocalTime.parse(start);
		return start2;
	}

	public LocalTime getEnd() {
		LocalTime end2 = LocalTime.parse(end);
		return end2;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	public String getStartString() {
		return start;
	}
	public String getEndString() {
		return end;
	}

	public List<SlotPreference> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<SlotPreference> preferences) {
		this.preferences = preferences;
	}
	public void addPreference(SlotPreference preference) {
		preferences.add(preference);
	}
	
}
