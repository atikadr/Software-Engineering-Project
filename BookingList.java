import java.util.*;
public class BookingList extends ArrayList<Booking>{
	
	public BookingList(){}

	public int has(Booking b){
		int result = -1;
		for (int i = 0; i<size() ;i++){
			if (this.get(i).getName()==b.getName() && this.get(i).getDate()==b.getDate() && this.get(i).getTimeStart() == b.getTimeStart() && this.get(i).getTimeEnd() == b.getTimeEnd()) {
				result = i;
				break;
			}
		}
		return result;
	}
}
