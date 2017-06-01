package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import model.AddEventModel;
import model.EventManager;

import view.AddEventDialog;
import view.CalendarView;

public class AddEventButtonListener implements ActionListener {
	private boolean isOpen = false;
	private CalendarView theCalendarView;
	private EventManager eventManager;
	
	AddEventButtonListener(CalendarView theCalendarView, EventManager eventManager) {
		this.theCalendarView = theCalendarView;
		this.eventManager = eventManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		final AddEventDialog dialog;
		AddEventModel addEventModel;
		
		if (isOpen == false) {
			this.isOpen = true;
			dialog = new AddEventDialog();
			addEventModel = new AddEventModel();
			dialog.setAlarmField(addEventModel.getAlarmNames());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.addConfirmListener(new EventConfirmListener(dialog, addEventModel, eventManager, theCalendarView));
			
			// Zapobiega dwukrotnemu w³¹czeniu okienka
			dialog.addWindowListener( new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			        isOpen = false;
			    }
			});
			
			dialog.setVisible(true);
		}
	}

}