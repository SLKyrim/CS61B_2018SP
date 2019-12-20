package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private final int BLANK = 0; // namely the position that can move to
    private int[][] board;
    private int size; // cache the size of the board

    /** Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        size = tiles.length;
        int sizey = tiles[0].length;
        if (size != sizey) {
            throw new IllegalArgumentException("input tiles must be square");
        }
        board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IndexOutOfBoundsException("tile index out of bounds");
        }
        return board[i][j];
    }

    /** returns the board size N */
    public int size() {
        return size;
    }

    /** returns the neighbors of the current board
     * @source http://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /** Hamming estimate
     * returns the number of tiles in the wrong position
     * @source https://github.com/btke/CS61B-Spring-2018
     */
    public int hamming() {
        int res = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int currPos = i * size + j + 1;
                if (tileAt(i, j) != BLANK && tileAt(i, j) != currPos) {
                    res += 1;
                }
            }
        }
        return res;
    }

    /** Manhattan estimate
     * returns the sum of the Manhattan distances (sun
     * of the vertical and horizontal distance) from the
     * tiles to their goal positions.
     * @source https://github.com/btke/CS61B-Spring-2018
     */
    public int manhattan() {
        int res = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int currPos = tileAt(i, j);
                if (currPos != BLANK) {
                    int expectedRow = (currPos - 1) / size;
                    int expectedCol = (currPos - 1) % size;
                    res += Math.abs(i - expectedRow);
                    res += Math.abs(j - expectedCol);
                }
            }
        }
        return res;
    }

    /** estimated distance to goal. This method should simply
     *  return the results of manhattan() when submitted to Gradescope
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /** returns true if this board's tile values are the same
     * position as o's
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Board boardToCompare = (Board) o;
        if (boardToCompare.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boardToCompare.tileAt(i, j) != tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        return 1;
    }


    /** Returns the string representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
