package pl.marcinm312.utils;

import org.apache.commons.io.FilenameUtils;

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
			File file = jFileChooser.getSelectedFile();
			if (file != null && !FilenameUtils.getExtension(file.getName()).equalsIgnoreCase(filterExtension)) {
				file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + "." + filterExtension);
			}
			return file;
		} else {
			jFileChooser.showOpenDialog(null);
			return jFileChooser.getSelectedFile();
		}
	}
}
