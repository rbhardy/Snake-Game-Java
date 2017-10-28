package def;

import java.awt.Color;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		JFrame obj = new JFrame();
		Gameplay gameplay = new Gameplay();
//		Gameplay_lvl_2 gameplay=new Gameplay_lvl_2();
		obj.setTitle("🐍     :SNAKE:     🐍");
		obj.setBounds(10, 10, 900, 700);
//		obj.setBackground(Color.BLACK);
		obj.setResizable(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
		obj.setVisible(true);
	}

}
