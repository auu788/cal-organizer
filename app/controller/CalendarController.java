package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.CalendarModel;
import model.DBManager;
import model.EventManager;
import model.ImportExportChooser;
import model.XMLManager;
import view.CalendarView;

public class CalendarController {
	private EventManager theEventManager = new EventManager();
	private CalendarView theCalendarView = new CalendarView();
	private CalendarModel theCalendarModel = new CalendarModel();
	
	public CalendarController(final CalendarView theCalendarView, CalendarModel theCalendarModel){
		this.theCalendarView = theCalendarView;
		this.theCalendarModel = theCalendarModel;
		
		theCalendarView.setLabelsNames(theCalendarModel.getDayNames());
		theCalendarView.createYearSelectComboBox(theCalendarModel.getYears());
		theCalendarView.createMonthSelectComboBox(theCalendarModel.getMonthsNames());
		theCalendarView.addYearSelectComboBoxListener(new YearComboBoxListener(this.theCalendarView, this.theCalendarModel, this.theEventManager));
		theCalendarView.addMonthSelectComboBoxListener(new MonthComboBoxListener(this.theCalendarView, this.theCalendarModel, this.theEventManager));
		theCalendarView.createAddEventButton();
		theCalendarView.addAddEventButtonListener(new AddEventButtonListener(this.theCalendarView, this.theEventManager));
		theCalendarView.createShowEventsButton();
		theCalendarView.addShowEventsButtonListener(new ShowEventsListener(this.theEventManager, this.theCalendarView));
		theCalendarView.createRemoveOlderThanButton();
		theCalendarView.addRemoveOlderThanButtonListener(new RemoveOlderThanListener(this.theEventManager, this.theCalendarView));
		
		theCalendarView.createImportFromXMLButton();
		theCalendarView.addImportFromXMLButtonListener(new ImportExportXMLListener(this.theEventManager, ImportExportChooser.IMPORT));
		theCalendarView.createExportToXMLButton();
		theCalendarView.addExportToXMLButtonListener(new ImportExportXMLListener(this.theEventManager, ImportExportChooser.EXPORT));
		
		theCalendarView.createExportToICSButton();
		theCalendarView.addExportToICSButtonListener(new ExportToICSListener(this.theEventManager));
		
		int year = theCalendarView.getYearSelectComboBoxSelectedItem();
		int month = theCalendarView.getMonthSelectComboBoxSelectedItem();
		

		theCalendarView.createButtonFielsGrid();
		theCalendarView.addDayButtonsListener(new DayButtonsListener(this.theCalendarView, this.theEventManager));
		
		theCalendarView.updateButtonFielsGrid(theCalendarModel.updateCalendar(year, month));
		theCalendarView.updateEventDays(
				theEventManager.getEventsByYearAndMonth(
						theCalendarView.getYearSelectComboBoxSelectedItem(),
						theCalendarView.getMonthSelectComboBoxSelectedItem()));
	}
	
}
