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
Boolean passwordVerified = (Boolean)session.getAttribute("withdrawalPasswordVerified");
Boolean userInfoVerified = (Boolean)session.getAttribute("userInfoVerified");

if(loginUser == null
        || !Boolean.TRUE.equals(userInfoVerified)
        || !Boolean.TRUE.equals(passwordVerified)){
    response.sendRedirect("../withdrawUser.jsp");
    return;
}

UpdateUserInfoService service = new UpdateUserInfoService();
boolean withdrawn = service.removeUserInfo(loginUser.getLoginId());

// 확인 세션은 한 번만 사용하며, 탈퇴 성공 시 로그인 세션 전체를 종료한다.
session.removeAttribute("withdrawalPasswordVerified");

if(withdrawn){
    session.invalidate();
%>
<script>
alert("회원 탈퇴가 완료되었습니다.");
location.href="../login.jsp";
</script>
<%
    return;
}
%>
<script>
alert("회원 탈퇴 처리에 실패했습니다.");
location.href="../withdrawUser.jsp";
</script>
