import student.TestCase;

/**
 * CommandProcessor test class that extends TestCase
 * Essentially tests that given a variety of input
 * commands, the correct method is called internally
 * and has the correct outputs for said command
 * 
 * @author Stuti and Jaydon
 * @version 2024-02-07
 */
public class CommandProcessorTest extends TestCase {

    // private CommandProcessor declared for testing
    private CommandProcessor testCommandProcessor;

    /**
     * setUp method, initializes testCommandProcessor
     */
    public void setUp() {
        testCommandProcessor = new CommandProcessor();
    }


    /**
     * test "insert" and "remove" output,
     * insert: when rectangles are invalid or valid
     * removeByKey: when rectangles are found, not found
     * removeByValue: when rectangles are found, not found
     */
    public void testInsertandRemove() {

        // Two successful insertions of points, checking correct
        // output and then clearing the system output
        String inputLine = "insert r14 120 117";
        testCommandProcessor.processor(inputLine);
        String correctOutput = "Point inserted: (r14, 120, 117)\n";
        String systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

        inputLine = "insert r2 15 15";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Point inserted: (r2, 15, 15)\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

// LOOK AT THIS CASE!!!
// // A rejected insertion of a point, checking correct
// // output and then clearing the system output
// inputLine = "insert r10 -1 -1";
// testCommandProcessor.processor(inputLine);
// correctOutput = "Point rejected: (r10, -1, -1)\n";
// systemOutput = systemOut().getHistory();
// assertEquals(correctOutput, systemOutput);
// systemOut().clearHistory();

        // A rejected removal of a rectangle by name (doesn't exist),
        // checking correct output and then clearing the system output
        inputLine = "remove r10";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Point not removed: r10\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

        // A removal of a rectangle by name, checking correct
        // output and then clearing the system output
        inputLine = "remove r14";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Point removed: (r14, 120, 117)\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

        // A rejected removal of a rectangle by name (previously removed),
        // checking correct output and then clearing the system output
        inputLine = "remove r14";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Point not removed: r14\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

        // A removal of a rectangle by specs, checking correct
        // output and then clearing the system output
        inputLine = "remove 15 15";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Point removed: (r2, 15, 15)\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();
    }


    /**
     * test "regionsearch" output, for invalid regions as well as valid regions
     * with rectangles
     */
    public void testRegionSearch() {

        // Inserts three rectangles for testing region search
        String inputLine = "insert r14 120 117 93 706";
        testCommandProcessor.processor(inputLine);
        inputLine = "insert r1 10 10";
        testCommandProcessor.processor(inputLine);
        inputLine = "insert r3 7 7";
        testCommandProcessor.processor(inputLine);
        systemOut().clearHistory();

        // Regionsearch rejected as the region is not possible (negative width
        // and height), checking correct behavior/output
        inputLine = "regionsearch 20 20 -5 -5";
        testCommandProcessor.processor(inputLine);
        String correctOutput = "Rectangle rejected: (20, 20, -5, -5)\n";
        String systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

        // Regionsearch for two of the three rectangles present, checking
        // correct behavior/output
        inputLine = "regionsearch -5 -5 20 20";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Points intersecting region (-5, -5, 20, 20):\n"
            + "Point found: (r1, 10, 10)\n" + "Point found: (r3, 7, 7)\n"
            + "1 quadtree nodes visited\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();
    }


    /**
     * test "search" command output for when a rectangle is found, as well as
     * when it isn't
     */
    public void testSearch() {

        // Inserts a rectangle for testing search, and clears the output
        String inputLine = "insert r2 15 15";
        testCommandProcessor.processor(inputLine);
        systemOut().clearHistory();

        // Searchs for a rectangle in the list, and then checks for correct
        // output when it is found
        inputLine = "search r2";
        testCommandProcessor.processor(inputLine);
        String correctOutput = "Found (r2, 15, 15)\n";
        String systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

        // Searchs for a rectangle not in the list, and then checks for correct
        // output when it is found
        inputLine = "search r1";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Point not found: r1\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();
    }


    /**
     * test "dump" command output, and further dump testing is done in skiplist
     * and database.
     */
    public void testDump() {

        // Checks the dump command prints the correct output, further testing is
        // in the skipList
        String inputLine = "dump";
        testCommandProcessor.processor(inputLine);
        String correctOutput = "SkipList dump:\n"
            + "Node has depth 1, Value (null)\n" + "SkipList size is: 0\n"
            + "QuadTree dump:\n" + "Node at 0, 0, 1024: Empty\n"
            + "1 quadtree nodes printed\n";
        String systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();
    }


    /**
     * test "duplicates" command output
     */
    public void testDuplicate() {
        // Inserts a rectangle for testing search, and clears the output
        String inputLine = "insert a1 10 10";
        String inputLine2 = "insert a2 10 10";
        String inputLine3 = "insert a3 10 10";
        String inputLine4 = "insert a4 10 10";
        String inputLine5 = "insert a5 10 10";
        String inputLine6 = "insert x1 20 20";
        String inputLine7 = "insert x2 20 20";
        String inputLine8 = "insert b1 200 200";
        String inputLine10 = "insert b2 200 201";
        String inputLine11 = "insert b3 200 200";
        String inputLine12 = "insert c1 600 600";
        String inputLine13 = "insert c2 600 600";
        String inputLine14 = "insert c3 600 601";
        String inputLine15 = "insert d1 200 600";
        String inputLine16 = "insert d2 200 601";
        String inputLine17 = "insert d3 200 601";
        String inputLine18 = "insert e1 600 200";
        String inputLine19 = "insert e2 600 201";
        String inputLine20 = "insert e3 600 201";
        String inputLine21 = "insert f1 0 1000";
        String inputLine22 = "insert f2 0 1001";
        String inputLine23 = "insert f3 1 1000";

        testCommandProcessor.processor(inputLine);
        testCommandProcessor.processor(inputLine2);
        testCommandProcessor.processor(inputLine3);
        testCommandProcessor.processor(inputLine4);
        testCommandProcessor.processor(inputLine5);
        testCommandProcessor.processor(inputLine6);
        testCommandProcessor.processor(inputLine7);
        testCommandProcessor.processor(inputLine8);
        testCommandProcessor.processor(inputLine10);
        testCommandProcessor.processor(inputLine11);
        testCommandProcessor.processor(inputLine12);
        testCommandProcessor.processor(inputLine13);
        testCommandProcessor.processor(inputLine14);
        testCommandProcessor.processor(inputLine15);
        testCommandProcessor.processor(inputLine16);
        testCommandProcessor.processor(inputLine17);
        testCommandProcessor.processor(inputLine18);
        testCommandProcessor.processor(inputLine19);
        testCommandProcessor.processor(inputLine20);
        testCommandProcessor.processor(inputLine21);
        testCommandProcessor.processor(inputLine22);
        testCommandProcessor.processor(inputLine23);
        systemOut().clearHistory();

        String inputLine1 = "duplicates";
        testCommandProcessor.processor(inputLine1);
        String correctOutput = "Duplicate points:\n" + "(10, 10)\n"
            + "(20, 20)\n" + "(200, 200)\n" + "(600, 201)\n" + "(200, 601)\n"
            + "(600, 600)\n";
        String systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();
    }


    /**
     * test unrecognized commands output, such as entirely unrecognized commands
     * as well as correct commands with the wrong number of arguments
     */
    public void testUnrecognizedCommand() {

        // Checks for a typical unrecognized command, and then clears history
        String inputLine = "invalid command";
        testCommandProcessor.processor(inputLine);
        String correctOutput = "Unrecognized command: invalid command\n";
        String systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

        // Checks for a search unrecognized command, on basis of too many
        // arguments and then clears history
        inputLine = "search r4 r5";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Unrecognized command: search r4 r5\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

        // Checks for a remove unrecognized command, on basis of wrong amount of
        // arguments and then clears history
        inputLine = "remove 6 8 9";
        testCommandProcessor.processor(inputLine);
        correctOutput = "Unrecognized command: remove 6 8 9\n";
        systemOutput = systemOut().getHistory();
        assertEquals(correctOutput, systemOutput);
        systemOut().clearHistory();

    }

}
