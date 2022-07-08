package pl.marcinm312.model;

import com.opencsv.CSVWriter;
import pl.marcinm312.exception.ValidationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

	public void sortPlaylist() {
		songsList = songsList.stream().sorted((s1, s2) -> s1.getTitle().compareToIgnoreCase(s2.getTitle())).collect(Collectors.toList());
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
