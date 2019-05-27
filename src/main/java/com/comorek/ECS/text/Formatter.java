package com.comorek.ECS.text;

import java.text.DecimalFormat;
import java.time.Year;
import com.comorek.ECS.engine.ResultTax;

/**
 * The class designed to format the tax calculations into plain text report.
 * Can be extended for other types of formatting. For example a suitable one would be getting the values separately so the composition logic
 * is up to another layer/module/whatever. This one is just suitable for unformatted static text environment (console).
 */
public class Formatter {
	public static final String CURRENCY = "£";	//different currencies may be considered in the future
	
	public static final DecimalFormat DF2 = new DecimalFormat((CURRENCY + "#,##0.00"));
	public static final DecimalFormat DF0 = new DecimalFormat((CURRENCY + "#,##0"));
	public static final DecimalFormat DF_PERCENT = new DecimalFormat("##0.#%");
	
	/**
	 * This function produces a plain text string with the results of the tax calculations.
	 * 
	 * @param result The tax calculations result to be formatted and printed.
	 * @param year The year for which the calculation was done.
	 * @param income The income that was taxed.
	 * @return The formatted plain text string containing the tax breakdown.
	 */
	public static String producePlainTextMessage(ResultTax result, Year year, double income) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Tax Year: " + year.toString() + "-" + year.plusYears(1).toString() + System.lineSeparator());
		sb.append("Gross salary: " + DF0.format(income) + System.lineSeparator() + System.lineSeparator());
		
		sb.append("Personal Allowance: " + DF2.format(result.getAllowance()) + System.lineSeparator() + System.lineSeparator());
		
		sb.append("Taxable Income: " + DF2.format(income - result.getAllowance()) + System.lineSeparator() + System.lineSeparator());
		
		for(int i = 0; i < result.getAmounts().size(); i++) {
			sb.append(
				result.getBands().get(i) + ": " +
					DF2.format(result.getAmounts().get(i)) +
					" @ " + DF_PERCENT.format(result.getRates().get(i)) +
					" = "+ DF2.format(result.getTaxes().get(i)) +
					System.lineSeparator()
			);
		}
		
		sb.append(System.lineSeparator() + "Total Tax Due: " + DF2.format(result.getTotalTax()));
		
		return sb.toString();
	}
}
