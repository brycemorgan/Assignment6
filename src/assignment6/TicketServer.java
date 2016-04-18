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
	int PORT = 2222;
	static ArrayList<TheaterSeat> seating = new ArrayList<TheaterSeat>();
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public void start(int portNumber) throws IOException {
		PORT = portNumber;
		Runnable serverThread = new ThreadedTicketServer(PORT);
		Thread t = new Thread(serverThread);
		t.start();
	}

	public static void setTheater2() {
		ArrayList<TheaterSeat> seats = new ArrayList<TheaterSeat>();
		for (int i = 101; i <= 128; i++) 
			for (char c = 'C'; c <= 'X'; c++) 
				seats.add(new TheaterSeat(i, Character.toString(c)));
		for (int i = 108; i <= 128; i++) 
			for (char c = 'A'; c <= 'B'; c++) 
				seats.add(new TheaterSeat(i, Character.toString(c)));
		for (int i = 101; i <= 107; i++) 
			for (char c = 'Y'; c <= 'Z'; c++) 
				seats.add(new TheaterSeat(i, Character.toString(c)));
		for (int i = 122; i <= 128; i++) 
			for (char c = 'Y'; c <= 'Z'; c++) 
				seats.add(new TheaterSeat(i, Character.toString(c)));
		for (int i = 101; i <= 104; i++) seats.add(new TheaterSeat(i, "AA"));
		for (int i = 116; i <= 118; i++) seats.add(new TheaterSeat(i, "AA"));
		seats.add(new TheaterSeat(127, "AA"));
		seats.add(new TheaterSeat(128, "AA"));
		Collections.sort(seats);
		seating = seats;
	}

	public synchronized static int bestAvailable() {
		for (int i = 0; i < seating.size(); i++) {
			if (seating.get(i).isAvailable()) {
				return i;
			}
		}
		return -1;
	}

	public static void markSeatUnavailable(int arraylistindex) {
		seating.get(arraylistindex).setAvailable(false);
	}
}

class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1";
	String threadname = "X";
	String testcase;
	TicketClient sc;

	private int port;

	public ThreadedTicketServer(int port) {
		this.port = port;
	}

	public void run() {
		ServerSocket serverSocket;
		try {
//			System.out.println(port);
			serverSocket = new ServerSocket(port);
			int i = 0;
			while (true) {
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				int seatNo = TicketServer.bestAvailable();
				if (seatNo != -1) {
					TicketServer.markSeatUnavailable(seatNo);
					out.println("Box Office " + port + " " + TicketServer.seating.get(seatNo).toString());
				} else
					out.println("Theater Sold Out!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
