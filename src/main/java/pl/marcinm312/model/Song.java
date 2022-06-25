package pl.marcinm312.model;

import pl.marcinm312.exception.ValidationException;

import java.io.File;
import java.util.Objects;

public class Song implements Comparable<Song> {

	private final String title;
	private final String performer;
	private final int publicationYear;
	private final String filePath;
	private final File file;


	public Song(String title, String performer, String publicationYear, String filePath) throws ValidationException {

		if (title == null || title.isEmpty()) {
			throw new ValidationException("Nazwa utworu nie może być pusta!");
		}
		this.title = title;

		if (performer == null || performer.isEmpty()) {
			throw new ValidationException("Nazwa wykonawcy nie może być pusta!");
		}
		this.performer = performer;

		if (publicationYear == null || publicationYear.isEmpty()) {
			throw new ValidationException("Rok wydania utworu nie może być pusty!");
		}
		try {
			this.publicationYear = Integer.parseInt(publicationYear);
		} catch (Exception e) {
			throw new ValidationException("Wpisany rok nie jest liczbą!");
		}

		if (filePath == null || filePath.isEmpty()) {
			throw new ValidationException("Nazwa pliku MP3 nie może być pusta!");
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Song song = (Song) o;

		if (publicationYear != song.publicationYear) return false;
		if (!Objects.equals(title, song.title)) return false;
		if (!Objects.equals(performer, song.performer)) return false;
		if (!Objects.equals(filePath, song.filePath)) return false;
		return Objects.equals(file, song.file);
	}

	@Override
	public int hashCode() {
		int result = title != null ? title.hashCode() : 0;
		result = 31 * result + (performer != null ? performer.hashCode() : 0);
		result = 31 * result + publicationYear;
		result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
		result = 31 * result + (file != null ? file.hashCode() : 0);
		return result;
	}
}
