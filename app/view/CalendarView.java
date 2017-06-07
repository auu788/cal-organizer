package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Klasa odpowiadaj¹ca za okno g³ówne organizera. Pokazuje kalendarz g³ówny.
 */
public class CalendarView extends JFrame {
	private int todaysDay, todaysMonth, todaysYear;
	
	private JPanel contentPane = new JPanel();
	private JPanel monthGridPanel = new JPanel();;
	private JButton[] buttonFields = new JButton[42];
	private JLabel[] labels = new JLabel[7];
	private JComboBox yearSelectComboBox;
	private JComboBox monthSelectComboBox;
	private JButton addEventButton, showEventsButton, removeOlderButton;
	private JButton importFromXMLButton, exportToXMLButton;
	private JButton exportToICSButton;
	
	// Menu bar
	private JMenuBar menuBar;
	private JMenu programMenu, eventsMenu;
	private JMenu exportSubMenu, importSubMenu;
	private JMenuItem expDBItem, expXMLItem, expICSItem;
	private JMenuItem impDBItem, impXMLItem;
	private JMenuItem settingsItem, aboutItem, exitItem;
	private JMenuItem addEventItem, showEventsItem, removeOlderItem;
	
	private int d_year = Calendar.getInstance().get(Calendar.YEAR);
	private int d_month = Calendar.getInstance().get(Calendar.MONTH);

	/**
	 * Konstruktor inicjalizuj¹cy initefejs graficzny okna g³ównego, ustawiaj¹cy parametry poszczególnych elementów.
	 */
	public CalendarView() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Organizer");
		
		setBounds(500, 200, 700, 550);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Menu bar
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 700, 21);
		contentPane.add(menuBar);
		
		programMenu = new JMenu("Program");
		programMenu.setMnemonic('p');
		menuBar.add(programMenu);
	
		importSubMenu = new JMenu("Importuj");
		programMenu.add(importSubMenu);
		
		exportSubMenu = new JMenu("Eksportuj");
		programMenu.add(exportSubMenu);
		
		expDBItem = new JMenuItem("Baza SQLite (.db)");
		exportSubMenu.add(expDBItem);
		
		expXMLItem = new JMenuItem("Format XML (.xml)");
		exportSubMenu.add(expXMLItem);
		
		expICSItem = new JMenuItem("iCalendar (.ics)");
		exportSubMenu.add(expICSItem);
		
		impDBItem = new JMenuItem("Baza SQLite (.db)");
		importSubMenu.add(impDBItem);
		
		impXMLItem = new JMenuItem("Format XML (.xml)");
		importSubMenu.add(impXMLItem);
		
		settingsItem = new JMenuItem("Ustawienia");
		programMenu.add(settingsItem);
		
		aboutItem = new JMenuItem("O programie");
		programMenu.add(aboutItem);
		
		exitItem = new JMenuItem("Wyjd\u017A");
		programMenu.add(exitItem);
		
		eventsMenu = new JMenu("Wydarzenia");
		eventsMenu.setMnemonic('w');
		menuBar.add(eventsMenu);
		
		addEventItem = new JMenuItem("Dodaj wydarzenie");
		eventsMenu.add(addEventItem);
		
		showEventsItem = new JMenuItem("Wy\u015Bwietl wydarzenia");
		eventsMenu.add(showEventsItem);
		
		removeOlderItem = new JMenuItem("Usu\u0144 wydarzenia starsze ni\u017C...");
		eventsMenu.add(removeOlderItem);

		// Siatka kalendarza
		monthGridPanel.setBounds(10, 85, 675, 350);
		contentPane.add(monthGridPanel);
		monthGridPanel.setLayout(new GridLayout(7, 7, 2, 2));
	}

	/**
	 * Tworzy siatkê przycisków, które bêd¹ poszczególnymi dniami w kalendarzu.
	 */
	public void createButtonFielsGrid(){
		for (int i = 0; i < buttonFields.length; i++) {
			buttonFields[i] = new JButton();
			buttonFields[i].setHorizontalAlignment(JTextField.CENTER);
			buttonFields[i].setFont(new Font("Tahoma", Font.PLAIN, 20));

			monthGridPanel.add(buttonFields[i]);
		}
	}
	
	/**
	 * Aktualizuje nazwy - dni przycisków na siatce.
	 * @param dayButtonsText tablica nazw przycisków
	 */
	public void updateButtonFielsGrid(String[] dayButtonsText){
		for (int i = 0; i < buttonFields.length; i++) {
			buttonFields[i].setText(dayButtonsText[i]);
			buttonFields[i].setBackground(Color.WHITE);
			buttonFields[i].setForeground(Color.BLACK);
			buttonFields[i].setBorderPainted(true);
			buttonFields[i].setOpaque(true);
			
			if (dayButtonsText[i] != "") {
				buttonFields[i].setEnabled(true);
			}
			else {
				buttonFields[i].setBackground(new Color(235, 235, 235));
				buttonFields[i].setEnabled(false);
			}
		}
	}

	/**
	 * Ustawia nazwy dni tygodnia w górnej czêœci siatki.
	 * @param dayNames tablica dni tygodnia
	 */
	public void setLabelsNames(String[] dayNames){
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(dayNames[i]);
			labels[i].setHorizontalAlignment(JLabel.CENTER);
			labels[i].setFont(new Font("Tahoma", Font.BOLD, 20));
			
			if (i == 6) { 
				labels[i].setForeground(Color.RED);
			}
			monthGridPanel.add(labels[i]);
		}
	}
	
	/**
	 * Dodaje rozwijane menu, pozwalaj¹ce wybraæ rok, który ma wyœwietlaæ siatka kalendarza.
	 * @param getYears tablica lat
	 */
	public void createYearSelectComboBox(String[] getYears) {
		yearSelectComboBox = new JComboBox<String>(getYears);
		yearSelectComboBox.setBounds(190, 30, 147, 43);
		yearSelectComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		((JLabel)yearSelectComboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

		yearSelectComboBox.setSelectedIndex(d_year - 1900);
		contentPane.add(yearSelectComboBox);
	}
	
	/**
	 * Ustawia atrybuty wskazuj¹ce na dzisiejszy dzieñ.
	 * @param day dzisiejszy dzieñ
	 */
	public void markTodaysDay(int day) {
		this.todaysDay = day;
		this.todaysMonth = monthSelectComboBox.getSelectedIndex();
		this.todaysYear = yearSelectComboBox.getSelectedIndex() + 1900;

		updateTodaysDay();
	}
	
	/**
	 * Zaznacza dzisiejszy dzieñ na siatce kalendarza, jeœli siatka wskazuje na miesi¹c i rok, który mamy.
	 */
	private void updateTodaysDay() {
		if (this.todaysMonth == monthSelectComboBox.getSelectedIndex() &&
				this.todaysYear == yearSelectComboBox.getSelectedIndex() + 1900) {
			int day = 0;
			for (int i = 0; i < buttonFields.length; i++) {
				if (buttonFields[i].getText() != "") {
					day++;
				}
				if (day == todaysDay) {
					buttonFields[i].setForeground(Color.RED);
				}
			}
		}
	}
	
	/**
	 * Dodaje listener na przycisk "Dodaj wydarzenie"
	 * @param listenForAddEvent listener dodawania wydarzenia
	 */
	public void addAddEventButtonListener(ActionListener listenForAddEvent) {
		addEventButton.addActionListener(listenForAddEvent);
		addEventItem.addActionListener(listenForAddEvent);
	}

	/** Dodaje listener na rozwiajane menu wyboru roku.
	 * 
	 * @param listenForSelectYear listener wyboru roku
	 */
	public void addYearSelectComboBoxListener(ActionListener listenForSelectYear) {
		yearSelectComboBox.addActionListener(listenForSelectYear);
	}

	/**
	 * Dodaje rozwijane menu, pozwalaj¹ce wybraæ miesiac, który ma wyœwietlaæ siatka kalendarza.
	 * @param getYears tablica nazw miesiêcy
	 */
	public void createMonthSelectComboBox(String[] getMonths) {
		monthSelectComboBox = new JComboBox<String>(getMonths);
		monthSelectComboBox.setBounds(370, 30, 147, 43);
		monthSelectComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		((JLabel)monthSelectComboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
		monthSelectComboBox.setSelectedIndex(d_month);
		contentPane.add(monthSelectComboBox);
	}

	/** Dodaje listener na rozwiajane menu wyboru miesi¹ca.
	 * 
	 * @param listenForSelectYear listener wyboru miesi¹ca
	 */
	public void addMonthSelectComboBoxListener(ActionListener listenForSelectMonth) {
		monthSelectComboBox.addActionListener(listenForSelectMonth);
	}

	/**
	 * Pobiera obiekt rozwijanego menu dla roku.
	 * @return obiekt rozwijanego menu dla roku
	 */
	public JComboBox getYearSelectComboBox() {
		return yearSelectComboBox;
	}
	
	/**
	 * Pobiera rok wybrany z rozwijanego menu.
	 * @return rok
	 */
	public int getYearSelectComboBoxSelectedItem() {
		return Integer.parseInt(yearSelectComboBox.getSelectedItem().toString());
	}
	
	/**
	 * Pobiera miesi¹c wybrany z rozwijanego menu.
	 * @return miesi¹c
	 */
	public int getMonthSelectComboBoxSelectedItem() {
		return monthSelectComboBox.getSelectedIndex();
	}

	/**
	 * Ustawia rozwiajne menu dla roku.
	 * @param yearSelectComboBox rozwijane menu dla roku
	 */
	public void setYearSelectComboBox(JComboBox yearSelectComboBox) {
		this.yearSelectComboBox = yearSelectComboBox;
	}

	/**
	 * Pobiera obiekt rozwijanego menu dla miesi¹ca.
	 * @return obiekt rozwijanego menu dla miesi¹ca
	 */
	public JComboBox getMonthSelectComboBox() {
		return monthSelectComboBox;
	}

	/**
	 * Ustawia rozwiajne menu dla roku.
	 * @param yearSelectComboBox rozwijane menu dla roku
	 */
	public void setMonthSelectComboBox(JComboBox monthSelectComboBox) {
		this.monthSelectComboBox = monthSelectComboBox;
	}

	/**
	 * Pobiera tablicê przycisków z siatki kalendarza
	 * @return tablica przycisków
	 */
	public JButton[] getButtonFields() {
		return buttonFields;
	}

	/**
	 * Aktualizuje tablicê przycisków na siatkê kalendarza.
	 * @param buttonFields tablica przycisków
	 */
	public void setButtonFields(JButton[] buttonFields) {
		this.buttonFields = buttonFields;
	}
	
	/**
	 * Dodaje przycisk "Dodaj wydarzenie".
	 */
	public void createAddEventButton() {
		addEventButton = new JButton("Dodaj wydarzenie");
		addEventButton.setMnemonic('d');
		addEventButton.setBounds(255, 450, 180, 50);
		addEventButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(addEventButton);
	}
	
	/**
	 * Zaznacza na siatce kalendarza dni, w których wyst¹puj¹ jakiekolwiek wydarzenia.
	 * @param eventDays
	 */
	public void updateEventDays(List<Integer> eventDays) {
		boolean isEvent;
		
		for (int i = 0; i < buttonFields.length; i++) {
			isEvent = false;
			if (buttonFields[i].getText() != "") { 
				for (int evt_day : eventDays) {
					if (Integer.parseInt(buttonFields[i].getText()) == evt_day) {
						buttonFields[i].setBackground(new Color(55, 50, 250));
						buttonFields[i].setForeground(Color.WHITE);
						isEvent = true;
					}
				}
			}
			
			if (isEvent == false && buttonFields[i].getText() != "") {
				buttonFields[i].setBackground(Color.WHITE);
				buttonFields[i].setForeground(Color.BLACK);
			}
		}
		
		updateTodaysDay();
	}
	
	/**
	 * Dodaje listener dla ka¿dego przycisku na siatce kalendarza.
	 * @param listenForDayButtons listener dla klikniêcia w przycisk
	 */
	public void addDayButtonsListener(ActionListener listenForDayButtons) {
		for (int i = 0; i < buttonFields.length; i++) {
			buttonFields[i].addActionListener(listenForDayButtons);
		}
	}

	/**
	 * Dodaje listener dla przycisku z menu "Wyœwietl wydarzenia".
	 * @param listenForShowEventsButton listener odpowiadaj¹cy za wyœwietlenie wszystkich wydarzeñ
	 */
	public void addShowEventsItemListener(ActionListener listenForShowEventsButton) {
		showEventsItem.addActionListener(listenForShowEventsButton);
	}

	/**
	 * Dodaje listener dla przycisku z menu "Usuñ starsze ni¿...".
	 * @param listenForRemoveOlderThanButton listener odpowiadaj¹cy za usuniêcie wydarzeñ starszych ni¿ podana data
	 */
	public void addRemoveOlderThanItemListener(ActionListener listenForRemoveOlderThanButton) {
		removeOlderItem.addActionListener(listenForRemoveOlderThanButton);
	}
	
	/**
	 * Dodaje listener dla przycisku z menu "Importuj z XML".
	 * @param listenForImportFromXML listener odpowiadaj¹ca za import z XML-a
	 */
	public void addImportFromXMLItemListener(ActionListener listenForImportFromXML) {
		impXMLItem.addActionListener(listenForImportFromXML);
	}

	/**
	 * Dodaje listener dla przycisku z menu "Eksportuj do XML".
	 * @param listenForImportFromXML listener odpowiadaj¹ca za eksport do XML-a
	 */
	public void addExportToXMLItemListener(ActionListener listenForExportToXML) {
		expXMLItem.addActionListener(listenForExportToXML);
	}
	
	/**
	 * Dodaje listener dla przycisku z menu "Eksportuj do bazy danych SQLite".
	 * @param listenForImportFromXML listener odpowiadaj¹ca za eksport do bazy SQLite
	 */
	public void addExportToDBItemListener(ActionListener listenForExportToDB) {
		expDBItem.addActionListener(listenForExportToDB);
	}
	
	/**
	 * Dodaje listener dla przycisku z menu "Importuj z bazy danych SQLite".
	 * @param listenForImportFromXML listener odpowiadaj¹ca za import z bazy danych SQLite
	 */
	public void addImportFromDBItemListener(ActionListener listenForImportFromDB) {
		impDBItem.addActionListener(listenForImportFromDB);
	}
	
	/**
	 * Dodaje listener dla przycisku z menu "Eksportuj do formatu standardowego ICS".
	 * @param listenForImportFromXML listener odpowiadaj¹ca za eksport do formatu ICS
	 */
	public void addExportToICSItemListener(ActionListener listenForExportToICS) {
		expICSItem.addActionListener(listenForExportToICS);
	}
	
	/**
	 * Dodaje listener dla przycisku z menu "O programie".
	 * @param listenForAboutItem listener odpowiadaj¹cy za okienko "O programie"
	 */
	public void addAboutItemListener(ActionListener listenForAboutItem) {
		aboutItem.addActionListener(listenForAboutItem);
	}
	
	/**
	 * Dodaje listener dla przycisku z menu "WyjdŸ".
	 * @param listenForExitItem listener odpowiadaj¹cy za wyjœcie z programu
	 */
	public void addExitItemListener(ActionListener listenForExitItem) {
		exitItem.addActionListener(listenForExitItem);
	}
	
	/**
	 * Dodaje listener dla przycisku z menu "Ustawienia".
	 * @param listenForSettingsItem listener odpowiadaj¹cy za ustawienia programu
	 */
	public void addSettingsItemListener(ActionListener listenForSettingsItem) {
		settingsItem.addActionListener(listenForSettingsItem);
	}
}
