import java.awt.EventQueue;
import controller.CalendarController;
import model.CalendarModel;
import view.CalendarView;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarView theCalendarView = new CalendarView();
					CalendarModel theCalendarModel = new CalendarModel();
					theCalendarView.setVisible(true);
					CalendarController theCalendarController = new CalendarController(theCalendarView, theCalendarModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
