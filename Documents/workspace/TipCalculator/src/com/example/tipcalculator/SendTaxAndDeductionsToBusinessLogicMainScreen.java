package com.example.tipcalculator;

import java.io.IOException;

public class SendTaxAndDeductionsToBusinessLogicMainScreen {

	private static boolean taxAdd = false;
	private static boolean deduct = false;
	private static String newMin = "";
	private static String newMax = "";
	private static boolean minNew = false;
	private static boolean maxNew = false;
	
	
	public static boolean useTaxWhenCalcTip()
	{
		try {
			taxAdd = AddTaxToTip.addTax();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taxAdd;
	}
	
	public static boolean useDeductionsWhenCalcTip()
	{
		try {
			deduct  = AddTaxToTip.subtractDecuctions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deduct;
	}
	
	public static boolean newMin()
	{
		try {
			newMin = AddTaxToTip.getMinTip();
			minNew = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return minNew;		
	}
	
	public static boolean newMax()
	{
		try {
			newMax = AddTaxToTip.getMaxTip();
			maxNew = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxNew;		
	}
	
	public static String maxChanged()
	{
		return newMax;
	}
	
	public static String minChanged()
	{
		return newMin;
	}
	
}
