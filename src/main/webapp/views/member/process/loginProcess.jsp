<!--  
 로그인 처리는 MemberLoginProceeServlet으로 이동되었습니다.
 현재 파일은 기존 경로 보존용으로 남겨 둡니다.
 
 처리 경로 : /member/login/process.do
-->

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
// 메인 화면 연결 전까지 로그인 화면으로 이동한다.
location.href = "../main.do";
</script>
<%
    return;
}
%>

<script>
alert("아이디 또는 비밀번호를 확인해주세요.");
history.back();
</script>
