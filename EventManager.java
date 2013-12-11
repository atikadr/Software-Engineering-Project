import java.io.IOException;
import java.io.File;


public class EventManager {
	
	public static void eventCreator(String eventName, EventList el) {
		File FL, IL, PF, PL, BL;
			//new text file for funding list
			FL = new File(eventName + "FL.txt");

			//new text file for item list
			IL = new File(eventName + "IL.txt");
			
			//new text file for program flow
			PF = new File(eventName + "PF.txt");
			
			//new text file for participant list
			PL = new File(eventName + "PL.txt");
			
			//new text file for booking list
			BL = new File(eventName + "BL.txt");
			
			try {
				FL.createNewFile();
				IL.createNewFile();
				PF.createNewFile();
				PL.createNewFile();
				BL.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		el.add(new Event(eventName));
		WriteFile.writeEventList(el);
	}
	
	public static void eventSwitch(String eventName, FundingList fl, ItemList il, ProgramFlow pf, ParticipantList pl, BookingList bl) {
		fl.clear(); il.clear(); pf.clear(); pl.clear(); bl.clear();
		ReadFile.readFile(fl, eventName + "FL.txt");
		ReadFile.readFile(il, eventName + "IL.txt");
		ReadFile.readFile(pf, eventName + "PF.txt");
		ReadFile.readFile(pl, eventName + "PL.txt");
		ReadFile.readFile(bl, eventName + "BL.txt");
		//ReadFile.readVenueList(vl);
		//for(int i = 0 ; i < vl.size() ; i++)
			//ReadFile.readVenueTimeSlot(vl.get(i));
	}
	
	public static void eventWrite(String eventName,FundingList fl, ItemList il, ProgramFlow pf, ParticipantList pl, BookingList bl, VenueList vl) {
		WriteFile.writeFile(fl, eventName + "FL.txt");
		WriteFile.writeFile(il, eventName + "IL.txt");
		WriteFile.writeFile(pf, eventName + "PF.txt");
		WriteFile.writeFile(pl, eventName + "PL.txt");
		WriteFile.writeFile(bl, eventName + "BL.txt");
		WriteFile.writeVenueList(vl);
		System.out.println("Write vl succesful? " + WriteFile.writeVenueList(vl));
	}
	
	public static void eventDelete(String eventName, EventList el, VenueList vl){
		System.out.println("eventDelete() -eventName:"+eventName);
		//delete event's attributes from database
		File FL = new File(eventName + "FL.txt");
		File IL = new File(eventName + "IL.txt");
		File PF = new File(eventName + "PF.txt");
		File PL = new File(eventName + "PL.txt");
		File BL = new File(eventName + "BL.txt");
		
		BookingList bl = new BookingList();
		ReadFile.readFile(bl, eventName + "BL.txt");
		
		//delete vl timeslots Booked
		for(int i=0; i<bl.size(); i++) {
		
		String name = bl.get(i).getName();
		int date = bl.get(i).getDate();
		int timeStart = bl.get(i).getTimeStart();
		int timeEnd = bl.get(i).getTimeEnd();
					
			for(int j=0; j<vl.size(); j++)
			{
				if(vl.get(j).getName().equals(name))
				{
					for(int k=0; k<vl.get(j).getTS().size(); k++)
					{
						if(vl.get(j).getTS().get(k).getDate() == date && vl.get(j).getTS().get(k).getHour() >= timeStart && vl.get(j).getTS().get(k).getHour() < timeEnd )
							vl.get(j).getTS().get(k).setBooked(false);					
					}
				}
			}							
		}
		WriteFile.writeVenueList(vl);
		
		try {
			System.out.println("FL:"+FL.getCanonicalPath());  
			boolean deletingFL = FL.delete();
			System.out.println("Deleting FL file result:"+deletingFL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IL.delete();
		PF.delete();
		PL.delete();
		BL.delete();
		
		//delete event from event list and update it in the database
		for (int i = 0 ; i < el.size() ; i++) {
			if (eventName.equals(el.get(i).getName()))
				el.remove(i);
		}
		WriteFile.writeEventList(el);
	}

}
