<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
// 최종 탈퇴를 취소한 경우 비밀번호 확인 세션값을 즉시 폐기한다.
session.removeAttribute("withdrawalPasswordVerified");
response.sendRedirect("../withdrawUser.jsp");
%>
