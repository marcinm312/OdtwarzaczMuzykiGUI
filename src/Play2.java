import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javazoom.jl.player.Player;

public class Play2 extends Thread {
	HashMap<String, File> mojePliki = new HashMap<String, File>();
	Playlista lista;

	public Play2(HashMap<String, File> Pliki,Playlista list) {
		mojePliki= Pliki;
		lista=list;
	}

	public void run() {
		while (true) {
			try {
				for(int i=0;i<lista.size();i++){
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(mojePliki.get(lista.zwroc_mp3naz(i))));
				Player player = new Player(bis);
				player.play();
				}
			} catch (Exception e) {
				return;
			}
		}
	}
}
