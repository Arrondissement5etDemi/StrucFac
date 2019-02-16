import java.util.*;

/**This is a class mainly used to provide functions that samples points on spheres/balls.*/

public class SphereSampling {
	private static Random randGen = new Random();

	/**generates a Vector3D from the uniform distribution on a sphere with radius r*/
	/**normal distribution coord-wise method
 * 	@param r double, the radius of the sphere
 * 	@return Vector3D, the vector generated */
	public static Vector3D uniform(double r) {
		double x = randGen.nextGaussian();
		double y = randGen.nextGaussian();
		double z = randGen.nextGaussian();
		double l = Math.sqrt(x*x + y*y+ z*z); //length of the vector (x,y,z)
		Vector3D result = new Vector3D(r*x/l,r*y/l,r*z/l);
		return result;
	}
}
