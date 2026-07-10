package com.hospital.admin.doctor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.util.AppConfig;

public class DoctorImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String BUNDLED_IMAGE_DIR = "/resources/images/doctors";
	private static final String DEFAULT_IMAGE = "doctor_default.png";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = sanitizeFileName(request.getPathInfo());
		if (fileName == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Path uploadedImage = resolveUploadedImage(fileName);
		if (uploadedImage != null && Files.isRegularFile(uploadedImage)) {
			writeImage(response, uploadedImage, fileName);
			return;
		}

		InputStream bundledImage = getServletContext().getResourceAsStream(BUNDLED_IMAGE_DIR + "/" + fileName);
		if (bundledImage == null && !DEFAULT_IMAGE.equals(fileName)) {
			bundledImage = getServletContext().getResourceAsStream(BUNDLED_IMAGE_DIR + "/" + DEFAULT_IMAGE);
			fileName = DEFAULT_IMAGE;
		}

		if (bundledImage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		try (InputStream inputStream = bundledImage) {
			writeImage(response, inputStream, fileName);
		}
	}

	private Path resolveUploadedImage(String fileName) {
		String uploadDir = AppConfig.getDoctorImageUploadDir(getServletContext());
		if (uploadDir == null) {
			return null;
		}

		Path basePath = Paths.get(uploadDir).toAbsolutePath().normalize();
		Path imagePath = basePath.resolve(fileName).normalize();
		if (!imagePath.startsWith(basePath)) {
			return null;
		}

		return imagePath;
	}

	private String sanitizeFileName(String pathInfo) {
		if (pathInfo == null || pathInfo.trim().isEmpty()) {
			return null;
		}

		String fileName = Paths.get(pathInfo).getFileName().toString();
		return isAllowedImageName(fileName) ? fileName : null;
	}

	private boolean isAllowedImageName(String fileName) {
		if (fileName == null || fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
			return false;
		}

		String lowerFileName = fileName.toLowerCase(Locale.ROOT);
		return lowerFileName.endsWith(".jpg")
				|| lowerFileName.endsWith(".jpeg")
				|| lowerFileName.endsWith(".png")
				|| lowerFileName.endsWith(".gif")
				|| lowerFileName.endsWith(".webp");
	}

	private void writeImage(HttpServletResponse response, Path imagePath, String fileName) throws IOException {
		try (InputStream inputStream = Files.newInputStream(imagePath)) {
			writeImage(response, inputStream, fileName);
		}
	}

	private void writeImage(HttpServletResponse response, InputStream inputStream, String fileName) throws IOException {
		response.setContentType(resolveContentType(fileName));
		response.setHeader("Cache-Control", "public, max-age=86400");

		try (OutputStream outputStream = response.getOutputStream()) {
			byte[] buffer = new byte[8192];
			int readLength;
			while ((readLength = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, readLength);
			}
		}
	}

	private String resolveContentType(String fileName) {
		String lowerFileName = fileName.toLowerCase(Locale.ROOT);
		if (lowerFileName.endsWith(".png")) {
			return "image/png";
		}
		if (lowerFileName.endsWith(".gif")) {
			return "image/gif";
		}
		if (lowerFileName.endsWith(".webp")) {
			return "image/webp";
		}
		return "image/jpeg";
	}
}
