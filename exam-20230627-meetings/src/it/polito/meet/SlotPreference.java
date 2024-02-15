package it.polito.meet;

public class SlotPreference {
	String email; String name; String surname; String meetingId; String date; String slot; MeetingSlot meetSlot;

	public SlotPreference(String email, String name, String surname, String meetingId, String date, String slot) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.meetingId = meetingId;
		this.date = date;
		this.slot = slot;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public String getDate() {
		return date;
	}

	public String getSlot() {
		return slot;
	}

	public MeetingSlot getMeetSlot() {
		return meetSlot;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public void setMeetSlot(MeetingSlot meetSlot) {
		this.meetSlot = meetSlot;
	}
	
}
