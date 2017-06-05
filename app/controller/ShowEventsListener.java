package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import model.EventManager;

import view.CalendarView;
import view.EventTable;

public class ShowEventsListener implements ActionListener {
	EventTable eventTable;
	EventManager eventManager;
	CalendarView calendarView;
	
	ShowEventsListener(EventManager eventManager, CalendarView calendarView) {
		this.eventManager = eventManager;
		this.calendarView = calendarView;
	}
	
	public void actionPerformed(ActionEvent e) {
		eventTable = new EventTable(this.eventManager);
		eventTable.addSearchButtonListener(new SearchListener(this.eventTable, 
				eventTable.getSearchButton(), eventTable.getClearSearchButton()));
		
		eventTable.addRemoveSelectedListener(new RemoveSelectedListener(this.eventTable, this.eventManager, this.calendarView));
		eventTable.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		eventTable.setVisible(true);
		
	}

}
