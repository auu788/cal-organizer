package model;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	private String driver = "org.sqlite.JDBC";

	private Connection connection;
	private Statement statement;

	
	public DBManager() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.err.println("Brak sterownika JDBC.");
			e.printStackTrace();
		}
	}
	
	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS events (id TEXT PRIMARY KEY, date TEXT, alarm TEXT, place TEXT, name TEXT)";
		
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			System.err.println("B³¹d przy tworzeniu tabeli.");
			e.printStackTrace();
		}
	}
	
	public void addEvent(Event evt) {
		System.out.println(this.hashCode());
		DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String date = date_format.format(evt.getDate());
		String alarm;
		
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
	}
	
	public void removeById(String uuid) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement(
					"DELETE FROM events WHERE id=?");
	
			prepStmt.setString(1, uuid);
			prepStmt.execute();
			
		} catch (SQLException e) {
            System.err.println("B³¹d przy usuwaniu wydarzenia.");
            e.printStackTrace();
        }
	}
	
	
	public List<Event> importFromDB(File file) {
		String filePath = "jdbc:sqlite:" + file.toString();
		initConnection(filePath);
		
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
		
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("B³¹d przy zamykaniu po³¹czenia z baz¹ danych.");
			e.printStackTrace();
		}
			
		return eventList;
	}
	
	public void exportToDB(List<Event> eventList, File file) {
		String filePath = "jdbc:sqlite:" + file.toString();
		initConnection(filePath);
		createTable();
		
		for (Event evt : eventList) {
			addEvent(evt);
		}
		
		System.out.println("Pomyœlnie wyeksportowane wydarzenia do bazy danych.");
		
		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("B³¹d przy zamykaniu po³¹czenia z baz¹ danych.");
			e.printStackTrace();
		}
	}
	
	private void initConnection(String filePath) {
		try {
			this.connection = DriverManager.getConnection(filePath);
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("B³¹d po³¹czenia z baz¹ danych.");
			e.printStackTrace();
		}
	}
}
