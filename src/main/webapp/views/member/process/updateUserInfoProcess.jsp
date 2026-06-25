<%@page import="java.sql.Date"%>
<%@page import="com.hospital.common.MemberDTO"%>
<%@page import="com.hospital.common.MinorMemberDTO"%>
<%@page import="com.hospital.member.UpdateUserInfoService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
request.setCharacterEncoding("UTF-8");

MemberDTO loginUser = (MemberDTO)session.getAttribute("loginUser");
Boolean verified = (Boolean)session.getAttribute("userInfoVerified");

if(loginUser == null || !Boolean.TRUE.equals(verified)){
	response.sendRedirect("../myPage.jsp");
	return;
}

UpdateUserInfoService service = new UpdateUserInfoService();
String actionType = request.getParameter("actionType");
boolean updated = false;

if("member".equals(actionType)){
	MemberDTO member = new MemberDTO();
	member.setLoginId(loginUser.getLoginId());
	member.setPhoneNumber(request.getParameter("phoneNumber"));
	member.setEmail(request.getParameter("email"));
	member.setZipCode(request.getParameter("zipCode"));
	member.setAddress(request.getParameter("address"));
	member.setAddressDetail(request.getParameter("addressDetail"));
	try{
		// 회원가입 화면과 동일하게 전달된 연도·월·일을 DB 날짜 형식으로 조합한다.
		String memberBirthDate = request.getParameter("memberBirthYear")
				+ "-" + request.getParameter("memberBirthMonth")
				+ "-" + request.getParameter("memberBirthDay");
		member.setBirthDate(Date.valueOf(memberBirthDate));
		updated = service.modifyUserInfo(member);
	}catch(IllegalArgumentException ignored){
		updated = false;
	}
} else if("minor".equals(actionType)){
	MemberDTO memberInfo = service.searchUserInfo(loginUser.getLoginId());
	if(memberInfo != null && "Y".equalsIgnoreCase(memberInfo.getHasMinorMemberYn())){
		MinorMemberDTO minor = new MinorMemberDTO();
		minor.setPatientNo(memberInfo.getPatientNo());
		minor.setMinorName(request.getParameter("minorName"));
		minor.setRelationship(request.getParameter("relationship"));
		try{
			String minorBirthDate = request.getParameter("minorBirthYear")
					+ "-" + request.getParameter("minorBirthMonth")
					+ "-" + request.getParameter("minorBirthDay");
			minor.setMinorBirthDate(Date.valueOf(minorBirthDate));
			updated = service.modifyMinorUserInfo(minor);
		}catch(IllegalArgumentException ignored){
			updated = false;
		}
	}
}
pageContext.setAttribute("updateMessage", updated ? "정보가 수정되었습니다." : "정보 수정에 실패했습니다.");
%>
<script>
alert("<c:out value='${updateMessage}' />");
location.href="${pageContext.request.contextPath}/member/mypage/info.do";
</script>
