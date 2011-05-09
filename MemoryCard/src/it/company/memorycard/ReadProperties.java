package it.company.memorycard;

import java.util.Properties;
import java.io.*;

public class ReadProperties {
	private static final String PROP_FILE = "application.properties";
	String srcDirectory;
	private MsgLog logger;

	public String readSourceFile() {
		try {
			InputStream is = ReadProperties.class
					.getResourceAsStream(PROP_FILE);
			Properties prop = new Properties();
			prop.load(is);
			srcDirectory = prop.getProperty("SourceDirectory");
			
			
			is.close();
			/* code to use values read from the file */
		} catch (Exception e) {
			logger.write("Errore di lettura da " + PROP_FILE + " file.");
			
		}
		return srcDirectory;
	}
}
