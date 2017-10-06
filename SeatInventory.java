import java.util.ArrayList;

public class SeatInventory {

	ArrayList<SeatInformation> _listOfSeats;

	public SeatInventory(int numberOfSeats) {
		_listOfSeats = new ArrayList<SeatInformation>();
		for (int i = 1; i <= numberOfSeats; i++) {
			SeatInformation seat = new SeatInformation(i);
			_listOfSeats.add(seat);
		}
	}

	public ArrayList<SeatInformation> getSeatList() {
		return _listOfSeats;
	}

	public String ReserveSeat(String personName) {
		int openSeats = 0;
		int firstOpenSeatIndex = -1;
		boolean isAlreadyBooked = false;

		for (int i = 0; i < _listOfSeats.size(); i++) {
			if (_listOfSeats.get(i).getPerson() == "") {
				if (firstOpenSeatIndex == -1) {
					firstOpenSeatIndex = i;
				}
				openSeats++;
			} else if (_listOfSeats.get(i).getPerson().equalsIgnoreCase(personName)) {
				isAlreadyBooked = true;
				break;
			}
		}

		if (isAlreadyBooked) {
			return "Seat already booked against the name provided";
		} else if (openSeats == 0) {
			return "Sold out - No seat available";
		} else {
			_listOfSeats.get(firstOpenSeatIndex).setPerson(personName);
			return "Seat assigned to you is " + _listOfSeats.get(firstOpenSeatIndex).getSeat();
		}

	}

	public String ReserveThatSeat(String personName, int SeatNum) {
		int openSeats = 0;
		boolean isAlreadyBooked = false;

		for (int i = 0; i < _listOfSeats.size(); i++) {
			if (_listOfSeats.get(i).getPerson() == "") {
				openSeats++;
			} else if (_listOfSeats.get(i).getPerson().equalsIgnoreCase(personName)) {
				isAlreadyBooked = true;
				break;
			}
		}
		if (isAlreadyBooked) {
			return "Seat already booked against the name provided";
		} else if (_listOfSeats.get(SeatNum - 1).getPerson() == "") {
			_listOfSeats.get(SeatNum - 1).setPerson(personName);
			return "Seat assigned to you is " + _listOfSeats.get(SeatNum - 1).getSeat();
		} else if (_listOfSeats.get(SeatNum - 1).getPerson().equalsIgnoreCase(personName)) {
			return "Seat already booked against the name provided";
		} else if (openSeats == 0) {
			return "Sold out - No seat available";
		} else {
			return SeatNum + " is not available";
		}

	}

	public String SearchPerson(String personName) {
		for (int i = 0; i < _listOfSeats.size(); i++) {
			if (_listOfSeats.get(i).getPerson().equalsIgnoreCase(personName)) {
				return "" + _listOfSeats.get(i).getSeat();
			}
		}
		return "No reservation found for " + personName;

	}

	public String RemoveReservation(String personName) {
		for (int i = 0; i < _listOfSeats.size(); i++) {
			if (_listOfSeats.get(i).getPerson().equalsIgnoreCase(personName)) {
				_listOfSeats.get(i).setPerson("");
				return "" + _listOfSeats.get(i).getSeat();
			}
		}
		return "No reservation found for " + personName;

	}

	public String getStatus() {
		String str = "";
		for (int i = 0; i < _listOfSeats.size(); i++) {
			str = str + _listOfSeats.get(i).getStatus()+"\n";
		}
		return str;
	}

}
