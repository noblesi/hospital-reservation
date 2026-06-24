<%@page import="java.sql.Date"%>
<%@page import="com.hospital.common.MemberDTO"%>
<%@page import="com.hospital.common.MinorMemberDTO"%>
<%@page import="com.hospital.member.MemberRegisterService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
request.setCharacterEncoding("UTF-8");

/* ==============================
   파라미터 받기
============================== */

// 회원가입 유형 TG: 일반회원, TC: 미성년자 회원
String joinType = request.getParameter("join_type");

/* 공통 회원 정보 */
String loginId = request.getParameter("id");
String password = request.getParameter("pass");
String name = request.getParameter("name");

String birth = request.getParameter("birth");
String genderFM = request.getParameter("gender");

String phoneNumber = request.getParameter("hp_no");
String email = request.getParameter("email");

String zipcode = request.getParameter("zipcode");
String address = request.getParameter("address");
String addressDetail = request.getParameter("addressDetail");

/* ==============================
   기본값 검증
============================== */

if(birth == null || "".equals(birth)){
%>
<script>
alert("생년월일 값이 없습니다.");
history.back();
</script>
<%
    return;
}

String hasMinorMemberYn = "TC".equals(joinType) ? "Y" : "N";

/* ==============================
   MemberDTO 생성
============================== */

MemberDTO mDTO = new MemberDTO();

mDTO.setLoginId(loginId);
mDTO.setPassword(password);
mDTO.setName(name);
mDTO.setBirthDate(Date.valueOf(birth));
mDTO.setGenderFM(genderFM);
mDTO.setPhoneNumber(phoneNumber);
mDTO.setEmail(email);
mDTO.setZipCode(zipcode);
mDTO.setAddress(address);
mDTO.setAddressDetail(addressDetail);
mDTO.setHasMinorMemberYn(hasMinorMemberYn);
mDTO.setIp(request.getRemoteAddr());

/* ==============================
   MinorMemberDTO 생성
   미성년자 회원가입일 때만 생성
============================== */

MinorMemberDTO minorDTO = null;

if("TC".equals(joinType)){

    String childYear = request.getParameter("childYear");
    String childMonth = request.getParameter("childMonth");
    String childDate = request.getParameter("childDate");

    if(childYear == null || childMonth == null || childDate == null ||
       "".equals(childYear) || "".equals(childMonth) || "".equals(childDate)){
%>
<script>
alert("환자 생년월일 값이 없습니다.");
history.back();
</script>
<%
        return;
    }

    String childBirth = childYear + "-" + childMonth + "-" + childDate;

    minorDTO = new MinorMemberDTO();

    minorDTO.setRelationship(request.getParameter("relationshipType"));
    minorDTO.setMinorName(request.getParameter("childName"));
    minorDTO.setMinorBirthDate(Date.valueOf(childBirth));
}

/* ==============================
   회원가입 처리
============================== */

MemberRegisterService mrs = new MemberRegisterService();

boolean result = mrs.registerMember(mDTO, minorDTO);

if(result){
    session.setAttribute("registerLoginId", loginId);
%>
<script>
alert("회원가입이 완료되었습니다.");
location.href = "../joinComplete.jsp";
</script>
<%
    return;
}
%>

<script>
alert("회원가입에 실패했습니다.");
history.back();
</script>