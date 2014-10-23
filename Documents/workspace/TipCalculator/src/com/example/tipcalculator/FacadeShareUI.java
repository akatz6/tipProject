package com.example.tipcalculator;


public class FacadeShareUI {
	private static double numGuests = 0;
	
	public static void setNumGuests(double staticNumGuests)
	{
		numGuests = staticNumGuests;
	}
	
	public static double getNumGuests()
	{
		return numGuests;
	}
}
