import java.util.*;

/**This is a class that contains functions that generate various point patterns for 1,2,3 dimensions*/
public class PointProcess {
	/**generates a Cell of 1D lattice
 * 	@param n int, the number of particles in the unit cell
 * 	@param l double, the unit cell length
 * 	@return Cell, a Cell with n particles for the 1D lattice */
	public static Cell lattice1D(int n, double l) {
		Cell c = new Cell(n, l);
		double neighborDist = l/(double)n;
		Particle[] pa = new Particle[n];
		for (int i = 0; i < n; i++) {
			pa[i] = new Particle(i*neighborDist,0,0);
		}
		c.setPartiArr(pa);
		return c;
	}

	/**generates a 1D ideal gas
 * 	@param n int, the number of particles in the unit cell
 * 	@param l double, the unit cell length
 * 	@return Cell, a ideal gas Cell with n particles on 1D */
	public static Cell poisson1D(int n, double l) {
		Cell c = new Cell(n,l);
		Particle[] pa = new Particle[n];
		double x = 0;
                for (int i = 0; i < n; i++) {
			x = getRandomNumberInRange(0.0,l);
                        pa[i] = new Particle(x,0,0);
                }
                c.setPartiArr(pa);
                return c;
	}

	private static double getRandomNumberInRange(double min, double max) {
                if (min >= max) {
                        throw new IllegalArgumentException("max must be greater than min");
                }
                Random r = new Random();
                return r.nextDouble()*(max - min) + min;
        }
}

