package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;

public class BoundingBox {
    protected static final String BOUNDING_BOX_PARAM = "boundingBoxSize";
    protected static final String CENTER_PARAM = "center";

    @JsonProperty(BOUNDING_BOX_PARAM)
    @Getter
    private final BigDecimal[] boundingBoxSize;

    @JsonProperty(CENTER_PARAM)
    @Getter
    private final BigDecimal[] center;

    public BoundingBox(
            @JsonProperty(value = BOUNDING_BOX_PARAM, required = true)
            final BigDecimal[] boundingBoxSize,
            @JsonProperty(value = CENTER_PARAM, required = true)
            final BigDecimal[] center) {
        this.boundingBoxSize = boundingBoxSize;
        this.center = center;
    }
}
