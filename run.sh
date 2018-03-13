#!/usr/bin/env bash
mvn package assembly:single

echo "Starting server..."
java -cp target/software-challenge-didier-1.0-SNAPSHOT-jar-with-dependencies.jar Main >logs.txt 2>&1 &
SERVER_PID=$!
SERVER_STARTED=1
while [ $SERVER_STARTED -eq 1 ]
do
    grep 'INFO org.eclipse.jetty.server.Server - Started' logs.txt
    SERVER_STARTED=$?
done
echo "Server started. Logs are in logs.txt"

echo "Endpoint 1: POST scan"
curl -X POST --data '{ "points": [[5.0, 7.0, -3.4], [8.0, 5.0 ,2.2], [10.0, 12.0, 6], [15.1, 9.2, 2.2], [9.3, 10.2, 3.1]] }' localhost:4567/scans
echo ""

echo "Endpoint 3: PUT scan"
curl -X PUT --data '{ "id": "my-scan1", "points": [[5.0, 7.0, -3.4], [8.0, 5.0 ,2.2], [10.0, 12.0, 6], [15.1, 9.2, 2.2], [9.3, 10.2, 3.1]] }' localhost:4567/scans/my-scan1
echo ""

echo "Endpoint 2: GET scan"
curl -X GET localhost:4567/scans/my-scan1
echo ""

echo "Endpoint 4: GET bounding box of PUT scan"
curl -X GET localhost:4567/scans/my-scan1/boundingbox
echo ""

echo "Shutting down server"
kill $SERVER_PID