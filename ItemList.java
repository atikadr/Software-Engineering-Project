import java.util.ArrayList;

@SuppressWarnings("serial")
public class ItemList extends ArrayList<Item>{
	//data fields
	private double amountTotal;
	
	//1 Constructor since it is an arrayList.
    public ItemList () {}
    
    //setters and getters
    public void setPrice(int i, String p){get(i).setPrice(p);}
    public void setQty(int i, int q){get(i).setQty(q);}
    public double getAmountTotal(){
    	recalcAmounts();
    	return amountTotal;}
    
    //methods
    public void recalcAmounts(){
        amountTotal=0;
        for (int i=0; i<size(); ++i)
     	   amountTotal += get(i).getTotalPrice();
     }
    
    public String printAmountTotal() {
    	recalcAmounts();
    	return "$" + Double.toString(amountTotal);}
    
    public String getItemName(int i){return get(i).getName();}
    public String getItemActivity(int i){return get(i).getActivity();}
    public String getItemQty(int i){return get(i).getQtyToString();}
    public String getItemPrice(int i){return get(i).getPriceToString();}
    public String getItemTotalPrice(int i){return "$" + get(i).getTotalPriceToString();}
    
    //money conversion methods
    public String intToStr(int money){
    	int dollar, cents;
    	dollar = money/100;
    	cents = money%100;
    	if (cents < 10)
    		return Integer.toString(dollar) + ".0" + Integer.toString(cents);
    	else
    		return Integer.toString(dollar) + "." + Integer.toString(cents);
    }
    

}
