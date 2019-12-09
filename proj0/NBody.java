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
        /** Collecting All Needed Input */
        
        /* Use for gradescope.com */
        // double T = Double.parseDouble(args[0]);
        // double dt = Double.parseDouble(args[1]);
        // String filename = args[2];
        
        /* Use for Run in local */
        double T = 157788000.0; 
        double dt = 25000.0;
        String filename = "data/planets.txt";
        
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        /**
         * test for Collecting All Needed Input
         * @author SingleLong 20191206
         */
        // System.out.println(T);
        // System.out.println(dt);
        // System.out.println(radius);

        /** Draw background */
        StdDraw.setScale(-radius, radius); // Sets up the universe so it goes from (-radius, -radius) up to (radius, radius); the center of the universe is (0,0)
        StdDraw.clear(); // Clears the drawing window
        StdDraw.picture(0, 0, "images/starfield.jpg");
        StdDraw.show();
        
        /** Draw Planets */
        for (Planet planet : planets) {
            planet.draw();
        }

        /** Create animation */
        StdDraw.enableDoubleBuffering(); // prevent flickering in the animation
        
        for (int time = 0; time < T; time += dt) {
            /** Create an xForces array and yForces array */
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            /** Calculate the net x and y forces for each planet, storing these in the xForces and yForces arrays respectively */
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }

            /** Call update on each of the planets. This will update each planet’s position, velocity, and acceleration. */
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            /** Draw the background image */
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");

            /** Draw all of the planets */
            for (Planet planet : planets) {
                planet.draw();
            }

            /** Show the offscreen buffer (see the show method of StdDraw) */
            StdDraw.show();

            /** Pause the animation for 10 milliseconds (see the pause method of StdDraw). You may need to tweak this on your computer */
            StdDraw.pause(1);
        }

        /** Printing the Universe */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", 
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, 
                          planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
    }
}