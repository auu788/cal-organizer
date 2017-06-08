package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.converters.extended.NamedMapConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.ArrayTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

/**
 * Klasa zarz¹dzaj¹ca formatem XML.
 */
public class XMLManager {
	private XStream xstream;
	private String settingsDefaultPath;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt menad¿era formatu XML.
	 * Ustanawia równie¿ œcie¿kê dla pliku z ustawieniami.
	 */
	public XMLManager() {
		xstream = new XStream(new DomDriver());
		
		xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypeHierarchy(Collection.class);
		xstream.allowTypeHierarchy(Event.class);
		xstream.addPermission(ArrayTypePermission.ARRAYS);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(AnyTypePermission.ANY);
		
		String myDocumentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
		this.settingsDefaultPath = myDocumentsPath + "/.organizer/settings.xml";
		String directoryPath = myDocumentsPath + "/.organizer";
		
		File directory = new File(directoryPath);
	    if (!directory.exists()){
	    	try {
				Files.createDirectories(Paths.get(directoryPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * Eksportuje wszystkie wydarzenia do formatu XML.
	 * 
	 * @param eventList lista wydarzeñ
	 * @param filePath œcie¿ka do pliku XML
	 */
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
	
	/**
	 * Importuje wydarzenia z formatu XML.
	 * 
	 * @param file œcie¿ka do pliku XML
	 * @return lista wydarzeñ
	 * @see Event
	 */
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
	
	/**
	 * Eksportuje ustawienia do pliku XML.
	 * 
	 * @param settings mapa ustawieñ
	 */
	public void exportSettings(Map<String, String> settings) {
		xstream.registerConverter(new NamedMapConverter(xstream.getMapper(), "entry", "key", String.class, null, String.class, true, false, xstream.getConverterLookup()));
		xstream.alias("settings", Map.class);
		
		FileWriter file = null;
		try {
			file = new FileWriter(this.settingsDefaultPath);
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
			xstream.toXML(settings, new FileWriter(this.settingsDefaultPath, true));
		} catch (IOException e) {
			System.err.println("Nie uda³o siê zapisaæ XML-a do pliku.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Importuje ustawienia z pliku XML.
	 * 
	 * @return mapa ustawieñ
	 */
	public Map<String, String> importSettings() {
		xstream.registerConverter(new NamedMapConverter(xstream.getMapper(), "entry", "key", String.class, null, String.class, true, false, xstream.getConverterLookup()));
		xstream.alias("settings", Map.class);

		Map<String, String> settings = new HashMap<String, String>();
		FileReader reader = null;
		
		try {
			reader = new FileReader(this.settingsDefaultPath);
		} catch (FileNotFoundException e) {
			System.err.println("Nie uda³o siê wczytaæ danych z pliku XML.");
			e.printStackTrace();
		}
		
		try {
			settings = (HashMap<String, String>) xstream.fromXML(reader);
		} catch (XStreamException e) {
			System.err.println("Nie uda³o siê przetworzyæ danych z pliku XML.");
			e.printStackTrace();
		}
		
		return settings;
	}
}
