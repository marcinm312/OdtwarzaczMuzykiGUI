package pl.marcinm312;

import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener {

	private final JPanel jPanel;
	private static final List<Playlist> playlistList = new ArrayList<>();
	private List<JButton> playlistShowButtons;
	private List<JButton> playlistRemoveButtons;
	private List<JButton> playlistSaveButtons;
	private JButton addPlaylistButton;
	private JButton loadPlaylistButton;
	private JButton showAboutButton;
	private final String fileSeparator = FileSystems.getDefault().getSeparator();

	public MainWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//Default look and feel
		}
		setTitle("Odtwarzacz 4.4");
		jPanel = new JPanel();
		add(jPanel);
		fillWindow();
	}

	public static List<Playlist> getPlaylistList() {
		return playlistList;
	}

	private void fillWindow() {

		jPanel.removeAll();
		GridLayout layout = new GridLayout(playlistList.size() + 1, 4);
		jPanel.setLayout(layout);
		playlistShowButtons = new ArrayList<>();
		playlistRemoveButtons = new ArrayList<>();
		playlistSaveButtons = new ArrayList<>();

		for (Playlist playlist : playlistList) {

			JLabel labelWithName = new JLabel();
			jPanel.add(labelWithName);
			labelWithName.setText(playlist.getName());

			JButton playlistShowButton = new JButton("Wyświetl playlistę");
			playlistShowButtons.add(playlistShowButton);
			jPanel.add(playlistShowButton);
			playlistShowButton.addActionListener(this);

			JButton playlistRemoveButton = new JButton("Usuń playlistę");
			playlistRemoveButtons.add(playlistRemoveButton);
			jPanel.add(playlistRemoveButton);
			playlistRemoveButton.addActionListener(this);

			JButton playlistSaveButton = new JButton("Zapisz playlistę do pliku");
			playlistSaveButtons.add(playlistSaveButton);
			jPanel.add(playlistSaveButton);
			playlistSaveButton.addActionListener(this);
		}

		addPlaylistButton = new JButton("Utwórz nową playlistę");
		jPanel.add(addPlaylistButton);
		addPlaylistButton.addActionListener(this);

		loadPlaylistButton = new JButton("Wczytaj playlistę z pliku TXT");
		jPanel.add(loadPlaylistButton);
		loadPlaylistButton.addActionListener(this);

		JLabel emptyLabel = new JLabel();
		jPanel.add(emptyLabel);
		emptyLabel.setText("");

		showAboutButton = new JButton("O programie...");
		jPanel.add(showAboutButton);
		showAboutButton.addActionListener(this);

		pack();
	}

	public void actionPerformed(ActionEvent actionEvent) {

		Object eventSource = actionEvent.getSource();

		if (eventSource == addPlaylistButton) {
			String playlistName = JOptionPane.showInputDialog(null, "Nazwa nowej playlisty:", "Nowa playlista",
					JOptionPane.WARNING_MESSAGE);
			try {
				Playlist playlist = new Playlist(playlistName);
				if (playlistList.contains(playlist)) {
					JOptionPane.showMessageDialog(null, "Playlista o takiej nazwie już istnieje!");
				} else {
					playlistList.add(playlist);
					fillWindow();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Wystąpił błąd: " + e.getMessage());
			}
		}

		if (eventSource == showAboutButton) {
			JOptionPane.showMessageDialog(null, "Odtwarzacz 4.4\n\nCopyright (C) 2022\nMarcin Michalczyk");
		}

		if (eventSource == loadPlaylistButton) {
			FileDialog loadPlaylistFileDialog = new FileDialog(this, "Wczytaj", FileDialog.LOAD);
			loadPlaylistFileDialog.setFilenameFilter((dir, name) -> name.toLowerCase().endsWith(".txt"));
			loadPlaylistFileDialog.setMultipleMode(false);
			loadPlaylistFileDialog.setVisible(true);
			String directory = loadPlaylistFileDialog.getDirectory();
			String file = loadPlaylistFileDialog.getFile();
			if (file.toLowerCase().endsWith(".txt")) {
				try (FileReader fr = new FileReader(directory + fileSeparator + file)) {
					BufferedReader bfr = new BufferedReader(fr);
					String playlistName = JOptionPane.showInputDialog(null, "Nazwa nowo wczytanej playlisty", "Nowa playlista",
							JOptionPane.WARNING_MESSAGE);
					try {
						Playlist playlist = new Playlist(playlistName);
						if (playlistList.contains(playlist)) {
							JOptionPane.showMessageDialog(null, "Playlista o takiej nazwie już istnieje!");
						} else {
							playlistList.add(playlist);
							String line;
							while ((line = bfr.readLine()) != null) {
								String[] details;
								details = line.split(";");
								try {
									playlist.addSong(new Song(details[0], details[1], details[2], details[3]));
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null,
											"Wystąpił błąd podczas dodawania utworu:\n"
													+ line + "\n"
													+ e.getMessage());
									break;
								}
							}
						}
						bfr.close();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Wystąpił błąd podczas tworzenia playlisty: " + e.getMessage());
						return;
					}
					fillWindow();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas wczytywania pliku: " + e.getMessage());
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null, "Wczytywany plik musi mieć format *.txt lub *.TXT.");
				return;
			}
		}

		int i = 0;
		while (i < playlistShowButtons.size()) {
			if (eventSource == playlistShowButtons.get(i)) {
				Playlist playlist = playlistList.get(i);
				PlaylistWindow playlistWindow = new PlaylistWindow(playlist);
				playlistWindow.setVisible(true);
				break;
			}
			i++;
		}

		i = 0;
		while (i < playlistRemoveButtons.size()) {
			if (eventSource == playlistRemoveButtons.get(i)) {
				playlistList.remove(i);
				break;
			}
			i++;
		}

		i = 0;
		while (i < playlistSaveButtons.size()) {
			if (eventSource == playlistSaveButtons.get(i)) {
				JOptionPane.showMessageDialog(null, "Pamiętaj dodać rozszerzenie *.txt do zapisywanego pliku!");
				try {
					FileDialog savePlaylistFileDialog = new FileDialog(this, "Zapisz", FileDialog.SAVE);
					savePlaylistFileDialog.setFilenameFilter((dir, name) -> name.endsWith(".txt") || name.endsWith(".TXT"));
					savePlaylistFileDialog.setVisible(true);
					String directory = savePlaylistFileDialog.getDirectory();
					String file = savePlaylistFileDialog.getFile();
					playlistList.get(i).savePlaylistToFile(file, directory);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Wystąpił nieoczekiwany błąd zapisu. Upewnij się, czy zapisywany plik ma format *.txt lub *.TXT.");
				}
				break;
			}
			i++;
		}
		fillWindow();
	}
}
