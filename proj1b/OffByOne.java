public class OffByOne implements CharacterComparator {

    /** Returns true for characters that are different by one */
    @Override
    public boolean equalChars(char x, char y) {
        if (Math.abs(x - y) == 1) {
            return true;
        }
        return false;
    }
}
