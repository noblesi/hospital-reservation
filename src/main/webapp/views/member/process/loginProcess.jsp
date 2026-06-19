<%@page import="com.hospital.common.MemberDTO"%>
<%@page import="com.hospital.member.LoginService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

String loginId = request.getParameter("loginId");
String password = request.getParameter("password");

LoginService ls = new LoginService();
MemberDTO loginMember = ls.login(loginId, password);

if(loginMember != null){
	session.setAttribute("loginMember", loginMember);
%>
	<script>
	alert("로그인 성공");
	location.href="login.jsp";
	</script>
<%
	return;
}
%>

<script>
alert("아이디 또는 비밀번호를 확인해주세요.");
history.back();
</script>