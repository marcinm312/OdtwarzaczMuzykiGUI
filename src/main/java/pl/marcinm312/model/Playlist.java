package pl.marcinm312.model;

import pl.marcinm312.exception.ValidationException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {

	private final String name;
	private final List<Song> songsList = new ArrayList<>();
	private final String fileSeparator = FileSystems.getDefault().getSeparator();

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

	public void savePlaylistToFile(String fileDirectory, String fileName) throws IOException {

		try (FileWriter fileWriter = new FileWriter(fileName + fileSeparator + fileDirectory)) {
			for (Song song : songsList) {
				fileWriter.write(song.toString());
				fileWriter.write("\n");
			}
		}
	}

	public List<Song> getSongsList() {
		return songsList;
	}
}
