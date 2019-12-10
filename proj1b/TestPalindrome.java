import com.sun.tools.javac.Main;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));

        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", obo));
        assertTrue(palindrome.isPalindrome("smelt", obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("a", obo));
        assertFalse(palindrome.isPalindrome("aa", obo));
        assertFalse(palindrome.isPalindrome("aaa", obo));

        OffByN obo5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("", obo5));
        assertTrue(palindrome.isPalindrome("a", obo5));
        assertFalse(palindrome.isPalindrome("aa", obo5));
        assertFalse(palindrome.isPalindrome("aaa", obo5));
        assertTrue(palindrome.isPalindrome("tiny", obo5));
        assertTrue(palindrome.isPalindrome("tiffany", obo5));
    }
}
