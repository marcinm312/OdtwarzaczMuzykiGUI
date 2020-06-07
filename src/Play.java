import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Play extends Thread {
    File plik;

    public Play(File plik2) {
        plik = plik2;
    }

    public void run() {
        while (true) {
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(plik));
                Player player = new Player(bis);
                player.play();
            } catch (Exception e) {
                return;
            }
        }
    }
}