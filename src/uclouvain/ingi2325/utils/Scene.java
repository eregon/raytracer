package uclouvain.ingi2325.utils;

import java.util.ArrayList;
import java.util.List;

import raytracer.Camera;
import raytracer.Geometry;

/**
 * Represents a scene
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class Scene {
	public Camera camera;
	public List<Geometry> objects = new ArrayList<Geometry>();
}
