package com.loci.object;

import java.io.Serializable;
import java.util.List;

public class Subway implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private String name;
	
	
	private List<Station> stations;
	private int numberOfStops;
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Station> getStops() {
		return stations;
	}
	
	public void setStops(List<Station> stations) {
		this.stations = stations;
	}
	
	public int getNumberOfStops() {
		return numberOfStops;
	}
	
	public void setNumberOfStops(int numberOfStops) {
		this.numberOfStops = numberOfStops;
	}

}
