package com.comorek.ECS.engine;

import java.util.ArrayList;

/**
 * The output class of the TaxInterface containing all the results of the tax calculations in simple format.
 * TODO: arraylists should be turned into one collection of combined entry for better usability.
 */
public class ResultTax {
	private double allowance = 0;
	private double totalTax  = 0;
	private ArrayList<String> bands = new ArrayList<String>();
	private ArrayList<Double> amounts = new ArrayList<Double>();
	private ArrayList<Double> rates = new ArrayList<Double>();
	private ArrayList<Double> taxes = new ArrayList<Double>();
	
	public double getAllowance() {
		return allowance;
	}
	public void setAllowance(double allowance) {
		this.allowance = allowance;
	}
	public double getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}
	public void addToTotalTax(double taxAddition) {
		totalTax += taxAddition;
	}
	public ArrayList<String> getBands() {
		return bands;
	}
	public ArrayList<Double> getAmounts() {
		return amounts;
	}
	public ArrayList<Double> getRates() {
		return rates;
	}
	public ArrayList<Double> getTaxes() {
		return taxes;
	}
	
}
