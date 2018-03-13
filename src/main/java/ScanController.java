import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.BoundingBox;
import models.Scan;
import models.ScanWithId;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class ScanController {
    private static final Logger LOGGER
            = Logger.getLogger(ScanController.class.getName());

    private final ObjectMapper mapper;
    private final Map<String, ScanWithId> scansMap;
    private final Map<String, BoundingBox> boundingBoxMap;

    public ScanController() {
        this.mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        this.scansMap = new HashMap<>();
        this.boundingBoxMap = new HashMap<>();
    }

    public String createNewScan(
            final Request request,
            final Response response) {
        final String id = getNewId();
        final Scan scan = parseRequest(request, Scan.class);
        final ScanWithId scanWithId = new ScanWithId(id, scan.getPoints());
        final BoundingBox boundingBox;
        try {
            boundingBox
                    = BoundingBoxMath.createBoundingBoxFromScan(
                            scan.getPoints());
        } catch (final IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Exception thrown.", e);
            halt(400);
            return "";
        }
        scansMap.put(id, scanWithId);
        boundingBoxMap.put(id, boundingBox);
        return dataToJson(scanWithId);
    }

    public String getScan(
            final Request request,
            final Response response) {
        final String id = request.params(":id");
        final ScanWithId scanWithId = scansMap.get(id);
        if (scanWithId == null) {
            LOGGER.log(Level.WARNING, "Scan not found: " + id);
            halt(404);
        }
        return dataToJson(scanWithId);
    }

    public String createOrUpdateScan(
            final Request request,
            final Response response) {
        final String id = request.params(":id");
        final ScanWithId scanWithId = parseRequest(request, ScanWithId.class);
        if (!id.equals(scanWithId.getId())) {
            LOGGER.log(
                    Level.WARNING,
                    "ID found in url does not match"
                        + " ID found in request data.");
            halt(400);
        }
        final BoundingBox boundingBox;
        try {
            boundingBox
                    = BoundingBoxMath.createBoundingBoxFromScan(
                            scanWithId.getPoints());
        } catch (final IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Exception thrown.", e);
            halt(400);
            return "";
        }
        scansMap.put(id, scanWithId);
        boundingBoxMap.put(id, boundingBox);
        return dataToJson(scanWithId);
    }

    public String getBoundingBox(
            final Request request,
            final Response response) {
        final String id = request.params(":id");
        final BoundingBox boundingBox = boundingBoxMap.get(id);
        if (boundingBox == null) {
            LOGGER.log(Level.WARNING, "Bounding box not found: " + id);
            halt(404);
        }
        return dataToJson(boundingBox);
    }

    private String getNewId() {
        return UUID.randomUUID().toString();
    }

    private <T> T parseRequest(
            final Request request,
            final Class<T> tClass) {
        try {
            return mapper.readValue(request.body(), tClass);
        } catch (final IOException e) {
            LOGGER.log(
                    Level.WARNING,
                    "Could not read JSON, expected class: " + tClass,
                    e);
            halt(500);
            return null;
        }
    }

    private String dataToJson(final Object data) {
        try {
            final StringWriter sw = new StringWriter();
            mapper.writerWithDefaultPrettyPrinter().writeValue(sw, data);
            return sw.toString();
        } catch (final IOException e){
            LOGGER.log(
                    Level.WARNING,
                    "Could not convert data to JSON String.",
                    e);
            halt(500);
            return null;
        }
    }
}
