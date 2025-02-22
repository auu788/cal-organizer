package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JDialog;

import model.Event;
import model.EventManager;

import view.CalendarView;
import view.EventInfo;

/**
 * Klasa implementuj�ca listenery dla ka�dego dnia - przycisku na siatce kalendarza.
 * Je�li w danym dniu istnieje jakiekolwiek wydarzenie, zostanie otworzone okienko ze szczeg�ami na jego temat.
 */
public class DayButtonsListener implements ActionListener {
	CalendarView theCalendarView;
	EventManager theEventManager;
	
	/**
	 * Konstruktor inicjalizuj�cy obiekt listenera dla dnia - przycisku na kalendarzu.
	 * @param theCalendarView g��wny widok kalendarza
	 * @param theEventManager menad�er kalendarza
	 * @see CalendarView
	 * @see EventManager
	 */
	public DayButtonsListener(CalendarView theCalendarView, EventManager theEventManager) {
		this.theCalendarView = theCalendarView;
		this.theEventManager = theEventManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		int day = Integer.parseInt(((JButton) e.getSource()).getActionCommand());
		int month = theCalendarView.getMonthSelectComboBoxSelectedItem() + 1;
		int year = theCalendarView.getYearSelectComboBoxSelectedItem();
		List<Event> eventList = theEventManager.getEventsByDate(year, month, day);
		EventInfo eventInfo;
		
		if ((eventList).size() > 0) {
			SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
			eventInfo = new EventInfo(day + "-" + month + "-" + year);
			eventInfo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			eventInfo.setVisible(true);

			for (Event evt : eventList) {				
				String hour = hourFormat.format(evt.getDate());
				String alarmText;
				
				if (evt.getAlarm() == null) {
					alarmText = "Wy��czony";
				} else {
					long diffInMilliseconds = evt.getDate().getTime() - evt.getAlarm().getTime();
					int alarmMinutes = (int) TimeUnit.MINUTES.convert(diffInMilliseconds,TimeUnit.MILLISECONDS);

					if (alarmMinutes < 60) {
						alarmText = "W��czony, " + alarmMinutes + " min przed";
					} else {
						alarmText = "W��czony, " + alarmMinutes / 60 + " godz przed";
					}
				}
	
				eventInfo.addEvent(evt.getName(), evt.getPlace(), hour, alarmText);
			}
		}
	}
}
