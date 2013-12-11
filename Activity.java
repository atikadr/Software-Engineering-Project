/*
Change log:
changed getTotalPrice to Expenditure
added var isActivity in class Tslot. Can be used for Activity.

not so impt:
Changed variable ItemList to itemList.
changed constructor for itemlist
*/


public class Activity {
	//data fields
	private String name;
	private String desc = "-";
	private int timeStart; //in the form of hhmm, no restriction on digit
	private int timeEnd; //in the form of hhmm, no digit restriction
	private int date; //in the form of ddmmyyyy

	//constructors
	public Activity(){}
	public Activity(String n, int ts, int te){
		if (n.equals("")) name = "-";
		else name = n;
		timeStart = ts;
		timeEnd = te;
	}
	public Activity(String n, String d, int ts, int te){
		if (n.equals("")) name = "-";
		else name = n;
		desc = d;
		timeStart = ts;
		timeEnd = te;
	}
	public Activity(String n, String d, int ts, int te, int da){
		if (n.equals("")) name = "-";
		else name = n;
		desc = d;
		timeStart = ts;
		timeEnd = te;
		date = da;	
	}
	public Activity(String n, int ts, int te, int da){
		if (n.equals("")) name = "-";
		else name = n;
		timeStart = ts;
		timeEnd = te;
		date = da;	
	}
	
	//setters and getters
	public String setDesc(String d){desc = d; return desc;}
	public String getName(){return name;}
	public String getDesc(){return desc;}
	public int getTimeStart(){return timeStart;}
	public int getTimeEnd(){return timeEnd;}
	public int getDate(){return date;}
	public int setDate(int d){date = d; return date;}
	
	
	//methods
	public String printTime(){
		String tshour, tsmin, tehour, temin;
		tshour = Integer.toString(timeStart/100);
		if (tshour.length() == 1) tshour = "0" + tshour;
		tsmin = Integer.toString(timeStart%100);
		if (tsmin.length() == 1) tsmin = "0" + tsmin;
		tehour = Integer.toString(timeEnd/100);
		if (tehour.length() == 1) tehour = "0" + tehour;
		temin = Integer.toString(timeEnd%100);
		if (temin.length() == 1) temin = "0" + temin;
		String ans = tshour + ":" + tsmin + "-" + tehour + ":" + temin + "\n";
		return ans;
		}
	public String prtTime(){return Integer.toString(timeStart) + "-" + Integer.toString(timeEnd) + "\n";}
	
	
	//ADDITIONAL CODES:
/*	
 	//data fields
 	private ArrayList<Item> itemList;
	
	constructor
	public Activity(String n){name = n;}
	public Activity(String n, String d, int ts, int te, ArrayList<Item> iL){
		name = n;
		desc = d;
		timeStart = ts;
		timeEnd = te;
		itemList = iL;
	}
	
	//setters and getters
	public String setName(String n){name = n; return name;}
	public int setTimeStart(int ts){timeStart = ts; return timeStart;}
	public int setTimeEnd(int te){timeEnd = te; return timeEnd;}
	public void addItem(String n){itemList.add(new Item(n));} //
	public ArrayList<Item> getItemList(){return itemList;}

	//methods
	public String printItem(){
		int i;
		String s = "";
		for (i = 0 ; i < itemList.size(); i++)
			s = itemList.get(i).getName() + "\n";
		
		return s;
		
	}
	
	public int getExpenditure(){
		int ans=0;
		for(int i=0;i< getItemList().size();i++){
			ans += getItemList().get(i).getPrice();
		}
		return ans;
	}
	
	*/
}