/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        // return a == b;
        // == only can be correct in range of [-128, 127]
        // out of this range can only use equals() to get the correct return
        return a.equals(b);
    }
}
