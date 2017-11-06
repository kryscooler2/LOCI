Back :

The API allows the user to get all stations of subway. Thanks to this API we can also have the shortest path between two stations

REST : 

This is an REST API (Framework to use http request). Data is exposed as URL which can be retrieved with HTTP clients (such as web browsers).


API documentation is available here :
To create the server on a machine you have to run the class /LOCI/back_loci/src/main/java\com/loci/LociBackApplication.java thanks to eclipse or another IDE.


Some http requests are allowed to get informations from the server :

List of URL : 
	- {ip}:8081/subway/stations  					==> get all stations.
	- {ip}:8081/subway/edges     					==> get all edges.
	- {ip}:8081/subway/SP/{from}/{to}				==> get the shortest between to stations.
	- {ip}:8081/subway/lines						==> get all subways lines. 
	- {ip}:8081/subway/lines/Metro_{numberLine}		==> get a subway line by his name.
	- {ip}:8081/subway/stationsNames				==> get all stations Names
