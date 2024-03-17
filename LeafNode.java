import java.util.ArrayList;

/**
 * This class represents the data-holding parts of our quadTree, the Leaf nodes.
 * As such, we have implemented all the methods here with that in mind. When we
 * hold too much data, we split this into an internal node. when we remove all
 * data, we turn it into an Empty Node. This is also where we check if the nodes
 * in our data array are within the region, where we check for duplicates, and
 * where we "dump" our data. We also have a search method to return a point
 * given an x and y coordinate.
 * 
 * @author Stuti and Jaydon
 * 
 * @version 2024-03-16
 */
public class LeafNode implements QuadNodeInterface {

    private ArrayList<Point> data;

    public LeafNode() {
        data = new ArrayList<>();
    }


    @Override
    public QuadNodeInterface insert(Point point, int x, int y, int s) {
        if (data.size() < 3 || (data.get(0).equalCoords(point) && data.get(1)
            .equalCoords(point) && data.get(2).equalCoords(point))) {
            data.add(point);
            return this;
        }
        else {
            InternalNode internalNode = new InternalNode();
            for (Point pair : data) {
                internalNode.insert(pair, x, y, s);
            }
            internalNode.insert(point, x, y, s);
            return internalNode;

        }
    }


    /**
     * Searches for a point in the quadtree represented by this leaf node.
     * 
     * @param search
     *            the point to search for
     * @param x
     *            the x-coordinate of the region represented by this node
     * @param y
     *            the y-coordinate of the region represented by this node
     * @param s
     *            the size of the region represented by this node
     * @return the found point, or null if not found
     */
    public Point search(Point search, int x, int y, int s) {
        Point found = null;
        for (Point datapoint : data) {
            if (datapoint.equalCoords(search)) {
                found = datapoint;
                break;
            }
        }
        return found;
    }


    /**
     * Removes a point from the quadtree represented by this leaf node.
     * 
     * @param removal
     *            the point to remove
     * @param x
     *            the x-coordinate of the region represented by this node
     * @param y
     *            the y-coordinate of the region represented by this node
     * @param s
     *            the size of the region represented by this node
     * @return the updated quadtree after removal
     */
    public QuadNodeInterface remove(Point removal, int x, int y, int s) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).equalCoords(removal)) {
                data.remove(i);
                break;
            }
        }
        if (data.isEmpty()) {
            return EmptyNode.getInstance();
        }
        else {
            return this;
        }
    }


    /**
     * Checks if the leaf node is empty.
     * 
     * @return true if the leaf node is empty, false otherwise
     */
    public boolean isEmpty() {
        return data.size() == 0;
    }


    /**
     * Dumps the contents of the quadtree represented by this leaf node.
     * 
     * @param level
     *            the current level of the quadtree
     * @param x
     *            the x-coordinate of the region represented by this node
     * @param y
     *            the y-coordinate of the region represented by this node
     * @param s
     *            the size of the region represented by this node
     * @return the number of nodes dumped
     */
    public int dump(int level, int x, int y, int s) {
        StringBuilder output = new StringBuilder();
        String spaces = "";
        for (int i = 0; i < level; i++) {
            spaces += "  ";
        }
        output.append(spaces);
        output.append("Node at ");
        output.append(x);
        output.append(", ");
        output.append(y);
        output.append(", ");
        output.append(s);
        output.append(":");
        for (Point pair : data) {
            output.append("\n");
            output.append(spaces);
            output.append(pair);
        }
        System.out.println(output.toString());
        return 1;
    }


    /**
     * Checks for duplicates in the quadtree represented by this leaf node.
     */
    public void duplicates() {
        Point helper = helperDuplicate();
        if (helper != null) {
            System.out.println("(" + helper.getxCoordinate() + ", " + helper
                .getyCoordinate() + ")");
        }
    }


    /**
     * helper method for duplicates
     */
    private Point helperDuplicate() {
        if (data.size() == 1) {
            return null;
        }
        else if (data.size() == 2) {
            if (data.get(0).equalCoords(data.get(1))) {
                return data.get(0);
            }
            else {
                return null;
            }
        }
        else if (data.size() == 3) {
            if (data.get(0).equalCoords(data.get(1))) {
                return data.get(0);
            }
            else if (data.get(0).equalCoords(data.get(2))) {
                return data.get(0);
            }
            else if (data.get(1).equalCoords(data.get(2))) {
                return data.get(1);
            }
            else {
                return null;
            }
        }
        else {
            return data.get(0);
        }
    }


    /**
     * Performs a region search in the quadtree represented by this leaf node.
     * 
     * @param x
     *            the x-coordinate of the region represented by this node
     * @param y
     *            the y-coordinate of the region represented by this node
     * @param s
     *            the size of the region represented by this node
     * @param region
     *            the region to search within
     * @return the number of points found within the region
     */
    public int regionSearch(int x, int y, int s, Rectangle region) {

        for (Point pair : data) {
            int tempX = pair.getxCoordinate();
            int tempY = pair.getyCoordinate();
            if ((tempX >= region.getxCoordinate()) && (tempX <= region
                .getWidth() + region.getxCoordinate()) && (tempY >= region
                    .getyCoordinate()) && (tempY <= region.getHeight() + region
                        .getyCoordinate())) {
                System.out.println("Point found: " + pair);
            }

        }
        return 1;
    }


    /**
     * Gets the data stored in this leaf node.
     * 
     * @return the data stored in this leaf node
     */
    public ArrayList<Point> getData() {
        return data;
    }

}
