import java.util.List;
import java.util.ArrayList;

public class Edge {
    private long id;
    private List<Long> nodes;
    private String maxSpeed = "";

    public Edge(long id) {
        this.id = id;
        nodes = new ArrayList<>();
    }

    public void addNode(long id) {
        nodes.add(id);
    }

    public List<Long> getNodes() {
        return nodes;
    }

    public void setMaxSpeed(String s) {
        maxSpeed = s;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }
}
