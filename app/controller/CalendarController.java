package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import model.AlarmChecker;
import model.CalendarModel;
import model.EventManager;
import model.ImportExportChooser;
import model.SettingsManager;
import view.About;
import view.CalendarView;

/**
 * Klasa - kontroler, bêd¹ca ³¹cznikiem pomiêdzy wszystkimi klikalnymi obiektami graficznymi, a baz¹ danych.
 * Dodaje listenery do poszczególnych przycisków, widocznych w okne kalendarza g³ównego.
 * @author auu78
 *
 */
public class CalendarController {
	SettingsManager settingsManager = new SettingsManager();
	private EventManager theEventManager = new EventManager(this.settingsManager);
	private CalendarView theCalendarView = new CalendarView();
	private CalendarModel theCalendarModel = new CalendarModel();
	
	/**
	 * Konstruktor inicjalizuj¹cy kontroler g³ówny dla kalendarza.
	 * @param theCalendarView g³ówny widok kalendarza
	 * @param theCalendarModel g³ówny model kalendarza
	 */
	public CalendarController(final CalendarView theCalendarView, CalendarModel theCalendarModel){
		this.theCalendarView = theCalendarView;
		this.theCalendarModel = theCalendarModel;
		
		AlarmChecker ac = new AlarmChecker(this.theEventManager, this.settingsManager);
		
		theCalendarView.setLabelsNames(theCalendarModel.getDayNames());
		theCalendarView.createYearSelectComboBox(theCalendarModel.getYears());
		theCalendarView.createMonthSelectComboBox(theCalendarModel.getMonthsNames());
		
		theCalendarView.addYearSelectComboBoxListener(new YearComboBoxListener(this.theCalendarView, this.theCalendarModel, this.theEventManager));
		theCalendarView.addMonthSelectComboBoxListener(new MonthComboBoxListener(this.theCalendarView, this.theCalendarModel, this.theEventManager));
		
		theCalendarView.createAddEventButton();
		theCalendarView.addAddEventButtonListener(new AddEventButtonListener(this.theCalendarView, this.theEventManager));
		theCalendarView.addShowEventsItemListener(new ShowEventsListener(this.theEventManager, this.theCalendarView));
		theCalendarView.addRemoveOlderThanItemListener(new RemoveOlderThanListener(this.theEventManager, this.theCalendarView));
		
		theCalendarView.addImportFromXMLItemListener(new ImportExportXMLListener(this.theEventManager, this.theCalendarView, ImportExportChooser.IMPORT));
		theCalendarView.addExportToXMLItemListener(new ImportExportXMLListener(this.theEventManager, this.theCalendarView, ImportExportChooser.EXPORT));		
		theCalendarView.addExportToICSItemListener(new ExportToICSListener(this.theEventManager));
		theCalendarView.addImportFromDBItemListener(new ImportExportDBListener(this.theEventManager, this.theCalendarView, ImportExportChooser.IMPORT));
		theCalendarView.addExportToDBItemListener(new ImportExportDBListener(this.theEventManager, this.theCalendarView, ImportExportChooser.EXPORT));
		theCalendarView.addSettingsItemListener(new SettingsListener(this.theEventManager, this.settingsManager, this.theCalendarView));
		
		theCalendarView.addAboutItemListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				About about = new About();
				about.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				about.setVisible(true);
			}
		});
		
		theCalendarView.addExitItemListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		int year = theCalendarView.getYearSelectComboBoxSelectedItem();
		int month = theCalendarView.getMonthSelectComboBoxSelectedItem();

		theCalendarView.createButtonFielsGrid();
		theCalendarView.addDayButtonsListener(new DayButtonsListener(this.theCalendarView, this.theEventManager));
		theCalendarView.updateButtonFielsGrid(theCalendarModel.updateCalendar(year, month));
		
		theCalendarView.updateEventDays(
				theEventManager.getEventsByYearAndMonth(
						theCalendarView.getYearSelectComboBoxSelectedItem(),
						theCalendarView.getMonthSelectComboBoxSelectedItem()));
		
		theCalendarView.markTodaysDay(theCalendarModel.getTodaysDay());

	}
	
}
