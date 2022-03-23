package movieSeatBooking;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import movieSeatBooking.InputHandler.BookingRequest;
import movieSeatBooking.OutputHandler.BookingResponse;



public class MovieTheaterTest {
	
	MovieTheater underTest;
	
	@Before
	public void init() {
		underTest= new MovieTheater("test Id");
	}
	
	
	@Test
	public void itShouldBook1SeatandBlockseatsAroundit() {
		BookingRequest temp = new BookingRequest("R0001", 1);
		List<BookingRequest> book1seatRequest = new ArrayList<BookingRequest>();
		book1seatRequest.add(temp);
		
		List<BookingResponse> res = underTest.findSeats(book1seatRequest);
		
		int size = res.size();
		assertEquals(1, size);
		
		int[][] seats = underTest.getSeats();
		int mid = Configurations.N_SEATS/2;
		assertEquals(1,seats[0][mid]);
		
		if(Configurations.N_ROWS>1) {
			assertEquals(-1,seats[1][mid]);
		}
		
		int l = mid-1, r=mid+1;
		int blockCount = Configurations.N_SEATS_BUFFER;
		int totalBlocked = 0;
		while(blockCount > 0) {
			if(l>=0) {
				assertEquals(-1,seats[0][l]);
				totalBlocked++;
			}
				
			if(r <Configurations.N_SEATS) {
				assertEquals(-1,seats[0][r]);
				totalBlocked++;
			}
			l--;
			r++;
			blockCount--;
		}
		
		int[] seatsLeft = underTest.getSeatsPresentInRow();
		assertEquals(Configurations.N_SEATS -1 - totalBlocked,seatsLeft[0]);
		assertEquals(Configurations.N_SEATS -1, seatsLeft[1]);
	}
	
	
	@DisplayName("Will Block 1 Seat in Middle and Second Seat to the Right")
	@Test
	public void itShouldBlock1SeatinMiddleandAnotherToTheRight() {
		BookingRequest temp1 = new BookingRequest("R0001", 1);
		List<BookingRequest> book2seatsRequest = new ArrayList<BookingRequest>();
		book2seatsRequest.add(temp1);
		BookingRequest temp2 = new BookingRequest("R0002", 1);
		book2seatsRequest.add(temp2);
		
		List<BookingResponse> res = underTest.findSeats(book2seatsRequest);
		
		int size = res.size();
		assertEquals(2, size);
		
		int[][] seats = underTest.getSeats();
		int mid = Configurations.N_SEATS/2;
		assertEquals(1,seats[0][mid]);
		
		if(Configurations.N_ROWS>1) {
			assertEquals(-1,seats[1][mid]);
		}
		
		int l = mid-1, r=mid+1;
		int blockCount = Configurations.N_SEATS_BUFFER;
		int totalBlocked = 0;
		while(blockCount > 0) {
			if(l>=0) {
				assertEquals(-1,seats[0][l]);
				totalBlocked++;
			}
				
			if(r <Configurations.N_SEATS) {
				assertEquals(-1,seats[0][r]);
				totalBlocked++;
			}
			l--;
			r++;
			blockCount--;
		}
		
		assertEquals(1,seats[0][r]);
		assertEquals(-1, seats[1][r]);
		
		blockCount = Configurations.N_SEATS_BUFFER;
		System.out.println(r);
		r++;
		while(blockCount > 0) {
			if(r <Configurations.N_SEATS) {
				assertEquals(-1,seats[0][r]);
				totalBlocked++;
			}
			l--;
			r++;
			blockCount--;
		}
	
		int[] seatsLeft = underTest.getSeatsPresentInRow();
		assertEquals(Configurations.N_SEATS -2 - totalBlocked,seatsLeft[0]);
		assertEquals(Configurations.N_SEATS -2, seatsLeft[1]);
	}
	
	@DisplayName("Will Block 1st Seat in Middle and 2nd Seat to the Right and 3rd Seat to the Left")
	@Test
	public void itShouldBlockSeatsinMiddleLeftandRight() {
		BookingRequest temp1 = new BookingRequest("R0001", 1);
		List<BookingRequest> book2seatsRequest = new ArrayList<BookingRequest>();
		book2seatsRequest.add(temp1);
		BookingRequest temp2 = new BookingRequest("R0002", 1);
		book2seatsRequest.add(temp2);
		BookingRequest temp3 = new BookingRequest("R0003", 1);
		book2seatsRequest.add(temp3);
		
		List<BookingResponse> res = underTest.findSeats(book2seatsRequest);
		
		int size = res.size();
		assertEquals(3, size);
		
		int[][] seats = underTest.getSeats();
		int mid = Configurations.N_SEATS/2;
		assertEquals(1,seats[0][mid]);
		
		if(Configurations.N_ROWS>1) {
			assertEquals(-1,seats[1][mid]);
		}
		
		int l = mid-1, r=mid+1;
		int blockCount = Configurations.N_SEATS_BUFFER;
		int totalBlocked = 0;
		while(blockCount > 0) {
			if(l>=0) {
				assertEquals(-1,seats[0][l]);
				totalBlocked++;
			}
				
			if(r <Configurations.N_SEATS) {
				assertEquals(-1,seats[0][r]);
				totalBlocked++;
			}
			l--;
			r++;
			blockCount--;
		}
		
		assertEquals(1,seats[0][l]);
		assertEquals(-1, seats[1][l]);
		
		assertEquals(1,seats[0][r]);
		assertEquals(-1, seats[1][r]);
		
		blockCount = Configurations.N_SEATS_BUFFER;
		
		r++;
		while(blockCount > 0) {
			if(r <Configurations.N_SEATS) {
				assertEquals(-1,seats[0][r]);
				totalBlocked++;
			}
			r++;
			blockCount--;
		}
		System.out.println("l"+ l);
		
		blockCount = Configurations.N_SEATS_BUFFER;
		l--;
		while(blockCount > 0) {
			if(l>=0) {
				assertEquals(-1,seats[0][l]);
				totalBlocked++;
			}
			l--;
			blockCount--;
		}
		
	
		int[] seatsLeft = underTest.getSeatsPresentInRow();
		assertEquals(Configurations.N_SEATS -3 - totalBlocked,seatsLeft[0]);
		assertEquals(Configurations.N_SEATS -3, seatsLeft[1]);
	}
	
	
	
	
	
}
