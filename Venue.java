
import java.util.*;

public class Venue {
	//data fields
	private String name;
	private double price;
	private ArrayList<TimeSlot> tS = new ArrayList<TimeSlot>();
	
	//constructors
	public Venue(String n, double p){
		name = n;
		price = p;
	}
	
	//setters and getters
	public String getName(){return name;}
	public double getPrice(){return price;}
	public ArrayList<TimeSlot> getTS(){return tS;}

}
