package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class RemoveOlderThanDialog extends JDialog {
	private JLabel removeOlderThanLabel;
	private JTextField dateField;
	private JButton confirmButton;
	
	public RemoveOlderThanDialog() {
		setBounds(100, 100, 250, 200);
		getContentPane().setLayout(null);
		
		removeOlderThanLabel = new JLabel("Usu\u0144 wydarzenia starsze ni\u017C...");
		removeOlderThanLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		removeOlderThanLabel.setHorizontalAlignment(SwingConstants.CENTER);
		removeOlderThanLabel.setBounds(10, 11, 214, 40);
		getContentPane().add(removeOlderThanLabel);
		
		dateField = new JTextField();
		dateField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dateField.setBounds(35, 50, 167, 40);
		getContentPane().add(dateField);
		dateField.setColumns(10);
		
		confirmButton = new JButton("USU\u0143");
		confirmButton.setBounds(65, 116, 102, 34);
		getContentPane().add(confirmButton);
	}
	
	public String getDate() {
		return dateField.getText();
	}
	
	public void addConfirmListener(ActionListener listenForConfirmation) {
		confirmButton.addActionListener(listenForConfirmation);
	}
}
