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
		image = new Image(options);
	}

	public void render() {
		RayTracer tracer = new RayTracer(scene, image, options);
		tracer.render();
		image.saveImage(outputFile);
	}

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 2) {
			System.out.println("java raytracer.CLI SCENE.sdl OUTPUT.png");
			return;
		}

		Options options = new Options();
		options.width = 640;
		options.height = 480;
		options.super_sampling = 1;

		String sceneFile = args[0];
		String outputFile = args[1];

		new CLI(sceneFile, outputFile, options).render();
	}

}
