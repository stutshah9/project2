/**
 * This class represents the flyweight EmptyNode of our quadTree. Notice how we
 * only create this node once, and only if it doesn't already exist. If we try
 * to insert at this emptyNode, we return a new LeafNode with our data. Search
 * returns null as this hold no nodes, and remove returns the instance since we
 * want this part of quadtree to stay the same. Dump prints out the empty
 * status, and regionsearch returns that this is a node. Overall, empty node
 * functionality.
 * 
 * @author Stuti and Jaydon
 * 
 * @version 2024-03-16
 */
public class EmptyNode implements QuadNodeInterface {

    private static EmptyNode flyweight = null;

    private EmptyNode() {

    }


    /**
     * getInstance method
     * 
     * @return EmptyNode
     */
    public static EmptyNode getInstance() {
        if (flyweight == null) {
            flyweight = new EmptyNode();
        }
        return flyweight;
    }


    /**
     * insert method
     * 
     * @param point
     *            the point to insert
     * @param x
     *            the x-coordinate
     * @param y
     *            the y-coordinate
     * @param s
     *            the size
     * @return QuadNodeInterface
     */
    public QuadNodeInterface insert(Point point, int x, int y, int s) {

        LeafNode leaf = new LeafNode();
        leaf.insert(point, x, y, s);
        return leaf;
    }


    /**
     * search method
     * 
     * @param search
     *            the Point to search
     * @param x
     *            the x-coordinate
     * @param y
     *            the y-coordinate
     * @param s
     *            the size
     * @return KVPair<String, Point>
     */
    public Point search(Point search, int x, int y, int s) {
        return null;
    }


    /**
     * remove method
     * 
     * @param removal
     *            the Point to remove
     * @param x
     *            the x-coordinate
     * @param y
     *            the y-coordinate
     * @param s
     *            the size
     * @return QuadNodeInterface
     */
    public QuadNodeInterface remove(Point removal, int x, int y, int s) {
        return getInstance();
    }


    /**
     * dump method
     * 
     * @param level
     *            the level of indentation
     * @param x
     *            the x-coordinate
     * @param y
     *            the y-coordinate
     * @param s
     *            the size
     * @return int
     */
    public int dump(int level, int x, int y, int s) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < level; i++) {
            output.append("  ");
        }
        output.append("Node at ");
        output.append(x);
        output.append(", ");
        output.append(y);
        output.append(", ");
        output.append(s);
        output.append(": Empty");
        System.out.println(output.toString());
        return 1;
    }


    /**
     * duplicates method
     */
    public void duplicates() {

    }


    /**
     * regionSearch method
     * 
     * @param x
     *            the x-coordinate
     * @param y
     *            the y-coordinate
     * @param s
     *            the size
     * @param region
     *            the search region
     * @return int
     */
    public int regionSearch(int x, int y, int s, Rectangle region) {
        return 1;
    }

}
