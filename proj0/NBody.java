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
            planets[i].xxPos = in.readDouble();
            planets[i].yyPos = in.readDouble();
            planets[i].xxVel = in.readDouble();
            planets[i].yyVel = in.readDouble();
            planets[i].mass = in.readDouble();
            planets[i].imgFileName = in.readString();
        }
        return planets;
    }
}