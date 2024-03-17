import student.TestCase;

/**
 * Database test class that extends TestCase,
 * Used to test if the object output is correct given
 * a variety of method calls that cover all behavior
 * 
 * @author Stuti and Jaydon
 * @version 2024-02-07
 */
public class DatabaseTest extends TestCase {

    // private Database declared for testing
    private Database testData;

    /**
     * setUp method, initializing testData Database
     */
    public void setUp() {
        testData = new Database();
    }


    /**
     * test insert method behavior/output, such as a bad Point being
     * rejected
     */
    public void testInsert() {

        // creates two rectangles for insertion testing
        Point testRect1 = new Point("r1", 10, 10);
        Point testRect2 = new Point("r2", 564, 564);
        Point testRect3 = new Point("r3", 10, 564);
        Point testRect4 = new Point("r4", 564, 10);
        Point testRect5 = new Point("r5", 60, 60);
        Point testRect6 = new Point("r6", 60, 60);
        Point testPoi7 = new Point("r7", -1, -1);

        // rejected insertion output test
        testData.insert(new KVPair<String, Point>("r7", testPoi7));
        assertEquals(systemOut().getHistory(),
            "Point rejected: (r7, -1, -1)\n");
        systemOut().clearHistory();

        // accepted insertion output test
        testData.insert(new KVPair<String, Point>("r1", testRect1));
        assertEquals(systemOut().getHistory(),
            "Point inserted: (r1, 10, 10)\n");
        systemOut().clearHistory();

        // rejected insertion output test
        testData.insert(new KVPair<String, Point>("r2", testRect2));
        assertEquals(systemOut().getHistory(),
            "Point inserted: (r2, 564, 564)\n");
        systemOut().clearHistory();

        // rejected insertion output test
        testData.insert(new KVPair<String, Point>("r3", testRect3));
        assertEquals(systemOut().getHistory(),
            "Point inserted: (r3, 10, 564)\n");
        systemOut().clearHistory();

        // rejected insertion output test
        testData.insert(new KVPair<String, Point>("r4", testRect4));
        assertEquals(systemOut().getHistory(),
            "Point inserted: (r4, 564, 10)\n");
        systemOut().clearHistory();

        // rejected insertion output test
        testData.insert(new KVPair<String, Point>("r5", testRect5));
        assertEquals(systemOut().getHistory(),
            "Point inserted: (r5, 60, 60)\n");
        systemOut().clearHistory();

        // rejected insertion output test
        testData.insert(new KVPair<String, Point>("r6", testRect6));
        assertEquals(systemOut().getHistory(),
            "Point inserted: (r6, 60, 60)\n");
        systemOut().clearHistory();
    }


    /**
     * test removeByKey method behavior/output such as a Point not being
     * found and subsequently not removed
     */
    public void testRemoveByKey() {

        // creates a Point and inserts it for removal by key testing
        Point testRect1 = new Point("r4", 20, 12);
        testData.insert(new KVPair<String, Point>("r4", testRect1));
        systemOut().clearHistory();

        // accepted removal by key output test
        testData.remove("r4");
        assertEquals(systemOut().getHistory(), "Point removed: (r4, 20, 12)\n");
        systemOut().clearHistory();

        // rejected removal by key output test
        testData.remove("inExistRec");
        assertEquals(systemOut().getHistory(),
            "Point not removed: inExistRec\n");
        systemOut().clearHistory();

        testData.remove("r_r");
        assertEquals(systemOut().getHistory(), "Point not removed: r_r\n");
        systemOut().clearHistory();
    }


    /**
     * test removeByValue method behavior/output, checking for valid Point
     * to remove, as well as its presence in the lsit
     */
    public void testRemoveByValue() {

        // creates a Point and inserts it for removal by value testing
        Point testRect1 = new Point("r1", 120, 117);
        testData.insert(new KVPair<String, Point>("r1", testRect1));

        Point testRect2 = new Point("r2", 120, 117);
        testData.insert(new KVPair<String, Point>("r2", testRect2));
        systemOut().clearHistory();

        // accepted removal by value output test
        testData.remove(120, 117);
        assertEquals(systemOut().getHistory(),
            "Point removed: (r1, 120, 117)\n");
        systemOut().clearHistory();

        // rejected removal by value for non-existent Point test
        testData.remove(2, 0);
        assertEquals(systemOut().getHistory(), "Point not found: (2, 0)\n");
        systemOut().clearHistory();

        // rejected removal by value for invalid Point test
        testData.remove(-1, -1);
        assertEquals(systemOut().getHistory(), "Point rejected: (-1, -1)\n");
        systemOut().clearHistory();

        // rejected removal by value for invalid Point test
        testData.remove(-1, 3);
        assertEquals(systemOut().getHistory(), "Point rejected: (-1, 3)\n");
        systemOut().clearHistory();

        // rejected removal by value for invalid Point test
        testData.remove(10, -3);
        assertEquals(systemOut().getHistory(), "Point rejected: (10, -3)\n");
        systemOut().clearHistory();
    }


    /**
     * test regionSearch method behavior/output for invalid region, empty
     * region, and region with rectangles
     */
    public void testRegionSearch() {

        // Creates two rectangles and inserts them for region search testing
        Point testRect1 = new Point("a", 1000, 10);
        Point testRect2 = new Point("b", 910, 10);
        testData.insert(new KVPair<String, Point>("a", testRect1));
        testData.insert(new KVPair<String, Point>("b", testRect2));
        systemOut().clearHistory();

        // testing regionsearch output when rectangles are in said region
        testData.regionsearch(900, 5, 1000, 1000);
        assertEquals(systemOut().getHistory(),
            "Points intersecting region (900, 5, 1000, 1000):\r\n"
                + "Point found: (a, 1000, 10)\r\n"
                + "Point found: (b, 910, 10)\r\n"
                + "1 quadtree nodes visited\n");
        systemOut().clearHistory();

        // No rectangles intersecting searched region, so no rectangles
        // are output
        testData.regionsearch(0, 500, 20, 1);
        assertEquals(systemOut().getHistory(),
            "Points intersecting region (0, 500, 20, 1):\r\n"
                + "1 quadtree nodes visited\n");
        systemOut().clearHistory();

        // Invalid regions to search, so rejected on that basis
        testData.regionsearch(0, 0, -10, 20);
        assertEquals(systemOut().getHistory(),
            "Rectangle rejected: (0, 0, -10, 20)\n");
        systemOut().clearHistory();

        testData.regionsearch(0, 0, 10, -20);
        assertEquals(systemOut().getHistory(),
            "Rectangle rejected: (0, 0, 10, -20)\n");

        Point regionSearch = new Point("r1", 10, 10);
        testData.insert(new KVPair<String, Point>("r1", regionSearch));
        systemOut().clearHistory();

        testData.regionsearch(11, 11, 0, 0);
        assertEquals(systemOut().getHistory(),
            "Rectangle rejected: (11, 11, 0, 0)\n");
        systemOut().clearHistory();
    }


    /**
     * test search method behavior/output, finding all rectangles with one name
     * and finding no rectangles if not found
     */
    public void testSearch() {

        // Creates and inserts 2 rectangles for intersection testing
        Point testRect1 = new Point("a", 1, 0);
        Point testRect2 = new Point("a", 10, 4);
        testData.insert(new KVPair<String, Point>("a", testRect2));
        testData.insert(new KVPair<String, Point>("a", testRect1));
        systemOut().clearHistory();

        // Test finding all points with the name a
        testData.search("a");
        assertEquals(systemOut().getHistory(), "Found (a, 1, 0)\r\n"
            + "Found (a, 10, 4)\n");
        systemOut().clearHistory();

        // Test searching for a Point not in the list
        testData.search("b");
        assertEquals(systemOut().getHistory(), "Point not found: b\n");
        systemOut().clearHistory();
    }


    /**
     * test the duplicates method, finding all the duplicate points that exist
     */
    public void testDuplicates() {
        Point testRect1 = new Point("a", 1, 0);
        Point testRect2 = new Point("b", 1, 0);
        testData.insert(new KVPair<String, Point>("a", testRect2));
        testData.insert(new KVPair<String, Point>("b", testRect1));
        systemOut().clearHistory();

        testData.duplicates();
        assertEquals(systemOut().getHistory(), "Duplicate points:\r\n"
            + "(1, 0)\r\n");

    }

// public void testAll() {
// Point point = new Point("r1", 10, 10);
// Point point1 = new Point("r2", 15, 15);
// Point point2 = new Point("r3", 7, 7);
// Point point3 = new Point("r4", 20, 25);
// Point point4 = new Point("r4", 20, 12);
// Point point5 = new Point("r5", 6, 7);
// Point point6 = new Point("r12", 108, 136);
// Point point7 = new Point("r14", 120, 117);
// Point point8 = new Point("r15", 120, 117);
// testData.insert(new KVPair<String, Point>("r1", point));
// testData.regionsearch(11, 11, 0, 0);
// testData.insert(new KVPair<String, Point>("r2", point1));
// testData.insert(new KVPair<String, Point>("r3", point2));
// testData.insert(new KVPair<String, Point>("r4", point3));
// testData.insert(new KVPair<String, Point>("r4", point4));
// testData.insert(new KVPair<String, Point>("r5", point5));
// testData.insert(new KVPair<String, Point>("r12", point6));
// testData.insert(new KVPair<String, Point>("r14", point7));
// testData.insert(new KVPair<String, Point>("r15", point8));
//
// testData.dump();
// testData.remove("r_r");
// testData.remove("inExistRec");
// testData.search("r4");
// testData.remove("r4");
// testData.dump();
// testData.remove("r5");
// testData.dump();
// testData.search("r14");
// testData.search("r11");
// testData.search("R11");
// testData.remove("r10");
// testData.remove("r11");
// testData.remove("r12");
// testData.remove("r13");
// testData.dump();
// testData.remove("120, 117");
// testData.dump();
// testData.remove("100, 1000");
// testData.remove("r14");
// testData.regionsearch(-5, -5, 20, 20);
// Point big = new Point("big", 10, 10);
// testData.insert(new KVPair<String, Point>("big", big));
// testData.duplicates();
// testData.search("r2");
// testData.search("r4");
// testData.remove(20, 25);
// testData.regionsearch(10, 10, 100, 100);
// testData.dump();
//
// String string1 = systemOut().getHistory();
// assertFuzzyEquals(string1, "Point inserted: (r1, 10, 10)\r\n"
// + "Rectangle rejected: (11, 11, 0, 0)\r\n"
// + "Point inserted: (r2, 15, 15)\r\n"
// + "Point inserted: (r3, 7, 7)\r\n"
// + "Point inserted: (r4, 20, 25)\r\n"
// + "Point inserted: (r4, 20, 12)\r\n"
// + "Point inserted: (r5, 6, 7)\r\n"
// + "Point inserted: (r12, 108, 136)\r\n"
// + "Point inserted: (r14, 120, 117)\r\n"
// + "Point inserted: (r15, 120, 117)\r\n" + "SkipList dump:\r\n"
// + "Node has depth 3, Value (null)\r\n"
// + "Node has depth 2, Value (r1, 10, 10)\r\n"
// + "Node has depth 3, Value (r12, 108, 136)\r\n"
// + "Node has depth 3, Value (r14, 120, 117)\r\n"
// + "Node has depth 1, Value (r15, 120, 117)\r\n"
// + "Node has depth 2, Value (r2, 15, 15)\r\n"
// + "Node has depth 2, Value (r3, 7, 7)\r\n"
// + "Node has depth 2, Value (r4, 20, 12)\r\n"
// + "Node has depth 3, Value (r4, 20, 25)\r\n"
// + "Node has depth 2, Value (r5, 6, 7)\r\n"
// + "SkipList size is: 9\r\n" + "QuadTree dump:\r\n"
// + "Node at 0, 0, 1024: Internal\r\n"
// + "Node at 0, 0, 512: Internal\r\n"
// + "Node at 0, 0, 256: Internal\r\n"
// + "Node at 0, 0, 128: Internal\r\n"
// + "Node at 0, 0, 64: Internal\r\n"
// + "Node at 0, 0, 32: Internal\r\n"
// + "Node at 0, 0, 16: Internal\r\n" + "Node at 0, 0, 8:\r\n"
// + "(r3, 7, 7)\r\n" + "(r5, 6, 7)\r\n" + "Node at 8, 0, 8: Empty\r\n"
// + "Node at 0, 8, 8: Empty\r\n" + "Node at 8, 8, 8:\r\n"
// + "(r1, 10, 10)\r\n" + "(r2, 15, 15)\r\n" + "Node at 16, 0, 16:\r\n"
// + "(r4, 20, 12)\r\n" + "Node at 0, 16, 16: Empty\r\n"
// + "Node at 16, 16, 16:\r\n" + "(r4, 20, 25)\r\n"
// + "Node at 32, 0, 32: Empty\r\n" + "Node at 0, 32, 32: Empty\r\n"
// + "Node at 32, 32, 32: Empty\r\n" + "Node at 64, 0, 64: Empty\r\n"
// + "Node at 0, 64, 64: Empty\r\n" + "Node at 64, 64, 64:\r\n"
// + "(r15, 120, 117)\r\n" + "(r14, 120, 117)\r\n"
// + "Node at 128, 0, 128: Empty\r\n" + "Node at 0, 128, 128:\r\n"
// + "(r12, 108, 136)\r\n" + "Node at 128, 128, 128: Empty\r\n"
// + "Node at 256, 0, 256: Empty\r\n"
// + "Node at 0, 256, 256: Empty\r\n"
// + "Node at 256, 256, 256: Empty\r\n"
// + "Node at 512, 0, 512: Empty\r\n"
// + "Node at 0, 512, 512: Empty\r\n"
// + "Node at 512, 512, 512: Empty\r\n"
// + "29 quadtree nodes printed\r\n" + "Point not removed: r_r\r\n"
// + "Point not removed: inExistRec\r\n" + "Found (r4, 20, 12)\r\n"
// + "Found (r4, 20, 25)\r\n" + "Point removed: (r4, 20, 12)\r\n"
// + "SkipList dump:\r\n" + "Node has depth 3, Value (null)\r\n"
// + "Node has depth 2, Value (r1, 10, 10)\r\n"
// + "Node has depth 3, Value (r12, 108, 136)\r\n"
// + "Node has depth 3, Value (r14, 120, 117)\r\n"
// + "Node has depth 1, Value (r15, 120, 117)\r\n"
// + "Node has depth 2, Value (r2, 15, 15)\r\n"
// + "Node has depth 2, Value (r3, 7, 7)\r\n"
// + "Node has depth 3, Value (r4, 20, 25)\r\n"
// + "Node has depth 2, Value (r5, 6, 7)\r\n"
// + "SkipList size is: 8\r\n" + "QuadTree dump:\r\n"
// + "Node at 0, 0, 1024: Internal\r\n"
// + "Node at 0, 0, 512: Internal\r\n"
// + "Node at 0, 0, 256: Internal\r\n"
// + "Node at 0, 0, 128: Internal\r\n"
// + "Node at 0, 0, 64: Internal\r\n"
// + "Node at 0, 0, 32: Internal\r\n"
// + "Node at 0, 0, 16: Internal\r\n" + "Node at 0, 0, 8:\r\n"
// + "(r3, 7, 7)\r\n" + "(r5, 6, 7)\r\n" + "Node at 8, 0, 8: Empty\r\n"
// + "Node at 0, 8, 8: Empty\r\n" + "Node at 8, 8, 8:\r\n"
// + "(r1, 10, 10)\r\n" + "(r2, 15, 15)\r\n"
// + "Node at 16, 0, 16: Empty\r\n" + "Node at 0, 16, 16: Empty\r\n"
// + "Node at 16, 16, 16:\r\n" + "(r4, 20, 25)\r\n"
// + "Node at 32, 0, 32: Empty\r\n" + "Node at 0, 32, 32: Empty\r\n"
// + "Node at 32, 32, 32: Empty\r\n" + "Node at 64, 0, 64: Empty\r\n"
// + "Node at 0, 64, 64: Empty\r\n" + "Node at 64, 64, 64:\r\n"
// + "(r15, 120, 117)\r\n" + "(r14, 120, 117)\r\n"
// + "Node at 128, 0, 128: Empty\r\n" + "Node at 0, 128, 128:\r\n"
// + "(r12, 108, 136)\r\n" + "Node at 128, 128, 128: Empty\r\n"
// + "Node at 256, 0, 256: Empty\r\n"
// + "Node at 0, 256, 256: Empty\r\n"
// + "Node at 256, 256, 256: Empty\r\n"
// + "Node at 512, 0, 512: Empty\r\n"
// + "Node at 0, 512, 512: Empty\r\n"
// + "Node at 512, 512, 512: Empty\r\n"
// + "29 quadtree nodes printed\r\n" + "Point removed: (r5, 6, 7)\r\n"
// + "SkipList dump:\r\n" + "Node has depth 3, Value (null)\r\n"
// + "Node has depth 2, Value (r1, 10, 10)\r\n"
// + "Node has depth 3, Value (r12, 108, 136)\r\n"
// + "Node has depth 3, Value (r14, 120, 117)\r\n"
// + "Node has depth 1, Value (r15, 120, 117)\r\n"
// + "Node has depth 2, Value (r2, 15, 15)\r\n"
// + "Node has depth 2, Value (r3, 7, 7)\r\n"
// + "Node has depth 3, Value (r4, 20, 25)\r\n"
// + "SkipList size is: 7\r\n" + "QuadTree dump:\r\n"
// + "Node at 0, 0, 1024: Internal\r\n"
// + "Node at 0, 0, 512: Internal\r\n"
// + "Node at 0, 0, 256: Internal\r\n"
// + "Node at 0, 0, 128: Internal\r\n"
// + "Node at 0, 0, 64: Internal\r\n"
// + "Node at 0, 0, 32: Internal\r\n" + "Node at 0, 0, 16:\r\n"
// + "(r2, 15, 15)\r\n" + "(r1, 10, 10)\r\n" + "(r3, 7, 7)\r\n"
// + "Node at 16, 0, 16: Empty\r\n" + "Node at 0, 16, 16: Empty\r\n"
// + "Node at 16, 16, 16:\r\n" + "(r4, 20, 25)\r\n"
// + "Node at 32, 0, 32: Empty\r\n" + "Node at 0, 32, 32: Empty\r\n"
// + "Node at 32, 32, 32: Empty\r\n" + "Node at 64, 0, 64: Empty\r\n"
// + "Node at 0, 64, 64: Empty\r\n" + "Node at 64, 64, 64:\r\n"
// + "(r15, 120, 117)\r\n" + "(r14, 120, 117)\r\n"
// + "Node at 128, 0, 128: Empty\r\n" + "Node at 0, 128, 128:\r\n"
// + "(r12, 108, 136)\r\n" + "Node at 128, 128, 128: Empty\r\n"
// + "Node at 256, 0, 256: Empty\r\n"
// + "Node at 0, 256, 256: Empty\r\n"
// + "Node at 256, 256, 256: Empty\r\n"
// + "Node at 512, 0, 512: Empty\r\n"
// + "Node at 0, 512, 512: Empty\r\n"
// + "Node at 512, 512, 512: Empty\r\n"
// + "25 quadtree nodes printed\r\n" + "Point not removed: r5\r\n"
// + "SkipList dump:\r\n" + "Node has depth 3, Value (null)\r\n"
// + "Node has depth 2, Value (r1, 10, 10)\r\n"
// + "Node has depth 3, Value (r12, 108, 136)\r\n"
// + "Node has depth 3, Value (r14, 120, 117)\r\n"
// + "Node has depth 1, Value (r15, 120, 117)\r\n"
// + "Node has depth 2, Value (r2, 15, 15)\r\n"
// + "Node has depth 2, Value (r3, 7, 7)\r\n"
// + "Node has depth 3, Value (r4, 20, 25)\r\n"
// + "SkipList size is: 7\r\n" + "QuadTree dump:\r\n"
// + "Node at 0, 0, 1024: Internal\r\n"
// + "Node at 0, 0, 512: Internal\r\n"
// + "Node at 0, 0, 256: Internal\r\n"
// + "Node at 0, 0, 128: Internal\r\n"
// + "Node at 0, 0, 64: Internal\r\n"
// + "Node at 0, 0, 32: Internal\r\n" + "Node at 0, 0, 16:\r\n"
// + "(r2, 15, 15)\r\n" + "(r1, 10, 10)\r\n" + "(r3, 7, 7)\r\n"
// + "Node at 16, 0, 16: Empty\r\n" + "Node at 0, 16, 16: Empty\r\n"
// + "Node at 16, 16, 16:\r\n" + "(r4, 20, 25)\r\n"
// + "Node at 32, 0, 32: Empty\r\n" + "Node at 0, 32, 32: Empty\r\n"
// + "Node at 32, 32, 32: Empty\r\n" + "Node at 64, 0, 64: Empty\r\n"
// + "Node at 0, 64, 64: Empty\r\n" + "Node at 64, 64, 64:\r\n"
// + "(r15, 120, 117)\r\n" + "(r14, 120, 117)\r\n"
// + "Node at 128, 0, 128: Empty\r\n" + "Node at 0, 128, 128:\r\n"
// + "(r12, 108, 136)\r\n" + "Node at 128, 128, 128: Empty\r\n"
// + "Node at 256, 0, 256: Empty\r\n"
// + "Node at 0, 256, 256: Empty\r\n"
// + "Node at 256, 256, 256: Empty\r\n"
// + "Node at 512, 0, 512: Empty\r\n"
// + "Node at 0, 512, 512: Empty\r\n"
// + "Node at 512, 512, 512: Empty\r\n"
// + "25 quadtree nodes printed\r\n" + "Found (r14, 120, 117)\r\n"
// + "Point not found: r11\r\n" + "Point not found: R11\r\n"
// + "Point not removed: r10\r\n" + "Point not removed: r11\r\n"
// + "Point removed: (r12, 108, 136)\r\n"
// + "Point not removed: r13\r\n" + "SkipList dump:\r\n"
// + "Node has depth 3, Value (null)\r\n"
// + "Node has depth 2, Value (r1, 10, 10)\r\n"
// + "Node has depth 3, Value (r14, 120, 117)\r\n"
// + "Node has depth 1, Value (r15, 120, 117)\r\n"
// + "Node has depth 2, Value (r2, 15, 15)\r\n"
// + "Node has depth 2, Value (r3, 7, 7)\r\n"
// + "Node has depth 3, Value (r4, 20, 25)\r\n"
// + "SkipList size is: 6\r\n" + "QuadTree dump:\r\n"
// + "Node at 0, 0, 1024: Internal\r\n"
// + "Node at 0, 0, 512: Internal\r\n"
// + "Node at 0, 0, 256: Internal\r\n"
// + "Node at 0, 0, 128: Internal\r\n"
// + "Node at 0, 0, 64: Internal\r\n"
// + "Node at 0, 0, 32: Internal\r\n" + "Node at 0, 0, 16:\r\n"
// + "(r2, 15, 15)\r\n" + "(r1, 10, 10)\r\n" + "(r3, 7, 7)\r\n"
// + "Node at 16, 0, 16: Empty\r\n" + "Node at 0, 16, 16: Empty\r\n"
// + "Node at 16, 16, 16:\r\n" + "(r4, 20, 25)\r\n"
// + "Node at 32, 0, 32: Empty\r\n" + "Node at 0, 32, 32: Empty\r\n"
// + "Node at 32, 32, 32: Empty\r\n" + "Node at 64, 0, 64: Empty\r\n"
// + "Node at 0, 64, 64: Empty\r\n" + "Node at 64, 64, 64:\r\n"
// + "(r15, 120, 117)\r\n" + "(r14, 120, 117)\r\n"
// + "Node at 128, 0, 128: Empty\r\n"
// + "Node at 0, 128, 128: Empty\r\n"
// + "Node at 128, 128, 128: Empty\r\n"
// + "Node at 256, 0, 256: Empty\r\n"
// + "Node at 0, 256, 256: Empty\r\n"
// + "Node at 256, 256, 256: Empty\r\n"
// + "Node at 512, 0, 512: Empty\r\n"
// + "Node at 0, 512, 512: Empty\r\n"
// + "Node at 512, 512, 512: Empty\r\n"
// + "25 quadtree nodes printed\r\n"
// + "Point removed: (r15, 120, 117)\r\n" + "SkipList dump:\r\n"
// + "Node has depth 3, Value (null)\r\n"
// + "Node has depth 2, Value (r1, 10, 10)\r\n"
// + "Node has depth 3, Value (r14, 120, 117)\r\n"
// + "Node has depth 2, Value (r2, 15, 15)\r\n"
// + "Node has depth 2, Value (r3, 7, 7)\r\n"
// + "Node has depth 3, Value (r4, 20, 25)\r\n"
// + "SkipList size is: 5\r\n" + "QuadTree dump:\r\n"
// + "Node at 0, 0, 1024: Internal\r\n"
// + "Node at 0, 0, 512: Internal\r\n"
// + "Node at 0, 0, 256: Internal\r\n"
// + "Node at 0, 0, 128: Internal\r\n"
// + "Node at 0, 0, 64: Internal\r\n"
// + "Node at 0, 0, 32: Internal\r\n" + "Node at 0, 0, 16:\r\n"
// + "(r2, 15, 15)\r\n" + "(r1, 10, 10)\r\n" + "(r3, 7, 7)\r\n"
// + "Node at 16, 0, 16: Empty\r\n" + "Node at 0, 16, 16: Empty\r\n"
// + "Node at 16, 16, 16:\r\n" + "(r4, 20, 25)\r\n"
// + "Node at 32, 0, 32: Empty\r\n" + "Node at 0, 32, 32: Empty\r\n"
// + "Node at 32, 32, 32: Empty\r\n" + "Node at 64, 0, 64: Empty\r\n"
// + "Node at 0, 64, 64: Empty\r\n" + "Node at 64, 64, 64:\r\n"
// + "(r14, 120, 117)\r\n" + "Node at 128, 0, 128: Empty\r\n"
// + "Node at 0, 128, 128: Empty\r\n"
// + "Node at 128, 128, 128: Empty\r\n"
// + "Node at 256, 0, 256: Empty\r\n"
// + "Node at 0, 256, 256: Empty\r\n"
// + "Node at 256, 256, 256: Empty\r\n"
// + "Node at 512, 0, 512: Empty\r\n"
// + "Node at 0, 512, 512: Empty\r\n"
// + "Node at 512, 512, 512: Empty\r\n"
// + "25 quadtree nodes printed\r\n"
// + "Point not found: (100, 1000)\r\n"
// + "Point removed: (r14, 120, 117)\r\n"
// + "Points intersecting region (-5, -5, 20, 20):\r\n"
// + "Point found: (r1, 10, 10)\r\n" + "Point found: (r3, 7, 7)\r\n"
// + "7 quadtree nodes visited\r\n"
// + "Point inserted: (big, 10, 10)\r\n" + "Duplicate points:\r\n"
// + "(10, 10)\r\n" + "Found (r2, 15, 15)\r\n"
// + "Found (r4, 20, 25)\r\n" + "Point removed: (r4, 20, 25)\r\n"
// + "Points intersecting region (10, 10, 100, 100):\r\n"
// + "Point found: (r1, 10, 10)\r\n" + "Point found: (r2, 15, 15)\r\n"
// + "Point found: (big, 10, 10)\r\n" + "17 quadtree nodes visited\r\n"
// + "SkipList dump:\r\n" + "Node has depth 3, Value (null)\r\n"
// + "Node has depth 1, Value (big, 10, 10)\r\n"
// + "Node has depth 2, Value (r1, 10, 10)\r\n"
// + "Node has depth 2, Value (r2, 15, 15)\r\n"
// + "Node has depth 2, Value (r3, 7, 7)\r\n"
// + "SkipList size is: 4\r\n" + "QuadTree dump:\r\n"
// + "Node at 0, 0, 1024: Internal\r\n"
// + "Node at 0, 0, 512: Internal\r\n"
// + "Node at 0, 0, 256: Internal\r\n"
// + "Node at 0, 0, 128: Internal\r\n"
// + "Node at 0, 0, 64: Internal\r\n"
// + "Node at 0, 0, 32: Internal\r\n"
// + "Node at 0, 0, 16: Internal\r\n" + "Node at 0, 0, 8:\r\n"
// + "(r3, 7, 7)\r\n" + "Node at 8, 0, 8: Empty\r\n"
// + "Node at 0, 8, 8: Empty\r\n" + "Node at 8, 8, 8:\r\n"
// + "(r1, 10, 10)\r\n" + "(r2, 15, 15)\r\n" + "(big, 10, 10)\r\n"
// + "Node at 16, 0, 16: Empty\r\n" + "Node at 0, 16, 16: Empty\r\n"
// + "Node at 16, 16, 16: Empty\r\n" + "Node at 32, 0, 32: Empty\r\n"
// + "Node at 0, 32, 32: Empty\r\n" + "Node at 32, 32, 32: Empty\r\n"
// + "Node at 64, 0, 64: Empty\r\n" + "Node at 0, 64, 64: Empty\r\n"
// + "Node at 64, 64, 64: Empty\r\n" + "Node at 128, 0, 128: Empty\r\n"
// + "Node at 0, 128, 128: Empty\r\n"
// + "Node at 128, 128, 128: Empty\r\n"
// + "Node at 256, 0, 256: Empty\r\n"
// + "Node at 0, 256, 256: Empty\r\n"
// + "Node at 256, 256, 256: Empty\r\n"
// + "Node at 512, 0, 512: Empty\r\n"
// + "Node at 0, 512, 512: Empty\r\n"
// + "Node at 512, 512, 512: Empty\r\n"
// + "29 quadtree nodes printed\r\n");
// }
}
