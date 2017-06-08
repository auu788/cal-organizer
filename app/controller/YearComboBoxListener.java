package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.CalendarModel;
import model.EventManager;
import view.CalendarView;

/**
 * Klasa implementuj¹ca listener dla menu rozwijanego pozwalaj¹cego wybraæ miesi¹c wyœwietlany w kalendarzu g³ównym.
 */
public class YearComboBoxListener implements ActionListener {
	private CalendarView theCalendarView = new CalendarView();
	private CalendarModel theCalendarModel = new CalendarModel();
	private EventManager theEventManager;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt listenera.
	 * @param theCalendarView g³ówne okno kalendarza
	 * @param theCalendarModel model z danymi dla okna kalendarza
	 * @param theEventManager menad¿er wydarzeñ
	 */
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
	}

}
