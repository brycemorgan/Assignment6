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
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			Thread.sleep(100);
			if (TicketServer.bestAvailable() != -1)
				System.out.println(
						"***************Ticket***************\n" + in.readLine() + "\n**************Reserved**************\n");
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

		try {
			while (TicketServer.bestAvailable() != -1) {
				for (int line = rand.nextInt((1000 - 100) + 1) + 100; line > 0; line--) {
					if (TicketServer.bestAvailable() == -1)
						break;
					tc.run();
				}
			}
			System.out.println("Theater Sold Out!");
		} catch (Exception e) {
		}
	}

	void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
