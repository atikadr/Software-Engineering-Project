public class Item {
	//data fields
	private String name;
	private int qty;
	private double price;
	private String activity;
	
	//constructors
	public Item(){}
	public Item(String n, String a){
		if (n.equals("")) name = "-";
		else name = n;
		qty = 0;
		price = 0;
		if (a.equals("")) activity = "Miscellaneous";
		else activity = a;
	}
	public Item(String n, String a, int q, double p){
		if (n.equals("")) name = "-";
		else name = n;
		qty = q;
		price = p;
		if (a.equals("")) activity = "Miscellaneous";
		else activity = a;
	}
	
	//setter as getter methods
	
	public void setQty(int q){qty = q;}
	public void setPrice(String p){price = strToDouble(p);}
	public String getName(){return name;}
	public String getActivity(){return activity;}
	public int getQty(){return qty;}
	public double getPrice(){return price;}
	public double getTotalPrice(){return price*qty;}
	
	//toString methods
	public String getQtyToString() {Integer i = new Integer(qty); return i.toString();}
	public String getPriceToString() {return doubleToStr(price);}
	public String getTotalPriceToString() {return doubleToStr(price*qty);}
	
	
	//merging 2 Items of same name and price (case insensitive)
	//param: 2 Items of same name and price (case insensitive)
	//return: an Item class of the name and price and combined quantity
	//precond: must be same name and price, an Item instance is not needed, static method
	//post cond: the new Item instance is returned, returns null if Items are not of same name and price (case insensitive)
	public static Item mergeItem(Item a, Item b){
		if( (a.getPrice() == b.getPrice()) && (a.getName().equalsIgnoreCase(b.getName())) ){
			Item newItem = new Item(a.getName(), a.getActivity(), a.getQty()+b.getQty(), a.getPrice());
			return newItem;
		}
		else return null;
	}
	
	
	//money conversion methods
    public int strToInt(String money){
    	int dollar, cents;
    	dollar = Integer.parseInt(money.substring(0,money.length()-2));
		cents = Integer.parseInt(money.substring(money.length()-2));
		return (dollar*100 + cents);
    }
    
    public String doubleToStr (double money){
    	return Double.toString(money);
    }
    
    public double strToDouble (String money){
    	double ans = Double.parseDouble(money);
    	return ((int) (ans * 100)) / 100.0;
    }
    
    public String intToStr(int money){
    	int dollar, cents;
    	dollar = money/100;
    	cents = money%100;
    	if (cents < 10)
    		return Integer.toString(dollar) + ".0" + Integer.toString(cents);
    	else
    		return Integer.toString(dollar) + "." + Integer.toString(cents);
    }
	

	//ADDITIONAL CODES:
	
	//public void setName(String n){name = n;}
	/*
	public Item(String n, int p){
		name = n;
		price = p;
		qty = 1;
	}
	*/
	
}