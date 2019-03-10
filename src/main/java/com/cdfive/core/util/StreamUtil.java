package com.cdfive.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {

	public static byte[] getByteFromStream(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int length = 0;
		while (length != -1) {
			try {
				length = is.read(b);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (length != -1) {
				baos.write(b, 0, length);
			}
		}
		if (is != null) {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}
		return baos.toByteArray();
	}

	public static String getStringFromStream(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return sb.toString();
	}
}
