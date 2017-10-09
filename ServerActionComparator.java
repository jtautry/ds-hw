import java.util.Comparator;

public class ServerActionComparator implements Comparator<ServerAction> {

	@Override
	public int compare(ServerAction o1, ServerAction o2) {
		return o1.getTimeStamp().compareTo(o2.getTimeStamp());
	}

}
