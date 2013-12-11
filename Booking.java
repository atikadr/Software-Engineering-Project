

public class Booking {
	//data fields
	String venueName;
	double price;
	int date;
	int timestart;
	int timeend;
	
	//constructors
	public Booking(String vn, double p, int d, int ts, int te){
		venueName = vn;
		price = p;
		date = d;
		timestart = ts;
		timeend = te;
	}
	
	public String getName(){return venueName;}
	public double getPrice(){return price;}
	public int getDate(){return date;}
	public int getTimeStart(){return timestart;}
	public int getTimeEnd(){return timeend;}

}
