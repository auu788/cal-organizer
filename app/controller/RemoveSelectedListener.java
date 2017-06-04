package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

import model.EventManager;

import view.EventTable;

public class RemoveSelectedListener implements ActionListener {
	private EventTable eventTable;
	private EventManager eventManager;
	
	public RemoveSelectedListener(EventTable eventTable, EventManager eventManager) {
		this.eventTable = eventTable;
		this.eventManager = eventManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		int[] selectedRows = eventTable.getTable().getSelectedRows();
		
		for (int row : selectedRows) {
			String id = (String) eventTable.getTableModel().getValueAt(row, 0);

			eventManager.removeById(UUID.fromString(id));
		}
		
		eventTable.getTableModel().updateTableData("", "", "");
		eventTable.getTableModel().fireTableDataChanged();

	}

}
