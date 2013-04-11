package uclouvain.ingi2325.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;

import raytracer.Camera;
import raytracer.Cube;
import raytracer.Geometry;
import raytracer.Light;
import raytracer.Material;
import raytracer.PointLight;
import raytracer.RayTracer;
import raytracer.Shape;
import raytracer.Sphere;
import raytracer.Transformation;
import raytracer.Triangle;
import uclouvain.ingi2325.exception.ParseException;
import uclouvain.ingi2325.parser.Parser;
import uclouvain.ingi2325.parser.ParserHandler;

/**
 * Represents a builder for scene
 * 
 * @author Antoine Cailliau <antoine.cailliau@uclouvain.be>
 * @author Julien Dupuis
 */
public class SceneBuilder implements ParserHandler {

	/**
	 * The scene being build
	 */
	private Scene scene = null;

	Map<String, Camera> cameras = new HashMap<String, Camera>();
	Map<String, List<Geometry>> geometries = new HashMap<String, List<Geometry>>();
	Map<String, Material> materials = new HashMap<String, Material>();
	Map<String, Light> lights = new HashMap<String, Light>();

	Transformation transformation = Transformation.DEFAULT;

	/**
	 * Returns the build scene
	 * 
	 * @return the build scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * The path to the xml directory. This path can be used to locate texture
	 * files.
	 */
	private String path = null;

	/**
	 * Returns the path
	 * 
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Load a scene.
	 * 
	 * @param filename
	 *            The name of the file that contains the scene.
	 * @return The scene, or null if something went wrong.
	 * @throws FileNotFoundException
	 *             The file corresponding to the given filename could not be
	 *             found.
	 */
	public Scene loadScene(String filename) throws FileNotFoundException {

		File file = new File(filename);
		FileInputStream fileInputStream = new FileInputStream(file);

		InputSource inputSource = new InputSource(fileInputStream);
		path = file.getParentFile().getAbsolutePath() + "/";
		inputSource.setSystemId("file:///"
				+ file.getParentFile().getAbsolutePath() + "/");

		scene = new Scene();

		Parser parser = new Parser();
		parser.addHandler(this);

		if (!parser.parse(inputSource, /* validate */true, /* echo */false)) {
			scene = null;
		}

		return scene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startSdl()
	 */
	@Override
	public void startSdl() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endSdl()
	 */
	@Override
	public void endSdl() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startCameras()
	 */
	@Override
	public void startCameras() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endCameras()
	 */
	@Override
	public void endCameras() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startCamera(uclouvain.ingi2325
	 * .utils.Point3D, uclouvain.ingi2325.utils.Vector3,
	 * uclouvain.ingi2325.utils.Vector3, float, java.lang.String)
	 */
	@Override
	public void startCamera(Point3D position, Vector3D direction, Vector3D up,
			float fovy, String name) throws Exception {
		cameras.put(name, new Camera(position, direction, up, fovy));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endCamera()
	 */
	@Override
	public void endCamera() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startLights()
	 */
	@Override
	public void startLights() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endLights()
	 */
	@Override
	public void endLights() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startDirectionalLight(uclouvain
	 * .ingi2325.utils.Vector3, float, uclouvain.ingi2325.utils.Color,
	 * java.lang.String)
	 */
	@Override
	public void startDirectionalLight(Vector3D direction, float intensity,
			Color color, String name) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endDirectionalLight()
	 */
	@Override
	public void endDirectionalLight() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startPointLight(uclouvain.ingi2325
	 * .utils.Point3D, float, uclouvain.ingi2325.utils.Color, java.lang.String)
	 */
	@Override
	public void startPointLight(Point3D position, float intensity, Color color,
			String name) throws Exception {
		lights.put(name, new PointLight(color, position, intensity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endPointLight()
	 */
	@Override
	public void endPointLight() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startSpotLight(uclouvain.ingi2325
	 * .utils.Point3D, uclouvain.ingi2325.utils.Vector3, float, float,
	 * uclouvain.ingi2325.utils.Color, java.lang.String)
	 */
	@Override
	public void startSpotLight(Point3D position, Vector3D direction,
			float angle, float intensity, Color color, String name)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endSpotLight()
	 */
	@Override
	public void endSpotLight() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startGeometry()
	 */
	@Override
	public void startGeometry() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endGeometry()
	 */
	@Override
	public void endGeometry() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startSphere(float,
	 * java.lang.String)
	 */
	@Override
	public void startSphere(float radius, String name) throws Exception {
		geometries.put(name, Arrays.asList((Geometry) new Sphere(radius)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endSphere()
	 */
	@Override
	public void endSphere() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startCylinder(float, float,
	 * boolean, java.lang.String)
	 */
	@Override
	public void startCylinder(float radius, float height, boolean capped,
			String name) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endCylinder()
	 */
	@Override
	public void endCylinder() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startCone(float, float,
	 * boolean, java.lang.String)
	 */
	@Override
	public void startCone(float radius, float height, boolean capped,
			String name) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endCone()
	 */
	@Override
	public void endCone() throws Exception {
	}

	@Override
	public void startCube(float size, String name) throws Exception {
		Geometry cube = new Cube(size);
		geometries.put(name, Arrays.asList(cube));
	}

	@Override
	public void endCube() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startTorus(float, float,
	 * java.lang.String)
	 */
	@Override
	public void startTorus(float innerRadius, float outerRadius, String name)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTorus()
	 */
	@Override
	public void endTorus() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startTeapot(float,
	 * java.lang.String)
	 */
	@Override
	public void startTeapot(float size, String name) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTeapot()
	 */
	@Override
	public void endTeapot() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startIndexedTriangleSet(uclouvain
	 * .ingi2325.utils.Point3D[], uclouvain.ingi2325.utils.Vector3[],
	 * uclouvain.ingi2325.utils.TextureCoordinates[], int[], int[], int[],
	 * java.lang.String)
	 */
	@Override
	public void startIndexedTriangleSet(Point3D[] coordinates,
			Vector3D[] normals, TextureCoordinates[] textureCoordinates,
			int[] coordinateIndices, int[] normalIndices,
			int[] textureCoordinateIndices, String name) throws Exception {
		List<Geometry> geoms = new ArrayList<Geometry>();
		for (int i = 0; i < coordinateIndices.length; i += 3) {
			Point3D[] vertices = new Point3D[3];
			Vector3D[] _normals = new Vector3D[3];
			for (int j = 0; j < 3; j++) {
				vertices[j] = coordinates[coordinateIndices[i + j]];
				_normals[j] = normals[normalIndices[i + j]];
			}
			geoms.add(new Triangle(vertices, normals));
		}
		geometries.put(name, geoms);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endIndexedTriangleSet()
	 */
	@Override
	public void endIndexedTriangleSet() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startFileGeometry(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	public void startFileGeometry(String filename, String name) {
		File file = new File(path, filename);
		try {
			geometries.put(name, new FileGeometryParser(file).parse());
		} catch (IOException e) {
			System.err.println("Could not load " + file);
			System.err.println("  " + e);
		} catch (ParseException e) {
			System.err.println("Could not parse " + file);
			System.err.println("  " + e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endFileGeometry()
	 */
	@Override
	public void endFileGeometry() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startTextures()
	 */
	@Override
	public void startTextures() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTextures()
	 */
	@Override
	public void endTextures() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startTexture(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void startTexture(String src, String name) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTexture()
	 */
	@Override
	public void endTexture() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startMaterials()
	 */
	@Override
	public void startMaterials() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endMaterials()
	 */
	@Override
	public void endMaterials() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startDiffuseMaterial(uclouvain
	 * .ingi2325.utils.Color, java.lang.String)
	 */
	@Override
	public void startDiffuseMaterial(Color color, String name) throws Exception {
		materials.put(name, new Material(color));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endDiffuseMaterial()
	 */
	@Override
	public void endDiffuseMaterial() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startPhongMaterial(uclouvain.
	 * ingi2325.utils.Color, float, java.lang.String)
	 */
	@Override
	public void startPhongMaterial(Color color, float shininess, String name)
			throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endPhongMaterial()
	 */
	@Override
	public void endPhongMaterial() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startLinearCombinedMaterial(java
	 * .lang.String, float, java.lang.String, float, java.lang.String)
	 */
	@Override
	public void startLinearCombinedMaterial(String material1Name,
			float weight1, String material2Name, float weight2, String name)
			throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endLinearCombinedMaterial()
	 */
	@Override
	public void endLinearCombinedMaterial() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startScene(java.lang.String,
	 * java.lang.String[], uclouvain.ingi2325.utils.Color)
	 */
	@Override
	public void startScene(String cameraName, String[] lightNames,
			Color background) throws Exception {
		scene.camera = cameras.get(cameraName);
		scene.background = background;
		for (String lightName : lightNames)
			scene.lights.add(lights.get(lightName));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endScene()
	 */
	@Override
	public void endScene() throws Exception {
		// System.out.println(scene.objects.size() + " shapes to draw");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#startShape(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void startShape(String geometryName, String materialName,
			String textureName) throws Exception {
		List<Geometry> geoms = geometries.get(geometryName);
		Material material = materials.get(materialName);
		if (geoms == null) {
			System.err.println("Could not find geometry named " + geometryName);
		} else if (material == null) {
			System.err.println("Could not find material named " + materialName);
		} else {
			for (Geometry geometry : geoms) {
				Shape shape = new Shape(geometry, material, transformation);
				scene.objects.add(shape);

				if (RayTracer.TRACE_BOUNDING_BOXES) {
					scene.objects.add(new Shape(new Cube(shape.boundingBox),
							new Material(new Color(1, 0, 0)), Transformation.DEFAULT));
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endShape()
	 */
	@Override
	public void endShape() throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startRotate(uclouvain.ingi2325
	 * .utils.Vector3, float)
	 */
	@Override
	public void startRotate(Vector3D axis, float angle) throws Exception {
		angle = (float) (angle / 180 * Math.PI);
		transformation = transformation.rotate(axis, angle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endRotate()
	 */
	@Override
	public void endRotate() throws Exception {
		transformation = transformation.parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startTranslate(uclouvain.ingi2325
	 * .utils.Vector3)
	 */
	@Override
	public void startTranslate(Vector3D vector) throws Exception {
		transformation = transformation.translate(vector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endTranslate()
	 */
	@Override
	public void endTranslate() throws Exception {
		transformation = transformation.parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * uclouvain.ingi2325.parser.ParserHandler#startScale(uclouvain.ingi2325
	 * .utils.Vector3)
	 */
	@Override
	public void startScale(Vector3D scale) throws Exception {
		transformation = transformation.scale(scale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uclouvain.ingi2325.parser.ParserHandler#endScale()
	 */
	@Override
	public void endScale() throws Exception {
		transformation = transformation.parent;
	}

}
