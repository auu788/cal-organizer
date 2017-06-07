package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Klasa odpowiadaj�ca za pojedy�czy obiekt wydarzenia.
 */
public class Event {
	private UUID id;
	private String name;
	private String place;
	private Date date;
	private Date alarm;
	
	/** 
	 * Konstruktor generuj�cy instacj� wydarzenia.
	 * 
	 * @param name 	nazwa lub opis wydarzenia
	 * @param place miejsce wydarzenia
	 * @param date 	data wydarzenia
	 * @param alarm data wyst�pienia powiadomienia
	 */
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
	
	/** 
	 * Konstruktor generuj�cy instacj� wydarzenia.
	 * Wykorzystywany dla pobierania wydarze� z bazy danych.
	 * 
	 * @param name 	nazwa lub opis wydarzenia
	 * @param place miejsce wydarzenia
	 * @param date 	data wydarzenia
	 * @param alarm data wyst�pienia powiadomienia
	 * @param uuid unikalny identyfikator uuid
	 */
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
	/**
	 * Ustawia nazw� lub opis wydarzenia.
	 * 
	 * @param name nazwa lub opis wydarzenia
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Ustawia miejsce lub opis wydarzenia.
	 * 
	 * @param place miejsce wydarzenia
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	
	/**
	 * Ustawia dat� wydarzenia.
	 * 
	 * @param date data wydarzenia
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Ustawia dat� powiadomienia dla wydarzenia.
	 * 
	 * @param alarm data powiadomienia
	 */
	public void setAlarm(Date alarm) {
		this.alarm = alarm;
	}
	
	/**
	 * Zwraca unikalny identyfikator UUID.
	 * 
	 * @return identyfikator UUID
	 */
	public UUID getID() {
		return id;
	}
	
	/**
	 * Zwraca nazw� lub opis wydarzenia.
	 * 
	 * @return nazwa lub opis wydarzenia
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Zwraca miejsce wydarzenia.
	 * 
	 * @return miejsce wydarzenia
	 */
	public String getPlace() {
		return place;
	}
	
	/**
	 * Zwraca dat� wydarzenia.
	 * 
	 * @return data wydarzenia
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Zwraca dat� powiadomienia dla wydarzenia.
	 * 
	 * @return data powiadomienia dla wydarzenia
	 */
	public Date getAlarm() {
		return alarm;
	}
}
