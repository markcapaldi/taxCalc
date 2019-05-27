package com.comorek.ECS.model;

/**
 * Simple POJO class representing a tax band.
 *
 */
public class TaxBand {
	/**
	 * The tax rate for this band.
	 */
	private double rate;
	/**
	 * The name of the band.
	 */
	private String name;
	
	public TaxBand(double rate, String name) {
		super();
		this.rate = rate;
		this.name = name;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
