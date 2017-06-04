package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import model.AddEventModel;
import model.EventManager;

import view.AddEventDialog;
import view.CalendarView;

public class EventConfirmListener implements ActionListener {
	AddEventDialog dialog;
	AddEventModel model;
	EventManager eventManager;
	CalendarView theCalendarView;
	
	String name = null;
	String place = null;
	Date dateHour = null;
	
	EventConfirmListener(AddEventDialog dialog, AddEventModel model, EventManager eventManager, CalendarView theCalendarView) {
		this.dialog = dialog;
		this.model = model;
		this.eventManager = eventManager;
		this.theCalendarView = theCalendarView;
	};
	
	public void actionPerformed(ActionEvent e) {	
		if (assignAndIsValid() == true) {
			eventManager.addEvent(name, place, dateHour, getDateOfAlarm());
			theCalendarView.updateEventDays(
					eventManager.getEventsByYearAndMonth(
							theCalendarView.getYearSelectComboBoxSelectedItem(),
							theCalendarView.getMonthSelectComboBoxSelectedItem()));
			
			dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	private Date convertStringToDate(String dateHour) throws ParseException {
		DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = null;
		
		try {
			date = date_format.parse(dateHour);
		} catch (ParseException e){
			JOptionPane.showMessageDialog(dialog,
				    "Data oraz godzina musz¹ byæ poprawnie wpisane",
				    "B³¹d!",
				    JOptionPane.WARNING_MESSAGE);
			throw new ParseException("Date is wrong", 0);
		}
		
		return date;
	}
	
	private boolean assignAndIsValid() {
		if (dialog.getEventName().length() == 0) {
			JOptionPane.showMessageDialog(dialog,
				    "Wydarzenie musi mieæ nazwê.",
				    "B³¹d!",
				    JOptionPane.WARNING_MESSAGE);
			
			return false;
		}
		
		if (dialog.getEventPlace().length() == 0) {
			JOptionPane.showMessageDialog(dialog,
				    "Wydarzenie musi mieæ miejsce.",
				    "B³¹d!",
				    JOptionPane.WARNING_MESSAGE);
			
			return false;
		}
		
		try {
			dateHour = convertStringToDate(dialog.getEventDate());
		} catch (ParseException e) {
			return false;
		}
		
		name = dialog.getEventName();
		place = dialog.getEventPlace();
		
		return true;
	}
	
	private Date getDateOfAlarm() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateHour);
		cal.add(Calendar.MINUTE, model.getAlarmTimes()[dialog.getEventAlarm()] * -1);
		Date newDate = cal.getTime();
		return newDate;
	}
}