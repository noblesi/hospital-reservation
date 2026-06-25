package com.hospital.user.appointment.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.appointment.UserAppointmentService;
import com.hospital.user.appointment.dto.UserAppointmentConfirmDTO;

public class UserAppointmentSuccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserAppointmentService userAppointmentService = new UserAppointmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patientNo = UserAppointmentSessionUtil.getLoginPatientNo(request);
		if (UserAppointmentSessionUtil.isBlank(patientNo)) {
			response.sendRedirect(request.getContextPath() + "/member/login.do");
			return;
		}

		String appointmentNo = request.getParameter("apptNo");
		UserAppointmentConfirmDTO appointment = userAppointmentService.searchAppointmentConfirm(appointmentNo);
		if (appointment == null || !patientNo.equals(appointment.getPatientNo())) {
			request.getSession().setAttribute("errorMessage", "예약 정보를 확인할 수 없습니다.");
			response.sendRedirect(request.getContextPath() + "/appointment/reserve.do");
			return;
		}

		request.setAttribute("activeMenu", "hospital");
		request.setAttribute("depth1", "진료안내");
		request.setAttribute("depth2", "인터넷 진료예약");
		request.setAttribute("uacDTO", appointment);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/appointment/appointmentSuccess.jsp");
		dispatcher.forward(request, response);
	}
}
