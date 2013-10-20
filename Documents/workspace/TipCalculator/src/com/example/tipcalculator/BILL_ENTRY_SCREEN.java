package com.example.tipcalculator;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.tipcalculator.MainActivity;

public class BILL_ENTRY_SCREEN extends Activity {

	Button goToMainPage;
	EditText enterNames;
	SeekBar percentageTip;
	TextView tipPerPerson;
	TableRow rowPerPerson;
	MainActivity main;
	Float totalTip;
	String tipTotal;
	ArrayList<TextView> rows;
	ArrayList<SeekBar> bars;
	TableLayout table;
	int rowNum;
	int numberOfGuests;
	Float numGuest;
	static boolean hasTipBeenTailored;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill__entry__screen);
		enterNames = (EditText) findViewById(R.id.enterName);
		hasTipBeenTailored = false;
		tipPerPerson = (TextView) findViewById(R.id.tipPerPerson);
		goToMainPage = (Button) findViewById(R.id.billEntry);

		table = (TableLayout) BILL_ENTRY_SCREEN.this.findViewById(R.id.table);

/*		String numberOfGuest = main.howManyGuests();
		tipTotal = main.tipTotalForBill();*/
		totalTip = Float.parseFloat(tipTotal);
/*		numGuest = Float.parseFloat(numberOfGuest);
		float tipPerPersons = totalTip / numGuest;
		numberOfGuests = Math.round(numGuest);
		rows = new ArrayList<TextView>();
		bars = new ArrayList<SeekBar>();
		if (!numberOfGuest.matches("") && !tipTotal.matches("")) {

			LayoutInflater in = getLayoutInflater();

			for (int i = 0; i < numberOfGuests; i++) {
				View v = in.inflate(R.layout.row_per_person, table, false);
				float num = (float) (Math.round(tipPerPersons * 100.0) / 100.0);
				String tip = Float.toString(num);
				tipPerPerson = (TextView) v.findViewById(R.id.tipPerPerson);
				tipPerPerson.setText(tip);
				rows.add(tipPerPerson);

				percentageTip = (SeekBar) v.findViewById(R.id.seekBar1);
				percentageTip.setProgress(100 / numberOfGuests);
				percentageTip.setId(i);
				table.addView(v);
				bars.add(percentageTip);
				setSeekBar();
			}

		}*/

		setSeekBar();
		goToMainPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToMainActivityPage();
			}

			private void goToMainActivityPage() {
				// TODO Auto-generated method stub
				finish();

			}
		});
	}

	private void setSeekBar() {

		rowNum = 0;

		percentageTip.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				for (int i = 0; i < numberOfGuests; i++) {
					if (seekBar.getId() == bars.get(i).getId()) {
						rowNum = i;
						hasTipBeenTailored = true;
						break;
					}

				}
				// TODO Auto-generated method stub
				float changePerPersonTip = Float.parseFloat(tipTotal);
				String oldTips = rows.get(rowNum).getText().toString();
				float fOldTipOfPerson = Float.parseFloat(oldTips);
				changePerPersonTip *= progress;
				changePerPersonTip /= 100;
				changePerPersonTip = (float) (Math
						.round(changePerPersonTip * 100.0) / 100.0);
				String changeInPerPerson = Float.toString(changePerPersonTip);

				float changePerRestOfPeople = 0;
				changePerRestOfPeople = (fOldTipOfPerson - changePerPersonTip)
						/ (numGuest - 1);
				changePerRestOfPeople = (float) (Math
						.round(changePerRestOfPeople * 100.0) / 100.0);

				rows.get(rowNum).setText(changeInPerPerson);
				for (int i = 0; i < numberOfGuests; i++) {
					if (seekBar.getId() != bars.get(i).getId()) {
						// bars.get(i).getProgress();
						String oldTip = rows.get(i).getText().toString();
						float fOldTip = Float.parseFloat(oldTip);

						fOldTip += changePerRestOfPeople;
						fOldTip = (float) (Math.round(fOldTip * 100.0) / 100.0);
						String newTip = Float.toString(fOldTip);
						rows.get(i).setText(newTip);
					}

				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_bill__entry__screen, menu);
		return true;
	}

	public static final boolean hasTipPerPersonBeenTailored() throws IOException {

		// TODO Auto-generated method stub
		try {
			hasTipBeenTailored = true;
		} catch (Exception e) {
			throw new IOException(e.toString());
		}
		return hasTipBeenTailored;

	}
}
