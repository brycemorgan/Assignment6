package assignment6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClient sc;
	int port;
	static boolean full = false;

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname, int port) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
		this.port = port;
	}

	public synchronized void run() {
		System.out.flush();
		try {
			Socket echoSocket = new Socket(hostname, port);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			Thread.sleep(100);
			if(TicketServer.bestAvailable() != -1)
			System.out.println("*************Ticket*************\n" + in.readLine() + "\n********************************\n");
/*			System.out.println(in.readLine());
			System.out.println("********************************\n");*/
			echoSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";

	TicketClient(String hostname, String threadname, int port) {
		tc = new ThreadedTicketClient(this, hostname, threadname, port);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name, 100);
	}

	TicketClient() {
		this("localhost", "unnamed client", 200);
	}

	synchronized void requestTicket() {
		Random rand = new Random();
		int count = 0;
		try{
			while(TicketServer.bestAvailable() != -1) tc.run();
			System.out.println("Theater Sold Out!");
/*				tc.run();
				tc.run();
				tc.run();
				tc.run();
				tc.run();
				tc.run();*/
		} catch (Exception e){}
//		System.out.println(hostName + "," + threadName + " got one ticket");
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
