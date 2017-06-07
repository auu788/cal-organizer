package model;

/*
 * Klasa przechowuj�ca dane do wy�wietlenia podczas tworzenia nowego wydarzenia.
 */
public class AddEventModel {
	private Integer alarmTimes[] = { 0, 5, 10, 30, 60, 180, 360, 720, 1440 };
	private String alarmNames[] = {"Wy��czony", "5 min przed", "10 min przed", "30 min przed", "1 godz przed", 
		"3 godz przed", "6 godz przed", "12 godz przed", "24 godz przed" };
	
	/**
	 * Pobiera nazwy s�owne dla kazdej opcji alarmu.
	 * @return tablica nazw dla alarm�w
	 */
	public String[] getAlarmNames() {
		return alarmNames;
	}
	
	/**
	 * Pobiera czasy alarm�w, ile przed dat� wydarzenia maj� by� w��czone.
	 * @return tablica czas�w alarm�w
	 */
	public Integer[] getAlarmTimes(){
		return alarmTimes;
	}
}