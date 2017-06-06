package model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class EventManager {
	List<Event> eventList = new ArrayList<Event>();
	
	DBManager db;
	XMLManager xml;
	ICSManager ics;
	
	public EventManager() {
		db = new DBManager();
		xml = new XMLManager();
		ics = new ICSManager();
		
		//eventList = db.loadEventsFromDB();
	}
	
	public void addEvent(String name, String place, Date date, Date alarm) {
		Event event = new Event(name, place, date, alarm);
		eventList.add(event);
		
		//db.addEvent(event);
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
		SimpleDateFormat eventFormatDate = new SimpleDateFormat("d-M-yyyy");
		
		for (Event evt : eventList) {
			String eventDate = eventFormatDate.format(evt.getDate());
			if (queryDate.equals(eventDate)) {
				queredEventList.add(evt);
			}
		}
		
		return queredEventList;
	}
	
	public void removeById(UUID id) {
		Iterator<Event> iter = eventList.listIterator();
		
		while (iter.hasNext()) {
			if (iter.next().getID().equals(id)) {
				//db.removeById(id.toString());
				iter.remove();
			}
		}
	}
	
	public void importFromXML(File file) {
		this.eventList = xml.importFromXML(file);
		System.out.println("Pomyœlnie zaimportowano dane z pliku XML.");
	}
	
	public void exportToXML(File file) {
		xml.exportToXML(this.eventList, file);
		System.out.println("Pomyœlnie wyeksportowane dane do pliku XML.");
	}
	
	public void importFromDB(File file) {
		this.eventList = db.importFromDB(file);
		System.out.println("Pomyœlnie zaimportowane dane z bazy SQLite.");
	}
	
	public void exportToDB(File file) {
		db.exportToDB(this.eventList, file);
		System.out.println("Pomyœlnie wyeksportowane dane do bazy SQLite.");
	}
	
	public void exportToICS(File file) {
		ics.exportToICS(this.eventList, file);
		System.out.println("Pomyœlnie wyeksportowane dane do pliku ICS.");
	}

}