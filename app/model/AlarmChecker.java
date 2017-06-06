package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import view.AlarmDialog;

import model.Event;
import model.EventManager;

public class AlarmChecker {
	private EventManager eventManager;
	private String alarmFilePath;
	private CheckEvents checkEvents;
	
	public AlarmChecker(EventManager eventManager) {
		this.eventManager = eventManager;
		this.alarmFilePath = "alarm.wav";

		Timer t = new Timer();
		
		checkEvents = new CheckEvents(this.eventManager.getEventList());
		t.scheduleAtFixedRate(checkEvents, 0, 60000);
	}
	
	public void updateAlarmFilePath(String path) {
		checkEvents.updateAlarmFilePath(path);
	}
	
	private class CheckEvents extends TimerTask {
		private List<Event> eventList;
		private String alarmFilePath;
		
		public CheckEvents(List<Event> eventList) {
			this.eventList = eventList;
			this.alarmFilePath = "alarm.wav";
		}
		
		@Override
		public void run() {
			checkEvents();
		}
		
		public void updateAlarmFilePath(String path) {
			this.alarmFilePath = path;
		}
		
		private void checkEvents() {
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			now = cal.getTime();
			
			for (Event evt : eventList) {
				if (now.equals(evt.getAlarm())) {
					long diffInMilliseconds = evt.getDate().getTime() - evt.getAlarm().getTime();
					int alarmMinutes = (int) TimeUnit.MINUTES.convert(diffInMilliseconds,TimeUnit.MILLISECONDS);
					String alarmText;
					if (alarmMinutes >= 60) {
						alarmText = alarmMinutes / 60 + " godzin";
					} else {
						alarmText = alarmMinutes + " minut";
					}
					
					System.out.println("ALARM - " + evt.getName() + " --- " + evt.getPlace());
					AlarmDialog alarm = new AlarmDialog();
					alarm.setAutoRequestFocus(true);
					alarm.setLocationRelativeTo(null);
					alarm.setAlwaysOnTop(true);
					alarm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					alarm.setNameText(evt.getName());
					alarm.setPlaceText(evt.getPlace());
					alarm.setTimeText(alarmText);
					alarm.setVisible(true);
					playSound();
				}
			}
		}
		
		private void playSound() {
	        InputStream in = null;
			try {
				in = new FileInputStream(this.alarmFilePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        AudioStream sound = null;
			try {
				sound = new AudioStream(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        AudioPlayer.player.start(sound);
		}
	}
}