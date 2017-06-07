package model;

import java.util.Calendar;

/**
 * Klasa zarz�dzaj�ca danymi znajduj�cymi w g��wnej ramce kalendarza.
 * @author auu78
 *
 */
public class CalendarModel {
	private String monthsNames[] = { "Stycze�", "Luty", "Marzec", "Kwiecie�", "Maj", "Czerwiec", "Lipiec", "Sierpie�",
			"Wrzesie�", "Pa�dziernik", "Listopad", "Grudzie�" };
	private String[] dayNames = { "PN", "WT", "�R", "CZW", "PT", "SOB", "ND" };
	private String[] dayButtonsText = new String[42];
	private int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	/**
	 * Pobiera tablic� nazw miesi�cy.
	 * @return tablica nazw miesi�cy
	 */
	public String[] getMonthsNames() {
		return monthsNames;
	}

//	public void setMonthsNames(String[] monthsNames) {
//		this.monthsNames = monthsNames;
//	}

	/**
	 * Pobiera tablic� ilo�ci dni dla ka�dego miesi�ca.
	 * @return tablica ilo�ci dni
	 */
	public int[] getMonthDays() {
		return monthDays;
	}

	/**
	 * Pobiera tablic� nazwy dni.
	 * @return tablica nazw dni
	 */
	public String[] getDayNames() {
		return dayNames;
	}

//	public void setDayNames(String[] dayNames) {
//		this.dayNames = dayNames;
//	}

	/**
	 * Pobiera tablic� lat od 1900 do 2100.
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
	 * Pobiera tablic� z warto�ciami dni, opuszczaj�c indeksy kt�re w "kalendarzu �ciennym" nie maj� �adnego daty w danym miesi�cu i roku.
	 * @param year rok, dla kt�rego wyliczana jest tablica dni
	 * @param month miesi�c, dla kt�rego wyliczana jest tablica dni
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
	 * Sprawdza czy dany rok jest przest�pny.
	 * @param year sprawdzany rok
	 * @return warto�� bool
	 */
	private boolean leapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return true;
		}
		return false;
	}
}
