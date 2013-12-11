import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class ReadFile {

	public static void readEventList(EventList el){
		File f = new File("EventList.txt");
		try{
			Scanner scan = new Scanner(f).useDelimiter("\\s*\t\\s*");
			while (scan.hasNext()){
				el.add(new Event(scan.next()));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static void readVenueList(VenueList vl){
		File f = new File("VenueList.txt");
		try{
			Scanner scan = new Scanner(f).useDelimiter("\\s*\t\\s*");
			while (scan.hasNext()){
				vl.add(new Venue(scan.next(), scan.nextDouble()));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readVenueTimeSlot(Venue venue){
		File f = new File(venue.getName() + ".txt");
		try{
			Scanner scan = new Scanner(f).useDelimiter("\\s*\t\\s*");
			while (scan.hasNext()){
				venue.getTS().add(new TimeSlot(scan.nextInt(), scan.nextInt(), scan.nextBoolean()));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readFile(BookingList bookvl, String filename){
		File f = new File(filename);
		try {
			Scanner scan = new Scanner(f).useDelimiter("\\s*\t\\s*");

			while(scan.hasNext()){
				bookvl.add(new Booking(scan.next() , scan.nextDouble(), scan.nextInt(), scan.nextInt(), scan.nextInt()));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readFile(FundingList fl, String filename){
		File f = new File(filename);
		try {
			Scanner scan = new Scanner(f).useDelimiter("\\s*\t\\s*");

			while(scan.hasNext()){
				fl.add(new Funding(Double.toString(scan.nextDouble()), scan.next()));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

	public static void readFile(ItemList il, String filename){
		File f = new File(filename);
		try {
			Scanner scan = new Scanner(f).useDelimiter("\\s*\t\\s*");

			while(scan.hasNext()){
				String n = scan.next();
				int q = scan.nextInt();
				double p = scan.nextDouble();
				String a = scan.next();
				il.add(new Item(n,a, q, p));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}

	public static void readFile(ProgramFlow pf, String filename){
		File f = new File(filename);
		try {
			Scanner scan = new Scanner(f).useDelimiter("\\s*\t\\s*");

			while(scan.hasNext()){
				String n = scan.next();
				String d = scan.next();
				int da = scan.nextInt();
				int ts = scan.nextInt();
				int te = scan.nextInt();
				pf.add(new Activity(n,d, ts, te, da));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	public static void readFile(ParticipantList pl, String filename){
		File f = new File(filename);
		try {
			Scanner scan = new Scanner(f).useDelimiter("\\s*\t\\s*");
			if(scan.hasNext())
			{
				double price = scan.nextDouble();
				pl.setPr(price);
			}
			while(scan.hasNext()){
				String name = scan.next();
				String id = scan.next();
				String contact = scan.next();
				String email = scan.next();
				String paid = scan.next();
				if (paid.equals("true")) 
					pl.add(new Participant(name, id, contact, email, true));
				else pl.add(new Participant(name, id, contact, email, false));
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
