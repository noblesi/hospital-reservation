package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.member.UpdateUserInfoService;

/**
 * 회원탈퇴 최종 처리를 담당하는 Servlet Controller.
 *
 * 기존 withdrawUserProcess.jsp에서 처리하던 탈퇴 가능 세션 확인,
 * 회원 탈퇴 처리, 세션 종료, 화면 이동 처리를 Servlet으로 분리한다.
 */
public class MemberWithdrawProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UpdateUserInfoService updateUserInfoService = new UpdateUserInfoService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");
		Boolean passwordVerified = (Boolean) request.getSession().getAttribute("withdrawalPasswordVerified");
		Boolean userInfoVerified = (Boolean) request.getSession().getAttribute("userInfoVerified");

		if(loginUser == null
				|| !Boolean.TRUE.equals(userInfoVerified)
				|| !Boolean.TRUE.equals(passwordVerified)) {
			response.sendRedirect(request.getContextPath() + "/member/withdraw.do");
			return;
		}//end if

		boolean withdrawn = updateUserInfoService.removeUserInfo(loginUser.getLoginId());

		// 탈퇴 비밀번호 확인 세션값은 최종 처리 요청에서 한 번만 사용한다.
		request.getSession().removeAttribute("withdrawalPasswordVerified");

		if(withdrawn) {
			request.getSession().invalidate();
			request.getSession(true).setAttribute("loginMessage", "회원 탈퇴가 완료되었습니다.");
			response.sendRedirect(request.getContextPath() + "/member/login.do");
			return;
		}//end if

		response.sendRedirect(request.getContextPath() + "/member/withdraw.do?withdrawal=error");
	}//doPost

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/member/withdraw.do");
	}//doGet
}//class
