package com.hospital.user.department.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.user.department.UserDepartmentService;

public class UserDepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserDepartmentService userDepartmentService = new UserDepartmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			request.setAttribute("activeMenu", "treatment");
			request.setAttribute("departmentList", userDepartmentService.searchDepartmentList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user/department/departmentList.jsp");
		dispatcher.forward(request, response);
	}
}
