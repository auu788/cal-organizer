package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;
import javax.swing.JTextField;

public class Settings extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel settingsLabel, alarmSoundLabel, dbFileLabel;
	private JTextField alarmSoundPath, dbFilePath;
	private JButton alarmButton, dbButton, confirmButton, cancelButton;

	public Settings() {
		setBounds(400, 200, 300, 270);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Ustawienia");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		settingsLabel = new JLabel("Ustawienia");
		settingsLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		settingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		settingsLabel.setBounds(10, 11, 264, 14);
		contentPanel.add(settingsLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 264, 2);
		contentPanel.add(separator);
		
		alarmSoundLabel = new JLabel("D\u017Awi\u0119k alarmu");
		alarmSoundLabel.setHorizontalAlignment(SwingConstants.CENTER);
		alarmSoundLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		alarmSoundLabel.setBounds(10, 58, 264, 14);
		contentPanel.add(alarmSoundLabel);
		
		dbFileLabel = new JLabel("Domy\u015Blny plik wydarze\u0144");
		dbFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dbFileLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		dbFileLabel.setBounds(10, 126, 264, 14);
		contentPanel.add(dbFileLabel);
		
		dbFilePath = new JTextField();
		dbFilePath.setBounds(10, 151, 234, 20);
		contentPanel.add(dbFilePath);
		dbFilePath.setColumns(10);
		
		alarmButton = new JButton("");
		alarmButton.setBounds(254, 151, 20, 20);
		contentPanel.add(alarmButton);
		
		alarmSoundPath = new JTextField();
		alarmSoundPath.setColumns(10);
		alarmSoundPath.setBounds(10, 83, 234, 20);
		contentPanel.add(alarmSoundPath);
		
		dbButton = new JButton("");
		dbButton.setBounds(254, 83, 20, 20);
		contentPanel.add(dbButton);
		
		confirmButton = new JButton("Zatwierd\u017A");
		confirmButton.setBounds(36, 197, 95, 23);
		contentPanel.add(confirmButton);
		
		cancelButton = new JButton("Anuluj");
		cancelButton.setBounds(155, 197, 95, 23);
		contentPanel.add(cancelButton);
	}
	
	public void addDBFilePathButtonListener(ActionListener listenForDBFilePathButton) {
		dbButton.addActionListener(listenForDBFilePathButton);
	}
	
	public void addAlarmFilePathButtonListener(ActionListener listenForAlarmFilePathButton) {
		alarmButton.addActionListener(listenForAlarmFilePathButton);
	}
	
	public void addConfirmButtonListener(ActionListener listenForConfirmButton) {
		confirmButton.addActionListener(listenForConfirmButton);
	}
	
	public void addCancelButtonListener(ActionListener listenForCancelButton) {
		cancelButton.addActionListener(listenForCancelButton);
	}
	
	public String getDBFilePath() {
		return dbFilePath.getText();
	}
	
	public String getAlarmFilePath() {
		return alarmSoundPath.getText();
	}
	
	public void setDBFilePath(String path) {
		dbFilePath.setText(path);
	}
	
	public void setAlarmFilePath(String path) {
		alarmSoundPath.setText(path);
	}
}
