import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RamkaPlaylist extends JFrame implements ActionListener {
	public JPanel mojPanel;
	public static ArrayList<Playlista> listaList = new ArrayList<Playlista>();
	public ArrayList<JLabel> nazwyList;
	public ArrayList<JButton> przyciskiWyswietl;
	public ArrayList<JButton> przyciskiUsun;
	public ArrayList<JButton> przyciskiZapisz;
	public JButton przyciskDodaj;
	public JButton przyciskWczytaj;
	public JButton przyciskInfo;
	public String katalog;
	public String plik;
	public static HashMap<String, File> mojePliki = new HashMap<String, File>();
	public static RamkaListy mojaRamka2;
	public static Playlista lista100;

	public RamkaPlaylist() {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		setTitle("Odtwarzacz 4.2");
		mojPanel = new JPanel();
		add(mojPanel);
		wypelnijPanel();
	}

	public void wypelnijPanel() {
		mojPanel.removeAll();
		GridLayout layout = new GridLayout(listaList.size() + 1, 4);
		mojPanel.setLayout(layout);
		nazwyList = new ArrayList<JLabel>();
		przyciskiWyswietl = new ArrayList<JButton>();
		przyciskiUsun = new ArrayList<JButton>();
		przyciskiZapisz = new ArrayList<JButton>();
		for (int i = 0; i < listaList.size(); i++) {
			JLabel nazwa = new JLabel();
			mojPanel.add(nazwa);
			nazwa.setText(listaList.get(i).pobierz_nazwe());
			nazwyList.add(nazwa);

			JButton przyciskWyswietl = new JButton("Wyświetl playlistę");
			przyciskiWyswietl.add(przyciskWyswietl);
			mojPanel.add(przyciskWyswietl);
			przyciskWyswietl.addActionListener(this);

			JButton przyciskUsun = new JButton("Usuń playlistę");
			przyciskiUsun.add(przyciskUsun);
			mojPanel.add(przyciskUsun);
			przyciskUsun.addActionListener(this);

			JButton przyciskZapisz = new JButton("Zapisz playlistę do pliku");
			przyciskiZapisz.add(przyciskZapisz);
			mojPanel.add(przyciskZapisz);
			przyciskZapisz.addActionListener(this);
		}
		przyciskDodaj = new JButton("Utwórz nową playlistę");
		mojPanel.add(przyciskDodaj);
		przyciskDodaj.addActionListener(this);

		przyciskWczytaj = new JButton("Wczytaj playlistę z pliku TXT");
		mojPanel.add(przyciskWczytaj);
		przyciskWczytaj.addActionListener(this);

		JLabel n4 = new JLabel();
		mojPanel.add(n4);
		n4.setText("");

		przyciskInfo = new JButton("O programie...");
		mojPanel.add(przyciskInfo);
		przyciskInfo.addActionListener(this);

		pack();
	}

	public void actionPerformed(ActionEvent arg0) {
		Object zrodlo = arg0.getSource();
		if (zrodlo == przyciskDodaj) {
			String nazwapl = JOptionPane.showInputDialog(null, "Nazwa nowej playlisty:", "Nowa playlista",
					JOptionPane.OK_CANCEL_OPTION);
			if ((nazwapl != null) && (nazwapl.length() > 0)) {
				try {
					Playlista playlista = new Playlista(nazwapl);
					if (listaList.contains(playlista) == true) {
						JOptionPane.showMessageDialog(null, "Playlista o takiej nazwie już istnieje!");
					} else {
						listaList.add(playlista);
						wypelnijPanel();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Wystąpił nieoczekiwany błąd.");
				}
			}
		}
		if (zrodlo == przyciskInfo) {
			JOptionPane.showMessageDialog(null, "Odtwarzacz 4.2\n\nCopyright (C) 2019\nMarcin Michalczyk");
		}
		if (zrodlo == przyciskWczytaj) {

			FileDialog fdwczytaj = new FileDialog(Runner.mojaRamka, "Wczytaj", FileDialog.LOAD);
			fdwczytaj.setFilenameFilter(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".txt") || name.endsWith(".TXT");
				}
			});
			fdwczytaj.setVisible(true);
			katalog = fdwczytaj.getDirectory();
			plik = fdwczytaj.getFile();
			try {
				FileReader fr = new FileReader(katalog + "\\" + plik);
				BufferedReader bfr = new BufferedReader(fr);
				String nazwapl = JOptionPane.showInputDialog(null, "Nazwa nowo wczytanej playlisty", "Nowa playlista",
						JOptionPane.OK_CANCEL_OPTION);
				if ((nazwapl != null) && (nazwapl.length() > 0)) {
					try {
						Playlista playlista = new Playlista(nazwapl);
						if (listaList.contains(playlista) == true) {
							JOptionPane.showMessageDialog(null, "Playlista o takiej nazwie już istnieje!");
						} else {
							listaList.add(playlista);
							String linia;
							while ((linia = bfr.readLine()) != null) {
								String details[] = new String[3];
								details = linia.split("/");
								mojePliki.put(details[3], new File(details[3]));
								try {
									playlista.dodaj(new Utwor(details[0], details[1], Integer.parseInt(details[2]),
											details[3]));
								} catch (Exception e) {
									// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,
						"Podano niewłaściwą nazwę pliku, albo taki plik nie isnieje. Upewnij się, czy wczytywany plik ma format *.txt lub *.TXT.");
				return;
			}
		}
		int i = 0;
		while (i < przyciskiWyswietl.size()) {
			if (zrodlo == przyciskiWyswietl.get(i)) {
				lista100 = listaList.get(i);
				mojaRamka2 = new RamkaListy(lista100);
				mojaRamka2.setVisible(true);
				break;
			}
			i++;
		}
		i = 0;
		while (i < przyciskiUsun.size()) {
			if (zrodlo == przyciskiUsun.get(i)) {
				listaList.remove(i);
				System.gc();
				break;
			}
			i++;
		}
		i = 0;
		while (i < przyciskiZapisz.size()) {
			if (zrodlo == przyciskiZapisz.get(i)) {
				JOptionPane.showMessageDialog(null, "Pamiętaj dodać rozszerzenie *.txt do zapisywanego pliku!");
				try {
					FileDialog fdzapisz = new FileDialog(Runner.mojaRamka, "Zapisz", FileDialog.SAVE);
					fdzapisz.setFilenameFilter(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return name.endsWith(".txt") || name.endsWith(".TXT");
						}
					});
					fdzapisz.setVisible(true);
					katalog = fdzapisz.getDirectory();
					plik = fdzapisz.getFile();
					listaList.get(i).zapiszUtwor(plik, katalog);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,
							"Wystąpił nieoczekiwany błąd zapisu. Upewnij się, czy zapisywany plik ma format *.txt lub *.TXT.");
				}
				break;
			}
			i++;
		}
		i = 0;
		wypelnijPanel();
	}
}
