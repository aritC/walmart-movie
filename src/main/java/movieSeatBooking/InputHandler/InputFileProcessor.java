package movieSeatBooking.InputHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import util.InvalidRequestIdException;


public class InputFileProcessor implements IInputProcessor {

	public List<BookingRequest> getRequests(String path){
		HashSet<String> requestIds = new HashSet<String>();
		List<BookingRequest> requests = new ArrayList<BookingRequest>();


		File myObj = new File(path);
		Scanner fileReader = null;
		int lineNo = 0;
		try {
			fileReader = new Scanner(myObj);
			while (fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
				String[] splittedData = data.split(" ");

				if(splittedData.length != 2) {
					InputFileErrorHandler.formatError(lineNo);
				}else {
					String requestId = splittedData[0];
					int seats = Integer.parseInt(splittedData[1]);

					if(requestIds.contains(requestId)) {
						InputFileErrorHandler.duplicateError(lineNo, requestId);
					}else {
						try {
						BookingRequest newRequest = new BookingRequest(requestId, seats);
						requestIds.add(requestId);
						requests.add(newRequest);
						}catch(InvalidRequestIdException e) {
							InputFileErrorHandler.invalidRequestIdError(e.getMessage());
						}
					}
				}
				lineNo++;
			}
		}catch(FileNotFoundException e) {
			InputFileErrorHandler.fileNotFoundError(path);
		}
		
		finally {
			if(fileReader != null)
				fileReader.close();
		}
		return requests;
	}

}
