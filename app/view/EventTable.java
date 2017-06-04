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
	private EventManager eventManager;
	private JTextField dateFromTextField;
	private JTextField dateToTextField;
	private JTextField searchTextField;
	private JButton searchButton, clearSearchButton;
	private JTable table;
	private OrganizerTableModel organizerTableModel;
	
	public EventTable(EventManager eventManager) {
		this.eventManager = eventManager;
		this.organizerTableModel = new OrganizerTableModel(this.eventManager);
		setBounds(100, 100, 900, 300);
		getContentPane().setLayout(null);
		
		JPanel filtersPanel = new JPanel();
		filtersPanel.setBounds(10, 11, 864, 49);
		getContentPane().add(filtersPanel);
		filtersPanel.setLayout(null);
		
		dateFromTextField = new JTextField();
		dateFromTextField.setBounds(10, 18, 86, 20);
		filtersPanel.add(dateFromTextField);
		dateFromTextField.setColumns(10);
		
		JLabel lblDataOd = new JLabel("Data - od");
		lblDataOd.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataOd.setBounds(10, 0, 86, 20);
		filtersPanel.add(lblDataOd);
		
		dateToTextField = new JTextField();
		dateToTextField.setBounds(106, 18, 86, 20);
		filtersPanel.add(dateToTextField);
		dateToTextField.setColumns(10);
		
		JLabel lblDataDo = new JLabel("Data - do");
		lblDataDo.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataDo.setBounds(106, 0, 86, 20);
		filtersPanel.add(lblDataDo);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(202, 18, 162, 20);
		
		JLabel searchLabel = new JLabel("Szukana fraza");
		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchLabel.setBounds(235, 0, 86, 20);
		filtersPanel.add(searchLabel);
		
		filtersPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		searchButton = new JButton("Filtruj");
		searchButton.setBounds(374, 18, 135, 20);
		filtersPanel.add(searchButton);
		
		clearSearchButton = new JButton("Wyczyœæ filtry");
		clearSearchButton.setBounds(559, 18, 135, 20);
		filtersPanel.add(clearSearchButton);
		
		getRootPane().setDefaultButton(searchButton);
		updateTable();
	}
	
	public void updateTable() {
		table = new JTable(this.organizerTableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 59, 864, 191);
		//table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(401);
		table.setAutoCreateRowSorter(true);
		
		TableRowSorter<OrganizerTableModel> rowSorter = new TableRowSorter<OrganizerTableModel>(this.organizerTableModel);
		table.setRowSorter(rowSorter);
		
		rowSorter.setComparator(0, new TableSorter());
		
		getContentPane().add(scrollPane);
	}
	public void addSearchButtonListener(ActionListener listenForSearchButton) {
		searchButton.addActionListener(listenForSearchButton);
		clearSearchButton.addActionListener(listenForSearchButton);
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

}
