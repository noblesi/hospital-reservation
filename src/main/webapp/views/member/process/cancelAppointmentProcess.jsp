<%@page import="com.hospital.common.MemberDTO"%>
<%@page import="com.hospital.member.UserMyPageService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

if(!"POST".equalsIgnoreCase(request.getMethod())){
	response.sendRedirect("../myPage.jsp");
	return;
}

MemberDTO loginUser = (MemberDTO)session.getAttribute("loginUser");

if(loginUser == null){
%>
<script>
alert("로그인이 필요한 서비스입니다.");
location.href="../login.jsp";
</script>
<%
	return;
}

String appointmentNo = request.getParameter("appointmentNo");

if(appointmentNo == null || appointmentNo.trim().isEmpty()){
%>
<script>
alert("잘못된 예약 취소 요청입니다.");
location.href="../myPage.jsp";
</script>
<%
	return;
}

UserMyPageService service = new UserMyPageService();
MemberDTO memberInfo = service.searchMemberInfo(loginUser.getLoginId());

boolean canceled = false;
if(memberInfo != null && memberInfo.getPatientNo() != null){
	canceled = service.cancelAppointment(
		appointmentNo.trim(),
		memberInfo.getPatientNo()
	);
}
%>
<script>
alert("<%= canceled ? "예약이 취소되었습니다." : "예약을 취소하지 못했습니다. 예약 상태를 확인해주세요." %>");
location.href="../myPage.jsp";
</script>
