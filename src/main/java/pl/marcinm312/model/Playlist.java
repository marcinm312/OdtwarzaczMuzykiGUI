package pl.marcinm312.model;

import pl.marcinm312.exception.ValidationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		Collections.sort(songsList);
	}

	public void savePlaylistToFile(File file) throws IOException {

		try (FileWriter fileWriter = new FileWriter(file)) {
			for (Song song : songsList) {
				fileWriter.write(song.toString());
				fileWriter.write("\n");
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
