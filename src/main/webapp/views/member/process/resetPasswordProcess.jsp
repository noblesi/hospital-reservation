<%--
	비밀번호 재설정 처리는 MemberResetPasswordProcessServlet으로 이동되었습니다.
	기존 restPAsswordPRocess.jsp는 사용되지 않습니다.
	
	
 --%>

<%@ page import="com.hospital.member.FindAccountService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

String loginId = (String) session.getAttribute("resetLoginId");

String newPassword = request.getParameter("newPassword");
String confirmPassword = request.getParameter("confirmPassword");

// 비밀번호 재설정 대상 회원이 세션에 존재하는지 확인한다.
if (loginId == null) {
%>
<script>
    alert("비정상적인 접근입니다.");
    location.href = "../findPassword.jsp";
</script>
<%
    return;
}

// 새 비밀번호 입력 여부를 확인한다.
if (newPassword == null || "".equals(newPassword.trim())) {
%>
<script>
    alert("새 비밀번호를 입력해주세요.");
    history.back();
</script>
<%
    return;
}

// 비밀번호 확인값 입력 여부를 확인한다.
if (confirmPassword == null || "".equals(confirmPassword.trim())) {
%>
<script>
    alert("비밀번호 확인을 입력해주세요.");
    history.back();
</script>
<%
    return;
}

// 새 비밀번호와 확인값이 같은지 확인한다.
if (!newPassword.equals(confirmPassword)) {
%>
<script>
    alert("비밀번호가 일치하지 않습니다.");
    history.back();
</script>
<%
    return;
}

FindAccountService service = new FindAccountService();
boolean updated = service.resetPassword(loginId, newPassword);

if (updated) {
    session.removeAttribute("resetLoginId");
%>
<script>
    alert("비밀번호가 변경되었습니다.");
    location.href = "../login.jsp";
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
