<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
    회원탈퇴 취소 처리는 MemberCancelWithdrawalServlet으로 이동되었습니다.
    기존 cancelWithdrawalProcess.jsp는 더 이상 직접 사용하지 않습니다.

    처리 경로:
    /member/withdraw/cancel.do
--%>

<%
    response.sendRedirect(request.getContextPath() + "/member/withdraw.do");
%>
