package com.example.hotel;

import android.app.TabActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

public class Manage extends TabActivity {

	private Button bnTime, bnCacul, ruzhu_commit, ruzhu_fresh, rep_sure,
			rep_cancel, exits_sure, exits_query, query_button;
	private EditText ruzhu_edTime;
	SQLiteDatabase db;
	String strCurrentTime = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		TabHost tabhost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.maneger,
				tabhost.getTabContentView(), true);
		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("��ס")
				.setContent(R.id.ruzhu));
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("��ѯ")
				.setContent(R.id.query));
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("����")
				.setContent(R.id.repairs));
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("�˷�")
				.setContent(R.id.exits));

		final dbHelper helper = new dbHelper(Manage.this, "hotel", 1);
		db = helper.getWritableDatabase();
		/**
		 * ��סJava����
		 */
		// ��ȡ��סʱ��
		bnTime = (Button) findViewById(R.id.bntime);
		bnTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ruzhu_edTime = (EditText) findViewById(R.id.ruzhu_edtime);
				strCurrentTime = new Time().currentTime();
				ruzhu_edTime.setText(strCurrentTime);
			}
		});
		// ͨ����ס�������Զ�����۸�
		bnCacul = (Button) findViewById(R.id.allPrice);
		bnCacul.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String days = ((EditText) findViewById(R.id.ruzhu_days))
						.getText().toString();
				String room_id = ((EditText) findViewById(R.id.ruzhu_room_id))
						.getText().toString();
				EditText money = (EditText) findViewById(R.id.ruzhu_money);
				if (days.equals("") || room_id.equals("")) {
					Toast.makeText(Manage.this, "��ס����ź������Ǳ����",
							Toast.LENGTH_LONG).show();
				} else {
					if (Integer.parseInt(room_id) > 20) {
						int APrice = Integer.parseInt(days) * 120;
						money.setText(String.valueOf(APrice));
					} else {
						int APrice = Integer.parseInt(days) * 80;
						money.setText(String.valueOf(APrice));
					}
				}
			}
		});
		// ˢ�°�ť�����¼�ʵ��
		ruzhu_fresh = (Button) findViewById(R.id.ruzhu_fresh);
		ruzhu_fresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText name = ((EditText) findViewById(R.id.ruzhu_person_name));
				name.setText("");
				EditText id = ((EditText) findViewById(R.id.ruzhu_person_id));
				id.setText("");
				EditText days = ((EditText) findViewById(R.id.ruzhu_days));
				days.setText("");
				EditText room_id = ((EditText) findViewById(R.id.ruzhu_room_id));
				room_id.setText("");
				EditText money = ((EditText) findViewById(R.id.ruzhu_money));
				money.setText("");
				ruzhu_edTime = (EditText) findViewById(R.id.ruzhu_edtime);
				ruzhu_edTime.setText("");
			}
		});

		// ʵ����ס��ť�����¼�
		ruzhu_commit = (Button) findViewById(R.id.ruzhu_commit);
		ruzhu_commit.setOnClickListener(new OnClickListener() {
			String sex = "";

			public void onClick(View v) {
				// ��ȡ��ס������Ϣ
				String name = ((EditText) findViewById(R.id.ruzhu_person_name))
						.getText().toString();
				final RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
				if (radioButton1.isChecked()) {
					sex = "��";
				} else {
					sex = "Ů";
				}
				String id = ((EditText) findViewById(R.id.ruzhu_person_id))
						.getText().toString();
				String days = ((EditText) findViewById(R.id.ruzhu_days))
						.getText().toString();
				String room_id = ((EditText) findViewById(R.id.ruzhu_room_id))
						.getText().toString();
				String money = ((EditText) findViewById(R.id.ruzhu_money))
						.getText().toString();
				String time = strCurrentTime;

				// �жϹ˿���Ϣ�Ƿ�Ϊ��
				if (name.equals("")) {
					Toast.makeText(Manage.this, "�˿���������Ϊ��", Toast.LENGTH_LONG)
							.show();
				} else if (id.equals("")) {
					Toast.makeText(Manage.this, "�˿����֤�Ų���Ϊ��", Toast.LENGTH_LONG)
							.show();

				} else if (time.equals("")) {
					Toast.makeText(Manage.this, "��סʱ�䲻��Ϊ��", Toast.LENGTH_LONG)
							.show();

				} else if (days.equals("")) {
					Toast.makeText(Manage.this, "��ס��������Ϊ��", Toast.LENGTH_LONG)
							.show();

				} else if (room_id.equals("")) {
					Toast.makeText(Manage.this, "����Ų���Ϊ��", Toast.LENGTH_LONG)
							.show();

				} else if (money.equals("")) {
					Toast.makeText(Manage.this, "�ܼ۸���Ϊ��", Toast.LENGTH_LONG)
							.show();

				} else {
					// �ж���Ҫ��ס�ķ����Ƿ�����
					Cursor cursor = db.query("employee",
							new String[] { "name" }, "room_id= ?",
							new String[] { room_id }, null, null, null);
					if (cursor.moveToNext()) {
						String strName = cursor.getString(cursor
								.getColumnIndex("name"));
						System.out.println(strName);
						Toast.makeText(Manage.this, "�÷����й˿ͣ���ѡ����������",
								Toast.LENGTH_LONG).show();
					} else {
						// �ж����֤���Ƿ���18λ
						if (id.length() == 18) {
							// ����ס��Ϣ�������ݿ�
							db.execSQL(
									"insert into employee values(?,?,?,?,?,?,?)",
									new String[] { name, sex, id, time, days,
											room_id, money });
							Toast.makeText(Manage.this, "��ס�ɹ�", 10000).show();
						} else {
							Toast.makeText(Manage.this, "���֤�Ų���ȷ������������",
									Toast.LENGTH_LONG).show();
							((EditText) findViewById(R.id.ruzhu_person_id))
									.setText("");
						}
					}
				}

			}
		});

		/**
		 * ��ѯjava����
		 */

		final EditText query_room_id = (EditText) findViewById(R.id.query_room_id);
		query_button = (Button) findViewById(R.id.query_button);
		final WebView wv = (WebView) findViewById(R.id.weserach);

		// Ϊ��ѯ��ť�󶨼�����
		query_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ����û�����ķ����
				String room_id_2 = query_room_id.getText().toString();
				// ��ѯ�û�����ķ�����Ƿ������ݿ��еķ����ƥ��
				Cursor cursor = db.query("employee", new String[] { "name",
						"sex", "id", "room_id", "time", "days", "money" },
						"room_id= ?", new String[] { room_id_2 }, null, null,
						null);
				if (cursor.moveToNext()) {
					// ������ݿ���Ϣ
					String query_room_id = cursor.getString(cursor
							.getColumnIndex("room_id"));
					String query_name = cursor.getString(cursor
							.getColumnIndex("name"));
					String sex = cursor.getString(cursor.getColumnIndex("sex"));
					String query_id = cursor.getString(cursor
							.getColumnIndex("id"));
					String query_time = cursor.getString(cursor
							.getColumnIndex("time"));
					String query_days = cursor.getString(cursor
							.getColumnIndex("days"));
					String query_money = cursor.getString(cursor
							.getColumnIndex("money"));
					StringBuilder sb = new StringBuilder();
					// ƴ��HTML����
					sb.append("<html>");
					sb.append("<head>");
					sb.append("</head>");
					sb.append("<body>");
					sb.append("�����:" + query_room_id);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("����:" + query_name);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("�Ա�:" + sex + "\n");
					sb.append("<br>");
					sb.append("<br>");
					sb.append("���֤:" + query_id);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("��סʱ��:" + query_time);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("��ס����:" + query_days);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("��Ǯ��:" + query_money);
					sb.append("</body>");
					sb.append("</html>");
					// �����ݼ��ص�webview��������ʾ��ѯ��Ϣ
					wv.loadDataWithBaseURL(null, sb.toString(), "text/html",
							"utf-8", null);
				} else {
					// û������סʱ��ʾ��ʾ��Ϣ
					Toast.makeText(Manage.this, "�÷���û����ס��Ϣ��", 5000).show();
					wv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
				}
			}
		});
		/**
		 * ����Java����
		 */
		rep_sure = (Button) findViewById(R.id.rep_sure);
		rep_cancel = (Button) findViewById(R.id.rep_cancel);
		final EditText rep_id = (EditText) findViewById(R.id.rep_id);
		final EditText rep_status = (EditText) findViewById(R.id.rep_status);
		rep_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mRep_id = (rep_id.getText()).toString();
				String mRep_status = (rep_status.getText()).toString();
				db.execSQL("insert into repair values(?,?,?)", new String[] {
						mRep_id, mRep_status, "no_repairs" });
				Toast.makeText(Manage.this, "���޳ɹ�", 10000).show();
			}
		});
		rep_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rep_id.setText("");
				rep_status.setText("");

			}
		});

		// �˷�����
		final EditText exits_name = (EditText) findViewById(R.id.exits_name);
		final EditText exits_room_id = (EditText) findViewById(R.id.exits_room_id);

		exits_sure = (Button) findViewById(R.id.exits_sure);
		exits_query = (Button) findViewById(R.id.exits_query);

		// ȡ�õ�ǰ�����ŵ��û���
		exits_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name_demo = "";
				String room_id1 = exits_room_id.getText().toString();
				String strNameid = exits_name.getText().toString();

				Cursor cursor = db.query("employee", new String[] { "name" },
						"room_id like ?", new String[] { room_id1 }, null,
						null, null);
				// cursor.moveToFirst();
				while (cursor.moveToNext()) {
					name_demo = cursor.getString(cursor.getColumnIndex("name"));
				}

				// �жϸ÷�����û����Ƿ���������û������
				if (strNameid.equals(name_demo)) {
					// ɾ���÷���ĵǼ���Ϣ
					db.delete("employee", "room_id like ?",
							new String[] { room_id1 });
					exits_room_id.setText("");
					exits_name.setText("");
					Toast.makeText(Manage.this, "ɾ���ɹ�", 10000).show();
				} else {
					Toast.makeText(Manage.this, "ɾ�����ɹ�", 10000).show();
				}
				cursor.close();
			}

		});
		exits_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}
}
