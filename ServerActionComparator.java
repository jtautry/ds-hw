import java.util.Comparator;

/**
 * Comparator to help sort the server action lists
 *
 */
public class ServerActionComparator implements Comparator<ServerCommand> {

	@Override
	public int compare(ServerCommand o1, ServerCommand o2) {
		return o1.getTimeStamp().compareTo(o2.getTimeStamp());
	}

}
