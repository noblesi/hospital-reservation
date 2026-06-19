<%@ page import="com.hospital.member.FindAccountDAO" %>
<%@ page import="com.hospital.member.dto.FindAccountDTO" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

String loginId = request.getParameter("loginId");
String name = request.getParameter("name");
String phoneNumber = request.getParameter("phoneNumber");
String email = request.getParameter("email");
String birthDateStr = request.getParameter("birthDate");

try {
    FindAccountDTO faDTO = new FindAccountDTO();

    faDTO.setLoginId(loginId);
    faDTO.setName(name);
    faDTO.setBirthDate(Date.valueOf(birthDateStr));

    if(phoneNumber != null && !"".equals(phoneNumber)){
        faDTO.setPhoneNumber(phoneNumber);
    } else {
        faDTO.setEmail(email);
    }//end else

    boolean result = FindAccountDAO.getInstance().checkPassword(faDTO);

    if(result){
        session.setAttribute("resetLoginId", loginId);
        response.sendRedirect("findPassword.jsp?reset=Y");
        return;
    }//end if

} catch(Exception e){
    e.printStackTrace();
}//end catch
%>

<script>
alert("입력하신 회원정보와 일치하는 계정을 찾을 수 없습니다.");
location.href="findPassword.jsp";
</script>