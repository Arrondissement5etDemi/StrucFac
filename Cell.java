import java.util.*;
/** models the unit cell of a general simulation box */

public class Cell {
	private double sideLength; // the side length of the cell
	private int size; // number of particles in the cell
	private Particle[] partiArr; // the array of partiles in the cell
	
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

}
