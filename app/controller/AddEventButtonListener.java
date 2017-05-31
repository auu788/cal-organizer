package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import view.AddEventDialog;

public class AddEventButtonListener implements ActionListener {
	private boolean isOpen = false;
	
	AddEventButtonListener() {};
	
	public void actionPerformed(ActionEvent e) {
		final AddEventDialog dialog;
		if (isOpen == false) {
			this.isOpen = true;
			dialog = new AddEventDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.addConfirmListener(new EventConfirmListener(dialog));
			
			// Zapobiega dwukrotnemu w³¹czeniu okienka
			dialog.addWindowListener( new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			    	dialog.getEventHour();
			        isOpen = false;
			    }
			});
			
			dialog.setVisible(true);
		}
	}

}