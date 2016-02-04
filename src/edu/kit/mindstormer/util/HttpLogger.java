package edu.kit.mindstormer.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import edu.kit.mindstormer.program.OperatingSystem;

public final class HttpLogger {

	private static String CHARSET = "UTF-8";
	private static String BASE_URL = "http://192.168.0.213:5000/log";

	private static final HttpLogger instance = new HttpLogger();
	private static boolean enabled = true;

	private HttpLogger() {
		// ctor
	}

	public static HttpLogger getInstance() {
		return instance;
	}

	public static void disable() {
		enabled = false;
	}

	public void log(String message) {
		if (!enabled)
			return;

		try {
			String logEntry = format(message);
			postMessage(logEntry);
		} catch (Exception e) {
			enabled = false;
			OperatingSystem.displayText("Log error: " + e.getMessage());
		}
	}

	private String format(String message) {
		StackTraceElement[] stackTrace = new Exception().getStackTrace();
		String name = stackTrace[stackTrace.length - 1].getClassName();
		name = pad(name.substring(name.lastIndexOf('.') + 1, name.length()), 15, ' ');
		return name + " | " + message;
	}

	private String pad(String s, int width, char fill) {
		int validLength = Math.min(s.length(), width);
		String padded = s.substring(0, validLength);
		padded += new String(new char[width - validLength]).replace('\0', fill);
		return padded;
	}

	private void postMessage(String parameter) throws IOException {
		try {
			String query = String.format("message=%s", URLEncoder.encode(parameter, CHARSET));
			String url = BASE_URL + "?" + query;
			new URL(url).openConnection();
		} catch (UnsupportedEncodingException | MalformedURLException e) {
			OperatingSystem.displayText("URL error: " + e.getMessage());
		}
	}
}
