import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {

        Node start = g.getNode(g.closest(stlon, stlat));
        Long startID = start.getId();
        Node dest = g.getNode(g.closest(destlon,destlat));
        Long destID = dest.getId();

        // The distance(Double) from start to the vertex with a id(Long)
        HashMap<Long, Double> distTo = new HashMap<>();
        // the edge between a vertex with a id(Long) and another one with a id(Long)
        HashMap<Long, Long> edges = new HashMap<>();

        class SearchNode implements Comparator<Node> {
            @Override
            public int compare(Node o1, Node o2) {
                // Compare the A* distance (= distance to start + distance to destination)
                double disto1 = distTo.get(o1.getId()) + g.distance(o1.getId(), destID);
                double disto2 = distTo.get(o2.getId()) + g.distance(o2.getId(), destID);
                return Double.compare(disto1, disto2);
            }
        }

        // Sorts the nodes in the queue by priority of SearchNode
        PriorityQueue<Node> fringe = new PriorityQueue<>(new SearchNode());

        // Initialize the distTo, edges and fringe
        for (Long v : g.vertices()) {
            if (v.equals(startID)) {
                distTo.put(v, 0.0); // first to remove will be the start vertex
            } else {
                distTo.put(v, Double.MAX_VALUE);
            }
            edges.put(v, 0L);
            fringe.add(g.getNode(v));
        }

        boolean isReached = false;
        ArrayList<Long> path = new ArrayList<>();

        while (!isReached && !fringe.isEmpty()) {
            Node curr = fringe.remove();
            Long currID = curr.getId();
            for (Long v : g.adjacent(currID)) {
                Double currDist = distTo.get(currID) + g.distance(v, currID);
                if (distTo.get(v) > currDist) {
                    distTo.replace(v, currDist); // update the distance from start to vertex v
                    edges.replace(v, currID); // build a connection between current vertex and v
                    /* update fringe by removing the old Node with a DOUBLE.MAX_VALUE
                    distance and adding the new Node with the same id as the old Node
                    with the updated distance
                     */
                    fringe.remove(g.getNode(v));
                    // next loop the node with the smallest currDist will be removed to be curr
                    fringe.add(g.getNode(v));
                }
                if (v.equals(destID)) {
                    isReached = true;
                }
            }
        }

        Long currID = destID;

        while (!currID.equals(startID) && !currID.equals(0L)) {
            path.add(0, currID);
            currID = edges.get(currID);
        }
        path.add(0, startID);

        return path;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
