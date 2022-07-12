package pl.marcinm312.model;

import com.opencsv.CSVWriter;
import pl.marcinm312.exception.ValidationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Playlist {

	private final String name;
	private List<Song> songsList = new ArrayList<>();


	public Playlist(String name) throws ValidationException {

		if (name == null || name.isEmpty()) {
			throw new ValidationException("Nazwa playlisty nie może być pusta!");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addSong(Song song) {
		songsList.add(song);
	}

	public void removeSong(int j) {
		songsList.remove(j);
	}

	public void moveSongUp(int i) {

		if (i == 0) {
			throw new IndexOutOfBoundsException("Nie można przenieść wyżej pierwszego utworu!");
		}
		Collections.swap(songsList, i, i - 1);
	}

	public void moveSongDown(int i) {

		if (i == songsList.size() - 1) {
			throw new IndexOutOfBoundsException("Nie można przenieść niżej ostatniego utworu!");
		}
		Collections.swap(songsList, i, i + 1);
	}

	public void sortPlaylistByTitleAscending() {
		songsList = songsList.stream().sorted((s1, s2) -> s1.getTitle().compareToIgnoreCase(s2.getTitle())).collect(Collectors.toList());
	}

	public void sortPlaylistByTitleDescending() {
		songsList = songsList.stream().sorted((s1, s2) -> s2.getTitle().compareToIgnoreCase(s1.getTitle())).collect(Collectors.toList());
	}

	public void sortPlaylistByPerformerAscending() {
		songsList = songsList.stream().sorted((s1, s2) -> s1.getPerformer().compareToIgnoreCase(s2.getPerformer())).collect(Collectors.toList());
	}

	public void sortPlaylistByPerformerDescending() {
		songsList = songsList.stream().sorted((s1, s2) -> s2.getPerformer().compareToIgnoreCase(s1.getPerformer())).collect(Collectors.toList());
	}

	public void sortPlaylistByYearAscending() {
		songsList = songsList.stream().sorted(Comparator.comparingInt(Song::getYear)).collect(Collectors.toList());
	}

	public void sortPlaylistByYearDescending() {
		songsList = songsList.stream().sorted(Comparator.comparingInt(Song::getYear).reversed()).collect(Collectors.toList());
	}

	public void savePlaylistToFile(File file) throws IOException {

		try (CSVWriter writer = new CSVWriter(new FileWriter(file), ';', '"', '"', "\n")) {
			writer.writeNext(new String[]{"Tytuł", "Wykonawca", "Rok wydania"});
			for (Song song : songsList) {
				writer.writeNext(song.toArray());
			}
		}
	}

	public List<Song> getSongsList() {
		return songsList;
	}

	public void setSongsList(List<Song> songsList) {
		this.songsList = songsList;
	}
}
