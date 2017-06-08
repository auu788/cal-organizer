package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.EventManager;
import model.OrganizerTableModel;

import view.CalendarView;
import view.EventTable;

/**
 * Klasa implementuje listener dla przycisku "Wyœwietl wydarzenia".
 * Pojawia siê wówczas okno ze wszystkimi wydarzeniami w formie tabeli oraz filtrami daty, frazy oraz mo¿liwoœci¹ usuniêcia zaznaczonych wydarzeñ.
 */
public class ShowEventsListener implements ActionListener {
	EventTable eventTable;
	EventManager eventManager;
	CalendarView calendarView;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt listenera.
	 * @param eventManager menad¿er wydarzeñ
	 * @param calendarView g³ówne okno kalendarza
	 */
	ShowEventsListener(EventManager eventManager, CalendarView calendarView) {
		this.eventManager = eventManager;
		this.calendarView = calendarView;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (this.eventManager.getEventList().size() == 0) {
			JOptionPane.showMessageDialog(this.calendarView,
				    "Brak wydarzeñ do wyœwietlenia.",
				    "Uwaga!",
				    JOptionPane.WARNING_MESSAGE);
		} else {
			
			OrganizerTableModel organizerTableModel = new OrganizerTableModel(this.eventManager);
			eventTable = new EventTable(organizerTableModel);
			eventTable.addSearchButtonListener(new SearchListener(this.eventTable, 
					eventTable.getSearchButton(), eventTable.getClearSearchButton()));
			
			eventTable.addRemoveSelectedListener(new RemoveSelectedListener(this.eventTable, this.eventManager, this.calendarView));
			eventTable.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			eventTable.setVisible(true);
		}
	}

}
