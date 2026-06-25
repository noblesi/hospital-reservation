package com.hospital.user.appointment.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.dto.DepartmentDTO;
import com.hospital.common.dto.DoctorDTO;
import com.hospital.common.dto.DoctorScheduleDTO;
import com.hospital.user.appointment.UserAppointmentService;

public class UserAppointmentAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserAppointmentService userAppointmentService = new UserAppointmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		if ("sort".equals(action)) {
			renderDepartmentList(request, response);
			return;
		}
		if ("searchDoctor".equals(action)) {
			renderDoctorSearch(request, response);
			return;
		}
		if ("doctorList".equals(action)) {
			renderDoctorList(request, response);
			return;
		}
		if ("schedule".equals(action)) {
			renderSchedule(request, response);
			return;
		}
		if ("timeTable".equals(action)) {
			renderTimeTable(request, response);
			return;
		}

		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	}

	private void renderDepartmentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<DepartmentDTO> departmentList = new ArrayList<DepartmentDTO>(userAppointmentService.searchDepartmentList());
		if ("ascending".equals(request.getParameter("sort"))) {
			departmentList.sort(Comparator.comparing(DepartmentDTO::getDeptName, Comparator.nullsLast(String::compareTo)));
		}

		StringBuilder html = new StringBuilder();
		for (int i = 0; i < departmentList.size(); i++) {
			DepartmentDTO department = departmentList.get(i);
			if (i % 9 == 0) {
				html.append("<div class='sliderPage'><table class='slTab'>");
			}
			if (i % 3 == 0) {
				html.append("<tr class='slRow'>");
			}

			String deptNo = escapeHtml(department.getDeptNo());
			html.append("<td class='slCol'>")
					.append("<input class='deptRadio' style='display: none;' type='radio' name='dept' value='")
					.append(deptNo)
					.append("' id='")
					.append(deptNo)
					.append("'>")
					.append("<label for='")
					.append(deptNo)
					.append("'>")
					.append(escapeHtml(department.getDeptName()))
					.append("</label></td>");

			if (i % 3 == 2 || i == departmentList.size() - 1) {
				html.append("</tr>");
			}
			if (i % 9 == 8 || i == departmentList.size() - 1) {
				html.append("</table></div>");
			}
		}

		response.getWriter().print(html);
	}

	private void renderDoctorSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("keyword");
		List<DoctorDTO> doctorList = userAppointmentService.searchDoctorListByKeyword(keyword);
		renderDoctorCards(response, doctorList, null);
	}

	private void renderDoctorList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String deptNo = request.getParameter("deptNo");
		if (UserAppointmentSessionUtil.isBlank(deptNo)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		List<DoctorDTO> doctorList = userAppointmentService.searchDoctorList(deptNo);
		renderDoctorCards(response, doctorList, request.getParameter("deptName"));
	}

	private void renderDoctorCards(HttpServletResponse response, List<DoctorDTO> doctorList, String deptName)
			throws IOException {
		if (doctorList.isEmpty()) {
			response.getWriter().print("<p class='noResult'>조건에 일치하는 의료진이 없습니다.</p>");
			return;
		}

		StringBuilder html = new StringBuilder("<ul class='doctorUl'>");
		for (int i = 0; i < doctorList.size(); i++) {
			DoctorDTO doctor = doctorList.get(i);
			if (i % 2 == 0) {
				html.append("<div class='col'>");
			}

			html.append("<li class='doctorLi'><img class='doctorThumnail' src='")
					.append(escapeHtml(doctor.getThumbnailUrl()))
					.append("'><div class='doctorInfoDiv'><h4 class='doctorName'>")
					.append(escapeHtml(doctor.getName()))
					.append("<a href='#void'><i class='bi bi-search blueSearchIcon'></i></a></h4><p class='detail'>");
			if (!UserAppointmentSessionUtil.isBlank(deptName)) {
				html.append("<strong class='deptName'>").append(escapeHtml(deptName)).append("</strong><br>");
			}
			html.append("세부전공: <span class='specialty'>")
					.append(escapeHtml(doctor.getSpecialty()))
					.append("</span></p></div><button class='selectDoctorBtn' value='")
					.append(doctor.getDoctorLicenseNo())
					.append("'><i class='bi bi-check-circle checkIcon'></i> 선택</button></li>");

			if (i % 2 == 1 || i == doctorList.size() - 1) {
				html.append("</div>");
			}
		}
		html.append("</ul>");
		response.getWriter().print(html);
	}

	private void renderSchedule(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer doctorLicenseNo = parseInt(request.getParameter("dln"));
		if (doctorLicenseNo == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		LocalDate calendarDate = LocalDate.now();
		Integer year = parseInt(request.getParameter("year"));
		Integer month = parseInt(request.getParameter("month"));
		if (year != null) {
			calendarDate = calendarDate.withYear(year);
		}
		if (month != null && month >= 1 && month <= 12) {
			calendarDate = calendarDate.withMonth(month);
		}

		List<DoctorScheduleDTO> scheduleList = userAppointmentService.searchDoctorSchedule(doctorLicenseNo);
		List<Integer> allDay = new ArrayList<Integer>();
		List<Integer> am = new ArrayList<Integer>();
		List<Integer> pm = new ArrayList<Integer>();
		for (DoctorScheduleDTO schedule : scheduleList) {
			if ("전일".equals(schedule.getStatus())) {
				allDay.add(schedule.getDayOfWeek());
			} else if ("오전".equals(schedule.getStatus())) {
				am.add(schedule.getDayOfWeek());
			} else if ("오후".equals(schedule.getStatus())) {
				pm.add(schedule.getDayOfWeek());
			}
		}

		StringBuilder html = new StringBuilder();
		html.append("<div class='moveMonthBar'><button class='prevMonthBtn'><i class='bi bi-arrow-left-circle'></i></button>")
				.append("<h4 class='nowMonthTitle'><span class='year'>")
				.append(calendarDate.getYear())
				.append("</span>년 <span class='month'>")
				.append(calendarDate.getMonthValue())
				.append("</span>월</h4>")
				.append("<button class='nextMonthBtn'><i class='bi bi-arrow-right-circle'></i></button></div>");
		html.append("<table class='calTab'><thead><tr class='weekTr'><th style='color: #ee1c24'>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th style='color: #02348b'>토</th></tr></thead><tbody><tr>");

		LocalDate firstDay = calendarDate.withDayOfMonth(1);
		int blankCount = firstDay.getDayOfWeek().getValue() % 7;
		for (int i = 0; i < blankCount; i++) {
			html.append("<td><span></span></td>");
		}

		for (int day = 1; day <= calendarDate.lengthOfMonth(); day++) {
			LocalDate currentDay = calendarDate.withDayOfMonth(day);
			int dayOfWeek = currentDay.getDayOfWeek().getValue();
			String className = "";
			if (allDay.contains(dayOfWeek)) {
				className = "available allDay";
			} else if (am.contains(dayOfWeek)) {
				className = "available am";
			} else if (pm.contains(dayOfWeek)) {
				className = "available pm";
			}

			html.append("<td><span");
			if (!className.isEmpty()) {
				html.append(" class='").append(className).append("' data-date='").append(currentDay).append("'");
			}
			html.append(">").append(day).append("</span></td>");

			if (currentDay.getDayOfWeek() == DayOfWeek.SATURDAY && day != calendarDate.lengthOfMonth()) {
				html.append("</tr><tr>");
			}
		}

		html.append("</tr></tbody></table><div class='infoCal'><span class='am ex'></span> <span> 오전 </span> <span class='pm ex'></span> <span> 오후 </span> <span class='allDay ex'></span> <span> 종일 </span></div>");
		response.getWriter().print(html);
	}

	private void renderTimeTable(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer doctorLicenseNo = parseInt(request.getParameter("dln"));
		String date = request.getParameter("date");
		if (doctorLicenseNo == null || UserAppointmentSessionUtil.isBlank(date)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		List<String> availableTimes = userAppointmentService.searchAvailableTime(doctorLicenseNo, Date.valueOf(date));
		StringBuilder html = new StringBuilder("<ul class='timeTableUl'>");
		if (availableTimes.isEmpty()) {
			html.append("<p style='text-align: center; font-size: 18px;'>해당 일자의 예약이<br> 모두 완료되었습니다.<br>다른 날짜를 선택해주세요.</p>");
		} else {
			for (String availableTime : availableTimes) {
				html.append("<li class='timeTableLi'>").append(escapeHtml(availableTime)).append("</li>");
			}
		}
		html.append("</ul>");
		response.getWriter().print(html);
	}

	private Integer parseInt(String value) {
		if (UserAppointmentSessionUtil.isBlank(value) || !value.matches("\\d+")) {
			return null;
		}

		return Integer.valueOf(value);
	}

	private String escapeHtml(String value) {
		if (value == null) {
			return "";
		}

		return value.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&#39;");
	}
}
