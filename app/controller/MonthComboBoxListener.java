package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.KalendarzModel;
import view.KalendarzView;

public class MonthComboBoxListener implements ActionListener{
	private KalendarzView theKalendarzView = new KalendarzView();
	private KalendarzModel theKalendarzModel = new KalendarzModel();
	
	MonthComboBoxListener(KalendarzView theKalendarzView, KalendarzModel theKalendarzModel){
		this.theKalendarzView = theKalendarzView;
		this.theKalendarzModel = theKalendarzModel;
	}
	
	public void actionPerformed(ActionEvent e) {
		int year = theKalendarzView.getYearSelectComboBoxSelectedItem();
		int month = theKalendarzView.getMonthSelectComboBoxSelectedItem();
		theKalendarzView.updateButtonFielsGrid(theKalendarzModel.updateCalendar(year, month));
	}
}
