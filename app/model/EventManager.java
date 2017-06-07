package model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Klasa agreguj�ca i zarz�dzaj�ca wydarzeniami.
 */
public class EventManager {
	SettingsManager settingsManager;
	String defaultDBPath;
	
	List<Event> eventList = new ArrayList<Event>();
	
	DBManager db;
	XMLManager xml;
	ICSManager ics;
	
	/**
	 * Konstruktor inicjalizuj�cy obiekt menad�era wydarze�.
	 * <p>
	 * Tworzy instacj� obiekt�w zarz�dzaj�cych r�nymi bazami danych.
	 * Wymusza importowanie danych je�li, wykryje �e ustawienia zawieraj� zapisan� �ci�k� do konkretnej bazy danych.
	 * 
	 * @param settingsManager obiekt menad�era ustawie�
	 * @see SettingsManager
	 */
	public EventManager(SettingsManager settingsManager) {
		this.settingsManager = settingsManager;
		
		db = new DBManager();
		xml = new XMLManager();
		ics = new ICSManager();
		
		String dbFile = settingsManager.getDBFilePath();
		
		if (!dbFile.isEmpty()) {
			if (dbFile.endsWith(".db")) {
				importFromDB(new File(dbFile));
			} else if (dbFile.endsWith(".xml")) {
				importFromXML(new File(dbFile));
			} else {
				System.err.println("Z�y format bazy, nie wczytano...");
				System.exit(1);
			}
			
			this.defaultDBPath = dbFile;
		}
		
	}
	
	/**
	 * Tworzy nowe wydarzenie i zapisuje je w li�cie wydarze�.
	 * Je�li w ustawieniach istnieje �cie�ka do bazy wybranej przez u�ytkownika, to w zale�no�ci od formatu:
	 * <ul>
	 * <li>XML - eksportuje ca�� list� wydarze� do pliku</li>
	 * <li>DB - zapisuje utworzone wydarzenie w bazie danych</li>
	 * </ul>
	 * @param name nazwa lub opis wydarzenia
	 * @param place miejsce wydarzenia
	 * @param date data wydarzenia
	 * @param alarm data powiadomienia dla wydarzenia
	 */
	public void addEvent(String name, String place, Date date, Date alarm) {
		Event event = new Event(name, place, date, alarm);
		eventList.add(event);
		
		if (defaultDBPath.endsWith(".xml")) {
			xml.exportToXML(eventList, new File(defaultDBPath));
		} else {
			db.addEvent(event);
		}
	}
	
	/**
	 * Pobiera dni wszystkich wydarze� w danym miesi�cu i roku.
	 * 
	 * @param year rok, dla kt�rego maj� by� wybrane wydarzenia
	 * @param month miesi�c, dla kt�rego maj� by� wybrane wydarzenia
	 * @return lista dni, w kt�rych wyst�puj� wydarzenia
	 */
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
	
	/**
	 * Pobiera list� wszystkich wydarze�.
	 * @return lista wydarze�
	 */
	public List<Event> getEventList() {
		return eventList;
	}
	
	/**
	 * Pobiera list� wydarze� z okre�lonego dnia, miesi�ca i roku.
	 * 
	 * @param year rok, dla kt�rego maj� by� wybrane wydarzenia
	 * @param month miesi�c, dla kt�rego maj� by� wybrane wydarzenia
	 * @param day dzie�, dla kt�rego maj� by� wybrane wydarzenia
	 * @return lista wydarze�
	 */
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
	
	/**
	 * Usuwa wydarzenie. Je�li istenieje �cie�ka do bazy ustawionowiona przez u�ytkownika, to w zale�no�ci od bazy:
	 * <ul>
	 * <li>XML - eksportuje wydarzenia nadpisuj�c stare wydarzenia
	 * <li>DB - usunie dane zdarzenie z bazy
	 * </ul>
	 * @param id unikalny identyfikator UUID wydarzenia
	 */
	public void removeById(UUID id) {
		Iterator<Event> iter = eventList.listIterator();
		
		while (iter.hasNext()) {
			if (iter.next().getID().equals(id)) {
				iter.remove();
				
				if (defaultDBPath.endsWith(".xml")) {
					xml.exportToXML(eventList, new File(defaultDBPath));
				} else {
					db.removeById(id.toString());
				}
			}
		}
	}
	
	/**
	 * Importuje wydarzenia z pliku XML do listy wydarze�.
	 * 
	 * @param file �cie�ka do pliku XML
	 */
	public void importFromXML(File file) {
		this.eventList = xml.importFromXML(file);
		System.out.println("Pomy�lnie zaimportowano dane z pliku XML.");
	}
	
	/**
	 * Eksportuje wydarzenia do pliku XML.
	 * 
	 * @param file �cie�ka do pliku XML
	 */
	public void exportToXML(File file) {
		xml.exportToXML(this.eventList, file);
		System.out.println("Pomy�lnie wyeksportowane dane do pliku XML.");
	}
	
	/**
	 * Importuje wydarzenia z bazy danych SQLite do listy wydarze�.
	 * 
	 * @param file �cie�ka do pliku DB
	 */
	public void importFromDB(File file) {
		this.eventList = db.importFromDB(file);
		System.out.println("Pomy�lnie zaimportowane dane z bazy SQLite.");
	}
	
	/**
	 * Eksportuje wydarzenia do bazy danych SQLite.
	 * 
	 * @param file �cie�ka do pliku DB
	 */
	public void exportToDB(File file) {
		db.exportToDB(this.eventList, file);
		System.out.println("Pomy�lnie wyeksportowane dane do bazy SQLite.");
	}
	
	/**
	 * Eksportuje wydarzenia do formatu standardowego iCalendar.
	 * 
	 * @param file �cie�ka do pliku ICS
	 */
	public void exportToICS(File file) {
		ics.exportToICS(this.eventList, file);
		System.out.println("Pomy�lnie wyeksportowane dane do pliku ICS.");
	}

	/**
	 * Aktualizuje �cie�k� do pliku z baz� danych ustawion� przez u�ytkownika.
	 * Je�li plik nie istnieje, tworzy nowy i eksportuje do niego wszystkie wydarzenia przechowane w li�cie wydarze�.
	 * @param dbFilePath �cie�ka do pliku z baz� danych
	 */
	public void updateDBPath(String dbFilePath) {
		this.defaultDBPath = dbFilePath;
		
		
		if (!this.defaultDBPath.isEmpty()) {
			if (new File(this.defaultDBPath).exists()) {
				if (this.defaultDBPath.endsWith(".xml")) {
					this.eventList = xml.importFromXML(new File(this.defaultDBPath));
				} else {
					this.eventList = db.importFromDB(new File(this.defaultDBPath));
				}
				
			} else {
				if (this.defaultDBPath.endsWith(".xml")) {
					xml.exportToXML(eventList, new File(this.defaultDBPath));
				} else {
					db.createTable();
					db.exportToDB(eventList, new File(this.defaultDBPath));
				}
			}
		}
	}

}