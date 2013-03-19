package uclouvain.ingi2325.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import raytracer.Triangle;

public class FileGeometryParser {
	BufferedReader io;
	String line;

	ArrayList<Point3D> vertices = new ArrayList<Point3D>();

	public FileGeometryParser(File file) throws FileNotFoundException {
		io = new BufferedReader(new FileReader(file));
		vertices.add(null); // Indexes start at 1
	}

	public void parse() throws Exception {
		while ((line = io.readLine()) != null) {
			String[] parts = line.trim().split(" ", 2);
			String type = parts[0].intern();
			String value = parts[1];

			if (type == "v") { // vertex
				vertices.add(Point3D.valueOf(value));
			} else if (type == "f") { // surface
				String[] triplets = value.split(" ");
				Point3D[] points = new Point3D[triplets.length];
				for (int i = 0; i < triplets.length; i++) {
					points[i] = vertices.get(Integer.valueOf(triplets[i].split("/")[0]));
				}
				if (points.length == 3) {
					Triangle t = new Triangle("", points[0], points[1], points[2]);
				} else {
					System.err.println("Unhandled polygon with " + points.length + " vertices");
				}
			}
		}
	}
}
