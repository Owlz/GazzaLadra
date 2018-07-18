package it.unisa.tirocinio.gazzaladra.file_writer;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AsyncFileWriter {
	/**
	 * Simple utility function to parse a List of strings into a CSV String
	 *
	 * @param values list of strings
	 * @return the formatted CSV String
	 */
	public static String parseLines(String[] values) {
		StringBuilder out = new StringBuilder();

		boolean firstVal = true;
		for (String val : values) {
			if (val == null) continue;

			if (!firstVal) {
				out.append(",");
			}
			out.append("\"");
			for (int i = 0; i < val.length(); i++) {
				char ch = val.charAt(i);
				if (ch == '\"')
					out.append("\""); // extra quote
				out.append(ch);
			}
			out.append("\"");
			firstVal = false;
		}
		out.append("\n");

		return out.toString();
	}

	/**
	 * A simple async writer
	 *
	 * @param values     is the list of all the data to write in the file
	 * @param folderName is the folder name, if it does not exist it will be created
	 * @param fileName   its the file name, if it does not exist it will be created
	 */
	static public void write(final String[] values, final String folderName, final String fileName) {
		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {

				final String data = parseLines(values);

				final String path_string = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + folderName;
				final File path = new File(path_string);

				if (!path.exists()) {
					path.mkdirs();
				}

				final File file = new File(path, fileName);

				try {
					file.createNewFile();
					FileOutputStream fOut = new FileOutputStream(file, true);
					OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

					myOutWriter.append(data);

					myOutWriter.close();

					// remember to flush the stream before closing it
					fOut.flush();
					fOut.close();
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("Exception", "File write failed: " + e.toString());
				}
			}
		});

	}

}
