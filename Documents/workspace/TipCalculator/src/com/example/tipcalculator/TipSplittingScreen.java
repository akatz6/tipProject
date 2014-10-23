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
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.tipcalculator.MainActivity;

public class TipSplittingScreen extends Activity {

	Button goToMainPage;
	EditText enterNames;
	SeekBar percentageTip;
	static TextView tipPerPerson;
	TableRow rowPerPerson;
	MainActivity main;
	Float totalTip;
	String tipTotal;
	static ArrayList<TextView> rows;
	static ArrayList<SeekBar> bars;
	TableLayout table;
	Float numGuest;
	static boolean hasTipBeenTailored;
	LayoutInflater in;
	static double numberOfGuests;
	static double tipPerEachPerson;
	static double newTotalValueOfTips;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill__entry__screen);
		initiliaze();
		inflateTable();
	}

	private View.OnClickListener returnToMainPage = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			for (int i = 0; i < numberOfGuests; i++) {
				String newTipPerPerson = rows.get(i).getText().toString();
				double dNewTipPerPerson = Double.parseDouble(newTipPerPerson);
				newTotalValueOfTips += dNewTipPerPerson;
			} 
			BusineesDelegateTipSplitting.sendNewTipAmount(newTotalValueOfTips, numberOfGuests);
			finish();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_bill__entry__screen, menu);
		return true;
	}

	private void inflateTable() {
		for (int i = 0; i < FacadeShareUI.getNumGuests(); i++) {
			View v = in.inflate(R.layout.row_per_person, table, false);
			double tipPerIndividual = (Math.round(InBetweenUIAndBusinessLogic
					.getTipPerPerson() * 100.0) / 100.0);
			String tip = Double.toString(tipPerIndividual);
			tipPerPerson = (TextView) v.findViewById(R.id.tipPerPerson);
			tipPerPerson.setId(i);
			tipPerPerson.setText(tip);
			rows.add(tipPerPerson);
			percentageTip = (SeekBar) v.findViewById(R.id.seekBar1);
			percentageTip.setOnSeekBarChangeListener(new myListener());
			numberOfGuests = FacadeShareUI.getNumGuests();
			percentageTip.setProgress((int) (100 / numberOfGuests));
			percentageTip.setId(i);
			table.addView(v);
			bars.add(percentageTip);

		}

	}

	private void initiliaze() {
		enterNames = (EditText) findViewById(R.id.enterName);
		hasTipBeenTailored = false;
		tipPerPerson = (TextView) findViewById(R.id.tipPerPerson);
		goToMainPage = (Button) findViewById(R.id.billEntry);
		goToMainPage.setOnClickListener(returnToMainPage);
		table = (TableLayout) TipSplittingScreen.this.findViewById(R.id.table);
		rows = new ArrayList<TextView>();
		bars = new ArrayList<SeekBar>();
		in = getLayoutInflater();
		numberOfGuests = 0;
		newTotalValueOfTips = 0.0;

	}

	private class myListener implements SeekBar.OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar num, int changeInValue,
				boolean seekBarChanged) {
			if (seekBarChanged) {
				BusineesDelegateTipSplitting.sendSeekBarInfo(num, changeInValue);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
/*			oldBarValue = seekBar.getProgress();*/
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

	}

	public static void setNewTipAmount(SeekBar barChanged, double amountForTip) {
		int getID = barChanged.getId();
		for (int i = 0; i < numberOfGuests; i++) {
			if (getID == rows.get(i).getId()) {
				amountForTip = (Math.round(amountForTip * 100.0) / 100.0);
				rows.get(i).setText(Double.toString(amountForTip));
//				dNewBarValue = bars.get(i).getProgress();
			}
		} 
/*		double changeInBar =  (oldBarValue - dNewBarValue)/numberOfGuests-1;
		for (int i = 0; i < numberOfGuests; i++) {
			if (getID != rows.get(i).getId()) {
				int sOldBarThisValue =bars.get(i).getProgress();
				sOldBarThisValue += changeInBar;
				bars.get(i).setProgress(sOldBarThisValue);
			}
		}*/
	}
}

