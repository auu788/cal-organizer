package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

import model.Event;
import model.EventManager;

import view.AddEventDialog;
import view.CalendarView;
import view.EventInfo;

public class DayButtonsListener implements ActionListener {
	CalendarView theCalendarView;
	EventManager theEventManager;
	
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
			for (Event evt : eventList) {
				eventInfo = new EventInfo();
				eventInfo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				eventInfo.setVisible(true);
				System.out.println("Klik: " + evt.getEventInfo());
			}
		}
	}
}
