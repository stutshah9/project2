/**
 * This class holds the name and coordinates of a point and methods to check if
 * it has the same coordinates and/or name as another Point
 * 
 * @author Stuti and Jaydon
 * 
 * @version 2024-02-28
 */
public class Point {
    private String namePoint;
    // the x coordinate of the point
    private int xCoordinate;
    // the y coordinate of the point
    private int yCoordinate;

    /**
     * Creates an object with the values to the parameters given in the
     * xCoordinate, yCoordinate
     * 
     * @param x
     *            x-coordinate of the point
     * @param y
     *            y-coordinate of the point
     */

    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 1024;

    /**
     * constructor method
     * 
     * @param x
     *            takes in xCoordinate
     * @param y
     *            takes in yCoordinate
     */
    public Point(String name, int x, int y) {
        namePoint = name;
        xCoordinate = x;
        yCoordinate = y;
    }


    public String getName() {
        return namePoint;
    }


    /**
     * Getter for the x coordinate
     *
     * @return the x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }


    /**
     * Getter for the y coordinate
     *
     * @return the y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }


    /**
     * Checks, if the invoking point has the same coordinates as poi.
     * 
     * @param poi
     *            the poi parameter
     * @return true if the point has the same coordinates as poi, false if
     *         not
     */
    public boolean equals(Object poi) {
        boolean result = false;
        // object is null
        if (poi == null) {
            return false;
        }

        // same instance
        if (poi == this) {
            return true;
        }

        // same class
        if (poi.getClass() == this.getClass()) {
            // cast Object as Point
            Point temp = (Point)poi;
            result = (this.namePoint == temp.namePoint && this
                .getxCoordinate() == (temp.getxCoordinate()) && this
                    .getyCoordinate() == (temp.getyCoordinate()));
        }
        return result;
    }


    public boolean equalName(Point other) {
        return this.namePoint == other.namePoint;
    }


    public boolean equalCoords(Point other) {
        return this.getxCoordinate() == other.getxCoordinate() && this
            .getyCoordinate() == other.getyCoordinate();
    }


    /**
     * Outputs a human readable string with information about the point
     * which includes the x and y coordinate
     *
     * @return a human readable string containing information about the
     *         point
     */
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("(" + namePoint + ", " + xCoordinate + ", " + yCoordinate
            + ")");
        return string.toString();
    }


    /**
     * Checks if the point has invalid parameters
     * 
     * @return true if the point has invalid parameters, false if not
     */
    public boolean isInvalid() {
        return xCoordinate >= MAX_WIDTH || xCoordinate < 0
            || yCoordinate >= MAX_HEIGHT || yCoordinate < 0;
    }
}
