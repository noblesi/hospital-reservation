<%--
	아이디 찾기 처리는 memberFindIdProcessServlet으로 이동 되었습니다.
	기존 findidprocess.jsp는 더이상 사용되지 않습니다.

 --%>

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

try {
    FindAccountDTO faDTO = new FindAccountDTO();

    faDTO.setName(name);
    faDTO.setPhoneNumber(phoneNumber);
    faDTO.setEmail(email);

    if(birthDate != null && !"".equals(birthDate)){
        faDTO.setBirthDate(Date.valueOf(birthDate));
    }

    FindAccountService service = new FindAccountService();
    String loginId = service.findId(faDTO);

    if(loginId != null){
        request.setAttribute("loginId", loginId);
        request.getRequestDispatcher("../findIdResult.jsp").forward(request, response);
        return;
    }
} catch(IllegalArgumentException exception){
    exception.printStackTrace();
}
%>

<script>
alert("일치하는 회원정보가 없습니다.");
history.back();
</script>
