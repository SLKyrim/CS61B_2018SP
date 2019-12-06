/**
 * Simulator of NBody
 */
public class NBody {
    
    /** Reads the radius of the universe */
    public static double readRadius(String filename) {
        In in = new In(filename);
        int tmp = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /** Reads the Planets of the universe */
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int N = in.readInt(); // the number of the planets
        double radius = in.readDouble();

        Planet[] planets = new Planet[N];
        for (int i = 0; i < N; i++) {
            /**
             * this will throw a exception - java.lang.NullPointerException
             * @author SingleLong 20191206
             */
            // planets[i].xxPos = in.readDouble();
            // planets[i].yyPos = in.readDouble();
            // planets[i].xxVel = in.readDouble();
            // planets[i].yyVel = in.readDouble();
            // planets[i].mass = in.readDouble();
            // planets[i].imgFileName = in.readString();

            // need the first constructor
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return planets;
    }

    /** Draws the initial universe state (main) */
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        /**
         * test for Collecting All Needed Input
         * @author SingleLong 20191206
         */
        // System.out.println(T);
        // System.out.println(dt);
        // System.out.println(radius);

        
    }
}