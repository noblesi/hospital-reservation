<%@page import="com.hospital.user.appointment.dto.UserAppointmentConfirmDTO"%>
<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@page import="com.hospital.user.appointment.dto.UserAppointmentRequestDTO"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/* 입력받은 정보를 DTO에 담는다. */
/* String patientNo = (String) session.getAttribute("patientNo"); */
String patientNo = "P00000001";

String parmDln = request.getParameter("doctorLicenseNo");
String dateParam = request.getParameter("appointmentDate");
String appointmentTime = request.getParameter("appointmentTime");
String requirement = request.getParameter("requirement");

/* 입력 값 예외 처리 */
if (parmDln == null || dateParam == null || appointmentTime == null || requirement == null) {
	response.sendRedirect("errorpage");
	return;
}

if (!parmDln.matches("^\\d+$")) {
	response.sendRedirect("errorpage");
	return;
}

if (!dateParam.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
	response.sendRedirect("errorpage");
	return;
}

int doctorLicenseNo = Integer.parseInt(parmDln);
Date appointmentDate = Date.valueOf(dateParam);
String status = "예약대기";


UserAppointmentRequestDTO uarDTO = new UserAppointmentRequestDTO(patientNo, doctorLicenseNo, appointmentDate,
		appointmentTime, requirement, status);

/* 예약변경 하기 위한 예약번호 */
String modifyApptNo = request.getParameter("appointmentNo");

/* 입력받은 값으로 예약을 진행한다. */
UserAppointmentService uas = new UserAppointmentService();

if (modifyApptNo == null || modifyApptNo == "") {
	
	if (uas.checkReservable(uarDTO)) {
		UserAppointmentConfirmDTO uacDTO = uas.reserveAppointment(uarDTO);
		String apptNo = uacDTO.getAppointmentNo();
	
		response.sendRedirect("http://localhost/hospital-reservation/views/user/appointment/appointmentSuccess.jsp?apptNo=" + apptNo);
		return;
		
	} else {
%>
		<script type="text/javascript">
			alert("이미 예약된 시간입니다. 다시 시도해주세요.");
			location.href = "../appointment.jsp";
		</script>
<%
	}
	
}

/* 만약 수정하기 위한 예약 번호가 있다면 새로운 예약을 만드는게 아니라 예약을 수정하는 method를 불러온다. */
if (modifyApptNo != null) {
	
	if (uas.checkReservable(uarDTO)) {
		UserAppointmentConfirmDTO uacDTO = uas.reserveAppointment(modifyApptNo, patientNo, uarDTO);
		
		if (uacDTO == null) {
			response.sendRedirect("errorpage");
			return;
		}
		
		String apptNo = uacDTO.getAppointmentNo();
	
		response.sendRedirect("appointmentSuccess.jsp?apptNo=" + apptNo);
		
		return;
		
	} else {
%>
		<script type="text/javascript">
			alert("이미 예약된 시간입니다. 다시 시도해주세요.");
			location.href = "appointment.jsp";
		</script>
<%
	}
	
}
%>

