package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import model.AlarmChecker;
import model.EventManager;

import view.Settings;

public class SettingsListener implements ActionListener {
	private AlarmChecker alarmChecker;
	private EventManager eventManager;
	
	public SettingsListener(AlarmChecker alarmChecker, EventManager eventManager) {
		this.alarmChecker = alarmChecker;
		this.eventManager = eventManager;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		final Settings settings = new Settings();
		settings.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		settings.setVisible(true);
		
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
				settings.dispose();
				return;
			}
		});
	}

}
