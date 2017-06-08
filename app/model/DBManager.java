package model;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa zarz¹dzaj¹ca baz¹ danych SQLite.
 */
public class DBManager {
	private String driver = "org.sqlite.JDBC";
	private String filePath;
	
	private Connection connection;
	private Statement statement;

	/**
	 * Konstruktor inicjalizuj¹cy obiekt menad¿era bazy danych SQLite.
	 */
	public DBManager() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.err.println("Brak sterownika JDBC.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Tworzy tabelê w bazie danych z polami odpowiadaj¹cymi atrybutom wydarzenia.
	 */
	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS events (id TEXT PRIMARY KEY, date TEXT, alarm TEXT, place TEXT, name TEXT)";
		
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			System.err.println("B³¹d przy tworzeniu tabeli.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Dodaje wydarzenie do bazy danych.
	 * @param evt obiekt wydarzenia
	 * @see Event
	 */
	public void addEvent(Event evt) {
		DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String date = date_format.format(evt.getDate());
		String alarm;
		
		initConnection();
		
		if (evt.getAlarm() == null) {
			alarm = null;
		} else {
			alarm = date_format.format(evt.getAlarm());
		}
		
		String uuid = evt.getID().toString();
		
		try {
            PreparedStatement prepStmt = connection.prepareStatement(
                    	"INSERT INTO events VALUES (?, ?, ?, ?, ?);");
          
            prepStmt.setString(1, uuid);
            prepStmt.setString(2, date);
            prepStmt.setString(3, alarm);
            prepStmt.setString(4, evt.getPlace());
            prepStmt.setString(5, evt.getName());
            prepStmt.execute();
            
        } catch (SQLException e) {
            System.err.println("B³¹d przy wstawianiu nowego wydarzenia.");
            e.printStackTrace();
        }
		
		closeConnection();
	}
	
	/**
	 * Usuwa wydarzenie z bazy danych na podstawie identyfikatora UUID.
	 * @param uuid identyfikator UUID
	 */
	public void removeById(String uuid) {
		initConnection();
		
		try {
			PreparedStatement prepStmt = connection.prepareStatement(
					"DELETE FROM events WHERE id=?");
	
			prepStmt.setString(1, uuid);
			prepStmt.execute();
			
		} catch (SQLException e) {
            System.err.println("B³¹d przy usuwaniu wydarzenia.");
            e.printStackTrace();
        }
		
		closeConnection();
	}
	
	/**
	 * Importuje wszystkie wydarzenia z bazy danych.
	 * @param file œcie¿ka do pliku DB
	 * @return lista wydarzeñ
	 */
	public List<Event> importFromDB(File file) {
		this.filePath = "jdbc:sqlite:" + file.toString();
		initConnection();
		
		List<Event> eventList = new ArrayList<Event>();
		
		try {
			ResultSet result = statement.executeQuery("SELECT * FROM events");
			
			while (result.next()) {
				Event event = new Event(
						result.getString("name"), 
						result.getString("place"), 
						result.getString("date"),
						result.getString("alarm"),
						result.getString("id"));
				eventList.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeConnection();
			
		return eventList;
	}
	
	/**
	 * Eksportuje do bazy danych wszystkie wydarzenia.
	 * @param eventList lista wydarzeñ
	 * @param file œcie¿ka do pliku DB
	 */
	public void exportToDB(List<Event> eventList, File file) {
		this.filePath = "jdbc:sqlite:" + file.toString();
		initConnection();
		createTable();
		
		for (Event evt : eventList) {
			addEvent(evt);
		}
	
		closeConnection();
	}
	
	/**
	 * Inicjalizuje po³¹czenie z baz¹ danych.
	 */
	private void initConnection() {
		try {
			this.connection = DriverManager.getConnection(this.filePath);
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("B³¹d po³¹czenia z baz¹ danych.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Koñczy po³¹czenie z baz¹ danych.
	 */
	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("B³¹d przy zamykaniu po³¹czenia z baz¹ danych.");
			e.printStackTrace();
		}
	}
}
