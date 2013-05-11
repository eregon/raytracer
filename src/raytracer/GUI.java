package raytracer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Scene;

public class GUI extends JFrame implements KeyListener {

	protected static final long UPDATE_EVERY = 30; // ms

	final PixelPanel panel;
	final RayTracer tracer;
	final Scene scene;

	private volatile boolean drawing = false;

	public GUI(PixelPanel panel, RayTracer tracer, Scene scene, Options options) {
		super();
		this.panel = panel;
		this.tracer = tracer;
		this.scene = scene;
		setResizable(false);
		getContentPane().add(panel);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		super.setVisible(true);
		createUpdater();
	}

	public void start() {
		drawing = true;
	}

	public void done() {
		panel.paint(panel.getGraphics());
		drawing = false;
	}

	void createUpdater() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(UPDATE_EVERY);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
					if (drawing)
						panel.paint(panel.getGraphics());
				}
			}
		}, "GUI Updater").start();
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
		start();
		tracer.render();
		done();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
