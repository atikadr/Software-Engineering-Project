public class Funding {
    //2 Fields, 2 inputEnabled
    private double amount;
    private String source;
    
    //2 Constructors
    public Funding () {}
    
    public Funding (String inp_amount, String inp_source) {
    	if (inp_source.equals("")) source = "-";
    	else source = inp_source;
        amount = strToDouble(inp_amount);
    }
    
    //getters
    public double    getAmount(){return amount;}
    
    //Methods
    public String printFunding() {return "$" + Double.toString(amount)+ " by " +source+ "\n";}
    
    
    
    //money conversion methods
    public int strToInt (String money){

    	int dollar, cents;
    	dollar = Integer.parseInt(money.substring(0,money.length()-2));
		cents = Integer.parseInt(money.substring(money.length()-2));
		return (dollar*100 + cents);
    }
    
    public double strToDouble (String money){
    	double ans = Double.parseDouble(money);
    	return ((int) (ans * 100)) / 100.0;
    }
    
    
    
 /*   public String intToStr(int money){
    	int dollar, cents;
    	dollar = money/100;
    	cents = money%100;
    	if (cents < 10)
    		return Integer.toString(dollar) + ".0" + Integer.toString(cents);
    	else
    		return Integer.toString(dollar) + "." + Integer.toString(cents);
    }
    
*/    
    
    
    //public String toDollarSign(double dbl_amount) {return '$' + "NOOOOOOO";}
    //public String toDollarSign(int int_amount) {return '$' + Integer.toString(int_amount/100)+ '.' + Integer.toString(int_amount%100);}
    
    //ADDITIONAL CODE:
    public String getSource(){return source;}
}