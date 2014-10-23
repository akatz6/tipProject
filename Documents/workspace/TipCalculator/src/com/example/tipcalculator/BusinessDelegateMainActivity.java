package com.example.tipcalculator;

public class BusinessDelegateMainActivity {
	
	private static double staticNumGuests;
	private static double staticBillTotal;
	private static double staticDeductions;
	private static double staticTax;
	private static double staticStars;
	
	
	public static void getVariableFromMainActivity(double variableFromUI, Text text) {
		//if look up service has been used
		LookUpServiceForMainActivity.getInstance("MainScreen");
		
		switch(text)
		{
		case NUMGUESTS:
			staticNumGuests = variableFromUI;
			BusinessLogicForMainScreen.getDataForBusinessUI(staticNumGuests, TextForBusinesLevel.NUMGUESTS);
			FacadeShareUI.setNumGuests(staticNumGuests);
			break;
		case BILL:
			staticBillTotal = variableFromUI;
			BusinessLogicForMainScreen.getDataForBusinessUI(staticBillTotal, TextForBusinesLevel.BILL);
			break;
		case TAX:
			staticTax = variableFromUI;
			BusinessLogicForMainScreen.getDataForBusinessUI(staticTax, TextForBusinesLevel.TAX);
			break;
		case STARS:
			staticStars = variableFromUI;
			BusinessLogicForMainScreen.getDataForBusinessUI(staticStars, TextForBusinesLevel.STARS);
			break;
		default:
			staticDeductions = variableFromUI;
			BusinessLogicForMainScreen.getDataForBusinessUI(staticDeductions, TextForBusinesLevel.DEDUCTIONS);
			break;			
		}
	}
	
	public static double getGuests()
	{
		return staticNumGuests;
	}
}
