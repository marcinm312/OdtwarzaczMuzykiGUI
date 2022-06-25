package pl.marcinm312;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.UnsupportedLookAndFeelException;

public class PlaylistWindow extends JFrame implements ActionListener {

	private final transient Playlist playlist;
	private final JPanel jPanel;
	private JButton addSongButton;
	private JButton sortButton;
	private JButton playAllButton;
	private JButton stopButton;
	private List<JButton> moveButtonsList;
	private List<JButton> copyButtonsList;
	private List<JButton> removeSongButtonsList;
	private List<JButton> playSongButtons;
	private transient FilesPlayer filesPlayer;

	public PlaylistWindow(Playlist lista2) {
		this.playlist = lista2;
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
				 IllegalAccessException e) {
			// handle exception
		}
		setTitle("Playlista " + playlist.getName() + ":");
		jPanel = new JPanel();
		add(jPanel);
		wypelnijPanel();
	}

	public void wypelnijPanel() {
		jPanel.removeAll();
		GridLayout layout2 = new GridLayout(playlist.getSongsList().size() + 2, 7);
		jPanel.setLayout(layout2);
		moveButtonsList = new ArrayList<>();
		copyButtonsList = new ArrayList<>();
		removeSongButtonsList = new ArrayList<>();
		playSongButtons = new ArrayList<>();

		JLabel n1 = new JLabel();
		n1.setForeground(Color.red);
		jPanel.add(n1);
		n1.setText("Tytuł");

		JLabel n2 = new JLabel();
		n2.setForeground(Color.red);
		jPanel.add(n2);
		n2.setText("Wykonawca");

		JLabel n3 = new JLabel();
		n3.setForeground(Color.red);
		jPanel.add(n3);
		n3.setText("Rok wydania");

		JLabel n4 = new JLabel();
		jPanel.add(n4);
		n4.setText("");

		JLabel n5 = new JLabel();
		jPanel.add(n5);
		n5.setText("");

		JLabel n6 = new JLabel();
		jPanel.add(n6);
		n6.setText("");

		JLabel n7 = new JLabel();
		jPanel.add(n7);
		n7.setText("");

		for (int i = 0; i < playlist.getSongsList().size(); i++) {
			JLabel tytul = new JLabel();
			jPanel.add(tytul);
			tytul.setText(playlist.getSongsList().get(i).getTitle());

			JLabel wykonawca = new JLabel();
			jPanel.add(wykonawca);
			wykonawca.setText(playlist.getSongsList().get(i).getPerformer());

			JLabel rok = new JLabel();
			jPanel.add(rok);
			rok.setText(playlist.getSongsList().get(i).getPublicationYear() + "");

			JButton przyciskPrzenies = new JButton("Przenieś utwór do ...");
			moveButtonsList.add(przyciskPrzenies);
			jPanel.add(przyciskPrzenies);
			przyciskPrzenies.addActionListener(this);

			JButton przyciskKopiuj = new JButton("Kopiuj utwór do ...");
			copyButtonsList.add(przyciskKopiuj);
			jPanel.add(przyciskKopiuj);
			przyciskKopiuj.addActionListener(this);

			JButton przyciskUsun = new JButton("Usuń utwór z playlisty");
			removeSongButtonsList.add(przyciskUsun);
			jPanel.add(przyciskUsun);
			przyciskUsun.addActionListener(this);

			JButton przyciskOdtworz = new JButton("Odtwórz utwór");
			playSongButtons.add(przyciskOdtworz);
			jPanel.add(przyciskOdtworz);
			przyciskOdtworz.addActionListener(this);

			pack();
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

		JLabel n9 = new JLabel();
		jPanel.add(n9);
		n9.setText("");

		JLabel n10 = new JLabel();
		jPanel.add(n10);
		n10.setText("");

		pack();
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent arg0) {
		Object zrodlo = arg0.getSource();
		if (zrodlo == addSongButton) {
			try {
				String utwor1 = JOptionPane.showInputDialog(null, "Tytuł utworu:", "Nowy utwór",
						JOptionPane.WARNING_MESSAGE);
				if ((utwor1 != null) && (utwor1.length() > 0)) {
					String wykonawca1 = JOptionPane.showInputDialog(null, "Wykonawca utworu:", "Nowy utwór",
							JOptionPane.WARNING_MESSAGE);
					if ((wykonawca1 != null) && (wykonawca1.length() > 0)) {
						String rok1str = JOptionPane.showInputDialog(null, "Rok wydania utworu:", "Nowy utwór",
								JOptionPane.WARNING_MESSAGE);
						if ((rok1str != null) && (rok1str.length() > 0)) {
							try {
								JOptionPane.showMessageDialog(null, "Obsługiwane są tylko pliki MP3.");
								FileDialog fdwczytaj = new FileDialog(this, "Wczytaj", FileDialog.LOAD);
								fdwczytaj.setFilenameFilter((dir, name) -> name.endsWith(".mp3") || name.endsWith(".MP3"));
								fdwczytaj.setVisible(true);
								String katalog = fdwczytaj.getDirectory();
								String plik = fdwczytaj.getFile();
								if ((plik != null) && (plik.length() > 0)) {
									String filePath = katalog + "\\" + plik;
									playlist.addSong(new Song(utwor1, wykonawca1, rok1str, filePath));
									wypelnijPanel();
								}

							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Podano niewłaściwe lub niewystarczające dane!");
								return;
							}
						}
					}
				}
			} catch (Exception b) {
				JOptionPane.showMessageDialog(null, "Podano niewłaściwe dane!");
				return;
			}
		}
		if (zrodlo == sortButton) {
			playlist.sortPlaylist();
		}
		if (zrodlo == stopButton) {
			filesPlayer.stop();

		}
		if (zrodlo == playAllButton) {
			try {
				filesPlayer = new FilesPlayer(playlist.getSongsList());
				filesPlayer.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Załadowano nieprawidłowy plik!");
				return;
			}
		}
		int i = 0;
		List<Playlist> playlistList;
		while (i < moveButtonsList.size()) {
			if (zrodlo == moveButtonsList.get(i)) {
				playlistList = MainWindow.getPlaylistList();
				List<String> playlistNamesList = new ArrayList<>();
				for (Playlist playlistItem : playlistList) {
					playlistNamesList.add(playlistItem.getName());
				}
				String[] playlistNamesArray = playlistNamesList.toArray(new String[0]);
				String input2 = (String) JOptionPane.showInputDialog(null,
						"Wybierz playlistę, do której chcesz przenieść utwór:", "Wybór playlisty",
						JOptionPane.QUESTION_MESSAGE, null, playlistNamesArray, playlistNamesArray[0]);
				if ((input2 != null) && (input2.length() > 0)) {
					List<String> temp = Arrays.asList(playlistNamesArray);
					int a = temp.indexOf(input2);
					Song xyz = playlist.getSongsList().get(i);
					playlistList.get(a).addSong(xyz);
					try {
						playlist.removeSong(i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			}
			i++;
		}
		i = 0;

		while (i < copyButtonsList.size()) {
			if (zrodlo == copyButtonsList.get(i)) {
				playlistList = MainWindow.getPlaylistList();
				List<String> playlistNamesList = new ArrayList<>();
				for (Playlist playlistItem : playlistList) {
					playlistNamesList.add(playlistItem.getName());
				}
				String[] playlistNamesArray = playlistNamesList.toArray(new String[0]);
				String input = (String) JOptionPane.showInputDialog(null,
						"Wybierz playlistę, do której chcesz skopiować utwór:", "Wybór playlisty",
						JOptionPane.QUESTION_MESSAGE, null, playlistNamesArray, playlistNamesArray[0]);
				if ((input != null) && (input.length() > 0)) {
					List<String> temp = Arrays.asList(playlistNamesArray);
					int a = temp.indexOf(input);
					Song xyz = playlist.getSongsList().get(i);
					playlistList.get(a).addSong(xyz);
				}
				break;
			}
			i++;
		}
		i = 0;

		while (i < removeSongButtonsList.size()) {
			if (zrodlo == removeSongButtonsList.get(i)) {
				try {
					playlist.removeSong(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			i++;
		}
		i = 0;

		while (i < playSongButtons.size()) {
			if (zrodlo == playSongButtons.get(i)) {
				try {
					List<Song> songToPlay = Collections.singletonList(playlist.getSongsList().get(i));
					filesPlayer = new FilesPlayer(songToPlay);
					filesPlayer.start();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Załadowano nieprawidłowy plik!");
					break;
				}
				break;
			}
			i++;
		}
		wypelnijPanel();
	}
}
