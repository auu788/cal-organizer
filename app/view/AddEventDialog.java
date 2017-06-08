package view;


import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.text.MaskFormatter;

/**
 * Klasa odpowiadaj¹ca za interfejs graficzny okienka dialogowego "Dodaj wydarzenie".
 * @author auu78
 *
 */
public class AddEventDialog extends JDialog {
	private JLabel nameLabel, placeLabel, dateLabel, hourLabel, alarmLabel;
	private JTextField placeTxtField;
	private JTextArea nameTxtField;
	private JFormattedTextField dateTxtField, hourTxtField;
	private JComboBox alarmComboBox;
	private JButton confirmButton;
	
	/**
	 * Konstruktor inicjalizuj¹cy initefejs graficzny okienka "Dodaj wydarzenie", ustawiaj¹cy parametry poszczególnych elementów.
	 */
	public AddEventDialog() {
		setBounds(400, 200, 450, 300);
		setTitle("Tworzenie nowego wydarzenia");
		getContentPane().setLayout(null);
		
		nameLabel = new JLabel("Nazwa");
		nameLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(nameLabel);
		
		nameTxtField = new JTextArea();
		nameTxtField.setBounds(59, 8, 365, 86);
		nameTxtField.setLineWrap(true);
		nameTxtField.setWrapStyleWord(true);
		nameTxtField.setBorder(new JTextField().getBorder());
		getContentPane().add(nameTxtField);
		nameTxtField.setColumns(10);
		
		placeLabel = new JLabel("Miejsce");
		placeLabel.setBounds(10, 108, 46, 14);
		getContentPane().add(placeLabel);
		
		placeTxtField = new JTextField();
		placeTxtField.setBounds(59, 105, 365, 20);
		getContentPane().add(placeTxtField);
		
		dateLabel = new JLabel("Data");
		dateLabel.setBounds(10, 139, 46, 14);
		getContentPane().add(dateLabel);
		
		dateTxtField = new JFormattedTextField(formatter("##-##-####"));
		dateTxtField.setBounds(59, 136, 99, 20);
		getContentPane().add(dateTxtField);
		
		hourLabel = new JLabel("Godzina");
		hourLabel.setBounds(271, 139, 46, 14);
		getContentPane().add(hourLabel);
		
		hourTxtField = new JFormattedTextField(formatter("##:##"));
		hourTxtField.setBounds(325, 136, 99, 20);
		getContentPane().add(hourTxtField);
		
		confirmButton = new JButton("Stw\u00F3rz wydarzenie");
		confirmButton.setBounds(138, 216, 163, 34);
		getContentPane().add(confirmButton);
		
		getRootPane().setDefaultButton(confirmButton);
	}
	
	/**
	 * Wymusza formatowanie wpisanynej daty oraz godziny w taki sposób: dd-mm-yyyy oraz hh:mm.
	 * @param s
	 * @return
	 */
	private MaskFormatter formatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("Zle formatowanie: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
	
	/**
	 * Pobiera datê wydarzania.
	 * @return data wydarznia
	 */
	public String getEventDate() {
		return dateTxtField.getText() + " " + hourTxtField.getText();
	}
	
	/**
	 * Pobiera nazwê lub opis wydarzenia.
	 * @return nazwa lub opis wydarzenia
	 */
	public String getEventName() {
		return nameTxtField.getText();
	}
	
	/**
	 * Pobiera miejsce wydarzenia.
	 * @return miejsce wydarzenia
	 */
	public String getEventPlace() {
		return placeTxtField.getText();
	}
	
	/**
	 * Pobiera indeks wybranego alarmu.
	 * @return indeks wybranego alarmu
	 */
	public int getEventAlarm() {
		return alarmComboBox.getSelectedIndex();
	}
	
	/**
	 * Dodaje listener na przycisk potwierdzaj¹cy dodanie wydarzenia.
	 * @param listenForConfirmation listener na przycisk
	 */
	public void addConfirmListener(ActionListener listenForConfirmation) {
		confirmButton.addActionListener(listenForConfirmation);
	}
	
	/**
	 * Dodaje rozwijane menu z mo¿liwymi czasami alarmów.
	 * @param alarmNames tablica nazw czasów alarmów
	 */
	public void setAlarmField(String[] alarmNames) {
		alarmLabel = new JLabel("Alarm");
		alarmLabel.setBounds(10, 167, 46, 14);
		getContentPane().add(alarmLabel);
		
		alarmComboBox = new JComboBox(alarmNames);
		alarmComboBox.setBounds(59, 164, 99, 20);
		getContentPane().add(alarmComboBox);
	}
}
