package raytracer;

/**
 * The demo :-)
 *
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class Demo {
	public static void main(String[] args) throws Exception {
		String sceneFile = "XML/shadows.sdl";
		String outputFile = "image.png";

		Options options = new Options();
		options.width = 800;
		options.height = 600;
		options.shadows = true;
		options.super_sampling = 1;
		options.trace_bounding_boxes = false;

		CLI cli = new CLI(sceneFile, outputFile, options);
		cli.renderGUI();
	}
}
