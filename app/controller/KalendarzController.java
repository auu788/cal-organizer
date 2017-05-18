package controller;

import model.KalendarzModel;
import view.KalendarzView;

public class KalendarzController {
	private KalendarzView theKalendarzView = new KalendarzView();
	private KalendarzModel theKalendarzModel = new KalendarzModel();
	
	public KalendarzController(KalendarzView theKalendarzView, KalendarzModel theKalendarzModel){
		this.theKalendarzView = theKalendarzView;
		this.theKalendarzModel = theKalendarzModel;

		theKalendarzView.setLabelsNames(theKalendarzModel.getDayNames());
		theKalendarzView.crateYearSelectComboBox(theKalendarzModel.getYears());
		theKalendarzView.crateMonthSelectComboBox(theKalendarzModel.getMonthsNames());
		theKalendarzView.addYearSelectComboBoxListener(new YearComboBoxListener(this.theKalendarzView, this.theKalendarzModel));
		theKalendarzView.addMonthSelectComboBoxListener(new MonthComboBoxListener(this.theKalendarzView, this.theKalendarzModel));
		
		int year = theKalendarzView.getYearSelectComboBoxSelectedItem();
		int month = theKalendarzView.getMonthSelectComboBoxSelectedItem();

		theKalendarzView.createButtonFielsGrid();
		theKalendarzView.updateButtonFielsGrid(theKalendarzModel.updateCalendar(year, month));
	}
	
}
