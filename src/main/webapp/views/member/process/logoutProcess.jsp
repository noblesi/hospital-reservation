<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
	로그아웃 처리는 MemberLogoutProcessServlet으로 이동되었습니다.
	기존 logoutProcess.jsp는 더이상 사용하지 않습니다.
 --%>

<%
session.invalidate();
response.sendRedirect(request.getContextPath() + "/main.do");
%>
