package com.comorek.ECS;

import java.nio.charset.Charset;
import java.time.Year;

import com.comorek.ECS.engine.ResultTax;
import com.comorek.ECS.engine.TaxEngine;
import com.comorek.ECS.text.Formatter;

/**
 * Hello world!
 *
 */
public class App {
	private static final String HELP_COMMAND = "help"; 
	private static final String UNKNOWN_CHARACTER = "¯\\_(ツ)_/¯";
	
	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.out.println("Add input arguments or \"HELP\" for more info");
		} else if(args[0].equalsIgnoreCase(HELP_COMMAND)) {
			help(args);
		} else {
			String errString = null;
			
			// THE MAIN EXECUTION PART
			try {
				Year y = Year.parse(args[0]);
				double income = Double.parseDouble(args[1]);
				
				ResultTax rt = TaxEngine.getInstance().calcYear(y, income);
				
				String output = Formatter.producePlainTextMessage(rt, y, income);
				
				System.out.println(output);
				
			// INPUTS EXCEPTION HANDLING
			} catch (NumberFormatException numberException) {
				errString = "The input is not a valid number : \"" + args[1] + "\" " + UNKNOWN_CHARACTER;
				// log the exception - some logging library if extended
				errString += System.lineSeparator() + numberException.getMessage();
			} catch (IllegalArgumentException argumentException) {
				errString = "Invalid input parameters: \"" + args[0] + "\" \"" + args[1] + "\" " + UNKNOWN_CHARACTER;
				// log the exception - some logging library if extended
				errString += System.lineSeparator() + argumentException.getMessage();
			} catch (Exception e) {
				errString = "Something went wrong. Blame the author of this code. " + UNKNOWN_CHARACTER;
				// log the exception - some logging library if extended
				errString += System.lineSeparator() + e.getMessage();
			} finally {
				if(errString != null) {
					Charset.forName("UTF-8").encode(errString);
					System.out.println(errString);
					System.out.println("If not sure please use the \"help\" argument to see valid options.");
				};
			}
		}
	}
	
	private static void help(String[] args) {
		String line1 = "This JAR archive takes arguments representing the tax year and the income you want to calculate.";
		String line2 = "The list of valid tax years is following:";
		String line3 = "Hope this helps";

		System.out.println(line1);
		System.out.println(line2);
		for(Year y : TaxEngine.getInstance().getYears()) {
			System.out.println(y.toString() + " for tax year: " + y.toString() + "-" + y.plusYears(1).toString());
		}
		System.out.println(System.lineSeparator() + line3);
	}
}
