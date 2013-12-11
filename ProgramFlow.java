import java.util.*;

@SuppressWarnings("serial")
public class ProgramFlow extends ArrayList<Activity>{
	//constructors
	public ProgramFlow (){super();}
	
	//hasClash method
	//return: true if there is an activity clash
	//return: false if there is no clash
	//para: none
	//precond: program flow not empty
	//postcond: none
	public boolean hasClash(){
		for (int i =0; i< size()-1;i++){
			for (int j =i+1; j< size(); j++){
				if (get(i).getDate() == get(j).getDate() && 
					(get(i).getTimeStart() < get(j).getTimeEnd() && get(j).getTimeStart() < get(i).getTimeEnd() ||
							get(i).getTimeStart() > get(j).getTimeEnd() && get(j).getTimeStart() > get(i).getTimeEnd()))return true;
			}
		}
		return false;
	}
	
	public void arrangeByTime(){
		
		for (int i=1; i<size(); i++) {
			Activity next = new Activity();
			next = get(i);
			int j;
			for(j=i-1; j>=0 && get(j).getTimeStart() > next.getTimeStart() ;j--)
				set(j+1, get(j));
			set(j+1, next);
		}
		
		for (int i=1; i<size(); i++) {
			Activity next = new Activity();
			next = get(i);
			int j;
			for(j=i-1; j>=0 && get(j).getDate() > next.getDate() ;j--)
				set(j+1, get(j));
			set(j+1, next);
		}
	}
	
	//methods
	public String printTime(int i) {return get(i).printTime();}
	public String printActivity(int i) {return get(i).getName();}
	public String printDesc(int i) {return get(i).getDesc();}
	public void setDesc(int i, String d) {get(i).setDesc(d);}

}