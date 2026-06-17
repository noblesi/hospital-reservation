<%@ page import="com.hospital.member.FindAccountDAO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

String loginId = (String) session.getAttribute("resetLoginId");

String newPassword = request.getParameter("newPassword");
String confirmPassword = request.getParameter("confirmPassword");

// 세션 체크
if (loginId == null) {
%>
<script>
	alert("비정상적인 접근입니다.");
	location.href = "findPassword.jsp";
</script>
<%
return;
}

// 빈값 체크
if (newPassword == null || "".equals(newPassword.trim())) {
%>
<script>
	alert("새 비밀번호를 입력해주세요.");
	history.back();
</script>
<%
return;
}

// 비밀번호 확인 체크
if (confirmPassword == null || "".equals(confirmPassword.trim())) {
%>
<script>
	alert("비밀번호 확인을 입력해주세요.");
	history.back();
</script>
<%
return;
}

// 일치 여부 체크
if (!newPassword.equals(confirmPassword)) {
%>
<script>
	alert("비밀번호가 일치하지 않습니다.");
	history.back();
</script>
<%
return;
}

int rowCnt = 0;

try {
rowCnt = FindAccountDAO.getInstance().resetPassword(loginId, newPassword);

} catch (Exception e) {
e.printStackTrace();
}

if (rowCnt > 0) {

session.removeAttribute("resetLoginId");
%>
<script>
	alert("비밀번호가 변경되었습니다.");
	location.href = "login.jsp";
</script>
<%
} else {
%>
<script>
	alert("비밀번호 변경에 실패했습니다.");
	history.back();
</script>
<%
}
%>