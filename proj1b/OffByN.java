public class OffByN implements CharacterComparator {

    private int n;

    /** Creates an object whose equalChars method returns true
     *  for characters that are off by N */
    public OffByN(int N) {
        n = N;
    }

    /** Returns true for characters that are different by N */
    @Override
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == n) {
            return true;
        }
        return false;
    }
}