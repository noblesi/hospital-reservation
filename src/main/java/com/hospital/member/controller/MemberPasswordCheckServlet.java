package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.member.UpdateUserInfoService;

public class MemberPasswordCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UpdateUserInfoService updateUserInfoService = new UpdateUserInfoService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/member/login.do");
			return;
		}// end if

		String password = request.getParameter("password");

		if (password != null && updateUserInfoService.checkPassword(loginUser.getLoginId(), password)) {
			request.getSession().setAttribute("userInfoVerified", Boolean.TRUE);
			response.sendRedirect(request.getContextPath() + "/member/mypage/info.do");
			return;
		}// end if

		response.sendRedirect(request.getContextPath() + "/member/mypage.do?passwordCheck=fail");
	}// doPost

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/member/mypage.do");
	}// doGet
}// class
