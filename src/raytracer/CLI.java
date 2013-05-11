package raytracer;

import java.io.FileNotFoundException;

import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.SceneBuilder;

public class CLI {

	String outputFile;
	Options options;
	Image image;
	Scene scene;

	public CLI(String sceneFile, String outputFile, Options options) throws FileNotFoundException {
		this.options = options;
		this.outputFile = outputFile;
		scene = new SceneBuilder().loadScene(sceneFile, options);
	}

	public void render() {
		image = new Image(options);
		RayTracer tracer = new RayTracer(scene, image, options);
		tracer.render();
		image.saveImage(outputFile);
	}

	public static void usage() {
		System.out.println("java raytracer.CLI [OPTIONS] SCENE.sdl OUTPUT.png");
		System.exit(1);
	}

	public static void main(String[] args) throws FileNotFoundException {
		String sceneFile = null;
		String outputFile = null;

		Options options = new Options();
		options.width = 640;
		options.height = 480;
		options.shadows = true;
		options.super_sampling = 1;
		options.trace_bounding_boxes = false;

		for (String arg : args) {
			arg = arg.intern();

			if (arg == "-noShadows")
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

		new CLI(sceneFile, outputFile, options).render();
	}
}
