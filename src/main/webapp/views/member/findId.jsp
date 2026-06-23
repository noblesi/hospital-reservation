<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="login" scope="request" />
<c:set var="depth1" value="아이디/비밀번호 찾기" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>아이디/비밀번호 찾기</title>

<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/find-account.css' />">
</head>

<body>

<jsp:include page="../common/userHeader.jsp" />
<jsp:include page="../common/userBreadcrumb.jsp" />

<main id="content" class="findContent">

    <div class="contHeadingWrap">
        <h2>아이디/비밀번호 찾기</h2>
    </div>

    <div class="tabTypeCol2">
        <ul>
            <li class="current">
                <a href="#">아이디 찾기</a>
            </li>
            <li>
                <a href="<c:url value='findPassword.jsp' />">비밀번호 찾기</a>
            </li>
        </ul>
    </div>

    <div class="confirmWrap">
        <p>아이디를 잊으셨습니까?</p>
        <p>인증 방법 중 한가지를 선택하여 찾으실 수 있습니다.</p>
    </div>

    <div class="memType03">
        <a href="#" class="layerBtn" data-layer="layerHp">
            <span class="icon">
                <img src="http://localhost:8081/hospital_reservation/resources/images/login_phone.png">
            </span>
            <span class="methodSub">가입정보</span>
            <span class="methodTitle">휴대전화 이용하기</span>
        </a>

        <a href="#" class="layerBtn" data-layer="layerMail">
            <span class="icon">
                <img src="http://localhost:8081/hospital_reservation/resources/images/login-email.png">
            </span>
            <span class="methodSub">가입정보</span>
            <span class="methodTitle">이메일 이용하기</span>
        </a>
    </div>
</main>

<div class="layerDim"></div>

<section class="layerWrap layerHp">
    <h1>회원정보 휴대전화번호 확인</h1>

    <div class="layerContent">
        <form id="hForm" name="hForm"
              action="<c:url value='process/findIdProcess.jsp' />"
              method="post">

            <input type="hidden" name="findType" value="tel">

            <fieldset>
                <legend>휴대전화번호 확인</legend>

                <div class="boardTypeForm">
                    <table>
                        <tbody>
                            <tr>
                                <th>이름</th>
                                <td>
                                    <input id="hpName" name="name" class="inputText" type="text" maxlength="20">
                                </td>
                            </tr>
                            <tr>
                                <th>휴대전화번호</th>
                                <td>
                                    <input id="hpPhoneNumber" name="phoneNumber" class="inputText" type="text" maxlength="40">
                                </td>
                            </tr>
                            <tr>
                                <th>생년월일</th>
                                <td>
                                    <input id="hpBirthDate" name="birthDate" class="inputText" type="text" maxlength="10">
                                    <p class="desc">예) 1970-01-01</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </fieldset>
        </form>

        <div class="btnWrap">
            <button type="button" class="btnType03" id="confirmHpBtn">확인하기</button>
        </div>
    </div>

    <button type="button" class="layerCloseBtn">×</button>
</section>

<section class="layerWrap layerMail">
    <h1>회원정보 이메일 확인</h1>

    <div class="layerContent">
        <form id="mForm" name="mForm"
              action="<c:url value='process/findIdProcess.jsp' />"
              method="post">

            <input type="hidden" name="findType" value="email">

            <fieldset>
                <legend>이메일 확인</legend>

                <div class="boardTypeForm">
                    <table>
                        <tbody>
                            <tr>
                                <th>이름</th>
                                <td>
                                    <input id="mailName" name="name" class="inputText" type="text" maxlength="20">
                                </td>
                            </tr>
                            <tr>
                                <th>이메일주소</th>
                                <td>
                                    <input id="mailEmail" name="email" class="inputText" type="text" maxlength="40">
                                </td>
                            </tr>
                            <tr>
                                <th>생년월일</th>
                                <td>
                                    <input id="mailBirthDate" name="birthDate" class="inputText" type="text" maxlength="10">
                                    <p class="desc">예) 1970-01-01</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </fieldset>
        </form>

        <div class="btnWrap">
            <button type="button" class="btnType03" id="confirmMailBtn">확인하기</button>
        </div>
    </div>

    <button type="button" class="layerCloseBtn">×</button>
</section>

<script src="<c:url value='/resources/js/user-layout.js' />"></script>
<jsp:include page="../common/userFooter.jsp" />

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!-- 아이디/비밀번호 찾기 전용 JS -->
<script src="<c:url value='/resources/js/find-account.js' />"></script>


</body>
</html>