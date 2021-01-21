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
		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("入住")
				.setContent(R.id.ruzhu));
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("查询")
				.setContent(R.id.query));
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("报修")
				.setContent(R.id.repairs));
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("退房")
				.setContent(R.id.exits));

		final dbHelper helper = new dbHelper(Manage.this, "hotel", 1);
		db = helper.getWritableDatabase();
		/**
		 * 入住Java代码
		 */
		// 获取入住时间
		bnTime = (Button) findViewById(R.id.bntime);
		bnTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ruzhu_edTime = (EditText) findViewById(R.id.ruzhu_edtime);
				strCurrentTime = new Time().currentTime();
				ruzhu_edTime.setText(strCurrentTime);
			}
		});
		// 通过入住天数来自动计算价格
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
					Toast.makeText(Manage.this, "入住房间号和天数是必填的",
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
		// 刷新按钮监听事件实现
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

		// 实现入住按钮监听事件
		ruzhu_commit = (Button) findViewById(R.id.ruzhu_commit);
		ruzhu_commit.setOnClickListener(new OnClickListener() {
			String sex = "";

			public void onClick(View v) {
				// 获取入住界面信息
				String name = ((EditText) findViewById(R.id.ruzhu_person_name))
						.getText().toString();
				final RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
				if (radioButton1.isChecked()) {
					sex = "男";
				} else {
					sex = "女";
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

				// 判断顾客信息是否为空
				if (name.equals("")) {
					Toast.makeText(Manage.this, "顾客姓名不能为空", Toast.LENGTH_LONG)
							.show();
				} else if (id.equals("")) {
					Toast.makeText(Manage.this, "顾客身份证号不能为空", Toast.LENGTH_LONG)
							.show();

				} else if (time.equals("")) {
					Toast.makeText(Manage.this, "入住时间不能为空", Toast.LENGTH_LONG)
							.show();

				} else if (days.equals("")) {
					Toast.makeText(Manage.this, "入住天数不能为空", Toast.LENGTH_LONG)
							.show();

				} else if (room_id.equals("")) {
					Toast.makeText(Manage.this, "房间号不能为空", Toast.LENGTH_LONG)
							.show();

				} else if (money.equals("")) {
					Toast.makeText(Manage.this, "总价格不能为空", Toast.LENGTH_LONG)
							.show();

				} else {
					// 判断想要入住的房间是否有人
					Cursor cursor = db.query("employee",
							new String[] { "name" }, "room_id= ?",
							new String[] { room_id }, null, null, null);
					if (cursor.moveToNext()) {
						String strName = cursor.getString(cursor
								.getColumnIndex("name"));
						System.out.println(strName);
						Toast.makeText(Manage.this, "该房间有顾客，请选择其它房间",
								Toast.LENGTH_LONG).show();
					} else {
						// 判断身份证号是否是18位
						if (id.length() == 18) {
							// 将入住信息插入数据库
							db.execSQL(
									"insert into employee values(?,?,?,?,?,?,?)",
									new String[] { name, sex, id, time, days,
											room_id, money });
							Toast.makeText(Manage.this, "入住成功", 10000).show();
						} else {
							Toast.makeText(Manage.this, "身份证号不正确，请重新输入",
									Toast.LENGTH_LONG).show();
							((EditText) findViewById(R.id.ruzhu_person_id))
									.setText("");
						}
					}
				}

			}
		});

		/**
		 * 查询java代码
		 */

		final EditText query_room_id = (EditText) findViewById(R.id.query_room_id);
		query_button = (Button) findViewById(R.id.query_button);
		final WebView wv = (WebView) findViewById(R.id.weserach);

		// 为查询按钮绑定监听器
		query_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获得用户输入的房间号
				String room_id_2 = query_room_id.getText().toString();
				// 查询用户输入的房间号是否与数据库中的房间号匹配
				Cursor cursor = db.query("employee", new String[] { "name",
						"sex", "id", "room_id", "time", "days", "money" },
						"room_id= ?", new String[] { room_id_2 }, null, null,
						null);
				if (cursor.moveToNext()) {
					// 获得数据库信息
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
					// 拼接HTML代码
					sb.append("<html>");
					sb.append("<head>");
					sb.append("</head>");
					sb.append("<body>");
					sb.append("房间号:" + query_room_id);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("姓名:" + query_name);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("性别:" + sex + "\n");
					sb.append("<br>");
					sb.append("<br>");
					sb.append("身份证:" + query_id);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("入住时间:" + query_time);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("入住天数:" + query_days);
					sb.append("<br>");
					sb.append("<br>");
					sb.append("总钱数:" + query_money);
					sb.append("</body>");
					sb.append("</html>");
					// 把内容加载到webview中让其显示查询信息
					wv.loadDataWithBaseURL(null, sb.toString(), "text/html",
							"utf-8", null);
				} else {
					// 没有人入住时显示提示信息
					Toast.makeText(Manage.this, "该房间没有入住信息！", 5000).show();
					wv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
				}
			}
		});
		/**
		 * 报修Java代码
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
				Toast.makeText(Manage.this, "报修成功", 10000).show();
			}
		});
		rep_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rep_id.setText("");
				rep_status.setText("");

			}
		});

		// 退房代码
		final EditText exits_name = (EditText) findViewById(R.id.exits_name);
		final EditText exits_room_id = (EditText) findViewById(R.id.exits_room_id);

		exits_sure = (Button) findViewById(R.id.exits_sure);
		exits_query = (Button) findViewById(R.id.exits_query);

		// 取得当前房间编号的用户名
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

				// 判断该房间的用户名是否与输入的用户名相等
				if (strNameid.equals(name_demo)) {
					// 删除该房间的登记信息
					db.delete("employee", "room_id like ?",
							new String[] { room_id1 });
					exits_room_id.setText("");
					exits_name.setText("");
					Toast.makeText(Manage.this, "删除成功", 10000).show();
				} else {
					Toast.makeText(Manage.this, "删除不成功", 10000).show();
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
