package it.company.memorycard;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MsgLog {
	private String logFile;

	public MsgLog(String logFile) {
		this.logFile = logFile;
	}

	public void write(String s) {
		write(logFile, s);
	}

	private static void write(String f, String s) {
		try {
			Date now = new Date();
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
			String currentTime = df.format(now);
			FileWriter aWriter = new FileWriter(f, true);
			aWriter.write(currentTime + " " + s + "\n");
			aWriter.flush();
			aWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
