import java.util.concurrent.Callable;

public class TCPServerThread implements Callable<ClientAction> {
	private SeatInventory _seatList;
	private ServerAction _action;

	public TCPServerThread( SeatInventory seatList,ServerAction action) {
		_seatList = seatList;
		_action = action;
	}
//NOT USED
	@Override
	public ClientAction call() throws Exception {
		String[] bufferArray = _action.getAction().split(" ");
		String response = "";
		if (bufferArray.length > 1) {
			String actionFromBuffer = bufferArray[0].toLowerCase();
			switch (actionFromBuffer) {
			case "reserve":
				response = _seatList.ReserveSeat(bufferArray[1]);
				break;
			case "bookseat":
				response = _seatList.ReserveThatSeat(bufferArray[1], Integer.parseInt(bufferArray[2]));
				break;
			case "search":
				response = _seatList.SearchPerson(bufferArray[1]);
				break;
			case "delete":
				response = _seatList.RemoveReservation(bufferArray[1]);
				break;
			default:
				response = "ERROR: No such command";
				break;
			}
		}else {
			response = "Bad Request";
		}
		  return new ClientAction(_seatList,response);
	}

//	@Override
//	public void run() {
//		System.out.println("TCPServerThread started.");
//		try (BufferedReader sc = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()))) {
//
//			if (sc.ready()) {
//				String[] bufferArray = sc.readLine().split(" ");
//				System.err.println(bufferArray[0]);
//				String response = "";
//				  if (bufferArray[0].trim().equalsIgnoreCase("sync")) {
//						response = "RECEIVED";
//						
//				  }else if (bufferArray.length > 1) {
//					String actionFromBuffer = bufferArray[0].toLowerCase();
//					switch (actionFromBuffer) {
//					case "reserve":
//						response = seatList.ReserveSeat(bufferArray[1]);
////						Thread t2 = new Thread(new SyncServerThread(this.myID,this.listOfOtherServers, this.seatList));
////						t2.start();
////						try {
////							t2.join();
////						} catch (InterruptedException e) {
////							System.err.println("Server aborted:" + e);
////						}
//						break;
//					case "bookseat":
//						response = seatList.ReserveThatSeat(bufferArray[1], Integer.parseInt(bufferArray[2]));
//						for (int i = 0; i < listOfOtherServers.size(); i++) {
//							Socket newSocket = new Socket(listOfOtherServers.get(i).getIpAddress(),
//									listOfOtherServers.get(i).getPortAddress());
//							try (PrintWriter os = new PrintWriter(newSocket.getOutputStream());) {
//								os.write(this.seatList.getStatus());
//								os.flush();
//							} catch (IOException e) {
//								System.err.println(e);
//							}
//						}
//						break;
//					case "search":
//						response = seatList.SearchPerson(bufferArray[1]);
//						for (int i = 0; i < listOfOtherServers.size() && i != (myID - 1); i++) {
//							Socket newSocket = new Socket(listOfOtherServers.get(i).getIpAddress(),
//									listOfOtherServers.get(i).getPortAddress());
//							try (PrintWriter os = new PrintWriter(newSocket.getOutputStream());) {
//								os.write(this.seatList.getStatus());
//								os.flush();
//							} catch (IOException e) {
//								System.err.println(e);
//							}
//						}
//						break;
//					case "delete":
//						response = seatList.RemoveReservation(bufferArray[1]);
//						for (int i = 0; i < listOfOtherServers.size(); i++) {
//							Socket newSocket = new Socket(listOfOtherServers.get(i).getIpAddress(),
//									listOfOtherServers.get(i).getPortAddress());
//							try (PrintWriter os = new PrintWriter(newSocket.getOutputStream());) {
//								os.write(this.seatList.getStatus());
//								os.flush();
//							} catch (IOException e) {
//								System.err.println(e);
//							}
//						}
//						break;
//					default:
//						response = "ERROR: No such command";
//						break;
//					}
//				} else if (bufferArray[0].trim().equalsIgnoreCase("status")) {
//					response = seatList.getStatus();
//				}else {
//					response = "Bad Request";
//				}
//				PrintWriter pout = new PrintWriter(this.clientSocket.getOutputStream());
//				pout.println(response);
//				pout.flush();
//			}
//
//		} catch (IOException e) {
//			System.err.println(e);
//		}
//
//		System.out.println("TCPServerThread stopped.");
//	}
	

}
