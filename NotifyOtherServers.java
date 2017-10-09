import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Callable;

public class NotifyOtherServers implements Runnable {
	private int _myID;
	private ArrayList<ServerInformation> _listOfOtherServers = null;
	private ServerAction _action;
	public NotifyOtherServers(int myID, ArrayList<ServerInformation> listOfOtherServers,ServerAction action ) {
		_myID = myID;
		_listOfOtherServers = listOfOtherServers;
		_action = action;

	}

	@Override
	public void run() {
		System.out.println("SyncServerThread started.");
		for (int i = 0; i < _listOfOtherServers.size(); i++) {
			if (i != (_myID - 1)) {
				try {
						Socket newSocket = new Socket(_listOfOtherServers.get(i).getIpAddress(),
								_listOfOtherServers.get(i).getPortAddress());
						while(!newSocket.isConnected()) {
						}
						ObjectOutputStream oos = new ObjectOutputStream(newSocket.getOutputStream());
						oos.writeObject(_action);
						oos.flush();
						// receive response
					ObjectInputStream ois = new ObjectInputStream(newSocket.getInputStream());
					ServerAction otherAction = (ServerAction) ois.readObject();
					newSocket.close();

				} catch (IOException | ClassNotFoundException e) {
					System.out.println(e);
				}
			}
			
		}
		System.out.println("SyncServerThread stopped.");

	}

//	@Override
//	public Integer call() throws Exception {
//		System.out.println("SyncServerThread started.");
//		int requests=1;
//		for (int i = 0; i < _listOfOtherServers.size(); i++) {
//			if (i != (_myID - 1)) {
//				try {
//						Socket newSocket = new Socket(_listOfOtherServers.get(i).getIpAddress(),
//								_listOfOtherServers.get(i).getPortAddress());
//						while(!newSocket.isConnected()) {
//						}
//						ObjectOutputStream oos = new ObjectOutputStream(newSocket.getOutputStream());
//						oos.writeObject(_action);
//						oos.flush();
//						// receive response
////						ObjectInputStream ois = new ObjectInputStream(newSocket.getInputStream());
////						ServerAction otherAction = (ServerAction) ois.readObject();
////						if(otherAction.getTimeStamp()!=null) {
////							requests++;
////						}
//						newSocket.close();
//
//				} catch (IOException e) {
//					System.out.println(e);
//				}
//			}
//			
//		}
//		System.out.println("SyncServerThread stopped.");
//		return requests;
//	}
}
