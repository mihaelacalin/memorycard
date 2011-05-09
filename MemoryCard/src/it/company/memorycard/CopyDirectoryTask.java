package it.company.memorycard;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.swing.SwingWorker;

public class CopyDirectoryTask extends SwingWorker<Void, Void> {
	File sourceLocation, targetLocation;
	int currentValue;
	SerialNumber serial;
	public CopyDirectoryTask(File sourceLocation, File targetLocation) {
		this.sourceLocation = sourceLocation;
		this.targetLocation = targetLocation;
		// TODO Auto-generated constructor stub
	}

	public void copyDirectory(File sourceLocation, File targetLocation) throws IOException
			 {

		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			long totalBytes = in.available();
			long byteRead = 0;

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			try {
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);

				//	System.out.println("byte read" + byteRead);
					byteRead += len;
					currentValue = (int) (byteRead * 100 / totalBytes);
					setProgress(currentValue);

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.close();

		}

	}

	@Override
	protected Void doInBackground() throws Exception {
		int progress = 0;
		copyDirectory(sourceLocation, targetLocation);
		return null;
	}

}
