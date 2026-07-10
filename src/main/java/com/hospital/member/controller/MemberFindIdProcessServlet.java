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
 * 아이디 찾기를 처리하는 Servlet Controller
 * 
 * 기존 findidProcess.jsp에서 처리하던 파라미터 수집, 아이디 조회,
 * 결과화면 이동 처리를 Servlet으로 전환한다.
 */
public class MemberFindIdProcessServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private final FindAccountService findAccountService = new FindAccountService();
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String birthDate = request.getParameter("birthDate");
		
		try {
			FindAccountDTO faDTO = new FindAccountDTO();
			
			faDTO.setName(name);
			faDTO.setPhoneNumber(phoneNumber);
			faDTO.setEmail(email);
			
			if(birthDate != null && !"".equals(birthDate.trim())) {
				faDTO.setBirthDate(Date.valueOf(birthDate.trim()));
			}//end if 
			
			String loginId = findAccountService.findId(faDTO);
			
			if(loginId != null) {
				request.setAttribute("loginId", loginId);
				request.getRequestDispatcher("/views/member/findIdResult.jsp").forward(request, response);
				return;
			}//end if
			request.getSession().setAttribute("findIdMessage", "일치하는 회원정보가 없습니다.");
			response.sendRedirect(request.getContextPath()+"/views/member/findId.jsp");
		} catch(IllegalArgumentException iae) {
			request.getSession().setAttribute("findIdMessage", "생년월일 형식을 확인해주세요");
			response.sendRedirect(request.getContextPath()+"/views/member/findId.jsp");
		}//end catch 
	}//doPost
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/views/member/findId.jsp");
	}//doGet
}//class
