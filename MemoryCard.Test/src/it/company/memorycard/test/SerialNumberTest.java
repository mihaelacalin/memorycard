package it.company.memorycard.test;

import it.company.memorycard.SerialNumber;

import org.junit.Assert;
import org.junit.Test;

public class SerialNumberTest {

	@Test
	public void testSNnotNUll() {
		String serial = SerialNumber.generateSerialNumber();
		Assert.assertNotNull(serial);

	}

	@Test
	public void testSNlength() {
		String serial = SerialNumber.generateSerialNumber();
		Assert.assertEquals(14, serial.length());

	}

	@Test
	public void testSNunivoco() throws InterruptedException {
		String serial1 = SerialNumber.generateSerialNumber();
		Thread.sleep(1000);
		String serial2 = SerialNumber.generateSerialNumber();
		Assert.assertFalse(serial1.equals(serial2));
	}
	@Test
	public void testSNvisualizza(){
		String serial1 = SerialNumber.generateSerialNumber();
		System.out.println(serial1);
	}

}
