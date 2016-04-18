package assignment6;

public class TheaterSeat extends Object implements Comparable {
	private int seatNumber;
	private String row;
	private boolean available;

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public TheaterSeat(int seatNumber, String row) {
		this.seatNumber = seatNumber;
		this.row = row;
		available = true;
	}

	public String getRow() {
		return row;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	@Override
	public int compareTo(Object o) {
		TheaterSeat seat = (TheaterSeat) o;
		if (getRow().length() > seat.getRow().length()) {
			return 1;
		} else if (getRow().length() < seat.getRow().length()) {
			return -1;
		}

		if (row.compareTo(seat.getRow()) < 0) {
			return -1;
		} else if (row.compareTo(seat.getRow()) > 0) {
			return 1;
		} else {
			if (seatNumber >= 108 && seatNumber <= 121) {
				if (seat.getSeatNumber() < 108 || seat.getSeatNumber() > 121) {
					return -1;
				}
			} else {
				if (seat.getSeatNumber() >= 108 || seat.getSeatNumber() <= 121) {
					return 1;
				}
			}

		}
		return 0;
	}

	@Override
	public String toString() {
		if (seatNumber < 108) {
			return "Seat " + row + Integer.toString(seatNumber) + ", House Right";
		}
		
		if (seatNumber > 121) {
			return "Seat " + row + Integer.toString(seatNumber) + ", House Left";
		}
		
		else {
			return "Seat " + row + Integer.toString(seatNumber) + ", House Middle";
		}
	}
}
