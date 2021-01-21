package com.example.hotel;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Time {
	public String currentTime() {
		String time;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = sdf.format(new Date());
		return time;
	}
}
