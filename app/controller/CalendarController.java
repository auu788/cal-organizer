package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.AlarmChecker;
import model.CalendarModel;
import model.DBManager;
import model.EventManager;
import model.ImportExportChooser;
import model.XMLManager;
import view.About;
import view.AlarmDialog;
import view.CalendarView;

public class CalendarController {
	private EventManager theEventManager = new EventManager();
	private CalendarView theCalendarView = new CalendarView();
	private CalendarModel theCalendarModel = new CalendarModel();
	
	public CalendarController(final CalendarView theCalendarView, CalendarModel theCalendarModel){
		this.theCalendarView = theCalendarView;
		this.theCalendarModel = theCalendarModel;
		
		AlarmChecker ac = new AlarmChecker(this.theEventManager);

		theCalendarView.setLabelsNames(theCalendarModel.getDayNames());
		theCalendarView.createYearSelectComboBox(theCalendarModel.getYears());
		theCalendarView.createMonthSelectComboBox(theCalendarModel.getMonthsNames());
		
		
		theCalendarView.addYearSelectComboBoxListener(new YearComboBoxListener(this.theCalendarView, this.theCalendarModel, this.theEventManager));
		theCalendarView.addMonthSelectComboBoxListener(new MonthComboBoxListener(this.theCalendarView, this.theCalendarModel, this.theEventManager));
		
		theCalendarView.createAddEventButton();
		theCalendarView.addAddEventButtonListener(new AddEventButtonListener(this.theCalendarView, this.theEventManager));
		theCalendarView.addShowEventsButtonListener(new ShowEventsListener(this.theEventManager, this.theCalendarView));
		theCalendarView.addRemoveOlderThanButtonListener(new RemoveOlderThanListener(this.theEventManager, this.theCalendarView));
		
		theCalendarView.addImportFromXMLButtonListener(new ImportExportXMLListener(this.theEventManager, this.theCalendarView, ImportExportChooser.IMPORT));
		theCalendarView.addExportToXMLButtonListener(new ImportExportXMLListener(this.theEventManager, this.theCalendarView, ImportExportChooser.EXPORT));		
		theCalendarView.addExportToICSButtonListener(new ExportToICSListener(this.theEventManager));
		theCalendarView.addImportFromDBButtonListener(new ImportExportDBListener(this.theEventManager, this.theCalendarView, ImportExportChooser.IMPORT));
		theCalendarView.addExportToDBButtonListener(new ImportExportDBListener(this.theEventManager, this.theCalendarView, ImportExportChooser.EXPORT));
		
		theCalendarView.addSettingsItemListener(new SettingsListener(ac, this.theEventManager));
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
		
		theCalendarView.markTodaysDay(theCalendarModel.getTodaysDay(year, month));

	}
	
}
