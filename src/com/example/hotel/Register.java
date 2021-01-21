package com.example.hotel;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity {

	private EditText name;
	private EditText pass;
	private Button reg;
	private Button back;
	private Spinner r_spinner;
	SQLiteDatabase db;
	dbHelper helper = new dbHelper(Register.this, "hotel", 1);
	List<String> listName_manage = new ArrayList<String>();
	List<String> listName_repair = new ArrayList<String>();
	String strName;
	String strSpinner;
	String strPass;
	int numTemp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regis);
		name = (EditText) findViewById(R.id.Ereg_name);
		pass = (EditText) findViewById(R.id.Ereg_pass);
		r_spinner = (Spinner) findViewById(R.id.r_Spinner);
		reg = (Button) findViewById(R.id.re);
		back = (Button) findViewById(R.id.back);
		db = helper.getWritableDatabase();
		reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strName = name.getText().toString();
				strPass = pass.getText().toString();
				int numTemp = 0;
				strSpinner = r_spinner.getSelectedItem().toString();
				// Toast.makeText(Register.this, strSpinner, 10000).show();

				if (strSpinner.equals("管理部")) {

					// db.execSQL("insert into reg_manage values(?,?,?)",
					// new String[] { strName, strPass, strSpinner });
					// Toast.makeText(Register.this, "注册成功", 10000).show();
					Cursor cursor = db.query("reg_manage",
							new String[] { "name" }, null, null, null, null,
							null);
					// Cursor cursor =
					// db.rawQuery("select name from reg_manage",
					// null);
					while (cursor.moveToNext()) {
						listName_manage.add(cursor.getString(cursor
								.getColumnIndex("name")));

					}
					for (int i = 0; i < listName_manage.size(); i++) {

						if (strName.equals(listName_manage.get(i))) {
							new AlertDialog.Builder(Register.this)
									.setTitle("提示框")
									.setMessage("该用户名已存在，请重新输入！")
									.setPositiveButton(
											R.string.ok,
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}

									).show();

							break;
						}
						numTemp = i;
					}

					if ((numTemp + 1) >= listName_manage.size()) {
						db.execSQL("insert into reg_manage values(?,?,?)",
								new String[] { strName, strPass, strSpinner });
						Toast.makeText(Register.this, "注册成功", 10000).show();
					}
				}
				if (strSpinner.equals("维修部")) {
					Cursor cursor = db.query("reg_repair",
							new String[] { "name" }, null, null, null, null,
							null);
					// Cursor cursor =
					// db.rawQuery("select name from reg_manage",
					// null);
					while (cursor.moveToNext()) {
						listName_repair.add(cursor.getString(cursor
								.getColumnIndex("name")));

					}
					for (int i = 0; i < listName_repair.size(); i++) {

						if (strName.equals(listName_repair.get(i))) {
							new AlertDialog.Builder(Register.this)
									.setTitle("提示框")
									.setMessage("该用户名已存在，请重新输入！")
									.setPositiveButton(
											R.string.ok,
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
												}
											}

									).show();

							break;
						}
						numTemp = i;
					}
					if ((numTemp + 1) >= listName_repair.size()) {
						db.execSQL("insert into reg_repair values(?,?,?)",
								new String[] { strName, strPass, strSpinner });
						Toast.makeText(Register.this, "注册成功", 10000).show();
					}

					// db.execSQL("insert into reg_repair values(?,?,?)",
					// new String[] { strName, strPass, strSpinner });

				}

				// Toast.makeText(Register.this, "注册成功", 10000).show();
			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
