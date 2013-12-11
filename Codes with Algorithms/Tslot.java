import java.util.regex.Pattern;


public class Tslot {
	//data fields
	private int year, month, day, hour;
	private boolean isAvailable;
	private boolean isBooked;

	//constructors
	public Tslot(){}
	public Tslot(int y, int m, int d, int h) {
		year = y; month = m; day = d;
		hour = h;
		isAvailable = true; isBooked = false;
	}

	public Tslot(String date, int h) {
		setDate(date); //See below for the function.
		hour = h;
		isAvailable = true; isBooked = false;
	}

	
	public boolean setAvailable() {isAvailable = true; return isAvailable;}
	public boolean setNotAvailable() {isAvailable = false; return isAvailable;}
	//NotAvailable does not mean it isBooked by this Event Organizer. It may be booked by other Event Organizer.
	public boolean setBooked()    {isAvailable = false; isBooked = true; return isBooked;}
	public boolean setNotBooked() {isAvailable = true; isBooked = false; return isBooked;}
	//isBooked is meant for individual Organizers ONLY. Not for the global AvailableVenue.

	public boolean isAvailable() {return isAvailable;}
	public boolean isBooked()    {return isBooked;}

	public void setYear(int y){year=y;}
	public void setMonth(int m){month=m;}
	public void setDay(int d){day=d;}
	public void setHour(int h){hour=h;}

	public void setDate(String date){
		Pattern slashPattern = Pattern.compile("/");
		String[] dateElementsStr = slashPattern.split(date); //Format is DD/MM/YYYY
		for (int i=0; i<dateElementsStr.length; ++i) {
			if(i==0) day = Integer.parseInt(dateElementsStr[i]);
			if(i==1) month = Integer.parseInt(dateElementsStr[i]);
			if(i==2) year = Integer.parseInt(dateElementsStr[i]);
		}
	}
	
	//public String getTslot(){return getDate() +", "+ getTime() +", "+ getMoneySign();}
	public String getDTime(){return getDate() +", "+ getTime();}
	public String getDate() {return intToZeroString(day)+"/"+ intToZeroString(month)+"/"+ year;}
	public String getTime() {return intToZeroString(hour)+":00";}

	public int getYear(){return year;}
	public int getMonth(){return month;}
	public int getDay(){return day;}
	public int getHour(){return hour;}

	

	public String intToZeroString (int num) {
		if (num <10) { return '0' +Integer.toString(num); }
		else {return Integer.toString(num); }
	}
	
}
