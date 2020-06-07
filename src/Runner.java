import javax.swing.JFrame;

public class Runner {
    public static RamkaPlaylist mojaRamka;

    public static void main(String[] args) {
        mojaRamka = new RamkaPlaylist();
        mojaRamka.setVisible(true);
        mojaRamka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}