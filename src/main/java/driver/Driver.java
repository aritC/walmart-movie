package driver;


import movieSeatBooking.InputHandler.InputFileProcessor;
import movieSeatBooking.OutputHandler.BookingResponse;
import movieSeatBooking.OutputHandler.IOutputProcessor;
import movieSeatBooking.OutputHandler.OutputFileProcessor;

import java.util.List;

import movieSeatBooking.MovieTheater;
import movieSeatBooking.InputHandler.BookingRequest;
import movieSeatBooking.InputHandler.IInputProcessor;
import movieSeatBooking.InputHandler.InputFileErrorHandler;

public class Driver {

	public static void main(String[] args) {

		if (args.length > 0) {

			IInputProcessor input = new InputFileProcessor();
			IOutputProcessor output = new OutputFileProcessor();
			try {
				List<BookingRequest> requests = input.getRequests(args[0]);
				MovieTheater theater = new MovieTheater("Theater 1");
				List<BookingResponse> responses = theater.findSeats(requests);
				if(!responses.isEmpty()) {
					theater.displayTheaterStatus();
					output.getResponse(responses);
				}

				output.getErrors(InputFileErrorHandler.getErrors());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("No command line arguments found.");
		}

	}

}
