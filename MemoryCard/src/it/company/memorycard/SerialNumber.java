package it.company.memorycard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialNumber {
	public static String generateSerialNumber() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String result = dateFormat.format(date);
		return result;

	}

}
