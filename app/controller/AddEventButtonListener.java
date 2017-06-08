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

/**
 * Klasa implementuj¹ca listener dla akcji "Dodaj wydarzenie". Zajmuje siê otwarciem okienka dialogowego.
 */
public class AddEventButtonListener implements ActionListener {
	private boolean isOpen = false;
	private CalendarView theCalendarView;
	private EventManager eventManager;
	
	/**
	 * Konstruktor inicjalizuj¹cy listener.
	 * @param theCalendarView obiekt widoku g³ównego okna kalenarza
	 * @param eventManager obiekt menad¿era wydarzeñ
	 * @see CalendarView
	 * @see EventManager
	 */
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