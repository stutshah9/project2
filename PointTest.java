import student.TestCase;

/**
 * Point test class that extends TestCase.
 * Used for extensive mutation testing of all
 * Point methods, ensuring that the behavior
 * of points are as expected
 * 
 * @author Stuti and Jaydon
 * @version 2024-02-08
 */
public class PointTest extends TestCase {

    // private Point declared for testing
    private Point testPoi;

    /**
     * setUp method to initialize our testing Point
     */
    public void setUp() {
        testPoi = new Point("r0", 20, 40);
    }


    /**
     * test getxCoordinate
     */
    public void testGetxCoordinate() {
        // test testRec, zero xcoordinate
        assertEquals(20, testPoi.getxCoordinate());

        // test testPoi1, non-zero xcoordinate
        Point testPoi1 = new Point("r1", 30, 20);
        assertEquals(30, testPoi1.getxCoordinate());
    }


    /**
     * test getyCoordinate
     */
    public void testGetyCoordinate() {
        // test testPoi, zero ycoordinate
        assertEquals(40, testPoi.getyCoordinate());

        // test testPoi1, non-zero ycoordinate
        Point testPoi1 = new Point("r2", 30, 20);
        assertEquals(20, testPoi1.getyCoordinate());
    }


    /**
     * test equals method for null case, same case, different class, different
     * specs
     */
    public void testEquals() {
        // null
        assertFalse(testPoi.equals(null));

        // same
        assertTrue(testPoi.equals(testPoi));

        // different but same
        Point same = new Point("r0", 20, 40);
        assertTrue(same.equals(testPoi));

        // different class type
        assertFalse(testPoi.equals("Hello"));

        // xCoordinate not the same
        Point xCoordinate = new Point("r3", 30, 40);

        // yCoordinate not the same
        Point yCoordinate = new Point("r4", 20, 10);

        assertFalse(xCoordinate.equals(yCoordinate));
    }


    /**
     * test toString
     */
    public void testToString() {
        assertEquals("(r0, 20, 40)", testPoi.toString());
    }


    /**
     * test isInvalid for negative size, maximum size, etc.
     */
    public void testIsInvalid() {
        // point is valid
        assertFalse(testPoi.isInvalid());

        // xCoordinate is negative
        Point xNegative = new Point("r1", -10, 45);
        assertTrue(xNegative.isInvalid());

        // yCoordinate is negative
        Point yNegative = new Point("r2", 10, -45);
        assertTrue(yNegative.isInvalid());

        // xCoordinate > 1024
        Point outOfX = new Point("r3", 1025, 45);
        assertTrue(outOfX.isInvalid());

        // yCoordinate > 1024
        Point outOfY = new Point("r4", 25, 1025);
        assertTrue(outOfY.isInvalid());

        // both negative
        Point bothNeg = new Point("r5", -10, -45);
        assertTrue(bothNeg.isInvalid());

        // both > 1024
        Point outOfBox = new Point("r6", 1025, 1025);
        assertTrue(outOfBox.isInvalid());

        // edge cases
        Point edge1 = new Point("r7", 1024, 1024);
        assertTrue(edge1.isInvalid());

        Point edge2 = new Point("r8", 0, 0);
        assertFalse(edge2.isInvalid());

        Point edge3 = new Point("r9", 1024, 0);
        assertTrue(edge3.isInvalid());

        Point edge4 = new Point("r10", 0, 1024);
        assertTrue(edge4.isInvalid());
    }
}
