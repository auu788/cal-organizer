package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.JOptionPane;

import model.EventManager;

import view.CalendarView;
import view.EventTable;

/**
 * Klasa implementuj�ca listener dla przycisku "Usu� zaznaczone" dost�pny z okna "Wszystkie wydarzenia".
 * W oknie tym mo�na zaznacza� wiersze z tabeli, a przycisk ten pozwala na usuni�cie zaznaczonych wydarze�.
 */
public class RemoveSelectedListener implements ActionListener {
	private EventTable eventTable;
	private EventManager eventManager;
	private CalendarView calendarView;
	
	/**
	 * Konstruktor inicjalizuj�cy obiekt listenera.
	 * @param eventTable okno z wszystkimi wydarzeniami
	 * @param eventManager menad�er wydarze�
	 * @param calendarView okno g��wne kalendarza
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
        int PromptResult = JOptionPane.showOptionDialog(eventTable,"Na pewno chcesz usun�� zaznaczone elementy?","Ostrze�enie",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,confirmButtons,confirmButtons[1]);
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
