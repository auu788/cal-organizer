package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.AlarmChecker;
import model.EventManager;
import model.ImportExportChooser;
import model.SettingsManager;

import view.CalendarView;
import view.Settings;

public class SettingsListener implements ActionListener {
	private AlarmChecker alarmChecker;
	private EventManager eventManager;
	private SettingsManager settingsManager;
	private CalendarView calendarView;
	
	public SettingsListener(AlarmChecker alarmChecker, EventManager eventManager, SettingsManager settingsManager, CalendarView calendarView) {
		this.alarmChecker = alarmChecker;
		this.eventManager = eventManager;
		this.settingsManager = settingsManager;
		this.calendarView = calendarView;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		final Settings settings = new Settings();
		settings.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		settings.setAlarmFilePath(settingsManager.getAlarmFilePath());
		settings.setDBFilePath(settingsManager.getDBFilePath());
		settings.setVisible(true);
		
		settings.addAlarmFilePathButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Klik");
				JFileChooser fileChooser = new JFileChooser();
				File file;
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki WAV (*.wav)", "wav");
				fileChooser.addChoosableFileFilter(filter);
				fileChooser.setAcceptAllFileFilterUsed(false);
				int val = fileChooser.showOpenDialog(null);		
				
				if (val == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					settings.setAlarmFilePath(file.toString());
				}
			}
		});
		
		settings.addDBFilePathButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				File file;
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki bazy wydarzeñ (.db, .xml)", "db", "xml");
				fileChooser.addChoosableFileFilter(filter);
				fileChooser.setAcceptAllFileFilterUsed(false);
				int val = fileChooser.showOpenDialog(null);		
				
				if (val == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					settings.setDBFilePath(file.toString());
				}
			}
		});
		
		
		settings.addCancelButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settings.dispose();
				return;
			}
		});
		
		settings.addConfirmButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String alarmFilePath = settings.getAlarmFilePath();
				alarmChecker.updateAlarmFilePath(alarmFilePath);
				System.out.println("Sciezka: " + alarmFilePath);
				settingsManager.setAlarmFilePath(settings.getAlarmFilePath());
				settingsManager.setDBFilePath(settings.getDBFilePath());
				eventManager.updateDBPath(settings.getDBFilePath());
				calendarView.updateEventDays(
						eventManager.getEventsByYearAndMonth(
								calendarView.getYearSelectComboBoxSelectedItem(),
								calendarView.getMonthSelectComboBoxSelectedItem()));
				settings.dispose();
				return;
			}
		});
	}

}
