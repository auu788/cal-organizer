package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.CalendarView;

import model.EventManager;
import model.ImportExportChooser;

/**
 * Klasa implementuj¹ca listener dla przycisków "Importuj / Eksportuj do formatu XML".
 * Tworzy okienko dialogowe pozwalaj¹ce wybraæ miejsce i plik, do którego maj¹ byæ eksportowane wydarzenia.
 */
public class ImportExportXMLListener implements ActionListener {
	EventManager eventManager;
	ImportExportChooser choose;
	CalendarView calendarView;
	
	/**
	 * Konstruktor inicjalizuj¹cy obiekt listenera.
	 * @param eventManager menad¿er wydarzeñ
	 * @param calendarView g³ówne okno kalendarza
	 * @param choose enumerator pozwalaj¹cy wybraæ: import / eksport
	 */
	public ImportExportXMLListener(EventManager eventManager, CalendarView calendarView, ImportExportChooser choose) {
		this.eventManager = eventManager;
		this.choose = choose;
		this.calendarView = calendarView;
	}

	public void actionPerformed(ActionEvent e) {
		if (this.eventManager.getEventList().size() == 0 && choose == ImportExportChooser.EXPORT) {
			JOptionPane.showMessageDialog(null,
				    "Brak wydarzeñ do wyeksportowania!",
				    "Ostrze¿enie!",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		JFileChooser fileChooser = new JFileChooser();
		File file;
		
		if (choose == ImportExportChooser.IMPORT) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki XML (*.xml)", "xml");
			fileChooser.addChoosableFileFilter(filter);
			fileChooser.setAcceptAllFileFilterUsed(false);
			int val = fileChooser.showOpenDialog(null);		
			
			if (val == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				
				eventManager.importFromXML(file);
				calendarView.updateEventDays(
						eventManager.getEventsByYearAndMonth(
								calendarView.getYearSelectComboBoxSelectedItem(),
								calendarView.getMonthSelectComboBoxSelectedItem()));
				
				JOptionPane.showMessageDialog(fileChooser,
					    "Pomyœlnie zaimportowano wydarzenia!",
					    "Informacja!",
					    JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			
		    int retrival = fileChooser.showSaveDialog(null);
		    if (retrival == JFileChooser.APPROVE_OPTION) {
		    	file = fileChooser.getSelectedFile();
		    	String filePath = file.toString();
		    	
		    	if (!filePath.endsWith(".xml"))
		    		filePath += ".xml";
		    	
		    	eventManager.exportToXML(new File(filePath));
		    	
		    	JOptionPane.showMessageDialog(fileChooser,
					    "Pomyœlnie wyeksportowano wydarzenia!",
					    "Informacja!",
					    JOptionPane.INFORMATION_MESSAGE);
		    	
		    }
			
		}

	}
	
	
	
}
