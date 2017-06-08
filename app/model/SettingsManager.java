package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

/**
 * Menad¿er ustawieñ u¿ytkownika pozwalaj¹cy na ustawienie w³asnego dŸwiêku powiadomienia lub u¿ycie konkretnej bazy SQlite3 lub XML.
 */
public class SettingsManager {
	private String alarmFilePath;
	private String dbFilePath;
	private XMLManager xml;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt menad¿era ustawieñ.
	 */
	public SettingsManager() {
		this.xml = new XMLManager();
		
		if (settingsFileExists()) {
			updatePaths();
		} else {
			this.alarmFilePath = "";
			this.dbFilePath = "";
			updateSettingsFile();
		}
	}
	
	/** Ustawia œcie¿kê do pliku dŸwiêkowego dla powiadomienia.
	 * 
	 * @param path œcie¿ka do pliku WAV
	 */
	public void setAlarmFilePath(String path) {
		this.alarmFilePath = path;
		updateSettingsFile();
	}
	
	/**
	 * Ustawia œcie¿kê do pliku bazy danych SQLite3 lub XML.
	 * 
	 * @param path œcie¿ka do pliku DB lub XML
	 */
	public void setDBFilePath(String path) {
		this.dbFilePath = path;
		updateSettingsFile();
	}
	
	/**
	 * Pobiera œcie¿kê do pliku dŸwiêkowego dla powiadomienia.
	 * 
	 * @return œcie¿ka do pliku WAV
	 */
	public String getAlarmFilePath() {
		return this.alarmFilePath;
	}
	
	/**
	 * Pobiera œcie¿kê do bazy danych.
	 * 
	 * @return œcie¿ka do bazy danych DB lub XML
	 */
	public String getDBFilePath() {
		return this.dbFilePath;
	}
	
	/**
	 * Pobiera ustawienia z pliku z ustawieniami XML i aktualizuje œcie¿ki do plików bazy danych i pliku dŸwiêkowego.
	 */
	private void updatePaths() {
		Map<String, String> settings = xml.importSettings();
		
		if (settings == null) {
			this.alarmFilePath = "";
			this.dbFilePath = "";
			return;
		}
		
		if (settings.get("alarmSoundFilePath") == null) {
			this.alarmFilePath = "";
		} else {
			this.alarmFilePath = settings.get("alarmSoundFilePath");
		}
		
		if (settings.get("dbFilePath") == null) {
			this.dbFilePath = "";
		} else {
			
			this.dbFilePath = settings.get("dbFilePath");
		}
	}
	
	/**
	 * Eksportuje ustawienia do pliku z ustawieniami XML.
	 */
	private void updateSettingsFile() {
		Map<String, String> settings = new HashMap<String, String>();
		
		settings.put("alarmSoundFilePath", this.alarmFilePath);
		settings.put("dbFilePath", this.dbFilePath);
		
		xml.exportSettings(settings);
	}
	
	/**
	 * Sprawdza czy plik z ustawieniami istnieje.
	 * 
	 * @return wartoœæ bool
	 */
	private boolean settingsFileExists() {
		String myDocumentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
		String settingsDefaultPath = myDocumentsPath + "/.organizer/settings.xml";
		
		if (new File(settingsDefaultPath).isFile()) {
			return true;
		}
		
		return false;
	}
}
