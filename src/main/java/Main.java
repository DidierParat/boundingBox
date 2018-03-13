import spark.Spark;

public class Main {

    public static void main(final String[] args) {
        final ScanController scanController = new ScanController();
        Spark.post("/scans", (req, res) -> scanController.createNewScan(req, res));
        Spark.get("/scans/:id", (req, res) -> scanController.getScan(req, res));
        Spark.put("/scans/:id", (req, res) -> scanController.createOrUpdateScan(req, res));
        Spark.get("/scans/:id/boundingbox", (req, res) -> scanController.getBoundingBox(req, res));
        Spark.awaitInitialization();
    }
}
