<%@page import="com.hospital.member.MemberRegisterService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

String loginId = request.getParameter("loginId");

if(loginId != null){
    loginId = loginId.trim();
}//end if

if(loginId == null || "".equals(loginId)){
%>
<script>
alert("아이디를 입력해주세요.");
history.back();
</script>
<%
    return;
}//end if

MemberRegisterService mrs = new MemberRegisterService();

boolean duplicate = mrs.checkLoginIdDuplicate(loginId);

if(duplicate){
%>
<script>
alert("이미 사용 중인 아이디입니다.");
history.back();
</script>
<%
} else {
%>
<script>
alert("사용 가능한 아이디입니다.");
history.back();
</script>
<%
}//end else
%>