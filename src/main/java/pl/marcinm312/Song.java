package pl.marcinm312;

import java.io.File;

public class Song implements Comparable<Song> {

	private final String title;
	private final String performer;
	private final int publicationYear;
	private final String filePath;
	private final File file;


	public Song(String title, String performer, String publicationYear, String filePath) throws Exception {

		if (title == null || title.isEmpty()) {
			throw new Exception("Nazwa utworu nie może być pusta!");
		}
		this.title = title;

		if (performer == null || performer.isEmpty()) {
			throw new Exception("Nazwa wykonawcy nie może być pusta!");
		}
		this.performer = performer;

		if (publicationYear == null || publicationYear.isEmpty()) {
			throw new Exception("Rok wydania utworu nie może być pusty!");
		}
		try {
			this.publicationYear = Integer.parseInt(publicationYear);
		} catch (Exception e) {
			throw new Exception("Wpisany rok nie jest liczbą!");
		}

		if (filePath == null || filePath.isEmpty()) {
			throw new Exception("Nazwa pliku MP3 nie może być pusta!");
		}
		this.filePath = filePath;
		this.file = new File(filePath);
	}

	public String getTitle() {
		return title;
	}

	public String getPerformer() {
		return performer;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public String getFilePath() {
		return filePath;
	}

	public File getFile() {
		return file;
	}

	@Override
	public String toString() {
		return (getTitle() + ";" + getPerformer() + ";" + getPublicationYear() + ";" + getFilePath());
	}

	@Override
	public int compareTo(Song song) {
		return this.getTitle().compareTo(song.getTitle());
	}
}
