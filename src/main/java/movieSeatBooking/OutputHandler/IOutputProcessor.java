package movieSeatBooking.OutputHandler;

import java.util.List;

public interface IOutputProcessor {
	public void getResponse(List<BookingResponse> responses);
	
	public void getErrors(List<String> errors);

}
