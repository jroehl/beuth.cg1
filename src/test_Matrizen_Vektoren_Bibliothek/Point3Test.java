package test_Matrizen_Vektoren_Bibliothek;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Matrizen_Vektoren_Bibliothek.Mat3x3;
import Matrizen_Vektoren_Bibliothek.Point3;
import Matrizen_Vektoren_Bibliothek.Vector3;

/**
 * Point3 Tester.
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
public class Point3Test extends TestCase {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * 
	 * Method: sub(Point3 p)
	 * 
	 */
	@Test
	public void testSubP() throws Exception {

		Point3 p1 = new Point3(1, 1, 1);
		Point3 p2 = new Point3(2, 2, 0);

		Vector3 r = new Vector3(-1, -1, 1);

		Vector3 subP = p1.sub(p2);

		assertTrue(subP.equals(r));

	}

	/**
	 * 
	 * Method: sub(Vector3 v)
	 * 
	 */
	@Test
	public void testSubV() throws Exception {

		Point3 p1 = new Point3(1, 1, 1);
		Vector3 v1 = new Vector3(4, 3, 2);

		Point3 r = new Point3(-3, -2, -1);

		Point3 subV = p1.sub(v1);

		assertTrue(subV.equals(r));

	}

	/**
	 * 
	 * Method: add(Vector3 v)
	 * 
	 */
	@Test
	public void testAdd() throws Exception {

		Point3 p1 = new Point3(1, 1, 1);
		Vector3 v1 = new Vector3(4, 3, 2);

		Point3 r = new Point3(5, 4, 3);

		Point3 add = p1.add(v1);

		assertTrue(add.equals(r));

	}

	/**
	 * 
	 * Method: equals(Object o)
	 * 
	 */
	@Test
	public void testEquals() throws Exception {

		// assertTrue(new Point3(1,1,1).equals(new Vector3(1,1,1)));
		assertTrue(new Point3(1, 1, 1).equals(new Point3(1, 1, 1)));

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

		String toString = new Point3(1, 1, 1).toString();

		String r = "Point3{x=1.0, y=1.0, z=1.0}";

		assertEquals(r, toString);
	}

	/**
	 * Method: mul()
	 */
	@Test
	public void testMul() {
		Mat3x3 m1 = new Mat3x3(1, 0, 0, 0, 1, 0, 0, 0, 1);
		Point3 p1 = new Point3(3, 2, 1);

		System.out.println(m1.mul(p1).x + " " + m1.mul(p1).y + " " + m1.mul(p1).z);

		assertTrue(new Point3(3, 2, 1).equals(m1.mul(p1)));
	}
}