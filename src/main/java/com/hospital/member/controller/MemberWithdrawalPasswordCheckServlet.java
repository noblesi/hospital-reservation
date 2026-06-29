package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.member.UpdateUserInfoService;

/**
 * 회원탈퇴 전 현재 비밀번호 확인을 처리하는 Servlet Controller.
 *
 * 기존 checkWithdrawalPasswordProcess.jsp에서 처리하던 로그인 세션 확인,
 * 비밀번호 검증, 최종 탈퇴 확인 화면 이동 처리를 Servlet으로 분리한다.
 */
public class MemberWithdrawalPasswordCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UpdateUserInfoService updateUserInfoService = new UpdateUserInfoService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");
		Boolean verified = (Boolean) request.getSession().getAttribute("userInfoVerified");

		if(loginUser == null || !Boolean.TRUE.equals(verified)) {
			response.sendRedirect(request.getContextPath() + "/member/mypage.do");
			return;
		}//end if

		String password = request.getParameter("password");

		// 현재 비밀번호가 일치한 경우에만 최종 탈퇴 처리가 가능하도록 세션에 확인값을 저장한다.
		if(password != null && updateUserInfoService.checkPassword(loginUser.getLoginId(), password)) {
			request.getSession().setAttribute("withdrawalPasswordVerified", Boolean.TRUE);
			response.sendRedirect(request.getContextPath() + "/views/member/withdrawUser.jsp?withdrawal=confirm");
			return;
		}//end if

		request.getSession().removeAttribute("withdrawalPasswordVerified");
		response.sendRedirect(request.getContextPath() + "/views/member/withdrawUser.jsp?withdrawal=fail");
	}//doPost

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/views/member/withdrawUser.jsp");
	}//doGet
}//class
