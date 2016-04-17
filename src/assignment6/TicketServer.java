package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class TicketServer {
	static int PORT = 2222;
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public static void start(int portNumber) throws IOException {
		PORT = portNumber;
		Runnable serverThread = new ThreadedTicketServer();
		Thread t = new Thread(serverThread);
		t.start();
	}
	
	public static ArrayList<TheaterSeat> setTheater2() {
		ArrayList<TheaterSeat> seats = new ArrayList<TheaterSeat>();
		for (int i = 101; i <= 128; i++) {
			for (char c = 'C'; c <= 'X'; c++) {
				seats.add(new TheaterSeat(i, Character.toString(c)));
			}
		}
		for (int i = 108; i <= 128; i++) {
			for (char c = 'A'; c <= 'B'; c++) {
				seats.add(new TheaterSeat(i, Character.toString(c)));
			}
		}
		
		for (int i = 101; i <= 107; i++) {
			for (char c = 'Y'; c <= 'Z'; c++) {
				seats.add(new TheaterSeat(i, Character.toString(c)));
			}
		}
		
		for (int i = 122; i <= 128; i++) {
			for (char c = 'Y'; c <= 'Z'; c++) {
				seats.add(new TheaterSeat(i, Character.toString(c)));
			}
		}
		
		for (int i = 101; i <= 104; i++) {
			seats.add(new TheaterSeat(i, "AA"));
		}
		for (int i = 116; i <= 118; i++) {
			seats.add(new TheaterSeat(i, "AA"));
		}
		seats.add(new TheaterSeat(127, "AA"));
		seats.add(new TheaterSeat(128, "AA"));

		Collections.sort(seats);
		
		for (TheaterSeat seat: seats) {
			System.out.println(seat.toString());
		}
		
		return seats;
	}
	
	public static TheaterSeat bestAvailable(ArrayList<TheaterSeat> theaterConfig) {
		for (TheaterSeat seat: theaterConfig) {
			if (seat.isAvailable()) {
				return seat;
			}
		}
		return null;
	}
	
	public static void markSeatUnavailable(int arraylistindex, ArrayList<TheaterSeat> theaterConfig) {
		theaterConfig.get(arraylistindex).setAvailable(false);
	}
}

class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1";
	String threadname = "X";
	String testcase;
	TicketClient sc;

	public void run() {
		// TODO 422C
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(TicketServer.PORT);
			ArrayList<String> seating = setTheater();
			int i = 0;
			while (true) {
/*				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				if (!seating.isEmpty()) {
					out.println(seating.remove(i));
				}
				else System.out.println("Theater is sold out!");
				clientSocket.close();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> setTheater() {
		ArrayList<String> seats = new ArrayList<String>();
		for (int i = 108; i <= 121; i++)
			seats.add("M,A" + i);
		for (int i = 122; i <= 128; i++)
			seats.add("HR,A" + i);
		for (int i = 108; i <= 121; i++)
			seats.add("M,B" + i);
		for (int i = 122; i <= 128; i++)
			seats.add("HR,B" + i);
		for (char j = 'C'; j < 'Y'; j++) {
			for (int i = 108; i <= 121; i++)
				seats.add("M," + j + i);
			for (int i = 101; i <= 107; i++)
				seats.add("HL," + j + i);
			for (int i = 122; i <= 128; i++)
				seats.add("HR," + j + i);
		}
		for (int i = 101; i <= 107; i++)
			seats.add("HL,Y" + i);
		for (int i = 122; i <= 128; i++)
			seats.add("HL,Y" + i);
		for (int i = 101; i <= 107; i++)
			seats.add("HR,Z" + i);
		for (int i = 122; i <= 128; i++)
			seats.add("HR,Z" + i);
		for (int i = 101; i <= 107; i++)
			seats.add("HL,AA" + i);
		for (int i = 122; i <= 128; i++)
			seats.add("HR,AA" + i);
		return seats;
	}
	
	
	
	
}