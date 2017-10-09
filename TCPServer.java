import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class TCPServer implements Runnable {
	private ArrayList<ServerInformation> _listOfOtherServers;
	private volatile SeatInventory _seatList;
	private int _myID;
	private ArrayList<ServerAction> _serverQueue;
	//NOT USED
	public TCPServer(int myID, ArrayList<ServerInformation> listOfOtherServers, SeatInventory seatList) {
		_myID = myID;
		_listOfOtherServers = listOfOtherServers;
		_seatList = seatList;
		_serverQueue = new ArrayList<ServerAction>();
	}

	@Override
	public void run() {
		System.out.println("listening for tcp");
		ServerSocket serverSocket = null;
		try {
			while (true) {
				serverSocket = new ServerSocket(_listOfOtherServers.get(_myID - 1).getPortAddress());

				ObjectInputStream ois = new ObjectInputStream(serverSocket.accept().getInputStream());
				ServerAction otherAction = (ServerAction) ois.readObject();
				_serverQueue.add(otherAction);
				serverSocket.close();
	//			Thread t2 = new Thread(new NotifyOtherServers(_myID, _listOfOtherServers, otherAction));
	//			t2.start();
			}
		} catch (IOException e) {
			System.err.println("Server aborted:" + e);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		//
		// try ()) {
		// Thread t = new Thread(new TCPServerThread(this.myID,this.listOfOtherServers,
		// this.seatList, serverSocket.accept()));
		// t.start();
		//
		// } catch (IOException e) {
		// System.err.println("Server aborted:" + e);
		// }
	}

}
