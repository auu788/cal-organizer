package controller;

import model.CalendarModel;
import view.CalendarView;

public class CalendarController {
	private CalendarView theCalendarView = new CalendarView();
	private CalendarModel theCalendarModel = new CalendarModel();
	
	public CalendarController(CalendarView theCalendarView, CalendarModel theCalendarModel){
		this.theCalendarView = theCalendarView;
		this.theCalendarModel = theCalendarModel;

		theCalendarView.setLabelsNames(theCalendarModel.getDayNames());
		theCalendarView.crateYearSelectComboBox(theCalendarModel.getYears());
		theCalendarView.crateMonthSelectComboBox(theCalendarModel.getMonthsNames());
		theCalendarView.addYearSelectComboBoxListener(new YearComboBoxListener(this.theCalendarView, this.theCalendarModel));
		theCalendarView.addMonthSelectComboBoxListener(new MonthComboBoxListener(this.theCalendarView, this.theCalendarModel));
		theCalendarView.addAddEventButtonListener(new AddEventButtonListener());
		
		int year = theCalendarView.getYearSelectComboBoxSelectedItem();
		int month = theCalendarView.getMonthSelectComboBoxSelectedItem();

		theCalendarView.createButtonFielsGrid();
		theCalendarView.updateButtonFielsGrid(theCalendarModel.updateCalendar(year, month));
	}
	
}
