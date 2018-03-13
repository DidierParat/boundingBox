import java.math.BigDecimal;
import models.BoundingBox;
import models.Scan;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BoundingBoxMathTest {
    @Test
    public void testCreateBoundingBoxFromScan() {
        final BigDecimal[][] points = new BigDecimal[][] {
                {new BigDecimal(5.0), new BigDecimal(7.0), new BigDecimal(-3.4)},
                {new BigDecimal(8.0), new BigDecimal(5.0), new BigDecimal(2.2)},
                {new BigDecimal(10.0), new BigDecimal(12.0), new BigDecimal(6.0)},
                {new BigDecimal(15.1), new BigDecimal(9.2), new BigDecimal(2.2)},
                {new BigDecimal(9.3), new BigDecimal(10.2), new BigDecimal(3.1)}
        };
        final BoundingBox boundingBox = BoundingBoxMath.createBoundingBoxFromScan(points);
        final BigDecimal[] boundingBoxSize = boundingBox.getBoundingBoxSize();
        assertThat(boundingBoxSize.length, is(3));
        assertThat(boundingBoxSize[0], BigDecimalCloseTo.closeTo(new BigDecimal(10.1), new BigDecimal(0.01)));
        assertThat(boundingBoxSize[1], BigDecimalCloseTo.closeTo(new BigDecimal(7.0), new BigDecimal(0.01)));
        assertThat(boundingBoxSize[2], BigDecimalCloseTo.closeTo(new BigDecimal(9.4), new BigDecimal(0.01)));

        final BigDecimal[] boundingBoxCenter = boundingBox.getCenter();
        assertThat(boundingBoxCenter.length, is(3));
        assertThat(boundingBoxCenter[0], BigDecimalCloseTo.closeTo(new BigDecimal(10.05), new BigDecimal(0.01)));
        assertThat(boundingBoxCenter[1], BigDecimalCloseTo.closeTo(new BigDecimal(8.5), new BigDecimal(0.01)));
        assertThat(boundingBoxCenter[2], BigDecimalCloseTo.closeTo(new BigDecimal(1.3), new BigDecimal(0.01)));
    }
}
