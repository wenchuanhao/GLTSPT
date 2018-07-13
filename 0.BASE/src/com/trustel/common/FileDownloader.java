package com.trustel.common;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader extends Thread {
	private String url;
	private String filename;

	private FileDownloader() {

	}

	public FileDownloader(String url, String filename) {
		this.url = url;
		this.filename = filename;
	}

	public void run() {
		try {
			download();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void download() throws RuntimeException {
		try {
			URL temp = new URL(url);
			InputStream input = temp.openStream();
			FileOutputStream output = new FileOutputStream(filename);

			byte[] buffer = new byte[2048];
			int length = 0;
			do {
				length = input.read(buffer);
				if (length > 0)
					output.write(buffer, 0, length);
			} while (length > 0);

			output.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getClass().getName() + ": "
					+ e.getMessage() + " at Downloader.download");
		}
	}
}
