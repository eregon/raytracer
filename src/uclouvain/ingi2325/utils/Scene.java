package uclouvain.ingi2325.utils;

import java.util.ArrayList;
import java.util.List;

import raytracer.Camera;
import raytracer.Light;
import raytracer.Shape;

/**
 * Represents a scene
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class Scene {
	public Camera camera;
	public List<Shape> objects = new ArrayList<Shape>();
	public List<Light> lights = new ArrayList<Light>();
	public Color background;
}
