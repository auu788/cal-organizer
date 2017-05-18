package model;

import java.util.Calendar;

public class KalendarzModel {
	private String monthsNames[] = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpien",
			"Wrzesien", "Pażdziernik", "Listopad", "Grudzień" };
	private String[] dayNames = { "PN", "WT", "ŚR", "CZW", "PT", "SOB", "ND" };
	private String[] newButtonsText = new String[42];
	private int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	public KalendarzModel() {
	}
	
	public String[] getMonthsNames() {
		return monthsNames;
	}

	public void setMonthsNames(String[] monthsNames) {
		this.monthsNames = monthsNames;
	}

	public int[] getMonthDays() {
		return monthDays;
	}

	public void setMonthDays(int[] monthDays) {
		this.monthDays = monthDays;
	}

	public String[] getDayNames() {
		return dayNames;
	}

	public void setDayNames(String[] dayNames) {
		this.dayNames = dayNames;
	}

	public String[] getYears() {
		String[] years = new String[201];
		for (int i = 1900, j = 0; i < 2100; i++, j++) {
			years[j] = String.valueOf(i);
		}
		return years;
	}
		
	public String[] updateCalendar(int year, int month) {
		int day = 0;
		if (month == 1 && leapYear(year)) {
			monthDays[month] = 29;
		} else {
			monthDays[month] = 28;
		}
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);

		int day_of_week = c.get(Calendar.DAY_OF_WEEK);
		day = Math.floorMod(day_of_week - 2, 7);

		for (int i = 0; i < newButtonsText.length; i++) {
			newButtonsText[i] = "";
		}
		for (int i = 1, j = day; i <= monthDays[month]; i++, j++) {
			newButtonsText[j] = Integer.toString(i);
		}
		return newButtonsText;
	}
	
	private boolean leapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return true;
		}
		return false;
	}
}
