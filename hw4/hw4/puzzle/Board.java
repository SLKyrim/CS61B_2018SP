package hw4.puzzle;

public class Board implements WorldState {

    /** Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {

    }

    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {

    }

    /** returns the board size N */
    public int size() {

    }

    /** returns the neighbors of the current board */
    @Override
    public Board neighbors() {

    }

    /** Hamming estimate
     * returns the number of tiles in the wrong position
     */
    public int hamming() {

    }

    /** Manhattan estimate
     * returns the sum of the Manhattan distances (sun
     * of the vertical and horizontal distance) from the
     * tiles to their goal positions.
     */
    public int manhattan() {

    }

    /** estimated distance to goal. This method should simply
     *  return the results of manhattan() when submitted to Gradescope
     */
    @Override
    public int estimatedDistanceToGoal() {

    }

    /** returns true if this board's tile values are the same
     * position as y's
     */
    @Override
    public boolean equals() {

    }


    /** Returns the string representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
