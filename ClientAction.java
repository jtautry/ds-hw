import java.io.Serializable;

public class ClientAction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7748060040829149621L;

	private SeatInventory _inventory;

	private String _response = "";
	
	

	public ClientAction() {
		
	}
	public ClientAction(SeatInventory inventory, String response) {
		setInventory(inventory);
		setResponse(response);
	}
	public SeatInventory getInventory() {
		return _inventory;
	}
	public void setInventory(SeatInventory _inventory) {
		this._inventory = _inventory;
	}
	public String getResponse() {
		return _response;
	}
	public void setResponse(String _response) {
		this._response = _response;
	}

}