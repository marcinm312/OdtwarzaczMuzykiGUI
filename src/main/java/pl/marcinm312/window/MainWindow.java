package pl.marcinm312.window;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import pl.marcinm312.model.Playlist;
import pl.marcinm312.model.Song;
import pl.marcinm312.utils.FilesPlayer;
import pl.marcinm312.utils.UIUtils;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener {

	private final JPanel jPanel;
	private static final List<Playlist> playlistList = new ArrayList<>();
	private List<JButton> playlistShowButtons;
	private List<JButton> playlistRemoveButtons;
	private List<JButton> playlistSaveButtons;
	private JButton addPlaylistButton;
	private JButton loadPlaylistButton;
	private JButton showAboutButton;
	private static FilesPlayer filesPlayer;
	private static final String APPLICATION_NAME = "Odtwarzacz 5.0";

	public MainWindow() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//Default look and feel
		}
		setTitle(APPLICATION_NAME);
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

		loadPlaylistButton = new JButton("Wczytaj playlistę z pliku CSV");
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

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		Object eventSource = actionEvent.getSource();

		if (eventSource == addPlaylistButton) {
			addPlaylistButtonAction();
			return;
		}
		if (eventSource == showAboutButton) {
			UIUtils.showMessageDialog(APPLICATION_NAME + "\n\nCopyright (C) 2022\nMarcin Michalczyk");
			return;
		}
		if (eventSource == loadPlaylistButton) {
			loadPlaylistButtonAction();
			return;
		}

		int i = 0;
		while (i < playlistShowButtons.size()) {
			if (eventSource == playlistShowButtons.get(i)) {
				playlistShowButtonAction(i);
				return;
			}
			if (eventSource == playlistRemoveButtons.get(i)) {
				playlistRemoveButtonAction(i);
				return;
			}
			if (eventSource == playlistSaveButtons.get(i)) {
				playlistSaveButtonAction(i);
				return;
			}
			i++;
		}
	}

	private void playlistSaveButtonAction(int i) {

		try {
			File file = UIUtils.getFileFromFileChooser("Pliki CSV", "csv", true);
			if (file != null) {
				playlistList.get(i).savePlaylistToFile(file);
			}
		} catch (IOException e) {
			UIUtils.showMessageDialog("Wystąpił nieoczekiwany błąd zapisu. Upewnij się, czy zapisywany plik ma format CSV");
		}
	}

	private void playlistRemoveButtonAction(int i) {
		playlistList.remove(i);
	}

	private void playlistShowButtonAction(int i) {

		Playlist playlist = playlistList.get(i);
		PlaylistWindow playlistWindow = new PlaylistWindow(playlist);
		playlistWindow.setVisible(true);
	}

	private void loadPlaylistButtonAction() {

		File file = UIUtils.getFileFromFileChooser("Pliki CSV", "csv", false);
		if (file != null) {
			try (FileReader fr = new FileReader(file)) {
				createNewPlaylistFromFile(fr);
			} catch (Exception e) {
				UIUtils.showMessageDialog("Wystąpił błąd podczas wczytywania pliku: " + e.getMessage());
			}
		}
	}

	private void createNewPlaylistFromFile(FileReader fr) {

		try (BufferedReader bfr = new BufferedReader(fr)) {
			String playlistName = JOptionPane.showInputDialog(null, "Nazwa nowo wczytanej playlisty", "Nowa playlista",
					JOptionPane.WARNING_MESSAGE);
			Playlist playlist = new Playlist(playlistName);
			if (playlistList.contains(playlist)) {
				UIUtils.showMessageDialog("Playlista o takiej nazwie już istnieje!");
			} else {
				CSVParser parser = new CSVParserBuilder()
						.withSeparator(';')
						.withEscapeChar('#')
						.withIgnoreQuotations(false)
						.build();
				CSVReader csvReader = new CSVReaderBuilder(bfr)
						.withSkipLines(1)
						.withCSVParser(parser)
						.build();
				readPlaylistFromFile(csvReader, playlist);
				if (!playlist.getSongsList().isEmpty()) {
					playlistList.add(playlist);
					fillWindow();
				}
			}
		} catch (Exception e) {
			UIUtils.showMessageDialog("Wystąpił błąd podczas tworzenia playlisty: " + e.getMessage());
		}
	}

	private void readPlaylistFromFile(CSVReader csvReader, Playlist playlist) throws CsvValidationException, IOException {

		String[] line;
		while ((line = csvReader.readNext()) != null) {
			try {
				playlist.addSong(new Song(line));
			} catch (Exception e) {
				playlist.setSongsList(new ArrayList<>());
				UIUtils.showMessageDialog("Wystąpił błąd podczas dodawania utworu:\n"
						+ Arrays.toString(line) + "\n"
						+ e.getMessage());
				return;
			}
		}
	}

	private void addPlaylistButtonAction() {

		String playlistName = JOptionPane.showInputDialog(null, "Nazwa nowej playlisty:", "Nowa playlista",
				JOptionPane.WARNING_MESSAGE);
		try {
			Playlist playlist = new Playlist(playlistName);
			if (playlistList.contains(playlist)) {
				UIUtils.showMessageDialog("Playlista o takiej nazwie już istnieje!");
			} else {
				playlistList.add(playlist);
				fillWindow();
			}
		} catch (Exception e) {
			UIUtils.showMessageDialog("Wystąpił błąd: " + e.getMessage());
		}
	}

	public static FilesPlayer getFilesPlayer() {
		return filesPlayer;
	}

	public static void setFilesPlayer(FilesPlayer filesPlayer) {
		MainWindow.filesPlayer = filesPlayer;
	}
}
