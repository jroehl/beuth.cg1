package test_Matrizen_Vektoren_Bibliothek;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Matrizen_Vektoren_Bibliothek.Mat3x3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Mat3x3 Tester.
 * 
 * @author <Authors name>
 * @since
 * 
 * 		<pre>
 * Okt 20, 2015
 *        </pre>
 * 
 * @version 1.0
 */
public class Mat3x3Test {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * 
	 * Method: mul(Mat3x3 m)
	 * 
	 */
	@Test
	public void testMulM() throws Exception {

		Mat3x3 m1 = new Mat3x3(1, 2, 3, 4, 5, 6, 7, 8, 9);
		// System.out.println(m1.m11 + " " + m1.m12 + " " + m1.m13 + " " + "\n"
		// + m1.m21 + " " + m1.m22 + " " + m1.m23 + " " + "\n" + m1.m31
		// + " " + m1.m32 + " " + m1.m33 + "\n");
		Mat3x3 m2 = new Mat3x3(1, 0, 0, 0, 1, 0, 0, 0, 1);

		// System.out.println(m1.mul(m2).m11 + " " + m1.mul(m2).m12 + " "
		// + m1.mul(m2).m13 + " " + "\n" + m1.mul(m2).m21 + " "
		// + m1.mul(m2).m22 + " " + m1.mul(m2).m23 + " " + "\n"
		// + m1.mul(m2).m31 + " " + m1.mul(m2).m32 + " " + m1.mul(m2).m33
		// + "\n");

		assertTrue(m1.mul(m2).equals(m1));

		Mat3x3 m3 = new Mat3x3(1, 2, 3, 4, 5, 6, 7, 8, 9);

		Mat3x3 m4 = new Mat3x3(0, 0, 1, 0, 1, 0, 1, 0, 0);

		assertTrue(m3.mul(m4).equals(new Mat3x3(3, 2, 1, 6, 5, 4, 9, 8, 7)));

	}

	/**
	 * 
	 * Method: mul(Vector3 v)
	 * 
	 */
	@Test
	public void testMulV() throws Exception {

		Mat3x3 m1 = new Mat3x3(1, 0, 0, 0, 1, 0, 0, 0, 1);

		Vector3 v1 = new Vector3(3, 2, 1);

		assertTrue(m1.mul(v1).equals(v1));

	}

	/**
	 * 
	 * Method: mul(Point3 p)
	 * 
	 */
	@Test
	public void testMulP() throws Exception {

		Mat3x3 m1 = new Mat3x3(1, 0, 0, 0, 1, 0, 0, 0, 1);

		Point3 p1 = new Point3(3, 2, 1);

		assertTrue(m1.mul(p1).equals(p1));

	}

	/**
	 * 
	 * Method: changeCol1(Vector3 m)
	 * 
	 */
	@Test
	public void testChangeCol1() throws Exception {
		Mat3x3 m1 = new Mat3x3(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Vector3 v1 = new Vector3(8, 8, 8);
		assertTrue(m1.changeCol1(v1).equals(new Mat3x3(8, 2, 3, 8, 5, 6, 8, 8, 9)));
	}

	/**
	 * 
	 * Method: changeCol2(Vector3 m)
	 * 
	 */
	@Test
	public void testChangeCol2() throws Exception {
		Mat3x3 m1 = new Mat3x3(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Vector3 v1 = new Vector3(8, 8, 8);

		// System.out.println(m1.m11 + " " + m1.m12 + " " + m1.m13 + " " + "\n"
		// + m1.m21 + " " + m1.m22 + " " + m1.m23 + " " + "\n" + m1.m31
		// + " " + m1.m32 + " " + m1.m33 + "\n");

		// System.out.println(m1.changeCol2(v1).m11 + " " +
		// m1.changeCol2(v1).m12
		// + " " + m1.changeCol2(v1).m13 + " " + "\n"
		// + m1.changeCol2(v1).m21 + " " + m1.changeCol2(v1).m22 + " "
		// + m1.changeCol2(v1).m23 + " " + "\n" + m1.changeCol2(v1).m31
		// + " " + m1.changeCol2(v1).m32 + " " + m1.changeCol2(v1).m33
		// + "\n");
		assertTrue(m1.changeCol2(v1).equals(new Mat3x3(1, 8, 3, 4, 8, 6, 7, 8, 9)));
	}

	/**
	 * 
	 * Method: changeCol3(Vector3 m)
	 * 
	 */
	@Test
	public void testChangeCol3() throws Exception {
		Mat3x3 m1 = new Mat3x3(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Vector3 v1 = new Vector3(8, 8, 8);

		assertTrue(m1.changeCol3(v1).equals(new Mat3x3(1, 2, 8, 4, 5, 8, 7, 8, 8)));
	}

	/**
	 * 
	 * Method: equals(Object o)
	 * 
	 */
	@Test
	public void testEquals() throws Exception {

		Mat3x3 m1 = new Mat3x3(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Mat3x3 m2 = new Mat3x3(1, 2, 3, 4, 5, 6, 7, 8, 9);

		assertTrue(m1.equals(m2));
	}

	/**
	 * 
	 * Method: hashCode()
	 * 
	 */
	@Test
	public void testHashCode() throws Exception {
		// TODO: Test goes here...
	}

	/**
	 * 
	 * Method: toString()
	 * 
	 */
	@Test
	public void testToString() throws Exception {
		// TODO: Test goes here...
	}

}
