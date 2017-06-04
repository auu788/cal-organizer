package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class TableSorter implements Comparator<String> {
	@Override
    public int compare(String d1, String d2) {
    	DateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
    	
    	Date date1 = null, date2 = null;
    	
    	try {
			date1 = date_format.parse(d1);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
    	try {
			date2 = date_format.parse(d2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
        return date1.compareTo(date2);
    }
}
