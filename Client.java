import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ArrayList<ServerInformation> listOfServers = new ArrayList<ServerInformation>();

		int numServer = sc.nextInt();
		System.out.println("Server count " + numServer);

		while (listOfServers.size() < numServer) {
			String serverInfo = sc.nextLine();
			if (!serverInfo.isEmpty()) {
				String[] partsOfServerAddress = serverInfo.split(":");
				if (partsOfServerAddress.length == 2) {
					ServerInformation serverObj = new ServerInformation(partsOfServerAddress[0],
							Integer.parseInt(partsOfServerAddress[1]));
					listOfServers.add(serverObj);
					System.out.println("Server " + listOfServers.size() + " is " + serverObj.toString());
				} else {
					System.out.println("Try again");
				}
			}
		}

		while (sc.hasNextLine()) {
			String cmd = sc.nextLine();
			String[] tokens = cmd.split(" ");

			if (tokens[0].equals("reserve") && tokens.length == 2) {

				for (int serverNumber = 0; serverNumber < listOfServers.size(); serverNumber++) {
					Socket socket;
					try {
						socket = new Socket(listOfServers.get(serverNumber).getIpAddress(),
								listOfServers.get(serverNumber).getPortAddress());
						//socket.setSoTimeout(100);
						ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						ServerAction action = new ServerAction(new Date(), new String("reserve " + tokens[1]));
						out.writeObject(action);
						out.flush();

						String line;
						while ((line = in.readLine().toString()) != null) {
							System.out.println(line);
						}
						out.close();
						break;
					} catch (SocketTimeoutException e) {
						// listOfServers.remove(serverNumber);
						continue;
					} catch (Exception e) {
						System.out.println("Something Bad Happened");
					}
				}
			} else if (tokens[0].equals("bookSeat") && tokens.length == 3) {

				for (int serverNumber = 0; serverNumber < listOfServers.size(); serverNumber++) {
					try {
						Socket socket = new Socket(listOfServers.get(serverNumber).getIpAddress(),
								listOfServers.get(serverNumber).getPortAddress());
						socket.setSoTimeout(100);
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

						// send command
						out.println("bookSeat " + tokens[1] + " " + tokens[2]);

						// receive response
						String line;
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
						break;
					} catch (SocketTimeoutException e) {
						// listOfServers.remove(serverNumber);
						continue;
					} catch (Exception e) {
						System.out.println("Something Bad Happened");
					}
				}

			} else if (tokens[0].equals("search") && tokens.length == 2) {

				for (int serverNumber = 0; serverNumber < listOfServers.size(); serverNumber++) {
					try {
						Socket socket = new Socket(listOfServers.get(serverNumber).getIpAddress(),
								listOfServers.get(serverNumber).getPortAddress());
						socket.setSoTimeout(100);
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

						// send command
						out.println("search " + tokens[1]);

						// receive response
						String line;
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
						break;
					} catch (SocketTimeoutException e) {
						// listOfServers.remove(serverNumber);
						continue;
					} catch (Exception e) {
						System.out.println("Something Bad Happened");
					}
				}
			} else if (tokens[0].equals("delete") && tokens.length == 2) {

				for (int serverNumber = 0; serverNumber < listOfServers.size(); serverNumber++) {
					try {
						Socket socket = new Socket(listOfServers.get(serverNumber).getIpAddress(),
								listOfServers.get(serverNumber).getPortAddress());
						socket.setSoTimeout(100);
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

						// send command
						out.println("delete " + tokens[1]);

						// receive response
						String line;
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
						break;
					} catch (SocketTimeoutException e) {
						// listOfServers.remove(serverNumber);
						continue;
					} catch (Exception e) {
						System.out.println("Something Bad Happened");
					}
				}
				// TODO: Get Rid of this after it's working
			} else if (tokens[0].equals("status")) {
				for (int serverNumber = 0; serverNumber < listOfServers.size(); serverNumber++) {
					try {
						Socket socket = new Socket(listOfServers.get(serverNumber).getIpAddress(),
								listOfServers.get(serverNumber).getPortAddress());
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

						// send command
						out.println("status");

						// receive response
						String line;
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
						break;
					} catch (SocketTimeoutException e) {
						// listOfServers.remove(serverNumber);
						continue;
					} catch (Exception e) {
						System.out.println("Something Bad Happened");
					}
				}
			} else if (tokens[0].equals("servers")) {
				for (int serverNumber = 0; serverNumber < listOfServers.size(); serverNumber++) {
					System.out.println("Server " + serverNumber + " is " + listOfServers.get(serverNumber).toString());

				}
			} else {
				System.out.println("ERROR: No such command");
			}
		}
	}

}
