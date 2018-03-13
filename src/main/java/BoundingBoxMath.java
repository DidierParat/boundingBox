import java.math.BigDecimal;
import models.BoundingBox;
import models.Scan;

public class BoundingBoxMath {

    public static BoundingBox createBoundingBoxFromScan(
            final BigDecimal[][] points) throws IllegalArgumentException {
        final BigDecimal[][] boundingBoxCoordinates
                = computeCoordinatesOfBoundingBox(points);
        final BigDecimal[] boundingBoxSize
                = computeSizeOfBoundingBox(boundingBoxCoordinates);
        final BigDecimal[] boundingBoxCenter
                = computeCenterOfBoundingBox(
                        boundingBoxCoordinates, boundingBoxSize);
        return new BoundingBox(boundingBoxSize, boundingBoxCenter);
    }

    private static BigDecimal[][] computeCoordinatesOfBoundingBox(
            final BigDecimal[][] points) throws IllegalArgumentException {
        if (points.length == 0) {
            throw new IllegalArgumentException(
                    "Array of points from scan is empty");
        }
        final BigDecimal[] maxCoordinates = new BigDecimal[3];
        final BigDecimal[] minCoordinates = new BigDecimal[3];
        maxCoordinates[0] = points[0][0];
        maxCoordinates[1] = points[0][1];
        maxCoordinates[2] = points[0][2];
        minCoordinates[0] = points[0][0];
        minCoordinates[1] = points[0][1];
        minCoordinates[2] = points[0][2];
        for (final BigDecimal[] point : points) {
            if (point.length != 3) {
                throw new IllegalArgumentException(
                        "Unexpected length for one of the points. Length: "
                        + point.length);
            }
            if (point[0].compareTo(maxCoordinates[0]) == 1) {
                maxCoordinates[0] = point[0];
            }
            if (point[1].compareTo(maxCoordinates[1]) == 1) {
                maxCoordinates[1] = point[1];
            }
            if (point[2].compareTo(maxCoordinates[2]) == 1) {
                maxCoordinates[2] = point[2];
            }
            if (point[0].compareTo(minCoordinates[0]) == -1) {
                minCoordinates[0] = point[0];
            }
            if (point[1].compareTo(minCoordinates[1]) == -1) {
                minCoordinates[1] = point[1];
            }
            if (point[2].compareTo(minCoordinates[2]) == -1) {
                minCoordinates[2] = point[2];
            }
        }
        final BigDecimal[][] boundingBoxCoordinates = new BigDecimal[8][3];
        boundingBoxCoordinates[0][0] = minCoordinates[0];
        boundingBoxCoordinates[0][1] = minCoordinates[1];
        boundingBoxCoordinates[0][2] = minCoordinates[2];

        boundingBoxCoordinates[1][0] = minCoordinates[0];
        boundingBoxCoordinates[1][1] = minCoordinates[1];
        boundingBoxCoordinates[1][2] = maxCoordinates[2];

        boundingBoxCoordinates[2][0] = minCoordinates[0];
        boundingBoxCoordinates[2][1] = maxCoordinates[1];
        boundingBoxCoordinates[2][2] = minCoordinates[2];

        boundingBoxCoordinates[3][0] = maxCoordinates[0];
        boundingBoxCoordinates[3][1] = minCoordinates[1];
        boundingBoxCoordinates[3][2] = minCoordinates[2];

        boundingBoxCoordinates[4][0] = maxCoordinates[0];
        boundingBoxCoordinates[4][1] = maxCoordinates[1];
        boundingBoxCoordinates[4][2] = minCoordinates[2];

        boundingBoxCoordinates[5][0] = maxCoordinates[0];
        boundingBoxCoordinates[5][1] = minCoordinates[1];
        boundingBoxCoordinates[5][2] = maxCoordinates[2];

        boundingBoxCoordinates[6][0] = minCoordinates[0];
        boundingBoxCoordinates[6][1] = maxCoordinates[1];
        boundingBoxCoordinates[6][2] = maxCoordinates[2];

        boundingBoxCoordinates[7][0] = maxCoordinates[0];
        boundingBoxCoordinates[7][1] = maxCoordinates[1];
        boundingBoxCoordinates[7][2] = maxCoordinates[2];
        return boundingBoxCoordinates;
    }

    private static BigDecimal[] computeSizeOfBoundingBox(
            final BigDecimal[][] boundingBoxCoordinates) {
        final BigDecimal[] boundingBoxSize = new BigDecimal[3];
        boundingBoxSize[0]
                = boundingBoxCoordinates[0][0]
                .subtract(boundingBoxCoordinates[3][0]).abs();
        boundingBoxSize[1]
                = boundingBoxCoordinates[0][1]
                .subtract(boundingBoxCoordinates[2][1]).abs();
        boundingBoxSize[2]
                = boundingBoxCoordinates[0][2]
                .subtract(boundingBoxCoordinates[1][2]).abs();
        return boundingBoxSize;
    }

    private static BigDecimal[] computeCenterOfBoundingBox(
            final BigDecimal[][] boundingBoxCoordinates,
            final BigDecimal[] boundingBoxSize) {
        final BigDecimal[] boundingBoxCenter = new BigDecimal[3];
        final BigDecimal twoAsBigDecimal = new BigDecimal(2);
        boundingBoxCenter[0]
                = boundingBoxCoordinates[0][0]
                .add(boundingBoxSize[0].divide(twoAsBigDecimal));
        boundingBoxCenter[1]
                = boundingBoxCoordinates[0][1]
                .add(boundingBoxSize[1].divide(twoAsBigDecimal));
        boundingBoxCenter[2]
                = boundingBoxCoordinates[0][2]
                .add(boundingBoxSize[2].divide(twoAsBigDecimal));
        return boundingBoxCenter;
    }
}
