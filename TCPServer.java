import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer implements Runnable {
	private ArrayList<ServerInformation> listOfOtherServers;
	private SeatInventory seatList;
	private int myID;

	public TCPServer(int myID, ArrayList<ServerInformation> listOfOtherServers, SeatInventory seatList) {
		this.myID = myID;
		this.listOfOtherServers = listOfOtherServers;
		this.seatList = seatList;
	}

	@Override
	public void run() {
		System.out.println("listening for tcp");
		try (ServerSocket serverSocket = new ServerSocket(this.listOfOtherServers.get(myID - 1).getPortAddress())) {
			while (true) {
				Runnable t = new TCPServerThread(this.myID, this.listOfOtherServers, this.seatList,
						serverSocket.accept());
				new Thread(t).start();
			}
		} catch (IOException e) {
			System.err.println("Server aborted:" + e);
		}
	}
}
