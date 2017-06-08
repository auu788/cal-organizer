package model;

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

/**
 * Klasa zarz¹dzaj¹ca powiadomieniami.
 */
public class AlarmChecker {
	private EventManager eventManager;
	private CheckEvents checkEvents;
	private SettingsManager settingsManager;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt zarz¹dzaj¹cy powiadomieniami.
	 * Ustawia timer, który co minutê uruchamia obiekt sprawdzaj¹cy, czy maj¹ byæ pokazane jakieœ powiadmienia.
	 * 
	 * @param eventManager menad¿er wydarzeñ, wykorzystywany do pobrania aktualnej listy wydarzeñ
	 * @param settingsManager menad¿er ustawieñ, wykorzystywany do pobrania aktualnego dŸwiêku powiadomienia
	 * @see EventManager 
	 * @see SettingsManager
	 */
	public AlarmChecker(EventManager eventManager, SettingsManager settingsManager) {
		this.eventManager = eventManager;
		this.settingsManager = settingsManager;
		
		Timer t = new Timer();
		
		checkEvents = new CheckEvents(this.eventManager.getEventList());
		t.scheduleAtFixedRate(checkEvents, 0, 60000);
	}
	
	/**
	 * Klasa urchamiaj¹ca okienko z powiadomieniem, jeœli czas alarmu dla wydarzenia pokrywa z czasem teraŸniejszym.
	 */
	private class CheckEvents extends TimerTask {
		private List<Event> eventList;
		
		/**
		 * Konstruktor inicjalizuj¹cy obiekt sprawdzaj¹cy czy w³¹czyæ powiadomienie.
		 * @param eventList lista wydarzeñ
		 * @see EventManager
		 */
		public CheckEvents(List<Event> eventList) {
			this.eventList = eventList;
		}
		
		@Override
		public void run() {
			checkEvents();
		}
		
		/**
		 * Sprawdza w chwili odpalenia metody dla jakiegokolwiek wydarzenia data alarmu jest taka sama jak czas teraŸniejszy. Jeœli tak, to w³¹cza okienko z powiadomieniem i sygna³ dŸwiêkowy.
		 */
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
		
		/**
		 * W³¹cza dŸwiêk powiadomienia, jeœli u¿ytkownik takowy ustawi³.
		 */
		private void playSound() {
			String alarmFilePath = settingsManager.getAlarmFilePath();

			if (alarmFilePath.isEmpty() || alarmFilePath == null) {
				return;
			}
			
	        InputStream in = null;
			try {
				in = new FileInputStream(alarmFilePath);
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