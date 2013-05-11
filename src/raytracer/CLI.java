package raytracer;

import java.io.FileNotFoundException;

import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.PixelPanel;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.SceneBuilder;

public class CLI {

	String outputFile;
	Options options;
	Scene scene;
	Image image;
	RayTracer tracer;

	public CLI(String sceneFile, String outputFile, Options options) throws FileNotFoundException {
		this.options = options;
		this.outputFile = outputFile;
		scene = new SceneBuilder().loadScene(sceneFile, options);
		image = new Image(options);
		tracer = new RayTracer(scene, image, options);
	}

	public void saveImage() {
		image.saveImage(outputFile);
	}

	public void trace() {
		tracer.render();
		saveImage();
	}

	public void renderCLI() {
		trace();
	}

	public void renderGUI() {
		PixelPanel panel = new PixelPanel(image, options);

		GUI gui = new GUI(panel, tracer, scene, options);
		gui.start();
		trace();
		gui.done();
	}

	public static void usage() {
		System.out.println("java raytracer.CLI [OPTIONS] SCENE.sdl OUTPUT.png");
		System.out.println("  -gui          Render in a window");
		System.out.println("  -width=N");
		System.out.println("  -height=N");
		System.out.println("  -noShadows");
		System.out.println("  -ss=N         Supersampling (N*N samples per pixel)");
		System.out.println("  -bb           Draw bounding boxes");
		System.exit(1);
	}

	public static void main(String[] args) throws FileNotFoundException {
		String sceneFile = null;
		String outputFile = null;
		boolean gui = false;

		Options options = new Options();
		options.width = 640;
		options.height = 480;
		options.shadows = true;
		options.super_sampling = 1;
		options.trace_bounding_boxes = false;

		for (String arg : args) {
			arg = arg.intern();

			if (arg == "-gui")
				gui = true;

			else if (arg == "-noShadows")
				options.shadows = false;

			else if (arg == "-bb")
				options.trace_bounding_boxes = true;

			else if (arg.startsWith("-width="))
				options.width = Integer.parseInt(arg.substring("-width=".length()));

			else if (arg.startsWith("-height="))
				options.height = Integer.parseInt(arg.substring("-height=".length()));

			else if (arg.startsWith("-ss="))
				options.super_sampling = Integer.parseInt(arg.substring("-ss=".length()));

			else {
				if (arg.startsWith("-")) {
					System.err.println("Unrecognized option: " + arg);
					usage();
				}

				if (sceneFile == null)
					sceneFile = arg;
				else if (outputFile == null)
					outputFile = arg;
				else
					usage();
			}
		}
		if (sceneFile == null || outputFile == null)
			usage();

		CLI cli = new CLI(sceneFile, outputFile, options);
		if (gui)
			cli.renderGUI();
		else
			cli.renderCLI();
	}
}
