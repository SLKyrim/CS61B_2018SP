import static org.junit.Assert.*;
import org.junit.Test;

public class FilkTest {
    @Test
    public void testIsSameNumber() {
        Integer a = -129;
        Integer b = -129;
        assertTrue("Integer -129 is not equal to Integer -129", Flik.isSameNumber(a, b));

        Integer a0 = -1;
        Integer b0 = -1;
        assertTrue("Integer -1 is not equal to Integer -1", Flik.isSameNumber(a0, b0));

        Integer a1 = 50;
        Integer b1 = 50;
        assertTrue("Integer 50 is not equal to Integer 50", Flik.isSameNumber(a1, b1));

        Integer a2 = 128;
        Integer b2 = 128;
        assertTrue("Integer 128 is not equal to Integer 128", Flik.isSameNumber(a2, b2));

        Integer a3 = 500;
        Integer b3 = 500;
        assertTrue("Integer 128 is not equal to Integer 128", Flik.isSameNumber(a3, b3));
    }
}
