package com.loci.object;

import java.io.Serializable;

public class Edge implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String from;
	private String to;
	private Double weight;

	/**
	 * getWeight()
	 * @return weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * setWeight()
	 * @param weight
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * getFrom()
	 * @return from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * setFrom()
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * getTo()
	 * @return to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * setTo()
	 * @param to
	 */
	public void setTo(String to) {
		this.to = to;
	}

}
