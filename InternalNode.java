import java.util.ArrayList;

/**
 * This class represents the navigational parts of our quadTree, the Internal
 * nodes. As such, we have implemented all the methods here with that in mind.
 * When we insert, search, remove, regionsearch, or dump, we simply call the
 * method on the correct child node/nodes, and send it down the tree. When it is
 * time to remove, we make sure to merge our children into a new LeafNode if we
 * can, otherwise leaving it alone. This is done through a couple of helper
 * methods. Overall, this class satisfies all functionality an internal node
 * should have!
 * 
 * @author Stuti and Jaydon
 * 
 * @version 2024-03-16
 */
public class InternalNode implements QuadNodeInterface {

    private QuadNodeInterface nw;
    private QuadNodeInterface ne;
    private QuadNodeInterface sw;
    private QuadNodeInterface se;

    /**
     * Constructor method for the InternalNode class.
     */
    public InternalNode() {
        nw = EmptyNode.getInstance();
        ne = EmptyNode.getInstance();
        sw = EmptyNode.getInstance();
        se = EmptyNode.getInstance();
    }


    @Override
    public QuadNodeInterface insert(Point point, int x, int y, int s) {
        int xInsert = point.getxCoordinate();
        int yInsert = point.getyCoordinate();
        int newSize = s / 2;
        int maxX = x + newSize;
        int maxY = y + newSize;
        if (xInsert < maxX) {
            if (yInsert < maxY) {
                nw = nw.insert(point, x, y, newSize);
            }
            else {
                sw = sw.insert(point, x, maxY, newSize);
            }
        }
        else {
            if (yInsert < maxY) {
                ne = ne.insert(point, maxX, y, newSize);
            }
            else {
                se = se.insert(point, maxX, maxY, newSize);
            }
        }
        return this;
    }


    @Override
    public Point search(Point search, int x, int y, int s) {
        Point found = null;

        int xSearch = search.getxCoordinate();
        int ySearch = search.getyCoordinate();
        int newSize = s / 2;
        int maxX = x + newSize;
        int maxY = y + newSize;
        if (xSearch <= maxX) {
            if (ySearch <= maxY) {
                found = nw.search(search, x, y, newSize);
            }
            else {
                found = sw.search(search, x, maxY, newSize);
            }
        }
        else {
            if (ySearch <= maxY) {
                found = ne.search(search, maxX, y, newSize);
            }
            else {
                found = se.search(search, maxX, maxY, newSize);
            }
        }
        return found;
    }


    /**
     * Removes a point from the quadtree represented by this internal node.
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
        int xRemove = removal.getxCoordinate();
        int yRemove = removal.getyCoordinate();
        int newSize = s / 2;
        int maxX = x + newSize;
        int maxY = y + newSize;
        if (xRemove <= maxX) {
            if (yRemove <= maxY) {
                nw = nw.remove(removal, x, y, newSize);
            }
            else {
                sw = sw.remove(removal, x, maxY, newSize);
            }
        }
        else {
            if (yRemove <= maxY) {
                ne = ne.remove(removal, maxX, y, newSize);
            }
            else {
                se = se.remove(removal, maxX, maxY, newSize);
            }
        }

        if (mergeLeavesDuplicates()) {
            return mergeLeaves(x, y, s);
        }
        else {
            return this;
        }
    }


    /**
     * helper method used in rmeove
     */
    private boolean mergeLeavesDuplicates() {
        if (!(nw instanceof InternalNode || ne instanceof InternalNode
            || sw instanceof InternalNode || se instanceof InternalNode)) {

            ArrayList<Point> dataChildren = new ArrayList<>();
            if (nw instanceof LeafNode) {
                for (Point oldData : ((LeafNode)nw).getData())
                    dataChildren.add(oldData);
            }
            if (ne instanceof LeafNode) {
                for (Point oldData : ((LeafNode)ne).getData())
                    dataChildren.add(oldData);
            }
            if (sw instanceof LeafNode) {
                for (Point oldData : ((LeafNode)sw).getData())
                    dataChildren.add(oldData);
            }
            if (se instanceof LeafNode) {
                for (Point oldData : ((LeafNode)se).getData())
                    dataChildren.add(oldData);
            }

            int x = dataChildren.get(0).getxCoordinate();
            int y = dataChildren.get(0).getxCoordinate();
            if (dataChildren.size() <= 3) {
                return true;
            }
            for (Point dataChild : dataChildren) {
                if (x != dataChild.getxCoordinate() || y != dataChild
                    .getyCoordinate()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    /**
     * helper method used in remove
     */
    private QuadNodeInterface mergeLeaves(int x, int y, int s) {
        LeafNode newLeaf = new LeafNode();
        if (nw instanceof LeafNode) {
            for (Point oldData : ((LeafNode)nw).getData())
                newLeaf.insert(oldData, x, y, s);
        }
        if (ne instanceof LeafNode) {
            for (Point oldData : ((LeafNode)ne).getData())
                newLeaf.insert(oldData, x, y, s);
        }
        if (sw instanceof LeafNode) {
            for (Point oldData : ((LeafNode)sw).getData())
                newLeaf.insert(oldData, x, y, s);
        }
        if (se instanceof LeafNode) {
            for (Point oldData : ((LeafNode)se).getData())
                newLeaf.insert(oldData, x, y, s);
        }
        return newLeaf;
    }


    /**
     * Dumps the contents of the quadtree represented by this internal node.
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
        int newSize = s / 2;
        int maxX = x + newSize;
        int maxY = y + newSize;

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
        output.append(": Internal");
        System.out.println(output.toString());
        int dumpSum = nw.dump(level + 1, x, y, newSize);
        dumpSum += ne.dump(level + 1, maxX, y, newSize);
        dumpSum += sw.dump(level + 1, x, maxY, newSize);
        dumpSum += se.dump(level + 1, maxX, maxY, newSize);
        return 1 + dumpSum;
    }


    /**
     * Checks for duplicates in the quadtree represented by this internal node.
     */
    public void duplicates() {
        nw.duplicates();
        ne.duplicates();
        sw.duplicates();
        se.duplicates();
    }


    /**
     * Performs a region search in the quadtree represented by this internal
     * node.
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
        int searchSum = 1;
        int newSize = s / 2;
        int maxX = x + newSize;
        int maxY = y + newSize;
        if (region.intersect(new Rectangle(x, y, newSize, newSize))) {
            searchSum += nw.regionSearch(x, y, newSize, region);
        }
        if (region.intersect(new Rectangle(maxX, y, newSize, newSize))) {
            searchSum += ne.regionSearch(maxX, y, newSize, region);
        }
        if (region.intersect(new Rectangle(x, maxY, newSize, newSize))) {
            searchSum += sw.regionSearch(x, maxY, newSize, region);
        }
        if (region.intersect(new Rectangle(maxX, maxY, newSize, newSize))) {
            searchSum += se.regionSearch(maxX, maxY, newSize, region);
        }
        return searchSum;
    }
}
