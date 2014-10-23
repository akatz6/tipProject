package com.example.tipcalculator;

public class LookUpServiceForMainActivity {
	private static LookUpServiceForMainActivity lookUpServiceForMainActivity = null;

	protected LookUpServiceForMainActivity() {

	}

	public static LookUpServiceForMainActivity getInstance(String mainScreen) {
		if (lookUpServiceForMainActivity == null) {
			lookUpServiceForMainActivity = new LookUpServiceForMainActivity();
		}
		if (mainScreen.equals("mainScreen")) {
			BusinessLogicForMainScreen.getInstance();
		}
		else if(mainScreen.equals("TipSplit"))
		{
			BusinessLogForTipSplitting.getInstance();
		}
		return lookUpServiceForMainActivity;
	}
}
