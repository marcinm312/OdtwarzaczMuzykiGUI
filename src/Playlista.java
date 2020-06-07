import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Playlista {
    private final String nazwa;
    int licznik = 1;
    ArrayList<Utwor> utwory = new ArrayList<>();

    public Playlista(String n) throws Exception {
        nazwa = n;
        if (nazwa == null || nazwa.isEmpty()) {
            throw new Exception("Nazwa playlisty nie może być pusta!");
        }
    }

    public String pobierz_nazwe() {
        return nazwa;
    }

    public void dodaj(Utwor utwor) {
        utwory.add(utwor);
    }

    public void usun(int j) throws Exception {
        utwory.remove(j);
        if (utwory == null || utwory.isEmpty()) {
            throw new Exception("Lista jest pusta!");
        }
    }

    public void posortujUtwory() {
        Collections.sort(utwory);
    }

    public String zwroc_mp3naz(int g) throws Exception {
        if (utwory == null || utwory.isEmpty()) {
            throw new Exception("Lista jest pusta!");
        }
        return utwory.get(g).pobierz_nazwe_pliku_mp3();
    }

    public void zapiszUtwor(String katalog, String plik) throws IOException {
        FileWriter plik2 = new FileWriter(plik + "\\" + katalog);
        for (Utwor utwor : utwory) {
            plik2.write(utwor.wyswietl_utwor_plik());
            plik2.write("\n");
        }
        plik2.close();
        licznik++;
    }

    public int size() {
        return utwory.size();
    }
}