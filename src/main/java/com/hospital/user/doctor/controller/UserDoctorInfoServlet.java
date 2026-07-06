package com.hospital.user.doctor.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.appointment.UserAppointmentService;

public class UserDoctorInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserAppointmentService userAppointmentService = new UserAppointmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String paramDln = request.getParameter("dln");

		if (paramDln == null || paramDln.isEmpty() || !paramDln.matches("\\d+")) {
			response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
			return;
		}

		int dln = Integer.parseInt(paramDln);

		request.setAttribute("activeMenu", "treatment");
		request.setAttribute("depth2", "의료진 안내");

		request.setAttribute("udDTO", userAppointmentService.searchDoctorDetail(dln));

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/doctor/doctorInfo.jsp");
		dispatcher.forward(request, response);
	}
}
