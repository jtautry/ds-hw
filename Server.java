import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("ID?");
		int myID = sc.nextInt();
		System.out.println("Number of Servers?");
		int numServer = sc.nextInt();
		System.out.println("Number of Seats");
		int numSeat = sc.nextInt();
		ArrayList<ServerInformation> listOfServers = new ArrayList<ServerInformation>();
		SeatInventory inventory = new SeatInventory(numSeat);
		String serverInfo;
		for (int i = 0; i < numServer
				&& (serverInfo = sc.next("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}(:)\\d{1,5}")) != ""; i++) {
			String[] partsOfServerAddress = serverInfo.split(":");
			if (partsOfServerAddress.length == 2) {
				ServerInformation serverObj = new ServerInformation(partsOfServerAddress[0],
						Integer.parseInt(partsOfServerAddress[1]));
				listOfServers.add(serverObj);
				System.out.println("Server " + i + " is " + serverObj.toString());
			}
		}

		System.out.println("listening for tcp");
		ServerSocket serverSocket = null;
		ArrayList<ServerAction> serverQueue = new ArrayList<ServerAction>();
		try {
			while (true) {
				serverSocket = new ServerSocket(listOfServers.get(myID - 1).getPortAddress());
				Socket currentSocket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(currentSocket.getInputStream());
				ServerAction otherAction = (ServerAction) ois.readObject();
				//coming from client
				if(otherAction.getServerId()==0 && otherAction.getAction()!=null) {
					otherAction.setServerId(myID);
					serverQueue.add(otherAction);
					serverQueue.sort(new ServerActionComparator());
					
					Thread t = new Thread(new NotifyOtherServers(myID, listOfServers, otherAction));
					t.start();
					//coming from other server
				}else {	
					serverQueue.add(otherAction);
					serverQueue.sort(new ServerActionComparator());
					ServerAction timeStampAction = new ServerAction(new Date());

					ObjectOutputStream oos = new ObjectOutputStream(currentSocket.getOutputStream());
					oos.writeObject(timeStampAction);
					oos.flush();
				}
				//TODO count responses from the notify thread before execution
				if(serverQueue.get(0).getServerId()==myID && serverQueue.get(0).equals(otherAction) ) {
						String[] bufferArray = otherAction.getAction().split(" ");
						String response = "";
						if (bufferArray.length > 1) {
							String actionFromBuffer = bufferArray[0].toLowerCase();
							switch (actionFromBuffer) {
							case "reserve":
								response = inventory.ReserveSeat(bufferArray[1]);
								break;
							case "bookseat":
								response = inventory.ReserveThatSeat(bufferArray[1], Integer.parseInt(bufferArray[2]));
								break;
							case "search":
								response = inventory.SearchPerson(bufferArray[1]);
								break;
							case "delete":
								response = inventory.RemoveReservation(bufferArray[1]);
								break;
							default:
								response = "ERROR: No such command";
								break;
							}
						}else {
							response = "Bad Request";
						} 
						PrintWriter	pout = new PrintWriter(currentSocket.getOutputStream());
						pout.println(response);
						pout.flush();
						serverQueue.remove(0);
						//TODO remove item from other queues
				    
				}
				serverSocket.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Server aborted: " + e);

		}

	}
	
}
