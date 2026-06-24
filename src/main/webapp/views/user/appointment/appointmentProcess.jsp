<%@page import="java.net.URLEncoder"%>
<%@page import="java.sql.Date"%>
<%@page import="com.hospital.common.MemberDTO"%>
<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@page import="com.hospital.user.appointment.dto.UserAppointmentConfirmDTO"%>
<%@page import="com.hospital.user.appointment.dto.UserAppointmentRequestDTO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

if (!"POST".equalsIgnoreCase(request.getMethod())) {
	response.sendRedirect("appointment.jsp");
	return;
}

MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
if (loginUser == null || loginUser.getPatientNo() == null || loginUser.getPatientNo().isBlank()) {
%>
<script>
alert("로그인이 필요한 서비스입니다.");
location.href = "../../member/login.jsp";
</script>
<%
	return;
}

String doctorLicenseNoParam = request.getParameter("doctorLicenseNo");
String appointmentDateParam = request.getParameter("appointmentDate");
String appointmentTime = request.getParameter("appointmentTime");
String requirement = request.getParameter("requirement");

try {
	int doctorLicenseNo = Integer.parseInt(doctorLicenseNoParam);
	Date appointmentDate = Date.valueOf(appointmentDateParam);

	UserAppointmentRequestDTO requestDTO = new UserAppointmentRequestDTO();
	requestDTO.setPatientNo(loginUser.getPatientNo());
	requestDTO.setDoctorLicenseNo(doctorLicenseNo);
	requestDTO.setAppointmentDate(appointmentDate);
	requestDTO.setAppointmentTime(appointmentTime);
	requestDTO.setRequirement(requirement);
	requestDTO.setStatus("승인 대기");

	UserAppointmentService service = new UserAppointmentService();
	UserAppointmentConfirmDTO confirmDTO = service.reserveAppointment(requestDTO);

	if (confirmDTO == null) {
%>
<script>
alert("이미 예약된 시간이거나 예약을 저장하지 못했습니다.");
history.back();
</script>
<%
		return;
	}

	String appointmentNo = URLEncoder.encode(confirmDTO.getAppointmentNo(), "UTF-8");
	response.sendRedirect("appointmentSuccess.jsp?appointmentNo=" + appointmentNo);
	return;
} catch (Exception e) {
	e.printStackTrace();
%>
<script>
alert("예약 요청 값이 올바르지 않습니다.");
history.back();
</script>
<%
}
%>
