package com.hospital.admin.auth.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.admin.auth.AdminLoginService;
import com.hospital.admin.auth.dto.AdminDTO;

public class AdminLoginProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final AdminLoginService adminLoginService = new AdminLoginService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String adminId = request.getParameter("adminId");
		String password = request.getParameter("password");

		if (isBlank(adminId) || isBlank(password)) {
			request.getSession().setAttribute("adminLoginMessage", "아이디 또는 비밀번호를 입력해주세요.");
			redirect(request, response, "/views/admin/auth/adminLogin.jsp");
			return;
		}

		AdminDTO loginAdmin = adminLoginService.login(adminId.trim(), password);

		if (loginAdmin != null) {
			request.getSession().setAttribute("loginAdmin", loginAdmin);
			request.getSession().removeAttribute("adminLoginMessage");
			redirect(request, response, "/admin/dashboard.do");
			return;
		}

		request.getSession().setAttribute("adminLoginMessage", "관리자 계정 정보를 확인해주세요.");
		redirect(request, response, "/views/admin/auth/adminLogin.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		redirect(request, response, "/views/admin/auth/adminLogin.jsp");
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + path));
	}
}
