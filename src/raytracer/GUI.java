package raytracer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import uclouvain.ingi2325.utils.PixelPanel;

public class GUI extends JFrame implements KeyListener {
	public GUI(PixelPanel panel) {
		super();
		setResizable(false);
		getContentPane().add(panel);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
	}

	public void start() {
		super.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			System.out.println("left");
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("right");
			break;
		case KeyEvent.VK_UP:
			System.out.println("up");
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("down");
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
