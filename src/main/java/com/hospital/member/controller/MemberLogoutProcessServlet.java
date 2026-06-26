package com.hospital.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 회원 로그아웃을 처리하는 Servlet controller
 * 
 * 기존 logout.process에서 처리되던 세션 제거와 화면 이동 처리를 
 * Servlet으로 분리한다. 
 */
public class MemberLogoutProcessServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			// 로그인 사용자 정보가 담긴 세션 전체를 만료 한다. 
			session.invalidate();
		}//end if
		
		response.sendRedirect(request.getContextPath()+"/main.do");
	}//doGet
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}//doPost

}//class
