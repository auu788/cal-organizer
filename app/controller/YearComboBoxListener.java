package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.CalendarModel;
import model.EventManager;
import view.CalendarView;

public class YearComboBoxListener implements ActionListener {
	private CalendarView theCalendarView = new CalendarView();
	private CalendarModel theCalendarModel = new CalendarModel();
	private EventManager theEventManager = new EventManager();
	
	YearComboBoxListener(CalendarView theCalendarView, CalendarModel theCalendarModel, EventManager theEventManager){
		this.theCalendarView = theCalendarView;
		this.theCalendarModel = theCalendarModel;
		this.theEventManager = theEventManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		int year = theCalendarView.getYearSelectComboBoxSelectedItem();
		int month = theCalendarView.getMonthSelectComboBoxSelectedItem();
		theCalendarView.updateButtonFielsGrid(theCalendarModel.updateCalendar(year, month));
		theCalendarView.updateEventDays(
				theEventManager.getEventsByYearAndMonth(
						theCalendarView.getYearSelectComboBoxSelectedItem(),
						theCalendarView.getMonthSelectComboBoxSelectedItem()));
		
		theEventManager.printAllEvents();
	}

}
