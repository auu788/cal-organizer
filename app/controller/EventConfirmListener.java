package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.AddEventDialog;

public class EventConfirmListener implements ActionListener {
	AddEventDialog dialog;
	
	EventConfirmListener(AddEventDialog dialog) {
		this.dialog = dialog;
	};
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Tworzenie nowego wydarzenia, tylko wypisanie... Chyba trzeba po��czy� ze sob� dat� i godzin�");
		System.out.println("Data: " + dialog.getEventDate());
		System.out.println("Godzina: " + dialog.getEventHour());
		System.out.println("Nazwa: " + dialog.getEventName());
		System.out.println("Miejsce: " + dialog.getEventPlace());
		dialog.dispose();
	}
}
