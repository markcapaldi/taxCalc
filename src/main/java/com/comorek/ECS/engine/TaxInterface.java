package com.comorek.ECS.engine;

import java.time.Year;
import java.util.List;

/**
 * The interface for tax calculations.
 * Not really needed in this simple program. But useful if the code gets extended. For example modularisation of the code with OSGI.
 */
public interface TaxInterface {

	/**
	 * The function that executes the tax calculations and throws an exception if there is invalid input combination.
	 * @param year The year for which the tax is calculated.
	 * @param amount The amount to be taxed.
	 * @return The result structured data.
	 * @throws IllegalArgumentException In the case of invalid inputs.
	 */
	public ResultTax calcYear(Year year, double amount) throws IllegalArgumentException;
	
	/**
	 * Function that returns the years this program knows.
	 * @return The list of years taxes can be calculated for.
	 */
	public List<Year> getYears();
	
	/**
	 * Function that returns the allowance for given year. Throws an exception if there is no entry for the input year.
	 * @param year The year of when the allowance is queried.
	 * @return The allowance of the input year.
	 * @throws IllegalArgumentException In the case of missing year in the data set.
	 */
	public double getAllowance(Year year) throws IllegalArgumentException;
}
