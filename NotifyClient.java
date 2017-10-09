import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class NotifyClient implements Runnable {

	private Socket _socket;
	private String _response;
	
	public NotifyClient(Socket socket, String response) {
		_socket= socket;
		_response = response;
	}

	@Override
	public void run() {
		  PrintWriter pout= null;
		try {
			pout = new PrintWriter(_socket.getOutputStream());
		} catch (IOException e) {
		}
		  pout.println(_response);
		  pout.flush();
		
	}

}
