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
	private Dijkstra dijkstra;
	
	
	@GetMapping("/stations")
	public List<Station> getStations() throws IOException {
		return networkService.getAllStations();
	}
	
	@GetMapping("/edges")
	public List<Edge> getEdges() throws IOException {
		return networkService.getAllEdges();
	}
	
	@GetMapping("/SP/{from}/{to}")
	public List<Station> getSPBetweenToStations(@PathVariable String from, @PathVariable String to) throws IOException {
		return dijkstra.getSPBetweenToStations(from, to);
	}
	
	@GetMapping("/lines")
	public ArrayList<Subway> getSubways() throws IOException {
		return networkService.getAllSubways();
	}
	
	@GetMapping("/lines/{subwayName}")
	public Subway getSubwayByName(@PathVariable String subwayName) throws IOException {
		return networkService.getSubwayByName(subwayName);
	}
	
	@GetMapping("/stationsNames")
	public ArrayList<String> getStationsNames() throws IOException {
		return networkService.getAllStationsName();
	}
	
	
	
}
