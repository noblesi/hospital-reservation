<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
    회원탈퇴 비밀번호 확인 처리는 MemberWithdrawalPasswordCheckServlet으로 이동되었습니다.
    기존 checkWithdrawalPasswordProcess.jsp는 더 이상 직접 사용하지 않습니다.

    처리 경로:
    /member/withdraw/password-check.do
--%>

<%
    response.sendRedirect(request.getContextPath() + "/member/withdraw.do");
%>
