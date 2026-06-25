<%@page import="java.sql.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.hospital.common.dto.DoctorScheduleDTO"%>
<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="com.hospital.common.dto.DoctorDTO"%>
<%@page import="java.time.DayOfWeek"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.List"%>
<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String action = request.getParameter("action");

String deptName = "";

/* 진료과 리스트 정렬 */
if ("sort".equals(action)) {
	String sortType = request.getParameter("sort");

	if (sortType == null) {
		sortType = "default"; // 기본값
	}

	UserAppointmentService uas = new UserAppointmentService();
	List<DepartmentDTO> deptList = uas.searchDepartmentList();

	if (sortType.equals("ascending")) {
		deptList.sort(Comparator.comparing(DepartmentDTO::getDeptName));
	}

	int totalCnt = deptList.size();
	DepartmentDTO deptDTO = null;

	for (int i = 0; i < totalCnt; i++) {
		if (i % 9 == 0) {
%>
<div class="sliderPage"><table class="slTab">
<%
		}
		if (i % 3 == 0) {
%>
<tr class="slRow">
<%
		}

		DepartmentDTO dept = deptList.get(i);
		pageContext.setAttribute("dept", dept);
%>

<td class="slCol">
	<input class="deptRadio" style="display: none;" type="radio" name="dept"
		value="<c:out value='${dept.deptNo}' />" id="<c:out value='${dept.deptNo}' />">
	<label for="<c:out value='${dept.deptNo}' />"><c:out value="${dept.deptName}" /></label>
</td>
<%

		if (i % 3 == 2 || i == totalCnt - 1) {
%>
</tr>
<%
		}
		if (i % 9 == 8 || i == totalCnt - 1) {
%>
</table></div>
<%
		}
	}
}

/* 의료진 목록 */
if ("doctorList".equals(action)) {
	String deptNo = request.getParameter("deptNo");
	deptName = request.getParameter("deptName");

	UserAppointmentService uas = new UserAppointmentService();
	List<DoctorDTO> doctorList = uas.searchDoctorList(deptNo);

	int doctorCnt = doctorList.size();

	if (doctorCnt == 0) {
		out.println("<p class='noResult'>선택하신 진료과에 진료 가능한 의료진이 없습니다.</p>");
		return;
	}

	out.println("<ul class='doctorUl'>");

	DoctorDTO dDTO = null;

	for (int i = 0; i < doctorCnt; i++) {
		dDTO = doctorList.get(i);

		if (i % 2 == 0) {
	out.println("<div class='col'>");
		}
		pageContext.setAttribute("doctor", dDTO);
		pageContext.setAttribute("deptName", deptName);
%>
<li class="doctorLi"><img class="doctorThumnail"
	src="<c:out value='${doctor.thumbnailUrl}' />">
	<div class="doctorInfoDiv">
		<h4 class="doctorName"><c:out value="${doctor.name}" />
			<a href="#void"> <img class="searchBlueIcon"
				src="http://localhost/hospital-reservation/resources/images/appointment/search_blue.png"></a>
		</h4>
		<p class="detail">
			<strong class="deptName"><c:out value="${deptName}" /></strong><br> 세부전공:
			<span class="specialty"><c:out value="${doctor.specialty}" /></span>
		</p>
	</div>
	<button class="selectDoctorBtn"
		value="<c:out value='${doctor.doctorLicenseNo}' />">
		<i class="bi bi-check-circle checkIcon"></i> 선택
	</button></li>
<%
if (i % 2 == 1 || i == (doctorCnt - 1)) {
	out.print("</div>");
}
}

out.print("</ul>");
}
%>


<!-- 의사의 진료 가능 날짜 -->

<%
if ("schedule".equals(action)) {
	LocalDate ld = LocalDate.now();
	boolean leepYearFlag = ld.isLeapYear();

	String year = request.getParameter("year");
	String month = request.getParameter("month");
	String dln = request.getParameter("dln");
	
	if (year != null && !year.isEmpty()) {
		ld = ld.withYear(Integer.parseInt(year));
	} else {
		year = String.valueOf(ld.getYear());
	}

	if (month != null && !month.isEmpty()) {
		ld = ld.withMonth(Integer.parseInt(month));
	} else {
		month = String.valueOf(ld.getMonthValue());
	}

	UserAppointmentService uas = new UserAppointmentService();
	List<DoctorScheduleDTO> dsList = uas.searchDoctorSchedule(Integer.parseInt(dln));

	List<Integer> allDay = new ArrayList<>();
	List<Integer> am = new ArrayList<>();
	List<Integer> pm = new ArrayList<>();

	DoctorScheduleDTO dsDTO = null;
	
	for (int i = 0; i < dsList.size(); i++) {
		dsDTO = dsList.get(i);

		if ("전일".equals(dsDTO.getStatus())) {
			allDay.add(dsDTO.getDayOfWeek());
		}

		if ("오전".equals(dsDTO.getStatus())) {
			am.add(dsDTO.getDayOfWeek());
		}

		if ("오후".equals(dsDTO.getStatus())) {
			pm.add(dsDTO.getDayOfWeek());
		}
	}
	pageContext.setAttribute("year", year);
	pageContext.setAttribute("month", month);
%>
<div class="moveMonthBar">
	<button class="prevMonthBtn">
		<i class="bi bi-arrow-left-circle"></i>
	</button>
	<h4 class="nowMonthTitle">
		<span class="year"><c:out value="${year}" /></span>년 <span class="month"><c:out value="${month}" /></span>월
	</h4>
	<button class="nextMonthBtn">
		<i class="bi bi-arrow-right-circle"></i>
	</button>
</div>
<table class="calTab">
	<thead>
		<tr class="weekTr">
			<th style="color: #ee1c24">일</th>
			<th>월</th>
			<th>화</th>
			<th>수</th>
			<th>목</th>
			<th>금</th>
			<th style="color: #02348b">토</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<%
			for (int i = 1; i < ld.getMonth().length(leepYearFlag) + 1; i++) {
				ld = ld.withDayOfMonth(i);

				/* 시작일이 일요일이 아니면 앞에 공백 추가 */
				if (i == 1) {
					if (ld.getDayOfWeek().getValue() != 7) {
				for (int j = 0; j < ld.getDayOfWeek().getValue(); j++) {
					out.print("<td><span></span></td>");
				}
					}
				}

				if (allDay.contains(ld.getDayOfWeek().getValue())) {
					pageContext.setAttribute("dateValue", ld.toString());
					pageContext.setAttribute("dayNo", i);
				%>
					<td><span class='available allDay' data-date="<c:out value='${dateValue}' />"><c:out value="${dayNo}" /></span></td>
				<%
					// out.print("<td><span class='available allDay'>" + i + "</span></td>");
				} else if (am.contains(ld.getDayOfWeek().getValue())) {
					pageContext.setAttribute("dateValue", ld.toString());
					pageContext.setAttribute("dayNo", i);
				%>
					<td><span class='available am' data-date="<c:out value='${dateValue}' />"><c:out value="${dayNo}" /></span></td>
				<%
				} else if (pm.contains(ld.getDayOfWeek().getValue())) {
					pageContext.setAttribute("dateValue", ld.toString());
					pageContext.setAttribute("dayNo", i);
				%>
					<td><span class='available pm' data-date="<c:out value='${dateValue}' />"><c:out value="${dayNo}" /></span></td>
				<%
				} else {
					pageContext.setAttribute("dayNo", i);
				%>
					<td><span><c:out value="${dayNo}" /></span></td>
				<%
				}

				/* 토요일이면 <tr>을 새로 열어서 줄 바꿈. */
				if (ld.getDayOfWeek() == DayOfWeek.SATURDAY) {
					out.print("</tr>");
					out.print("<tr>");
				}
			}
			%>
		
	</tbody>
</table>
<div class="infoCal">
	<span class='am ex'></span>
	<span> 오전 </span>
	<span class='pm ex'></span>
	<span> 오후 </span>
	<span class='allDay ex'></span>
	<span> 종일 </span>
</div>
<%
}
%>


<!-- 시간 테이블  -->
<%
if ("timeTable".equals(action)) {
	Date appointmentDate = Date.valueOf(request.getParameter("date"));
	int dln = Integer.parseInt(request.getParameter("dln"));
	
	UserAppointmentService uas = new UserAppointmentService();
	
	List<String> availableTimes = uas.searchAvailableTime(dln, appointmentDate);
%>
	<ul class="timeTableUl">
<%
	for(int i = 0; i < availableTimes.size(); i++) {
		pageContext.setAttribute("availableTime", availableTimes.get(i));
%>
		<li class="timeTableLi"><c:out value="${availableTime}" /></li>
<%
	}
%>
	</ul>
<%
}
%>
