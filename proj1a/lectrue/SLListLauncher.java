/**
 * SLListLauncher
 */
package lectrue;
 
public class SLListLauncher {

    public static void main(String[] args) {
        SLList<String> s1 = new SLList<>();
        s1.addLast("gaga");
        s1.addFirst("test");
        s1.addFirst("test again");
        System.out.println(s1.getFirst());
        System.out.println(s1.size());
    }
}