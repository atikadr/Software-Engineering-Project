
public class Participant {
	//data fields
	String name = "";
	String ID = "";
	String contactNumber = "";
	String emailAdd = "";
	boolean hasPaid = false;
	
	//constructors
	public Participant(String n, String id, String contact, String email){
		name = n;
		ID = id;
		contactNumber = contact;
		emailAdd = email;
	}
	
	public Participant(String n, String id, String contact, String email, boolean bool){
		if (n.equals("")) name = "-";
		else	name = n;
		
		if (id.equals("")) ID = "-";
		else 	ID = id;
		
		if (contact.equals("")) contactNumber = "-";
		else 	contactNumber = contact;
		
		if (email.equals("")) emailAdd = "-";
		else emailAdd = email;
		
		hasPaid = bool;
	}
	
	//setters and getters
	public String getName() {return name;}
	public String getID() {return ID;}
	public String getContact() {return contactNumber;}
	public String getEmail() {return emailAdd;}
	public void setPaid(boolean bool){hasPaid = bool;}
	public boolean getPaid() {return hasPaid;}
	
	public void setName(String n) {name = n;}
	public void setID(String id) { ID = id;}
	public void setContact(String contact) { contactNumber = contact;}
	public void setEmail(String email) {emailAdd = email;}

}
