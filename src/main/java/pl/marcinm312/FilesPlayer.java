package pl.marcinm312;

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.util.List;

import javazoom.jl.player.Player;

public class FilesPlayer extends Thread {

	List<Song> songsList;

	public FilesPlayer(List<Song> songsList) {
		this.songsList = songsList;
	}

	@Override
	public void run() {

		try {
			for (Song songItem : songsList) {
				BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(songItem.getFile().toPath()));
				Player player = new Player(bis);
				player.play();
			}
		} catch (Exception e) {

		}
	}
}
