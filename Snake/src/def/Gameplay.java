package def;

import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private sad x;
	private Sound1 a;
	private boolean flag = false;
	private int highscore = 0;
	private int score = 0;
	private int enemyx;
	private int enemyy;

	private ImageIcon titleImage;

	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];

	private int lengthofsnake = 1;

	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	private ImageIcon rightmouth;
	private ImageIcon leftmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;

	private Timer timer;
	private int delay = 100;

	private ImageIcon snakeImage;

	private int moves = 0;

	private boolean start = true;

	private ImageIcon enemyimage;

	public Gameplay() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		a = new Sound1();
		a.start();
		x = new sad();
		// System.out.println(x.read());
		highscore = x.read();
	}

	public void stop() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		for (int i = 1; i < lengthofsnake; i++) {
			if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
				a.stop();
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		if (moves == 0) {
			snakexlength[2] = 50;
			snakexlength[1] = 75;
			snakexlength[0] = 100;

			snakeylength[2] = 100;
			snakeylength[1] = 100;
			snakeylength[0] = 100;
		}
		// draw title image border
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);

		// draw title image
		titleImage = new ImageIcon("lol.png");
		titleImage.paintIcon(this, g, 25, 11);

		// draw border for playing area
		g.setColor(Color.BLACK);
		g.drawRect(24, 74, 851, 577);

		// draw background for game
		g.setColor(Color.WHITE);
		g.fillRect(25, 75, 850, 575);

		// scores

		g.setColor(Color.CYAN);
		g.setFont(new Font("Comic Sans", 1, 15));
		g.drawString("SCORE : " + score, 35, 35);

		if (score >= highscore) {
			// highscore = score;
			x = new sad();
			x.write(score + "");
			highscore = x.read();
		}

		// high score

		g.setColor(Color.CYAN);
		g.setFont(new Font("Comic Sans", 1, 15));
		g.drawString("HIGH-SCORE : " + x.read(), 35, 55);

		// length
		g.setColor(Color.CYAN);
		g.setFont(new Font("Comic Sans", 1, 15));
		g.drawString("LENGTH : " + lengthofsnake, 780, 45);

		if (start) {
			downmouth = new ImageIcon("down.png");
			downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
			start = false;
		}
		for (int a = 0; a < lengthofsnake; a++) {
			if (a == 0 && right) {
				rightmouth = new ImageIcon("right.png");
				rightmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if (a == 0 && left) {
				leftmouth = new ImageIcon("left.png");
				leftmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if (a == 0 && up) {
				upmouth = new ImageIcon("up.png");
				upmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if (a == 0 && down) {
				downmouth = new ImageIcon("down.png");
				downmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if (a != 0) {
				snakeImage = new ImageIcon("e1.png");
				snakeImage.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
		}

		enemyimage = new ImageIcon("food.png");

		// random enemy position generation
		if (!flag) {
			enemyx = (int) (Math.random());
			enemyy = (int) (Math.random());

			// frame adjustment

			enemyx %= 34.0;
			enemyy %= 23.0;

			enemyx += 1.0;
			enemyy += 3.0;

			enemyx *= 25.0;
			enemyy *= 25.0;
			flag = true;
		}
		// check collision

		if (enemyx == snakexlength[0] && enemyy == snakeylength[0]) {
			lengthofsnake++;
			score++;
			enemyx = (int) (Math.random() * 100);
			enemyy = (int) (Math.random() * 100);

			// frame adjustment

			enemyx %= 34.0;
			enemyy %= 23.0;

			enemyx += 1.0;
			enemyy += 3.0;

			enemyx *= 25.0;
			enemyy *= 25.0;
		}

		enemyimage.paintIcon(this, g, enemyx, enemyy);

		for (int i = 1; i < lengthofsnake; i++) {
			if (snakexlength[i] == snakexlength[0] && snakeylength[i] == snakeylength[0]) {
				right = false;
				left = false;
				up = false;
				down = false;
				String str = "GAME OVER\nYOUR SCORE :" + score + "\nPRESS SPACE TO CONTINUE";
				JOptionPane.showMessageDialog(this, str);
			}
		}
		//
		// x.write("" + highscore);
		g.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			start = true;
			moves = 0;
			score = 0;
			lengthofsnake = 1;
			try {
				a.start();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moves++;
			right = true;
			if (!left) {
				right = true;
			} else {
				right = false;
				left = true;
			}
			up = false;
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moves++;
			left = true;
			if (!right) {
				left = true;
			} else {
				left = false;
				right = true;
			}
			up = false;
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moves++;
			up = true;
			if (!down) {
				up = true;
			} else {
				up = false;
				down = true;
			}
			left = false;
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			moves++;
			down = true;
			if (!up) {
				down = true;
			} else {
				down = false;
				up = true;
			}
			left = false;
			right = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (right) {

			for (int r = lengthofsnake - 1; r >= 0; r--) {
				snakeylength[r + 1] = snakeylength[r];
			}

			for (int r = lengthofsnake; r >= 0; r--) {
				if (r == 0) {
					snakexlength[r] += 25;
				} else {
					snakexlength[r] = snakexlength[r - 1];
				}
				if (snakexlength[r] > 850) {
					snakexlength[r] = 25;
				}
			}
			try {
				stop();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}
			repaint();
		} else if (left) {

			for (int r = lengthofsnake - 1; r >= 0; r--) {
				snakeylength[r + 1] = snakeylength[r];
			}

			for (int r = lengthofsnake; r >= 0; r--) {
				if (r == 0) {
					snakexlength[r] -= 25;
				} else {
					snakexlength[r] = snakexlength[r - 1];
				}
				if (snakexlength[r] < 25) {
					snakexlength[r] = 850;
				}
			}
			try {
				stop();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}
			repaint();

		} else if (up) {

			for (int r = lengthofsnake - 1; r >= 0; r--) {
				snakexlength[r + 1] = snakexlength[r];
			}

			for (int r = lengthofsnake; r >= 0; r--) {
				if (r == 0) {
					snakeylength[r] -= 25;
				} else {
					snakeylength[r] = snakeylength[r - 1];
				}
				if (snakeylength[r] < 75) {
					snakeylength[r] = 625;
				}
			}
			try {
				stop();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}
			repaint();
		} else if (down) {

			for (int r = lengthofsnake - 1; r >= 0; r--) {
				snakexlength[r + 1] = snakexlength[r];
			}

			for (int r = lengthofsnake; r >= 0; r--) {
				if (r == 0) {
					snakeylength[r] += 25;
				} else {
					snakeylength[r] = snakeylength[r - 1];
				}
				if (snakeylength[r] > 625) {
					snakeylength[r] = 75;
				}
			}
			try {
				stop();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			}
			repaint();
		}
	}

	private class Sound1 {
		private Clip clip;

		private Sound1() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

		}

		private void start() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			String soundName = "yoursound.wav";
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}

		private void stop() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			clip.stop();
		}
	}

	private class sad {

		private sad() {

		}

		private void write(String str) {

			// The name of the file to open.
			String fileName = "temp.txt";

			try {
				// Assume default encoding.
				FileWriter fileWriter = new FileWriter(fileName);

				// Always wrap FileWriter in BufferedWriter.
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				// Note that write() does not automatically
				// append a newline character.
				bufferedWriter.write(str);

				// Always close files.
				bufferedWriter.close();
			} catch (IOException ex) {
				System.out.println("Error writing to file '" + fileName + "'");
				// Or we could just do this:
				// ex.printStackTrace();
			}
		}

		private int read() {

			// The name of the file to open.
			String fileName = "temp.txt";

			// This will reference one line at a time
			String line = null;

			try {
				// FileReader reads text files in the default encoding.
				FileReader fileReader = new FileReader(fileName);

				// Always wrap FileReader in BufferedReader.
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				int one = 0;
				int two = 0;
				while ((line = bufferedReader.readLine()) != null) {
					one = Integer.parseInt(line);
					if (one >= two) {
						two = one;
					}
				}

				// Always close files.
				bufferedReader.close();
				return two;
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + fileName + "'");
			} catch (IOException ex) {
				System.out.println("Error reading file '" + fileName + "'");
				// Or we could just do this:
				// ex.printStackTrace();
			}
			return 0;
		}

	}

}
