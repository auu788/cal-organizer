package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

public class XMLManager {
	private XStream xstream;
	
	public XMLManager() {
		xstream = new XStream(new DomDriver());
		
		xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypeHierarchy(Collection.class);
		xstream.allowTypeHierarchy(Event.class);
	}
	
	public void exportToXML(List<Event> eventList, File filePath) {
		xstream.alias("event", Event.class);
		xstream.alias("events", List.class);
		
		FileWriter file = null;
		try {
			file = new FileWriter(filePath);
		} catch (IOException e) {
			System.err.println("Nie uda³o siê stworzyæ pliku.");
			e.printStackTrace();
		}
		
		try {
			file.write("<?xml version=\"1.0\"?>\n");
		} catch (IOException e) {
			System.err.println("Nie uda³o siê zapisaæ XML-a do pliku.");
			e.printStackTrace();
		}
		
		try {
			file.flush();
		} catch (IOException e1) {
			System.err.println("Nie uda³o siê wyczyœciæ bufora strumienia.");
			e1.printStackTrace();
		}
		try {
			file.close();
		} catch (IOException e1) {
			System.err.println("Nie uda³o siê zamkn¹æ pliku.");
			e1.printStackTrace();
		}
		
		try {
			xstream.toXML(eventList, new FileWriter(filePath, true));
		} catch (IOException e) {
			System.err.println("Nie uda³o siê zapisaæ XML-a do pliku.");
			e.printStackTrace();
		}
	}
	
	public List<Event> importFromXML(File file) {
		List<Event> eventList = null;
		FileReader reader = null;
		
		xstream.alias("event", Event.class);
		xstream.alias("events", List.class);
		
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.err.println("Nie uda³o siê wczytaæ danych z pliku XML.");
			e.printStackTrace();
		}
		
		try {
			eventList = (List<Event>) xstream.fromXML(reader);
		} catch (XStreamException e) {
			System.err.println("Nie uda³o siê wczytaæ danych z pliku XML.");
			e.printStackTrace();

		}
		
		for (Event evt : eventList) {
			System.out.println(evt.getName());
		}
		
		return eventList;
	}
}
