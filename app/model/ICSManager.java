package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.GregorianCalendar;
import java.util.List;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

public class ICSManager {
	private Calendar calendar;
	
	public ICSManager() {
		calendar = new Calendar();
		calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
	}
	
	public void exportToICS(List<Event> eventList, File file) {
		
		for (Event evt : eventList) {
			String eventName = evt.getName();
			Location location = new Location(evt.getPlace());
			DateTime start = new DateTime(evt.getDate());
			
			VEvent icsEvent = new VEvent(start, eventName);
			icsEvent.getProperties().add(location);
			
			if (evt.getAlarm() != null) {
				DateTime alarm_time = new DateTime(evt.getAlarm());
				VAlarm alarm = new VAlarm(alarm_time);
				icsEvent.getAlarms().add(alarm);
			}
			
			UidGenerator ug = null;
			try {
				ug = new UidGenerator("uidGen");
			} catch (SocketException e) {
				e.printStackTrace();
			}
			
			icsEvent.getProperties().add(ug.generateUid());
			
			calendar.getComponents().add(icsEvent);
		}

		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			System.err.println("Wyst¹pi³ b³¹d przy tworzeniu pliku ICS");
			e1.printStackTrace();
		}

		CalendarOutputter outputter = new CalendarOutputter();
		
		try {
			outputter.output(calendar, fout);
		} catch (ValidationException e) {
			System.err.println("Wyst¹pi³ b³¹d przy formatowaniu danych do formatu ICS");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Wyst¹pi³ b³¹d przy zapisie pliku ICS");
			e.printStackTrace();
		}
	}
}
