package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventManager {
	List<Event> eventList = new ArrayList<Event>();
	
	public EventManager() {}
	
	public void addEvent(String name, String place, Date date, Date alarm) {
		eventList.add(new Event(name, place, date, alarm));
	}
	
	public List<Integer> getEventsByYearAndMonth(int year, int month) {
	    List<Integer> eventDays = new ArrayList<Integer>();
	    Calendar cal = Calendar.getInstance();
	    
		for (Event evt : eventList) {
			cal.setTime(evt.getDate());
			int evt_year = cal.get(Calendar.YEAR);
			int evt_month = cal.get(Calendar.MONTH);
			int evt_day = cal.get(Calendar.DAY_OF_MONTH);
			if (evt_year == year && evt_month == month) {
				eventDays.add(evt_day);
			}
		}
		
		return eventDays;
	}
	
	public List<Event> getEventList() {
		return eventList;
	}
	
	public void printAllEvents() {
		for (Event evt : eventList) {
			System.out.println(evt.getEventInfo());
		}
	}
	
	public List<Event> getEventsByDate(int year, int month, int day) {
		List<Event> queredEventList = new ArrayList<Event>();
		String queryDate = new String(day + "-" + month + "-" + year);
		SimpleDateFormat eventFormatDate = new SimpleDateFormat("dd-MM-yyyy");
		
		for (Event evt : eventList) {
			String eventDate = eventFormatDate.format(evt.getDate());
			
			if (queryDate.equals(eventDate)) {
				queredEventList.add(evt);
			}
		}
		
		return queredEventList;
	}
}