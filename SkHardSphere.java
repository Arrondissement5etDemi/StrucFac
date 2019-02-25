import java.util.*;

public class SkHardSphere {

	public static double pi = Math.PI;

	public static void main(String[] args) {
		double[][] sk = new double[601][2]; 
		double[][] sampleSk = new double[601][2];
		//double[][] skIdeal = new double[201][2];
		//double[][] sampleSkIdeal = new double[201][2];
		double[][] g2 = new double[200][2];
		double[][] sampleG2 = new double[200][2];
		int numSample = 200;
		/**treating ideal gas*/
		//for (int m = 0; m < numSample; m++) {
		//	Cell ig = PointProcess.poisson3D(10,1);
		//	sampleSkIdeal = strucFacReport3D(ig);
		//	for (int i = 0; i < skIdeal.length; i++) {
                //               skIdeal[i][1] = skIdeal[i][1] + sampleSkIdeal[i][1];
                //        }
		//	System.out.println(m+" ig");
		//}
		//for (int i = 0; i < skIdeal.length; i++) {
		//	skIdeal[i][0] = sampleSkIdeal[i][0];
                //        skIdeal[i][1] = skIdeal[i][1]/(double)numSample;
		//	System.out.println(skIdeal[i][0]+" "+skIdeal[i][1]);
		//}
		/**end treating ideal gas; now treating hard sphere equilbrium*/
		Cell pp = PointProcess.hardSphere3D(10,0.2);
		for (int m = 0; m < numSample; m++) {
			PointProcess.newHS(pp);
			//sampleG2 = RadialStat.g2(pp,50,4);
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
		double length = 0;
		double kMini = 2*pi/c.getSideLength();//the length of the smallest meaningful k		
		
		double n = 0;
                for (int i = 0; i <= result.length; i++) {
			boolean meaningfulN = false;
                        int multiplicityN = 0;
			 while (! meaningfulN) {
                                n++;
                                length = Math.sqrt(n)*kMini;
                                int searchRange = (int)Math.floor(length);
                                for (int jx = -searchRange; jx <= searchRange; jx++) {
                                for (int jy = -searchRange; jy <= searchRange; jy++) {
                                        if (jx*jx + jy*jy == n) {
                                                meaningfulN = true;
                                                k.setX(kMini*jx);
                                                k.setY(kMini*jy);
                                                result[i][1]=result[i][1] + sk(c,k);
                                                multiplicityN++;
                                        }
                                }
                                }
                        }
			result[i][0] = length;
                        result[i][1] = result[i][1]/multiplicityN;
                }

		return result;
	}

	/**generates the table of SPHERE AVERAGED s(k) for various k in 3D
 *      @param c Cell, which specifies the configuration of the particles
 *      @return double[][], the table of sphere averaged s(k), where k is reported as multiples of pi */
        public static double[][] strucFacReport3D(Cell c) {
                double[][] result = new double[601][2];
                Vector3D k = new Vector3D(0.0,0.0,0.0);
                double length = 0;
		double diam = c.getDiam();
		double kMini = 2*pi/c.getSideLength();//the length of the smallest meaningful k

		double n = 0;
                for (int i = 0; i < result.length; i++) {
			boolean meaningfulN = false;
			int multiplicityN = 0;
			while (! meaningfulN) {
				n++;
				length = Math.sqrt(n)*kMini;
				int searchRange = (int)Math.floor(length);			
				for (int jx = -searchRange; jx <= searchRange; jx++) {
				for (int jy = -searchRange; jy <= searchRange; jy++) {
				for (int jz = -searchRange; jz <= searchRange; jz++) {
					if (jx*jx + jy*jy + jz*jz == n) {
						meaningfulN = true;
						k.setX(kMini*jx);
						k.setY(kMini*jy);
						k.setZ(kMini*jz);
						result[i][1]=result[i][1] + sk(c,k);
						multiplicityN++;
					}
				}
				}	
				}
			}
                        result[i][0] = length*diam;
                        result[i][1] = result[i][1]/multiplicityN;
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

