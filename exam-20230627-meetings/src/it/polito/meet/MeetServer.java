package it.polito.meet;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class MeetServer {
	Integer meetingID = 1;
	List<String> categoriesList = new LinkedList<>();
	Map<String, Meeting> meetingsMap = new TreeMap<>();
	
	/**
	 * adds a set of meeting categories to the list of categories
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param categories the meeting categories
	 */
	public void addCategories(String... categories) {
		for(String category : categories) {
			categoriesList.add(category);
		}
	}

	/**
	 * retrieves the list of available categories
	 * 
	 * @return list of categories
	 */
	public Collection<String> getCategories() {
		return categoriesList;
	}
	
	
	/**
	 * adds a new meeting with a given category
	 * 
	 * @param title		title of meeting
	 * @param topic	    subject of meeting
	 * @param category  category of the meeting
	 * @return a unique id of the meeting
	 * @throws MeetException in case of non-existing category
	 */
	public String addMeeting(String title, String topic, String category) throws MeetException {
		if(!categoriesList.contains(category)) throw new MeetException("Category not in archive: " + category);
		
		Meeting m = new Meeting(title,topic,category);
		m.setMeetingID(String.valueOf(meetingID));
		meetingsMap.put(m.getMeetingID(), m);
		
		meetingID++;
		return m.getMeetingID();
		
	}

	/**
	 * retrieves the list of meetings with the given category
	 * 
	 * @param category 	required category
	 * @return list of meeting ids
	 */
	public Collection<String> getMeetings(String category) {
			return meetingsMap.values().stream().filter(m -> m.getCategory().equals(category)).map(m -> m.getMeetingID()).collect(Collectors.toList());
	}

	/**
	 * retrieves the title of the meeting with the given id
	 * 
	 * @param meetingId  id of the meeting 
	 * @return the title
	 */
	public String getMeetingTitle(String meetingId) {
		Meeting m = meetingsMap.get(meetingId);
		return m.getTitle();
	
	}

	/**
	 * retrieves the topic of the meeting with the given id
	 * 
	 * @param meetingId  id of the meeting 
	 * @return the topic of the meeting
	 */
	public String getMeetingTopic(String meetingId) {
		Meeting m = meetingsMap.get(meetingId);
		return m.getTopic();
	
	}

	// R2
	
	/**
	 * Add a new option slot for a meeting as a date and a start and end time.
	 * The slot must not overlap with an existing slot for the same meeting.
	 * 
	 * @param meetingId	id of the meeting
	 * @param date		date of slot
	 * @param start		start time
	 * @param end		end time
	 * @return the length in hours of the slot
	 * @throws MeetException in case of slot overlap or wrong meeting id
	 */
	public double addOption(String meetingId, String date, String start, String end) throws MeetException
	 {
		Meeting m = meetingsMap.get(meetingId);
		if (m==null) throw new MeetException("Meeting null");
		
		LocalTime startTime = LocalTime.parse(start);
		LocalTime endTime = LocalTime.parse(end);
		
		List<MeetingSlot> list = new LinkedList<>();
		list = m.getMeetingSlots().stream().filter(s -> s.getDate().equals(date) && s.getStart().isBefore(endTime) && s.getEnd().isAfter(startTime)).collect(Collectors.toList());
		
		if (!list.isEmpty()) throw new MeetException();
		
		MeetingSlot mS = new MeetingSlot(meetingId, date, start, end);
		m.addMeetingSlot(mS);
		Duration d = Duration.between(startTime, endTime);
		return d.toHours();
	 }
		

	/**
	 * retrieves the slots available for a given meeting.
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-15:30".
	 * 
	 * @param meetingId		id of the meeting
	 * @return a map date -> list of slots
	 */
	public Map<String, List<String>> showSlots(String meetingId) {
		return meetingsMap.values().stream()
				.filter(m -> m.getMeetingID().equals(meetingId))
				.flatMap(m -> m.getMeetingSlots().stream())
				.collect(Collectors.groupingBy(s -> s.getDate(), 
						 Collectors.mapping(s -> s.getStartString()+ "-"+ s.getEndString(), Collectors.toList())));
	}
	
	


	/**
	 * Declare a meeting open for collecting preferences for the slots.
	 * 
	 * @param meetingId	is of the meeting
	 */
	public void openPoll(String meetingId) {
		Meeting m = meetingsMap.get(meetingId);
		m.setStatus(true);

	}
	
	public int selectPreference(String email, String name, String surname, String meetingId, String date, String slot) throws MeetException {
	    Meeting m = meetingsMap.get(meetingId);
	    if (m==null) throw new MeetException("Meeting null");
	    if (m.getStatus() == false) throw new MeetException("Not enabled");
	    
	    String[] slots = slot.split("-");
	    
	    MeetingSlot mS = m.getMeetingSlots().stream().filter(s -> s.getDate().equals(date) && s.getStartString().equals(slots[0]) && s.getEndString().equals(slots[1])).findAny().get();
	    if(mS == null ) throw new MeetException("Not enabled");
	    
	    SlotPreference pref = new SlotPreference(email,name,surname,meetingId,date,slot);
	    pref.setMeetSlot(mS);
	    m.addPreference(pref);
	    
	    return mS.getPreferences().size();
	    
	    
	}
		

	/**
	 * retrieves the list of the preferences expressed for a meeting.
	 * Preferences are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=EMAIL", including date, time interval, and email separated
	 * respectively by "T" and "="
	 * 
	 * @param meetingId meeting id
	 * @return list of preferences for the meeting
	 */
	public Collection<String> listPreferences(String meetingId) throws MeetException {
	    Meeting m = meetingsMap.get(meetingId);
	    return m.getPreferences().stream().map(p -> p.getDate() + "T" + p.getSlot() + "=" + p.getEmail()).collect(Collectors.toList());
	}

	/**
	 * close the poll associated to a meeting and returns
	 * the most preferred options, i.e. those that have receive the highest number of preferences.
	 * The options are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=##", including date, time interval, and preference count separated
	 * respectively by "T" and "="
	 * 
	 * @param meetingId	id of the meeting
	 */
	public Collection<String> closePoll(String meetingId) {
	   Meeting m = meetingsMap.get(meetingId);
	   m.setStatus(false);
	  int prefSize; 
	  prefSize = m.getMeetingSlots().stream().max(Comparator.comparing(s -> s.getPreferences().size())).map(s -> s.getPreferences().size()).get();
	  List<MeetingSlot> meetSlots = m.getMeetingSlots().stream().filter(s -> s.getPreferences().size()== prefSize).collect(Collectors.toList());
	  List<String> meetSlots2 = meetSlots.stream().map(s ->s.getDate() + "T" + s.getStartString() + "-" + s.getEndString() + "=" + String.valueOf(s.getPreferences().size())).collect(Collectors.toList());
	  return meetSlots2;
	   
	}

	
	/**
	 * returns the preference count for each slot of a meeting
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots with preferences described as strings with the format 
	 * "hh:mm-hh:mm=###", including the time interval and the number of preferences 
	 * e.g. "14:00-15:30=2".
	 * 
	 * @param meetingId	the id of the meeting
	 * @return the map data -> slot preferences
	 */
	public Map<String, List<String>> meetingPreferences(String meetingId) {
	   Meeting meeting = meetingsMap.get(meetingId);
	   return meeting.meetingSlots.stream()
	            .collect(Collectors.groupingBy(
	                    MeetingSlot::getDate,
	                    Collectors.mapping(
	                            slot -> slot.getStartString() + "-" + slot.getEndString() + "=" + slot.getPreferences().size(),
	                            Collectors.toList()
	                    )
	            ));
	}


	/**
	 * computes the number preferences collected for each meeting
	 * The result is a map that associates to each meeting id the relative count of preferences expressed
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {
		return meetingsMap.values().stream()
	            .collect(Collectors.toMap(Meeting::getMeetingID, meeting -> meeting.getPreferences().size()));
	}
	
}



	