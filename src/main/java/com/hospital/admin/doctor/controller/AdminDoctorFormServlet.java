package com.hospital.admin.doctor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.doctor.AdminDoctorService;
import com.hospital.admin.doctor.dto.AdminDoctorFormDTO;
import com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO;
import com.hospital.common.dto.DoctorCareerDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorEducationDTO;
import com.hospital.common.dto.DoctorScheduleDTO;

public class AdminDoctorFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		DoctorDTO doctor = createDoctorDTO(request);
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

	private DoctorDTO createDoctorDTO(HttpServletRequest request) {
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
		doctor.setThumbnailUrl(defaultValue(request.getParameter("thumbnailUrl"), ""));
		doctor.setDetailImageUrl(defaultValue(request.getParameter("detailImageUrl"), ""));
		doctor.setSpecialty(defaultValue(request.getParameter("specialty"), ""));
		doctor.setStatusCode(defaultValue(request.getParameter("statusCode"), "CLS"));
		return doctor;
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
