package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Scan {
    protected static final String POINTS_PARAM = "points";

    @JsonProperty(POINTS_PARAM)
    @Getter
    private final BigDecimal[][] points;

    @JsonCreator
    public Scan(
            @JsonProperty(value = POINTS_PARAM, required = true)
            final BigDecimal[][] points) {
        this.points = points;
    }
}
