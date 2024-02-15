package it.polito.meet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Meeting {
	String title;
	String topic; 
	String category;
	String meetingID;
	Boolean status = false;
	List<SlotPreference> preferences = new LinkedList<>();
	List<MeetingSlot> meetingSlots = new ArrayList<>();
	public Meeting(String title, String topic, String category) {
		super();
		this.title = title;
		this.topic = topic;
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public String getTopic() {
		return topic;
	}
	public String getCategory() {
		return category;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMeetingID() {
		return meetingID;
	}
	public void setMeetingID(String meetingID) {
		this.meetingID = meetingID;
	}
	public List<MeetingSlot> getMeetingSlots() {
		return meetingSlots;
	}
	public void setMeetingSlots(List<MeetingSlot> meetingSlots) {
		this.meetingSlots = meetingSlots;
	}
	public void addMeetingSlot(MeetingSlot meet) {
		meetingSlots.add(meet);
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public List<SlotPreference> getPreferences() {
		return preferences;
	}
	public void setPreferences(List<SlotPreference> preferences) {
		this.preferences = preferences;
	}
	public void addPreference(SlotPreference preference) {
		preferences.add(preference);
		preference.getMeetSlot().addPreference(preference);
	}
	
	
	
}
