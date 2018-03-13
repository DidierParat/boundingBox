package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;

public class ScanWithId extends Scan {
    protected static final String ID_PARAM = "id";

    @JsonProperty(ID_PARAM)
    @Getter
    private final String id;

    @JsonCreator
    public ScanWithId(
            @JsonProperty(value = ID_PARAM, required = true)
            final String id,
            @JsonProperty(value = POINTS_PARAM, required = true)
            final BigDecimal[][] points) {
        super(points);
        this.id = id;
    }
}
