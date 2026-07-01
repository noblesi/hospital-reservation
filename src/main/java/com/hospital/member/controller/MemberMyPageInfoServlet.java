package com.hospital.member.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;
import com.hospital.member.UpdateUserInfoService;

public class MemberMyPageInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UpdateUserInfoService updateUserInfoService = new UpdateUserInfoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");
		Boolean verified = (Boolean) request.getSession().getAttribute("userInfoVerified");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/member/login.do");
			return;
		}// end if

		if (!Boolean.TRUE.equals(verified)) {
			response.sendRedirect(request.getContextPath() + "/member/mypage.do");
			return;
		}// end if

		MemberDTO userInfo = updateUserInfoService.searchUserInfo(loginUser.getLoginId());
		MinorMemberDTO minorInfo = null;

		if (userInfo != null && "Y".equalsIgnoreCase(userInfo.getHasMinorMemberYn())) {
			minorInfo = updateUserInfoService.searchMinorUserInfo(userInfo.getPatientNo());
		}// end if

		request.setAttribute("userInfo", userInfo);
		request.setAttribute("minorInfo", minorInfo);
		request.setAttribute("yearList", createYearList());
		request.setAttribute("monthList", createMonthList());
		setBirthAttributes(request, userInfo, minorInfo);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/myPageInfo.jsp");
		dispatcher.forward(request, response);
	}// doGet

	private void setBirthAttributes(HttpServletRequest request, MemberDTO userInfo, MinorMemberDTO minorInfo) {
		if (userInfo != null && userInfo.getBirthDate() != null) {
			LocalDate memberBirthDate = userInfo.getBirthDate().toLocalDate();
			request.setAttribute("memberBirthYear", String.valueOf(memberBirthDate.getYear()));
			request.setAttribute("memberBirthMonth", String.format("%02d", memberBirthDate.getMonthValue()));
			request.setAttribute("memberBirthDay", String.format("%02d", memberBirthDate.getDayOfMonth()));
		}// end if

		if (minorInfo != null && minorInfo.getMinorBirthDate() != null) {
			LocalDate minorBirthDate = minorInfo.getMinorBirthDate().toLocalDate();
			request.setAttribute("minorBirthYear", String.valueOf(minorBirthDate.getYear()));
			request.setAttribute("minorBirthMonth", String.format("%02d", minorBirthDate.getMonthValue()));
			request.setAttribute("minorBirthDay", String.format("%02d", minorBirthDate.getDayOfMonth()));
		}// end if
	}// setBirthAttributes

	private List<Integer> createYearList() {
		List<Integer> yearList = new ArrayList<Integer>();
		int currentYear = LocalDate.now().getYear();

		for (int year = currentYear; year >= 1920; year--) {
			yearList.add(year);
		}// end for

		return yearList;
	}// createYearList

	private List<String> createMonthList() {
		List<String> monthList = new ArrayList<String>();

		for (int month = 1; month <= 12; month++) {
			monthList.add(String.format("%02d", month));
		}// end for

		return monthList;
	}// createMonthList
}// class
