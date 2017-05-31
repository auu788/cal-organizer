package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.CalendarModel;
import view.CalendarView;

public class MonthComboBoxListener implements ActionListener{
	private CalendarView theCalendarView = new CalendarView();
	private CalendarModel theCalendarModel = new CalendarModel();
	
	MonthComboBoxListener(CalendarView theCalendarView, CalendarModel theCalendarModel){
		this.theCalendarView = theCalendarView;
		this.theCalendarModel = theCalendarModel;
	}
	
	public void actionPerformed(ActionEvent e) {
		int year = theCalendarView.getYearSelectComboBoxSelectedItem();
		int month = theCalendarView.getMonthSelectComboBoxSelectedItem();
		theCalendarView.updateButtonFielsGrid(theCalendarModel.updateCalendar(year, month));
	}
}
