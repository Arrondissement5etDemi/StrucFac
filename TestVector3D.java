import java.util.*;

public class TestVector3D {
	public static void main(String[] args) {
		Vector3D a = new Vector3D(1,2,3);
		Vector3D b = new Vector3D(4,5,6);
		Vector3D aMinusB = a.minus(b);
		double aDotB = a.innerProd(b);
		System.out.println(aMinusB);
		System.out.println(aDotB);
	}
}
