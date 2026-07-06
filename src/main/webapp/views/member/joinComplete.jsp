<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="login" scope="request" />
<c:set var="depth1" value="회원가입" scope="request" />


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입 완료</title>

<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=${initParam.assetVersion}' />">
<link rel="stylesheet" href="<c:url value='/resources/css/join.css' />">

</head>

<body>

<jsp:include page="/views/common/userHeader.jsp" />
<jsp:include page="/views/common/userBreadcrumb.jsp" />

<main id="content" class="memJoinContent">

    <div class="contHeadingWrap">
        <h2>회원가입</h2>
    </div>

    <ul class="stepWrap">
        <li><b>STEP 01</b>회원유형</li>
        <li><b>STEP 02</b>약관동의</li>
        <li><b>STEP 03</b>정보입력</li>
        <li class="current"><b>STEP 04</b>가입완료</li>
    </ul>

    <div class="joinCompleteWrap">

        <div class="completeIcon">
              <img src="<c:url value='/resources/images/security/login_check.png' />" alt="가입완료">
        </div>

        <h3>가입이 완료되었습니다.</h3>
        <p>한국중앙병원 회원이 되어주셔서 감사합니다.</p>

        <div class="memberInfoBox">

            <div class="infoCard">
                <span class="label">이름</span>
                <strong><c:out value="${member.name}" /></strong>
            </div>

            <div class="infoCard">
                <span class="label">회원 아이디</span>
                <strong><c:out value="${member.loginId}" /></strong>
            </div>

            <div class="infoCard">
                <span class="label">가입일</span>
                <strong><c:out value="${member.registeredAt}" /></strong>
            </div>

        </div>

        <div class="btnWrap">
            <a href="<c:url value='/main.do' />" class="btnType02">
                메인으로 이동
            </a>

            <a href="<c:url value='/views/member/login.jsp' />" class="btnType03">
                로그인
            </a>
        </div>

    </div>

</main>

<jsp:include page="/views/common/userFooter.jsp" />

<script src="<c:url value='/resources/js/user-layout.js?v=${initParam.assetVersion}' />"></script>

</body>
</html>
