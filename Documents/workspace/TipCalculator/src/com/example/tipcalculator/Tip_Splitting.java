package com.example.tipcalculator;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class Tip_Splitting extends Activity {

	Button returnToMainActivity;
	static CheckBox tax;
	static CheckBox deductions;
	static EditText minTip;
	static EditText maxTip;
	static String sMaxTip;
	static String sMinTip;
	boolean changeInMax;

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
			addTax();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			subtractDecuctions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public final static boolean subtractDecuctions() throws IOException{
		// TODO Auto-generated method stub
		boolean deduct = false;
		try {
			deduct = deductions.isChecked();
		} catch (Exception e) {
			throw new IOException(e.toString());
		}
		return deduct;
	}


	public final static boolean addTax() throws IOException{
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
			sMaxTip = minTip.getText().toString();
		} catch (Exception e) {
			throw new IOException(e.toString());
		}
		return sMaxTip;

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
				finish();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_tip__splitting, menu);
		return true;
	}
}
