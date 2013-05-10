package uclouvain.ingi2325.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

import raytracer.geometry.Geometry;
import raytracer.geometry.Triangle;

public class BinaryMeshFile {
	static final int floatsByTriangle = 2 * 3 * 3;
	static final int sizeofTriangle = floatsByTriangle * (Float.SIZE / 8);

	private final File file;

	public BinaryMeshFile(File file) {
		this.file = file;
	}

	public void dump(List<Geometry> geometries) throws IOException {
		FileOutputStream os = new FileOutputStream(file);
		FileChannel channel = os.getChannel();
		ByteBuffer bb = ByteBuffer.allocateDirect(sizeofTriangle);

		for (Geometry geometry : geometries) {
			bb.clear();

			Triangle t = (Triangle) geometry;
			for (Point3D p : new Point3D[] { t._a, t._b, t._c }) {
				bb.putFloat(p.x);
				bb.putFloat(p.y);
				bb.putFloat(p.z);
			}
			for (Vector3D n : new Vector3D[] { t.na, t.nb, t.nc }) {
				bb.putFloat(n.x);
				bb.putFloat(n.y);
				bb.putFloat(n.z);
			}
			bb.flip();
			while (bb.hasRemaining())
				channel.write(bb);
		}
		channel.close();
		os.close();
	}

	public List<Geometry> load() throws IOException {
		FileInputStream is = new FileInputStream(file);
		FileChannel channel = is.getChannel();
		MappedByteBuffer mmap = channel.map(MapMode.READ_ONLY, 0, file.length());
		FloatBuffer buffer = mmap.asFloatBuffer();

		int size = (int) (file.length() / sizeofTriangle);
		ArrayList<Geometry> geometries = new ArrayList<Geometry>(size);

		float data[] = new float[floatsByTriangle];
		Point3D a, b, c;
		Vector3D na, nb, nc;
		for (int i = 0; i < size; i++) {
			buffer.get(data);

			a = new Point3D(data[0], data[1], data[2]);
			b = new Point3D(data[3], data[4], data[5]);
			c = new Point3D(data[6], data[7], data[8]);

			na = new Vector3D(data[9], data[10], data[11]);
			nb = new Vector3D(data[12], data[13], data[14]);
			nc = new Vector3D(data[15], data[16], data[17]);

			geometries.add(new Triangle(a, b, c, na, nb, nc));
		}

		is.close();
		return geometries;
	}
}
