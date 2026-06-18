<%@page import="java.util.Comparator"%>
<%@page import="com.hospital.common.DepartmentDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String action = request.getParameter("action");

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
		out.print("  <label for='" + dept.getDeptNo() + "'>" + dept.getDeptName() + "</label>");
		out.print("</td>");

		if (i % 3 == 2 || i == totalCnt - 1) {
			out.print("</tr>");
		}
		if (i % 9 == 8 || i == totalCnt - 1) {
			out.print("</table></div>");
		}
	}
}
%>