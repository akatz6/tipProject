package com.example.tipcalculator;

import java.io.IOException;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddTaxToTip extends Activity {

	Button returnToMainActivity;
	static CheckBox tax;
	static CheckBox deductions;
	static EditText minTip;
	static EditText maxTip;
	static String sMaxTip;
	static String sMinTip;
	boolean changeInMax;
	SharedPreferences sharedPreferences;
	Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip__splitting);
		returnToMainActivity = (Button) findViewById(R.id.backToMainActivy);
		tax = (CheckBox) findViewById(R.id.tax);
		deductions = (CheckBox) findViewById(R.id.deductions);
		minTip = (EditText) findViewById(R.id.min_tip);
		maxTip = (EditText) findViewById(R.id.max_tip);
		minTip.setText("0.00");
		maxTip.setText("20.00");
		sMaxTip = "20.00";
		sMinTip = "0.00";
		changeInMax = false;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sharedPreferences.edit();
		returnToMainScreen();
		try {
			getMaxTip();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			getMinTip();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			tax.setChecked(sharedPreferences.getBoolean("tax", true));
			addTax();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			deductions.setChecked(sharedPreferences.getBoolean("deduct", true));
			subtractDecuctions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clicked(View view) {
		// Is the view now checked?
		boolean checked = ((CheckBox) view).isChecked();

		switch (view.getId()) {
		case R.id.tax:
			if (checked)
				try {
					addTax();
					savePreferences("tax", true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else {
				savePreferences("tax", false);
			}

			break;
		case R.id.deductions:
			if (checked)
				try {
					subtractDecuctions();
					savePreferences("deduct", true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else {
				savePreferences("deduct", false);
			}
			break;
		}
	}

	private void savePreferences(String boxName, boolean isSaved) {
		editor.putBoolean(boxName, isSaved);
		editor.commit();

	}

	public final static boolean subtractDecuctions() throws IOException {
		// TODO Auto-generated method stub
		boolean deduct = false;
		try {
			deduct = deductions.isChecked();
		} catch (Exception e) {
			throw new IOException(e.toString());
		}
		return deduct;
	}

	public final static boolean addTax() throws IOException {
		// TODO Auto-generated method stub
		boolean taxes = false;
		try {
			taxes = tax.isChecked();
		} catch (Exception e) {
			throw new IOException(e.toString());
		}
		return taxes;
	}

	public final static String getMinTip() throws IOException {

		sMinTip = "0.00";
		try {
			sMinTip = minTip.getText().toString();
		} catch (Exception e) {
			throw new IOException(e.toString());
		}
		return sMinTip;

		// TODO Auto-generated method stub

	}

	public final static String getMaxTip() throws IOException {
		sMaxTip = "20.00";
		try {
			sMaxTip = maxTip.getText().toString();
		} catch (Exception e) {
			throw new IOException(e.toString());
		}
		return sMaxTip;

		// TODO Auto-generated method stub

	}

	private void returnToMainScreen() {
		returnToMainActivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToMainPage();
			}

			private void goToMainPage() {
				// TODO Auto-generated method stub
				BusinessLogicForMainScreen.sendTotalTip();
				finish();

			}
		});
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.activity_tip__splitting, menu); return
	 * true; }
	 */
}
