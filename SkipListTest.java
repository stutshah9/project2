import java.util.Iterator;

import student.TestCase;
import student.TestableRandom;

/**
 * Skiplist test class that extends TestCase
 * Used to test Skiplist methods in a variety
 * of situations to ensure that the Skiplist
 * remains ordered and valid
 * 
 * @author Stuti and Jaydon
 * @version 2024-02-08
 */
public class SkipListTest extends TestCase {

    // private Skiplist declared for testing
    private SkipList<Integer, String> testList;

    /**
     * setUp method, initializing testList SkipList
     */
    public void setUp() {
        testList = new SkipList<Integer, String>();
    }


    /**
     * test random level using the TestableRandom Class
     * and ensuring the correct levels are returned
     */
    public void testRandomLevel() {
        // Testing the random level generation
        TestableRandom.setNextInts(0, 2, 0, 3, 0, 6, 1);
        assertEquals(testList.randomLevel(), 3);
        assertEquals(testList.randomLevel(), 2);

    }


    /**
     * testing the insert method, test that the list is sorted correctly and
     * size is updated after a variety of insertions of differing levels
     */
    public void testInsert() {
        // Set up and checking emptiness
        TestableRandom.setNextInts(1, 2, 2, 2, 2, 1, 2, 3, 5, 7, 8, 8, 8, 9);
        assertEquals(testList.size(), 0);

        // Inserting the key-value pairs
        testList.insert(new KVPair<Integer, String>(4, "Apple"));
        testList.insert(new KVPair<Integer, String>(1, "Grape"));
        testList.insert(new KVPair<Integer, String>(8, "StrawBerry"));
        testList.insert(new KVPair<Integer, String>(6, "Banana"));
        testList.insert(new KVPair<Integer, String>(2, "WaterMelon"));
        testList.insert(new KVPair<Integer, String>(9, "HoneyDew"));

        // Testing size and then testing that each value is in the correct order
        assertEquals(testList.size(), 6);
        Iterator<KVPair<Integer, String>> skipListIter = testList.iterator();
        assertEquals(skipListIter.next().getValue(), "Grape");
        assertEquals(skipListIter.next().getValue(), "WaterMelon");
        assertEquals(skipListIter.next().getValue(), "Apple");
        assertEquals(skipListIter.next().getValue(), "Banana");
        assertEquals(skipListIter.next().getValue(), "StrawBerry");
        assertEquals(skipListIter.next().getValue(), "HoneyDew");
    }


    /**
     * testing the removeByKey method, test that the list is sorted correctly
     * and size is updated after a variety of insertions and removals of
     * differing levels. Tests all behavior of the remove method (item not
     * present) and also tests that the correct order is maintained/items were
     * actually removed
     */
    public void testRemoveByKey() {
        // Set up, checking emptiness, inserting a variety of values, and
        // testing the size thereafter
        TestableRandom.setNextInts(1, 2, 2, 2, 2, 1, 2, 3, 5, 7, 8, 8, 8, 9);
        assertEquals(testList.size(), 0);
        testList.insert(new KVPair<Integer, String>(4, "Apple"));
        testList.insert(new KVPair<Integer, String>(4, "BlueBerry"));
        testList.insert(new KVPair<Integer, String>(1, "Grape"));
        testList.insert(new KVPair<Integer, String>(8, "StrawBerry"));
        testList.insert(new KVPair<Integer, String>(6, "Banana"));
        testList.insert(new KVPair<Integer, String>(2, "WaterMelon"));
        testList.insert(new KVPair<Integer, String>(9, "HoneyDew"));
        assertEquals(testList.size(), 7);

        // Tests that KVPairs are found and returned correctly, size is updated,
        // and that nothing is removed when object is not present
        assertEquals("BlueBerry", testList.remove(4).getValue());
        assertEquals("HoneyDew", testList.remove(9).getValue());
        assertEquals("Grape", testList.remove(1).getValue());
        assertEquals(testList.size(), 4);
        assertEquals(null, testList.remove(1));
        assertEquals(testList.size(), 4);

        // Checks that the list remains in the correct order after removal, and
        // that the items were actually removed from the list entirely
        Iterator<KVPair<Integer, String>> skipListIter = testList.iterator();
        assertEquals(skipListIter.next().getValue(), "WaterMelon");
        assertEquals(skipListIter.next().getValue(), "Apple");
        assertEquals(skipListIter.next().getValue(), "Banana");
        assertEquals(skipListIter.next().getValue(), "StrawBerry");
    }


    /**
     * testing the removeByValue method, test that the list is sorted correctly
     * and size is updated after a variety of insertions and removals of
     * differing levels. Tests all behavior of the remove method (item not
     * present) and also tests that the correct order is maintained/items were
     * actually removed
     */
    public void testRemoveByValue() {
        // Set up, checking emptiness, inserting a variety of values, and
        // testing the size thereafter
        TestableRandom.setNextInts(1, 2, 2, 2, 2, 1, 2, 3, 5, 7, 8, 8, 8, 9);
        assertEquals(testList.size(), 0);
        testList.insert(new KVPair<Integer, String>(4, "Apple"));
        testList.insert(new KVPair<Integer, String>(4, "BlueBerry"));
        testList.insert(new KVPair<Integer, String>(1, "Grape"));
        testList.insert(new KVPair<Integer, String>(8, "StrawBerry"));
        testList.insert(new KVPair<Integer, String>(6, "Apple"));
        testList.insert(new KVPair<Integer, String>(2, "WaterMelon"));
        testList.insert(new KVPair<Integer, String>(9, "HoneyDew"));
        assertEquals(testList.size(), 7);

        // Tests that KVPairs are found and returned correctly, size is updated,
        // and that nothing is removed when object is not present
        assertEquals(4, testList.removeByValue("Apple").getKey().intValue());
        assertEquals(9, testList.removeByValue("HoneyDew").getKey().intValue());
        assertEquals(1, testList.removeByValue("Grape").getKey().intValue());
        assertEquals(testList.size(), 4);
        assertEquals(null, testList.removeByValue("Grape"));
        assertEquals(testList.size(), 4);

        // Checks that the list remains in the correct order after removal, and
        // that the items were actually removed from the list entirely
        Iterator<KVPair<Integer, String>> skipListIter = testList.iterator();
        assertEquals(skipListIter.next().getValue(), "WaterMelon");
        assertEquals(skipListIter.next().getValue(), "BlueBerry");
        assertEquals(skipListIter.next().getValue(), "Apple");
        assertEquals(skipListIter.next().getValue(), "StrawBerry");
    }


    /**
     * test search functionally, such as finding no values in an empty list,
     * finding no values when value is not within the list, and returning all
     * KVPairs with said key when KVPairs are in the list (checking that
     * correct values are returned as well)
     */
    public void testSearch() {
        // Set up, as well as checking the case for searching an empty skiplist
        TestableRandom.setNextInts(1, 2, 2, 2, 2, 1, 2, 3, 5, 7, 8, 8, 8, 9);
        assertTrue(testList.search(5).isEmpty());
        assertEquals(0, testList.search(10).size());

        // Sets up a test KVPairs for comparison after searching, inserts these
        // very same pairs into the list
        KVPair<Integer, String> searching1 = new KVPair<Integer, String>(4,
            "BlueBerry");
        KVPair<Integer, String> searching2 = new KVPair<Integer, String>(4,
            "Apple");

        // Inserts a variety of KVPairs into the skiplist for later comparison,
        // including a duplicate pair
        testList.insert(searching2);
        testList.insert(searching1);
        testList.insert(new KVPair<Integer, String>(1, "Grape"));
        testList.insert(new KVPair<Integer, String>(8, "StrawBerry"));
        testList.insert(new KVPair<Integer, String>(6, "Banana"));
        testList.insert(new KVPair<Integer, String>(2, "WaterMelon"));
        testList.insert(new KVPair<Integer, String>(9, "HoneyDew"));

        // Checks that the list finds both of the KVPairs and compares that with
        // said KVPairs to ensure equals
        assertEquals(searching1.getValue(), testList.search(4).get(0)
            .getValue());
        assertEquals(searching2.getValue(), testList.search(4).get(1)
            .getValue());

        // Checks that the size of returned list is correct, as well as the
        // empty case with a list with values
        assertEquals(true, testList.search(5).isEmpty());
        assertEquals(0, testList.search(10).size());
        assertEquals(false, testList.search(4).isEmpty());
        assertEquals(testList.search(4).size(), 2);
    }


    /**
     * test dump, checking that it dumps correctly when no values are in the
     * list, and that it dumps correctly when some values are in the list
     */
    public void testDump() {
        TestableRandom.setNextInts(2, 2, 2, 2, 1);

        // Empty list case
        testList.dump();
        assertEquals(systemOut().getHistory(), "SkipList dump:\r\n"
            + "Node has depth 1, Value (null)\r\n" + "SkipList size is: 0\n");
        systemOut().clearHistory();

        // Non-empty list case
        testList.insert(new KVPair<Integer, String>(4, "BlueBerry"));
        testList.dump();
        assertEquals(systemOut().getHistory(), "SkipList dump:\r\n"
            + "Node has depth 5, Value (null)\r\n"
            + "Node has depth 5, Value BlueBerry\r\n"
            + "SkipList size is: 1\n");
        systemOut().clearHistory();
    }

}
