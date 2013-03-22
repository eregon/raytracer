package uclouvain.ingi2325.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import raytracer.Geometry;
import raytracer.Triangle;

public class FileGeometryParser {
	BufferedReader io;
	String line;

	ArrayList<Point3D> _vertices = new ArrayList<Point3D>();
	ArrayList<Vector3D> _normals = new ArrayList<Vector3D>();

	public FileGeometryParser(File file) throws FileNotFoundException {
		io = new BufferedReader(new FileReader(file));
		// Indexes start at 1
		_vertices.add(null);
		_normals.add(null);
	}

	public List<Geometry> parse() throws Exception {
		List<Geometry> geoms = new ArrayList<Geometry>();

		while ((line = io.readLine()) != null) {
			String[] parts = line.trim().split(" ", 2);
			String type = parts[0].intern();
			String value = parts[1];

			if (type == "v") { // vertex
				_vertices.add(Point3D.valueOf(value));
			} else if (type == "vn") {
				_normals.add(Vector3D.valueOf(value));
			} else if (type == "f") { // surface
				String[] triplets = value.split(" ");
				Point3D[] vertices = new Point3D[triplets.length];
				Vector3D[] normals = new Vector3D[triplets.length];
				for (int i = 0; i < triplets.length; i++) {
					String[] indexes = triplets[i].split("/");
					vertices[i] = _vertices.get(Integer.valueOf(indexes[0]));
					if (indexes.length >= 3)
						normals[i] = _normals.get(Integer.valueOf(indexes[2]));
					else
						normals[i] = null;
				}
				if (vertices.length == 3) {
					geoms.add(new Triangle(vertices, normals));
				} else {
					System.err.println("Unhandled polygon with " + vertices.length + " vertices");
				}
			}
		}
		return geoms;
	}
}
