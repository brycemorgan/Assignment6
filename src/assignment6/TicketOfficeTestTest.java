package assignment6;

import static org.junit.Assert.*;

import org.junit.Test;

public class TicketOfficeTestTest {


	//@Test
	public void testBasicServerTest() {
		System.out.println("new test");
		try {
			TicketServer.start(16789);
			//TicketServer.start(16900);

		} catch (Exception e) {
			fail();
		}
		TicketClient client = new TicketClient();
		client.requestTicket();
	}

	//@Test
	public void testTestServerCachedHardInstance() {
		System.out.println("new test");
		try {
			TicketServer.start(16901);
		} catch (Exception e) {
			fail();
		}
		TicketClient client1 = new TicketClient("localhost", "c1");
		TicketClient client2 = new TicketClient("localhost", "c2");
		client1.requestTicket();
		client2.requestTicket();
		
	}

	//@Test
	public void testTwoNonConcurrentServerTest() {
		System.out.println("new test");
		try {
			TicketServer.start(16791);
		} catch (Exception e) {
			fail();
		}
		TicketClient c1 = new TicketClient("nonconc1");
		TicketClient c2 = new TicketClient("nonconc2");
		TicketClient c3 = new TicketClient("nonconc3");
		c1.requestTicket();
		c2.requestTicket();
		c3.requestTicket();
	}

	//@Test
	public void testTwoConcurrentServerTest() {
		System.out.println("new test");
		try {
			TicketServer.start(16792);
		} catch (Exception e) {
			fail();
		}
		final TicketClient c1 = new TicketClient("conc1");
		final TicketClient c2 = new TicketClient("conc2");
		final TicketClient c3 = new TicketClient("conc3");
		Thread t1 = new Thread() {
			public void run() {
				c1.requestTicket();
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				c2.requestTicket();
			}
		};
		Thread t3 = new Thread() {
			public void run() {
				c3.requestTicket();
			}
		};
		t1.start();
		t2.start();
		t3.start();
		/*
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	@Test
	public void test1() {
		TicketServer.setTheater2();
	}
}
