package com.example.tipcalculator;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tipcalculator.AddTaxToTip;
import com.example.tipcalculator.TipSplittingScreen;
import com.example.tipcalculator.InBetweenUIAndBusinessLogic;

public class MainActivity extends Activity {

	private EditText numberOfGuests;
	private final String GUESTS = "Guests Must Be Between 1 and 99";
	private double numGuests;

	private EditText billTotal;
	private final String MONEY = "Only two numbers allowed after decimal";
	private double totalBill;

	private EditText billDeductions;
	private double deductionsToBill;

	private EditText tax;
	private double taxAmount;

	private RatingBar ratingBar;
	private double stars;

	private static double staticNumGuests;
	private static double staticBillTotal;
	private static double staticDeductions;
	private static double staticTax;
	private static double staticStars;

	private static TextView tip;
	private static TextView tipRate;
	private static TextView perPersonTip;
	private static TextView entireBill;
	// double defaultTip;
	// float Rating;
	private Button tipTailoring;
	private final String TOTALTIP = "Tip needs to be calcualted to go to this screen";
	
	private Button configTipItems;

	// Tip_Splitting configTip;
	// boolean subDeductions;
	/*
	 * boolean addTax; String minTip; Float fminTip; Boolean
	 * hasTipBeenPersonalized;
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		addListenerOnRatingBar();
		TextWatcher watcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (numberOfGuests.getText().hashCode() == s.hashCode()
						&& !numberOfGuests.getText().toString().matches("")) {
					numGuests = getTextFromEditText(numberOfGuests);
					checkThatTextIsCorrect(Text.NUMGUESTS, numberOfGuests);
					
				} else if (billTotal.getText().hashCode() == s.hashCode()
						&& !billTotal.getText().toString().matches("")) {
					totalBill = getTextFromEditText(billTotal);
					checkThatTextIsCorrect(Text.BILL, billTotal);
					
				} else if (billDeductions.getText().hashCode() == s.hashCode()
						&& !billDeductions.getText().toString().matches("")) {
					deductionsToBill = getTextFromEditText(billDeductions);
					checkThatTextIsCorrect(Text.DEDUCTIONS, billDeductions);
					
				} else if (tax.getText().hashCode() == s.hashCode()
						&& !tax.getText().toString().matches("")) {
					taxAmount = getTextFromEditText(tax);
					checkThatTextIsCorrect(Text.TAX, tax);
				}
				doesHaveInformationForBusinessLogic();
			}

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

		};
		numberOfGuests.addTextChangedListener(watcher);
		billTotal.addTextChangedListener(watcher);
		billDeductions.addTextChangedListener(watcher);
		tax.addTextChangedListener(watcher);
	}

	private void doesHaveInformationForBusinessLogic() {
		if (!billTotal.getText().toString().matches("")
				&& !tax.getText().toString().matches("")
				&& !numberOfGuests.getText().toString().matches("")) {
			InBetweenUIAndBusinessLogic.getVariableFromMainActivity(
					staticNumGuests, Text.NUMGUESTS);
			InBetweenUIAndBusinessLogic.getVariableFromMainActivity(staticTax,
					Text.TAX);
			InBetweenUIAndBusinessLogic.getVariableFromMainActivity(
					staticBillTotal, Text.BILL);
			InBetweenUIAndBusinessLogic.getVariableFromMainActivity(
					staticStars, Text.STARS);
			if (!billDeductions.getText().toString().matches("")) {
				InBetweenUIAndBusinessLogic.getVariableFromMainActivity(
						staticDeductions, Text.DEDUCTIONS);
			} else {
				InBetweenUIAndBusinessLogic.getVariableFromMainActivity(0,
						Text.DEDUCTIONS);
			}
		}

	}

	private void addListenerOnRatingBar() {

		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				stars = ratingBar.getRating();
				staticStars = stars;
				doesHaveInformationForBusinessLogic();
			}
		});
	}

	private void checkThatTextIsCorrect(Text text, EditText editText) {
		switch (text) {
		case NUMGUESTS:
			if (numGuests < 1 || numGuests > 99) {
				editText.setText("");
				Toast.makeText(MainActivity.this, GUESTS, 5000).show();
			} else {
				staticNumGuests = numGuests;
			}
			break;
		case TAX:
			checkNoMoreThatTwoNumbersAfterDecimal(tax.getText().toString(),
					editText);
			break;
		case DEDUCTIONS:
			checkNoMoreThatTwoNumbersAfterDecimal(billDeductions.getText()
					.toString(), editText);
			break;
		case BILL:
			checkNoMoreThatTwoNumbersAfterDecimal(billTotal.getText()
					.toString(), editText);
			break;
		default:
			break;
		}

	}

	private void checkNoMoreThatTwoNumbersAfterDecimal(final String number,
			final EditText editText) {
		int size = 0;
		int afterDecimal = number.length();
		for (char ch : number.toCharArray()) {
			if (ch == '.') {
				break;

			} else {
				size++;
			}
		}

		if ((afterDecimal - size) > 3) {
			billDeductions.setText("");
			Toast.makeText(MainActivity.this, MONEY, 5000).show();
			editText.setText("");
		} else {
			if (billTotal.getText().hashCode() == editText.getText().hashCode()) {
				staticBillTotal = totalBill;
			} else if (billDeductions.getText().hashCode() == editText
					.getText().hashCode()) {
				staticDeductions = deductionsToBill;
			} else if (tax.getText().hashCode() == editText.getText()
					.hashCode()) {
				staticTax = taxAmount;
			}
		}
	}

	private float getTextFromEditText(EditText editText) {
		String sValueFromText = editText.getText().toString();
		return Float.parseFloat(sValueFromText);
	}

	private void initialize() {
		numberOfGuests = (EditText) findViewById(R.id.numberOfGuests);
		billTotal = (EditText) findViewById(R.id.bill_Total);
		billDeductions = (EditText) findViewById(R.id.bill_Deductions);
		tax = (EditText) findViewById(R.id.Tax);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		tip = (TextView) findViewById(R.id.tip);
		tipRate = (TextView) findViewById(R.id.tipRate);
		perPersonTip = (TextView) findViewById(R.id.perPersonTip);
		entireBill = (TextView) findViewById(R.id.entireBill);
		tipTailoring = (Button) findViewById(R.id.tipTailoring);
		configTipItems = (Button) findViewById(R.id.configTipItems);
		tipTailoring.setOnClickListener(tipListener);
		configTipItems.setOnClickListener(configListener);
		numGuests = 0;
		totalBill = 0.00;
		deductionsToBill = 0.00;
	}

	public static void setDataInTextBoxes(double dataInBox, Text text) {
		dataInBox = (Math.round(dataInBox * 100.0) / 100.0);
		String putInTextBox = Double.toString(dataInBox);
		switch (text) {
		case TIPRATE:
			tipRate.setText(putInTextBox);
			break;
		case TOTALTIP:
			tip.setText(putInTextBox);
			break;
		case TIPPERPERSON:
			perPersonTip.setText(putInTextBox);
			break;
		default:
			entireBill.setText(putInTextBox);
			break;
		}

	}

	private View.OnClickListener tipListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (perPersonTip.getText().toString().matches("")) {
				Toast.makeText(MainActivity.this, TOTALTIP, 5000).show();
			} else {
				Intent intent = new Intent(MainActivity.this,
						TipSplittingScreen.class);
				startActivity(intent);
			}
		}
	};

	private View.OnClickListener configListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, AddTaxToTip.class);
			startActivity(intent);
		}
	};

	/*
	 * defaultTip = 20.00; String MaxTip = "20.00"; try { MaxTip =
	 * configTip.getMaxTip(); defaultTip = Float.parseFloat(MaxTip); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * tipTailoring.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { goToTipTailoringPage(); }
	 * 
	 * private void goToTipTailoringPage() { // TODO Auto-generated method stub
	 * if (!billTotal.getText().toString().matches("") &&
	 * !Tax.getText().toString().matches("") &&
	 * !numberOfGuests.getText().toString().matches("")) { Intent intent = new
	 * Intent(MainActivity.this, BILL_ENTRY_SCREEN.class);
	 * startActivity(intent); }
	 * 
	 * } });
	 * 
	 * configTipItems.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { goToConfiguratingTipItemsPage();
	 * }
	 * 
	 * private void goToConfiguratingTipItemsPage() { // TODO Auto-generated
	 * method stub Intent intent = new Intent(MainActivity.this,
	 * Tip_Splitting.class); startActivity(intent); } });
	 * 
	 * }
	 * 
	 * private void addListenerOnRatingBar() {
	 * 
	 * // if rating value is changed, // display the current rating value in the
	 * result (textview) // automatically
	 * 
	 * ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
	 * public void onRatingChanged(RatingBar ratingBar, float rating, boolean
	 * fromUser) { Rating = ratingBar.getRating(); calculation(); } }); }
	 * 
	 * private void calculation() { final double tipPercent = defaultTip;
	 * boolean dataEnterd = false; float deducs = (float) 0.00; dataEnterd =
	 * (!billTotal.getText().toString().matches("") &&
	 * !Tax.getText().toString().matches("") && !numberOfGuests
	 * .getText().toString().matches(""));
	 * 
	 * tryToGetDataFromOtherActivities();
	 * 
	 * if (!billDeductions.getText().toString().matches("")) { deducs =
	 * getTextFromEditTest(billDeductions); }
	 * 
	 * if (dataEnterd) {
	 * 
	 * float newRating = Rating * (float) (tipPercent / 4.0) / 100;
	 * tipRate.setText(Float.toString((float) (Math .round(newRating * 10000.0)
	 * / 100.0))); float tipTotal = getTextFromEditTest(billTotal); float tax =
	 * getTextFromEditTest(Tax); if (subDeductions) { tipTotal -= deducs; } if
	 * (addTax) { tipTotal += tax; } tipTotal *= newRating; tipTotal = (float)
	 * (Math.round(tipTotal * 100.0) / 100.0);
	 * 
	 * Float fCheckMinTip = getTextFromEditTest(billTotal); fCheckMinTip *=
	 * (fminTip / 100); fCheckMinTip = (float) (Math.round(fCheckMinTip * 100.0)
	 * / 100.0);
	 * 
	 * if (fCheckMinTip > tipTotal) { tipTotal = fCheckMinTip;
	 * Toast.makeText(MainActivity.this, "Less than Min tip", 500) .show(); }
	 * 
	 * String displayTip = Float.toString(tipTotal); tip.setText(displayTip);
	 * setTextForTipPerPerson(tipTotal); setTextForTotalBill(tipTotal, deducs,
	 * tax); } }
	 * 
	 * private void setTextForTipPerPerson(float tipTotal) { float people =
	 * getTextFromEditTest(numberOfGuests); float tipPerPerson = tipTotal /
	 * people; tipPerPerson = (float) (Math.round(tipPerPerson * 100.0) /
	 * 100.0); String displayTipPerPerson = Float.toString(tipPerPerson); if
	 * (hasTipBeenPersonalized) { displayTipPerPerson.concat("Tailored"); }
	 * perPersonTip.setText(displayTipPerPerson); }
	 * 
	 * private void setTextForTotalBill(float tipTotal, float deducs, float tax)
	 * { float billAmount = getTextFromEditTest(billTotal); float
	 * totalEntireBill = tipTotal - deducs + tax + billAmount; totalEntireBill =
	 * (float) (Math.round(totalEntireBill * 100.0) / 100.0); String
	 * displayTotalBill = Float.toString(totalEntireBill);
	 * entireBill.setText(displayTotalBill); }
	 * 
	 * private void tryToGetDataFromOtherActivities() { try { minTip =
	 * configTip.getMinTip(); fminTip = Float.parseFloat(minTip); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * try { addTax = configTip.addTax(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * try { subDeductions = configTip.subtractDecuctions(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * }
	 */

}
