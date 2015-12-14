package test_Matrizen_Vektoren_Bibliothek;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Matrizen_Vektoren_Bibliothek.Mat3x3;
import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Vector3 Tester.
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
public class Vector3Test extends TestCase {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	 *
	 * Method: magnitude(Vector3 v)
	 *
	 */
	@Test
	public void testMagnitude() throws Exception {

		Vector3 v1 = new Vector3(3, 3, 3);
		assertTrue(v1.magnitude == Math.sqrt(27));

	}

	/**
	 * 
	 * Method: add(Vector3 v)
	 * 
	 */
	@Test
	public void testAddV() throws Exception {
		// TODO: Test goes here...
	}

	/**
	 * 
	 * Method: add(Normal3 n)
	 * 
	 */
	@Test
	public void testAddN() throws Exception {
		// TODO: Test goes here...
	}

	/**
	 * 
	 * Method: sub(Normal3 n)
	 * 
	 */
	@Test
	public void testSub() throws Exception {
		// TODO: Test goes here...
	}

	/**
	 * 
	 * Method: mul(double c)
	 * 
	 */
	@Test
	public void testMul() throws Exception {

		Mat3x3 mat1 = new Mat3x3(1, 0, 0, 0, 1, 0, 0, 0, 1);
		Vector3 v1 = new Vector3(3, 2, 1);

		assertTrue(new Vector3(3, 2, 1).equals(mat1.mul(v1)));
	}

	/**
	 * 
	 * Method: dot(Vector3 v)
	 * 
	 */
	@Test
	public void testDotV() throws Exception {
		Vector3 v1 = new Vector3(1, 0, 0);
		assertTrue(v1.dot(new Vector3(1, 0, 0)) == 1);
		assertTrue(v1.dot(new Vector3(0, 1, 0)) == 0);
	}

	/**
	 * 
	 * Method: dot(Normal3 n)
	 * 
	 */
	@Test
	public void testDotN() throws Exception {
		Vector3 v1 = new Vector3(1, 0, 0);
		assertTrue(v1.dot(new Normal3(1, 0, 0)) == 1);
		assertTrue(v1.dot(new Normal3(0, 1, 0)) == 0);
	}

	/**
	 * 
	 * Method: normalized() Wir runden die errechnete magnitude von 0.99999999 auf den nächsten Int-Wert
	 */
	@Test
	public void testNormalized() throws Exception {
		Vector3 v1 = new Vector3(3, 5, 19);
		assertTrue(Math.round(v1.normalized().magnitude) == 1.0);
	}

	/**
	 * 
	 * Method: asNormal()
	 * 
	 */
	@Test
	public void testAsNormal() throws Exception {
		Vector3 v1 = new Vector3(3, 5, 19);
		assertTrue(v1.asNormal().equals(new Normal3(3, 5, 19)));
	}

	/**
	 * 
	 * Method: reflectedOn(Normal3 n)
	 * 
	 */

	@Test
	public void testReflectedOn() throws Exception {
		Vector3 v1 = new Vector3(-0.707, 0.707, 0);

		// System.out.println(v1.x);
		// System.out.println(v1.y);
		// System.out.println(v1.z + "\n");

		Normal3 n1 = new Normal3(0, 1, 0);

		// System.out.println(n1.x);
		// System.out.println(n1.y);
		// System.out.println(n1.z + "\n");

		// System.out.println((v1.reflectedOn(n1)).x + " "
		// + (v1.reflectedOn(n1)).y + " " + (v1.reflectedOn(n1)).z);

		assertTrue(v1.reflectedOn(n1).equals(new Vector3(0.707, 0.707, 0)));

		Vector3 v2 = new Vector3(0.707, 0.707, 0);
		Normal3 n2 = new Normal3(1, 0, 0);

		assertTrue(v2.reflectedOn(n2).equals(new Vector3(0.707, -0.707, 0)));
	}

	/**
	 * 
	 * Method: x(Vector3 v)
	 * 
	 */
	@Test
	public void testX() throws Exception {
		// TODO: Test goes here...
	}

	/**
	 * 
	 * Method: equals(Object o)
	 * 
	 */
	@Test
	public void testEquals() throws Exception {

		assertTrue(new Vector3(1, 1, 1).equals(new Vector3(1, 1, 1)));

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

		assertEquals("Vector3{x=1.0, y=1.0, z=1.0, magnitude=1.7320508075688772}", new Vector3(1, 1, 1).toString());

	}
}