package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class EventInfo extends JDialog {
	
	public EventInfo() {
		setBounds(100, 100, 450, 300);
		setTitle("Informacje o wydarzeniu");
		getContentPane().setLayout(null);
		
		JLabel nameLabel = new JLabel("Info o wydarzeniu");
		nameLabel.setBounds(10, 11, 46, 14);
		getContentPane().add(nameLabel);
		
		// TODO
	}
}
