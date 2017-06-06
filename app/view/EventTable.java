package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JTable;

import model.EventManager;
import model.OrganizerTableModel;
import model.TableSorter;

public class EventTable extends JDialog {
	private EventManager eventManager; // Do przeniesienia z widoku do kontrolera
	private JTextField dateFromTextField;
	private JTextField dateToTextField;
	private JTextField searchTextField;
	private JButton searchButton, clearSearchButton, removeSelectedButton;
	private JTable table;
	private OrganizerTableModel organizerTableModel;
	
	public EventTable(EventManager eventManager) {
		this.eventManager = eventManager;
		this.organizerTableModel = new OrganizerTableModel(this.eventManager);
		setBounds(400, 200, 900, 300);
		setTitle("Wszystkie wydarzenia");
		getContentPane().setLayout(null);
		
		JPanel filtersPanel = new JPanel();
		filtersPanel.setBounds(10, 11, 864, 49);
		getContentPane().add(filtersPanel);
		filtersPanel.setLayout(null);
		
		dateFromTextField = new JTextField();
		dateFromTextField.setBounds(10, 18, 86, 20);
		filtersPanel.add(dateFromTextField);
		dateFromTextField.setColumns(10);
		
		JLabel dateFromLabel = new JLabel("Data - od");
		dateFromLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateFromLabel.setBounds(10, 0, 86, 20);
		filtersPanel.add(dateFromLabel);
		
		dateToTextField = new JTextField();
		dateToTextField.setBounds(106, 18, 86, 20);
		filtersPanel.add(dateToTextField);
		dateToTextField.setColumns(10);
		
		JLabel dateToLabel = new JLabel("Data - do");
		dateToLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateToLabel.setBounds(106, 0, 86, 20);
		filtersPanel.add(dateToLabel);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(202, 18, 162, 20);
		searchTextField.setColumns(10);
		filtersPanel.add(searchTextField);
		
		JLabel searchLabel = new JLabel("Szukana fraza");
		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchLabel.setBounds(235, 0, 86, 20);
		filtersPanel.add(searchLabel);
		
		searchButton = new JButton("Filtruj");
		searchButton.setBounds(374, 0, 135, 38);
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		filtersPanel.add(searchButton);
		
		clearSearchButton = new JButton("Wyczyœæ filtry");
		clearSearchButton.setBounds(719, 20, 135, 18);
		filtersPanel.add(clearSearchButton);
		
		removeSelectedButton = new JButton("Usuñ zaznaczone");
		removeSelectedButton.setBounds(719, 0, 135, 18);
		filtersPanel.add(removeSelectedButton);
		
		getRootPane().setDefaultButton(searchButton);
		updateTable();
	}
	
	public void updateTable() {
		table = new JTable(this.organizerTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setBounds(10, 59, 860, 191);
		//table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(392);
		table.setAutoCreateRowSorter(true);
		
		// Ukrycie kolumny z ID
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setResizable(false);
		
		TableRowSorter<OrganizerTableModel> rowSorter = new TableRowSorter<OrganizerTableModel>(this.organizerTableModel);
		table.setRowSorter(rowSorter);
		
		rowSorter.setComparator(0, new TableSorter());
		
		getContentPane().add(scrollPane);
	}
	public void addSearchButtonListener(ActionListener listenForSearchButton) {
		searchButton.addActionListener(listenForSearchButton);
		clearSearchButton.addActionListener(listenForSearchButton);
	}
	
	public void addRemoveSelectedListener(ActionListener listenForSelectedRows) {
		removeSelectedButton.addActionListener(listenForSelectedRows);
	}
	
	public String getDateFrom() {
		return dateFromTextField.getText();
	}
	
	public String getDateTo() {
		return dateToTextField.getText();
	}
	
	public String getSearchText() {
		return searchTextField.getText();
	}
	
	public OrganizerTableModel getTableModel() {
		return organizerTableModel;
	}
	
	public JTable getTable() {
		return table;
	}
	
	public JButton getSearchButton() {
		return searchButton;
	}
	
	public JButton getClearSearchButton() {
		return clearSearchButton;
	}
	
	public void clearFields() {
		dateFromTextField.setText("");
		dateToTextField.setText("");
		searchTextField.setText("");
	}
	
	public int[] getSelectedRows() {
		return table.getSelectedRows();
	}

}
