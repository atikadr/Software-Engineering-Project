import java.util.ArrayList;

import com.ibm.icu.text.DecimalFormat;

@SuppressWarnings("serial")
public class FundingList extends ArrayList<Funding> {
    //1 Fields, 1 inputEnabled
    private double amountTotal;

    //1 Constructor since it is an arrayList.
    public FundingList () {}
    
    //getters
    public double getAmountTotal(){
    	recalcAmounts();
    	return amountTotal;}
    
    //Methods

    public String printFunding(int i) {return get(i).printFunding();}

    public void recalcAmounts(){
       amountTotal=0;
       for (int i=0; i<size(); ++i)
    	   amountTotal += get(i).getAmount();
    }
    
    public String printAmountTotal() {
    	recalcAmounts();
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return "$" + Double.toString(Double.valueOf(twoDForm.format(amountTotal)));}

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
    
    
    
    
    //ADDITIONAL CODES:
/*  
 	//data fields  
    public String sourceEvery;
    public int needRecalc;
    
    //setters and getters
    public void addFunding(){recalcAll();}
    public String getSourceEvery(){return sourceEvery;}
    public String toString() {return amountTotal+ ' ' +sourceEvery+ "\n";}

	//methods
    public void recalcAll() {recalcAmounts(); recalcSources();}
    
    public String recalcSources(){
    sourceEvery="";
    for (int i=0; i<size(); ++i)
    {sourceEvery += get(i).getSource()+' ';}
    return sourceEvery;  }
   
    public double toDollar(int int_amount) {return int_amount/100.00;}

   
    */

}
