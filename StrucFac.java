import java.util.*;

public class StrucFac {

	public static double pi = Math.PI;

	public static void main(String[] args) {
			double[][] sk = new double[801][2]; 
			double[][] sampleSk = new double[801][2];
			int numSample = 1;
			
		for (int m = 0; m < numSample; m++) {
			Cell pp = PointProcess.triangle2D(3600,60.0);
			sampleSk = strucFacReport2D(pp);
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

	/**generates the table of ANGULAR AVERAGED s(k) for various k in 2D
 * 	@param c Cell, which specifies the configuration of the particles
 * 	@return double[][], the table of s(k), where k is reported as multiples of pi */
	public static double[][] strucFacReport2D(Cell c) {
		double[][] result = new double[801][2];
		Vector3D k = new Vector3D(0.0,0.0,0.0);
		double angle, length;
                for (int i = 0; i <= 800; i++) {
			length = (double)i*0.01*pi;
			result[i][0] = length/pi;
			for (int j = 0; j < 200; j++) {
				angle = (double)j*0.01*pi;//run angle from 0 to 2pi
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

