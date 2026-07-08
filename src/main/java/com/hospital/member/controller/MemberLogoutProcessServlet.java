package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLogoutProcessServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean adminLogout = isAdminLogoutRequest(request);

		HttpSession session = request.getSession(false);

		if (session != null) {
			// 로그인 사용자 정보가 담긴 세션 전체를 만료한다.
			session.invalidate();
		}

		if (adminLogout) {
			request.getSession(true).setAttribute("adminLoginMessage", "관리자 로그아웃이 완료되었습니다.");
			response.sendRedirect(request.getContextPath() + "/views/admin/auth/adminLogin.jsp");
			return;
		}

		response.sendRedirect(request.getContextPath() + "/main.do");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean isAdminLogoutRequest(HttpServletRequest request) {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		return "/admin/logout.do".equals(path);
	}
}
