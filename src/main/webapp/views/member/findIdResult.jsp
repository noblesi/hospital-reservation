<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty loginId}">
    <c:redirect url="/views/member/findId.jsp" />
</c:if>

<c:set var="activeMenu" value="login" scope="request" />
<c:set var="depth1" value="아이디/비밀번호 찾기" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>아이디 찾기 결과</title>

<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/find-account.css' />">
</head>

<body>

<jsp:include page="/views/common/userHeader.jsp" />
<jsp:include page="/views/common/userBreadcrumb.jsp" />

<main id="content" class="findContent findResultContent">

    <div class="contHeadingWrap">
        <h2>아이디/비밀번호 찾기</h2>
    </div>

    <section class="findResultWrap">
        <div class="resultTitleBox">
            <h3>아이디 찾기가 완료 되었습니다.</h3>
            <p>가입된 아이디는 아래와 같습니다.</p>
        </div>

        <div class="resultIdBox">
            <span class="resultRadio" aria-hidden="true"></span>
            <span>회원님의 아이디는</span>
            <strong><c:out value="${loginId}" /></strong>
            <span>입니다.</span>
        </div>

        <div class="resultBtnWrap">
            <a href="<c:url value='/views/member/login.jsp' />" class="resultBtn">
                로그인 하기
            </a>
            <a href="<c:url value='/views/member/findPassword.jsp' />" class="resultBtn">
                비밀번호 찾기
            </a>
        </div>
    </section>

</main>

<jsp:include page="/views/common/userFooter.jsp" />

<script src="<c:url value='/resources/js/user-layout.js' />"></script>

</body>
</html>
