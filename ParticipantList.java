import java.util.ArrayList;

@SuppressWarnings("serial")
public class ParticipantList extends ArrayList<Participant>{
	//data fields
	double price = 0;
	double totalPrice = 0;
	
	//constructor
	public ParticipantList() {}
	
	//setters and getters
	public String getName(int i) {return get(i).getName();}
	public String getID(int i) {return get(i).getID();}
	public String getContact(int i) {return get(i).getContact();}
	public String getEmail(int i) {return get(i).getEmail();}
	public void setPaid(boolean bool, int i){get(i).setPaid(bool); calculateIncome();}
	public boolean getPaid(int i) {return get(i).getPaid();}
	public void setPrice(String p) {
		price = strToDouble(p);
		calculateIncome();
	}
	public void setPr(double p){
		price = p;
		calculateIncome();
	}
	public double getPrice() {return price;}
	public String printTotalPrice() {
		calculateIncome();
		return "$" + Double.toString(totalPrice);}
	public String printPrice() {return "$" + Double.toString(price);}
	
	//methods
	public void calculateIncome() {
		int numPaid = 0;
		for (int i = 0 ; i < size(); i++)
			if (getPaid(i) ==  true)
				numPaid++;
		
		totalPrice = price*numPaid;
	}
	
	 public double strToDouble (String money){
	    	double ans = Double.parseDouble(money);
	    	return ((int) (ans * 100)) / 100.0;
	    }

}
