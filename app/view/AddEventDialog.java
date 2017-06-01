package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.text.MaskFormatter;

public class AddEventDialog extends JDialog {
	private JLabel nameLabel, placeLabel, dateLabel, hourLabel, alarmLabel;
	private JTextField placeTxtField;
	private JTextArea nameTxtField;
	private JFormattedTextField dateTxtField, hourTxtField;
	private JComboBox alarmComboBox;
	private JButton confirmButton, cancelButton;
	
	public AddEventDialog() {
		setBounds(100, 100, 450, 300);
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
	}
	
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
	
	public String getEventDate() {
		return dateTxtField.getText() + " " + hourTxtField.getText();
	}
	
	public String getEventName() {
		return nameTxtField.getText();
	}
	
	public String getEventPlace() {
		return placeTxtField.getText();
	}
	
	public int getEventAlarm() {
		return alarmComboBox.getSelectedIndex();
	}
	
	public void addConfirmListener(ActionListener listenForConfirmation) {
		confirmButton.addActionListener(listenForConfirmation);
	}
	
	public void setAlarmField(String[] alarmNames) {
		alarmLabel = new JLabel("Alarm");
		alarmLabel.setBounds(10, 167, 46, 14);
		getContentPane().add(alarmLabel);
		
		alarmComboBox = new JComboBox(alarmNames);
		alarmComboBox.setBounds(59, 164, 99, 20);
		getContentPane().add(alarmComboBox);
	}
}
