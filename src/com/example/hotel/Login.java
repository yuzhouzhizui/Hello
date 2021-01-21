package com.example.hotel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Login extends Activity {
	private EditText name;
	private EditText password;
	private Spinner title;
	private Button login;
	private Button cancel;
	private Button comein;
	private dbHelper helper = new dbHelper(Login.this, "hotel", 1);
	private SQLiteDatabase sdb;
	private String strSpinner;
	String strName = "";
	String strPassword = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		login = (Button) findViewById(R.id.login);
		cancel = (Button) findViewById(R.id.cancel);
		comein = (Button) findViewById(R.id.reg);
		name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.password);
		title = (Spinner) findViewById(R.id.Spinner);
		sdb = helper.getReadableDatabase();
		login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//取得界面上的内容
				strSpinner = title.getSelectedItem().toString();
				strName = name.getText().toString();
				strPassword = password.getText().toString();
				//判断用户名和密码是否为空
				if (strName.equals("") || strPassword.equals("")) {
					Toast.makeText(Login.this, "用户名或者密码不能为空", Toast.LENGTH_LONG)
							.show();
				} else {
					//如果密码不为空，判断登录的人员是属于哪个部门的
					if (strSpinner.equals("管理部")) {
						//以用户名为限定条件，查询密码
						Cursor cursor = sdb.query("reg_manage",
								new String[] { "pass" }, "name=?",
								new String[] { strName }, null, null, null);
						// System.out.println("aaaaaaaaaaaaa");
						//如果密码不是空，获得的密码和输入的密码判断
						if (cursor.moveToNext()) {
							//获得的密码和输入的密码相等的情况下，执行页面跳转
							if (strPassword.equals(cursor.getString(cursor
									.getColumnIndex("pass")))) {
								Intent intent = new Intent(Login.this,
										Manage.class);
								startActivity(intent);
								// System.out.println("bbbbbbbbbbb");
							} 
							//获得的密码和输入的密码不相等的情况下，toast显示提示信息
							else {
								Toast.makeText(Login.this, "密码不正确",
										Toast.LENGTH_LONG).show();
							}
						} 
						//如果密码为空，则说明用户名不存在
						else {
							Toast.makeText(Login.this, "用户名不存在",
									Toast.LENGTH_LONG).show();
						}
					} 
					//同上
					else {
						Cursor cursor = sdb.query("reg_repair",
								new String[] { "pass" }, "name=?",
								new String[] { strName }, null, null, null);
						if (cursor.moveToNext()) {

							if (strPassword.equals(cursor.getString(cursor
									.getColumnIndex("pass")))) {
								Intent intent = new Intent(Login.this,
										Repair.class);
								startActivity(intent);
							} else {
								Toast.makeText(Login.this, "密码不正确",
										Toast.LENGTH_LONG).show();
							}

						} else {
							Toast.makeText(Login.this, "用户名不存在",
									Toast.LENGTH_LONG).show();
						}

					}
				}
				name.setText("");
				password.setText("");
			}

		});
		comein.setOnClickListener(new OnClickListenercomein());
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	class OnClickListenercomein implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Login.this, Register.class);
			startActivity(intent);

		}
	}

}
