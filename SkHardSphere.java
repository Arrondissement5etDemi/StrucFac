import java.util.*;

public class SkHardSphere {

	public static double pi = Math.PI;

	public static void main(String[] args) {
		double[][] sk = new double[401][2]; 
		double[][] sampleSk = new double[401][2];
		double[][] g2 = new double[400][2];
		double[][] sampleG2 = new double[401][2];
		int numSample = 20;
			
		for (int m = 0; m < numSample; m++) {
			Cell pp = PointProcess.hardSphere3D(12,0.2);
			//sampleG2 = RadialStat.g2(pp,100,4);
			sampleSk = strucFacReport3D(pp);
			for (int i = 0; i < sk.length; i++) {
                               sk[i][1] = sk[i][1] + sampleSk[i][1];
			}
			System.out.println(m);
			//for (int i = 0; i < g2.length; i++) {
                 	//       g2[i][1] = g2[i][1] + sampleG2[i][1];
                	//}
		}

		//for (int i = 0; i < g2.length; i++) {
		//	g2[i][0] = sampleG2[i][0];
		//	g2[i][1] = g2[i][1]/(double)numSample;
		//	System.out.println(g2[i][0]+" "+g2[i][1]);
		//}
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
		for (int i = 0; i <= 300; i++) {
                        result[i][0] = (double)i*0.1;
                        k.setX(result[i][0]/c.getDiam());
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
			result[i][1] = result[i][1]/400.0;
                }
		return result;
	}

	/**generates the table of SPHERE AVERAGED s(k) for various k in 3D
 *      @param c Cell, which specifies the configuration of the particles
 *      @return double[][], the table of sphere averaged s(k), where k is reported as multiples of pi */
        public static double[][] strucFacReport3D(Cell c) {
                double[][] result = new double[401][2];
                Vector3D k = new Vector3D(0.0,0.0,0.0);
                double length;
		double diam = c.getDiam();
                for (int i = 0; i < result.length; i++) {
                        length = (double)i*0.05/diam;
                        result[i][0] = length*diam;
                        for (int j = 0; j < 500; j++) {
                                k = SphereSampling.uniform(length);
                                result[i][1] = result[i][1] + sk(c,k);
                        }
                        result[i][1] = result[i][1]/500.0;
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

