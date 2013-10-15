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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tipcalculator.Tip_Splitting;
import com.example.tipcalculator.BILL_ENTRY_SCREEN;

public class MainActivity extends Activity {

	static EditText numberOfGuests;
	BILL_ENTRY_SCREEN tipTailored;
	EditText billTotal;
	EditText billDeductions;
	EditText Tax;
	RatingBar ratingBar;
	static TextView tip;
	TextView tipRate;
	TextView perPersonTip;
	TextView entireBill;
	double defaultTip;
	float Rating;
	Button tipTailoring;
	Button configTipItems;
	Tip_Splitting configTip;
	boolean subDeductions;
	boolean addTax;
	String minTip;
	Float fminTip;
	Boolean hasTipBeenPersonalized;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		subDeductions = false;
		addTax = false;
		minTip = "0.00";
		fminTip = (float) 0.00;
		hasTipBeenPersonalized = false;
		initialize();
		dataEnteredForGuests();
		billTotal();
		billDeductions();
		tax();
		addListenerOnRatingBar();
		calculation();
		howManyGuests();
		tipTotalForBill();
	}

	public final static String tipTotalForBill() {
		return tip.getText().toString();

	}

	public final static String howManyGuests() {
		return numberOfGuests.getText().toString();
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void initialize() {
		numberOfGuests = (EditText) findViewById(R.id.numberOfGuests);
		billTotal = (EditText) findViewById(R.id.bill_Total);
		billDeductions = (EditText) findViewById(R.id.bill_Deductions);
		Tax = (EditText) findViewById(R.id.Tax);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		tip = (TextView) findViewById(R.id.tip);
		tipRate = (TextView) findViewById(R.id.tipRate);
		perPersonTip = (TextView) findViewById(R.id.perPersonTip);
		entireBill = (TextView) findViewById(R.id.entireBill);
		tipTailoring = (Button) findViewById(R.id.tipTailoring);
		configTipItems = (Button) findViewById(R.id.configTipItems);
		defaultTip = 20.00;
		String MaxTip = "20.00";
		try {
			MaxTip = configTip.getMaxTip();
			defaultTip = Float.parseFloat(MaxTip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tipTailoring.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToTipTailoringPage();
			}

			private void goToTipTailoringPage() {
				// TODO Auto-generated method stub
				if (!billTotal.getText().toString().matches("")
						&& !Tax.getText().toString().matches("")
						&& !numberOfGuests.getText().toString().matches("")) {
					Intent intent = new Intent(MainActivity.this,
							BILL_ENTRY_SCREEN.class);
					startActivity(intent);
				}

			}
		});

		configTipItems.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToConfiguratingTipItemsPage();
			}

			private void goToConfiguratingTipItemsPage() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						Tip_Splitting.class);
				startActivity(intent);
			}
		});

	}

	private void addListenerOnRatingBar() {

		// if rating value is changed,
		// display the current rating value in the result (textview)
		// automatically

		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				Rating = ratingBar.getRating();
				calculation();
			}
		});
	}

	private void calculation() {
		final double tipPercent = defaultTip;
		boolean dataEnterd = false;
		float deducs = (float) 0.00;
		dataEnterd = (!billTotal.getText().toString().matches("")
				&& !Tax.getText().toString().matches("") && !numberOfGuests
				.getText().toString().matches(""));

		tryToGetDataFromOtherActivities();

		if (!billDeductions.getText().toString().matches("")) {
			deducs = getTextFromEditTest(billDeductions);
		}

		if (dataEnterd) {

			float newRating = Rating * (float) (tipPercent / 4.0) / 100;
			tipRate.setText(Float.toString((float) (Math
					.round(newRating * 10000.0) / 100.0)));
			float tipTotal = getTextFromEditTest(billTotal);
			float tax = getTextFromEditTest(Tax);
			if (subDeductions) {
				tipTotal -= deducs;
			}
			if (addTax) {
				tipTotal += tax;
			}
			tipTotal *= newRating;
			tipTotal = (float) (Math.round(tipTotal * 100.0) / 100.0);

			Float fCheckMinTip = getTextFromEditTest(billTotal);
			fCheckMinTip *= (fminTip / 100);
			fCheckMinTip = (float) (Math.round(fCheckMinTip * 100.0) / 100.0);

			if (fCheckMinTip > tipTotal) {
				tipTotal = fCheckMinTip;
				Toast.makeText(MainActivity.this, "Less than Min tip", 500)
						.show();
			}

			String displayTip = Float.toString(tipTotal);
			tip.setText(displayTip);
			setTextForTipPerPerson(tipTotal);
			setTextForTotalBill(tipTotal, deducs, tax);
		}
	}

	private void setTextForTipPerPerson(float tipTotal) {
		float people = getTextFromEditTest(numberOfGuests);
		float tipPerPerson = tipTotal / people;
		tipPerPerson = (float) (Math.round(tipPerPerson * 100.0) / 100.0);
		String displayTipPerPerson = Float.toString(tipPerPerson);
		if (hasTipBeenPersonalized) {
			displayTipPerPerson.concat("Tailored");
		}
		perPersonTip.setText(displayTipPerPerson);
	}

	private void setTextForTotalBill(float tipTotal, float deducs, float tax) {
		float billAmount = getTextFromEditTest(billTotal);
		float totalEntireBill = tipTotal - deducs + tax + billAmount;
		totalEntireBill = (float) (Math.round(totalEntireBill * 100.0) / 100.0);
		String displayTotalBill = Float.toString(totalEntireBill);
		entireBill.setText(displayTotalBill);
	}

	private void tryToGetDataFromOtherActivities() {
		try {
			minTip = configTip.getMinTip();
			fminTip = Float.parseFloat(minTip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			addTax = configTip.addTax();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			subDeductions = configTip.subtractDecuctions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void tax() {
		Tax.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String number = Tax.getText().toString();
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
					Tax.setText("");
					Toast.makeText(MainActivity.this,
							"Only two numbers allowed after decimal", 5000)
							.show();

				}
				calculation();
			}
		});

	}

	private void billDeductions() {
		billDeductions.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String number = billDeductions.getText().toString();
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
					Toast.makeText(MainActivity.this,
							"Only two numbers allowed after decimal", 5000)
							.show();

				}
				calculation();

			}
		});

	}

	private void billTotal() {
		billTotal.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String number = billTotal.getText().toString();
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
					billTotal.setText("");
					Toast.makeText(MainActivity.this,
							"Only two numbers allowed after decimal", 5000)
							.show();

				}
				calculation();
			}
		});
	}

	private void dataEnteredForGuests() {
		numberOfGuests.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String number = numberOfGuests.getText().toString();
				if (number.equals("0")) {
					numberOfGuests.setText("");
					Toast.makeText(MainActivity.this, "You count as guest",
							5000).show();
				}
				calculation();
			}
		});
	}

	private float getTextFromEditTest(EditText editText) {
		String sValueFromText = editText.getText().toString();
		return Float.parseFloat(sValueFromText);
	}
}
