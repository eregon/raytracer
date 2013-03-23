package raytracer;

import java.io.FileNotFoundException;

import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.SceneBuilder;

public class CLI {

	String outputFile = "image.png";
	int width = 512, height = 512;
	Image image;
	Scene scene;

	public CLI(String[] args) throws FileNotFoundException {
		scene = new SceneBuilder().loadScene("XML/simple5.sdl");
		image = new Image(width, height);
	}

	public void render() {
		RayTracer tracer = new RayTracer(scene, image, null);
		tracer.render();
		image.saveImage(outputFile);
	}

	public static void main(String[] args) throws FileNotFoundException {
		new CLI(args).render();
	}

}
