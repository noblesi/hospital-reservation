package com.hospital.admin.doctor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.hospital.admin.doctor.AdminDoctorService;
import com.hospital.admin.doctor.dto.AdminDoctorFormDTO;
import com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO;
import com.hospital.common.dto.DoctorCareerDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorEducationDTO;
import com.hospital.common.dto.DoctorScheduleDTO;

@MultipartConfig(
		fileSizeThreshold = 1024 * 1024,
		maxFileSize = 5 * 1024 * 1024,
		maxRequestSize = 20 * 1024 * 1024
)
public class AdminDoctorFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DOCTOR_IMAGE_DIR = "/resources/images/doctors";
	private static final String DEFAULT_DOCTOR_IMAGE = "doctor_default.png";
	private static final DateTimeFormatter FILE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	private final AdminDoctorService adminDoctorService = new AdminDoctorService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer doctorLicenseNo = parseInt(request.getParameter("doctorLicenseNo"));
		if (doctorLicenseNo != null) {
			if (!adminDoctorService.checkDoctorLicenseNo(doctorLicenseNo)) {
				request.getSession().setAttribute("errorMessage", "등록되지 않은 의료진 면허번호입니다.");
				response.sendRedirect(request.getContextPath() + "/admin/doctor/list.do");
				return;
			}

			AdminDoctorFormDTO formDTO = adminDoctorService.searchDoctorDetail(doctorLicenseNo);
			request.setAttribute("doctor", formDTO.getDoctorDTO());
			request.setAttribute("departmentList", formDTO.getDepartmentList());
			request.setAttribute("statusList", formDTO.getStatusList());
			request.setAttribute("positionList", formDTO.getPositionList());
			request.setAttribute("careerList", formDTO.getCareerList());
			request.setAttribute("scheduleList", formDTO.getScheduleList());
			request.setAttribute("scheduleDTOList", formDTO.getScheduleList());
			request.setAttribute("educationList", formDTO.getEducationList());
		} else {
			AdminDoctorFormOptionDTO formOptions = adminDoctorService.getDoctorFormOptions();
			request.setAttribute("departmentList", formOptions.getDepartmentList());
			request.setAttribute("statusList", formOptions.getStatusList());
			request.setAttribute("positionList", formOptions.getPositionList());
		}

		request.setAttribute("adminMenu", "doctor");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/doctor/adminDoctorDetail.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		DoctorDTO doctor = null;
		try {
			doctor = createDoctorDTO(request);
		} catch (IllegalArgumentException e) {
			request.getSession().setAttribute("errorMessage", e.getMessage());
			response.sendRedirect(request.getContextPath() + "/admin/doctor/list.do");
			return;
		} catch (IllegalStateException e) {
			request.getSession().setAttribute("errorMessage", "이미지 파일은 5MB 이하로 업로드해주세요.");
			response.sendRedirect(request.getContextPath() + "/admin/doctor/list.do");
			return;
		}

		if (doctor == null) {
			request.getSession().setAttribute("errorMessage", "의료진 저장 요청이 올바르지 않습니다.");
			response.sendRedirect(request.getContextPath() + "/admin/doctor/list.do");
			return;
		}

		AdminDoctorFormDTO formDTO = new AdminDoctorFormDTO();
		formDTO.setDoctorDTO(doctor);
		formDTO.setEducationList(createEducationList(request, doctor.getDoctorLicenseNo()));
		formDTO.setCareerList(createCareerList(request, doctor.getDoctorLicenseNo()));
		formDTO.setScheduleList(createScheduleList(request, doctor.getDoctorLicenseNo()));

		boolean success = adminDoctorService.checkDoctorLicenseNo(doctor.getDoctorLicenseNo())
				? adminDoctorService.modifyDoctor(formDTO)
				: adminDoctorService.registerDoctor(formDTO);

		if (success) {
			request.getSession().setAttribute("message", "의료진 정보를 저장했습니다.");
		} else {
			request.getSession().setAttribute("errorMessage", "의료진 정보를 저장하지 못했습니다.");
		}

		response.sendRedirect(request.getContextPath() + "/admin/doctor/list.do");
	}

	private Integer parseInt(String value) {
		if (value == null || value.isBlank() || !value.matches("\\d+")) {
			return null;
		}

		return Integer.valueOf(value);
	}

	private DoctorDTO createDoctorDTO(HttpServletRequest request) throws IOException, ServletException {
		Integer doctorLicenseNo = parseInt(firstValue(request, "doctorLicenseNo", "licenseNo"));
		if (doctorLicenseNo == null) {
			return null;
		}

		DoctorDTO doctor = new DoctorDTO();
		doctor.setDoctorLicenseNo(doctorLicenseNo);
		doctor.setDeptNo(firstValue(request, "deptNo", "department"));
		doctor.setName(firstValue(request, "name", "doctorName"));
		doctor.setPhoneNum(firstValue(request, "phoneNum", "phone"));
		doctor.setPositionCode(firstValue(request, "positionCode", "position"));
		doctor.setIntroTitle(defaultValue(request.getParameter("introTitle"), ""));
		doctor.setIntroContent(defaultValue(request.getParameter("introContent"), ""));
		doctor.setThumbnailUrl(saveDoctorImage(request, "thumbnailUrl", "currentThumbnailUrl", doctorLicenseNo, "thumb"));
		doctor.setSpecialty(defaultValue(request.getParameter("specialty"), ""));
		doctor.setStatusCode(defaultValue(request.getParameter("statusCode"), "CLS"));
		return doctor;
	}

	private String saveDoctorImage(HttpServletRequest request, String partName, String currentFileParam, int doctorLicenseNo, String imageType)
			throws IOException, ServletException {
		String currentFileName = resolveCurrentFileName(request, currentFileParam, doctorLicenseNo);
		Part imagePart = getUploadedFilePart(request, partName);
		if (imagePart == null || imagePart.getSize() == 0) {
			return defaultValue(currentFileName, DEFAULT_DOCTOR_IMAGE);
		}

		String originalFileName = Paths.get(defaultValue(imagePart.getSubmittedFileName(), "")).getFileName().toString();
		String extension = getImageExtension(originalFileName);
		if (extension == null || !isImageContentType(imagePart.getContentType())) {
			throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
		}

		String realUploadPath = getServletContext().getRealPath(DOCTOR_IMAGE_DIR);
		if (realUploadPath == null) {
			throw new IOException("의료진 이미지 저장 경로를 찾을 수 없습니다.");
		}

		Path uploadDir = Paths.get(realUploadPath);
		Files.createDirectories(uploadDir);

		String savedFileName = "doctor_" + doctorLicenseNo + "_" + imageType + "_"
				+ LocalDateTime.now().format(FILE_TIME_FORMAT) + extension;
		Path targetPath = uploadDir.resolve(savedFileName).normalize();
		if (!targetPath.startsWith(uploadDir)) {
			throw new IOException("의료진 이미지 저장 경로가 올바르지 않습니다.");
		}

		try (InputStream inputStream = imagePart.getInputStream()) {
			Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
		}

		return savedFileName;
	}

	private String resolveCurrentFileName(HttpServletRequest request, String currentFileParam, int doctorLicenseNo) {
		String currentFileName = trimToNull(request.getParameter(currentFileParam));
		if (currentFileName != null) {
			return currentFileName;
		}

		if (!adminDoctorService.checkDoctorLicenseNo(doctorLicenseNo)) {
			return "";
		}

		AdminDoctorFormDTO formDTO = adminDoctorService.searchDoctorDetail(doctorLicenseNo);
		if (formDTO == null || formDTO.getDoctorDTO() == null) {
			return "";
		}

		return defaultValue(formDTO.getDoctorDTO().getThumbnailUrl(), "");
	}

	private Part getUploadedFilePart(HttpServletRequest request, String partName) throws IOException, ServletException {
		for (Part part : request.getParts()) {
			if (!partName.equals(part.getName())) {
				continue;
			}

			String submittedFileName = defaultValue(part.getSubmittedFileName(), "");
			if (part.getSize() > 0 && !submittedFileName.isEmpty()) {
				return part;
			}
		}

		return null;
	}

	private String getImageExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
			return null;
		}

		String extension = fileName.substring(dotIndex).toLowerCase(Locale.ROOT);
		if (".jpg".equals(extension) || ".jpeg".equals(extension) || ".png".equals(extension)
				|| ".gif".equals(extension) || ".webp".equals(extension)) {
			return extension;
		}

		return null;
	}

	private boolean isImageContentType(String contentType) {
		return contentType != null && contentType.toLowerCase(Locale.ROOT).startsWith("image/");
	}

	private List<DoctorEducationDTO> createEducationList(HttpServletRequest request, int doctorLicenseNo) {
		List<DoctorEducationDTO> educationList = new ArrayList<DoctorEducationDTO>();
		String[] years = request.getParameterValues("educationYear[]");
		String[] contents = request.getParameterValues("educationContent[]");
		String[] educationNos = request.getParameterValues("educationNo[]");

		if (years == null) {
			return educationList;
		}

		for (int i = 0; i < years.length; i++) {
			String year = trimToNull(years[i]);
			String content = valueAt(contents, i);
			if (year == null && trimToNull(content) == null) {
				continue;
			}

			DoctorEducationDTO education = new DoctorEducationDTO();
			education.setDoctorLicenseNo(doctorLicenseNo);
			education.setEducationYear(defaultValue(year, ""));
			education.setEducationContent(defaultValue(content, ""));
			education.setEducationNo(parsePositiveInt(valueAt(educationNos, i)));
			educationList.add(education);
		}

		return educationList;
	}

	private List<DoctorCareerDTO> createCareerList(HttpServletRequest request, int doctorLicenseNo) {
		List<DoctorCareerDTO> careerList = new ArrayList<DoctorCareerDTO>();
		String[] years = request.getParameterValues("careerYear[]");
		String[] contents = request.getParameterValues("careerContent[]");
		String[] careerNos = request.getParameterValues("careerNo[]");

		if (years == null) {
			return careerList;
		}

		for (int i = 0; i < years.length; i++) {
			String year = trimToNull(years[i]);
			String content = valueAt(contents, i);
			if (year == null && trimToNull(content) == null) {
				continue;
			}

			DoctorCareerDTO career = new DoctorCareerDTO();
			career.setDoctorLicenseNo(doctorLicenseNo);
			career.setCareerYear(defaultValue(year, ""));
			career.setCareerContent(defaultValue(content, ""));
			career.setCareerNo(parsePositiveInt(valueAt(careerNos, i)));
			careerList.add(career);
		}

		return careerList;
	}

	private List<DoctorScheduleDTO> createScheduleList(HttpServletRequest request, int doctorLicenseNo) {
		List<DoctorScheduleDTO> scheduleList = new ArrayList<DoctorScheduleDTO>();
		String[] statuses = request.getParameterValues("ampm[]");
		String[] startTimes = request.getParameterValues("startTimeValue[]");
		String[] endTimes = request.getParameterValues("endTimeValue[]");

		if (statuses == null) {
			return scheduleList;
		}

		int scheduleCount = Math.min(statuses.length, 7);
		for (int i = 0; i < scheduleCount; i++) {
			DoctorScheduleDTO schedule = new DoctorScheduleDTO();
			schedule.setDoctorLicenseNo(doctorLicenseNo);
			schedule.setDayOfWeek(i + 1);
			schedule.setStatus(defaultValue(statuses[i], ""));
			schedule.setStartTime(defaultValue(valueAt(startTimes, i), ""));
			schedule.setEndTime(defaultValue(valueAt(endTimes, i), ""));
			scheduleList.add(schedule);
		}

		return scheduleList;
	}

	private String firstValue(HttpServletRequest request, String firstName, String secondName) {
		String first = trimToNull(request.getParameter(firstName));
		return first != null ? first : trimToNull(request.getParameter(secondName));
	}

	private String valueAt(String[] values, int index) {
		if (values == null || index >= values.length) {
			return null;
		}

		return values[index];
	}

	private int parsePositiveInt(String value) {
		Integer number = parseInt(value);
		return number == null ? 0 : number;
	}

	private String trimToNull(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		return value.trim();
	}

	private String defaultValue(String value, String defaultValue) {
		return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
	}
}
