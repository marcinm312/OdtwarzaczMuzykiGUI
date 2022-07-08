package pl.marcinm312.model;

import pl.marcinm312.exception.ValidationException;

import java.io.File;

public class Song {

	private String title;
	private String performer;
	private int publicationYear;
	private String filePath;
	private File file;


	public Song(String title, String performer, String publicationYear, File file) throws ValidationException {

		init(title, performer, publicationYear);

		if (file == null) {
			throw new ValidationException("Należy wybrać plik MP3!");
		}
		this.file = file;
		this.filePath = file.getPath();
	}

	public Song(String[] songArray) {

		if (songArray.length < 4) {
			throw new ValidationException("Wiersz zawiera zbyt mało danych!");
		}
		init(songArray[0], songArray[1], songArray[2], songArray[3]);
	}

	private void init(String title, String performer, String publicationYear, String filePath) throws ValidationException {

		init(title, performer, publicationYear);

		if (filePath == null || filePath.isEmpty()) {
			throw new ValidationException("Ścieżka do pliku MP3 nie może być pusta!");
		}
		this.filePath = filePath;
		this.file = new File(filePath);
	}

	private void init(String title, String performer, String publicationYear) throws ValidationException {

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

	public File getFile() {
		return file;
	}

	@Override
	public String toString() {
		return (title + ";" + performer + ";" + publicationYear + ";" + filePath);
	}

	public String[] toArray() {
		return new String[]{title, performer, publicationYear + "", filePath};
	}
}
