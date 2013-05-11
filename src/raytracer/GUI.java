package raytracer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Scene;

public class GUI extends JFrame implements KeyListener {

	static final long UPDATE_EVERY = 30; // ms
	static final float MOVE_ANGLE = (float) (Math.PI / 12);
	static final float ZOOM = 1.2f;

	final PixelPanel panel;
	final RayTracer tracer;
	final Scene scene;
	final float move_units;

	private volatile boolean drawing = false;

	public GUI(PixelPanel panel, RayTracer tracer, Scene scene, Options options) {
		super();
		this.panel = panel;
		this.tracer = tracer;
		this.scene = scene;
		move_units = options.width / 3f;
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
			newCamera = scene.camera.rotateLeftRight(-MOVE_ANGLE);
			break;
		case KeyEvent.VK_RIGHT:
			newCamera = scene.camera.rotateLeftRight(MOVE_ANGLE);
			break;
		case KeyEvent.VK_UP:
			newCamera = scene.camera.rotateUpDown(-MOVE_ANGLE);
			break;
		case KeyEvent.VK_DOWN:
			newCamera = scene.camera.rotateUpDown(MOVE_ANGLE);
			break;
		case KeyEvent.VK_P:
			newCamera = scene.camera.zoom(1 / ZOOM);
			break;
		case KeyEvent.VK_M:
			newCamera = scene.camera.zoom(ZOOM);
			break;
		case KeyEvent.VK_Q:
			newCamera = scene.camera.translate(-move_units, 0);
			break;
		case KeyEvent.VK_D:
			newCamera = scene.camera.translate(move_units, 0);
			break;
		case KeyEvent.VK_Z:
			newCamera = scene.camera.translate(0, move_units);
			break;
		case KeyEvent.VK_S:
			newCamera = scene.camera.translate(0, -move_units);
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
