package com.example.myreminder;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TimeTrackerAdapter extends CursorAdapter{
	
	
	public TimeTrackerAdapter(Context context, Cursor c) {
		
		super(context, c);
		
	}



	@Override
	public void bindView(View view, Context arg1, Cursor cursor) {
		// TODO Auto-generated method stub
		TextView nameTextView = (TextView) view.findViewById(R.id.tv_time);
		nameTextView.setText(cursor.getString(cursor.getColumnIndex("time")));
		TextView valueTextView = (TextView) view.findViewById(R.id.tv_note);
		valueTextView.setText(cursor.getString(cursor.getColumnIndex("notes")));
	}


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.time_list_item, parent, false);
		return view;
	}



   
}
