import java.util.List;
import java.util.ArrayList;

public class Node {
    private long id;
    private double lon;
    private double lat;
    private List connections;

    public Node(long id, double lon, double lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        connections = new ArrayList<Long>();
    }

    public long getId() {
        return id;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public List getConnections() {
        return connections;
    }

    public void addConnection(long id) {
        if (!connections.contains(id) && id != this.id) {
            connections.add(id);
        }
    }
}
