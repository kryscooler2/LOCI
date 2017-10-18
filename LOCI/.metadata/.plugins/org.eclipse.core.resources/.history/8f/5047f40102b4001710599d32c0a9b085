package com.loci.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loci.object.Edge;
import com.loci.object.Station;
import com.loci.object.Subway;
import com.loci.services.Dijkstra;
import com.loci.services.NetworkService;

@RestController
@RequestMapping("/subway")
public class StationAndSubwayController {
	
	@Autowired
	private NetworkService networkService;
	
	@Autowired
	private Dijkstra djDijkstra;
	
	Boolean isWeightedGraph = true;
	
	
	public final static String  tab[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","3b","7b"};
	
	@GetMapping("/stations")
	public List<Station> getStations() throws IOException {
		return networkService.getAllStations(tab, isWeightedGraph);
	}
	
	@GetMapping("/liens")
	public List<Edge> getEdges() throws IOException {
		return networkService.getAllEdges(tab, isWeightedGraph);
	}
	
	@GetMapping("/SP/{from}/{to}")
	public List<Station> getSPBetweenToStations(@PathVariable String from, @PathVariable String to) throws IOException {
		networkService.createAllTheNetwork(tab, isWeightedGraph);
		return djDijkstra.getSPBetweenToStations(from, to);
	}
	
	@GetMapping("/lines")
	public ArrayList<Subway> getSubways() throws IOException {
		return networkService.getAllSubways(tab, isWeightedGraph);
	}
	
	
	
}
