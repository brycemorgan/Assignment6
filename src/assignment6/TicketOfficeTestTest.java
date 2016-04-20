/* Bryce Morgan, George Tang
 * bcm2355, gdt382 
 * EE422C- Assignment 6
 */

package assignment6;

import static org.junit.Assert.*;

import org.junit.Test;

public class TicketOfficeTestTest {

	//@Test
	public void testBasicServerTest() {
		System.out.println("new test");
		try {
			TicketServer.setTheater2();
			TicketServer ticketServer1 = new TicketServer();
			TicketServer ticketServer2 = new TicketServer();
			ticketServer1.start(10000);
			ticketServer2.start(10001);

		} catch (Exception e) {
			fail();
		}
		TicketClient client1 = new TicketClient("localhost", "c1", 10000);
		TicketClient client2 = new TicketClient("localhost", "c2", 10001);
		client1.requestTicket();
		client2.requestTicket();
	}

	// @Test
	public void testTestServerCachedHardInstance() {
		System.out.println("new test");
		try {
			// TicketServer.start(16901);
		} catch (Exception e) {
			fail();
		}
		TicketClient client1 = new TicketClient("localhost", "c1", 10000);
		TicketClient client2 = new TicketClient("localhost", "c2", 10001);
		client1.requestTicket();
		client2.requestTicket();

	}

	// @Test
	public void testTwoNonConcurrentServerTest() {
		System.out.println("new test");
		try {
			// TicketServer.start(16791);
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
//MAIN TEST
	@Test
	public void testTwoConcurrentServerTest() {
		try {
			TicketServer.setTheater2();
			TicketServer ticketServer1 = new TicketServer();
			ticketServer1.start(1);
			TicketServer ticketServer2 = new TicketServer();
			ticketServer2.start(2);
			TicketServer ticketServer3 = new TicketServer();
			ticketServer2.start(3);
			// TicketServer.start(16777);
		} catch (Exception e) {
			fail();
		}
		TicketClient c1 = new TicketClient("localhost", "c1", 1);
		TicketClient c2 = new TicketClient("localhost", "c2", 2);
		TicketClient c3 = new TicketClient("localhost", "c2", 3);
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

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void test1() {
		TicketServer.setTheater2();
	}
}
