package com.hospital.user.appointment.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.appointment.UserAppointmentService;

public class UserAppointmentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserAppointmentService userAppointmentService = new UserAppointmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String patientNo = UserAppointmentSessionUtil.getLoginPatientNo(request);
		if (UserAppointmentSessionUtil.isBlank(patientNo)) {
			response.sendRedirect(request.getContextPath() + "/member/login.do");
			return;
		}

		request.setAttribute("activeMenu", "hospital");
		request.setAttribute("depth1", "진료안내");
		request.setAttribute("depth2", "예약확인");
		request.setAttribute("uasDTOList", userAppointmentService.searchAppointmentDetail(patientNo));

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/appointment/appointmentList.jsp");
		dispatcher.forward(request, response);
	}
}
