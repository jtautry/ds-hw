import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class NotifyOtherServers implements Runnable {
	private int _myID;
	private ArrayList<ServerMetadata> _listOfOtherServers = null;
	private ServerCommand _action;

	/**
	 * Creates Thread to notify servers in the list of the action
	 * 
	 * @param myID
	 * @param listOfOtherServers
	 * @param action
	 */
	public NotifyOtherServers(int myID, ArrayList<ServerMetadata> listOfOtherServers, ServerCommand action) {
		_myID = myID;
		_listOfOtherServers = listOfOtherServers;
		_action = action;

	}

	@Override
	public void run() {
		System.out.println("SyncServerThread started.");
		int acknowledgementsFromServer = 1;

		for (int i = 0; i < _listOfOtherServers.size(); i++) {
			if (i != (_myID - 1)) {
				try {
					Socket newSocket = new Socket(_listOfOtherServers.get(i).getIpAddress(),
							_listOfOtherServers.get(i).getPortAddress());
					while (!newSocket.isConnected()) {
					}
					// Send Message to other Servers
					ObjectOutputStream oos = new ObjectOutputStream(newSocket.getOutputStream());
					oos.writeObject(_action);
					oos.flush();
					// receive acknowledgement from other servers
					ObjectInputStream ois = new ObjectInputStream(newSocket.getInputStream());
					ServerCommand acknowledgements = (ServerCommand) ois.readObject();
					if (acknowledgements.getTimeStamp() != null) {
						acknowledgementsFromServer++;
					}

					newSocket.close();

				} catch (IOException | ClassNotFoundException e) {
					System.out.println(e);
				}
			}
		}

		// TODO: Count acknowledgements
		// while(acknowledgementsFromServer!=_listOfOtherServers.size()) {
		//
		// }
		System.out.println("Acknowledgements received "+acknowledgementsFromServer);

	}

	// @Override
	// public Integer call() throws Exception {
	// System.out.println("SyncServerThread started.");
	// int requests=1;
	// for (int i = 0; i < _listOfOtherServers.size(); i++) {
	// if (i != (_myID - 1)) {
	// try {
	// Socket newSocket = new Socket(_listOfOtherServers.get(i).getIpAddress(),
	// _listOfOtherServers.get(i).getPortAddress());
	// while(!newSocket.isConnected()) {
	// }
	// ObjectOutputStream oos = new ObjectOutputStream(newSocket.getOutputStream());
	// oos.writeObject(_action);
	// oos.flush();
	// // receive response
	//// ObjectInputStream ois = new ObjectInputStream(newSocket.getInputStream());
	//// ServerAction otherAction = (ServerAction) ois.readObject();
	//// if(otherAction.getTimeStamp()!=null) {
	//// requests++;
	//// }
	// newSocket.close();
	//
	// } catch (IOException e) {
	// System.out.println(e);
	// }
	// }
	//
	// }
	// System.out.println("SyncServerThread stopped.");
	// return requests;
	// }
}
