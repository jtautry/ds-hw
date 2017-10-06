
public class SeatInformation {

	private int _seat;

	private String _person = "";

	public SeatInformation(int seat) {
		_seat = seat;
	}

	public int getSeat() {
		return _seat;
	}

	public String getPerson() {
		return _person;
	}

	public void setPerson(String personName) {
		_person = personName;
	}

	public String getStatus() {

		return _seat + " : " + _person;

	}
}
