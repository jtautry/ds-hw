import java.io.*;
import java.net.*;
import java.util.*;

public class SyncServerThread implements Runnable {
	private int myID;
	private ArrayList<ServerInformation> listOfOtherServers;
	private SeatInventory seatList;

	public SyncServerThread(int myID, ArrayList<ServerInformation> listOfOtherServers, SeatInventory seatList) {
		this.myID = myID;
		this.listOfOtherServers = listOfOtherServers;
		this.seatList = seatList;
	}

	@Override
	public void run() {
		System.out.println("SyncServerThread started.");
		for (int i = 0; i < listOfOtherServers.size(); i++) {
			if (i != (myID - 1)) {

				try {
						Socket newSocket = new Socket(listOfOtherServers.get(i).getIpAddress(),
								listOfOtherServers.get(i).getPortAddress());
						while(!newSocket.isConnected()) {
						}
						PrintWriter os = new PrintWriter(newSocket.getOutputStream(), true);
						os.write(this.seatList.getStatus());
						os.flush();
						System.out.println(this.seatList.getStatus());
						// receive response
						String line;
						BufferedReader in = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
						System.out.println("Server Synced "+listOfOtherServers.get(i).getIpAddress()+":"+listOfOtherServers.get(i).getPortAddress());

				} catch (IOException e) {
					System.out.println(e);
				}
			}
			System.out.println("SyncServerThread stopped.");
		}

	}
}
