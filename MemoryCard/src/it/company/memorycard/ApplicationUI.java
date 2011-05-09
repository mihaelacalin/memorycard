package it.company.memorycard;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.awt.Image;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.util.*;
import java.util.concurrent.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ApplicationUI extends ApplicationUIBase {

	private String basePath = "";
	private String comboContent[] = { "PC Card", "CompactFlash I",
			 "SmartMedia", "MemoryStick" , "CompactFlash II" };
	private String recicledSerialNumber, serial;
	private MsgLog logger;


	public ApplicationUI(String basePath) {
		this.basePath = basePath;
		this.logger = new MsgLog(basePath + "log.txt");
		this.logger.write("Inizio applicazione Masterizza MemoryCard");
		for (Object item : comboContent)
			comboBox.addItem(item);
		int selectedIndex = comboBox.getSelectedIndex();
		showFileList(comboContent[selectedIndex]);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					pb.setValue(0);
					int selectedIndex = comboBox.getSelectedIndex();
					showIconForMemoryCard(comboContent[selectedIndex]);
					showFileList(comboContent[selectedIndex]);
					
				}
			}

		}// fine classe interna
		);// fine chiamata a addItemListener

		final String path = this.basePath;

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = comboBox.getSelectedIndex();
				copySerialNumber(path, comboContent[selectedIndex]);
				startCopyFiles(path, comboContent[selectedIndex]);

			}
		});

		showIconForMemoryCard(comboContent[0]);

		logButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showLog();
				} catch (IOException e1) {
					e1.getMessage();
				}

			}
		});
	}

	protected String getSerialNumber() {

		if (serial == null)
			return serial = SerialNumber.generateSerialNumber();
		else
			return serial;
	}

	protected void copySerialNumber(String path, String memoryCardTypeName) {
		serial = recicledSerialNumber;
		String targetLocation = path + "memorycard/" + memoryCardTypeName;
		serial = getSerialNumber();
		File destinationDir = new File(targetLocation);
		if (!destinationDir.isDirectory()) {
			destinationDir.mkdirs();
		}
		File destination = new File(targetLocation + "/serial.txt");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(destination));
			out.write(serial);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	protected void startCopyFiles(String path, String memoryCardTypeName) {
		logger.write("inizio processo di masterizzazione - tipo MemoryCard: "
				+ memoryCardTypeName);

		String sourceLocation = path + memoryCardTypeName;
		String targetLocation = path + "memorycard/" + memoryCardTypeName;
		copySerialNumber(path, memoryCardTypeName);
		File source = new File(sourceLocation);
		File destination = new File(targetLocation);
		
		CopyDirectoryTask copia = new CopyDirectoryTask(source, destination) {

			public void done() {

				try {
					get();
					
					completedOk();
					

				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {

					completedKO(e.getCause());

				}
				logger.write("fine processo di masterizzazione");

			}
			

		};
		copia.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("progress".equals(evt.getPropertyName())) {

					pb.setValue((Integer) evt.getNewValue());

				}
			}
		});

		copia.execute();

	}

	private void completedOk() {
		JOptionPane.showMessageDialog(new JFrame(),
				"Masterizzazione avvenuta con successo." + "\n"
						+ " Il serial number associato alla memory card è "
						+ serial, "Messaggio", JOptionPane.PLAIN_MESSAGE);
		recicledSerialNumber = null;
		logger.write("copia dei file avvenuta con successo");
		logger.write("serial number associato: " + serial);
	}

	protected void completedKO(Throwable cause) {
		logger.write("si è verificato un'errore durante la masterizzazione");

		JOptionPane optionPane = new JOptionPane();
		if (optionPane.showConfirmDialog(new JFrame(),
				"Errore di masterizzazione. Reciclare il Serial Number calcolato: "
						+ serial + "?", "Attenzione",
				JOptionPane.YES_NO_CANCEL_OPTION) == 0) {

			logger.write("l'utente ha deciso di reciclare il serial number calcolato"
					+ serial);
			recicledSerialNumber = getSerialNumber();
		} else {

			logger.write("l'utente ha deciso di non reciclare il serial number calcolato "
					+ serial);
			recicledSerialNumber = null;

		}

	}

	protected void showIconForMemoryCard(String memoryCardTypeName) {
		String folder = basePath + memoryCardTypeName;
		ImageIcon icon = new ImageIcon(folder + "/icon.jpg");
		Image bigIcon = icon.getImage();
		icon.setImage(bigIcon.getScaledInstance(220, 152, Image.SCALE_DEFAULT));

		immageLabel.setIcon(icon);
	}

	protected void showFileList(String memoryCardTypeName) {
		String folder = basePath + memoryCardTypeName + "/contenuto";
		String[] files = new VisualizzaFile(folder).getFileNames();

		String fileList = "";
		for (String file : files) {
			fileList = fileList + file + "\n";
		}

		fileLabel.setText(fileList);
		fileLabel.setEditable(false);
	}

	protected void showLog() throws IOException {
		JFrame logFrame = new JFrame("Log");
		logFrame.setSize(700, 600);
		JPanel panel = new JPanel();

		JTextArea logContent = new JTextArea();
		JScrollPane areaScrollPane = new JScrollPane(logContent);
		panel.add(areaScrollPane);
		logFrame.setContentPane(panel);
		areaScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(650, 550));

		String file = basePath + "/log.txt";
		logContent.setText(new VisualizzaFile().readFromFile(file));
		logFrame.setVisible(true);

	}

	
	// public static void main(String[] args) {
	// if (args.length != 1) {
	// System.out
	// .println("Utilizzo: java ApplicationUI {percorso della cartella dei template}");
	// return;
	// }
	// String basePath = args[0];
	// ApplicationUIBase comboBoxFrame = new ApplicationUI(basePath);
	// comboBoxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// comboBoxFrame.setVisible(true);
	// }

}
