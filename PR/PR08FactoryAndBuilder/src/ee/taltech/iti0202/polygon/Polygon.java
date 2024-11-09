package ee.taltech.iti0202.polygon;

/**
 * The Polygon interface represents a geometric polygon shape.
 * Implementing classes should provide functionality to retrieve
 * the type of the polygon.
 */
public interface Polygon {

    /**
     * Gets the type of the polygon.
     * @return type of polygon as a string.
     */
    String getType();
}
