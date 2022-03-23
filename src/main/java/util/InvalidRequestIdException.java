package util;

@SuppressWarnings("serial")
public class InvalidRequestIdException extends RuntimeException{
	
	public InvalidRequestIdException(String message) {
		super(message);
	}

}
