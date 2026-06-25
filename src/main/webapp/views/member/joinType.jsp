<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="login" scope="request" />
<c:set var="depth1" value="로그인" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>회원가입 유형 선택</title>

<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=20260623-menu-hover-guard' />">
<link rel="stylesheet" href="<c:url value='/resources/css/join.css' />">

</head>

<body>

<jsp:include page="../common/userHeader.jsp" />
<jsp:include page="../common/userBreadcrumb.jsp" />
<main id="content" class="memJoinContent">

    <div class="contHeadingWrap">
        <h2>회원가입</h2>
    </div>

    <ul class="stepWrap">
        <li class="current">
            <b>STEP 01</b>
            회원유형
        </li>
        <li>
            <b>STEP 02</b>
            약관동의/본인인증
        </li>
        <li>
            <b>STEP 03</b>
            회원정보
        </li>
        <li>
            <b>STEP 04</b>
            가입완료
        </li>
    </ul>

    <div class="memJoinWrap">

        <div class="card">
            <div class="card-header">
                <div class="card-icon">
                    <img src="<c:url value='/resources/images/security/login_child.png' />"
                         alt="어린이 회원">
                </div>
                <h3>만 14세 미만 <span class="title-child">어린이</span> 회원</h3>
            </div>

            <a href="<c:url value='/views/member/joinAgreeChild.jsp?join_type=TC' />"
               class="button">어린이 회원가입</a>

            <div class="note">
                <p>※ 어린이 진료회원은 법정대리인 보호자의 실명인증이 필요합니다.</p>
                <p><span class="highlight">SNS 회원가입</span>이 <span class="highlight">불가능</span>합니다.</p>
                <p>만 14세 미만 아동은 어린이 회원가입으로 진행해 주세요.</p>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <div class="card-icon">
                    <img src="<c:url value='/resources/images/security/login_TypeCommon.png' />"
                         alt="일반 회원">
                </div>
                <h3>만 14세 이상 <span class="title-common">일반</span> 회원</h3>
            </div>

            <a href="<c:url value='/views/member/joinAgreeCommon.jsp?join_type=TG' />"
               class="button">일반 회원가입</a>

            <div class="note">
                <p>※ 일반 회원은 본인인증 후 회원가입을 진행할 수 있습니다.</p>
                <p>병원 예약, 예약 조회, 마이페이지 서비스를 이용할 수 있습니다.</p>
            </div>
        </div>

    </div>

</main>


<jsp:include page="../common/userFooter.jsp" />
<script src="<c:url value='/resources/js/user-layout.js?v=20260623-menu-hover-guard' />"></script>

</body>
</html>
