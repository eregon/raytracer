import java.io.FileNotFoundException;

import javax.swing.JFrame;

import raytracer.RayTracer;
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
		Scene scene = new SceneBuilder().loadScene("XML/example.sdl");

		panel = new PixelPanel(640, 480);
		tracer = new RayTracer(scene, panel);

		frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		panel.clear(scene.background);
	}

	public void render() {
		tracer.render();
		panel.repaint();
		panel.saveImage("image.png");
	}
}
