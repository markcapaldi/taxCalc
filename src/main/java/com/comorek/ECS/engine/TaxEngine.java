package com.comorek.ECS.engine;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.comorek.ECS.model.TaxBand;
import com.comorek.ECS.model.TaxYear;

public class TaxEngine implements TaxInterface {
	private static final int MAGIC_NUMBER = 1;	// not sure why but in the example this number was used
	
	// simple singleton construct
	private static TaxEngine engine = new TaxEngine();
	// List of years, currently there ane not many years. If the number of years grows the structure may be altered for fast read.
	private List<TaxYear> taxYears;
	
	/**
	 * The static getter of the singleton instance.
	 * @return The instance of this.
	 */
	public static TaxEngine getInstance() {
		return engine;
	}
	
	/**
	 * The main calculation implementation. Does calculate the tax and returns structured data.
	 * @see com.comorek.ECS.engine.TaxInterface#calcYear(java.time.Year, double)
	 */
	public ResultTax calcYear(Year year, double amount) throws IllegalArgumentException  {
		ResultTax resultTax = null;
		if(amount <= 0) {
			throw new IllegalArgumentException("The income to be taxed must be greater than zero.");
		}
		if(year == null) {
			throw new IllegalArgumentException("The year for the tax calculations cannot be null.");
		}
		
		for(TaxYear taxYear : taxYears) {
			if(taxYear.getTaxYear().equals(year)) {
				resultTax = new ResultTax();
				double allowance = taxYear.getAllowance();
				
				amount -= allowance;	// taxable income
				resultTax.setAllowance(amount >= 0 ? allowance : allowance + amount);
				
				double previousTaxedAmount = 0.0d;
				for(Map.Entry<Integer, TaxBand> entry : taxYear.getTaxBands().entrySet()) {
					if(amount <= 0) break;	// break when there is nothing left to be taxed
					
					double toBeTaxed = entry.getKey() - previousTaxedAmount;
					amount -= toBeTaxed;	// the remainder of income to be taxed
					previousTaxedAmount += toBeTaxed + MAGIC_NUMBER;
					
					toBeTaxed = amount > 0 ? toBeTaxed : toBeTaxed + amount;
					
					double tax = toBeTaxed * entry.getValue().getRate();
					
					resultTax.getBands().add(entry.getValue().getName());
					resultTax.getAmounts().add(toBeTaxed);
					resultTax.getRates().add(entry.getValue().getRate());
					resultTax.getTaxes().add(tax);
					resultTax.addToTotalTax(tax);
				}
				break;
			}
		}
		if(resultTax == null) {
			throw new IllegalArgumentException("There are no entries for this year.");
		};
		
		return resultTax;
	}
	
	public List<Year> getYears() {
		ArrayList<Year> result = new ArrayList<Year>(taxYears.size());
		for(TaxYear y : taxYears) {
			result.add(y.getTaxYear());
		}
		return result;
	}
	
	public double getAllowance(Year year) throws IllegalArgumentException {
		double allowance = -1;
		
		for(TaxYear y : taxYears) {
			if(y.getTaxYear().equals(year)) {
				allowance = y.getAllowance();
				break;
			}
		}
		if (allowance == -1) {
			throw new IllegalArgumentException("There are no entries for this year.");
		}
		
		return allowance;
	}
	
	private TaxEngine() {
		init();
	}
	
	private void init() {
		defaultSetup();
	}
	
	/*
	 * Creates the data as specified in the example.
	 */
	private void defaultSetup() {
		TaxBand b1 = new TaxBand(0.19, "Starter rate");
		TaxBand b2 = new TaxBand(0.20, "Basic rate");
		TaxBand b3 = new TaxBand(0.21, "Intermediate rate");
		TaxBand b4 = new TaxBand(0.40, "Higher rate");
		TaxBand b5 = new TaxBand(0.46, "Top rate");
		
		TaxYear ty1 = new TaxYear(10600, Year.parse("2015"));
		TaxYear ty2 = new TaxYear(11000, Year.parse("2016"));
		TaxYear ty3 = new TaxYear(11500, Year.parse("2017"));
		TaxYear ty4 = new TaxYear(11850, Year.parse("2018"));
		
		// in first three years the taxes are limited on £150 000
		ty1.addBand(b2, 31785);
		ty1.addBand(b4, 150000);
		
		ty2.addBand(b2, 32000);
		ty2.addBand(b4, 150000);
		
		ty3.addBand(b2, 31500);
		ty3.addBand(b4, 150000);
		
		ty4.addBand(b1, 2000);
		ty4.addBand(b2, 12150);
		ty4.addBand(b3, 31580);
		ty4.addBand(b4, 150000);
		ty4.addBand(b5, Integer.MAX_VALUE);
		
		taxYears = new ArrayList<TaxYear>();
		taxYears.add(ty1);
		taxYears.add(ty2);
		taxYears.add(ty3);
		taxYears.add(ty4);
		
	}

}
