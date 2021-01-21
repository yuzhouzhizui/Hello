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
				//ȡ�ý����ϵ�����
				strSpinner = title.getSelectedItem().toString();
				strName = name.getText().toString();
				strPassword = password.getText().toString();
				//�ж��û����������Ƿ�Ϊ��
				if (strName.equals("") || strPassword.equals("")) {
					Toast.makeText(Login.this, "�û����������벻��Ϊ��", Toast.LENGTH_LONG)
							.show();
				} else {
					//������벻Ϊ�գ��жϵ�¼����Ա�������ĸ����ŵ�
					if (strSpinner.equals("����")) {
						//���û���Ϊ�޶���������ѯ����
						Cursor cursor = sdb.query("reg_manage",
								new String[] { "pass" }, "name=?",
								new String[] { strName }, null, null, null);
						// System.out.println("aaaaaaaaaaaaa");
						//������벻�ǿգ���õ����������������ж�
						if (cursor.moveToNext()) {
							//��õ�����������������ȵ�����£�ִ��ҳ����ת
							if (strPassword.equals(cursor.getString(cursor
									.getColumnIndex("pass")))) {
								Intent intent = new Intent(Login.this,
										Manage.class);
								startActivity(intent);
								// System.out.println("bbbbbbbbbbb");
							} 
							//��õ��������������벻��ȵ�����£�toast��ʾ��ʾ��Ϣ
							else {
								Toast.makeText(Login.this, "���벻��ȷ",
										Toast.LENGTH_LONG).show();
							}
						} 
						//�������Ϊ�գ���˵���û���������
						else {
							Toast.makeText(Login.this, "�û���������",
									Toast.LENGTH_LONG).show();
						}
					} 
					//ͬ��
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
								Toast.makeText(Login.this, "���벻��ȷ",
										Toast.LENGTH_LONG).show();
							}

						} else {
							Toast.makeText(Login.this, "�û���������",
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
