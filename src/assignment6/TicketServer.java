package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TicketServer {
	static int PORT = 2222;
	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public static void start(int portNumber) throws IOException {
		PORT = portNumber;
		Runnable serverThread = new ThreadedTicketServer();
		Thread t1 = new Thread(serverThread);
		Thread t2 = new Thread(serverThread);
		t1.start();
		t2.start();
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