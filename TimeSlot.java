
public class TimeSlot {
	//data fields
	int date;
	int hour;
	boolean booked = false;
	
	//constructor
	public TimeSlot(int d, int h, boolean b){
		date = d;
		hour = h;
		booked = b;
	}
	
	//setters and getters
	public void setBooked(boolean b) {booked = b;}
	//public void setDate()
	//public void
	
	public boolean isBooked(){return booked;}
	public int getDate(){return date;}
	public int getHour(){return hour;}

}
