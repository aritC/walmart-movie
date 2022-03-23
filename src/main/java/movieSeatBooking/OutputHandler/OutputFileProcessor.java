package movieSeatBooking.OutputHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import movieSeatBooking.Configurations;

public class OutputFileProcessor implements IOutputProcessor{

	public void getResponse(List<BookingResponse> responses) {

		try {
			String fileName = Configurations.OUTPUT_DIR + "\\"+"output_"+new Date().getTime()+".txt";
			File myObj = new File(fileName);
			if (myObj.createNewFile()) {
				System.out.println("Output File Found In "+fileName);
				FileWriter myWriter = new FileWriter(fileName);
				for(BookingResponse response: responses) {
					myWriter.write(response+"\n");
				}
				
				myWriter.close();
			}
		} catch (IOException e) {
			System.out.println("Output File Write Error Occured.");
			e.printStackTrace();
		}

	}

	public void getErrors(List<String> errors) {
		try {
			String fileName = Configurations.OUTPUT_DIR + "\\"+"error_"+new Date().getTime()+".txt";
			File myObj = new File(fileName);
			if (myObj.createNewFile()) {
				System.out.println("Error File Found In "+fileName);
				FileWriter myWriter = new FileWriter(fileName);
				for(String error: errors) {
					myWriter.write(error+"\n");
				}
				
				myWriter.close();
			}
		} catch (IOException e) {
			System.out.println("Error File Write Error Occured.");
			e.printStackTrace();
		}

	}



}
