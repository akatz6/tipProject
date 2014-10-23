package com.example.tipcalculator;

import android.widget.SeekBar;

public class BusineesDelegateTipSplitting {

	private static double changeTotalTipAmount =0;
	
	
	public static void sendSeekBarInfo(SeekBar num, int changeInValue) {
		useLookUp();
		SeekBar barChanged = num;
		int newValueInSeekBar = changeInValue;
		BusinessLogForTipSplitting.changeValueOfTip(barChanged , newValueInSeekBar);
	}
	
	public static void sendNewTipAmount(double newTotalValueOfTips, double numberOfGuests) {
		useLookUp();
		changeTotalTipAmount = newTotalValueOfTips;
		BusinessLogForTipSplitting.newTotalTipAmount(changeTotalTipAmount, numberOfGuests);		
	}

	private static void useLookUp()
	{
		LookUpServiceForMainActivity.getInstance("TipSplit"); 
	}
}
