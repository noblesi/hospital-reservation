package com.hospital.admin.doctor.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminDoctorListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("adminMenu", "doctor");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/doctor/adminDoctorListView.jsp");
		dispatcher.forward(request, response);
	}//doGet
}//class
