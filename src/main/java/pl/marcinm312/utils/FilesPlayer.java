package pl.marcinm312.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javazoom.jl.player.Player;
import pl.marcinm312.model.Song;

import javax.swing.*;

public class FilesPlayer implements Runnable {

	private Thread worker;
	private final AtomicBoolean running = new AtomicBoolean(false);
	private static FilesPlayer instance;
	private List<Song> songsList;
	private Player player;

	private FilesPlayer(List<Song> songsList) {
		this.songsList = Collections.synchronizedList(songsList);
	}

	public static FilesPlayer getInstance() {
		return getInstance(new ArrayList<>());
	}

	public static FilesPlayer getInstance(List<Song> songsList) {

		if (instance == null) {
			instance = new FilesPlayer(songsList);
		} else {
			instance.songsList = songsList;
		}
		return instance;
	}

	public void start() {

		worker = new Thread(this);
		worker.start();
	}

	public void interrupt() {

		running.set(false);
		if (worker != null) {
			worker.interrupt();
		}
		if (player != null) {
			player.close();
		}
	}

	public void run() {

		running.set(true);
		for (Song songItem : this.songsList) {
			if (running.get()) {
				File file = songItem.getFile();
				try (FileInputStream fis = new FileInputStream(file)) {
					player = new Player(fis);
					player.play();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Błąd podczas odtwarzania pliku:\n"
							+ file.getAbsolutePath() + "\n"
							+ e.getMessage());
				}
			}
		}
	}
}
