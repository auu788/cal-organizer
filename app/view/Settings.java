package view;

import java.awt.BorderLayout;

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

/**
 * Klasa odpowiadaj�ca za interfejs graficzny okienka "Ustawienia".
 */
public class Settings extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel settingsLabel, alarmSoundLabel, dbFileLabel;
	private JTextField alarmSoundPath, dbFilePath;
	private JButton alarmButton, dbButton, confirmButton, cancelButton;

	/**
	 * Konstruktor inicjalizuj�cy initefejs graficzny okienka "Ustawienia", ustawiaj�cy parametry poszczeg�lnych element�w.
	 */
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
		
		alarmSoundLabel = new JLabel("D\u017Awi\u0119k powiadomienia");
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
		
		dbButton = new JButton("...");
		dbButton.setBounds(254, 151, 20, 20);
		contentPanel.add(dbButton);
		
		alarmSoundPath = new JTextField();
		alarmSoundPath.setColumns(10);
		alarmSoundPath.setBounds(10, 83, 234, 20);
		contentPanel.add(alarmSoundPath);
		
		alarmButton = new JButton("...");
		alarmButton.setBounds(254, 83, 20, 20);
		contentPanel.add(alarmButton);
		
		confirmButton = new JButton("Zatwierd\u017A");
		confirmButton.setBounds(36, 197, 95, 23);
		contentPanel.add(confirmButton);
		
		cancelButton = new JButton("Anuluj");
		cancelButton.setBounds(155, 197, 95, 23);
		contentPanel.add(cancelButton);
	}
	
	/**
	 * Dodaje listener dla przycisku odpowiadaj�cego za okienko wyboru bazy danych SQlite lub XML.
	 * @param listenForDBFilePathButton listener odpowiadaj�cy za pobranie �ciezki bazy danych SQlite lub XML.
	 */
	public void addDBFilePathButtonListener(ActionListener listenForDBFilePathButton) {
		dbButton.addActionListener(listenForDBFilePathButton);
	}
	
	/**
	 * Dodaje listener dla przycisku odpowiadaj�cego za okienko wyboru pliku d�wi�kowego.
	 * @param listenForAlarmFilePathButton listener odpowiadaj�cy za pobranie �ciezki pliku d�wi�kowego
	 */
	public void addAlarmFilePathButtonListener(ActionListener listenForAlarmFilePathButton) {
		alarmButton.addActionListener(listenForAlarmFilePathButton);
	}
	
	/**
	 * Dodaje listener dla przycisku "Potwierd�". 
	 * @param listenForConfirmButton listener potwierdzaj�cy akcj�
	 */
	public void addConfirmButtonListener(ActionListener listenForConfirmButton) {
		confirmButton.addActionListener(listenForConfirmButton);
	}
	
	/**
	 * Dodaje listener dla przycisku "Anuluj". 
	 * @param listenForCancelButton listenForConfirmation listener anuluj�cy akcj�
	 */
	public void addCancelButtonListener(ActionListener listenForCancelButton) {
		cancelButton.addActionListener(listenForCancelButton);
	}
	
	/**
	 * Pobiera �cie�k� do pliku bazy danych SQlite lub XML
	 * @return �cie�ka do pliku bazy danych
	 */
	public String getDBFilePath() {
		return dbFilePath.getText();
	}
	
	/**
	 * Pobiera �cie�k� do pliku d�wi�kowego WAV dla powiadomienia.
	 * @return �cie�ka do pliku d�wi�kowego WAV
	 */
	public String getAlarmFilePath() {
		return alarmSoundPath.getText();
	}
	
	/**
	 * Ustawia �cie�k� do pliku bazy danych SQlite lub XML
	 * @param path �cie�ka do pliku d�wi�kowego WAV
	 */
	public void setDBFilePath(String path) {
		dbFilePath.setText(path);
	}
	
	/**
	 * Ustawia �cie�k� do pliku d�wi�kowego WAV dla powiadomienia.
	 * @param path �cie�ka do pliku d�wi�kowego WAV
	 */
	public void setAlarmFilePath(String path) {
		alarmSoundPath.setText(path);
	}
}
