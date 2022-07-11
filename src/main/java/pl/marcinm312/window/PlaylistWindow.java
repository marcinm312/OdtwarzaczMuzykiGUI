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
import java.util.stream.Collectors;

import javax.swing.*;

public class PlaylistWindow extends JFrame implements ActionListener {

	private final transient Playlist playlist;
	private final JPanel jPanel;
	private JButton sortByTitleAscButton;
	private JButton sortByTitleDescButton;
	private JButton sortByPerformerAscButton;
	private JButton sortByPerformerDescButton;
	private JButton sortByYearAscButton;
	private JButton sortByYearDescButton;
	private JButton addSongButton;
	private JButton playAllButton;
	private JButton stopButton;
	private JButton refreshButton;
	private List<JButton> moveUpButtons;
	private List<JButton> moveDownButtons;
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
		GridLayout gridLayout = new GridLayout(playlist.getSongsList().size() + 3, 7);
		jPanel.setLayout(gridLayout);
		moveUpButtons = new ArrayList<>();
		moveDownButtons = new ArrayList<>();
		moveButtons = new ArrayList<>();
		copyButtons = new ArrayList<>();
		removeButtons = new ArrayList<>();
		playSongButtons = new ArrayList<>();

		GridLayout sortByTitleGridLayout = new GridLayout(1, 2);
		JPanel sortByTitleJPanel = new JPanel();
		sortByTitleJPanel.setLayout(sortByTitleGridLayout);

		sortByTitleAscButton = new JButton("˄");
		sortByTitleJPanel.add(sortByTitleAscButton);
		sortByTitleAscButton.addActionListener(this);

		sortByTitleDescButton = new JButton("˅");
		sortByTitleJPanel.add(sortByTitleDescButton);
		sortByTitleDescButton.addActionListener(this);

		jPanel.add(sortByTitleJPanel);

		GridLayout sortByPerformerGridLayout = new GridLayout(1, 2);
		JPanel sortByPerformerJPanel = new JPanel();
		sortByPerformerJPanel.setLayout(sortByPerformerGridLayout);

		sortByPerformerAscButton = new JButton("˄");
		sortByPerformerJPanel.add(sortByPerformerAscButton);
		sortByPerformerAscButton.addActionListener(this);

		sortByPerformerDescButton = new JButton("˅");
		sortByPerformerJPanel.add(sortByPerformerDescButton);
		sortByPerformerDescButton.addActionListener(this);

		jPanel.add(sortByPerformerJPanel);

		GridLayout sortByYearGridLayout = new GridLayout(1, 2);
		JPanel sortByYearJPanel = new JPanel();
		sortByYearJPanel.setLayout(sortByYearGridLayout);

		sortByYearAscButton = new JButton("˄");
		sortByYearJPanel.add(sortByYearAscButton);
		sortByYearAscButton.addActionListener(this);

		sortByYearDescButton = new JButton("˅");
		sortByYearJPanel.add(sortByYearDescButton);
		sortByYearDescButton.addActionListener(this);

		jPanel.add(sortByYearJPanel);

		createEmptyLabels(5);

		JLabel titleHeaderLabel = new JLabel();
		titleHeaderLabel.setForeground(Color.red);
		jPanel.add(titleHeaderLabel);
		titleHeaderLabel.setText("Tytuł");
		titleHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel performerHeaderLabel = new JLabel();
		performerHeaderLabel.setForeground(Color.red);
		jPanel.add(performerHeaderLabel);
		performerHeaderLabel.setText("Wykonawca");
		performerHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel yearHeaderLabel = new JLabel();
		yearHeaderLabel.setForeground(Color.red);
		jPanel.add(yearHeaderLabel);
		yearHeaderLabel.setText("Rok wydania");
		yearHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);

		createEmptyLabels(5);

		for (Song song : playlist.getSongsList()) {

			JLabel titleLabel = new JLabel();
			jPanel.add(titleLabel);
			titleLabel.setText(song.getTitle());

			JLabel performerLabel = new JLabel();
			jPanel.add(performerLabel);
			performerLabel.setText(song.getPerformer());

			JLabel yearLabel = new JLabel();
			jPanel.add(yearLabel);
			yearLabel.setText(song.getYear() + "");

			GridLayout moveUpOrDownGridLayout = new GridLayout(1, 2);
			JPanel moveUpOrDownJPanel = new JPanel();
			moveUpOrDownJPanel.setLayout(moveUpOrDownGridLayout);

			JButton moveUpButton = new JButton("˄");
			moveUpButtons.add(moveUpButton);
			moveUpOrDownJPanel.add(moveUpButton);
			moveUpButton.addActionListener(this);

			JButton moveDownButton = new JButton("˅");
			moveDownButtons.add(moveDownButton);
			moveUpOrDownJPanel.add(moveDownButton);
			moveDownButton.addActionListener(this);

			jPanel.add(moveUpOrDownJPanel);

			JButton moveButton = new JButton("Przenieś");
			moveButtons.add(moveButton);
			jPanel.add(moveButton);
			moveButton.addActionListener(this);

			JButton copyButton = new JButton("Kopiuj");
			copyButtons.add(copyButton);
			jPanel.add(copyButton);
			copyButton.addActionListener(this);

			JButton removeButton = new JButton("Usuń");
			removeButtons.add(removeButton);
			jPanel.add(removeButton);
			removeButton.addActionListener(this);

			JButton playSongButton = new JButton("Odtwórz");
			playSongButtons.add(playSongButton);
			jPanel.add(playSongButton);
			playSongButton.addActionListener(this);
		}

		addSongButton = new JButton("Dodaj utwór");
		jPanel.add(addSongButton);
		addSongButton.addActionListener(this);

		refreshButton = new JButton("Odśwież");
		jPanel.add(refreshButton);
		refreshButton.addActionListener(this);

		playAllButton = new JButton("Odtwórz wszystko");
		jPanel.add(playAllButton);
		playAllButton.addActionListener(this);

		stopButton = new JButton("Zatrzymaj");
		jPanel.add(stopButton);
		stopButton.addActionListener(this);

		createEmptyLabels(4);
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

		if (sortActions(eventSource)) return;

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

		actionsForEverySong(eventSource);
	}

	private void actionsForEverySong(Object eventSource) {
		int i = 0;
		while (i < moveButtons.size()) {
			if (eventSource == moveUpButtons.get(i)) {
				moveUpButtonAction(i);
				return;
			}
			if (eventSource == moveDownButtons.get(i)) {
				moveDownButtonAction(i);
				return;
			}
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
				return;
			}
			if (eventSource == playSongButtons.get(i)) {
				playSongButtonAction(i);
				return;
			}
			i++;
		}
	}

	private boolean sortActions(Object eventSource) {
		if (eventSource == sortByTitleAscButton) {
			sortByTitleAscButtonAction();
			return true;
		}
		if (eventSource == sortByTitleDescButton) {
			sortByTitleDescButtonAction();
			return true;
		}
		if (eventSource == sortByPerformerAscButton) {
			sortByPerformerAscButtonAction();
			return true;
		}
		if (eventSource == sortByPerformerDescButton) {
			sortByPerformerDescButtonAction();
			return true;
		}
		if (eventSource == sortByYearAscButton) {
			sortByYearAscButtonAction();
			return true;
		}
		if (eventSource == sortByYearDescButton) {
			sortByYearDescButtonAction();
			return true;
		}
		return false;
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
		fillWindow();
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

		List<Playlist> playlistList = getPlaylistListWithoutCurrent();
		List<String> playlistNamesList = getPlaylistNamesList(playlistList);
		if (!playlistNamesList.isEmpty()) {
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
		} else {
			UIUtils.showMessageDialog("Nie ma innej playlisty!");
		}
	}

	private void moveDownButtonAction(int i) {

		try {
			playlist.moveSongDown(i);
			fillWindow();
		} catch (Exception e) {
			UIUtils.showMessageDialog(e.getMessage());
		}
	}

	private void moveUpButtonAction(int i) {

		try {
			playlist.moveSongUp(i);
			fillWindow();
		} catch (Exception e) {
			UIUtils.showMessageDialog(e.getMessage());
		}
	}

	private List<Playlist> getPlaylistListWithoutCurrent() {

		List<Playlist> playlistList = MainWindow.getPlaylistList();
		return playlistList.stream().filter(playlistItem -> !playlistItem.getName().equals(this.playlist.getName()))
				.collect(Collectors.toList());
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

	private void sortByTitleAscButtonAction() {
		playlist.sortPlaylistByTitleAscending();
		fillWindow();
	}

	private void sortByTitleDescButtonAction() {
		playlist.sortPlaylistByTitleDescending();
		fillWindow();
	}

	private void sortByPerformerAscButtonAction() {
		playlist.sortPlaylistByPerformerAscending();
		fillWindow();
	}

	private void sortByPerformerDescButtonAction() {
		playlist.sortPlaylistByPerformerDescending();
		fillWindow();
	}

	private void sortByYearAscButtonAction() {
		playlist.sortPlaylistByYearAscending();
		fillWindow();
	}

	private void sortByYearDescButtonAction() {
		playlist.sortPlaylistByYearDescending();
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
