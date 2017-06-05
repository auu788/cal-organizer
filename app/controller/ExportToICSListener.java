package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.EventManager;
import model.ICSManager;
import model.ImportExportChooser;

public class ExportToICSListener implements ActionListener{
	private EventManager eventManager;
	
	public ExportToICSListener(EventManager eventManager) {
		this.eventManager = eventManager;
	}
	
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		File file;

	    int retrival = fileChooser.showSaveDialog(null);
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	file = fileChooser.getSelectedFile();
	    	String filePath = file.toString();
	    	
	    	if (!filePath.endsWith(".ics"))
	    		filePath += ".ics";
	    	
	    	eventManager.exportToICS(new File(filePath));
	    	
	    }
	}

}
