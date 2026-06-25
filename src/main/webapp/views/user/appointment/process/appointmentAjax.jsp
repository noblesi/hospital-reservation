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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String action = request.getParameter("action");

String deptName = "";

/* 진료과 리스트 */
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
	out.print("<div class='sliderPage'><table class='slTab'>");
		}
		if (i % 3 == 0) {
	out.print("<tr class='slRow'>");
		}

		DepartmentDTO dept = deptList.get(i);

		out.print("<td class='slCol'>");
		out.print("  <input class='deptRadio' style='display: none;' type='radio' name='dept' value='"
		+ dept.getDeptNo() + "' id='" + dept.getDeptNo() + "'>");
		out.print("  <label class='detpLabel' for='" + dept.getDeptNo() + "'>" + dept.getDeptName() + "</label>");
		out.print("</td>");

		if (i % 3 == 2 || i == totalCnt - 1) {
	out.print("</tr>");
		}
		if (i % 9 == 8 || i == totalCnt - 1) {
	out.print("</table></div>");
		}
	}
}

/* 사용자 키워드로 의료진 검색 */
if ("searchDoctor".equals(action)) {
	String keyword = request.getParameter("keyword");

	if (keyword == null) {
		keyword = "";
	}

	UserAppointmentService uas = new UserAppointmentService();
	List<DoctorDTO> doctorList = uas.searchDoctorListByKeyword(keyword);

	int doctorCnt = doctorList.size();

	if (doctorCnt == 0) {
		out.println("<p class='noResult'>입력하신 의료진, 세부전공과 일치하는 의료진이 없습니다.</p>");
		return;
	}

	out.println("<ul class='doctorUl'>");

	DoctorDTO dDTO = null;

	for (int i = 0; i < doctorCnt; i++) {
		dDTO = doctorList.get(i);

		if (i % 2 == 0) {
	out.println("<div class='col'>");
		}
%>
<li class="doctorLi"><img class="doctorThumnail" src="<%=dDTO.getThumbnailUrl()%>">
	<div class="doctorInfoDiv">
		<h4 class="doctorName"><%=dDTO.getName()%>
			<a href="#void"> <i class="bi bi-search blueSearchIcon"></i>
			</a>
		</h4>
		<p class="detail">
			세부전공: <span class="specialty"><%=dDTO.getSpecialty()%></span>
		</p>
	</div>
	<button class="selectDoctorBtn" value="<%=dDTO.getDoctorLicenseNo()%>">
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


<!-- 진료과 선택 시 의료진 목록 -->
<%
if ("doctorList".equals(action)) {
	String deptNo = request.getParameter("deptNo");
	deptName = request.getParameter("deptName");

	if (deptNo == null || "".equals(deptNo)) {
		response.sendRedirect("errorpage.jsp");
		return;
	}

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
%>
<li class="doctorLi"><img class="doctorThumnail" src="<%=dDTO.getThumbnailUrl()%>">
	<div class="doctorInfoDiv">
		<h4 class="doctorName"><%=dDTO.getName()%>
			<a href="#void"> <i class="bi bi-search blueSearchIcon"></i>
			</a>
		</h4>
		<p class="detail">
			<strong class="deptName"><%=deptName%></strong><br> 세부전공: <span class="specialty"><%=dDTO.getSpecialty()%></span>
		</p>
	</div>
	<button class="selectDoctorBtn" value="<%=dDTO.getDoctorLicenseNo()%>">
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
	LocalDate nowLd = LocalDate.now();
	boolean leepYearFlag = ld.isLeapYear();

	String year = request.getParameter("year");
	String month = request.getParameter("month");
	String dln = request.getParameter("dln");
	
	if (dln == null || !dln.matches("^\\d+$")) {
		response.sendRedirect("errorpage");
		return;
	}

	if (year != null && !year.isEmpty() && year.matches("[0-9]")) {
		ld = ld.withYear(Integer.parseInt(year));
	} else {
		year = String.valueOf(ld.getYear());
	}

	if (month != null && !month.isEmpty() && month.matches("[0-9]")) {
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
%>
<div class="moveMonthBar">
	<button class="prevMonthBtn">
		<i class="bi bi-arrow-left-circle"></i>
	</button>
	<h4 class="nowMonthTitle">
		<span class="year"><%=year%></span>년 <span class="month"><%=month%></span>월
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

				if (allDay.contains(ld.getDayOfWeek().getValue()) && ld.isAfter(nowLd)) {
			%>
			<td>
				<span class='available allDay' data-date="<%=ld.toString()%>"><%=i%></span>
			</td>
			<%
			} else if (am.contains(ld.getDayOfWeek().getValue()) && ld.isAfter(nowLd)) {
			%>
			<td>
				<span class='available am' data-date="<%=ld.toString()%>"><%=i%></span>
			</td>
			<%
			} else if (pm.contains(ld.getDayOfWeek().getValue()) && ld.isAfter(nowLd)) {
			%>
			<td>
				<span class='available pm' data-date="<%=ld.toString()%>"><%=i%></span>
			</td>
			<%
			} else {
			%>
			<td>
				<span><%=i%></span>
			</td>
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
	<span class='am ex'></span> <span> 오전 </span> <span class='pm ex'></span> <span> 오후 </span> <span class='allDay ex'></span> <span> 종일 </span>
</div>
<%
}
%>


<!-- 시간 테이블  -->
<%
if ("timeTable".equals(action)) {
	String dateParam = request.getParameter("date");
	String dlnParam = request.getParameter("dln");

	if (dateParam == null || !dateParam.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
		response.sendRedirect("errorpage");
		return;
	}

	if (dlnParam == null || !dlnParam.matches("^\\d+$")) {
		response.sendRedirect("errorpage");
		return;
	}

	Date appointmentDate = Date.valueOf(dateParam);
	int dln = Integer.parseInt(dlnParam);

	UserAppointmentService uas = new UserAppointmentService();

	List<String> availableTimes = uas.searchAvailableTime(dln, appointmentDate);

	pageContext.setAttribute("availableTimes", availableTimes);
%>

<ul class="timeTableUl">
	<c:if test="${ not empty availableTimes }">
		<c:forEach var="time" items="${ availableTimes }">
			<li class="timeTableLi"><c:out value="${ time }" /></li>
		</c:forEach>
	</c:if>
	<c:if test="${ empty availableTimes }">
		<p style="text-align: center; font-size: 18px;">
			해당 일자의 예약이<br> 모두 완료되었습니다.<br>다른 날짜를 선택해주세요.
		</p>
	</c:if>
</ul>
<%
}
%>