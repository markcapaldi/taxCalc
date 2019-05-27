package com.comorek.ECS.engine;

import java.time.Year;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class TaxEngineTest extends TestCase {
	private static final double TOLERANCE = 0.0001;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	// the assignment example
	@Test
	public void testCalcYear1() {
		final double allowance = 11850;
		final double[] amounts = {2000, 10149, 19429, 72};
		final double[] rates = {0.19, 0.2, 0.21, 0.4};
		final double[] taxes = {380, 2029.8, 4080.09, 28.8};
		final double totalTaxExpected = 6518.69;
		
		Year y = Year.parse("2018");
		double income = 43500;
		double finalTax = 0.0d;

		ResultTax rt = TaxEngine.getInstance().calcYear(y, income);
		
		assertEquals(rt.getAllowance(), TaxEngine.getInstance().getAllowance(y));
		assertEquals(rt.getAllowance(), allowance);
		for(int i = 0; i < rt.getAmounts().size(); i++) {
			assertEquals(rt.getAmounts().get(i), amounts[i], TOLERANCE);
			assertEquals(rt.getRates().get(i), rates[i], TOLERANCE);
			assertEquals(rt.getTaxes().get(i), taxes[i], TOLERANCE);
			finalTax += rt.getTaxes().get(i);
		}
		assertEquals(finalTax, totalTaxExpected, TOLERANCE);
		assertEquals(rt.getTotalTax(), totalTaxExpected, TOLERANCE);
	}
	
	@Test
	public void testCalcYear2() {
		final double totalTaxExpected = 53642.6;
		
		Year y = Year.parse("2015");
		double income = Double.MAX_VALUE;
		
		ResultTax rt = TaxEngine.getInstance().calcYear(y, income);
		
		assertEquals(rt.getAllowance(), TaxEngine.getInstance().getAllowance(y));
		//This one is weird to me. The total to be taxed is lower due to the magic number in the tax engine. Must be something British.
		assertEquals(rt.getTotalTax(), totalTaxExpected, TOLERANCE);
		
	}
	
	@Test
	public void testCalcYear3() {
		Year y = Year.parse("2016");
		double income = -1;
		
		//thrown.expect(IllegalArgumentException.class); TODO this should work...
		try {
			TaxEngine.getInstance().calcYear(y, income);
		} catch (IllegalArgumentException e) {
			assertTrue("The correct exception was captured.", e.getMessage().equalsIgnoreCase("The income to be taxed must be greater than zero."));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testCalcYear4() {
		Year y = Year.parse("2017");
		double income = 100;
		
		ResultTax rt = TaxEngine.getInstance().calcYear(y, income);
		assertEquals(rt.getAllowance(), income);
	}
	
	@Test
	public void testCalcYear5() {
		final double expectedTax = 53699.6;
		
		Year y = Year.parse("2017");
		double income = 200000;
		
		ResultTax rt = TaxEngine.getInstance().calcYear(y, income);
		
		assertEquals(rt.getTotalTax(), expectedTax, TOLERANCE);
	}

}
