package com.hospital.common.util;

import javax.servlet.ServletContext;

public final class AppConfig {
	private static final String DEFAULT_KEY_FILE_PATH = "C:/qoeryqoeryqoe.txt";
	private static final String DOCTOR_IMAGE_CONTEXT_PARAM = "doctorImageUploadDir";
	private static final String DOCTOR_IMAGE_SYSTEM_PROPERTY = "hospital.doctor.image.dir";
	private static final String DOCTOR_IMAGE_ENV = "HOSPITAL_DOCTOR_IMAGE_DIR";
	private static final String KEY_FILE_SYSTEM_PROPERTY = "hospital.key.file";
	private static final String KEY_FILE_ENV = "HOSPITAL_KEY_FILE";

	private AppConfig() {
	}

	public static String getKeyFilePath() {
		String configuredPath = firstText(System.getProperty(KEY_FILE_SYSTEM_PROPERTY), System.getenv(KEY_FILE_ENV));
		return hasText(configuredPath) ? configuredPath : DEFAULT_KEY_FILE_PATH;
	}

	public static String getDoctorImageUploadDir(ServletContext servletContext) {
		String contextParam = servletContext == null ? null : servletContext.getInitParameter(DOCTOR_IMAGE_CONTEXT_PARAM);
		return firstText(System.getProperty(DOCTOR_IMAGE_SYSTEM_PROPERTY), System.getenv(DOCTOR_IMAGE_ENV), contextParam);
	}

	private static String firstText(String... values) {
		if (values == null) {
			return null;
		}

		for (String value : values) {
			if (hasText(value)) {
				return value.trim();
			}
		}

		return null;
	}

	private static boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}
}
