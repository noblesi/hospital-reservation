<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="login" scope="request" />
<c:set var="depth1" value="로그인" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>한국중앙병원</title>

<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/join.css' />">

<!-- 카카오 우편번호 서비스 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>

<jsp:include page="../common/userHeader.jsp" />
<jsp:include page="../common/userBreadcrumb.jsp" />

<main id="content" class="memJoinContent">

    <div class="contHeadingWrap">
        <h2>만 <span class="title child">14세 미만</span> 회원가입</h2>
    </div>

    <ul class="stepWrap">
        <li><b>STEP 01</b>회원유형</li>
        <li><b>STEP 02</b>약관동의/본인인증</li>
        <li class="current"><b>STEP 03</b>회원정보</li>
        <li><b>STEP 04</b>가입완료</li>
    </ul>

    <form id="memberVo"
          name="hForm"
          action="<c:url value='/views/member/process/joinProcess.jsp' />"
          method="post">
        <input id="join_type" name="join_type" type="hidden" value="${param.join_type}">

        <fieldset>
            <legend>회원가입</legend>

            <p class="supText"><span class="required">*</span> 표기 항목은 필수 입력 항목입니다.</p>

            <div class="boardTypeForm">
                <table>
                    <colgroup>
                        <col style="width:150px;">
                        <col style="width:auto;">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row"><span class="required">*</span> 아이디</th>
                            <td>
                                <input id="id" name="id" title="아이디" class="inputText" type="text" maxlength="12">
                                <button type="button" class="btnType01" id="idChkBtn">중복확인</button>
                                <span class="desc">한글(3자 이상), 영문 + 숫자 혼용 6~12자</span>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><span class="required">*</span> 비밀번호</th>
                                <td>
                                    <input id="pass" name="pass" title="비밀번호" class="inputText" type="password"> 
                                    <span class="desc">영문, 숫자, 특수문자 조합으로 9~16자</span>
                                    <p class="errorPass" role="alert"></p>
                                </td>
                        </tr>
                        <tr>
                            <th scope="row"><span class="required">*</span> 비밀번호 확인</th>
                                <td>
                                <input type="password" id="passConfirm" name="passConfirm" class="inputText" title="비밀번호 확인">
                                    <p class="error" role="alert"></p>
                                </td>
                        </tr>
                        <tr>
                            <th scope="row"><span class="required">*</span> 보호자 이름</th>
                            <td>
                                <input id="name" name="name" class="inputText" type="text" maxlength="20">
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 보호자 생년월일</th>
                            <td>
                                <input id="birth" name="birth" type="hidden" value="">
                                <select id="year" name="year" class="dateYY">
                                    <option value="">연도</option>
                                    <%
                                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                                    for(int i = currentYear; i >= 1920; i--){
                                    %>
                                        <option value="<%= i %>"><%= i %></option>
                                    <%
                                    }
                                    %>
                                </select>
                                <span class="txtWrap">-</span>

                                <select id="month" name="month" class="dateMM">
                                    <option value="">월</option>
                                    <%
                                    for(int i = 1; i <= 12; i++){
                                        String month = i < 10 ? "0" + i : String.valueOf(i);
                                    %>
                                        <option value="<%= month %>"><%= month %></option>
                                    <%
                                    }
                                    %>
                                </select>
                                <span class="txtWrap">-</span>

                                <select id="date" name="date" class="dateDD">
                                    <option value="">일</option>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 휴대전화</th>
                            <td>
                                <input id="hp_no" name="hp_no" type="hidden" value="">
                                <select id="hp1" name="hp1" class="selectTypeM">
                                    <option value="">선택</option>
                                    <option value="010">010</option>
                                    <option value="011">011</option>
                                    <option value="016">016</option>
                                    <option value="017">017</option>
                                    <option value="019">019</option>
                                </select>

                                <span class="txtWrap">-</span>
                                <input type="tel" id="hp2" name="hp2" class="inputTextSmall" maxlength="4">

                                <span class="txtWrap">-</span>
                                <input type="tel" id="hp3" name="hp3" class="inputTextSmall" maxlength="4">
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 이메일</th>
                            <td>
                                <input id="email" name="email" type="hidden" value="">
                                <input type="text" name="email1" id="email1" class="inputText">
                                <span class="txtWrap">@</span>
                                <input type="text" name="email2" id="email2" class="inputText">

                                <select id="emailDomain" name="emailDomain" class="selectTypeE">
                                    <option value="">직접입력</option>
                                    <option value="naver.com">naver.com</option>
                                    <option value="gmail.com">gmail.com</option>
                                    <option value="daum.net">daum.net</option>
                                    <option value="hanmail.net">hanmail.net</option>
                                    <option value="nate.com">nate.com</option>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th scope="row" class="verTop"><span class="required">*</span> 주소</th>
                            <td>
                                <input type="text" id="sample6_postcode" name="zipcode" class="inputText" placeholder="우편번호" readonly>
                                <button type="button" class="btnType01" id="addressSearchButton">우편번호 찾기</button>
                                <br>

                                <input type="text" id="sample6_address" name="address" class="inputAddress" placeholder="주소" readonly>
                                <br>

                                <input type="text" id="sample6_detailAddress" name="addressDetail" class="inputAddress" placeholder="상세주소">
                                <input type="hidden" id="sample6_extraAddress" name="extraAddress">
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 보호자 성별</th>
                            <td>
                                <input type="radio" name="gender" id="male" value="M">
                                <label for="male" class="lblTxt">남자</label>

                                <input type="radio" name="gender" id="female" value="F">
                                <label for="female" class="lblTxt">여자</label>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="contHeadingWrap childInfoTitle">
                <h2>환자 정보를 입력해주세요</h2>
            </div>

            <div class="boardTypeForm">
                <table>
                    <colgroup>
                        <col style="width:150px;">
                        <col style="width:auto;">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th scope="row"><span class="required">*</span> 환자 이름</th>
                            <td>
                            
                                <input id="childName" name="childName" class="inputText" type="text" maxlength="20">
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 환자 생년월일</th>
                            <td>
                                <select id="childYear" name="childYear" class="dateYY">
                                    <option value="">연도</option>
                                    <%
                                    for(int i = currentYear; i >= 1920; i--){
                                    %>
                                        <option value="<%= i %>"><%= i %></option>
                                    <%
                                    }
                                    %>
                                </select>
                                <span class="txtWrap">-</span>

                                <select id="childMonth" name="childMonth" class="dateMM">
                                    <option value="">월</option>
                                    <%
                                    for(int i = 1; i <= 12; i++){
                                        String childMonth = i < 10 ? "0" + i : String.valueOf(i);
                                    %>
                                        <option value="<%= childMonth %>"><%= childMonth %></option>
                                    <%
                                    }
                                    %>
                                </select>
                                <span class="txtWrap">-</span>

                                <select id="childDate" name="childDate" class="dateDD">
                                    <option value="">일</option>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 환자 성별</th>
                            <td>
                                <input type="radio" name="childGender" id="childMale" value="M">
                                <label for="childMale" class="lblTxt">남자</label>

                                <input type="radio" name="childGender" id="childFemale" value="F">
                                <label for="childFemale" class="lblTxt">여자</label>
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 보호자와의 관계</th>
                            <td>
                                <select id="relationshipType" name="relationshipType" class="selectTypeE">
                                    <option value="">선택</option>
                                    <option value="부">부</option>
                                    <option value="모">모</option>
                                    <option value="etc">기타</option>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </fieldset>

        <div class="btnWrap">
            <button type="button" class="btnType02 btnBig" id="gFormBeforeBtn">이전단계</button>
            <button type="button" class="btnType03 btnBig" id="gFormNextBtn">다음단계</button>
        </div>
    </form>
    <form id="idCheckFrm"
          action="<c:url value='/views/member/process/idCheckProcess.jsp' />"
          method="get">
        <input type="hidden" id="checkLoginId" name="loginId">
    </form>

</main>

<jsp:include page="../common/userFooter.jsp" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="<c:url value='/resources/js/join.js' />"></script>
<script src="<c:url value='/resources/js/user-layout.js' />"></script>

</body>
</html>
