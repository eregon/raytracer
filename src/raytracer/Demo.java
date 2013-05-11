package raytracer;

/**
 * The demo :-)
 *
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class Demo {
	public static void main(String[] args) throws Exception {
		String sceneFile = "XML/porsche.sdl";
		String outputFile = "image.png";

		Options options = new Options();
		options.width = 1024;
		options.height = 742;
		options.shadows = true;
		options.super_sampling = 2;
		options.trace_bounding_boxes = false;

		CLI cli = new CLI(sceneFile, outputFile, options);
		cli.renderGUI();
	}
}
