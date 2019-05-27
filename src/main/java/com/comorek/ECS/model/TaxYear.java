package com.comorek.ECS.model;

import java.time.Year;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Simple POJO class representing different tax years.
 *
 */
public class TaxYear {
	/**
	 * The first map argument represents the upper limit. Assuming the tax rates are ordered(SortedMap interface).
	 */
	private SortedMap<Integer, TaxBand> bands = new TreeMap<Integer, TaxBand>();
	/**
	 * Allowance valid in this year.
	 */
	private double allowance;
	/**
	 * The time entry of this tax year.
	 */
	private Year taxYear;
	
	public TaxYear(int allowance, Year taxYear) {
		super();
		this.allowance = allowance;
		this.taxYear = taxYear;
	}
	public void addBand(TaxBand band, int limit) {
		bands.put(limit, band);
	}
	public SortedMap<Integer, TaxBand> getTaxBands() {
		return new TreeMap<Integer, TaxBand>(bands);
	}
	public double getAllowance() {
		return allowance;
	}
	public void setAllowance(double allowance) {
		this.allowance = allowance;
	}
	public Year getTaxYear() {
		return taxYear;
	}
	public void setTaxYear(Year taxYear) {
		this.taxYear = taxYear;
	}
	
}
