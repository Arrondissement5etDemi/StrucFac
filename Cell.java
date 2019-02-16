import java.util.*;
/** models the unit cell of a general simulation box */

public class Cell {
	private double sideLength; // the side length of the cell
	private int size; // number of particles in the cell
	private Particle[] partiArr; // the array of partiles in the cell
	private double diam = 0.2; //the diameter of the spherical particles
	
	public Cell(int n, double l) {
		sideLength = l;
		size = n;
		partiArr = new Particle[size];
	}

	/**initializes the Cell with a predefined particle array 
 * 	@param pa Particle[] the predefined particle array */
	public void setPartiArr(Particle[] pa) {
		for (int i = 0; i < Math.min(size,pa.length); i++) {
			partiArr[i] = pa[i];
		}
	}
	
	/**sideLength accessor
 * 	@return double, the side length of the cell */
	public double getSideLength() {
		return sideLength;
	}

	/**size accessor
 * 	@return int, the number of particles in the cell */
	public int getSize() {
		return size;
	}

	/**diam accessor
 *  *      @return double, the diameter of the spherical particles */
        public double getDiam() {
                return diam;
        }

	/**PartiArr accessor
 * 	@return Parti[], a deep copy of PartiArr */
	public Particle[] toArray() {
		Particle[] partiArrCopy = new Particle[size];
		for (int i = 0; i < size; i++) {
			Particle p = partiArr[i];
			Particle partiCopy = new Particle(p.getx(),p.gety(),p.getz());
			partiArrCopy[i] = partiCopy;
		}
		return partiArrCopy;
	}

	/**Set diameter
 * 	@param d double, diameter to be set */
	public void setDiam(double d ) {
		diam = d;
	}
	
	/**moves a random particle in partiArr in a random direction by a random distance between 0 and maxDist
 *  	@param maxDist double, the maximum distance to move for a movement in each dimension */
	public void move(double maxDist) {
		int ind = 0;
		Particle thisParti = partiArr[0];
		boolean hasCollision = true;
		double distX, distY, distZ;
		while (hasCollision) {
			ind = (int) Math.floor(getRandomNumberInRange(0.0,(double)size));
			thisParti = partiArr[ind];
			distX = getRandomNumberInRange(-maxDist,maxDist);
			distY = getRandomNumberInRange(-maxDist,maxDist);
			distZ = getRandomNumberInRange(-maxDist,maxDist);
			Movement m = new Movement(thisParti, distX, distY, distZ);
			move(m);
			if (partiCollision(ind) <= 1) {
				hasCollision = false;
			}
			else {
				move(m.reverse());
			}
		}
	}

	/**moves a Particle with a Movement
 *  	@param m, the Movement */
	public void move(Movement m) {
		Particle p = m.getParticle();
		double x = p.getx();
		double y = p.gety();
		double z = p.getz();
		p.setX(positiveModulo(x + m.getDx(),sideLength));
		p.setY(positiveModulo(y + m.getDy(),sideLength));
		p.setZ(positiveModulo(z + m.getDz(),sideLength));
	}

	/**gets the number of collisions of a single particle in the box
 *   	@param ind integer, the index of the particle in partiArr 
 *    	@return int, the collisions of the particle */
	private int partiCollision(int ind) {
		if (ind >= size) {
			throw new IllegalArgumentException("input index must be < the number of particles");
		}
		Particle thisParti = partiArr[ind];//get the particle in question
		return collisionChecker(thisParti, size);
	}

	/**checks how many times a particle collides with any of the first m particles in partiArr
 *   	@param thisParti Particle, the particle we are checking
 *    	@param m int, we are checking collisions with the first m particles in partiArr
 *    	@param diam double, the diametet of the particles in a hard sphere system
 *    	@return int, the number of collisions */
	public int collisionChecker(Particle thisParti, int m) {
		int collisions = 0;
                for (int i = 0; i < m ; i++) {
                        Particle thatParti = partiArr[i];
			if (minDist(thisParti,thatParti) < diam) {
				collisions ++;
			}
		}
		return collisions;
	}

	/**the minimum distance between two particles in PBC
 *   	@param thisP Particle
 *    	@param another Particle, another particle 
 *      @return the minimum distance to that particle among all its replicates */
	public double minDist(Particle thisP, Particle another) {
                double thatX = another.getx();
                double thatY = another.gety();
                double thatZ = another.getz();
                double miniDx = minIn3(thatX,thatX+sideLength,thatX-sideLength,thisP.getx());
                double miniDy = minIn3(thatY,thatY+sideLength,thatY-sideLength,thisP.gety());
                double miniDz = minIn3(thatZ,thatZ+sideLength,thatZ-sideLength,thisP.getz());
                return Math.sqrt(miniDx*miniDx + miniDy*miniDy + miniDz*miniDz);
        }

	public static double minIn3(double a, double b, double c, double center) {
		return Math.min(Math.abs(a-center),Math.min(Math.abs(b-center),Math.abs(c-center)));
	}

	/**checks if a particle is in the cell
 * 	@param p, Particle, a particular particle
 * 	@return boolean, if the particle is in the cell */
	public boolean checkIn(Particle p) {
		for (int i = 0; i < size; i++) {
			if (p.equals(partiArr[i])) {
				return true;
			}
		}
		return false;
	} 

	/**to string
 * 	@return String, a string of the info of the particles in the cell */
	public String toString() {
		String result = "";
		for (int i = 0; i < size; i++) {
			result = result + partiArr[i].toString() + "\n";
		}
		return result;
	}

	/**auxillaries*/
	private static double getRandomNumberInRange(double min, double max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
	        }		
		Random r = new Random();	
       		return r.nextDouble()*(max - min) + min;
	}
	
		
	private static double positiveModulo(double x, double d) {
		double result = x%d;
		if (result < 0) {
			result = result + d;
		}
		return result;
	}

}
