package movieSeatBooking.InputHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.InvalidRequestIdException;

public class BookingRequest  {
	private String requestId;
	private int noOfSeats;

	public BookingRequest(String requestId, int noOfSeats) throws InvalidRequestIdException{

		if(checkRequestId(requestId)) {
			this.requestId = requestId;
			this.noOfSeats = noOfSeats;
		}else {
			throw new InvalidRequestIdException("Request Id: "+ requestId +" is Invalid");
		}
	}

	@Override public String toString() {
		return String.format("Request ID: %s\t No of Seats: %d", this.requestId, this.noOfSeats);
	}


	public String getRequestId() {
		return requestId;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public boolean checkRequestId(String requestId) {
		String regex = "^R[0-9]{4}$";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(requestId);
		
		return matcher.find();
	}


}
