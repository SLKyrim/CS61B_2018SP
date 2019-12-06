/**
 * Celestial Planet Class
 */
public class Planet {

    /** Planet's current x position */
    public double xxPos;

    /** Planet's current y position */
    public double yyPos;

    /** Planet's current velocity in the x direction */
    public double xxVel;

    /** Planet's current velocity in the y direction */
    public double yyVel;

    /** Planet's mass */
    public double mass;

    /** The name of the file that corresponds to the image that depicts the Planet */
    public String imgFileName;

    /** The first constructor */
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /** The second constructor : take in a Planet object and initialize an identical Planet object */
    public Planet(Planet b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }  
}