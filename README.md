Simple backend with a REST API for posting and getting information about scans.
In this context a scan is a set of 3D points, and is represented (in JSON format) as follows:

```json
{
    “points”: [
  [5.0, 7.0, -3.4],
  [8.0, 5.0 ,2.2],
  [10.0, 12.0, 6],
  [15.1, 9.2, 2.2],
  [9.3, 10.2, 3.1]]
}
```
I.e. the points are represented as an array of arrays where each sub array contains the x, y, z coordinates of each point.

Based on the point data we want to find a Bounding box and the center point of the bounding box. In this context a Bounding box is simply a box surrounding the points, and represented by the distances between the faces of the box in the x, y and z direction. For the example above, the bounding box would be represented by size:
```json
[10.1, 7.0, 9.4]
```
and center:
```json
[10.05, 8.5, 1.3]
```

## Endpoints

* Endpoint 1 - POST scan
```
Description: Create a new scan
URL: POST /scans
Request data: json data representing a scan (see above), i.e. {“points”: [<points>]}
Return: {“id”: <id>, “points”: [<points>]} where the id is created by the backend can be used later when asking for scans
```

* Endpoint 2 GET scan
```
Description: Get the points of a specific scan
URL: GET /scans/<id>
Return: {“id”: <id>, “points”: [<points>]}
```

* Endpoint 3 PUT scan
```
Description: Create or update a scan with a specific ID
URL: PUT /scans/<id>
Request data: A scan object {“id”: <id>, “points”: [<points>]}
Return: {“id”: <id>, “points”: [<points>]}
```

* Endpoint 4 - GET bounding box
```
Description: Get the bounding box of a scan (specified by the id from Endpoint 1) 
URL: GET /scans/<id>/boundingbox
Return: {“boundingBox”: [<x_l>, <y_l>, <z_l> ] , “center”: [<x>, <y>, <z>]}
```

## General notes 
To test, run the bash script "./run.sh".
This script will (in this order):
- create the jar
- run the server
- test each endpoint with 1 curl command
- shut down the server