package raytracer;

import java.io.FileNotFoundException;

import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.SceneBuilder;

public class CLI {

	String outputFile;
	int width = 512, height = 512;
	Image image;
	Scene scene;

	public CLI(String sceneFile, String outputFile) throws FileNotFoundException {
		scene = new SceneBuilder().loadScene(sceneFile);
		image = new Image(width, height);
		this.outputFile = outputFile;
	}

	public void render() {
		RayTracer tracer = new RayTracer(scene, image, null);
		tracer.render();
		image.saveImage(outputFile);
	}

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 2) {
			System.out.println("java raytracer.CLI SCENE.sdl OUTPUT.png");
			return;
		}

		String sceneFile = args[0];
		String outputFile = args[1];

		new CLI(sceneFile, outputFile).render();
	}

}
