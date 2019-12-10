/**
 * Test for ArrayDeque
 */
public class ArrayDequeTest {

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
    }
    
    /* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}
    
    /* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

    public static void addRemoveTest() {

		System.out.println("Running add/remove test.");
		
		ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, ad.isEmpty());

		ad.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, ad.isEmpty()) && passed;

		ad.removeFirst();
		// should be empty 
		passed = checkEmpty(true, ad.isEmpty()) && passed;

		printTestStatus(passed);		
    }

    public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");
	
		ArrayDeque<String> ad = new ArrayDeque<String>();

		boolean passed = checkEmpty(true, ad.isEmpty());

		ad.addFirst("front");
		
		passed = checkSize(1, ad.size()) && passed;
		passed = checkEmpty(false, ad.isEmpty()) && passed;

		ad.addLast("middle");
		passed = checkSize(2, ad.size()) && passed;

		ad.addLast("back");
        passed = checkSize(3, ad.size()) && passed;
        
        ad.addFirst("frontTmp");
        passed = checkSize(4, ad.size()) && passed;
        System.out.println("Printing out deque: ");
        ad.printDeque();
        
        ad.removeFirst();
        ad.removeLast();
        passed = checkSize(2, ad.size()) && passed;

		System.out.println("Printing out deque: ");
		ad.printDeque();

		printTestStatus(passed);
	}
    
    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addRemoveTest();
        addIsEmptySizeTest();
	}
}
