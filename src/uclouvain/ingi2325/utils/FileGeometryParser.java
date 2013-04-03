package uclouvain.ingi2325.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import raytracer.Geometry;
import raytracer.Triangle;
import uclouvain.ingi2325.exception.ParseException;

public class FileGeometryParser {
	BufferedReader io;
	String line;

	ArrayList<Point3D> _vertices = new ArrayList<Point3D>();
	ArrayList<Vector3D> _normals = new ArrayList<Vector3D>();

	public FileGeometryParser(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		if (file.getName().endsWith(".gz"))
			is = new GZIPInputStream(is);
		io = new BufferedReader(new InputStreamReader(is));

		// Indexes start at 1
		_vertices.add(null);
		_normals.add(null);
	}

	public List<Geometry> parse() throws IOException, ParseException {
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
