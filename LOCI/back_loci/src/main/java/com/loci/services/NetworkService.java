package com.loci.services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loci.object.Edge;
import com.loci.object.Station;
import com.loci.object.Subway;

@Service
public class NetworkService {
	private static int nodesCount;
	private static int edgesCount;
	
	private static ArrayList<Station> stations = new ArrayList<Station>();
	private static ArrayList<Subway> subways = new ArrayList<Subway>();
	private Map<String, Subway> nameSubway = new HashMap<String, Subway>();
	private static List<Edge> edges = new ArrayList<Edge>();
	private static List<Double> listOfWeight = new ArrayList<Double>();
	
	@Autowired
	private ReadAndParseFilesService readFile;

	Subway subway;
	
	private static boolean isWeigtedGraph;
	
	/**
	 * create the network of subway
	 * @param tab
	 * @throws IOException
	 */
	public void createAllTheNetwork(String  tab[], boolean isWeighted) throws IOException {
		setWeigtedGraph(isWeighted);
		createAllStations(tab);
		addEdges(tab);
		addNeighborsToStations();
	}
	
	public List<Station> getAllStations() throws IOException {
		return getListStations();
	}
	
	public List<Edge> getAllEdges() throws IOException {
		return getEdges();
	}
	
	public ArrayList<Subway> getAllSubways() throws IOException {
		return subways;
	}
	
	public ArrayList<String> getAllStationsName() throws IOException {
		ArrayList<String> stationsNames = new ArrayList<String>();
		for(Station s : getListStations()) {
			if(!stationsNames.contains(s.getName())) {
				stationsNames.add(s.getName());
			}
		}
		return stationsNames;
	}
	
	/**
	 * Create All Stations.
	 * @param tab
	 * @throws IOException
	 */
	public void createAllStations(String tab[]) throws IOException {
		for(String str : tab) {
			readFile = new ReadAndParseFilesService();
			List<String> lines = readFile.read("documents/RATP_GTFS_METRO_" + str + "/stops.txt");
			ArrayList<Station> listStopsBySubway = new ArrayList<Station>();
			listStopsBySubway = readFile.parseStopsFile(lines);
			subway = new Subway();
			subway.setName("Metro_"+ str);
			subway.setStops(listStopsBySubway);	
			subway.setNumberOfStops(listStopsBySubway.size());
			nameSubway.put(subway.getName(), subway);
			subways.add(subway);
			if(stations.isEmpty()) {
				stations.addAll(listStopsBySubway);
			} else {
				for(Station s : listStopsBySubway) {
					if(stations.contains(getStationByName(s.getName()))) {
						getStationByName(s.getName()).getIds().addAll(s.getIds());
					}
					else {
						stations.add(s);
					}
				}
			}
		}
	}
	
	/**
	 * Add the position of each stop inside subways.
	 * @param tab
	 * @throws IOException
	 */
	public void addEdges(String tab[]) throws IOException {
		for(String str : tab) {
			readFile = new ReadAndParseFilesService();
			List<String> lines = readFile.read("documents/RATP_GTFS_METRO_" + str + "/stop_times.txt");
			Subway sub = getSubwayByName(nameSubway, "Metro_"+str);
			readFile.parseStopTimesFile(lines,str,sub);
		}
	}
	
	/**
	 * createAndgetListOfEdge()
	 * @param edge
	 * @param idFrom
	 * @param idTo
	 */
	public static void createEdges(Long idFrom, Long idTo) {
		Edge edge = new Edge();
		String nameStopFrom = getStationById(idFrom).getName();
		String nameStopTo = getStationById(idTo).getName();
		Double weight =  getWeightBetweenToStops(getStationById(idFrom),getStationById(idTo));
				
		if(nameStopFrom != nameStopTo) {
			edge.setFrom(nameStopFrom);
			edge.setTo(nameStopTo);
			edge.setWeight(weight);
			getListOfWeight().add(weight);
		} 
		
		if(!getEdges().contains(edge)) {
			getEdges().add(edge);
		}
	}
	
	/**
	 * getWeightBetweenToStops()
	 * @param from
	 * @param to
	 * @return dist
	 */
	public static Double getWeightBetweenToStops(Station from, Station to) {
		Double dist = null;
		Double lat1 = from.getLatitude();
		Double lng1 = from.getLongitude();
		Double lat2 = to.getLatitude();
		Double lng2 = to.getLongitude();
		
		if(isWeigtedGraph() == true) {
			double earthRadius = 6371000; //meters
			double dLat = Math.toRadians(lat2-lat1);
			double dLng = Math.toRadians(lng2-lng1);
			double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			           Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
			           Math.sin(dLng/2) * Math.sin(dLng/2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			dist = earthRadius * c;
		} else {
			dist = 0.0;
		}
		return dist;
	}
	
	/**
	 * addNeighborsToStations()
	 */
	public static void addNeighborsToStations() {
		List<Edge> allEdges = getEdges();
		for(Edge e : allEdges) {
			Station stopFrom = getStationByName(e.getFrom());
			Station stopTo = getStationByName(e.getTo());
			
			if(stopFrom.getNeighbors().isEmpty() || !stopFrom.getNeighbors().contains(stopTo.getName()))  {
				stopFrom.getNeighbors().add(stopTo.getName());				
			}
		}
	}
	
	/**
	 * getSubwayByName()
	 * @param map
	 * @param name
	 * @return
	 */
	public Subway getSubwayByName(Map<String, Subway> map, String name) {
		return map.get(name);
	}
	
	/**
	 * getListStations()
	 * @return stops
	 */
	public static List<Station> getListStations() {
		return stations;
	}
	
	/**
	 * getListSubways()
	 * @return subways
	 */
	public static ArrayList<Subway> getListSubways() {
		return subways;
	}
	
	/**
	 * getNumberOfStopInParis()
	 * @return nodesCount
	 */
	public static int getNumberOfStopInParis() {
		nodesCount = getListStations().size();
		return nodesCount;
	}
	
	/**
	 * getNumberOfEdges()
	 * @return edgesCount
	 */
	public static int getNumberOfEdges() {
		edgesCount = getEdges().size();
		return edgesCount;
	}

	/**
	 * getEdges()
	 * @return edges
	 */
	public static List<Edge> getEdges() {
		return edges;
	}

	/**
	 * setEdges()
	 * @param edges
	 */
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	/**
	 * getStopById()
	 * @param id
	 * @return
	 */
	
	public static Station getStationById(Long id) {
		Station stop = null;
		List<Station> allStops = getListStations();
		for(Station s : allStops) {
			if(s.getIds().contains(id)) {
				stop = s;
			}
		}
		return stop;
	}
	
	/**
	 * getStopByName()
	 * @param name
	 * @return
	 */
	public static Station getStationByName(String name) {
		Station stop = null;
		List<Station> allStops = getListStations();
		for(Station s : allStops) {
			String name2 = s.getName();
			if(name2.equals(name)) {
				stop = s;
			}
		}
		return stop;
	}

	/**
	 * isWeigtedGraph()
	 * @return
	 */
	public static boolean isWeigtedGraph() {
		return isWeigtedGraph;
	}

	/**
	 * setWeigtedGraph()
	 * @param isWeigtedGraph
	 */
	public static void setWeigtedGraph(boolean isWeigtedGraph) {
		NetworkService.isWeigtedGraph = isWeigtedGraph;
	}

	/**
	 * getListOfWeight()
	 * @return listOfWeight
	 */
	public static List<Double> getListOfWeight() {
		return listOfWeight;
	}
	
	/**
	 * getEdgeWithFromAndTo()
	 * @param from
	 * @param to
	 * @return edge
	 */
	public static Edge getEdgeWithFromAndTo(String from, String to) {
		Edge edge = null;
		for(Edge e : getEdges()) {
			if(e.getFrom().equals(from) && e.getTo().equals(to)) {
				edge = e;
			}
		}
		return edge;
	}
	
	/**
	 * getEdgeByOneOfVertices()
	 * @param stopName
	 * @return edges
	 */
	public List<Edge> getEdgeByOneOfVertices(String stopName) {
		List<Edge> edges = new ArrayList<Edge>();
		for(Edge e : getEdges()) {
			if(e.getFrom().equals(stopName)) {
				edges.add(e);
			}
		}
		return edges;
	}
}
