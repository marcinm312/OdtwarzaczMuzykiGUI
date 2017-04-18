import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Runner {
	public static RamkaPlaylist mojaRamka;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mojaRamka = new RamkaPlaylist();
		mojaRamka.setVisible(true);
		mojaRamka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}