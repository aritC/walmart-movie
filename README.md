# Movie Theater Seating Challenge - 2020

### How to run
1. Before running make sure you have Maven, and JDK setup in your machine.
2. Make sure you change the output directory. To do so open `/src/main/java/movieSeatBooking/Configurations.java` 
 file and set `OUTPUT_DIR` variable's value to the directory you want the output files to be saved in.
3. Open terminal and cd into the project folder
4. To run the Unit Tests use the following command `mvn test`
5. To run tests and build the project run the following command `mvn package`
6. To run the project use the following command `mvn exec:java -Dexec.args="<<complete_path_to_input_file>>"`

### Problem Statement
Assign seats to Customers in the Movie Theater which maximizes the Customer Satisfaction as well as Customer Safety.

### Assumptions
- Customer Satisfaction is dependent on the following factors in order
	1.	If the group is able to sit all together
	2.	If they are able to get seats as far back as possible.
	3.	If they are able to get seats as close to middle as possible.
	4.	If they are able to get seats anywhere
- For Customer safety we have put 3 seats buffer in the same row and 1 row buffer above and below both of which can be configured by changin the values of variables in `/walmart-movie/src/main/java/movieSeatBooking/Configurations.java` file.
- We assume that either all the seats of the reservation are to be booked or none of them can be booked.
- If an error occurs while parsing the file we just ignore that request and move on to process the next request.

### Design and Implementation
Whole project is divided into following 5 packages:
- `driver`:
It consists of `Driver.java` which has the main function implemented into it. It initializes the Input and Output Handlers. It also get the file path of the input file from cmd line arguments and coordinates the flow of data from input file to output file
- `movieSeatBooking`:
It has 2 files:
    1. `Configurations.java` which can be used to change the theater configurations like no. of rows, no. of seats per row, safety buffer for seats, safety buffer for row and the output file directory.
    2. `MovieTheater.java` which has the main business logic of the process.
- `movieSeatBooking.InputHandler`:
This package handles the input to this software. It consists of the following files
    1. `BookingRequest.java`: This is the input object. Each Request (i.e. Reservation Id, No of Seats ) pair is converted into this object so as to maintain a input format which can be updated easily in the future. Also while initialization the Reservation Id format is matched against R####. 
    2. `IInputProcessor.java`: It is an interface for the input processor. Again keeping in mind future changes this interface can be implemented for any other input method like database or api etc.
    3. `InputFileProcessor.java`: It is the implementation of the `IInputProcessor` interface for getting inputs from txt file.
    4. `InputFileErrorHandler.java`: This saves the list of errors that can happen during the parsing of the Inputfile like duplicate reservation Id, bad request format, file not found, or invalid reservation id. 
- `movieSeatBooking.OutputHandler`:
This package handles the output of the software. It consists of the following classes:
    1. `Bookingresponse.java`: This is the output object. It consits of the reservation Id and list of booked seat's row and column number. It has `toString()` method which is used to convert the row numbers to respective alphabetical row and generate desired output. If the list is empty it means that seat cannot be assigned for that request and same is outputed for the reservation id.
    2. `IOuputProcessor.java`: This is an interface for the output processor. As in future output methods can change this interface can be implemented to output both the actual results and the errors that have occured.
    3. `OutputFileProcessor.java`: It is an implementation of the `IOutputProcessor`  interface. This creates two files first for output which contains all the booked seats or if seat could'nt be booked then says 'No seats available' for that reservation id. Secondly it contains an error file which consists of all the errors that have occured while processing of the input file. Both of the files get a unique name by appending timestamp at the end of them. The file are saved in the directory mentioned in `Configurations.OUTPUT_DIR` variable.
- `util`: This contains other Misc classes which is required by our software like:
    1. `InvalidRequestIdException.java`: It is a custom exception class which extends `RuntimeException`. It is thrown by the `BookingRequest` class when the reservation id does not match the regex pattern given.
### Algorithm
The main algorithm is in `MovieTheater.java` file. Here for each of the request we see if we can assign all the seats together as far back as possible and as close to middle as possible. If not we try to find seperate seats but again as far back as possible and as close to middle columns as possible. If still we can not find any seats then there are not enough seats for the requests.

`Time Complexity : O(noOfRequest x noOfRows x noOfColumns ) ~= O(n^3) `
`Space Complexity : O(n)`

