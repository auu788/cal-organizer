package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

public class OrganizerTableModel extends AbstractTableModel {
	private Vector<String> columnNames;
	private Vector<String[]> data;
	private EventManager eventManager;
	private TableRowSorter<OrganizerTableModel> sorter;
	
	public OrganizerTableModel(EventManager eventManager) {
		this.eventManager = eventManager;
		columnNames = new Vector<String>();
		data = new Vector<String[]>();
		
		String[] columnString = {"ID", "Data",
                "Godzina",
                "Alarm",
                "Miejsce",
                "Opis"};
		
		for (String column : columnString) {
			columnNames.add(column);
		}
		
		updateTableData("", "", "");
	}
	
	public void updateTableData(String searchText, String dateFrom, String dateTo) {
		clearTable();
		List<Event> events;
		
		if (dateFrom.length() == 0 && dateTo.length() == 0) {
			events = eventManager.getEventList();
		} else {
			events = getEventsInBetweenDates(dateFrom, dateTo);

		}
		
		DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat hour_format = new SimpleDateFormat("HH:mm");
		
		for (Event evt : events) {
			String uuid = evt.getID().toString();
			String name = evt.getName();
			String place = evt.getPlace();
			
			if (!name.toLowerCase().contains(searchText.toLowerCase()) && 
					!place.toLowerCase().contains(searchText.toLowerCase())) {
				continue;
			}
			
			String date = date_format.format(evt.getDate());
			String hour = hour_format.format(evt.getDate());
			String alarm;
			if (evt.getAlarm() == null) {
				alarm = "Wy³¹czony";
			} else {
				long diffInMilliseconds = evt.getDate().getTime() - evt.getAlarm().getTime();
				int alarmMinutes = (int) TimeUnit.MINUTES.convert(diffInMilliseconds,TimeUnit.MILLISECONDS);

				if (alarmMinutes < 60) {
					alarm = alarmMinutes + " min przed";
				} else {
					alarm = alarmMinutes / 60 + " godz przed";
				}
			}
			
			String[] info = {uuid, date, hour, alarm, place, name};
			data.add(info);
		}
	}
	
	private List<Event> getEventsInBetweenDates(String df, String dt) {
		List<Event> events = eventManager.getEventList();
		List<Event> filtered_events = new ArrayList<Event>();
		
		DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
		Date dateFrom = null, dateTo = null;
		
		if (df.length() == 0) {
			df = "01-01-1900";
		}
		
		if (dt.length() == 0) {
			dt = "31-12-2099";
		}
		
		try {
			dateFrom = date_format.parse(df);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			dateTo = date_format.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for (Event evt : events) {
			Date eventDate = null;
			try {
				eventDate = date_format.parse(date_format.format(evt.getDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if ((eventDate.after(dateFrom) || eventDate.equals(dateFrom)) &&
					(eventDate.before(dateTo) || eventDate.equals(dateTo))) {
				filtered_events.add(evt);
			}
		}
		
		return filtered_events;
		
	}

	@Override
	public String getColumnName(int col) {
		  return columnNames.get(col);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row)[col];
	}

	public void clearTable()
	{
		data.clear();
	}

}