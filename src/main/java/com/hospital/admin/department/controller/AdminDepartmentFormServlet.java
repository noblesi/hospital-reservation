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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		DepartmentDTO department = new DepartmentDTO();
		department.setDeptNo(trimToNull(request.getParameter("deptNo")));
		department.setDeptName(trimToNull(request.getParameter("deptName")));
		department.setDescription(defaultValue(request.getParameter("description"), ""));
		department.setDeptLoc(defaultValue(request.getParameter("deptLoc"), ""));
		department.setIsActiveYn(defaultValue(request.getParameter("isActiveYn"), "Y"));

		boolean success = department.getDeptNo() == null
				? adminDepartmentService.registerDepartment(department)
				: adminDepartmentService.modifyDepartment(department);

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write("<script>");
		if (success) {
			response.getWriter().write("if (window.opener) { window.opener.location.reload(); }");
			response.getWriter().write("window.close();");
		} else {
			response.getWriter().write("alert('진료과 저장에 실패했습니다.'); history.back();");
		}
		response.getWriter().write("</script>");
	}// doPost

	private String trimToNull(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}// end if

		return value.trim();
	}// trimToNull

	private String defaultValue(String value, String defaultValue) {
		return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
	}// defaultValue
}// class
