package test_Matrizen_Vektoren_Bibliothek;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Matrizen_Vektoren_Bibliothek.Normal3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Normal3 Tester.
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
public class Normal3Test extends TestCase {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * 
	 * Method: mul(Double n)
	 * 
	 */
	@Test
	public void testMul() throws Exception {

		final Normal3 n = new Normal3(1, 2, 3);

		final Normal3 mul = n.mul(0.5);

		final Normal3 r = new Normal3(0.5, 1, 1.5);

		assertTrue(mul.equals(r));

	}

	/**
	 * 
	 * Method: add(Normal3 n)
	 * 
	 */
	@Test
	public void testAdd() throws Exception {

		final Normal3 n1 = new Normal3(1, 2, 3);
		final Normal3 n2 = new Normal3(3, 2, 1);

		final Normal3 add = n1.add(n2);

		final Normal3 r = new Normal3(4, 4, 4);

		assertTrue(add.equals(r));

	}

	/**
	 * 
	 * Method: dot(Vector3 n)
	 * 
	 */
	@Test
	public void testDot() throws Exception {

		final Normal3 n1 = new Normal3(1, 0, 0);
		final Vector3 v1 = new Vector3(1, 0, 0);

		final Normal3 n2 = new Normal3(0, 1, 0);
		final Vector3 v2 = new Vector3(0, 1, 0);

		final double s1 = n1.dot(v1);
		final double s2 = n1.dot(v2);

		final double s3 = v1.dot(n1);
		final double s4 = v1.dot(n2);

		final double s5 = v1.dot(v1);
		final double s6 = v1.dot(v2);

		assertEquals(1.0, s1);
		assertEquals(0.0, s2);
		assertEquals(1.0, s3);
		assertEquals(0.0, s4);
		assertEquals(1.0, s5);
		assertEquals(0.0, s6);

	}

	/**
	 * 
	 * Method: toString()
	 * 
	 */
	@Test
	public void testToString() throws Exception {

		String toString = new Normal3(1, 1, 1).toString();

		String r = "Normal3{x=1.0, y=1.0, z=1.0}";

		assertEquals(r, toString);
	}

	/**
	 * 
	 * Method: equals(Object o)
	 * 
	 */
	@Test
	public void testEquals() throws Exception {
		assertTrue(new Normal3(1, 1, 1).equals(new Normal3(1, 1, 1)));
	}

	/**
	 * 
	 * Method: dot(Vector3)
	 * 
	 */

	@Test
	public void testDotV() throws Exception {
		Normal3 n1 = new Normal3(1, 0, 0);
		assertTrue(n1.dot(new Vector3(1, 0, 0)) == 1);
		assertTrue(n1.dot(new Vector3(0, 1, 0)) == 0);
	}

	/**
	 * 
	 * Method: hashCode()
	 * 
	 */
	@Test
	public void testHashCode() throws Exception {

	}

}
