import java.io.Serializable;
import java.util.Date;

public class ServerAction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3891008083136222563L;

	private Date _timeStamp;

	private String _action = "";
	
	private int _serverId=0;

	public ServerAction() {
		
	}
	public ServerAction(Date timestamp) {
		_timeStamp = timestamp;
	}
	
	public ServerAction( Date timestamp, String action) {
		_timeStamp = timestamp;
		_action = action;
	}
	public ServerAction(int serverId, Date timestamp, String action) {
		_serverId=serverId;
		_timeStamp = timestamp;
		_action = action;
	}

	public Date getTimeStamp() {
		return _timeStamp;
	}
	
	public String getAction() {
		return _action;
	}
	public int getServerId() {
		return _serverId;
	}
	public void setServerId(int id) {
		_serverId=id;
	}

	@Override
	public boolean equals(Object o) {
 
        // If the object is compared with itself then return true  
        if (o == null) {
            return true;
        }

        if (!(o instanceof ServerAction)) {
            return false;
        }
        ServerAction c = (ServerAction) o;
        if (this._action == c._action && this._timeStamp ==c._timeStamp) {
            return true;
        }
        return false;
    }
	
	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + (this._action != null ? this._action.hashCode() : 0);
	    hash = 53 * hash + (this._timeStamp != null ? this._timeStamp.hashCode() : 0);
	    return hash;
	}
	
}
