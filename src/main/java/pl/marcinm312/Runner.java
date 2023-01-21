package pl.marcinm312;

import pl.marcinm312.window.MainWindow;

import javax.swing.JFrame;

public class Runner {

	public static void main(String[] args) {

		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
