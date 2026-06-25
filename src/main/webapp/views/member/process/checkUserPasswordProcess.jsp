<%@page import="com.hospital.common.MemberDTO"%>
<%@page import="com.hospital.member.UpdateUserInfoService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

if(!"POST".equalsIgnoreCase(request.getMethod())){
	response.sendRedirect("../myPage.jsp");
	return;
}

MemberDTO loginUser = (MemberDTO)session.getAttribute("loginUser");

if(loginUser == null){
	response.sendRedirect("../login.jsp");
	return;
}

String password = request.getParameter("password");
UpdateUserInfoService service = new UpdateUserInfoService();

if(password != null && service.checkPassword(loginUser.getLoginId(), password)){
	session.setAttribute("userInfoVerified", Boolean.TRUE);
	response.sendRedirect(request.getContextPath() + "/member/mypage/info.do");
	return;
}

response.sendRedirect("../myPage.jsp?passwordCheck=fail");
%>
