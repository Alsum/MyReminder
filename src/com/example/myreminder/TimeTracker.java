package com.example.myreminder;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class TimeTracker extends ListActivity {

	public static final int TIME_ENTRY_REQUEST_CODE = 1;
	TimeTrackerAdapter Adapter;
	private TimeTrackerDatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		databaseHelper = new TimeTrackerDatabaseHelper(this);

		Adapter = new TimeTrackerAdapter(this,
				databaseHelper.getAllTimeRecords());
		setListAdapter(Adapter);

		if (Adapter.isEmpty()) {

			AlertDialog alertDialog = new AlertDialog.Builder(TimeTracker.this).create();

			// Setting Dialog Title
			alertDialog.setTitle("Note");

			// Setting Dialog Message
			alertDialog.setMessage("You don't have any reminders yet please click on menu to add");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.ic_launcher);

			// Setting OK Button
			alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

				}
			});

			// Showing Alert Message
			alertDialog.show();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuinflator = getMenuInflater();
		menuinflator.inflate(R.menu.time_list_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (R.id.add_time_menu == item.getItemId()) {

			Intent in = new Intent(this, AddTime.class);
			startActivityForResult(in, TIME_ENTRY_REQUEST_CODE);

		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (resultCode == RESULT_OK) {
			String time = data.getStringExtra("time");
			String date = data.getStringExtra("date");
			String fullDate="Date "+date+"\n"+"Time "+time;
			String note = data.getStringExtra("notes");
			databaseHelper.saveTimeRecord(fullDate, note);
			Adapter.changeCursor(databaseHelper.getAllTimeRecords());

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		TextView tv_time=(TextView)v.findViewById(R.id.tv_time);
		TextView tv_note=(TextView)v.findViewById(R.id.tv_note);
		
		String fulldate=tv_time.getText().toString();
		String date=fulldate.substring(fulldate.indexOf("e")+1, fulldate.indexOf("T"));
		String time=fulldate.substring(fulldate.lastIndexOf("e")+1,fulldate.length());

		
		Intent i = new Intent(this,AddTime.class);
		i.putExtra("note_data", tv_note.getText().toString());
		i.putExtra("formattedTime", time);
		i.putExtra("formattedDate", date.trim());
		startActivity(i);
		
		
	}

}
