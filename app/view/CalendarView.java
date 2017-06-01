package view;

import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CalendarView extends JFrame {
	private JPanel contentPane = new JPanel();
	private JPanel monthGridPanel = new JPanel();;
	private JButton[] buttonFields = new JButton[42];
	private JLabel[] labels = new JLabel[7];
	private JComboBox yearSelectComboBox;
	private JComboBox monthSelectComboBox;
	private JButton addEventButton;
	private int d_year = Calendar.getInstance().get(Calendar.YEAR);
	private int d_month = Calendar.getInstance().get(Calendar.MONTH);

	public CalendarView() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 500, 350);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		monthGridPanel.setBounds(10, 65, 474, 245);
		contentPane.add(monthGridPanel);
		monthGridPanel.setLayout(new GridLayout(7, 7, 0, 0));
	}

	public void createButtonFielsGrid(){
		for (int i = 0; i < buttonFields.length; i++) {
			buttonFields[i] = new JButton();
			// buttonFields[i].setEditable(false);
			buttonFields[i].setHorizontalAlignment(JTextField.CENTER);

//			buttonFields[i].addMouseListener(new MouseListener() {
//
//				public void mouseClicked(MouseEvent e) {
//					System.out.println("test");
//				}
//
//				public void mousePressed(MouseEvent e) {
//				}
//
//				public void mouseReleased(MouseEvent e) {
//				}
//
//				public void mouseEntered(MouseEvent e) {
//				}
//
//				public void mouseExited(MouseEvent e) {
//				}
//
//			});
			monthGridPanel.add(buttonFields[i]);
		}
	}
	
	
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

	public void setLabelsNames(String[] dayNames){
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel(dayNames[i]);
			labels[i].setHorizontalAlignment(JLabel.CENTER);
			monthGridPanel.add(labels[i]);
		}
	}
	
	public void createYearSelectComboBox(String[] getYears) {
		yearSelectComboBox = new JComboBox<String>(getYears);
		yearSelectComboBox.setBounds(10, 11, 147, 43);
		yearSelectComboBox.setSelectedIndex(d_year - 1900);
		contentPane.add(yearSelectComboBox);
	}
	
	public void addAddEventButtonListener(ActionListener listenForAddEvent) {
		addEventButton.addActionListener(listenForAddEvent);
	}

	public void addYearSelectComboBoxListener(ActionListener listenForSelectYear) {
		yearSelectComboBox.addActionListener(listenForSelectYear);
	}

	public void createMonthSelectComboBox(String[] getMonths) {
		monthSelectComboBox = new JComboBox<String>(getMonths);
		monthSelectComboBox.setBounds(180, 11, 147, 43);
		monthSelectComboBox.setSelectedIndex(d_month);
		contentPane.add(monthSelectComboBox);
	}

	public void addMonthSelectComboBoxListener(ActionListener listenForSelectMonth) {
		monthSelectComboBox.addActionListener(listenForSelectMonth);
	}

	public JComboBox getYearSelectComboBox() {
		return yearSelectComboBox;
	}
	
	public int getYearSelectComboBoxSelectedItem() {
		return Integer.parseInt(yearSelectComboBox.getSelectedItem().toString());
	}
	
	public int getMonthSelectComboBoxSelectedItem() {
		return monthSelectComboBox.getSelectedIndex();
	}

	public void setYearSelectComboBox(JComboBox yearSelectComboBox) {
		this.yearSelectComboBox = yearSelectComboBox;
	}

	public JComboBox getMonthSelectComboBox() {
		return monthSelectComboBox;
	}

	public void setMonthSelectComboBox(JComboBox monthSelectComboBox) {
		this.monthSelectComboBox = monthSelectComboBox;
	}

	public JButton[] getButtonFields() {
		return buttonFields;
	}

	public void setButtonFields(JButton[] buttonFields) {
		this.buttonFields = buttonFields;
	}
	
	public void createAddEventButton() {
		addEventButton = new JButton("Dodaj wydarzenie");
		addEventButton.setBounds(345, 11, 135, 43);
		contentPane.add(addEventButton);
	}
	
	public void updateEventDays(List<Integer> eventDays) {
		for (int i = 0; i < buttonFields.length; i++) {
			if (buttonFields[i].getText() != "") { 
				for (int evt_day : eventDays) {
					if (Integer.parseInt(buttonFields[i].getText()) == evt_day) {
						buttonFields[i].setBackground(Color.RED);
						buttonFields[i].setForeground(Color.WHITE);
					}
				}
			}
		}
	}
	
	public void addDayButtonsListener(ActionListener listenForDayButtons) {
		for (int i = 0; i < buttonFields.length; i++) {
			buttonFields[i].addActionListener(listenForDayButtons);
		}
	}
}
