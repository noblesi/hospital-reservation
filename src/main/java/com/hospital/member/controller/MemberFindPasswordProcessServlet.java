package com.hospital.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hospital.member.FindAccountService;
import com.hospital.member.dto.FindAccountDTO;

/**
 * 비밀 번호 찾기에서 회원 정보를 확인하는 Servlet Controller
 * 
 * 기존 findPasswordProcess.jsp에서 처리하던 요청 파라미터 수집, 
 * 회원 검증, 비밀번호 재설정 화면 이동처리를 servlet으로 분리된다.
 * 
 */
public class MemberFindPasswordProcessServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private final FindAccountService findAccountService = new FindAccountService();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// 이전 비밀번호 찾기에서 남아있을 수 있는 대상의 아이디 지우기 
		request.getSession().removeAttribute("resetLoginId");
		
		String loginId = request.getParameter("loginId");
		String name = request.getParameter("name");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String birthDate = request.getParameter("birthDate");
		
		try {
			FindAccountDTO faDTO = new FindAccountDTO();
			
			faDTO.setLoginId(loginId);
			faDTO.setName(name);
			
			if(birthDate != null && !"".equals(birthDate.trim())) {
				faDTO.setBirthDate(Date.valueOf(birthDate.trim()));
			}//end if
			
			if(phoneNumber != null && !"".equals(phoneNumber.trim())) {
				faDTO.setPhoneNumber(phoneNumber.trim());
			} else {
				faDTO.setEmail(email);
			}//end else 
			
			boolean result = findAccountService.findPassword(faDTO);
			
			if(result) {
				request.getSession().setAttribute("resetLoginId", loginId);
				response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp?reset=Y");
				return;
			}//end if
			
			request.getSession().setAttribute("findPasswordMsg", "입력하신 회원정보와 일치하는 계정을 찾을 수 없습니다.");
			response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp");
		} catch (Exception e) {
			request.getSession().setAttribute("findPasswordMsg", "입력 정보를 다시 확인해주세요");
			response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp");
		}//end catch
	}//doPost
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/views/member/findPassword.jsp");
	}//doGet
	
}//class
