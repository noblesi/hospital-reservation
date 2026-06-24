<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
/* 환자 번호, 예약 번호를 받아서 해당하는 예약을 취소. */
// String patientNo = session.getAttribute(name);
String patientNo = "P00000001";
String appointmentNo = request.getParameter("appointmentNo");

UserAppointmentService uas = new UserAppointmentService();

boolean cancelFlag = uas.cancelAppointment(appointmentNo, patientNo);

if (cancelFlag) {
%>
	<script type="text/javascript">
		alert("예약이 취소되었습니다.");
		location.href = "appointmentList.jsp";
	</script>
<%
} else {
%>
	<script type="text/javascript">
		alert("예약 취소가 실패했습니다. 잠시 후 다시 시도해 주세요.");
		location.href = "appointmentList.jsp";
	</script>
<%
}
%>