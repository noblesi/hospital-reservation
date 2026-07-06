<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="login" scope="request" />
<c:set var="depth1" value="로그인" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>로그인</title>

<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=${initParam.assetVersion}' />">

<style type="text/css">
html, body{
    height:100%;
}

body{
    min-height:100vh;
    margin:0;
    display:flex;
    flex-direction:column;
}

.login-container{
    flex:1;
    width:1180px;
    margin:80px auto 100px;
}

.login-card{
    width:1080px;
    min-height:760px;
    margin:0 auto;
    display:flex;
    background:#fff;
    border:1px solid #e5e8ef;
    border-radius:12px;
    box-shadow:0 8px 25px rgba(0,0,0,.08);
}

.login-left{
    width:50%;
    padding:75px 55px;
    text-align:center;
}

.login-icon{
    width:72px;
    height:72px;
    margin:0 auto 38px;
    display:flex;
    align-items:center;
    justify-content:center;
    border-radius:50%;
    background:#eef5ff;
    color:#005bac;
    font-size:34px;
}

.login-left h2{
    margin-bottom:12px;
    font-size:34px;
    color:#111;
}

.login-sub-text{
    margin-bottom:35px;
    font-size:17px;
    color:#6b7280;
}

.login-form input{
    width:100%;
    height:54px;
    margin-bottom:14px;
    padding:0 18px;
    border:1px solid #d8dee8;
    border-radius:5px;
    font-size:17px;
}

.login-form input:focus{
    outline:none;
    border-color:#005bac;
}

.login-btn{
    width:100%;
    height:56px;
    margin-top:8px;
    border:0;
    border-radius:5px;
    background:#005bac;
    color:#fff;
    font-size:18px;
    font-weight:600;
    cursor:pointer;
}

.login-btn:hover{
    background:#004792;
}

.login-links{
    margin-top:28px;
    display:flex;
    justify-content:center;
    align-items:center;
    gap:14px;
    font-size:16px;
}

.login-links a{
    color:#005bac;
    text-decoration:none;
}

.login-links a:hover{
    text-decoration:underline;
}

.login-links span{
    color:#c5cbd6;
}

.login-right{
    width:50%;
    padding:75px 60px;
    border-left:1px solid #edf0f5;
    display:flex;
    flex-direction:column;
    justify-content:center;
    align-items:center;
    text-align:center;
}

.security-icon{
    width:150px;
    height:150px;
    margin-bottom:35px;
    display:flex;
    align-items:center;
    justify-content:center;
    border-radius:50%;
    background:#eef5ff;
}

.security-icon img{
    width:95px;
    height:95px;
    object-fit:contain;
}

.login-right h3{
    margin-bottom:18px;
    font-size:26px;
    color:#111;
}

.login-right p{
    font-size:17px;
    color:#666;
    line-height:1.9;
}
</style>
</head>

<body>


<jsp:include page="/views/common/userHeader.jsp" />
<jsp:include page="/views/common/userBreadcrumb.jsp" />

<main class="login-container">

    <c:if test="${not empty sessionScope.loginMessage}">
        <script>
            alert("<c:out value='${sessionScope.loginMessage}' />");
        </script>
        <c:remove var="loginMessage" scope="session" />
    </c:if>

    <section class="login-card">

        <div class="login-left">

            <div class="login-icon">
                <img src="<c:url value='/resources/images/security/login_Human24px.png' />"
                     alt="사람 로그인 이미지">
            </div>

            <h2>로그인</h2>

            <p class="login-sub-text">
                시스템 이용을 위해 로그인해 주세요.
            </p>

            <form class="login-form"
                  action="<c:url value='/member/login/process.do' />"
                  method="post">

                <input type="text"
                       name="loginId"
                       placeholder="아이디를 입력해주세요">

                <input type="password"
                       name="password"
                       placeholder="비밀번호">

                <button type="submit" class="login-btn">
                    로그인
                </button>

            </form>

            <div class="login-links">
                <a href="<c:url value='/views/member/findId.jsp' />">아이디/비밀번호 찾기</a>
                <span>|</span>
                <a href="<c:url value='/views/member/joinType.jsp' />">회원가입</a>
            </div>

        </div>

        <div class="login-right">

            <div class="security-icon">
                <img src="<c:url value='/resources/images/security/login_Shield.png' />"
                     alt="보안 로그인 이미지">
            </div>

            <h3>안전한 로그인을 지원합니다.</h3>

            <p>
                고객님의 개인정보 보호를 위해<br>
                보안에 최선을 다하고 있습니다.
            </p>

        </div>

    </section>

</main>


<jsp:include page="/views/common/userFooter.jsp" />

<script src="<c:url value='/resources/js/user-layout.js?v=${initParam.assetVersion}' />"></script>

</body>
</html>
