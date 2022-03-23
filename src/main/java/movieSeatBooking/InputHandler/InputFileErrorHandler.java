package movieSeatBooking.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class InputFileErrorHandler {
	private static List<String> errors = new ArrayList<String>();
	
	public static void setErrors(String err) {
		errors.add(err);
	}
	
	public static void duplicateError(int lineNo, String requestId) {
		errors.add("Duplicate Request Id: "+requestId +" Found at line: "+lineNo);
	}
	
	public static void formatError(int lineNo) {
		errors.add("Bad Format of Request in file at line: "+lineNo);
	}

	public static List<String> getErrors() {
		return errors;
	}
	
	public static void fileNotFoundError(String path) {
		errors.add("File Not Found Error\nPath: "+path);
	}
	
	public static void invalidRequestIdError(String message) {
		errors.add(message);
	}
	
	
}
