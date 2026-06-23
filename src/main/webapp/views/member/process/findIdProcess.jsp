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
%>
<script>
alert("회원님의 아이디는 <%= loginId %> 입니다.");
location.href = "../login.jsp";
</script>
<%
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
