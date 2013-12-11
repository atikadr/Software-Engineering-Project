import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class WriteFile {
	public static boolean writeVenueList(VenueList vl){
		boolean result = false;
		FileWriter fstream;
		try{
			for (int i=0;i<vl.size();i++){
			fstream = new FileWriter(vl.get(i).getName()+".txt");
			BufferedWriter out = new BufferedWriter(fstream);
			
			for (int j=0; j < vl.get(i).getTS().size(); j++)
			{
				out.write(vl.get(i).getTS().get(j).getDate() + "\t" + vl.get(i).getTS().get(j).getHour() + "\t" + vl.get(i).getTS().get(j).isBooked() + "\t");
				out.newLine();
				result = true;
			}
			out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void writeFile(BookingList bookvl, String filename){
		FileWriter fstream;
		try {
			fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i=0;i<bookvl.size();i++){
				out.write(bookvl.get(i).getName() + "\t" + bookvl.get(i).getPrice() + "\t" + bookvl.get(i).getDate() + "\t" + bookvl.get(i).getTimeStart() + "\t" + bookvl.get(i).getTimeEnd() + "\t");
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeEventList(EventList el){
		FileWriter fstream;
		try{
			fstream = new FileWriter("EventList.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i=0;i<el.size();i++){
				out.write(el.get(i).getName() + "\t");
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeFile(FundingList fl, String filename){
		FileWriter fstream;
		try {
			fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i=0;i<fl.size();i++){
				out.write(fl.get(i).getAmount() + "\t" + fl.get(i).getSource() + "\t" );
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeFile(ItemList il, String filename){
		FileWriter fstream;
		try {
			fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i=0;i<il.size();i++){
				out.write(il.get(i).getName() + "\t" + il.get(i).getQty() + "\t" + il.get(i).getPrice() + "\t" + il.get(i).getActivity() + "\t" );
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeFile(ProgramFlow pf, String filename){
		FileWriter fstream;
		try {
			fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i=0;i<pf.size();i++){
				out.write(pf.get(i).getName() + "\t" + pf.get(i).getDesc() + "\t" + pf.get(i).getDate() + "\t" + pf.get(i).getTimeStart() + "\t" + pf.get(i).getTimeEnd() + "\t" );
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void writeFile(ParticipantList pl, String filename){
		FileWriter fstream;
		try {
			fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(pl.getPrice() + "\t");
			out.newLine();
			for (int i=0;i<pl.size();i++){
				out.write(pl.getName(i) + "\t" + pl.getID(i) + "\t" + pl.getContact(i) + "\t" + pl.getEmail(i) + "\t" + pl.getPaid(i) + "\t");
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
