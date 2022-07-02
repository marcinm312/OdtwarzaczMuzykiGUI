package pl.marcinm312.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

import javazoom.jl.player.Player;
import pl.marcinm312.model.Song;

import javax.swing.*;

public class FilesPlayer extends Thread {

	private final List<Song> songsList;
	private Player player;
	private boolean isClosed = false;

	public FilesPlayer(List<Song> songsList) {
		this.songsList = songsList;
	}

	@Override
	public void run() {

		for (Song songItem : songsList) {
			File file = songItem.getFile();
			try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
				if (isClosed) {
					break;
				}
				player = new Player(bis);
				player.play();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Błąd podczas odtwarzania pliku:\n"
						+ file.getAbsolutePath() + "\n"
						+ e.getMessage());
				isClosed = true;
			}
		}
	}

	public void stopPlayer() {

		player.close();
		isClosed = true;
	}
}
