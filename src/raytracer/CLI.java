package raytracer;

import java.io.FileNotFoundException;

import uclouvain.ingi2325.utils.Image;
import uclouvain.ingi2325.utils.Scene;
import uclouvain.ingi2325.utils.SceneBuilder;

public class CLI {

	String outputFile = "image.png";
	Image image;
	Scene scene;

	public CLI(String[] args) throws FileNotFoundException {
		scene = new SceneBuilder().loadScene("XML/simple5.sdl");
		image = new Image(512, 512);
	}

	public void render() {
		RayTracer tracer = new RayTracer(scene, image);
		tracer.render();
		image.saveImage(outputFile);
	}

	public static void main(String[] args) throws FileNotFoundException {
		new CLI(args).render();
	}

}
