package com.hospital.user.appointment.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAppointmentReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("activeMenu", "hospital");
		request.setAttribute("depth1", "진료안내");
		request.setAttribute("depth2", "인터넷 진료예약");

		String patientNo = UserAppointmentSessionUtil.getLoginPatientNo(request);

		// 사용자가 로그인 하지 않았으면 로그인 페이지로 이동시킨다.
		if (UserAppointmentSessionUtil.isBlank(patientNo)) {
			response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/appointment/appointment.jsp");
		dispatcher.forward(request, response);
	}
}
