import java.awt.EventQueue;

import controller.KalendarzController;
import model.KalendarzModel;
import view.KalendarzView;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KalendarzView theKalendarzView = new KalendarzView();
					KalendarzModel theKalendarzModel = new KalendarzModel();
					theKalendarzView.setVisible(true);
					KalendarzController theKalendarzController = new KalendarzController(theKalendarzView, theKalendarzModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
}
