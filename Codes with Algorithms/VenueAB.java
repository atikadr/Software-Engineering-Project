import java.util.*;

//VenueList ==> Create two VenueList variables for each Venue, namely availableVenueList and bookedVenueList.

public class VenueAB { //Name stands for: Venue Available (one master) & Booked-lists (multiple, one for each organizer)
//There will be a GLOBAL AvailableVenue and AvailableVenueList
//Each Event Organizer should have one booked element for THIS Venue. So that each Organizer can keep track of his unique booked Venues.

	//2 Fields, 2 inputEnabled
	public Venue available; //This is the global availableVenue.
	public ArrayList<Venue> booked = new ArrayList<Venue>(); //MUST INITIALIZE or will get NullPointerException //Each Event Organizer will have a unique element number.

	//2 Constructors
	public VenueAB () {}

	public VenueAB (Venue a) {
		int numOrganizers = 1; //TODO
		available = a;
		for (int i=0; i<numOrganizers; ++i) {booked.add(a);} //set (index, object_element) is an ArrayList class method.
	}
		
	public VenueAB (Venue a, int numOrganizers) {
		available = a;
		for (int i=0; i<numOrganizers; ++i) {booked.add(a);} //set (index, object_element) is an ArrayList class method.
	}

	public void initializeTL(int y, int m, int d){
		for (int h = 0 ; h < 24 ; ++h) {
			available.addTslot(new Tslot(y,m,d,h));
			for (int i=0; i<booked.size(); ++i) {booked.get(i).addTslot(new Tslot(y,m,d,h));}
		}
	}
	
	public void initializeTL(int y, int m){
		if (m==2) {for(int d=1;d<28;++d) { initializeTL(y, m, d); }}
		else if (m==4 || m==6 || m==9 || m==11) { for(int d=1;d<30;++d){ initializeTL(y, m, d); }}
		else if (m==1 || m==3 || m==5 || 
				m==7 || m==8 || m==10 || m==12) { for(int d=1;d<30;++d){ initializeTL(y, m, d); }}
	}

	public void initializeTL(int y){
		for (int m = 1 ; m < 12 ; ++m) {initializeTL(y,m);}
	}
	
	//Note: Setters and Getters are inheritted from class Venue and ArrayList.
	//Please refer to class Venue and ArrayList for more information.

	//bookSlot and unbookSlot are NOT add and remove.
        //The array element remains but the properties isAvailable n isBooked change, AS WELL AS corresponding GUI checkbox change to "DONOTSHOW".
	
	public String availableSlot(int i, int j) { //Two indexes. First indicates ORGANIZER_num, second indicates Tslot_Array element
		if (available.getTslotList(j).isAvailable()) {
		   booked.get(i).getTslotList(j).setBooked();
		   available.getTslotList(j).setNotAvailable();
		   return "Booking successfully made!";
		}
		else if (!(available.getTslotList(i).isAvailable())) {
		   return "Booking failed. Time slot is not available.";
		}
		String errorMsg = "error";
		return errorMsg;
	}

	
	

	public String bookSlot(int i, int j) { //Two indexes. First indicates ORGANIZER_num, second indicates Tslot_Array element
		if (available.getTslotList(j).isAvailable()) {
		   booked.get(i).getTslotList(j).setBooked();
		   available.getTslotList(j).setNotAvailable();
		   return "Booking successfully made!";
		}
		else if (!(available.getTslotList(i).isAvailable())) {
		   return "Booking failed. Time slot is not available.";
		}
		String errorMsg = "error";
		return errorMsg;
	}

	public String bookSlot(int i, String d, int h) { //Two indexes. First indicates ORGANIZER_num, second indicates Tslot_Array element
		int j = available.getTslotListIndex(d,h);
		return bookSlot(i,j);
	}
	
	public String unbookSlot(int i, int j) {
		if (booked.get(i).getTslotList(j).isBooked()) {
		   booked.get(i).getTslotList(j).setNotBooked();
		   available.getTslotList(j).setAvailable();
		   return "Booking successfully undone!";
		}
		else if (!(booked.get(i).getTslotList(j).isBooked())) {
		   return "No booking was made in the first place, so it cannot be 'undone'.";
		}
		String errorMsg = "error";
		return errorMsg;
	}

	public String unbookSlot(int i, String d, int h) { //Two indexes. First indicates ORGANIZER_num, second indicates Tslot_Array element
		int j = available.getTslotListIndex(d,h);
		return unbookSlot(i,j);
	}

	
	public void recalcAvailable(Venue a) { //For use when local-harddisk AvailableVenue is obtained from text-file / online-database AvailableVenue.
		for (int j=0; j<a.getTslotList().size(); ++j) {
		   if (available.getTslotList(j).isAvailable() != a.getTslotList(j).isAvailable()) { //Cannot direct copy over the AvailableVenue because it will overwrite isBooked.
		      if(a.getTslotList(j).isAvailable()){for(int i=0;i<booked.size();++i){booked.get(i).getTslotList(j).setAvailable();}}
		      else   {for(int i=0;i<booked.size();++i){booked.get(i).getTslotList(j).setNotAvailable();}}
		   }
		}
		available = a;
	} //numOrganizers issue: Alternatively could do a ifnotdefined, define it, then single-recursive-call the same function.


	public void addNewBookedforNewOrgs(int addlNum) {
		int oldBookedSize = booked.size();
		for (int i=0; i<addlNum; ++i) {
		   addNewBookedforNewOrg (i+oldBookedSize);
		}
	}
	public void addNewBookedforNewOrg(int newOrgID) {
		if (booked.size() == newOrgID) { //newOrgID should be ++ of the biggest orgID before the new org was created.
		   booked.set(newOrgID, available);
		}
	}

}

