package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.GridLayout;

public class EventInfo extends JDialog {
	int panelY;
	int panelSize;
	
	JPanel panel;
	JScrollPane scrollPanel;
	
	
	public EventInfo(String date) {
		this.panelSize = 40;
		this.panelY = 40;
		
		setBounds(0, 0, 360, 230);
		setTitle("Wydarzenia - " + date);
		setLayout(null);
		setResizable(false);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, this.panelSize));
        panel.setLayout(null);
        
        JLabel nameLabel = new JLabel(date);
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(10, 0, 330, 40);
		panel.add(nameLabel);
		
		scrollPanel = new JScrollPane(panel);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(15);
        scrollPanel.setBounds(0, 0, 355, 200);
        
        
		getContentPane().add(scrollPanel);
	}
	
	public void addEvent(String name, String place, String hour, String alarm) {
		JPanel eventPanel = new JPanel();
		eventPanel.setBounds(0, panelY, 350, 120);
		eventPanel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 0, 350, 3);
		eventPanel.add(separator);
		
		JLabel hourLabel = new JLabel("Godzina:");
		hourLabel.setVerticalAlignment(SwingConstants.TOP);
		hourLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		hourLabel.setBounds(10, 11, 65, 14);
		eventPanel.add(hourLabel);
		
		JTextField hourTextField = new JTextField();
		hourTextField.setText(hour);
		hourTextField.setEditable(false);
		hourTextField.setBackground(null);
		hourTextField.setBorder(null);
		hourTextField.setBounds(67, 9, 45, 20);
		eventPanel.add(hourTextField);
		hourTextField.setColumns(10);
		
		JLabel alarmLabel = new JLabel("Alarm:");
		alarmLabel.setVerticalAlignment(SwingConstants.TOP);
		alarmLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		alarmLabel.setBounds(159, 11, 46, 20);
		eventPanel.add(alarmLabel);
		
		JTextPane alarmTextField = new JTextPane();
		alarmTextField.setText(alarm);
		alarmTextField.setEditable(false);
		alarmTextField.setBackground(null);
		alarmTextField.setBorder(null);
		alarmTextField.setBounds(204, 11, 136, 20);
		eventPanel.add(alarmTextField);
		
		JLabel placeLabel = new JLabel("Miejsce:");
		placeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		placeLabel.setBounds(10, 35, 50, 14);
		eventPanel.add(placeLabel);
		
		JTextField placeTextField = new JTextField();
		placeTextField.setText(place);
		placeTextField.setEditable(false);
		placeTextField.setBackground(null);
		placeTextField.setBorder(null);
		placeTextField.setBounds(67, 33, 223, 20);
		eventPanel.add(placeTextField);
		placeTextField.setColumns(10);
		
		JTextArea nameTextArea = new JTextArea();
		nameTextArea.setWrapStyleWord(true);
		nameTextArea.setEditable(false);
		nameTextArea.setLineWrap(true);
		nameTextArea.setBackground(null);
		nameTextArea.setBorder(null);
		nameTextArea.setText(name);
		nameTextArea.setBounds(10, 60, 330, 49);
		eventPanel.add(nameTextArea);
		
		panel.add(eventPanel);
		
		this.panelY += 120;
		this.panelSize += 120;
		panel.setPreferredSize(new Dimension(300, this.panelSize));
		scrollPanel.getVerticalScrollBar().setValue(scrollPanel.getVerticalScrollBar().getMinimum());
		
	}
}