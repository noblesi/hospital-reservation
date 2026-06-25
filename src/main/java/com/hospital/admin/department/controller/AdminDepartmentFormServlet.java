package com.hospital.admin.department.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.department.AdminDepartmentService;
import com.hospital.common.dto.DepartmentDTO;

public class AdminDepartmentFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final AdminDepartmentService adminDepartmentService = new AdminDepartmentService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String modify = request.getParameter("modify");
		String deptNo = request.getParameter("deptNo");
		boolean modifyFlag = "Y".equalsIgnoreCase(modify);
		List<DepartmentDTO> departmentList = adminDepartmentService.searchDepartmentList();

		request.setAttribute("modifyFlag", modifyFlag);
		request.setAttribute("deptNo", deptNo);
		request.setAttribute("departmentList", departmentList);

		if (modifyFlag && deptNo != null && !deptNo.isBlank()) {
			request.setAttribute("department", adminDepartmentService.searchDepartmentDetail(deptNo));
		}// end if

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/admin/department/adminDepartmentAddModal.jsp");
		dispatcher.forward(request, response);
	}// doGet
}// class
