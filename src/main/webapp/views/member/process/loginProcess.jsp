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
	session.setAttribute("loginUser", loginMember);
%>
	<script>
	location.href="../login.jsp";
	//메인 화면이 없어서 로그인 성고해도 login.jsp로 보냄 
	</script>
<%
	return;
}
%>

<script>
alert("아이디 또는 비밀번호를 확인해주세요.");
history.back();
</script>