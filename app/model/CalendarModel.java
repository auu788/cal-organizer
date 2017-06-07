package model;

import java.util.Calendar;

/**
 * Klasa zarz¹dzaj¹ca danymi znajduj¹cymi w g³ównej ramce kalendarza.
 * @author auu78
 *
 */
public class CalendarModel {
	private String monthsNames[] = { "Styczeñ", "Luty", "Marzec", "Kwiecieñ", "Maj", "Czerwiec", "Lipiec", "Sierpieñ",
			"Wrzesieñ", "PaŸdziernik", "Listopad", "Grudzieñ" };
	private String[] dayNames = { "PN", "WT", "ŒR", "CZW", "PT", "SOB", "ND" };
	private String[] dayButtonsText = new String[42];
	private int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	/**
	 * Pobiera tablicê nazw miesiêcy.
	 * @return tablica nazw miesiêcy
	 */
	public String[] getMonthsNames() {
		return monthsNames;
	}

//	public void setMonthsNames(String[] monthsNames) {
//		this.monthsNames = monthsNames;
//	}

	/**
	 * Pobiera tablicê iloœci dni dla ka¿dego miesi¹ca.
	 * @return tablica iloœci dni
	 */
	public int[] getMonthDays() {
		return monthDays;
	}

	/**
	 * Pobiera tablicê nazwy dni.
	 * @return tablica nazw dni
	 */
	public String[] getDayNames() {
		return dayNames;
	}

//	public void setDayNames(String[] dayNames) {
//		this.dayNames = dayNames;
//	}

	/**
	 * Pobiera tablicê lat od 1900 do 2100.
	 * @return tablica lat
	 */
	public String[] getYears() {
		String[] years = new String[201];
		for (int i = 1900, j = 0; i < 2100; i++, j++) {
			years[j] = String.valueOf(i);
		}
		return years;
	}
		
	/**
	 * Pobiera tablicê z wartoœciami dni, opuszczaj¹c indeksy które w "kalendarzu œciennym" nie maj¹ ¿adnego daty w danym miesi¹cu i roku.
	 * @param year rok, dla którego wyliczana jest tablica dni
	 * @param month miesi¹c, dla którego wyliczana jest tablica dni
	 * @return tablica dni
	 */
	public String[] updateCalendar(int year, int month) {
		int day = 0;
		int daysInMonth = 0;
		
		if (month == 1 && leapYear(year)) {
			daysInMonth = 29;
		} else if (month == 1) {
			daysInMonth = 28;
		} else {
			daysInMonth = monthDays[month];
		}
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1);

		int day_of_week = c.get(Calendar.DAY_OF_WEEK);
		day = Math.floorMod(day_of_week - 2, 7);

		for (int i = 0; i < dayButtonsText.length; i++) {
			dayButtonsText[i] = "";
		}

		for (int i = 1, j = day; i <= daysInMonth; i++, j++) {
			dayButtonsText[j] = Integer.toString(i);
		}
		
		return dayButtonsText;
	}
	
	/**
	 * Pobiera liczbowy numer dzisiejszego dnia.
	 * @return liczby numer dzisiejszego dnia
	 */
	public int getTodaysDay() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0); // same for minutes and seconds
		
		int todaysDay = today.get(Calendar.DAY_OF_MONTH);
		return todaysDay;
	}
	
	/**
	 * Sprawdza czy dany rok jest przestêpny.
	 * @param year sprawdzany rok
	 * @return wartoœæ bool
	 */
	private boolean leapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return true;
		}
		return false;
	}
}
