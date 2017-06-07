package model;

/*
 * Klasa przechowuj¹ca dane do wyœwietlenia podczas tworzenia nowego wydarzenia.
 */
public class AddEventModel {
	private Integer alarmTimes[] = { 0, 5, 10, 30, 60, 180, 360, 720, 1440 };
	private String alarmNames[] = {"Wy³¹czony", "5 min przed", "10 min przed", "30 min przed", "1 godz przed", 
		"3 godz przed", "6 godz przed", "12 godz przed", "24 godz przed" };
	
	/**
	 * Pobiera nazwy s³owne dla kazdej opcji alarmu.
	 * @return tablica nazw dla alarmów
	 */
	public String[] getAlarmNames() {
		return alarmNames;
	}
	
	/**
	 * Pobiera czasy alarmów, ile przed dat¹ wydarzenia maj¹ byæ w³¹czone.
	 * @return tablica czasów alarmów
	 */
	public Integer[] getAlarmTimes(){
		return alarmTimes;
	}
}