package raytracer;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.SceneBuilder;

/**
 * The demo :-)
 *
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class Demo {

	protected static final long UPDATE_EVERY = 30; // ms

	public static void main(String[] args) throws Exception {
		Demo demo = new Demo();
		demo.render();
	}

	private JFrame frame;
	private PixelPanel panel;
	private RayTracer tracer;
	private Thread updater;
	private volatile boolean done = false;

	public Demo() throws FileNotFoundException {
		Options options = new Options();
		options.width = 1024;
		options.height = 742;
		options.shadows = true;
		options.super_sampling = 1;
		options.trace_bounding_boxes = false;

		Scene scene = new SceneBuilder().loadScene("XML/shadows.sdl", options);

		panel = new PixelPanel(options);
		tracer = new RayTracer(scene, panel.image, options);

		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// panel.clear(scene.background);
		// panel.repaint();

		updater = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!done) {
					try {
						Thread.sleep(UPDATE_EVERY);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
					panel.repaint();
				}
			}
		}, "GUI Updater");
	}

	public void render() {
		updater.start();
		tracer.render();
		done = true;
		panel.repaint();
		panel.image.saveImage("image.png");
	}
}
