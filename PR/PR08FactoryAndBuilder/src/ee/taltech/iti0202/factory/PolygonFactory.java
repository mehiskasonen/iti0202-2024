package ee.taltech.iti0202.factory;

import ee.taltech.iti0202.polygon.Hexagon;
import ee.taltech.iti0202.polygon.Pentagon;
import ee.taltech.iti0202.polygon.Polygon;
import ee.taltech.iti0202.polygon.Square;
import ee.taltech.iti0202.polygon.Triangle;

public class PolygonFactory {

    private static final int CASE_SIX = 6;

    /**
     * Factory makes a new Polygon with given amount of sides.
     * @param numberOfSides number of sides on the polygon
     * @return new Polygon class with correct number of sides ( numberOfSides = 4 -> new Square() )
     */
    public static Polygon getPolygon(int numberOfSides) throws IllegalArgumentException {

        return switch (numberOfSides) {
            case 3 -> new Triangle();
            case 4 -> new Square();
            case 5 -> new Pentagon();
            case CASE_SIX -> new Hexagon();
            default -> throw new IllegalArgumentException("Unexpected value: " + numberOfSides);
        };
    }
}
