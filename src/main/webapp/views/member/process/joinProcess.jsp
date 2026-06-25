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

// 미성년자 회원가입 폼에서 넘어온 경우 join_type 누락을 방지한다.
if((joinType == null || "".equals(joinType)) && request.getParameter("childName") != null){
    joinType = "TC";
}

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

MemberRegisterService mrs = new MemberRegisterService();

if(loginId == null || "".equals(loginId.trim())){
%>
<script>
alert("아이디 값이 없습니다.");
history.back();
</script>
<%
    return;
}

loginId = loginId.trim();

// 아이디는 한글 3자 이상 또는 영문+숫자 혼용 6~12자만 허용한다.
if(!loginId.matches("^[가-힣]{3,}$|^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,12}$")){
%>
<script>
alert("아이디는 한글 3자 이상 또는 영문+숫자 혼용 6~12자로 입력해주세요.");
history.back();
</script>
<%
    return;
}

// 아이디 중복확인 팝업을 거치지 않고 제출되는 경우를 대비해 DB에서 한 번 더 확인한다.
if(mrs.checkLoginIdDuplicate(loginId)){
%>
<script>
alert("이미 사용 중인 아이디입니다.");
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
    String childGender = request.getParameter("childGender");
    String childName = request.getParameter("childName");
    String relationshipType = request.getParameter("relationshipType");

    if(childName == null || "".equals(childName.trim())){
%>
<script>
alert("환자 이름 값이 없습니다.");
history.back();
</script>
<%
        return;
    }

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

    if(childGender == null || "".equals(childGender.trim())){
%>
<script>
alert("환자 성별 값이 없습니다.");
history.back();
</script>
<%
        return;
    }

    if(relationshipType == null || "".equals(relationshipType.trim())){
%>
<script>
alert("보호자와의 관계 값이 없습니다.");
history.back();
</script>
<%
        return;
    }

    String childBirth = childYear + "-" + childMonth + "-" + childDate;

    minorDTO = new MinorMemberDTO();

    minorDTO.setRelationship(relationshipType);
    minorDTO.setMinorName(childName.trim());
    minorDTO.setMinorBirthDate(Date.valueOf(childBirth));
    minorDTO.setMinorGenderFM(childGender);
}

/* ==============================
   회원가입 처리
============================== */

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
