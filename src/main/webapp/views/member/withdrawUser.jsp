<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hospital.common.MemberDTO"%>
<%@ page import="com.hospital.member.UpdateUserInfoService"%>

<%
MemberDTO loginUser = (MemberDTO)session.getAttribute("loginUser");
Boolean verified = (Boolean)session.getAttribute("userInfoVerified");

if(loginUser == null){
    response.sendRedirect("login.jsp");
    return;
}

if(!Boolean.TRUE.equals(verified)){
    response.sendRedirect("myPage.jsp");
    return;
}

UpdateUserInfoService service = new UpdateUserInfoService();
MemberDTO userInfo = service.searchUserInfo(loginUser.getLoginId());
pageContext.setAttribute("userInfo", userInfo);

// 최종 확인 단계가 아닌 일반 진입에서는 이전 탈퇴 비밀번호 확인값을 폐기한다.
if(!"confirm".equals(request.getParameter("withdrawal"))){
    session.removeAttribute("withdrawalPasswordVerified");
}
%>

<c:set var="activeMenu" value="mypage" scope="request" />
<c:set var="depth1" value="마이페이지" scope="request" />
<c:set var="depth2" value="회원탈퇴" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>한국중앙병원</title>
<link rel="stylesheet" href="<c:url value='/resources/css/sideBar.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/mypage.css' />">
</head>
<body>
<jsp:include page="/views/common/userHeader.jsp" />
<jsp:include page="/views/common/userBreadcrumb.jsp" />

<%-- 회원 탈퇴 본문 --%>
<main class="infoLayout">
    <jsp:include page="/views/member/userSideGuide2.jsp" />

    <section class="infoContent">
        <div class="infoHeading">
            <h2>회원탈퇴</h2>
            <p>회원 탈퇴 시 모든 정보가 삭제되며, 복구가 불가능합니다.</p>
        </div>

        <%-- 탈퇴 전 안내 사항 --%>
        <div class="withdrawalNotice">
            <ul>
                <li>회원정보 삭제 후에는 기존 회원 정보로 서비스를 이용할 수 없습니다.</li>
                <li>진행 중인 예약이 있다면 탈퇴 전에 예약 상태를 확인해 주세요.</li>
            </ul>
        </div>

        <%-- 탈퇴 대상 회원 및 현재 비밀번호 확인 --%>
        <form action="process/checkWithdrawalPasswordProcess.jsp"
              method="post"
              class="withdrawalForm">
            <div class="withdrawalInfoBox">
                <div class="withdrawalInfoRow">
                    <strong>성명</strong>
                    <span>${userInfo.name}</span>
                </div>
                <div class="withdrawalInfoRow">
                    <strong>생년월일</strong>
                    <span>${userInfo.birthDate}</span>
                </div>
                <div class="withdrawalInfoRow">
                    <strong>아이디</strong>
                    <span>${userInfo.loginId}</span>
                </div>
                <div class="withdrawalInfoRow">
                    <label for="withdrawalPassword">현재 비밀번호</label>
                    <div class="withdrawalPasswordWrap">
                        <input type="password"
                               id="withdrawalPassword"
                               name="password"
                               placeholder="현재 비밀번호를 입력해주세요."
                               autocomplete="current-password"
                               required>
                        <button type="button"
                                class="withdrawalPasswordToggle"
                                aria-label="비밀번호 표시">보기</button>
                    </div>
                </div>
                <c:if test="${param.withdrawal eq 'fail'}">
                    <p class="withdrawalError">비밀번호가 일치하지 않습니다.</p>
                </c:if>
            </div>

            <div class="withdrawalButtons">
                <a href="myPageInfo.jsp" class="withdrawalCancelButton">취소</a>
                <button type="submit" class="withdrawalSubmitButton">회원탈퇴</button>
            </div>
        </form>
    </section>
</main>

<%-- 비밀번호 확인 성공 후 노출되는 최종 탈퇴 확인 모달 --%>
<div class="withdrawalConfirmModal"
     id="withdrawalConfirmModal"
     role="dialog"
     aria-modal="true"
     aria-labelledby="withdrawalConfirmTitle"
     aria-hidden="true"
     data-auto-open="${param.withdrawal eq 'confirm'}"
     data-cancel-url="<c:url value='/views/member/process/cancelWithdrawalProcess.jsp' />">
    <div class="withdrawalConfirmContent">
        <div class="withdrawalWarningIcon">!</div>
        <h3 id="withdrawalConfirmTitle">정말 탈퇴하시겠습니까?</h3>
        <p>탈퇴 시 모든 정보가 삭제되며, 복구할 수 없습니다.</p>

        <form action="process/withdrawUserProcess.jsp" method="post">
            <div class="withdrawalConfirmButtons">
                <button type="submit"
                        class="withdrawalModalCancel"
                        formaction="process/cancelWithdrawalProcess.jsp">취소</button>
                <button type="submit" class="withdrawalModalConfirm">탈퇴</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/views/common/userFooter.jsp" />
<script src="<c:url value='/resources/js/user-layout.js' />"></script>
<script src="<c:url value='/resources/js/mypage.js' />"></script>
</body>
</html>
