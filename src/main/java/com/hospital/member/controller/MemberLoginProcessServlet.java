package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.member.LoginService;

/**
 * 회원 로그인을 처리하는 Servlet Controller.
 *
 * 기존 loginProcess.jsp에서 처리하던 요청 파라미터 수집, 로그인 검증,
 * 세션 저장, 화면 이동 처리를 Servlet으로 분리한다.
 */
public class MemberLoginProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final LoginService loginService = new LoginService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		if(loginId == null || password == null || "".equals(loginId.trim()) || "".equals(password.trim())) {
			request.getSession().setAttribute("loginMessage", "아이디 또는 비밀번호를 입력해주세요.");
			response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
			return;
		}//end if

		MemberDTO loginMember = loginService.login(loginId.trim(), password);

		if(loginMember != null) {
			request.getSession().setAttribute("loginUser", loginMember);
			response.sendRedirect(request.getContextPath() + "/main.do");
			return;
		}//end if

		request.getSession().setAttribute("loginMessage", "아이디 또는 비밀번호를 확인해주세요.");
		response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
	}//doPost

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
	}//doGet
}//class
