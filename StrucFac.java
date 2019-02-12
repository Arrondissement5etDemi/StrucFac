import java.util.*;

public class StrucFac {

	public static double pi = Math.PI;

	public static void main(String[] args) {
			double[][] sk = new double[801][2]; 
			double[][] sampleSk = new double[801][2];
			int numSample = 1;
			
		for (int m = 0; m < numSample; m++) {
			Cell pp = PointProcess.triangle2D(900,30.0);
			Vector3D k11 = new Vector3D(2*pi,2*pi,0);
			double[][] ppPlot = strucFac2D(pp,161);//for the 3D plot of s(k) where k is in 2D
			for (int i = 0; i < ppPlot.length; i++) {
				System.out.println(ppPlot[i][0]+","+ppPlot[i][1]+","+ppPlot[i][2]);
			}
			sampleSk = strucFacReport2D(pp);//for angular average
			for (int i = 0; i < sk.length; i++) {
                 	       sk[i][1] = sk[i][1] + sampleSk[i][1];
                	}
		}

		for (int i = 0; i < sk.length; i++) {
			sk[i][0] = sampleSk[i][0];
			sk[i][1] = sk[i][1]/(double)numSample;
			System.out.println(sk[i][0]+" "+sk[i][1]);
		}
	}

	/**generates the table of s(k) for various k in 1D
 * 	@param c Cell, which specifies the configuration of the particles
 * 	@return double[][], the table of s(k), where k is in multiples of pi */
	public static double[][] strucFacReport1D(Cell c) {
		double[][] result = new double[401][2];
		Vector3D k = new Vector3D(0.0,0.0,0.0);
		for (int i = 0; i <= 400; i++) {
                        result[i][0] = (double)i*0.01;
                        k.setX(result[i][0]*pi);
                        result[i][1] = sk(c,k);
                }
                return result;
	}
	
	/**generates the s(k) value for the vector k in a 2D plane (6pi*6pi)
 * 	@param c Cell, which specifies the configuration of the particles
 * 	@param plotRange int, how many data on the side of box
 * 	@return double[][], the table of s(k), where k is a Vector3D with z = 0 */
  	public static double[][] strucFac2D(Cell c, int plotRange) {
		int plotSize = plotRange*plotRange;
		double[][] result = new double[plotSize][3];
		Vector3D k = new Vector3D(0.0,0.0,0.0);
		double x, y;
		int index = 0;
		for (int i = 0; i < plotRange; i++) {
			x = i*(1.0/20.0)*pi;
			k.setX(x);
			for (int j = 0; j < plotRange; j++) {
				y = j*(1.0/20.0)*pi;
				k.setY(y);
				result[index][0] = x/pi;
				result[index][1] = y/pi;
				result[index][2] = sk(c,k); 
				index++;
			}
		}
		return result;
  	}
 
	/**generates the table of ANGULAR AVERAGED s(k) for various k in 2D
 * 	@param c Cell, which specifies the configuration of the particles
 * 	@return double[][], the table of angular averaged s(k), where k is reported as multiples of pi */
	public static double[][] strucFacReport2D(Cell c) {
		double[][] result = new double[801][2];
		Vector3D k = new Vector3D(0.0,0.0,0.0);
		double angle, length;
                for (int i = 0; i <= 800; i++) {
			length = (double)i*0.01*pi;
			result[i][0] = length/pi;
			for (int j = 0; j < 400; j++) {
				angle = (double)j*0.005*pi;//run angle from 0 to 2pi
				k.setX(length*Math.cos(angle));
				k.setY(length*Math.sin(angle));
                        	result[i][1] = result[i][1] + sk(c,k);
			}
			result[i][1] = result[i][1]/200.0;
                }
		return result;
	}

	/**computes the structure factor s(k) for a Cell config
 * 	@param c Cell, which specifies the configuration of the particles
 * 	@param k Vector3D, the argument wavevector
 * 	@return double, the structure factor */
	public static double sk(Cell c, Vector3D k) {
		int n = c.getSize(); //the numberr of particles
		Particle[] pa = c.toArray();
		Complex sum = new Complex(0.0);
		for (int i = 0; i < n; i++) {
			sum = sum.add(term(k,pa[i]));
		}
		double result = Math.pow(sum.getNorm(),2.0)/(double)n;
		return result;
	}
	
	/**computes a term of the structure factor
 * 	@param k Vector3D, the argument wavevector
 * 	@param r Particle, the particle in the term
 * 	@return Complex, the term */
	private static Complex term(Vector3D k, Particle r) {
		Vector3D rVec = new Vector3D(r.getx(),r.gety(),r.getz());
		Complex arg = new Complex(-k.innerProd(rVec));
		Complex exponent = Complex.i().times(arg);
		return exponent.expThis();	
	}

	public static void lat1d() {
		Cell lat1 = PointProcess.lattice1D(40,40.0);
                System.out.println(lat1);
                Vector3D k = new Vector3D(0.0,0.0,0.0);
                for (double i = 0.0; i <= 4.0*pi; i = i + 0.01*pi) {
                        k.setX(i);
                        System.out.println(i/pi+" "+sk(lat1,k));
                }
	}
}

