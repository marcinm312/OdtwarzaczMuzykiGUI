package pl.marcinm312;

import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWindow extends JFrame implements ActionListener {

	private final JPanel jPanel;
	private static final List<Playlist> playlistList = new ArrayList<>();
	private List<JButton> playlistShowButtons;
	private List<JButton> playlistDeleteButtons;
	private List<JButton> playlistSaveButtons;
	private JButton addPlaylistButton;
	private JButton loadPlaylistButton;
	private JButton showAboutButton;

	public MainWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
				 IllegalAccessException e) {

		}
		setTitle("Odtwarzacz 4.4");
		jPanel = new JPanel();
		add(jPanel);
		wypelnijPanel();
	}

	public static List<Playlist> getPlaylistList() {
		return playlistList;
	}

	public void wypelnijPanel() {
		jPanel.removeAll();
		GridLayout layout = new GridLayout(playlistList.size() + 1, 4);
		jPanel.setLayout(layout);
		playlistShowButtons = new ArrayList<>();
		playlistDeleteButtons = new ArrayList<>();
		playlistSaveButtons = new ArrayList<>();
		for (Playlist playlist : playlistList) {
			JLabel nazwa = new JLabel();
			jPanel.add(nazwa);
			nazwa.setText(playlist.getName());

			JButton przyciskWyswietl = new JButton("Wyświetl playlistę");
			playlistShowButtons.add(przyciskWyswietl);
			jPanel.add(przyciskWyswietl);
			przyciskWyswietl.addActionListener(this);

			JButton przyciskUsun = new JButton("Usuń playlistę");
			playlistDeleteButtons.add(przyciskUsun);
			jPanel.add(przyciskUsun);
			przyciskUsun.addActionListener(this);

			JButton przyciskZapisz = new JButton("Zapisz playlistę do pliku");
			playlistSaveButtons.add(przyciskZapisz);
			jPanel.add(przyciskZapisz);
			przyciskZapisz.addActionListener(this);
		}
		addPlaylistButton = new JButton("Utwórz nową playlistę");
		jPanel.add(addPlaylistButton);
		addPlaylistButton.addActionListener(this);

		loadPlaylistButton = new JButton("Wczytaj playlistę z pliku TXT");
		jPanel.add(loadPlaylistButton);
		loadPlaylistButton.addActionListener(this);

		JLabel n4 = new JLabel();
		jPanel.add(n4);
		n4.setText("");

		showAboutButton = new JButton("O programie...");
		jPanel.add(showAboutButton);
		showAboutButton.addActionListener(this);

		pack();
	}

	public void actionPerformed(ActionEvent arg0) {
		Object zrodlo = arg0.getSource();
		if (zrodlo == addPlaylistButton) {
			String nazwapl = JOptionPane.showInputDialog(null, "Nazwa nowej playlisty:", "Nowa playlista",
					JOptionPane.WARNING_MESSAGE);
			if ((nazwapl != null) && (nazwapl.length() > 0)) {
				try {
					Playlist playlist = new Playlist(nazwapl);
					if (playlistList.contains(playlist)) {
						JOptionPane.showMessageDialog(null, "Playlista o takiej nazwie już istnieje!");
					} else {
						playlistList.add(playlist);
						wypelnijPanel();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Wystąpił nieoczekiwany błąd.");
				}
			}
		}
		if (zrodlo == showAboutButton) {
			JOptionPane.showMessageDialog(null, "Odtwarzacz 4.4\n\nCopyright (C) 2022\nMarcin Michalczyk");
		}
		if (zrodlo == loadPlaylistButton) {

			FileDialog fdwczytaj = new FileDialog(this, "Wczytaj", FileDialog.LOAD);
			fdwczytaj.setFilenameFilter((dir, name) -> name.endsWith(".txt") || name.endsWith(".TXT"));
			fdwczytaj.setVisible(true);
			String directory = fdwczytaj.getDirectory();
			String file = fdwczytaj.getFile();
			try {
				FileReader fr = new FileReader(directory + "\\" + file);
				BufferedReader bfr = new BufferedReader(fr);
				String nazwapl = JOptionPane.showInputDialog(null, "Nazwa nowo wczytanej playlisty", "Nowa playlista",
						JOptionPane.WARNING_MESSAGE);
				if ((nazwapl != null) && (nazwapl.length() > 0)) {
					try {
						Playlist playlist = new Playlist(nazwapl);
						if (playlistList.contains(playlist)) {
							JOptionPane.showMessageDialog(null, "Playlista o takiej nazwie już istnieje!");
						} else {
							playlistList.add(playlist);
							String linia;
							while ((linia = bfr.readLine()) != null) {
								String[] details;
								details = linia.split(";");
								try {
									playlist.addSong(new Song(details[0], details[1], details[2], details[3]));
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null,
											"Nie dodano pliku do playlisty, ponieważ wpisane dane nie spełniają wymagań.");
									break;
								}
							}
						}
						bfr.close();
					} catch (IOException x) {
						JOptionPane.showMessageDialog(null,
								"Podano niewłaściwą nazwę pliku, albo taki plik nie isnieje.");
						return;
					}
					wypelnijPanel();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Podano niewłaściwą nazwę pliku, albo taki plik nie isnieje. Upewnij się, czy wczytywany plik ma format *.txt lub *.TXT.");
				return;
			}
		}
		int i = 0;
		while (i < playlistShowButtons.size()) {
			if (zrodlo == playlistShowButtons.get(i)) {
				Playlist playlist = playlistList.get(i);
				PlaylistWindow playlistWindow = new PlaylistWindow(playlist);
				playlistWindow.setVisible(true);
				break;
			}
			i++;
		}
		i = 0;
		while (i < playlistDeleteButtons.size()) {
			if (zrodlo == playlistDeleteButtons.get(i)) {
				playlistList.remove(i);
				break;
			}
			i++;
		}
		i = 0;
		while (i < playlistSaveButtons.size()) {
			if (zrodlo == playlistSaveButtons.get(i)) {
				JOptionPane.showMessageDialog(null, "Pamiętaj dodać rozszerzenie *.txt do zapisywanego pliku!");
				try {
					FileDialog fdzapisz = new FileDialog(this, "Zapisz", FileDialog.SAVE);
					fdzapisz.setFilenameFilter((dir, name) -> name.endsWith(".txt") || name.endsWith(".TXT"));
					fdzapisz.setVisible(true);
					String directory = fdzapisz.getDirectory();
					String file = fdzapisz.getFile();
					playlistList.get(i).savePlaylistToFile(file, directory);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Wystąpił nieoczekiwany błąd zapisu. Upewnij się, czy zapisywany plik ma format *.txt lub *.TXT.");
				}
				break;
			}
			i++;
		}
		wypelnijPanel();
	}
}
