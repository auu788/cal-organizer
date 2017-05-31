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
	private JTextField nameTxtField, placeTxtField;
	private JFormattedTextField dateTxtField, hourTxtField;
	private JComboBox alarmComboBox;
	private JButton confirmButton, cancelButton;
	
	public AddEventDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		nameLabel = new JLabel("Nazwa");
		nameLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(nameLabel);
		
		nameTxtField = new JTextField();
		nameTxtField.setBounds(59, 8, 365, 20);
		getContentPane().add(nameTxtField);
		nameTxtField.setColumns(10);
		
		placeLabel = new JLabel("Miejsce");
		placeLabel.setBounds(10, 73, 46, 14);
		getContentPane().add(placeLabel);
		
		placeTxtField = new JTextField();
		placeTxtField.setBounds(59, 73, 365, 20);
		getContentPane().add(placeTxtField);
		
		dateLabel = new JLabel("Data");
		dateLabel.setBounds(10, 133, 46, 14);
		getContentPane().add(dateLabel);
		
		dateTxtField = new JFormattedTextField(formatter("##-##-####"));
		dateTxtField.setBounds(59, 130, 99, 20);
		getContentPane().add(dateTxtField);
		
		hourLabel = new JLabel("Godzina");
		hourLabel.setBounds(269, 133, 46, 14);
		getContentPane().add(hourLabel);
		
		hourTxtField = new JFormattedTextField(formatter("##:##"));
		hourTxtField.setBounds(325, 130, 99, 20);
		getContentPane().add(hourTxtField);
		
		alarmLabel = new JLabel("Alarm");
		alarmLabel.setBounds(10, 182, 46, 14);
		getContentPane().add(alarmLabel);
		
		alarmComboBox = new JComboBox();
		alarmComboBox.setBounds(59, 179, 99, 20);
		getContentPane().add(alarmComboBox);
		
		confirmButton = new JButton("Stw\u00F3rz wydarzenie");
		confirmButton.setBounds(69, 216, 163, 34);
		getContentPane().add(confirmButton);
		
		cancelButton = new JButton("Anuluj");
		cancelButton.setBounds(232, 216, 163, 34);
		getContentPane().add(cancelButton);
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
	
	public Date getEventDate() {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = formatter.parse(dateTxtField.getText());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return date;
	}
	
	public Date getEventHour() {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		Date time = null;
		try {
			time = formatter.parse(hourTxtField.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return time;
	}
	
	public String getEventName() {
		return nameTxtField.getText();
	}
	
	public String getEventPlace() {
		return placeTxtField.getText();
	}
	
	public Integer getEventAlarm() {
		return alarmComboBox.getSelectedIndex();
	}
	
	public void addConfirmListener(ActionListener listenForConfirmation) {
		confirmButton.addActionListener(listenForConfirmation);
	}
}
