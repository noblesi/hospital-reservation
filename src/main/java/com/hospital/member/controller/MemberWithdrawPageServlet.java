package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.member.UpdateUserInfoService;

public class MemberWithdrawPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UpdateUserInfoService updateUserInfoService = new UpdateUserInfoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");
		Boolean verified = (Boolean) request.getSession().getAttribute("userInfoVerified");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
			return;
		}

		if (!Boolean.TRUE.equals(verified)) {
			response.sendRedirect(request.getContextPath() + "/member/mypage.do");
			return;
		}

		MemberDTO userInfo = updateUserInfoService.searchUserInfo(loginUser.getLoginId());
		request.setAttribute("userInfo", userInfo);

		if (!"confirm".equals(request.getParameter("withdrawal"))) {
			request.getSession().removeAttribute("withdrawalPasswordVerified");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/withdrawUser.jsp");
		dispatcher.forward(request, response);
	}
}
