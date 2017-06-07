package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Klasa odpowiadaj¹ca za interfejs graficzna wyskakuj¹cego powiadomienia.
 */
public class AlarmDialog extends JDialog {
	JLabel titleLabel, placeLabel, nameLabel;
	private final JPanel contentPanel = new JPanel();
	private JTextField eventTimeTextField, placeTextField;
	private JTextField textField;
	private JTextArea nameTextArea;
	private JButton okButton;

	/**
	 * Konstruktor inicjalizuj¹cy initefejs graficzny okienka "Powiadomienie", ustawiaj¹cy parametry poszczególnych elementów.
	 */
	public AlarmDialog() {
		setTitle("Powiadomienie");
		setBounds(100, 100, 250, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		titleLabel = new JLabel("Powiadomienie ");
		titleLabel.setVerticalAlignment(SwingConstants.TOP);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setBounds(10, 11, 214, 34);
		contentPanel.add(titleLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 43, 214, 2);
		contentPanel.add(separator);
		
		eventTimeTextField = new JTextField();
		eventTimeTextField.setEditable(false);
		eventTimeTextField.setBackground(null);
		eventTimeTextField.setBorder(null);
		eventTimeTextField.setBounds(10, 56, 214, 20);
		contentPanel.add(eventTimeTextField);
		eventTimeTextField.setColumns(10);
		
		placeLabel = new JLabel("Miejsce:");
		placeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		placeLabel.setBounds(10, 87, 46, 14);
		contentPanel.add(placeLabel);
		
		placeTextField = new JTextField();
		placeTextField.setEditable(false);
		placeTextField.setBackground(null);
		placeTextField.setBorder(null);
		placeTextField.setBounds(66, 84, 158, 20);
		contentPanel.add(placeTextField);
		placeTextField.setColumns(10);
		
		nameLabel = new JLabel("Opis:");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		nameLabel.setBounds(10, 112, 46, 14);
		contentPanel.add(nameLabel);
		
		nameTextArea = new JTextArea();
		nameTextArea.setWrapStyleWord(true);
		nameTextArea.setEditable(false);
		nameTextArea.setLineWrap(true);
		nameTextArea.setBackground(null);
		nameTextArea.setBorder(null);
		nameTextArea.setBounds(66, 112, 158, 50);
		contentPanel.add(nameTextArea);
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		okButton.setBounds(75, 173, 89, 23);
		contentPanel.add(okButton);

	}
	
	/**
	 * Ustawia tekst mówi¹cy, za ile zacznie siê wydarzenie.
	 * @param timeStr tekst, za ile zacznie siê wydarzenie
	 */
	public void setTimeText(String timeStr) {
		eventTimeTextField.setText("Wydarzenie rozpocznie si\u0119 za " + timeStr);
	}
	
	/** Ustawia nazwê lub opis wydarzania.
	 * 
	 * @param nameStr nazwa lub opis wydarzenia
	 */
	public void setNameText(String nameStr) {
		nameTextArea.setText(nameStr);
	}
	
	/**
	 * Ustawia miejsce wydarzenia.
	 * @param placeStr miejsce wydarzenia
	 */
	public void setPlaceText(String placeStr) {
		placeTextField.setText(placeStr);
	}
}
