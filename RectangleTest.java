import student.TestCase;

/**
 * Rectangle test class that extends TestCase.
 * Used for extensive mutation testing of all
 * Rectangle methods, ensuring that the behavior
 * of rectangles are as expected
 * 
 * @author Stuti and Jaydon
 * @version 2024-02-08
 */
public class RectangleTest extends TestCase {

    // private Rectangle declared for testing
    private Rectangle testRec;

    /**
     * setUp method to initialize our testing Rectangle
     */
    public void setUp() {
        testRec = new Rectangle(0, 0, 20, 40);
    }


    /**
     * test getxCoordinate
     */
    public void testGetxCoordinate() {
        // test testRec, zero xcoordinate
        assertEquals(0, testRec.getxCoordinate());

        // test testRec1, non-zero xcoordinate
        Rectangle testRec1 = new Rectangle(20, 20, 20, 40);
        assertEquals(20, testRec1.getxCoordinate());
    }


    /**
     * test getyCoordinate
     */
    public void testGetyCoordinate() {
        // test testRec, zero ycoordinate
        assertEquals(0, testRec.getyCoordinate());

        // test testRec1, non-zero ycoordinate
        Rectangle testRec1 = new Rectangle(20, 20, 20, 40);
        assertEquals(20, testRec1.getyCoordinate());
    }


    /**
     * test getWidth
     */
    public void testGetWidth() {
        // test testRec
        assertEquals(20, testRec.getWidth());
    }


    /**
     * test getHeight
     */
    public void testGetHeigth() {
        // test testRec
        assertEquals(40, testRec.getHeight());
    }


    /**
     * test intersect, and the variety of cases with it, such as inner, edges,
     * and normal cases. Testing for many false intersections as well
     */
    public void testIntersect() {
        // same Rectangle
        assertTrue(testRec.intersect(testRec));

        // inside the other Rectangle
        Rectangle inside = new Rectangle(10, 10, 20, 5);
        assertTrue(testRec.intersect(inside));

        // sets up rectangles for basic interseciton testing
        Rectangle test = new Rectangle(0, 0, 511, 512);
        Rectangle test1 = new Rectangle(512, 512, 512, 512);
        Rectangle test2 = new Rectangle(400, 600, 112, 200);
        Rectangle test3 = new Rectangle(300, 700, 400, 100);
        Rectangle test4 = new Rectangle(512, 312, 200, 300);

        // Comprehensive testing for a variety of intersection
        // cases, successes or failures, such as x1 > x2 + width,
        // y1 > y2 + height, etc.
        assertFalse(test.intersect(test1));
        assertFalse(test.intersect(test2));
        assertFalse(test.intersect(test3));
        assertFalse(test.intersect(test4));
        assertFalse(test1.intersect(test2));
        assertFalse(test2.intersect(test4));
        assertFalse(test3.intersect(test4));
        assertTrue(test1.intersect(test3));
        assertTrue(test1.intersect(test4));
        assertTrue(test2.intersect(test3));

        // touch bottom edge
        Rectangle bottomEdge = new Rectangle(0, 40, 20, 30);
        assertFalse(testRec.intersect(bottomEdge));

        // touch right side edge
        Rectangle rightSideEdge = new Rectangle(20, 0, 20, 40);
        assertFalse(testRec.intersect(rightSideEdge));

        // touch corner
        Rectangle corner = new Rectangle(20, 40, 20, 40);
        assertFalse(testRec.intersect(corner));

        // no intersection
        Rectangle noIntersection = new Rectangle(30, 45, 20, 20);
        assertFalse(testRec.intersect(noIntersection));

    }


    /**
     * test equals method for null case, same case, different class, different
     * specs
     */
    public void testEquals() {
        // null
        assertFalse(testRec.equals(null));

        // same
        assertTrue(testRec.equals(testRec));

        // different but same
        Rectangle same = new Rectangle(0, 0, 20, 40);
        assertTrue(same.equals(testRec));

        // different class type
        assertFalse(testRec.equals("Hello"));

        // different Rectangles over a variety of metrics
        Rectangle differentHeight = new Rectangle(0, 0, 20, 50);
        assertFalse(testRec.equals(differentHeight));

        Rectangle differentWidth = new Rectangle(0, 0, 30, 40);
        assertFalse(testRec.equals(differentWidth));

        Rectangle differenty = new Rectangle(0, 20, 20, 40);
        assertFalse(testRec.equals(differenty));

        Rectangle differentx = new Rectangle(20, 0, 20, 40);
        assertFalse(testRec.equals(differentx));

        Rectangle differentEveryhting = new Rectangle(20, 20, 30, 50);
        assertFalse(testRec.equals(differentEveryhting));
    }


    /**
     * test toString
     */
    public void testToString() {
        assertEquals("0, 0, 20, 40", testRec.toString());
    }


    /**
     * test isInvalid for negative size, maximum size, etc.
     */
    public void testIsInvalid() {
        // valid Rectangles
        assertFalse(testRec.isInvalid());

        Rectangle test7 = new Rectangle(10, 10, 10, 10);
        assertFalse(test7.isInvalid());

        Rectangle valid = new Rectangle(1, 4, 20, 30);
        assertFalse(valid.isInvalid());

        // valid edge case
        Rectangle edgeCase = new Rectangle(0, 0, 20, 30);
        assertFalse(edgeCase.isInvalid());

        // width negative
        Rectangle width = new Rectangle(0, 0, -20, 40);
        assertTrue(width.isInvalid());

        // height negative
        Rectangle height = new Rectangle(0, 0, 20, -40);
        assertTrue(height.isInvalid());

        // width greater than 1024 cases
        Rectangle width1024 = new Rectangle(0, 0, 1025, 40);
        assertTrue(width1024.isInvalid());

        Rectangle width10242 = new Rectangle(3, 12, 1025, 40);
        assertTrue(width10242.isInvalid());

        Rectangle width10244 = new Rectangle(1001, 0, 24, 10);
        assertTrue(width10244.isInvalid());

        // width equal 1024
        Rectangle width10243 = new Rectangle(1000, 0, 24, 10);
        assertFalse(width10243.isInvalid());

        // width 0
        Rectangle width0 = new Rectangle(1001, 1000, 0, 10);
        assertTrue(width0.isInvalid());

        // height greater than 1024 cases
        Rectangle height1024 = new Rectangle(0, 0, 20, 1025);
        assertTrue(height1024.isInvalid());

        Rectangle height10242 = new Rectangle(12, 3, 20, 1025);
        assertTrue(height10242.isInvalid());

        Rectangle height10243 = new Rectangle(0, 1000, 10, 1014);
        assertTrue(height10243.isInvalid());

        // height equal 0
        Rectangle height0 = new Rectangle(1001, 1000, 10, 0);
        assertTrue(height0.isInvalid());

        // width and height 0
        Rectangle widthheight0 = new Rectangle(1001, 1000, 0, 0);
        assertTrue(widthheight0.isInvalid());

        // too big
        Rectangle tooBig = new Rectangle(12, 12, 1025, 1025);
        assertTrue(tooBig.isInvalid());

        // too small
        Rectangle minRectangle = new Rectangle(0, 0, 0, 0);
        assertTrue(minRectangle.isInvalid());

        // max Rectangle
        Rectangle maxValidRectangle = new Rectangle(0, 0, 1024, 1024);
        assertFalse(maxValidRectangle.isInvalid());

        // negative coordinates
        Rectangle negativeCoordinates = new Rectangle(-5, -10, 15, 25);
        assertTrue(negativeCoordinates.isInvalid());

        // all cases
        Rectangle allCases = new Rectangle(-1, -1, -10, -15);
        assertTrue(allCases.isInvalid());

        // specific validity test targetting certain failures
        Rectangle test = new Rectangle(-1, 0, 10, 10);
        assertTrue(test.isInvalid());
        Rectangle test1 = new Rectangle(0, -1, 10, 10);
        assertTrue(test1.isInvalid());
        Rectangle test2 = new Rectangle(10, 10, -1, 10);
        assertTrue(test2.isInvalid());
        Rectangle test3 = new Rectangle(10, 10, 10, 0);
        assertTrue(test3.isInvalid());
        Rectangle test4 = new Rectangle(1024, 10, 10, 10);
        assertTrue(test4.isInvalid());
        Rectangle test5 = new Rectangle(1, 1024, 10, 100);
        assertTrue(test5.isInvalid());
        Rectangle test6 = new Rectangle(10, 10, 0, 10);
        assertTrue(test6.isInvalid());
    }
}
