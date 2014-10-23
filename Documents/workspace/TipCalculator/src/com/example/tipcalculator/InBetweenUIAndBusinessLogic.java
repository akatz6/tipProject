package com.example.tipcalculator;
import com.example.tipcalculator.BusinessLogicForMainScreen;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

public class InBetweenUIAndBusinessLogic extends Activity {
	

	private static double staticNumGuests;
	private static double totalTipAmount;
	private static double tipAmountPerPerson;
	private static double totalAmountForBill;
	private static double tipRate;
	private static double changeTotalTipAmount;
	private static double newTotalTipAmount;
	

	
	public static void getDataToPopulateTextFielsInMainActivity(double dataToPopulateTextBoxesInMainActivity,
			TextForBusinesLevel textForBusinessLevel) {
		switch(textForBusinessLevel)
		{
		case TIPRATE:
			tipRate = dataToPopulateTextBoxesInMainActivity;
			MainActivity.setDataInTextBoxes(tipRate, Text.TIPRATE);
			break;
		case TOTALTIP:
			totalTipAmount = dataToPopulateTextBoxesInMainActivity;
			MainActivity.setDataInTextBoxes(totalTipAmount , Text.TOTALTIP);
			BusinessLogForTipSplitting.getDataForTipSplitUI(totalTipAmount, Text.TOTALTIP);
			break;
		case TIPPERPERSON:
			tipAmountPerPerson = dataToPopulateTextBoxesInMainActivity;
			MainActivity.setDataInTextBoxes(tipAmountPerPerson, Text.TIPPERPERSON);
			break;
		default:
			totalAmountForBill = dataToPopulateTextBoxesInMainActivity;
			MainActivity.setDataInTextBoxes(totalAmountForBill, Text.TOTALBILL);
			break;			
		}
		
	}
	
	public static double getTipPercentage()
	{
		return tipRate;
	}
	
	public static double getTipPerPerson()
	{
		return tipAmountPerPerson;
	}
	
	public static double getGuests()
	{
		return staticNumGuests;
	}

	public static void getNewAmountOfTip(SeekBar num, double newTipAmount) {
		SeekBar barChanged = num;
		double amountForTip = newTipAmount;
		TipSplittingScreen.setNewTipAmount(barChanged,  amountForTip);
	}


	public static void establishNewTipAmount(double changeTotalTipAmount, double numberOfGuests) {
		// TODO Auto-generated method stub
		getDataToPopulateTextFielsInMainActivity(changeTotalTipAmount, TextForBusinesLevel.TOTALTIP);
		getDataToPopulateTextFielsInMainActivity((changeTotalTipAmount/numberOfGuests), TextForBusinesLevel.TIPPERPERSON);
	}
}