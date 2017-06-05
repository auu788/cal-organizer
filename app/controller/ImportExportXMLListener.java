package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.EventManager;
import model.ImportExportChooser;

public class ImportExportXMLListener implements ActionListener {
	EventManager eventManager;
	ImportExportChooser choose;
	
	public ImportExportXMLListener(EventManager eventManager, ImportExportChooser choose) {
		this.eventManager = eventManager;
		this.choose = choose;
	}

	public void actionPerformed(ActionEvent e) {
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
	            System.out.println(file);
			}
		} else {
			
		    int retrival = fileChooser.showSaveDialog(null);
		    if (retrival == JFileChooser.APPROVE_OPTION) {
		    	file = fileChooser.getSelectedFile();
		    	String filePath = file.toString();
		    	
		    	if (!filePath.endsWith(".xml"))
		    		filePath += ".xml";
		    	
		    	eventManager.exportToXML(new File(filePath));
		    	
		    }
			
		}

	}
	
	
	
}
