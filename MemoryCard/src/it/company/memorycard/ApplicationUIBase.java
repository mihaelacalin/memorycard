package it.company.memorycard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class ApplicationUIBase extends JFrame {

	protected JComboBox comboBox;
	protected JLabel immageLabel = new JLabel();
	protected JTextArea fileLabel = new JTextArea();

	protected JPanel panelN = new JPanel(new GridBagLayout());
	protected JPanel panelC = new JPanel(new GridBagLayout());
	protected JPanel panelS = new JPanel(new GridBagLayout());
	protected JButton startButton = new JButton("START");
	protected JButton logButton = new JButton("Visualizza log");
	protected JProgressBar pb = new JProgressBar();

	public ApplicationUIBase() {

		super("MemoryCard Application");
		setLayout(new BorderLayout());
		comboBox = new JComboBox();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(30, 20, 20, 20);
		panelN.add(comboBox, c);
		getContentPane().add(panelN, BorderLayout.NORTH);

		fileLabel.setSize(100, 100);
		immageLabel.setSize(100, 100);
		panelC.add(immageLabel);
		panelC.add(fileLabel);
		getContentPane().add(panelC, BorderLayout.CENTER);

		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 1;
		c2.gridy = 0;
		c2.insets = new Insets(30, 20, 20, 20);
		panelS.add(startButton, c2);
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 20;
		c3.gridy = 0;
		c3.insets = new Insets(30, 20, 20, 20);
		panelS.add(logButton, c3);
		pb.setValue(0);
		pb.setStringPainted(true);
		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 1;
		c4.gridy = 20;
		c4.insets = new Insets(30, 20, 20, 20);
		panelS.add(pb, c4);
		getContentPane().add(panelS, BorderLayout.SOUTH);
		setSize(700, 500);
	}

}
