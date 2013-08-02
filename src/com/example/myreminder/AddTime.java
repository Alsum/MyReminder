package com.example.myreminder;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddTime extends Activity implements OnClickListener {
	private static final int ID_TIMEPICKER = 0;
	private static final int ID_DATEPICKER = 1;
	int myHour;
	int myMinute,myYear,myMonth,myDay;
	Button save, cancel, settime,setdate;
	EditText note;
	private PendingIntent pendingIntent;
	String timeofpicker;
	String date;
	int i=0;
	boolean alarmUp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_time);
		init_vars();
		
		if (getIntent().hasExtra("note_data")){
			settime.setText(getIntent().getExtras().getString("formattedTime"));
			setdate.setText(getIntent().getExtras().getString("formattedDate"));
			note.setText(getIntent().getExtras().getString("note_data"));
			
			
		}
		
		
	}

	private void init_vars() {
		// TODO Auto-generated method stub
		save = (Button) findViewById(R.id.b_save);
		cancel = (Button) findViewById(R.id.b_cancel);
		settime = (Button) findViewById(R.id.bt_settime);
		setdate = (Button) findViewById(R.id.bt_setDate);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);
		settime.setOnClickListener(this);
		setdate.setOnClickListener(this);
		note = (EditText) findViewById(R.id.et_note);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == ID_TIMEPICKER) {
			/*
			 * Toast.makeText(Publicform_Activity.this,
			 * "- onCreateDialog(ID_TIMEPICKER) -", Toast.LENGTH_LONG).show();
			 */
			return new TimePickerDialog(this, myTimeSetListener, myHour,
					myMinute, false);
		}
		if (id == ID_DATEPICKER){
		/*
		 * Toast.makeText(Publicform_Activity.this,
		 * "- onCreateDialog(ID_DATEPICKER) -", Toast.LENGTH_LONG).show();
		 */
			return new DatePickerDialog(this, myDateSetListener, myYear,myMonth, myDay);
		}
		return null;

	}

	
	
	
	private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			date= String.valueOf(dayOfMonth) + "-"
			+ String.valueOf(monthOfYear + 1) + "-"
			+ String.valueOf(year);
			
			setdate.setText(date);
		}
		
	};
	
	private TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			timeofpicker = String.valueOf(hourOfDay) + ":"
					+ String.valueOf(minute);
			
			settime.setText(timeofpicker);
		
			
			
		}
	};
	
	
	
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.b_cancel:
			finish();
			break;

		case R.id.b_save:
			// set alarm service
			Intent myIntent = new Intent(AddTime.this, MyReceiver.class);
			
			myIntent.putExtra("note_data", note.getText().toString());
			
			alarmUp = (PendingIntent.getBroadcast(getBaseContext(), i, myIntent, PendingIntent.FLAG_NO_CREATE) != null);
			Log.d("alarmUp", alarmUp+"");
			
			while(alarmUp){
				i ++;
				Log.d("myTag", "Alarm is already active");
				Log.d("i", i+"");
				
				alarmUp = (PendingIntent.getBroadcast(getBaseContext(), i, myIntent, PendingIntent.FLAG_NO_CREATE) != null);
				Log.d("alarmUp", alarmUp+"");
				
				
			}
			
			myIntent.putExtra("NotifID", i);
			pendingIntent = PendingIntent.getBroadcast(getBaseContext(), i, myIntent,0);

			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			
			
			String hours = timeofpicker.substring(0, timeofpicker.indexOf(":"));
			String mintus = timeofpicker.substring(
					timeofpicker.indexOf(":") + 1, timeofpicker.length());

			String days=date.substring(0,date.indexOf("-"));
			String month=date.substring(date.indexOf("-")+1, date.lastIndexOf("-"));
			String year=date.substring(days.length()+month.length()+2,date.length());
			
			Log.d("days", days);
			Log.d("month", month);
			Log.d("year", year);
			
			calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
			calendar.set(Calendar.MINUTE, Integer.parseInt(mintus));
			calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(days));
			calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
			calendar.set(Calendar.YEAR,Integer.parseInt(year));
			
			alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);

			Intent i = new Intent();
			i.putExtra("notes", note.getText().toString());
			i.putExtra("time", settime.getText().toString());
			i.putExtra("date", setdate.getText().toString());
			setResult(RESULT_OK, i);
			finish();
	
			break;

		case R.id.bt_settime:
			// hide the keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(settime.getWindowToken(), 0);

			final Calendar c = Calendar.getInstance();
			myHour = c.get(Calendar.HOUR_OF_DAY);
			myMinute = c.get(Calendar.MINUTE);
			showDialog(ID_TIMEPICKER);
			break;
			
		case R.id.bt_setDate:
			
			final Calendar cal = Calendar.getInstance();
			myYear = cal.get(Calendar.YEAR);
			myMonth  = cal.get(Calendar.MONTH);
			myDay = cal.get(Calendar.DAY_OF_MONTH);
			showDialog(ID_DATEPICKER);
						
			break;

		default:
			break;
		}

	}

}


















