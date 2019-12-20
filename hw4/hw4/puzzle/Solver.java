package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    Stack<WorldState> solutions = new Stack<>(); // cache the process for solution

    /** Search Node in A* algorithm */
    private class SearchNode implements Comparable<SearchNode> {
        private WorldState curr; // world state of this search node
        private int moves; // moves so far to reach this search node (world state)
        private WorldState prev; // previous world state of this search node
        int priority = Integer.MAX_VALUE;

        public SearchNode(WorldState currIn, int movesIn, WorldState prevIn) {
            curr = currIn;
            moves = movesIn;
            prev = prevIn;
            priority = moves + curr.estimatedDistanceToGoal();
        }

        public WorldState getPrev() {
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
        Stack<WorldState> tmp = new Stack<>(); // cache solutions reversely
        MinPQ<SearchNode> searchNodes = new MinPQ<>(); // the PQ caches search nodes

        searchNodes.insert(new SearchNode(initial, 0, null));

        while (!searchNodes.isEmpty()) {
            SearchNode currMin = searchNodes.delMin(); // remove node with minimum priority
            WorldState prev = currMin.getPrev();
            WorldState curr = currMin.getWorldState();
            int currMoves = currMin.getMoves();
            tmp.push(curr);
            if (curr.isGoal()) {
                break;
            }
            else {
                for (WorldState neighbor : curr.neighbors()) {
                    if (prev == null || !neighbor.equals(prev)) {
                        /* !neighbor.equals(prev):
                        the critical optimization to avoid same node enqueue many times
                         */
                        searchNodes.insert(new SearchNode(neighbor, currMoves + 1, curr));
                    }
                }
            }
        }

        while (!tmp.isEmpty()) {
            solutions.push(tmp.pop());
        }
    }

    /** Returns the minimum number of moves to solve the
     * puzzle starting at the initial WorldState.
     */
    public int moves() {
        return solutions.size();
    }

    /** Returns a sequence of WorldStates from the initial WorldState
     * to the solution
     */
    public Iterable<WorldState> solution() {
        return solutions;
    }
}
