package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.EventTable;

/**
 * Klasa implementuje listener dla przycisku "Szukaj" dostêpny z okna "Wszystkie wydarzenia".
 * Pozwala na przefiltrowanie wydarzeñ na podstawie szukanej frazy. 
 * Filtruje atrybuty wydarzenia - nazwa i miejsce wydarzenia.
 */
public class SearchListener implements ActionListener {
	private EventTable eventTable;
	private JButton searchButton, clearSearchButton;
	private String searchPhrase, dateFrom, dateTo;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt listenera.
	 * @param eventTable okno z wszystkimi wydarzeniami
	 * @param searchButton przycisk "Szukaj"
	 * @param clearSearchButton przycisk "Wyczyœæ filtry"
	 */
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
		eventTable.getTableModel().fireTableDataChanged();
	}
}
