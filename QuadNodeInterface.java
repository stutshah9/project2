/**
 * This class is a basic interface for a variety of QuadNode data types to
 * ensure that given functionality for methods extends throughout all types of
 * QuadNode, ensuring that we can call insert, remove, search on each type of
 * node without having any typing troubles or having to make an if statement for
 * each type of different node to figure out what we are running
 * 
 * @author Stuti and Jaydon
 * 
 * @version 2024-03-16
 */
public interface QuadNodeInterface {

    public QuadNodeInterface insert(Point point, int x, int y, int s);


    public QuadNodeInterface remove(Point removal, int x, int y, int s);


    public Point search(Point search, int x, int y, int s);


    public int dump(int level, int x, int y, int s);


    public void duplicates();


    public int regionSearch(int x, int y, int s, Rectangle region);

}
