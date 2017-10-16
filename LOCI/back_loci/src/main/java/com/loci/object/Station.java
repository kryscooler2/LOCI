package com.loci.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Station implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private List<Long> ids;
	private String name;
	private String description;
	private Double latitude;
	private Double longitude;
	
	public List<String> neighbors;
	
	public Station() {
		neighbors = new ArrayList<String>();
	}

	public List<Long> getIds() {
		return ids;
	}
	
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	

	public List<String> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<String> neighbors) {
		this.neighbors = neighbors;
	}
}

