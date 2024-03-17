import student.TestCase;

/**
 * EmptyNode test class that ensures that our flyweight works as expected,
 * saving us space
 * 
 * @author Stuti and Jaydon
 * @version 2024-03-16
 */
public class EmptyNodeTest extends TestCase {
    // private EmptyNode declared for testing
    private EmptyNode emptyNode;

    /**
     * setUp method, initializing testData Database
     */
    public void setUp() {
        emptyNode = EmptyNode.getInstance();
    }


    /**
     * test getInstance
     */
    public void testGetInstance() {
        assertNotNull(emptyNode);

        EmptyNode emptyNode1 = EmptyNode.getInstance();
        assertEquals(emptyNode, emptyNode1);
    }
}
