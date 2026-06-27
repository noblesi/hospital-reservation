<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
    회원가입 처리는 MemberJoinProcessServlet으로 이동되었습니다.
    기존 joinProcess.jsp는 더 이상 직접 사용하지 않습니다.

    처리 경로:
    /member/join/process.do
--%>

<%
    response.sendRedirect(request.getContextPath() + "/member/join.do");
%>
