package pl.marcinm312.window;

import pl.marcinm312.utils.FilesPlayer;
import pl.marcinm312.model.Playlist;
import pl.marcinm312.model.Song;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class PlaylistWindow extends JFrame implements ActionListener {

	private final transient Playlist playlist;
	private final JPanel jPanel;
	private JButton addSongButton;
	private JButton sortButton;
	private JButton playAllButton;
	private JButton stopButton;
	private List<JButton> moveButtons;
	private List<JButton> copyButtons;
	private List<JButton> removeButtons;
	private List<JButton> playSongButtons;
	private transient FilesPlayer filesPlayer;
	private final String fileSeparator = FileSystems.getDefault().getSeparator();
	private static final String NEW_SONG = "Nowy utwór";

	public PlaylistWindow(Playlist playlist) {
		this.playlist = playlist;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			//Default look and feel
		}
		setTitle("Playlista " + this.playlist.getName() + ":");
		jPanel = new JPanel();
		add(jPanel);
		fillWindow();
	}

	private void fillWindow() {

		jPanel.removeAll();
		GridLayout gridLayout = new GridLayout(playlist.getSongsList().size() + 2, 7);
		jPanel.setLayout(gridLayout);
		moveButtons = new ArrayList<>();
		copyButtons = new ArrayList<>();
		removeButtons = new ArrayList<>();
		playSongButtons = new ArrayList<>();

		JLabel titleHeaderLabel = new JLabel();
		titleHeaderLabel.setForeground(Color.red);
		jPanel.add(titleHeaderLabel);
		titleHeaderLabel.setText("Tytuł");

		JLabel performerHeaderLabel = new JLabel();
		performerHeaderLabel.setForeground(Color.red);
		jPanel.add(performerHeaderLabel);
		performerHeaderLabel.setText("Wykonawca");

		JLabel yearHeaderLabel = new JLabel();
		yearHeaderLabel.setForeground(Color.red);
		jPanel.add(yearHeaderLabel);
		yearHeaderLabel.setText("Rok wydania");

		createEmptyLabels(4);

		for (Song song : playlist.getSongsList()) {

			JLabel titleLabel = new JLabel();
			jPanel.add(titleLabel);
			titleLabel.setText(song.getTitle());

			JLabel performerLabel = new JLabel();
			jPanel.add(performerLabel);
			performerLabel.setText(song.getPerformer());

			JLabel yearLabel = new JLabel();
			jPanel.add(yearLabel);
			yearLabel.setText(song.getPublicationYear() + "");

			JButton moveButton = new JButton("Przenieś utwór do...");
			moveButtons.add(moveButton);
			jPanel.add(moveButton);
			moveButton.addActionListener(this);

			JButton copyButton = new JButton("Kopiuj utwór do...");
			copyButtons.add(copyButton);
			jPanel.add(copyButton);
			copyButton.addActionListener(this);

			JButton removeButton = new JButton("Usuń utwór z playlisty");
			removeButtons.add(removeButton);
			jPanel.add(removeButton);
			removeButton.addActionListener(this);

			JButton playSongButton = new JButton("Odtwórz utwór");
			playSongButtons.add(playSongButton);
			jPanel.add(playSongButton);
			playSongButton.addActionListener(this);
		}

		addSongButton = new JButton("Dodaj utwór");
		jPanel.add(addSongButton);
		addSongButton.addActionListener(this);

		sortButton = new JButton("Sortuj utwory wg tytułów");
		jPanel.add(sortButton);
		sortButton.addActionListener(this);

		JButton refreshButton = new JButton("Odśwież playlistę");
		jPanel.add(refreshButton);
		refreshButton.addActionListener(this);

		playAllButton = new JButton("Odtwórz wszystko");
		jPanel.add(playAllButton);
		playAllButton.addActionListener(this);

		stopButton = new JButton("Zatrzymaj odtwarzanie");
		jPanel.add(stopButton);
		stopButton.addActionListener(this);

		createEmptyLabels(2);
		pack();
	}

	private void createEmptyLabels(int amount) {
		for (int i = 1; i <= amount; i++) {
			JLabel emptyLabel = new JLabel();
			jPanel.add(emptyLabel);
			emptyLabel.setText("");
		}
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent actionEvent) {

		Object eventSource = actionEvent.getSource();

		if (eventSource == addSongButton) {
			String title = JOptionPane.showInputDialog(null, "Tytuł utworu:", NEW_SONG,
					JOptionPane.WARNING_MESSAGE);
			String performer = JOptionPane.showInputDialog(null, "Wykonawca utworu:", NEW_SONG,
					JOptionPane.WARNING_MESSAGE);
			String year = JOptionPane.showInputDialog(null, "Rok wydania utworu:", NEW_SONG,
					JOptionPane.WARNING_MESSAGE);

			JOptionPane.showMessageDialog(null, "Obsługiwane są tylko pliki MP3.");
			FileDialog songLoadFileDialog = new FileDialog(this, "Wczytaj", FileDialog.LOAD);
			songLoadFileDialog.setFilenameFilter((dir, name) -> name.toLowerCase().endsWith(".mp3"));
			songLoadFileDialog.setVisible(true);
			String directory = songLoadFileDialog.getDirectory();
			String file = songLoadFileDialog.getFile();
			String filePath = directory + fileSeparator + file;
			try {
				playlist.addSong(new Song(title, performer, year, filePath));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas dodawania utworu: " + e.getMessage());
				return;
			}
			fillWindow();
		}

		if (eventSource == sortButton) {
			playlist.sortPlaylist();
		}

		if (eventSource == stopButton) {
			filesPlayer.stop();
		}

		if (eventSource == playAllButton) {
			try {
				filesPlayer = new FilesPlayer(playlist.getSongsList());
				filesPlayer.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas odtwarzania listy: " + e.getMessage());
				return;
			}
		}

		List<Playlist> playlistList;

		int i = 0;
		while (i < moveButtons.size()) {
			if (eventSource == moveButtons.get(i)) {
				playlistList = MainWindow.getPlaylistList();
				List<String> playlistNamesList = new ArrayList<>();
				for (Playlist playlistItem : playlistList) {
					playlistNamesList.add(playlistItem.getName());
				}
				String[] playlistNamesArray = playlistNamesList.toArray(new String[0]);
				String choosePlaylistInput = (String) JOptionPane.showInputDialog(null,
						"Wybierz playlistę, do której chcesz przenieść utwór:", "Wybór playlisty",
						JOptionPane.QUESTION_MESSAGE, null, playlistNamesArray, playlistNamesArray[0]);
				if ((choosePlaylistInput != null) && (choosePlaylistInput.length() > 0)) {
					List<String> namesList = Arrays.asList(playlistNamesArray);
					int nameIndex = namesList.indexOf(choosePlaylistInput);
					Song song = playlist.getSongsList().get(i);
					playlistList.get(nameIndex).addSong(song);
					playlist.removeSong(i);
				}
				break;
			}
			i++;
		}

		i = 0;
		while (i < copyButtons.size()) {
			if (eventSource == copyButtons.get(i)) {
				playlistList = MainWindow.getPlaylistList();
				List<String> playlistNamesList = new ArrayList<>();
				for (Playlist playlistItem : playlistList) {
					playlistNamesList.add(playlistItem.getName());
				}
				String[] playlistNamesArray = playlistNamesList.toArray(new String[0]);
				String choosePlaylistInput = (String) JOptionPane.showInputDialog(null,
						"Wybierz playlistę, do której chcesz skopiować utwór:", "Wybór playlisty",
						JOptionPane.QUESTION_MESSAGE, null, playlistNamesArray, playlistNamesArray[0]);
				if ((choosePlaylistInput != null) && (choosePlaylistInput.length() > 0)) {
					List<String> namesList = Arrays.asList(playlistNamesArray);
					int nameIndex = namesList.indexOf(choosePlaylistInput);
					Song song = playlist.getSongsList().get(i);
					playlistList.get(nameIndex).addSong(song);
				}
				break;
			}
			i++;
		}

		i = 0;
		while (i < removeButtons.size()) {
			if (eventSource == removeButtons.get(i)) {
				playlist.removeSong(i);
				break;
			}
			i++;
		}

		i = 0;
		while (i < playSongButtons.size()) {
			if (eventSource == playSongButtons.get(i)) {
				Song song = playlist.getSongsList().get(i);
				try {
					List<Song> singletonSongList = Collections.singletonList(song);
					filesPlayer = new FilesPlayer(singletonSongList);
					filesPlayer.start();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas odtwarzania utworu:\n"
							+ song.toString() + "\n"
							+ e.getMessage());
				}
				break;
			}
			i++;
		}
		fillWindow();
	}
}
