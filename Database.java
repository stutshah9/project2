import java.util.ArrayList;

/**
 * This class is responsible for interfacing between the command processor our
 * SkipList and QuadTree. The responsibility of this class is to further
 * interpret commands and check if said commands are valid on our dataSets. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList and/or QuadTree method after some preparation. For example, with
 * remove by value, we first call that on our quadtree to grab the name of the
 * point we are removing, and then similarly remove said point from our SkipList
 * and vice versa for the other remove. As such, this class ensures our commands
 * are run correctly and efficiently.
 * 
 * @author Stuti and Jaydon
 * 
 * @version 2024-02-17
 */
public class Database {
    // this is the SkipList object that we are using
    // a string for the name of the Point and then
    // a Point object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Point> list;

    private QuadTree quad;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Point SkipList.
     */
    public Database() {
        list = new SkipList<String, Point>();
        quad = new QuadTree();
    }


    /**
     * Inserts the KVPair in the SkipList if the Point has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the Point object has some area (not 0, 0, 0, 0). This insert will add
     * the KVPair specified into the sorted SkipList appropriately
     * 
     * @param pair
     *            the KVPair to be inserted
     */
    public void insert(KVPair<String, Point> pair) {

        // Validity check, rejected if invalid
        if (pair.getValue().isInvalid()) {
            System.out.println("Point rejected: " + pair.getValue());
        }

        // Otherwise inserts the node
        else {
            list.insert(pair);
            quad.insert(pair.getValue());
            System.out.println("Point inserted: " + pair.getValue());
        }

    }


    /**
     * Removes a Point with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the Point to be removed
     */
    public void remove(String name) {

        // Attempts to remove the Point by its key in the list
        KVPair<String, Point> removed = list.remove(name);

        // If the KVPair is not present, print the error message to the
        // console
        if (removed == null) {
            System.out.println("Point not removed: " + name);
        }

        // Otherwise print the removed KVPair to the console
        else {
            System.out.println("Point removed: " + removed.getValue());
            quad.remove(removed.getValue());
        }

    }


    /**
     * Removes a Point with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the Point to be removed
     * @param y
     *            x-coordinate of the Point to be removed
     */
    public void remove(int x, int y) {

        // Creates a new Point to try to remove
        Point requested = new Point("", x, y);

        // If the Point is not valid, rejects the Point entirely and
        // prints out the rejection error message
        if (x < 0 || y < 0) {
            System.out.println("Point rejected: (" + x + ", " + y + ")");
            return;
        }

        // Attempts to remove the Point by its value in the list
        Point removed = quad.remove(requested);

        // If the KVPair is not present, print the error message to the
        // console
        if (removed == null) {
            System.out.println("Point not found: (" + x + ", " + y + ")");
        }

        // Otherwise print the removed KVPair to the console
        else {
            System.out.println("Point removed: " + removed);
            list.removeByValue(removed);
        }
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region.
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {

        // Creates a new Rectangle region to search for intersections
        Rectangle region = new Rectangle(x, y, w, h);

        // If not a valid region (0 or negative area), print a rejection error
        if (w <= 0 || h <= 0) {
            System.out.println("Rectangle rejected: (" + region + ")");
            return;
        }

        // Prints out our starting message for our region search
        System.out.println("Points intersecting region (" + region + "):");

        quad.regionSearch(region);
    }


    /**
     * Displays any duplicate points that exist
     */
    public void duplicates() {
        System.out.println("Duplicate points:");
        quad.duplicates();

    }

// /**
// * Prints out all the rectangles that intersect each other. Note that it is
// * better not to implement an intersections method in the SkipList class as
// * the SkipList needs to be agnostic about the fact that it is storing
// * Rectangles.
// */
// public void intersections() {
//
// // Prints out our starting message for our intersections
// System.out.println("Intersection pairs:");
//
// // Sets up necessary iterators to iterate through the list and finds
// // intersection pairs.
// Iterator<KVPair<String, Rectangle>> itr2;
// itr1 = list.iterator();
//
// // Runs two iterators through the list, and if the value in both
// // iterators intersects eachother, prints out the pair of rectangles
// while (itr1.hasNext()) {
//
// // Iterates to the next value in the list and resets the second
// // iterator to get through each value in the list again
// KVPair<String, Rectangle> curr1 = itr1.next();
// itr2 = list.iterator();
//
// // Uses the second iterator in the list to go through each value
// // again, and checks if it intersects the first iterators current
// // value. If it does intersect the first value, prints out the
// // intersection pair
// while (itr2.hasNext()) {
// KVPair<String, Rectangle> curr2 = itr2.next();
// if (curr1 != curr2 && curr1.getValue().intersect(curr2
// .getValue())) {
// System.out.println("(" + curr1.getKey() + ", " + curr1
// .getValue() + " | " + curr2.getKey() + ", " + curr2
// .getValue() + ")");
// }
// }
// }
//
// // Resets the first iterator to the first node
// itr1 = list.iterator();
//
// }


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Point to be searched for
     */
    public void search(String name) {

        // Attempts to search for the specified rectangles in the list by name
        ArrayList<KVPair<String, Point>> foundRects = list.search(name);

        // If none is found/size is zero, prints out that the Point is not
        // found
        if (foundRects.size() == 0) {
            System.out.println("Point not found: " + name);
        }

        // Otherwise prints out each value in the list
        else {
            for (KVPair<String, Point> rect : foundRects) {
                System.out.println("Found " + rect.getValue());
            }
        }
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {

        // Calls the dump method of skipList
        list.dump();
        System.out.println("QuadTree dump:");
        int totalNodes = quad.dump();
    }

}
