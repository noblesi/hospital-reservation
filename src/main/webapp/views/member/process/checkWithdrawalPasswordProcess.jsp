<%@page import="com.hospital.common.MemberDTO"%>
<%@page import="com.hospital.member.UpdateUserInfoService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

if(!"POST".equalsIgnoreCase(request.getMethod())){
    response.sendRedirect("../withdrawUser.jsp");
    return;
}

MemberDTO loginUser = (MemberDTO)session.getAttribute("loginUser");
Boolean verified = (Boolean)session.getAttribute("userInfoVerified");

if(loginUser == null || !Boolean.TRUE.equals(verified)){
    response.sendRedirect("../myPage.jsp");
    return;
}

String password = request.getParameter("password");
UpdateUserInfoService service = new UpdateUserInfoService();

// 현재 비밀번호가 일치한 경우에만 최종 탈퇴 처리가 가능하도록 세션에 확인값을 저장한다.
if(password != null && service.checkPassword(loginUser.getLoginId(), password)){
    session.setAttribute("withdrawalPasswordVerified", Boolean.TRUE);
    response.sendRedirect("../withdrawUser.jsp?withdrawal=confirm");
    return;
}

session.removeAttribute("withdrawalPasswordVerified");
response.sendRedirect("../withdrawUser.jsp?withdrawal=fail");
%>
