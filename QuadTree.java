/**
 * This class implements QuadTree data structure for Points, using the
 * QuadNodeInterface to implement a variety of nodes along the tree (Internal,
 * Leaf, and Empty Node), which ensure fast spatial quieries. This quadTree and
 * the nodes contained provide functionality to insert, search for
 * points in a given region, removing by value, and printing out duplicates in
 * the tree or the tree entirely
 * 
 * @author Stuti and Jaydon
 * 
 * @version 2024-03-16
 */
public class QuadTree {
    private QuadNodeInterface rootNode;

    public QuadTree() {
        rootNode = EmptyNode.getInstance();
    }


    public void insert(Point point) {
        rootNode = rootNode.insert(point, 0, 0, 1024);
    }


    public Point remove(Point removal) {
        Point removed = rootNode.search(removal, 0, 0, 1024);
        if (removed != null) {
            rootNode = rootNode.remove(removal, 0, 0, 1024);
        }
        return removed;
    }


    public int dump() {
        int count = rootNode.dump(0, 0, 0, 1024);

        System.out.println(count + " quadtree nodes printed");
        return count;
    }


    public void duplicates() {
        rootNode.duplicates();
    }


    public int regionSearch(Rectangle region) {
        int count = rootNode.regionSearch(0, 0, 1024, region);

        System.out.println(count + " quadtree nodes visited");

        return count;
    }

}
