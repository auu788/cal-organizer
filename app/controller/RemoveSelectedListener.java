package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JOptionPane;

import model.EventManager;

import view.CalendarView;
import view.EventTable;

/**
 * Klasa implementuj¹ca listener dla przycisku "Usuñ zaznaczone" dostêpny z okna "Wszystkie wydarzenia".
 * W oknie tym mo¿na zaznaczaæ wiersze z tabeli, a przycisk ten pozwala na usuniêcie zaznaczonych wydarzeñ.
 */
public class RemoveSelectedListener implements ActionListener {
	private EventTable eventTable;
	private EventManager eventManager;
	private CalendarView calendarView;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt listenera.
	 * @param eventTable okno z wszystkimi wydarzeniami
	 * @param eventManager menad¿er wydarzeñ
	 * @param calendarView okno g³ówne kalendarza
	 * @see EventTable
	 * @see EventManager
	 * @see CalendarView
	 */
	public RemoveSelectedListener(EventTable eventTable, EventManager eventManager, CalendarView calendarView) {
		this.eventTable = eventTable;
		this.eventManager = eventManager;
		this.calendarView = calendarView;
	}
	
	public void actionPerformed(ActionEvent e) {
		int[] selectedRows = eventTable.getTable().getSelectedRows();
		
		if (selectedRows.length == 0) {
			return;
		}
		
		String confirmButtons[] = {"Tak","Nie"};
        int PromptResult = JOptionPane.showOptionDialog(eventTable,"Na pewno chcesz usun¹æ zaznaczone elementy?","Ostrze¿enie",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,confirmButtons,confirmButtons[1]);
        if(PromptResult!=JOptionPane.YES_OPTION)
        {
            return;
        }
				
		for (int row : selectedRows) {
			String id = (String) eventTable.getTableModel().getValueAt(row, 0);

			eventManager.removeById(UUID.fromString(id));
		}
		
		eventTable.getTableModel().updateTableData("", "", "");
		eventTable.getTableModel().fireTableDataChanged();
		calendarView.updateEventDays(
				eventManager.getEventsByYearAndMonth(
						calendarView.getYearSelectComboBoxSelectedItem(),
						calendarView.getMonthSelectComboBoxSelectedItem()));

	}

}
