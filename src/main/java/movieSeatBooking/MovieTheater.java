package movieSeatBooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movieSeatBooking.InputHandler.BookingRequest;
import movieSeatBooking.OutputHandler.BookingResponse;

public class MovieTheater {
	private final String theaterId;
	private int[][] seats = new int[Configurations.N_ROWS][Configurations.N_SEATS];
	private int[] seatsPresentInRow = new int[Configurations.N_ROWS];


	public MovieTheater(String theaterId) {
		this.theaterId = theaterId;
		for(int j =0;j< Configurations.N_ROWS; j++) {
			seatsPresentInRow[j] = Configurations.N_SEATS;
		}
	}

	public void displayTheaterStatus() {
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Seats Left Per Row: ");
		for(int x: this.seatsPresentInRow) {
			System.out.println(x);
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");

		System.out.println();
		for(int i =0;i< Configurations.N_ROWS; i++) {
			for(int j =0;j< Configurations.N_SEATS; j++) {
				System.out.print(seats[i][j]+"\t");
			}
			System.out.print("\n");
		}
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\t\t\t\t\t\t\t\t\t[[Screen]]");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	public void blockSeats(BookingResponse bookingResponse) {
		HashMap<Integer, List<Integer>> bookedSeats = bookingResponse.getBookedSeats();
		for(Map.Entry<Integer, List<Integer>> row: bookedSeats.entrySet() ) {

			int rowNo = row.getKey();

			for(int col: row.getValue()) {
				int rowBuffer = Configurations.N_ROW_BUFFER;
				while(rowBuffer > 0) {
					if(rowNo-rowBuffer>= 1 && this.seats[rowNo-rowBuffer][col] == 0) {
						this.seats[rowNo-rowBuffer][col] = -1;
						this.seatsPresentInRow[rowNo-rowBuffer]--;
					}

					if(rowNo+rowBuffer< Configurations.N_ROWS && this.seats[rowNo+rowBuffer][col] == 0) {
						this.seats[rowNo+rowBuffer][col] = -1;
						this.seatsPresentInRow[rowNo+rowBuffer]--;
					}
					rowBuffer--;
				}
			}

			List<Integer> columns = row.getValue();
			int left = columns.get(0)-1;
			int right = columns.get(columns.size()-1)+1;
			int blockCount = Configurations.N_SEATS_BUFFER;
			while(blockCount >0) {
				if(left >=0 && this.seats[rowNo][left] == 0) {
					this.seats[rowNo][left] = -1;
					this.seatsPresentInRow[rowNo]--;
					left--;
				}

				if(right <Configurations.N_SEATS &&this.seats[rowNo][right] == 0 ) {
					this.seats[rowNo][right] = -1;
					this.seatsPresentInRow[rowNo]--;
					right++;
				}
				blockCount--;
			}

		}
	}

	public HashMap<Integer, List<Integer>> bookSeats(int row, int startCol, int totalSeats){
		int totSeats = totalSeats;
		HashMap<Integer, List<Integer>> bookedColumns = new HashMap<Integer, List<Integer>>();

		for(int col=startCol; col<Configurations.N_SEATS&& totSeats >0; col++) {

			if(bookedColumns.containsKey(row)) {
				List<Integer> temp = bookedColumns.get(row);
				temp.add(col);
				bookedColumns.put(row, temp);
			}else {
				List<Integer> temp = new ArrayList<Integer>();
				temp.add(col);
				bookedColumns.put(row, temp);
			}

			this.seats[row][col] = 1;
			totSeats--;
		}

		this.seatsPresentInRow[row] -= totalSeats;
		return bookedColumns;
	}


	public boolean checkSeats(int row, int startCol, int totalSeats) {
		int col = startCol, count =0;
		while(count < totalSeats) {
			if(this.seats[row][col] != 0) return false;
			col++;
			count++;
		}

		return true;
	}


	public void addResponseandBlockSeats(List<BookingResponse> responses,BookingResponse newResponse, 
			HashMap<Integer, List<Integer>> bookedSeats ) {
		newResponse.setBookedSeats(bookedSeats);
		this.blockSeats(newResponse);
		responses.add(newResponse);
	}

	public boolean findAllSeatsTogether(List<BookingResponse> responses, String requestId, int seatsRequested) {
		HashMap<Integer, List<Integer>> bookedSeats = new HashMap<Integer, List<Integer>>();
		BookingResponse newResponse = new BookingResponse(requestId);
		for(int row=0; row<Configurations.N_ROWS; row++) {
			if(this.seatsPresentInRow[row] >= seatsRequested) {
				//Checking If we can assign the group all seats together
				int c = Configurations.N_SEATS / 2;
				if(this.seats[row][c] == 0) {
					int halfSeats = seatsRequested/2;
					if( this.checkSeats(row, c-halfSeats+1, halfSeats) && this.checkSeats(row,c , seatsRequested - halfSeats)) {
						bookedSeats = bookSeats(row,c-halfSeats,seatsRequested);
						this.addResponseandBlockSeats(responses,newResponse,bookedSeats);
						return true;
					}
				}else {

					int l = c, r= c+1;
					int lCount = seatsRequested, rCount = seatsRequested;
					while(l >= 0 && r < Configurations.N_SEATS) {

						if(this.seats[row][l] == 0) {

							lCount--;

						}else {
							lCount = seatsRequested;

						}
						if(this.seats[row][r] == 0) {
							rCount--;
						}else {
							rCount = seatsRequested;
						}
						if(lCount == 0) {
							bookedSeats = bookSeats(row,l, seatsRequested);
							this.addResponseandBlockSeats(responses,newResponse,bookedSeats);
							return true;
						}else if(rCount == 0) {
							bookedSeats = bookSeats(row,r-seatsRequested+1, seatsRequested);
							this.addResponseandBlockSeats(responses,newResponse,bookedSeats);
							return true;
						}
						r++;
						l--;

					}
				}
			}
		}
		return false;
	}


	public boolean findSeatsWhereverPossible(List<BookingResponse> responses, String requestId, int seatsRequested) {

		int sum =0;
		for(int x: this.seatsPresentInRow) {
			sum+= x;
		}

		if(seatsRequested <= sum) {

			HashMap<Integer, List<Integer>> bookedSeats = new HashMap<Integer, List<Integer>>();
			BookingResponse newResponse = new BookingResponse(requestId);

			int count = seatsRequested;
			for(int row = 0; row<Configurations.N_ROWS && count>0; row++) {
				if(this.seatsPresentInRow[row] != 0 ) {
					int c = Configurations.N_SEATS/2;
					int l = c, r= c+1;
					boolean leftSeatBooked=false, rightSeatBooked = false; 
					while(count > 0 && (l >= 0 || r < Configurations.N_SEATS ) ) {
						
						if(l >= 0 && this.seats[row][l] == 0 && count > 0) {
							count--;
							leftSeatBooked = true;
						}
						
						if( r < Configurations.N_SEATS && this.seats[row][r] == 0 && count > 0) {
							count--;
							rightSeatBooked = true;
						}

						if(leftSeatBooked) {
							bookedSeats = bookSeats(row,l, 1);
							newResponse.setBookedSeats(bookedSeats);
							leftSeatBooked = false;
						}

						if(rightSeatBooked) {
							bookedSeats = bookSeats(row,r, 1);
							newResponse.setBookedSeats(bookedSeats);
							rightSeatBooked = false;
						}
						r++;
						l--;

					}
				}

			}
			this.blockSeats(newResponse);
			responses.add(newResponse);
			return true;
		}
		else return false;
	}


	public List<BookingResponse> findSeats(List<BookingRequest> bookingRequests){
		List<BookingResponse> responses = new ArrayList<BookingResponse>();

		for(BookingRequest request: bookingRequests) {

			String requestId = request.getRequestId();
			int seatsRequested = request.getNoOfSeats();
			boolean seatsAssigned = this.findAllSeatsTogether(responses, requestId, seatsRequested);

			if(!seatsAssigned ) {
				seatsAssigned = this.findSeatsWhereverPossible(responses, requestId, seatsRequested);
			}

			if(!seatsAssigned){
				BookingResponse newResponse = new BookingResponse(requestId);
				HashMap<Integer, List<Integer>> bookedSeats = new HashMap<Integer, List<Integer>>();
				newResponse.setBookedSeats(bookedSeats);
				responses.add(newResponse);
			}

		}
		return responses;
	}



}
