import java.io.Serializable;
import java.util.Date;

/**
 * Class representing a command that is sent from Client to Server and Server to
 * Server
 *
 */
public class ServerCommand implements Serializable {

	/**
	 * Serial number from the interface
	 */
	private static final long serialVersionUID = 3891008083136222563L;

	/**
	 * TimeStamp of the Server Command
	 */
	private Date _timeStamp;
	/**
	 * Action to be performed by the Server
	 */
	private String _action = "";
	/**
	 * ID of the Server that owes this Action
	 */
	private int _serverId = 0;

	/**
	 * Returns the TimeStamp
	 */
	public Date getTimeStamp() {
		return _timeStamp;
	}

	/**
	 * Returns the action that should be performed
	 */
	public String getAction() {
		return _action;
	}

	/**
	 * returns the server id that owes this serverCommand
	 */
	public int getServerId() {
		return _serverId;
	}

	/**
	 * Sets the ServerID of ServerCommand
	 * 
	 * @param ServerId
	 */
	public void setServerId(int id) {
		_serverId = id;
	}

	/**
	 * Creates a empty ServerCommand with defaults of action="", serverID =0
	 */
	public ServerCommand() {

	}

	/**
	 * Creates a ServerCommand with only a timestamp
	 * 
	 * This is used when as the server response to the another Servers request.
	 * 
	 * 
	 * @param timestamp
	 */
	public ServerCommand(Date timestamp) {
		_timeStamp = timestamp;
	}

	/**
	 * Creates a ServerCommand from the client input
	 * 
	 * this is used when a client is sending a message to the server
	 * 
	 * @param timestamp
	 * @param action
	 */
	public ServerCommand(Date timestamp, String action) {
		_timeStamp = timestamp;
		_action = action;
	}

	/**
	 * Creates a ServerCommand
	 * 
	 * this is used when the server notifies other servers of the command
	 * 
	 * @param serverId
	 * @param timestamp
	 * @param action
	 */
	public ServerCommand(int serverId, Date timestamp, String action) {
		_serverId = serverId;
		_timeStamp = timestamp;
		_action = action;
	}
	
	/**
	 * Creates a ServerCommand 
	 * 
	 * this is used when the server releases other servers
	 * 
	 * @param serverId
	 * @param action
	 */
	public ServerCommand(int serverId, String action) {
		_serverId = serverId;
		_action = action;
	}

	/*
	 * Equals so that we can utilize it in the Server Class
	 */
	@Override
	public boolean equals(Object o) {
		ServerCommand c = (ServerCommand) o;
		if (this._action == c._action && this._timeStamp == c._timeStamp) {
			return true;
		}
		return false;
	}

	/*
	 * Hash so that we can utilize via contains in the server class
	 */
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this._action != null ? this._action.hashCode() : 0);
		hash = 53 * hash + (this._timeStamp != null ? this._timeStamp.hashCode() : 0);
		return hash;
	}

}
