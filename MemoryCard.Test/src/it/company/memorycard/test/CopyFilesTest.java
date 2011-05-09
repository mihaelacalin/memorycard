package it.company.memorycard.test;

import it.company.memorycard.CopyDirectoryTask;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class CopyFilesTest {
	
	
	@Test
	public void destinationFileMustExistOnlyAfterCopy() throws IOException{
		new File("/home/mihaela/dest/file1").delete();
		new File("/home/mihaela/dest/file2").delete();
		new File("/home/mihaela/dest").delete();
		Assert.assertFalse(new File("/home/mihaela/dest/file1").exists());
		new CopyDirectoryTask(new File("/home/mihaela/filesProva"), new File("/home/mihaela/dest")).copyDirectory(new File("/home/mihaela/filesProva"), new File("/home/mihaela/dest"));
		Assert.assertTrue(new File("/home/mihaela/dest/file1").exists());
	}

}
