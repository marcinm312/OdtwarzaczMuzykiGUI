package pl.marcinm312.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class UIUtils {

	private UIUtils() {

	}

	public static void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public static File getFileFromFileChooser(String filterDescription, String filterExtension, boolean isSaveDialog) {
		JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView());
		jFileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(filterDescription, filterExtension);
		jFileChooser.addChoosableFileFilter(extensionFilter);
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setMultiSelectionEnabled(false);
		if (isSaveDialog) {
			jFileChooser.showSaveDialog(null);
		} else {
			jFileChooser.showOpenDialog(null);
		}
		return jFileChooser.getSelectedFile();
	}
}
