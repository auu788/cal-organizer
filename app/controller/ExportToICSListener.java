package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.EventManager;
import model.ICSManager;
import model.ImportExportChooser;

/**
 * Klasa implementuj�ca listener dla przycisku "Eksportuj do iCalendar (ICS)".
 * Tworzy okienko dialogowe pozwalaj�ce wybra� miejsce i plik, do kt�rego maj� by� eksportowane wydarzenia.
 */
public class ExportToICSListener implements ActionListener{
	private EventManager eventManager;
	
	/**
	 * Konstruktor inicjalizuj�cy obiekt listenera.
	 * @param eventManager menad�er wydarze�
	 */
	public ExportToICSListener(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (this.eventManager.getEventList().size() == 0) {
			JOptionPane.showMessageDialog(null,
				    "Brak wydarze� do wyeksportowania!",
				    "Ostrze�enie!",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		JFileChooser fileChooser = new JFileChooser();
		File file;

	    int retrival = fileChooser.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	file = fileChooser.getSelectedFile();
	    	String filePath = file.toString();
	    	
	    	if (!filePath.endsWith(".ics"))
	    		filePath += ".ics";
	    	
	    	eventManager.exportToICS(new File(filePath));
	    	
	    	JOptionPane.showMessageDialog(fileChooser,
				    "Pomy�lnie wyeksportowano wydarzenia!",
				    "Informacja!",
				    JOptionPane.INFORMATION_MESSAGE);
	    }
	}

}
