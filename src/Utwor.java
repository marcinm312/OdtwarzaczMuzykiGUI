public class Utwor implements Comparable<Utwor> {

	private String nazwa_utworu;
	private String wykonawca;
	private int rok_wydania;
	public String nazwa_pliku_mp3;

	public Utwor(String nu, String wyk, int rok, String mp3nazwa) throws Exception {
		nazwa_utworu = nu;
		if (nazwa_utworu == null || nazwa_utworu.isEmpty()) {
			throw new Exception("Nazwa utworu nie mo¿e byæ pusta!");
		}
		wykonawca = wyk;
		if (wykonawca == null || wykonawca.isEmpty()) {
			throw new Exception("Nazwa wykonawcy nie mo¿e byæ pusta!");
		}
		rok_wydania = rok;

		nazwa_pliku_mp3 = mp3nazwa;
		if (nazwa_pliku_mp3 == null || nazwa_pliku_mp3.isEmpty()) {
			throw new Exception("Nazwa pliku MP3 nie mo¿e byæ pusta!");
		}
	}

	public String pobierz_nazwe() {
		return nazwa_utworu;
	}

	public String pobierz_wykonawce() {
		return wykonawca;
	}

	public int pobierz_rok() {
		return rok_wydania;
	}

	public String pobierz_rok_tekst() {
		String s = rok_wydania + "";
		return s;
	}

	public String pobierz_nazwe_pliku_mp3() {
		return nazwa_pliku_mp3;
	}

	public String wyswietl_utwor_plik() {
		return (pobierz_nazwe() + "/" + pobierz_wykonawce() + "/" + pobierz_rok() + "/" + pobierz_nazwe_pliku_mp3());
	}

	@Override
	public int compareTo(Utwor nu) {
		return this.pobierz_nazwe().compareTo(nu.pobierz_nazwe());
	}
}