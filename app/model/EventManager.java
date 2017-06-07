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
 * Klasa agreguj¹ca i zarz¹dzaj¹ca wydarzeniami.
 */
public class EventManager {
	SettingsManager settingsManager;
	String defaultDBPath;
	
	List<Event> eventList = new ArrayList<Event>();
	
	DBManager db;
	XMLManager xml;
	ICSManager ics;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt menad¿era wydarzeñ.
	 * <p>
	 * Tworzy instacjê obiektów zarz¹dzaj¹cych ró¿nymi bazami danych.
	 * Wymusza importowanie danych jeœli, wykryje ¿e ustawienia zawieraj¹ zapisan¹ œciê¿kê do konkretnej bazy danych.
	 * 
	 * @param settingsManager obiekt menad¿era ustawieñ
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
				System.err.println("Z³y format bazy, nie wczytano...");
				System.exit(1);
			}
			
			this.defaultDBPath = dbFile;
		}
		
	}
	
	/**
	 * Tworzy nowe wydarzenie i zapisuje je w liœcie wydarzeñ.
	 * Jeœli w ustawieniach istnieje œcie¿ka do bazy wybranej przez u¿ytkownika, to w zale¿noœci od formatu:
	 * <ul>
	 * <li>XML - eksportuje ca³¹ listê wydarzeñ do pliku</li>
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
	 * Pobiera dni wszystkich wydarzeñ w danym miesi¹cu i roku.
	 * 
	 * @param year rok, dla którego maj¹ byæ wybrane wydarzenia
	 * @param month miesi¹c, dla którego maj¹ byæ wybrane wydarzenia
	 * @return lista dni, w których wyst¹puj¹ wydarzenia
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
	 * Pobiera listê wszystkich wydarzeñ.
	 * @return lista wydarzeñ
	 */
	public List<Event> getEventList() {
		return eventList;
	}
	
	/**
	 * Pobiera listê wydarzeñ z okreœlonego dnia, miesi¹ca i roku.
	 * 
	 * @param year rok, dla którego maj¹ byæ wybrane wydarzenia
	 * @param month miesi¹c, dla którego maj¹ byæ wybrane wydarzenia
	 * @param day dzieñ, dla którego maj¹ byæ wybrane wydarzenia
	 * @return lista wydarzeñ
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
	 * Usuwa wydarzenie. Jeœli istenieje œcie¿ka do bazy ustawionowiona przez u¿ytkownika, to w zale¿noœci od bazy:
	 * <ul>
	 * <li>XML - eksportuje wydarzenia nadpisuj¹c stare wydarzenia
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
	 * Importuje wydarzenia z pliku XML do listy wydarzeñ.
	 * 
	 * @param file œcie¿ka do pliku XML
	 */
	public void importFromXML(File file) {
		this.eventList = xml.importFromXML(file);
		System.out.println("Pomyœlnie zaimportowano dane z pliku XML.");
	}
	
	/**
	 * Eksportuje wydarzenia do pliku XML.
	 * 
	 * @param file œcie¿ka do pliku XML
	 */
	public void exportToXML(File file) {
		xml.exportToXML(this.eventList, file);
		System.out.println("Pomyœlnie wyeksportowane dane do pliku XML.");
	}
	
	/**
	 * Importuje wydarzenia z bazy danych SQLite do listy wydarzeñ.
	 * 
	 * @param file œcie¿ka do pliku DB
	 */
	public void importFromDB(File file) {
		this.eventList = db.importFromDB(file);
		System.out.println("Pomyœlnie zaimportowane dane z bazy SQLite.");
	}
	
	/**
	 * Eksportuje wydarzenia do bazy danych SQLite.
	 * 
	 * @param file œcie¿ka do pliku DB
	 */
	public void exportToDB(File file) {
		db.exportToDB(this.eventList, file);
		System.out.println("Pomyœlnie wyeksportowane dane do bazy SQLite.");
	}
	
	/**
	 * Eksportuje wydarzenia do formatu standardowego iCalendar.
	 * 
	 * @param file œcie¿ka do pliku ICS
	 */
	public void exportToICS(File file) {
		ics.exportToICS(this.eventList, file);
		System.out.println("Pomyœlnie wyeksportowane dane do pliku ICS.");
	}

	/**
	 * Aktualizuje œcie¿kê do pliku z baz¹ danych ustawion¹ przez u¿ytkownika.
	 * Jeœli plik nie istnieje, tworzy nowy i eksportuje do niego wszystkie wydarzenia przechowane w liœcie wydarzeñ.
	 * @param dbFilePath œcie¿ka do pliku z baz¹ danych
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