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

/**
 * Klasa implementuj�ca listener dla akcji "Potwierd�" dla okna "Dodaj wydarzenie".
 * Przeprowadzana jest tutaj weryfikacja danych wpisanych w poszczeg�lne pola dotycz�ce wydarzenia.
 */
public class EventConfirmListener implements ActionListener {
	AddEventDialog dialog;
	AddEventModel model;
	EventManager eventManager;
	CalendarView theCalendarView;
	
	String name = null;
	String place = null;
	Date dateHour = null;
	
	/**
	 * Konstruktor inicjalizuj�cy obiekt listenera.
	 * @param dialog obiekt okienka "Dodaj wydarzenie"
	 * @param model obiekt z danymi do wypisania w okienku "Dodaj okienko"
	 * @param eventManager menad�er wydarze�
	 * @param theCalendarView g��wne okienko kalendarza
	 */
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
			
			JOptionPane.showMessageDialog(dialog,
				    "Stworzono wydarzenie!",
				    "Informacja!",
				    JOptionPane.INFORMATION_MESSAGE);
			dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));

		}
	}
	
	/**
	 * Pobiera dat� skonwertowan� ze Stringa na Date.
	 * @param dateHour data jako obiekt String
	 * @return data jako obiekt Date
	 * @throws ParseException wyj�tek, gdy format daty String jest niezgodny z narzuconm wzorcem
	 */
	private Date convertStringToDate(String dateHour) throws ParseException {
		DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = null;
		
		try {
			date = date_format.parse(dateHour);
		} catch (ParseException e){
			JOptionPane.showMessageDialog(dialog,
				    "Data oraz godzina musz� by� poprawnie wpisane",
				    "B��d!",
				    JOptionPane.WARNING_MESSAGE);
			throw new ParseException("Date is wrong", 0);
		}
		
		return date;
	}
	
	/**
	 * Przepisuje poszczeg�lne atrybuty wydarzenia do atrybut�w klasy, je�li przejd� weryfikacj�.
	 * @return warto�� boolean m�wi�ca, czy wszystkie dane przesz�y weryfikacj�
	 */
	private boolean assignAndIsValid() {
		if (dialog.getEventName().length() == 0) {
			JOptionPane.showMessageDialog(dialog,
				    "Wydarzenie musi mie� nazw�.",
				    "B��d!",
				    JOptionPane.WARNING_MESSAGE);
			
			return false;
		}
		
		if (dialog.getEventPlace().length() == 0) {
			JOptionPane.showMessageDialog(dialog,
				    "Wydarzenie musi mie� miejsce.",
				    "B��d!",
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
	
	/**
	 * Pobiera dat� alarmu jako obiekt Date, odejmuj�c od daty wydarzenia wybran� przez u�ytkownika ilo�� minut.
	 * @return data alarmu
	 */
	private Date getDateOfAlarm() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateHour);
		cal.add(Calendar.MINUTE, model.getAlarmTimes()[dialog.getEventAlarm()] * -1);
		Date newDate = cal.getTime();
		return newDate;
	}
}