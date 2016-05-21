package com.easy.common.core.builder.freemarker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getToday() {
		String result = "";
		Date date = new Date();
		result = format(date);
		return result;
	}

	public static String format(Date date) {
		String format = "yyyyMMdd";
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}
}
