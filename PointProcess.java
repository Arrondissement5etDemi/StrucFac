import java.util.*;

/**This is a class that contains functions that generate various point patterns for 1,2,3 dimensions*/
public class PointProcess {
	/**generates a 2D triangular lattice
 * 	@param n int, the number of particles in the unit cell
 * 	@param l double, the unit cell side length. The cell axes are 60 deg apart: (1,0) and (1/2, sqrt(3)/2)
 * 	@return Cell, a Cell with n particles for the 2D triang lattice */
	public static Cell triangle2D(int n, double l) {
                if (!isSquare(n)) {
                        throw new IllegalArgumentException("n has to be a square number.");
                }
                Cell c = new Cell(n, l);
                int partiPerSide = (int)Math.sqrt((double)n); //assumed n is a square number
                double neighborDist = l/(double)partiPerSide;
                Particle[] pa = new Particle[n];
                int index = 0;
                for (int i = 0; i < partiPerSide; i++) {
                        for (int j = 0; j < partiPerSide; j++) {
				double x = (i + 0.5*j)*neighborDist;
				double y = Math.sqrt(3)*0.5*j*neighborDist;
                                pa[index] = new Particle(x,y,0);
                                index++;
                        }
                }
                c.setPartiArr(pa);
                return c;
        }


	/**generates a 2D ideal gas
 * 	@param n int, the number of particles in the unit cell
 * 	@param l double, the unit cell length
 * 	@return Cell, a ideal gas Cell with n particles in 2D */
	public static Cell poisson2D(int n, double l) {
                Cell c = new Cell(n,l);
                Particle[] pa = new Particle[n];
                double x,y;
                for (int i = 0; i < n; i++) {
                        x = getRandomNumberInRange(0.0,l);
			y = getRandomNumberInRange(0.0,l);
                        pa[i] = new Particle(x,y,0);
                }
                c.setPartiArr(pa);
                return c;
        }


	/**generates a Cell of 2D square lattice
 *      @param n int, the number of particles in the unit cell
 *      @param l double, the unit cell side length
 *      @return Cell, a Cell with n particles for the 2D lattice */
        public static Cell square2D(int n, double l) {
		if (!isSquare(n)) {
			throw new IllegalArgumentException("n has to be a square number.");
		}
                Cell c = new Cell(n, l);
		int partiPerSide = (int)Math.sqrt((double)n); //assumed n is a square number
                double neighborDist = l/(double)partiPerSide;
                Particle[] pa = new Particle[n];
		int index = 0;
                for (int i = 0; i < partiPerSide; i++) {
			for (int j = 0; j < partiPerSide; j++) {
                        	pa[index] = new Particle(i*neighborDist,j*neighborDist,0);
				index++;
			}
                }
                c.setPartiArr(pa);
                return c;
        }

	/**determines whether an integer mm is a square of some integer.
 *  	@param mm nonnegative integer, candidate of a square number
 *  	@return boolean, whether mm is really a square number*/
	private static boolean isSquare(int mm) {
		int mCandi = (int) Math.round(Math.sqrt((double) mm));
		return (mm == mCandi * mCandi);
	}

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

