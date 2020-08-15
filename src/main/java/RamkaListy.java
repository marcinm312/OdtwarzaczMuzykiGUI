import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RamkaListy extends JFrame implements ActionListener {
    public Playlista lista;
    public JPanel mojPanel;
    private final HashMap<String, File> mojePliki;
    public ArrayList<Playlista> listaList;
    public String[] listaList_nazwy = new String[1000];
    public String nazwa2;
    public ArrayList<JLabel> tytuly;
    public ArrayList<JLabel> wykonawcy;
    public ArrayList<JLabel> daty;
    public JButton przyciskDodaj;
    public JButton przyciskSortuj;
    public JButton przyciskOdswiez;
    public JButton przyciskOdtworzWszystko;
    public JButton przyciskStop;
    public ArrayList<JButton> przyciskiPrzenies;
    public ArrayList<JButton> przyciskiKopiuj;
    public ArrayList<JButton> przyciskiUsun;
    public ArrayList<JButton> przyciskiOdtworz;
    public String katalog;
    public String plik;
    public Play mojWatek;
    public Play2 p;

    public RamkaListy(Playlista lista2) {
        this.lista = lista2;
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        setTitle("Playlista " + lista.pobierz_nazwe() + ":");
        mojePliki = RamkaPlaylist.mojePliki;
        mojPanel = new JPanel();
        add(mojPanel);
        wypelnijPanel();
    }

    public void wypelnijPanel() {
        mojPanel.removeAll();
        GridLayout layout2 = new GridLayout(lista.utwory.size() + 2, 7);
        mojPanel.setLayout(layout2);
        tytuly = new ArrayList<>();
        wykonawcy = new ArrayList<>();
        daty = new ArrayList<>();
        przyciskiPrzenies = new ArrayList<>();
        przyciskiKopiuj = new ArrayList<>();
        przyciskiUsun = new ArrayList<>();
        przyciskiOdtworz = new ArrayList<>();

        JLabel n1 = new JLabel();
        n1.setForeground(Color.red);
        mojPanel.add(n1);
        n1.setText("Tytuł");

        JLabel n2 = new JLabel();
        n2.setForeground(Color.red);
        mojPanel.add(n2);
        n2.setText("Wykonawca");

        JLabel n3 = new JLabel();
        n3.setForeground(Color.red);
        mojPanel.add(n3);
        n3.setText("Rok wydania");

        JLabel n4 = new JLabel();
        mojPanel.add(n4);
        n4.setText("");

        JLabel n5 = new JLabel();
        mojPanel.add(n5);
        n5.setText("");

        JLabel n6 = new JLabel();
        mojPanel.add(n6);
        n6.setText("");

        JLabel n7 = new JLabel();
        mojPanel.add(n7);
        n7.setText("");

        for (int i = 0; i < lista.utwory.size(); i++) {
            JLabel tytul = new JLabel();
            mojPanel.add(tytul);
            tytul.setText(lista.utwory.get(i).pobierz_nazwe());
            tytuly.add(tytul);

            JLabel wykonawca = new JLabel();
            mojPanel.add(wykonawca);
            wykonawca.setText(lista.utwory.get(i).pobierz_wykonawce());
            wykonawcy.add(wykonawca);

            JLabel rok = new JLabel();
            mojPanel.add(rok);
            rok.setText(lista.utwory.get(i).pobierz_rok_tekst());
            daty.add(rok);

            JButton przyciskPrzenies = new JButton("Przenieś utwór do ...");
            przyciskiPrzenies.add(przyciskPrzenies);
            mojPanel.add(przyciskPrzenies);
            przyciskPrzenies.addActionListener(this);

            JButton przyciskKopiuj = new JButton("Kopiuj utwór do ...");
            przyciskiKopiuj.add(przyciskKopiuj);
            mojPanel.add(przyciskKopiuj);
            przyciskKopiuj.addActionListener(this);

            JButton przyciskUsun = new JButton("Usuń utwór z playlisty");
            przyciskiUsun.add(przyciskUsun);
            mojPanel.add(przyciskUsun);
            przyciskUsun.addActionListener(this);

            JButton przyciskOdtworz = new JButton("Odtwórz utwór");
            przyciskiOdtworz.add(przyciskOdtworz);
            mojPanel.add(przyciskOdtworz);
            przyciskOdtworz.addActionListener(this);

            pack();
        }
        przyciskDodaj = new JButton("Dodaj utwór");
        mojPanel.add(przyciskDodaj);
        przyciskDodaj.addActionListener(this);

        przyciskSortuj = new JButton("Sortuj utwory wg tytułów");
        mojPanel.add(przyciskSortuj);
        przyciskSortuj.addActionListener(this);

        przyciskOdswiez = new JButton("Odśwież playlistę");
        mojPanel.add(przyciskOdswiez);
        przyciskOdswiez.addActionListener(this);

        przyciskOdtworzWszystko = new JButton("Odtwórz wszystko");
        mojPanel.add(przyciskOdtworzWszystko);
        przyciskOdtworzWszystko.addActionListener(this);

        przyciskStop = new JButton("Zatrzymaj odtwarzanie");
        mojPanel.add(przyciskStop);
        przyciskStop.addActionListener(this);

        JLabel n9 = new JLabel();
        mojPanel.add(n9);
        n9.setText("");

        JLabel n10 = new JLabel();
        mojPanel.add(n10);
        n10.setText("");

        pack();
    }

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent arg0) {
        Object zrodlo = arg0.getSource();
        if (zrodlo == przyciskDodaj) {
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
                                int rok1 = Integer.parseInt(rok1str);
                                JOptionPane.showMessageDialog(null, "Obsługiwane są tylko pliki MP3.");
                                FileDialog fdwczytaj = new FileDialog(Runner.mojaRamka, "Wczytaj", FileDialog.LOAD);
                                fdwczytaj.setFilenameFilter((dir, name) -> name.endsWith(".mp3") || name.endsWith(".MP3"));
                                fdwczytaj.setVisible(true);
                                katalog = fdwczytaj.getDirectory();
                                plik = fdwczytaj.getFile();
                                if ((plik != null) && (plik.length() > 0)) {
                                    String plik_a = katalog + "\\" + plik;
                                    mojePliki.put(plik_a, new File(plik_a));
                                    lista.dodaj(new Utwor(utwor1, wykonawca1, rok1, plik_a));
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
        if (zrodlo == przyciskSortuj) {
            lista.posortujUtwory();
        }
        if (zrodlo == przyciskStop) {
            try {
                mojWatek.stop();
            } catch (Exception e) {
                p.stop();
            }
        }
        if (zrodlo == przyciskOdtworzWszystko) {
            try {
                p = new Play2(mojePliki, lista);
                p.start();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Załadowano nieprawidłowy plik!");
                return;
            }
        }
        int i = 0;
        while (i < przyciskiPrzenies.size()) {
            if (zrodlo == przyciskiPrzenies.get(i)) {
                listaList = RamkaPlaylist.listaList;
                int y2 = 0;
                while (y2 < listaList.size()) {
                    nazwa2 = listaList.get(y2).pobierz_nazwe();
                    listaList_nazwy[y2] = nazwa2;
                    y2++;
                }
                String input2 = (String) JOptionPane.showInputDialog(null,
                        "Wybierz playlistę, do której chcesz przenieść utwór:", "Wybór playlisty",
                        JOptionPane.QUESTION_MESSAGE, null, listaList_nazwy, listaList_nazwy[0]);
                if ((input2 != null) && (input2.length() > 0)) {
                    List<String> temp = Arrays.asList(listaList_nazwy);
                    int a = temp.indexOf(input2);
                    Utwor xyz = lista.utwory.get(i);
                    listaList.get(a).dodaj(xyz);
                    try {
                        lista.usun(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.gc();
                }
                break;
            }
            i++;
        }
        i = 0;

        while (i < przyciskiKopiuj.size()) {
            if (zrodlo == przyciskiKopiuj.get(i)) {
                listaList = RamkaPlaylist.listaList;
                int y = 0;
                while (y < listaList.size()) {
                    nazwa2 = listaList.get(y).pobierz_nazwe();
                    listaList_nazwy[y] = nazwa2;
                    y++;
                }
                String input = (String) JOptionPane.showInputDialog(null,
                        "Wybierz playlistę, do której chcesz skopiować utwór:", "Wybór playlisty",
                        JOptionPane.QUESTION_MESSAGE, null, listaList_nazwy, listaList_nazwy[0]);
                if ((input != null) && (input.length() > 0)) {
                    List<String> temp = Arrays.asList(listaList_nazwy);
                    int a = temp.indexOf(input);
                    Utwor xyz = lista.utwory.get(i);
                    listaList.get(a).dodaj(xyz);
                }
                break;
            }
            i++;
        }
        i = 0;

        while (i < przyciskiUsun.size()) {
            if (zrodlo == przyciskiUsun.get(i)) {
                try {
                    lista.usun(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.gc();
                break;
            }
            i++;
        }
        i = 0;

        while (i < przyciskiOdtworz.size()) {
            if (zrodlo == przyciskiOdtworz.get(i)) {
                try {
                    mojWatek = new Play(mojePliki.get(lista.zwroc_mp3naz(i)));
                    mojWatek.start();
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
