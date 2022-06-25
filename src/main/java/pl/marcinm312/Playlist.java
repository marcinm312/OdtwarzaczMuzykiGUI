package pl.marcinm312;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {

	private final String name;
	private final List<Song> songsList = new ArrayList<>();

	public Playlist(String name) throws Exception {
		if (name == null || name.isEmpty()) {
			throw new Exception("Nazwa playlisty nie może być pusta!");
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
		FileWriter plik2 = new FileWriter(fileName + "\\" + fileDirectory);
		for (Song song : songsList) {
			plik2.write(song.toString());
			plik2.write("\n");
		}
		plik2.close();
	}

	public List<Song> getSongsList() {
		return songsList;
	}
}
