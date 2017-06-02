package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Event {
	private UUID id;
	private String name;
	private String place;
	private Date date;
	private Date alarm;
	
	public Event(String name, String place, Date date, Date alarm) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.place = place;
		this.date = date;
		
		if (date.equals(alarm)) {
			this.alarm = null;
		} else{
			this.alarm = alarm;
		}
	}
	
	// Dla wczytywania z bazy
	public Event(String name, String place, String date, String alarm, String uuid) {
		DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		this.id = UUID.fromString(uuid);
		this.name = name;
		this.place = place;
		try {
			this.date = date_format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (alarm == null) {
			this.alarm = null;
			
		} else{
			try {
				this.alarm = date_format.parse(alarm);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setAlarm(Date alarm) {
		this.alarm = alarm;
	}
	
	public UUID getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPlace() {
		return place;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Date getAlarm() {
		return alarm;
	}
	
	public String getEventInfo() {
		return "ID: " + id + ", nazwa: " + name + ", miejsce: " + place + ", data: " + date + ", alarm: " + alarm;
	}
}
