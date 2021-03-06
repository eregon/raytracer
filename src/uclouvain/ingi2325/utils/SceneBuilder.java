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
import raytracer.Material;
import raytracer.Options;
import raytracer.Shape;
import raytracer.Transformation;
import raytracer.geometry.Circle;
import raytracer.geometry.Cube;
import raytracer.geometry.Geometry;
import raytracer.geometry.Sphere;
import raytracer.geometry.Triangle;
import raytracer.light.AreaLight;
import raytracer.light.DirectionalLight;
import raytracer.light.Light;
import raytracer.light.PointLight;
import raytracer.light.SpotLight;
import uclouvain.ingi2325.exception.ParseError;
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
	 * The path to the xml directory. This path can be used to locate texture
	 * files.
	 */
	private String path;

	/**
	 * The scene being build
	 */
	private Scene scene;
	private boolean trace_bounding_boxes;

	final Map<String, Camera> cameras = new HashMap<String, Camera>();
	final Map<String, List<Geometry>> geometries = new HashMap<String, List<Geometry>>();
	final Map<String, Transformation> geometriesTransformations = new HashMap<String, Transformation>();
	final Map<String, Material> materials = new HashMap<String, Material>();
	final Map<String, Light> lights = new HashMap<String, Light>();

	Transformation transformation = Transformation.DEFAULT;

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
	public Scene loadScene(String filename, Options options) throws FileNotFoundException {

		File file = new File(filename);
		FileInputStream fileInputStream = new FileInputStream(file);

		InputSource inputSource = new InputSource(fileInputStream);
		path = file.getParentFile().getAbsolutePath() + "/";
		inputSource.setSystemId("file:///"
				+ file.getParentFile().getAbsolutePath() + "/");

		scene = new Scene();
		trace_bounding_boxes = options.trace_bounding_boxes;

		Parser parser = new Parser();
		parser.addHandler(this);

		System.out.print("Parsed in ");
		long t0 = System.currentTimeMillis();
		parser.parse(inputSource, /* validate */true, /* echo */false);
		long t1 = System.currentTimeMillis();
		System.out.println(String.format("%.3fs", (t1 - t0) / 1e3));

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
		fovy = deg2rad(fovy);
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
		lights.put(name, new DirectionalLight(direction, color, intensity));
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
		lights.put(name, new PointLight(position, color, intensity));
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
		angle = deg2rad(angle);
		lights.put(name, new SpotLight(position, direction, angle, color, intensity));
	}

	@Override
	public void startAreaLight(Point3D position, Vector3D a, Vector3D b, float intensity, Color color, String name) throws Exception {
		lights.put(name, new AreaLight(position, a, b, intensity, color));
	}

	@Override
	public void endAreaLight() throws Exception {
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

	@Override
	public void startCircle(float radius, String name) throws Exception {
		geometries.put(name, Arrays.asList((Geometry) new Circle(radius)));
	}

	@Override
	public void endCircle() throws Exception {
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
	public void startCylinder(float radius, float height, boolean capped, String name) throws Exception {
		List<Geometry> geoms = loadFileGeometry("cylinder.obj");
		if (!capped)
			geoms = rejectHorizontalTriangle(geoms);
		geometries.put(name, geoms);
		geometriesTransformations.put(name,
				Transformation.DEFAULT.scale(new Vector3D(radius, height / 2, radius)));
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
	public void startCone(float radius, float height, boolean capped, String name) throws Exception {
		List<Geometry> geoms = loadFileGeometry("cone.obj");
		if (!capped)
			geoms = rejectHorizontalTriangle(geoms);
		geometries.put(name, geoms);
		geometriesTransformations.put(name,
				Transformation.DEFAULT.scale(new Vector3D(radius, height / 2, radius)));
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
	public void startTorus(float innerRadius, float outerRadius, String name) throws Exception {
		geometries.put(name, loadFileGeometry("torus.obj"));
		geometriesTransformations.put(name,
				Transformation.DEFAULT.scale(new Vector3D(outerRadius / 1.5f)));
		// TODO: ignoring innerRadius for now
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
		geometries.put(name, loadFileGeometry("teapot.obj"));
		geometriesTransformations.put(name,
				Transformation.DEFAULT.scale(new Vector3D(size)));
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
			Point3D a, b, c;
			Vector3D na, nb, nc;

			a = coordinates[coordinateIndices[i]];
			na = normals[normalIndices[i]].normalized();
			b = coordinates[coordinateIndices[i + 1]];
			nb = normals[normalIndices[i + 1]].normalized();
			c = coordinates[coordinateIndices[i + 2]];
			nc = normals[normalIndices[i + 2]].normalized();

			geoms.add(new Triangle(a, b, c, na, nb, nc));
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
	public void startFileGeometry(String filename, String name) throws Exception {
		File file = new File(path, filename);
		File bmf = new File(path, filename + ".bmf");
		List<Geometry> geoms;
		if (bmf.exists() && file.lastModified() > bmf.lastModified())
			bmf.delete(); // Remove outdated file
		if (bmf.exists()) {
			geoms = new BinaryMeshFile(bmf).load();
		} else {
			geoms = new FileGeometryParser(file).parse();
			if (!bmf.exists() && file.length() > 1024 * 1024) {
				System.out.println("Creating BMF for " + filename);
				new BinaryMeshFile(bmf).dump(geoms);
			}
		}

		geometries.put(name, geoms);
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
	public void startPhongMaterial(Color color, float shininess, String name) throws Exception {
		materials.put(name, new Material(color, color, shininess));
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
	public void startLinearCombinedMaterial(String m1, float weight1, String m2,
			float weight2, String name) throws Exception {
		materials.put(name, materials.get(m1).mul(weight1).add(materials.get(m2).mul(weight2)));
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
		for (String lightName : lightNames) {
			Light light = lights.get(lightName);
			if (light == null)
				throw new ParseError("Light " + lightName + " not found!");
			scene.lights.add(light);
		}
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
			Transformation transform = transformation;
			if (geometriesTransformations.containsKey(geometryName))
				transform = transform.onTopOf(geometriesTransformations.get(geometryName));

			for (Geometry geometry : geoms) {
				Shape shape = new Shape(geometry, material, transform);
				scene.objects.add(shape);

				if (trace_bounding_boxes) {
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
		angle = deg2rad(angle);
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

	private float deg2rad(float angle) {
		return (float) (angle / 180 * Math.PI);
	}

	private List<Geometry> loadFileGeometry(String filename) throws IOException, ParseError {
		return new FileGeometryParser(new File("XML", filename)).parse();
	}

	private List<Geometry> rejectHorizontalTriangle(List<Geometry> geoms) {
		List<Geometry> filtered = new ArrayList<Geometry>();
		for (Geometry g : geoms) {
			Triangle t = (Triangle) g;
			if (t._a.y != t._b.y || t._b.y != t._c.y)
				filtered.add(g);
		}
		return filtered;
	}
}
