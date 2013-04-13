package uclouvain.ingi2325.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import raytracer.Geometry;
import raytracer.Triangle;
import uclouvain.ingi2325.exception.ParseException;
import uclouvain.ingi2325.parser.Splitter;

public class FileGeometryParser {
	final BufferedReader io;

	final ArrayList<Point3D> _vertices = new ArrayList<Point3D>();
	final ArrayList<Vector3D> _normals = new ArrayList<Vector3D>();

	public FileGeometryParser(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		if (file.getName().endsWith(".gz"))
			is = new GZIPInputStream(is);
		io = new BufferedReader(new InputStreamReader(is, Charset.forName("ASCII")));

		// Indexes start at 1
		_vertices.add(null);
		_normals.add(null);
	}

	public List<Geometry> parse() throws IOException, ParseException {
		List<Geometry> geoms = new ArrayList<Geometry>();

		String line;
		while ((line = io.readLine()) != null) {
			Splitter s = new Splitter(line);
			String type = s.getWord().intern();
			s.eatSpace();

			if (type == "v") { // vertex
				_vertices.add(Point3D.valueOf(s.rest()));
			} else if (type == "vn") {
				_normals.add(Vector3D.valueOf(s.rest()));
			} else if (type == "f") { // surface
				Point3D a, b, c;
				Vector3D na, nb, nc;

				a = _vertices.get(s.getInt());
				na = getNormal(s);
				s.eatSpace();

				b = _vertices.get(s.getInt());
				nb = getNormal(s);
				s.eatSpace();

				c = _vertices.get(s.getInt());
				nc = getNormal(s);

				if (s.more())
					System.err.println("Unhandled polygon with " + (4 + s.count(' ')) + " vertices");
				else
					geoms.add(new Triangle(a, b, c, na, nb, nc));

			}
		}
		return geoms;
	}

	private Vector3D getNormal(Splitter s) {
		if (s.have('/')) {
			if (s.current() != '/')
				s.getInt(); // TODO: texture
			if (s.have('/'))
				return _normals.get(s.getInt());
		}
		return null;
	}
}
