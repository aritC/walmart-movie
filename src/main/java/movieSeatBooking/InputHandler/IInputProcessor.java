package movieSeatBooking.InputHandler;

import java.util.List;

public interface IInputProcessor {
	public List<BookingRequest> getRequests(String path);
}
