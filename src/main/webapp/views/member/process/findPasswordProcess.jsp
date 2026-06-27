<%--
	비밀번호 찾기 회원 확인 관리는 MemberFindPasswordProcessServlet으로 이동하였습니다.
	기존 findPasswordProcess.jsp는 더이상 직접 사용하지 않습니다.
 --%>

<%@ page import="com.hospital.member.FindAccountService" %>
<%@ page import="com.hospital.member.dto.FindAccountDTO" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

// 이전 비밀번호 찾기에서 남아있을 수 있는 재설정 대상 아이디를 먼저 초기화한다.
session.removeAttribute("resetLoginId");

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

    FindAccountService service = new FindAccountService();
    boolean result = service.findPassword(faDTO);

    if(result){
        session.setAttribute("resetLoginId", loginId);
        response.sendRedirect("../findPassword.jsp?reset=Y");
        return;
    }//end if

} catch(Exception e){
    e.printStackTrace();
}//end catch
%>

<script>
alert("입력하신 회원정보와 일치하는 계정을 찾을 수 없습니다.");
location.href = "../findPassword.jsp";
</script>
