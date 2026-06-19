<%@page import="java.sql.Date"%>
<%@page import="com.hospital.member.FindAccountService"%>
<%@page import="com.hospital.member.dto.FindAccountDTO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

String name = request.getParameter("name");
String phoneNumber = request.getParameter("phoneNumber");
String email = request.getParameter("email");
String birthDate = request.getParameter("birthDate");

FindAccountDTO faDTO = new FindAccountDTO();

faDTO.setName(name);
faDTO.setPhoneNumber(phoneNumber);
faDTO.setEmail(email);

if(birthDate != null && !"".equals(birthDate)){
	faDTO.setBirthDate(Date.valueOf(birthDate));
}

FindAccountService fas = new FindAccountService();
String loginId = fas.findId(faDTO);

if(loginId != null){
	request.setAttribute("loginId", loginId);
	request.getRequestDispatcher("findIdResult.jsp").forward(request, response);
	return;
}

%>

<%
/* System.out.println("name = " + faDTO.getName());
System.out.println("phoneNumber = " + faDTO.getPhoneNumber());
System.out.println("email = " + faDTO.getEmail());
System.out.println("birthDate = " + faDTO.getBirthDate());
 */%>

<script>
alert("일치하는 회원정보가 없습니다.");
history.back();
</script>