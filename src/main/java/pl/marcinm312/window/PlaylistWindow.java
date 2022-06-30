package pl.marcinm312.window;

import pl.marcinm312.utils.FilesPlayer;
import pl.marcinm312.model.Playlist;
import pl.marcinm312.model.Song;
import pl.marcinm312.utils.UIUtils;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

public class PlaylistWindow extends JFrame implements ActionListener {

	private final transient Playlist playlist;
	private final JPanel jPanel;
	private JButton addSongButton;
	private JButton sortButton;
	private JButton playAllButton;
	private JButton stopButton;
	private JButton refreshButton;
	private List<JButton> moveButtons;
	private List<JButton> copyButtons;
	private List<JButton> removeButtons;
	private List<JButton> playSongButtons;
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

		refreshButton = new JButton("Odśwież playlistę");
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

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		Object eventSource = actionEvent.getSource();

		if (eventSource == addSongButton) {
			addSongButtonAction();
			return;
		}
		if (eventSource == sortButton) {
			sortButtonAction();
			return;
		}
		if (eventSource == stopButton) {
			stopFilesPlayer();
			return;
		}
		if (eventSource == playAllButton) {
			playAllButtonAction();
			return;
		}
		if (eventSource == refreshButton) {
			refreshButtonAction();
			return;
		}

		int i = 0;
		while (i < moveButtons.size()) {
			if (eventSource == moveButtons.get(i)) {
				moveButtonAction(i);
				return;
			}
			if (eventSource == copyButtons.get(i)) {
				copyButtonAction(i);
				return;
			}
			if (eventSource == removeButtons.get(i)) {
				removeSongFromPlaylist(i);
				fillWindow();
				return;
			}
			if (eventSource == playSongButtons.get(i)) {
				playSongButtonAction(i);
				return;
			}
			i++;
		}
	}

	private void playSongButtonAction(int i) {
		Song song = playlist.getSongsList().get(i);
		try {
			List<Song> singletonSongList = Collections.singletonList(song);
			startFilesPlayer(singletonSongList);
		} catch (Exception e) {
			UIUtils.showMessageDialog("Wystąpił błąd podczas odtwarzania utworu:\n"
					+ song.toString() + "\n"
					+ e.getMessage());
		}
	}

	private void removeSongFromPlaylist(int i) {
		playlist.removeSong(i);
	}

	private void copyButtonAction(int i) {
		List<Playlist> playlistList = MainWindow.getPlaylistList();
		List<String> playlistNamesList = getPlaylistNamesList(playlistList);
		String[] playlistNamesArray = playlistNamesList.toArray(new String[0]);
		String choosePlaylistInput = (String) JOptionPane.showInputDialog(null,
				"Wybierz playlistę, do której chcesz skopiować utwór:", "Wybór playlisty",
				JOptionPane.QUESTION_MESSAGE, null, playlistNamesArray, playlistNamesArray[0]);
		if ((choosePlaylistInput != null) && (choosePlaylistInput.length() > 0)) {
			int nameIndex = playlistNamesList.indexOf(choosePlaylistInput);
			Song song = playlist.getSongsList().get(i);
			playlistList.get(nameIndex).addSong(song);
		}
		fillWindow();
	}

	private void moveButtonAction(int i) {
		List<Playlist> playlistList = MainWindow.getPlaylistList();
		List<String> playlistNamesList = getPlaylistNamesList(playlistList);
		String[] playlistNamesArray = playlistNamesList.toArray(new String[0]);
		String choosePlaylistInput = (String) JOptionPane.showInputDialog(null,
				"Wybierz playlistę, do której chcesz przenieść utwór:", "Wybór playlisty",
				JOptionPane.QUESTION_MESSAGE, null, playlistNamesArray, playlistNamesArray[0]);
		if ((choosePlaylistInput != null) && (choosePlaylistInput.length() > 0)) {
			int nameIndex = playlistNamesList.indexOf(choosePlaylistInput);
			Song song = playlist.getSongsList().get(i);
			playlistList.get(nameIndex).addSong(song);
			removeSongFromPlaylist(i);
		}
		fillWindow();
	}

	private List<String> getPlaylistNamesList(List<Playlist> playlistList) {
		List<String> playlistNamesList = new ArrayList<>();
		for (Playlist playlistItem : playlistList) {
			playlistNamesList.add(playlistItem.getName());
		}
		return playlistNamesList;
	}

	private void refreshButtonAction() {
		fillWindow();
	}

	private void playAllButtonAction() {
		try {
			startFilesPlayer(playlist.getSongsList());
		} catch (Exception e) {
			UIUtils.showMessageDialog("Wystąpił błąd podczas odtwarzania listy: " + e.getMessage());
		}
	}

	private void sortButtonAction() {
		playlist.sortPlaylist();
		fillWindow();
	}

	private void addSongButtonAction() {
		String title = JOptionPane.showInputDialog(null, "Tytuł utworu:", NEW_SONG,
				JOptionPane.WARNING_MESSAGE);
		String performer = JOptionPane.showInputDialog(null, "Wykonawca utworu:", NEW_SONG,
				JOptionPane.WARNING_MESSAGE);
		String year = JOptionPane.showInputDialog(null, "Rok wydania utworu:", NEW_SONG,
				JOptionPane.WARNING_MESSAGE);

		File file = UIUtils.getFileFromFileChooser("Pliki MP3", "mp3", false);
		try {
			playlist.addSong(new Song(title, performer, year, file));
		} catch (Exception e) {
			UIUtils.showMessageDialog("Wystąpił błąd podczas dodawania utworu: " + e.getMessage());
		}
		fillWindow();
	}

	private void stopFilesPlayer() {
		if (MainWindow.getFilesPlayer() != null && MainWindow.getFilesPlayer().isAlive()) {
			MainWindow.getFilesPlayer().stopPlayer();
		}
	}

	private void startFilesPlayer(List<Song> songList) {
		stopFilesPlayer();
		MainWindow.setFilesPlayer(new FilesPlayer(songList));
		MainWindow.getFilesPlayer().start();
	}
}
