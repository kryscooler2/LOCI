package com.loci.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.loci.object.Edge;
import com.loci.object.Station;
import com.loci.services.NetworkService;

@Service
public class Dijkstra {
	
	
	private HashMap<String, Boolean> marked;
	private HashMap<String, String> previous;
	private HashMap<String, Double> distance;
	
	public Boolean hasPathTo(String v) {
        return getMarked().get(v);
    }
	
	public Double distTo(String v) {
        return getDistance().get(v);
    }
	
	public HashMap<String, Boolean> getMarked() {
		return marked;
	}
	
	public void setMarked(HashMap<String, Boolean> marked) {
		this.marked = marked;
	}
	
	public HashMap<String, String> getPrevious() {
		return previous;
	}
	
	public void setPrevious(HashMap<String, String> previous) {
		this.previous = previous;
	}
	
	public HashMap<String, Double> getDistance() {
		return distance;
	}
	
	public void setDistance(HashMap<String, Double> distance) {
		this.distance = distance;
	}
	
	public ArrayList<Station> createSPBetweenToStations(String v, String sommetNode) {
		ArrayList<Station> nodeListOfPath = new ArrayList<Station>();
		String currentNode = v;  
		nodeListOfPath.add(NetworkService.getStationByName(currentNode));  
		
		if (hasPathTo(currentNode)) {
			while(!currentNode.equals(sommetNode)) {			
				currentNode = getPrevious().get(currentNode); 
				nodeListOfPath.add(NetworkService.getStationByName(currentNode));
			}
		}
		return nodeListOfPath;
	}
	
	public ArrayList<Station> dijkstraSP(String s, String to) throws IOException {
		setMarked(new HashMap<String, Boolean>());
			setPrevious(new HashMap<String, String>());
			setDistance(new HashMap<String, Double>());
			getDistance().put(s, 0.0);
			Queue<String> q = new LinkedList<String>();
			List<String> visited = new ArrayList<String>();
			q.add(s);
			while(!q.isEmpty()) {
				String currentVisit = q.poll();
				List<String> neighbors = NetworkService.getStationByName(currentVisit).getNeighbors();
				visited.add(currentVisit);
				getMarked().put(currentVisit, true);
				if(neighbors != null) {
					for(String neighbor : neighbors) {
						if(!visited.contains(neighbor)) {
							Edge e = NetworkService.getEdgeWithFromAndTo(currentVisit, neighbor);
							if(getDistance().get(neighbor) == null || getDistance().get(neighbor) > getDistance().get(currentVisit) + e.getWeight()) {
								getPrevious().put(neighbor, currentVisit);
								getDistance().put(neighbor, getDistance().get(currentVisit) + e.getWeight());
								q.offer(neighbor);
							}
						}
					}
				}	
			}

		return createSPBetweenToStations(to, s);
	}
	
	public ArrayList<Station> getSPBetweenToStations(String s, String to) throws IOException {	
		return dijkstraSP(s,to);
	}
}
