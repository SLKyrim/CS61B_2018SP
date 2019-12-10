public class Palindrome {

    /** Returns a Deque where the characters appear in the same order as in the given String */
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> res = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            res.addLast(word.charAt(i));
        }
        return res;
    }

    /** Returns true if the given word is a palindrome, and false otherwise */
    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        Deque<Character> ad = wordToDeque(word);
        while (ad.size() > 1) {
            if (!ad.removeFirst().equals(ad.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
