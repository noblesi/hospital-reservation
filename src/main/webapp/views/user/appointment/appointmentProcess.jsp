<%@page import="com.hospital.user.appointment.dto.UserAppointmentConfirmDTO"%>
<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@page import="com.hospital.user.appointment.dto.UserAppointmentRequestDTO"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 입력 받은 정보로 UserAppointmentRequestDTO를 만들고
	// 만든 uarDTO가 예약 가능한지 확인하고
	// 예약 가능하다면 DB에 insert 한다.
	
	/* 입력받은 정보를 DTO에 담는다. */
	/* String patientNo = (String) session.getAttribute("patientNo"); */
	String patientNo = "P00000001";
	int doctorLicenseNo = Integer.parseInt(request.getParameter("doctorLicenseNo"));
	Date appointmentDate = Date.valueOf(request.getParameter("appointmentDate"));
	String appointmentTime = request.getParameter("appointmentTime");
	String requirement = request.getParameter("requirement");
	String status = "승인 대기";
	
	UserAppointmentRequestDTO uarDTO = new UserAppointmentRequestDTO(patientNo, doctorLicenseNo, appointmentDate, appointmentTime, requirement, status);
	
	UserAppointmentService uas = new UserAppointmentService();
	
	if(uas.checkReservable(uarDTO)) {
		UserAppointmentConfirmDTO uacDTO = uas.reserveAppointment(uarDTO);
		request.setAttribute("uacDTO", uacDTO);
		
		String apptNo = uacDTO.getAppointmentNo();
		
		request.getRequestDispatcher("appointmentSuccess.jsp?apptNo=" + apptNo).forward(request, response);
	} else {
%>
		<script type="text/javascript">
			alert("이미 예약된 시간입니다. 다시 시도해주세요.");
			location.href = "appointment.jsp";
		</script>
<%
		return;
	}
%>

