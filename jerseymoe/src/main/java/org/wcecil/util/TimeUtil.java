package org.wcecil.util;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtil {

	public static Long getNow() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
	}

}
