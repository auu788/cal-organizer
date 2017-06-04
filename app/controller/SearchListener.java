package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.EventTable;

public class SearchListener implements ActionListener {
	private EventTable eventTable;
	private JButton searchButton, clearSearchButton;
	private String searchPhrase, dateFrom, dateTo;
	
	
	public SearchListener(EventTable eventTable, JButton searchButton, JButton clearSearchButton) {
		this.eventTable = eventTable;
		this.searchButton = searchButton;
		this.clearSearchButton = clearSearchButton;

	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == searchButton) {
			searchPhrase = eventTable.getSearchText();
			dateFrom = eventTable.getDateFrom();
			dateTo = eventTable.getDateTo();
		} else {
			searchPhrase = "";
			dateFrom = "";
			dateTo = "";
			eventTable.clearFields();
		}
		
		eventTable.getTableModel().updateTableData(searchPhrase, dateFrom, dateTo);
		eventTable.updateTable();
	}
}
