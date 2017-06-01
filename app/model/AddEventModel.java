package model;

public class AddEventModel {
	private Integer alarmTimes[] = { 0, 5, 10, 30, 60, 180, 360, 720, 1440 };
	private String alarmNames[] = {"Wy³¹czony", "5 min przed", "10 min przed", "30 min przed", "1 godz przed", 
		"3 godz przed", "6 godz przed", "12 godz przed", "24 godz przed" };
	
	public AddEventModel() {}
	
	public String[] getAlarmNames() {
		return alarmNames;
	}
	
	public Integer[] getAlarmTimes(){
		return alarmTimes;
	}
}