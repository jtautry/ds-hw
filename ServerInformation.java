
public class ServerInformation {

	private String _ipAddress;

	private int _portAddress;

	public ServerInformation() {
		_ipAddress = new String("");
		_portAddress = 0;
	}

	public ServerInformation(String ipAddress, int portAddress) {
		_ipAddress = ipAddress;
		_portAddress = portAddress;
	}

	public String getIpAddress() {
		return _ipAddress;
	}

	public int getPortAddress() {
		return _portAddress;
	}

	@Override
	public String toString() {

		return _ipAddress + " : " + _portAddress;

	}

}
