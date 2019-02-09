import java.util.*;

/**A particle of at most 3D*/
public class Particle {
        //data
	private double x,y,z;//coordinates of the spheres

	//constructor
        public Particle(double xx, double yy, double zz) {
		x = xx;
                y = yy;
		z = zz;
        }

        //gets the coordinates
        public double getx() {
		return x;
	}
    
	public double gety() {
		return y;
	}

	public double getz() {
		return z;
	}

	//modifiers
	public void setX(double xx) {
		x = xx;
	}

	public void setY(double yy) {
		y = yy;
	}

	public void setZ(double zz) {
		z = zz;
	}

	/**computes the distance to another particle
 * 	@param another Particle, another particle */
	public double distanceto(Particle another) {
		double anotherX = another.getx();
                double anotherY = another.gety();
		double anotherZ = another.getz();
                return Math.sqrt(Math.pow((x - anotherX),2) + Math.pow((y - anotherY),2) +  Math.pow((z - anotherZ),2));
	}

	/**decides if two particles are the same
 * 	@param another Particle, another particle */
	public boolean equals(Particle another) {
		return (x == another.getx() && y == another.gety() && z == another.getz());
	}

	public String toString() {
		return "(" + Double.toString(x) + "," + Double.toString(y) + ',' + Double.toString(z) + ")";
	}
}
