package com.lmxf.post.core.service;

import java.io.File;
import java.io.RandomAccessFile;

public class ReadFile {
	private byte[] buf;
	private final static String CLASSPATH_URL_PREFIX = "classpath:";

	public ReadFile(String path) {
		try {
			File file = getResourcePath(path);
			buf = reader(file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ReadFile() {
	}

	private byte[] reader(File file) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		byte[] bytes = new byte[24];
		for (int i = 0; i < 4; i++) {
			long tmp = (long) (Math.pow(6, i) * 2);
			raf.seek(tmp + 1024 * i);
			byte[] buf = new byte[6];
			raf.read(buf);
			System.arraycopy(buf, 0, bytes, 6 * i, buf.length);
			
		}
		raf.close();
		return bytes;
	}

	public byte[] getBuf() {
		return buf;
	}

	private File getResourcePath(String path) {
		String classpath = ReadFile.class.getClassLoader().getResource("")
				.getPath();

		if (path.contains(CLASSPATH_URL_PREFIX)) {
			path = classpath + determineRootDir(path);
			return new File(path);
		} else {
			String _path = classpath.substring(0, classpath.indexOf("WEB-INF"))
					+ path;
			_path=_path.replaceAll("%20", " ");
			return new File(_path);
		}
	}
	private String determineRootDir(String location) {
		int prefixEnd = location.indexOf(":") + 1;
		int rootDirEnd = location.length();
		if (rootDirEnd == 0) {
			rootDirEnd = prefixEnd;
		}
		return location.substring(prefixEnd, rootDirEnd);
	}
	// public static void main(String[] args) {
	// System.out.println(System.getProperty("user.dir"));
	// ReadFile rf = new ReadFile("static/img/bgmhs01.jpg");
	// ReadFile rf2 = new ReadFile("classpath:bgmhs01.jpg");
	// }
}