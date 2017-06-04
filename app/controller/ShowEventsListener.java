package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import model.EventManager;

import view.EventTable;

public class ShowEventsListener implements ActionListener {
	EventTable eventTable;
	EventManager eventManager;
	ShowEventsListener(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		eventTable = new EventTable(this.eventManager);
		eventTable.addSearchButtonListener(new SearchListener(this.eventTable, 
				eventTable.getSearchButton(), eventTable.getClearSearchButton()));
		
		eventTable.addRemoveSelectedListener(new RemoveSelectedListener(this.eventTable, this.eventManager));
		eventTable.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		eventTable.setVisible(true);
		
	}

}
