package com.example.hotel;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Repair extends Activity {

	SQLiteDatabase db;
	// 维修事项
	TextView repMatter;
	EditText rep_room_id;
	Spinner repStatus;
	private Button rep_delete, rep_query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repair);
		rep_delete = (Button) findViewById(R.id.rep_commit);
		rep_query = (Button) findViewById(R.id.rep_select);
		final dbHelper helper = new dbHelper(Repair.this, "hotel", 1);
		db = helper.getWritableDatabase();
		// 维修事项
		repMatter = (TextView) findViewById(R.id.repMatter);
		// 维修的房间号
		rep_room_id = (EditText) findViewById(R.id.rep_room_id);
		final String result_room_id = rep_room_id.getText().toString();
		rep_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Cursor cursor = db.query("repair", new String[] { "room_id",
						"item", "finish" }, " room_id=?",
						new String[] { result_room_id }, null, null, null);
				String strOne = cursor.getString(cursor
						.getColumnIndex("room_id"));
				String strTwo = cursor.getString(cursor.getColumnIndex("item"));

				rep_room_id.setText(strOne);
				repMatter.setText(strTwo);
			}
		});
		rep_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				db.delete("repair", "id=?", new String[] { result_room_id });
				Toast.makeText(Repair.this, "删除成功", 10000).show();

			}
		});
	}
}
