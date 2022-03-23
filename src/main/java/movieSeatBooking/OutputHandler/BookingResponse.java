package movieSeatBooking.OutputHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movieSeatBooking.Configurations;

public class BookingResponse {
	private String requestId;
	private HashMap<Integer, List<Integer>> bookedSeats;
	
	public BookingResponse(String requestId){
		this.requestId = requestId;
		bookedSeats = new HashMap<Integer,List<Integer>>();
	}
	
	public BookingResponse setBookedSeats(HashMap<Integer, List<Integer>> bookingResponses) {
		
		for(Map.Entry<Integer, List<Integer>> bookingResponse: bookingResponses.entrySet() ) {
			int row = bookingResponse.getKey();
			
			if(bookedSeats.containsKey(row)) {
				List<Integer> temp = bookedSeats.get(row);
				System.out.println("temp List: "+ temp+ "adadasdas:\n"+bookedSeats.get(row));
				temp.addAll(bookingResponse.getValue());
				bookedSeats.put(row, temp);
			}else {
				bookedSeats.put(row, bookingResponse.getValue());
			}
			
		}		
		return this;
	}
	
	@Override
	public String toString() {
		
		StringBuilder response = new StringBuilder(this.requestId);
		response.append(" ");
		if(bookedSeats.isEmpty()) {
			response.append("No Seats Available");
		}else {
			List<String> seats = new ArrayList<String>();
			for(Map.Entry<Integer, List<Integer>> bookedRow: bookedSeats.entrySet()) {
				
				int row = bookedRow.getKey();
				for(int col: bookedRow.getValue()) {
					seats.add(String.valueOf((char)('A'+(Configurations.N_ROWS-row-1)) + String.valueOf(col+1)));
				}
			}
			response.append(String.join(",", seats));
		}

	    return new String(response);
	  }

	public HashMap<Integer, List<Integer>> getBookedSeats() {
		return bookedSeats;
	}


}
