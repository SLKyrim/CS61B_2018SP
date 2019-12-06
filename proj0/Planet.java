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

    /** Calculates the distance between two Planets */
    public double calcDistance(Planet b) {
        double dx = b.xxPos - this.xxPos;
        double dy = b.yyPos - this.yyPos;
        double r = Math.hypot(dx, dy); // returns sqrt(dx**2 + dy**2)
        return r;
    }

    /** Calculates the force exerted on this Planet by the given Planet */
    public double calcForceExertedBy(Planet b) {
        double G = 6.67e-11;
        double F = (G * this.mass * b.mass) / Math.pow(this.calcDistance(b), 2);
        return F;
    }

    /** Calculate the x-direction force exerted on this Planet by the given Planet */
    public double calcForceExertedByX(Planet b) {
        double dx = b.xxPos - this.xxPos;
        double F = this.calcForceExertedBy(b);
        double r = this.calcDistance(b);
        double Fx = F*dx/r;
        return Fx;
    }

    /** Calculate the y-direction force exerted on this Planet by the given Planet */
    public double calcForceExertedByY(Planet b) {
        double dy = b.yyPos - this.yyPos;
        double F = this.calcForceExertedBy(b);
        double r = this.calcDistance(b);
        double Fy = F*dy/r;
        return Fy;
    }

    /** Calculate the x-direction net force exerted on this Planet by the given Planets */
    public double calcNetForceExertedByX(Planet[] planets) {
        double netFx = 0;
        for (Planet planet : planets) {
            if (!planet.equals(this)) { // Planets cannot exert forces on themselves
                netFx += this.calcForceExertedByX(planet);
            }
        }
        return netFx;
    }

    /** Calculate the y-direction net force exerted on this Planet by the given Planets */
    public double calcNetForceExertedByY(Planet[] planets) {
        double netFy = 0;
        for (Planet planet : planets) {
            if (!planet.equals(this)) { // Planets cannot exert forces on themselves
                netFy += this.calcForceExertedByY(planet);
            }
        }
        return netFy;
    }

    /** 
     * Updates the velocity and position of this Planet
     * @param dt    the period of time
     * @param fX    the net force exerted on this Planet in x-direction
     * @param fY    the net force exerted on this Planet in y-direction
     */
    public void update(double dt, double fX, double fY) {
        double a_netx = fX / this.mass;
        double a_nety = fY / this.mass;
        xxVel = this.xxVel + dt * a_netx;
        yyVel = this.yyVel + dt * a_nety;
        xxPos = this.xxPos + dt * xxVel;
        yyPos = this.yyPos + dt * yyVel;
    }
}