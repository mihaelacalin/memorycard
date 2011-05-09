package it.company.memorycard;

import javax.swing.*;


public class ApplicationUITest {
	public static void main(String []args){
		ApplicationUIBase comboBoxFrame=new ApplicationUI(new ReadProperties().readSourceFile());
		comboBoxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		comboBoxFrame.setVisible(true);
	}

}
