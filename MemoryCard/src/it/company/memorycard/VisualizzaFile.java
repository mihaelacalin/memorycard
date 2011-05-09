package it.company.memorycard;

import java.io.*;

public class VisualizzaFile {
	File di;
	File fl[];
	int i;

	public VisualizzaFile() {

	}

	public VisualizzaFile(String folder) {
		di = new File(folder);
		fl = di.listFiles();
	}

//	public void visualizza() throws IOException {
//		for (i = 0; i < fl.length; i++) {
//
//			try {
//				FileInputStream fis = new FileInputStream(
//						fl[i].getCanonicalFile());
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			String filename = fl[i].getName().toString();
//
//			FileOutputStream file = new FileOutputStream(
//					"/home/mihaela/report.txt", true);
//			PrintStream printtofile = new PrintStream(file);
//
//			printtofile.println(filename);
//			printtofile.close();
//			file.close();
//
//		}
//
//	}

	public String[] getFileNames() {
		String[] result = new String[fl.length];

		int i = 0;
		for (File file : fl) {
			result[i++] = file.getName();

		}

		return result;
	}

	public String readFromFile(String filename) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filename));
		String nextLine = " ";
		StringBuffer sb = new StringBuffer();
		while ((nextLine = br.readLine()) != null) {
			sb.append(nextLine);
			sb.append("\n");
		}
		return sb.toString();
	}

}
