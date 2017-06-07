package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.filechooser.FileSystemView;

public class SettingsManager {
	private String alarmFilePath;
	private String dbFilePath;
	private XMLManager xml;
	
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
	
	public void setAlarmFilePath(String path) {
		this.alarmFilePath = path;
		updateSettingsFile();
	}
	
	public void setDBFilePath(String path) {
		this.dbFilePath = path;
		updateSettingsFile();
	}
	
	public String getAlarmFilePath() {
		return this.alarmFilePath;
	}
	
	public String getDBFilePath() {
		return this.dbFilePath;
	}
	
	private void updatePaths() {
		String[] settings = xml.importSettings();
		
		if (settings == null) {
			this.alarmFilePath = "";
			this.dbFilePath = "";
			return;
		}
		
		if (settings[0] == null) {
			this.alarmFilePath = "";
		} else {
			this.alarmFilePath = settings[0];
		}
		
		if (settings[1] == null) {
			this.dbFilePath = "";
		} else {
			this.dbFilePath = settings[1];
		}
	}
	
	private void updateSettingsFile() {
		
		String[] settings = {this.alarmFilePath, this.dbFilePath};
		xml.exportSettings(settings);
	}
	
	private boolean settingsFileExists() {
		String myDocumentsPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
		String settingsDefaultPath = myDocumentsPath + "/.organizer/settings.xml";
		
		if (new File(settingsDefaultPath).isFile()) {
			return true;
		}
		
		return false;
	}
}
