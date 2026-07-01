<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentYear" value="${now}" pattern="yyyy" />

<c:set var="activeMenu" value="login" scope="request" />
<c:set var="depth1" value="로그인" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>한국중앙병원</title>

<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=${initParam.assetVersion}' />">
<link rel="stylesheet" href="<c:url value='/resources/css/join.css' />">

<!-- 카카오 우편번호 서비스 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>

<jsp:include page="/views/common/userHeader.jsp" />
<jsp:include page="/views/common/userBreadcrumb.jsp" />

<main id="content" class="memJoinContent">

    <c:if test="${not empty sessionScope.joinMessage}">
        <script>
            alert("<c:out value='${sessionScope.joinMessage}' />");
        </script>
        <c:remove var="joinMessage" scope="session" />
    </c:if>

    <div class="contHeadingWrap">
        <h2>만 <span class="title">14세 이상</span> 회원가입</h2>
    </div>

    <ul class="stepWrap">
        <li><b>STEP 01</b>회원유형</li>
        <li><b>STEP 02</b>약관동의/본인인증</li>
        <li class="current"><b>STEP 03</b>회원정보</li>
        <li><b>STEP 04</b>가입완료</li>
    </ul>

    <form id="memberVo"
          name="hForm"
          action="<c:url value='/member/join/process.do' />"
          method="post">
        <input id="join_type" name="join_type" type="hidden" value="${param.join_type}">
        <input id="idChecked" name="idChecked" type="hidden" value="N">
        <fieldset>

            <p class="supText"><span class="required">*</span> 표기 항목은 필수 입력 항목입니다.</p>

            <div class="boardTypeForm">
                <table>
                    <colgroup>
                        <col class="joinFormLabelCol">
                        <col class="joinFormFieldCol">
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
                                    <input id="pass" name="pass" title="비밀번호"class="inputText" type="password">
                                    <span class="desc">영문,숫자, 특수문자 조합으로 9~16자</span>
                                    <p class="errorPass" role="alert"></p>
                                </td>
                        </tr>
                        <tr>
                            <th scope="row"><span class="required">*</span> 비밀번호 확인</th>
                                <td>
                                    <input type="password" id="passConfirm"name="passConfirm" class="inputText" title="비밀번호 확인">
                                    <p class="error" role="alert"></p>
                                </td>
                        </tr>
                        <tr>
                            <th scope="row"><span class="required">*</span> 이름</th>
                            <td>
                                <input id="name" name="name" class="inputText" type="text" maxlength="20">
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 생년월일</th>
                            <td>
                                <input id="birth" name="birth" type="hidden" value="">

                                <select title="생년월일 연도" id="year" name="year" class="dateYY">
                                    <option value="">연도</option>
                                    <c:forEach var="offset" begin="0" end="${currentYear - 1920}">
                                        <c:set var="yearValue" value="${currentYear - offset}" />
                                        <option value="<c:out value='${yearValue}' />"><c:out value="${yearValue}" /></option>
                                    </c:forEach>
                                </select>
                                <span class="txtWrap">-</span>

                                <select title="생년월일 월" id="month" name="month" class="dateMM">
                                    <option value="">월</option>
                                    <c:forEach var="month" begin="1" end="12">
                                        <fmt:formatNumber var="monthValue" value="${month}" pattern="00" />
                                        <option value="<c:out value='${monthValue}' />"><c:out value="${monthValue}" /></option>
                                    </c:forEach>
                                </select>
                                <span class="txtWrap">-</span>

                                <select title="생년월일 일" id="date" name="date" class="dateDD">
                                    <option value="">일</option>
                                </select>
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 휴대전화</th>
                            <td>
                                <input id="hp_no" name="hp_no" type="hidden" value="">

                                <select title="휴대전화 첫번째 자리" id="hp1" name="hp1" class="selectTypeM">
                                    <option value="">선택</option>
                                    <option value="010">010</option>
                                    <option value="011">011</option>
                                    <option value="016">016</option>
                                    <option value="017">017</option>
                                    <option value="019">019</option>
                                </select>

                                <span class="txtWrap">-</span>
                                <input type="tel" id="hp2" name="hp2" class="inputTextSmall" title="휴대전화 두번째 자리" maxlength="4">

                                <span class="txtWrap">-</span>
                                <input type="tel" id="hp3" name="hp3" class="inputTextSmall" title="휴대전화 세번째 자리" maxlength="4">
                            </td>
                        </tr>

                        <tr>
                            <th scope="row"><span class="required">*</span> 이메일</th>
                            <td>
                                <input id="email" name="email" type="hidden" value="">

                                <input type="text" name="email1" id="email1" class="inputText" title="이메일 아이디">
                                <span class="txtWrap">@</span>
                                <input type="text" name="email2" id="email2" class="inputText" title="도메인 주소">

                                <select id="emailDomain" name="emailDomain" class="selectTypeE" title="이메일 도메인">
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
                            <th scope="row"><span class="required">*</span> 성별</th>
                            <td>
                                <input type="radio" name="gender" id="male" value="M">
                                <label for="male" class="lblTxt">남자</label>

                                <input type="radio" name="gender" id="female" value="F">
                                <label for="female" class="lblTxt">여자</label>

                                <span class="desc">정확히 선택해 주십시오.</span>
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
</main>

<jsp:include page="/views/common/userFooter.jsp" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="<c:url value='/resources/js/join.js' />"></script>
<script src="<c:url value='/resources/js/user-layout.js?v=${initParam.assetVersion}' />"></script>

</body>
</html>
