import java.util.*;

public class TestSphereSampling {
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			Vector3D k = SphereSampling.uniform(1.0);
			System.out.println(k);
		}
	}
}
