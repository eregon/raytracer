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

	public static void main(String[] args) throws Exception {
		Demo demo = new Demo();
		demo.render();
	}

	private JFrame frame;
	private PixelPanel panel;
	private RayTracer tracer;

	public Demo() throws FileNotFoundException {
		Scene scene = new SceneBuilder().loadScene("XML/simple15.sdl");

		panel = new PixelPanel(512, 512);
		tracer = new RayTracer(scene, panel.image, new Runnable() {
			@Override
			public void run() {
				panel.repaint();
			}
		});

		frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// panel.clear(scene.background);
		// panel.repaint();
	}

	public void render() {
		tracer.render();
		panel.repaint();
		panel.image.saveImage("image.png");
	}
}
