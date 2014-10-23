package com.example.tipcalculator;

public class BusinessLogicForMainScreen {
	
	private static double staticNumGuests;
	private static double staticBillTotal;
	private static double staticDeductions;
	private static double staticTax;
	private static double staticStars;
	private static double tipRate;
	private static double tipPercent;
	private static double tipPerPerson;
	private static double totalTip;
	private static double totalBill;
	private static BusinessLogicForMainScreen businessLogicForMainScreen = null;
	
	protected BusinessLogicForMainScreen()
	{
		
	}
	
	public static BusinessLogicForMainScreen getInstance()
	{
		if(businessLogicForMainScreen  == null)
		{
			 businessLogicForMainScreen  = new  BusinessLogicForMainScreen();
		}
		return businessLogicForMainScreen;
	}
	
	
	public static void getDataForBusinessUI(double doublesFromUI, TextForBusinesLevel textForBusinesLevel) {
		switch(textForBusinesLevel)
		{
		case NUMGUESTS:
			staticNumGuests = doublesFromUI;
			break;
		case BILL:
			staticBillTotal = doublesFromUI;
			break;
		case TAX:
			staticTax = doublesFromUI;
			break;
		case STARS:
			staticStars = doublesFromUI;
			break;
		default:
			staticDeductions = doublesFromUI;
			// TODO find better place to put this
			startBusinessLogic();
			break;			
		}
	}
	
	
	private static void startBusinessLogic() {
		setTipRate();
		setTotalTip();
		setTipPerPerson();
		setTotalBill();
		
	}

	private static void setTotalBill() {
		totalBill = staticBillTotal - staticDeductions + staticTax + totalTip;
		InBetweenUIAndBusinessLogic.getDataToPopulateTextFielsInMainActivity(totalBill, TextForBusinesLevel.TOTALBILL);
	}
	

	private static void setTipPerPerson() {
		tipPerPerson = totalTip/staticNumGuests;
		InBetweenUIAndBusinessLogic.getDataToPopulateTextFielsInMainActivity(tipPerPerson, TextForBusinesLevel.TIPPERPERSON);
		
	}

	private static void setTotalTip() {
		totalTip = staticBillTotal * tipPercent;
		if(SendTaxAndDeductionsToBusinessLogicMainScreen.useTaxWhenCalcTip())
		{
			totalTip += (staticTax * tipPercent);
		}
		if(SendTaxAndDeductionsToBusinessLogicMainScreen.useDeductionsWhenCalcTip())
		{
			totalTip -= (staticDeductions * tipPercent);
		}
		totalTip /= 100;
		InBetweenUIAndBusinessLogic.getDataToPopulateTextFielsInMainActivity(totalTip, TextForBusinesLevel.TOTALTIP);		
	}

	private static void setTipRate() {
		// TODO Add tip rate change
		double max = 20.0;
		double min = 0.0;
		if(SendTaxAndDeductionsToBusinessLogicMainScreen.newMin())
		{
			min = Double.parseDouble(SendTaxAndDeductionsToBusinessLogicMainScreen.minChanged());
		}
		if(SendTaxAndDeductionsToBusinessLogicMainScreen.newMax())
		{
			max = Double.parseDouble((SendTaxAndDeductionsToBusinessLogicMainScreen.maxChanged()));
		}
		tipRate = max - min;
		tipPercent = (float) ((tipRate *(staticStars/4)) + min);
		InBetweenUIAndBusinessLogic.getDataToPopulateTextFielsInMainActivity(tipPercent, TextForBusinesLevel.TIPRATE);		
		
	}

	public static void sendTotalTip() {
		// TODO Auto-generated method stub
		startBusinessLogic();		
	}
	
}
