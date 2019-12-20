package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    // cache the process for solution
    private Stack<WorldState> solutions = new Stack<>();

    /** Search Node in A* algorithm */
    private class SearchNode implements Comparable<SearchNode> {
        private WorldState curr; // world state of this search node
        private int moves; // moves so far to reach this search node (world state)
        private SearchNode prev; // previous world state of this search node

        /* second optimization, by caching the estimation
        to avoid repeatedly call the time-consuming estimatedDistanceToGoal()
         */
        int priority = Integer.MAX_VALUE;

        public SearchNode(WorldState currIn, int movesIn, SearchNode prevIn) {
            curr = currIn;
            moves = movesIn;
            prev = prevIn;
            priority = moves + curr.estimatedDistanceToGoal();
        }

        public SearchNode getPrevNode() {
            return prev;
        }

        public WorldState getWorldState() {
            return curr;
        }

        public int getMoves() {
            return moves;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }
    }

    /** Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> searchNodes = new MinPQ<>(); // the PQ caches search nodes

        SearchNode goal = null;

        searchNodes.insert(new SearchNode(initial, 0, null));

        while (!searchNodes.isEmpty()) {
            SearchNode currNode = searchNodes.delMin(); // remove node with minimum priority
            SearchNode prevNode = currNode.getPrevNode();
            WorldState currWorld = currNode.getWorldState();
            int moves = currNode.getMoves();
            if (currWorld.isGoal()) {
                goal = currNode;
                break;
            } else {
                for (WorldState n : currWorld.neighbors()) {
                    if (prevNode == null || !n.equals(prevNode.getWorldState())) {
                        /* !neighbor.equals(prev):
                        the critical optimization to avoid same node enqueue many times
                         */
                        searchNodes.insert(new SearchNode(n, moves + 1, currNode));
                    }
                }
            }
        }

        while (goal != null) {
            solutions.push(goal.getWorldState());
            goal = goal.getPrevNode();
        }
    }

    /** Returns the minimum number of moves to solve the
     * puzzle starting at the initial WorldState.
     */
    public int moves() {
        return solutions.size() - 1;
    }

    /** Returns a sequence of WorldStates from the initial WorldState
     * to the solution
     */
    public Iterable<WorldState> solution() {
        return solutions;
    }
}
