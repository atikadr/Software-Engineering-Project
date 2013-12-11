import java.util.*;
import java.util.regex.Pattern;


public class Venue {

	//data fields
	private String name;
	private int price;
	private ArrayList<Tslot> tslotList = new ArrayList<Tslot>(); //Tslot includes Date, Timing
	//MUST INITIALIZE ArrayList or will get NullPointerException

	private int id, idLink;
	private int rememberTslotStart;
	private int rememberTslotSize;
	
	//constructors
	public Venue(){}
	public Venue(String n, int p){
		name = n;
		price = p;
	}
	
	public Venue(int id, String n, int p){
		this.id = id;
		name = n;
		price = p;
	}	
	
	//setters
	public void setTL(ArrayList<Tslot> TL) {
		tslotList = TL;
		//int i;
		//for (i = 0 ; i < TL.size() ; i++)
			//tslotList.add(new Tslot(TL.get(i).getYear(), TL.get(i).getMonth(), TL.get(i).getDay(), TL.get(i).getHour()));
	}


	public void setAvailable(int i) {tslotList.get(i).setAvailable();}
	public void setNotAvailable(int i) {tslotList.get(i).setNotAvailable();}
	//public void setDisplay(boolean i) {displayed = i;}

	public boolean setBooked(int i) {return tslotList.get(i).setBooked();}
	public boolean setNotBooked(int i) {return tslotList.get(i).setNotBooked();}
	//public void setDisplay(boolean i) {displayed = i;}

	public int getTslotListIndex(String d, int h) { //This is not a setter but a getter. But I am putting it here because getters need it.
    	final String slashRegex = "/"; //Weird. "private static" not allowed.
    	Pattern slashPattern = Pattern.compile(slashRegex);
    	String[] dateElementsStr = slashPattern.split(d); //Format is DD/MM/YYYY
    	int[] dateElements = new int[3];
    	for (int i=0; i<dateElementsStr.length; ++i) {dateElements[i] = Integer.parseInt(dateElementsStr[i]);}
    	for (int i=0; i<tslotList.size(); ++i){
    		if ( dateElements[2] == tslotList.get(i).getYear() &&
    		dateElements[1] == tslotList.get(i).getMonth() &&
    		dateElements[0] == tslotList.get(i).getDay() && h == tslotList.get(i).getHour() ){
    			return i;
    		}
    	} return 0;
	}

	public int getTslotListIndex(int y, int m, int d, int h) { //This is not a setter but a getter. But I am putting it here because getters need it.
    	for (int i=0; i<tslotList.size(); ++i){
    		if ( y == tslotList.get(i).getYear() &&
    		m == tslotList.get(i).getMonth() &&
    		d == tslotList.get(i).getDay() &&
    		h == tslotList.get(i).getHour() ){
    			return i;
    		}
    	} return 0;
	}

	
	public boolean setBooked(String d, int h) {
		int i = getTslotListIndex(d,h);
		return tslotList.get(i).setBooked();
	}	
	public boolean setNotBooked(String d, int h) {
		int i = getTslotListIndex(d,h);
		return tslotList.get(i).setNotBooked();
	}
	
	public String setName(String n){name = n; return name;}
	public int    setPrice(int p){price = p; return price;} //Price is in cents. $1 => int 100

	public Tslot setTslot(int index, Tslot t) {tslotList.set(index, t); return tslotList.get(index);}
	//public Activity setActivity(int index, activity a){activityList.set(index, a); return activityList.get(index);}
	
	public ArrayList<Tslot> setTslotList(ArrayList<Tslot> tL){tslotList = tL; return tslotList;}
	//public ArrayList<Activity> setActivityList(ArrayList<activity> aL){activityList = aL; return activityList;}
	
	public void addTslot(Tslot t) {tslotList.add(t);}
	//public void addActivity(Activity a) {activityList.add(a);}
	public void removeTslot(int i) {tslotList.remove(i);}
	//public void removeActivity(int i) {activityList.remove(i);}
	
	
	
	//getters
	public String getName(){return name;}
	public int  getPrice(){return price;}
	public String getPriceToString() {return "$" + intToStr(price);}
	public ArrayList<Tslot> getTslotList(){return tslotList;}
	//public ArrayList<Activity> getActivityList(){return activityList;}
	public Tslot getTslotList(int i){return tslotList.get(i);}
	//public Activity getActivityList(int i){return activityList.get(i);}
	public Tslot getTslotList(int y, int m, int d, int h){
		int i = getTslotListIndex(y,m,d,h);
		return	tslotList.get(i);
	}

	public Tslot getTslotList(String date, int h){
		int i = getTslotListIndex(date,h);
		return	tslotList.get(i);
	}
	
	//methods
	// private as this method is meant to be used only within the class scope
    private String intToStr(int money){
    	int dollar, cents;
    	dollar = money/100;
    	cents = money%100;
    	if (cents < 10)
    		return Integer.toString(dollar) + ".0" + Integer.toString(cents);
    	else
    		return Integer.toString(dollar) + "." + Integer.toString(cents);
    }
    
    public double getUnitPrice() {
    	return Double.parseDouble(intToStr(price));
    }    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}    
	
	public int getIdLink() {
		return idLink;
	}
	public void setIdLink(int idLink) {
		this.idLink = idLink;
	}    
    
	public void rememberTslotStart(int tslotIndex) {
		this.rememberTslotStart = tslotIndex;
	}
	public int rememberedTslotStart() {
		return this.rememberTslotStart;
	}
	
	public void rememberTslotSize(int numofIndex) {
		this.rememberTslotSize = numofIndex;
	}
	public int rememberedTslotSize() {
		return this.rememberTslotSize;
	}
	
}




