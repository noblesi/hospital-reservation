package com.hospital.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.common.MemberDTO;
import com.hospital.common.MinorMemberDTO;
import com.hospital.member.UpdateUserInfoService;

public class MemberUpdateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UpdateUserInfoService updateUserInfoService = new UpdateUserInfoService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDTO loginUser = (MemberDTO) request.getSession().getAttribute("loginUser");
		Boolean verified = (Boolean) request.getSession().getAttribute("userInfoVerified");

		if (loginUser == null || !Boolean.TRUE.equals(verified)) {
			response.sendRedirect(request.getContextPath() + "/member/mypage.do");
			return;
		}// end if

		boolean updated = updateInfo(request, loginUser);
		request.getSession().setAttribute("memberInfoMessage",
				updated ? "정보가 수정되었습니다." : "정보 수정에 실패했습니다.");
		response.sendRedirect(request.getContextPath() + "/member/mypage/info.do");
	}// doPost

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/member/mypage.do");
	}// doGet

	private boolean updateInfo(HttpServletRequest request, MemberDTO loginUser) {
		String actionType = request.getParameter("actionType");

		if ("member".equals(actionType)) {
			return updateMember(request, loginUser);
		}// end if

		if ("minor".equals(actionType)) {
			return updateMinor(request, loginUser);
		}// end if

		return false;
	}// updateInfo

	private boolean updateMember(HttpServletRequest request, MemberDTO loginUser) {
		String phoneNumber = request.getParameter("phoneNumber");
		
		/*
		 * 회원정보 수정 시 전화번호 형식을 검증한다.
		 * 암호화 전 평문 기준을 010-0000-0000 형식으로 통일하기 위해 서버에서도 한 번 더 확인한다.
		 */
		if(phoneNumber == null || !phoneNumber.matches("^010-\\d{4}-\\d{4}$")) {
			return false;
		}//end if
		
		MemberDTO member = new MemberDTO();
		member.setLoginId(loginUser.getLoginId());
		member.setPhoneNumber(phoneNumber);
		member.setEmail(request.getParameter("email"));
		member.setZipCode(request.getParameter("zipCode"));
		member.setAddress(request.getParameter("address"));
		member.setAddressDetail(request.getParameter("addressDetail"));

		try {
			String memberBirthDate = request.getParameter("memberBirthYear")
					+ "-" + request.getParameter("memberBirthMonth")
					+ "-" + request.getParameter("memberBirthDay");
			member.setBirthDate(Date.valueOf(memberBirthDate));
			return updateUserInfoService.modifyUserInfo(member);
		} catch (IllegalArgumentException ignored) {
			return false;
		}// end catch
	}// updateMember

	private boolean updateMinor(HttpServletRequest request, MemberDTO loginUser) {
		MemberDTO memberInfo = updateUserInfoService.searchUserInfo(loginUser.getLoginId());

		if (memberInfo == null || !"Y".equalsIgnoreCase(memberInfo.getHasMinorMemberYn())) {
			return false;
		}// end if

		MinorMemberDTO minor = new MinorMemberDTO();
		minor.setPatientNo(memberInfo.getPatientNo());
		minor.setMinorName(request.getParameter("minorName"));
		minor.setRelationship(request.getParameter("relationship"));

		try {
			String minorBirthDate = request.getParameter("minorBirthYear")
					+ "-" + request.getParameter("minorBirthMonth")
					+ "-" + request.getParameter("minorBirthDay");
			minor.setMinorBirthDate(Date.valueOf(minorBirthDate));
			return updateUserInfoService.modifyMinorUserInfo(minor);
		} catch (IllegalArgumentException ignored) {
			return false;
		}// end catch
	}// updateMinor
}// class
