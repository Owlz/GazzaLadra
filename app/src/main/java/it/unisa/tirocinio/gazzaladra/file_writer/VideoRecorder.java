package it.unisa.tirocinio.gazzaladra.file_writer;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class VideoRecorder {
	final static String FORMATO = ".mp4";

	public static File getNewFile(String folderName, String subFileName) {
		final String fileName = subFileName + FORMATO;
		final String path_string = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + folderName;
		final File path = new File(path_string);
		if (!path.exists()) {
			path.mkdirs();
		}

		final File file = new File(path, fileName);

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Exception", "Can't create the file: " + file.getName());
		}
		return file;
	}
}
