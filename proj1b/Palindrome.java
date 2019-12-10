public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> res = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            res.addLast(word.charAt(i));
        }
        return res;
    }
}
