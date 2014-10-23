package com.example.tipcalculator;

import android.widget.SeekBar;

public class BusinessLogForTipSplitting {
	
	static double totalTip  = 0;
	
	private static BusinessLogForTipSplitting businessLogForTipSplitting = null;
	
	protected BusinessLogForTipSplitting()
	{
		
	}
	
	public static BusinessLogForTipSplitting getInstance()
	{
		if(businessLogForTipSplitting  == null)
		{
			businessLogForTipSplitting  = new  BusinessLogForTipSplitting();
		}
		return businessLogForTipSplitting;
	}

	public static void changeValueOfTip(SeekBar num, int changeInValue) {
		double newTipAmount = totalTip  * (((double)changeInValue/100));
		InBetweenUIAndBusinessLogic.getNewAmountOfTip(num, newTipAmount);
	}

	public static void getDataForTipSplitUI(double staticTotalTip,
			Text totaltip) {
		totalTip = staticTotalTip;	
	}

	public static void newTotalTipAmount(double changeTotalTipAmount, double numberOfGuests) {
		if(changeTotalTipAmount > 0.0)
			InBetweenUIAndBusinessLogic.establishNewTipAmount(changeTotalTipAmount, numberOfGuests);
	}

}
