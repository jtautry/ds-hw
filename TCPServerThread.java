import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServerThread implements Runnable {
	private ArrayList<ServerInformation> listOfOtherServers;
	private SeatInventory seatList;
	private Socket clientSocket;
	private int myID;

	public TCPServerThread(int myID, ArrayList<ServerInformation> listOfOtherServers, SeatInventory seatList,
			Socket socket) {
		this.myID = myID;
		this.listOfOtherServers = listOfOtherServers;
		this.seatList = seatList;
		this.clientSocket = socket;
	}

	@Override
	public void run() {
		System.out.println("TCPServerThread started.");
		try (Scanner sc = new Scanner(this.clientSocket.getInputStream());) {
			PrintWriter pout = new PrintWriter(this.clientSocket.getOutputStream());

			if (sc.hasNext()) {
				String[] bufferArray = sc.nextLine().split(" ");
				String response = "";
				if (bufferArray.length > 1) {
					switch (bufferArray[0]) {
					case "reserve":
						response = seatList.ReserveSeat(bufferArray[1]);
						break;
					case "bookSeat":
						response = seatList.ReserveThatSeat(bufferArray[1], Integer.parseInt(bufferArray[2]));
						break;
					case "search":
						response = seatList.SearchPerson(bufferArray[1]);
						break;
					case "delete":
						response = seatList.RemoveReservation(bufferArray[1]);
						break;
					default:
						response = "ERROR: No such command";
						break;
					}
				} else if(bufferArray[0].trim().equalsIgnoreCase("status")) {
					response = seatList.getStatus();
				}
				else {
					response = "Bad Request";
				}
				pout.println(response);
				pout.flush();
			}

		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("TCPServerThread stopped.");
	}
}
