package raytracer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Scene;

public class GUI extends JFrame implements KeyListener {

	final RayTracer tracer;
	final Scene scene;

	public GUI(PixelPanel panel, RayTracer tracer, Scene scene, Options options) {
		super();
		this.tracer = tracer;
		this.scene = scene;
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
		Camera newCamera = null;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			newCamera = scene.camera.rotate(-Math.PI / 12, false);
			break;
		case KeyEvent.VK_RIGHT:
			newCamera = scene.camera.rotate(Math.PI / 12, false);
			break;
		case KeyEvent.VK_UP:
			newCamera = scene.camera.rotate(-Math.PI / 12, true);
			break;
		case KeyEvent.VK_DOWN:
			newCamera = scene.camera.rotate(Math.PI / 12, true);
			break;
		default:
			return;
		}

		scene.camera = newCamera;
		tracer.render();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
