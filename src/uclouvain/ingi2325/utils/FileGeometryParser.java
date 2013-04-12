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
import uclouvain.ingi2325.parser.Splitter;

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
			Splitter s = new Splitter(line);
			String type = s.getWord().intern();
			s.eatSpace();

			if (type == "v") { // vertex
				_vertices.add(Point3D.valueOf(s.rest()));
			} else if (type == "vn") {
				_normals.add(Vector3D.valueOf(s.rest()));
			} else if (type == "f") { // surface

				Point3D[] vertices = new Point3D[3];
				Vector3D[] normals = new Vector3D[3];

				for (int i = 0; i < 3; i++) {
					vertices[i] = _vertices.get(s.getInt());
					if (s.have('/')) {
						s.getInt(); // TODO: texture
						if (s.have('/'))
							normals[i] = _normals.get(s.getInt());
						else
							normals[i] = null;
					}
					s.eatSpace();
				}

				if (s.more())
					System.err.println("Unhandled polygon with " + (4 + s.count(' ')) + " vertices");
				else
					geoms.add(new Triangle(vertices, normals));

			}
		}
		return geoms;
	}
}
