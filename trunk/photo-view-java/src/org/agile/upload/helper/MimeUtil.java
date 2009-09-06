package org.agile.upload.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MimeUtil {

	private static String res = "/org/agile/upload/helper/mime.xml";
	private static String unknown = "application/octet-stream";
	private static Map<String, String> map = new HashMap<String, String>();
	static {
		try {
			init();
		} catch (IOException e) {
			System.err.println("Init mime xml error {" + res + "}");
		}
	}

	private static void init() throws IOException {
		InputStream is = MimeUtil.class.getResourceAsStream(res);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null, key = null, value = null;
		boolean bf = false, clear = false;

		while ((line = reader.readLine()) != null) {
			if (line.indexOf("<mime-mapping>") >= 0) {
				if (bf) {
					clear = true;
				} else {
					bf = true;
				}
			}
			if (line.indexOf("</mime-mapping>") >= 0) {
				if (bf && key != null && value != null) {
					key = key.trim();
					value = value.trim();
					map.put(key, value);
					// System.out.println(key + " --->  " + value);
				}
				clear = true;
			}
			if (line.indexOf("<extension>") >= 0 && line.indexOf("</extension>") >= 0 && bf) {
				key = line.substring(line.indexOf("<extension>") + 11, line.indexOf("</extension>"));
			}
			if (line.indexOf("<mime-type>") >= 0 && line.indexOf("</mime-type>") >= 0 && bf) {
				value = line.substring(line.indexOf("<mime-type>") + 11, line.indexOf("</mime-type>"));
			}
			if (clear) {
				key = null;
				value = null;
				bf = false;
				clear = false;
			}
		}
	}

	public static String getMimeBySuffix(String suf) {
		String mime =  map.get(suf);
		if (mime != null) {
			return mime;
		} else {
			return unknown;
		}
	}

	public static String getMimeByFileName(String name) {
		int s = name.lastIndexOf(".");
		if (s > 0) {
			String suf = name.substring(s);
			return getMimeBySuffix(suf);
		}
		return unknown;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MimeUtil.getMimeByFileName("sone.jpeg");
	}

}
