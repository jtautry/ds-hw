import java.util.ArrayList;
import java.util.Scanner;

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
		Runnable tcpServer = new TCPServer(myID, listOfServers, inventory);
		new Thread(tcpServer).start();
	}
}
