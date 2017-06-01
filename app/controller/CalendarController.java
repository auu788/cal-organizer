package controller;

import model.CalendarModel;
import model.EventManager;
import view.CalendarView;

public class CalendarController {
	private CalendarView theCalendarView = new CalendarView();
	private CalendarModel theCalendarModel = new CalendarModel();
	private EventManager theEventManager = new EventManager();
	
	public CalendarController(CalendarView theCalendarView, CalendarModel theCalendarModel){
		this.theCalendarView = theCalendarView;
		this.theCalendarModel = theCalendarModel;

		theCalendarView.setLabelsNames(theCalendarModel.getDayNames());
		theCalendarView.createYearSelectComboBox(theCalendarModel.getYears());
		theCalendarView.createMonthSelectComboBox(theCalendarModel.getMonthsNames());
		theCalendarView.addYearSelectComboBoxListener(new YearComboBoxListener(this.theCalendarView, this.theCalendarModel, this.theEventManager));
		theCalendarView.addMonthSelectComboBoxListener(new MonthComboBoxListener(this.theCalendarView, this.theCalendarModel, this.theEventManager));
		theCalendarView.createAddEventButton();
		theCalendarView.addAddEventButtonListener(new AddEventButtonListener(this.theCalendarView, this.theEventManager));
		
		int year = theCalendarView.getYearSelectComboBoxSelectedItem();
		int month = theCalendarView.getMonthSelectComboBoxSelectedItem();

		theCalendarView.createButtonFielsGrid();
		theCalendarView.addDayButtonsListener(new DayButtonsListener(this.theCalendarView, this.theEventManager));
		
		theCalendarView.updateButtonFielsGrid(theCalendarModel.updateCalendar(year, month));
	}
	
}
