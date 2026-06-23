<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hospital.common.MemberDTO"%>
<%@ page import="com.hospital.common.MinorMemberDTO"%>
<%@ page import="com.hospital.member.UpdateUserInfoService"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.util.Calendar"%>

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
MinorMemberDTO minorInfo = null;

if(userInfo != null && "Y".equalsIgnoreCase(userInfo.getHasMinorMemberYn())){
	minorInfo = service.searchMinorUserInfo(userInfo.getPatientNo());
}

pageContext.setAttribute("userInfo", userInfo);
pageContext.setAttribute("minorInfo", minorInfo);

if(userInfo != null && userInfo.getBirthDate() != null){
	LocalDate memberBirthDate = userInfo.getBirthDate().toLocalDate();
	pageContext.setAttribute("memberBirthYear", String.valueOf(memberBirthDate.getYear()));
	pageContext.setAttribute("memberBirthMonth", String.format("%02d", memberBirthDate.getMonthValue()));
	pageContext.setAttribute("memberBirthDay", String.format("%02d", memberBirthDate.getDayOfMonth()));
}

if(minorInfo != null && minorInfo.getMinorBirthDate() != null){
	LocalDate minorBirthDate = minorInfo.getMinorBirthDate().toLocalDate();
	pageContext.setAttribute("minorBirthYear", String.valueOf(minorBirthDate.getYear()));
	pageContext.setAttribute("minorBirthMonth", String.format("%02d", minorBirthDate.getMonthValue()));
	pageContext.setAttribute("minorBirthDay", String.format("%02d", minorBirthDate.getDayOfMonth()));
}
%>

<c:set var="activeMenu" value="mypage" scope="request" />
<c:set var="depth1" value="마이페이지" scope="request" />
<c:set var="depth2" value="내 정보 관리" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>내 정보 관리</title>
<link rel="stylesheet" href="<c:url value='/resources/css/sideBar.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=20260623-menu-hover-guard' />">
<link rel="stylesheet" href="<c:url value='/resources/css/mypage.css' />">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
<jsp:include page="../common/userHeader.jsp" />
<jsp:include page="../common/userBreadcrumb.jsp" />

<%-- 회원 정보 관리 본문 --%>
<main class="infoLayout">
    <jsp:include page="userSideGuide2.jsp" />

    <section class="infoContent">
        <%-- 페이지 제목 영역 --%>
        <div class="infoHeading">
            <h2>내 정보 관리</h2>
            <p>회원님의 정보를 확인하고 수정할 수 있습니다.</p>
        </div>

        <%-- 일반회원 또는 보호자회원 본인 정보 변경 영역 --%>
        <form action="process/updateUserInfoProcess.jsp" method="post">
            <input type="hidden" name="actionType" value="member">
            <div class="infoSection">
                <h3>${userInfo.hasMinorMemberYn eq 'Y' ? '보호자회원 정보 변경' : '일반회원 정보 변경'}</h3>
                <div class="infoRow">
                    <label>회원 유형</label>
                    <div>
                        <span class="memberType">
                            ${userInfo.hasMinorMemberYn eq 'Y' ? '보호자회원' : '일반회원'}
                        </span>
                    </div>
                </div>
                <div class="infoRow">
                    <label>이름</label>
                    <input type="text" value="${userInfo.name}" readonly>
                </div>
                <div class="infoRow">
                    <label>아이디</label>
                    <input type="text" value="${userInfo.loginId}" readonly>
                </div>
                <div class="infoRow">
                    <label for="memberBirthYear">생년월일</label>
                    <div>
                        <select class="birthSelect" id="memberBirthYear" name="memberBirthYear" required>
                            <option value="">연도</option>
                            <%
                            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                            for(int year = currentYear; year >= 1920; year--){
                            %>
                                <option value="<%= year %>"
                                    <%= String.valueOf(year).equals(pageContext.getAttribute("memberBirthYear")) ? "selected" : "" %>>
                                    <%= year %>
                                </option>
                            <% } %>
                        </select>
                        <span class="birthSeparator">-</span>
                        <select class="birthSelect" id="memberBirthMonth" name="memberBirthMonth" required>
                            <option value="">월</option>
                            <%
                            for(int month = 1; month <= 12; month++){
                            	String memberMonthValue = month < 10 ? "0" + month : String.valueOf(month);
                            %>
                                <option value="<%= memberMonthValue %>"
                                    <%= memberMonthValue.equals(pageContext.getAttribute("memberBirthMonth")) ? "selected" : "" %>>
                                    <%= memberMonthValue %>
                                </option>
                            <% } %>
                        </select>
                        <span class="birthSeparator">-</span>
                        <select class="birthSelect" id="memberBirthDay" name="memberBirthDay"
                                data-selected-day="${memberBirthDay}" required>
                            <option value="">일</option>
                        </select>
                    </div>
                </div>
                <div class="infoRow">
                    <label for="phoneNumber">휴대전화</label>
                    <input class="mediumInput"
                           id="phoneNumber"
                           name="phoneNumber"
                           value="${userInfo.phoneNumber}"
                           required>
                </div>
                <div class="infoRow">
                    <label for="email">이메일</label>
                    <input class="mediumInput"
                           type="email"
                           id="email"
                           name="email"
                           value="${userInfo.email}"
                           required>
                </div>
                <div class="infoRow">
                    <label for="zipCode">우편번호</label>
                    <div>
                        <input class="mediumInput" id="zipCode" name="zipCode" value="${userInfo.zipCode}" readonly required>
                        <button type="button" class="addressButton" id="addressSearchButton">우편번호 찾기</button>
                    </div>
                </div>
                <div class="infoRow">
                    <label for="address">주소</label>
                    <input class="wideInput"
                           id="address"
                           name="address"
                           value="${userInfo.address}"
                           readonly
                           required>
                </div>
                <div class="infoRow">
                    <label for="addressDetail">상세주소</label>
                    <input class="wideInput"
                           id="addressDetail"
                           name="addressDetail"
                           value="${userInfo.addressDetail}">
                </div>
                <div class="infoButtons">
                    <a class="cancelInfoBtn" href="myPage.jsp">취소</a>
                    <button class="saveInfoBtn" type="submit">저장하기</button>
                </div>
            </div>
        </form>

        <%-- 보호자회원에게만 노출되는 미성년자 정보 변경 영역 --%>
        <c:if test="${userInfo.hasMinorMemberYn eq 'Y'}">
            <form action="process/updateUserInfoProcess.jsp" method="post">
                <input type="hidden" name="actionType" value="minor">
                <div class="infoSection">
                    <h3>미성년자 회원 정보 변경</h3>
                    <p class="minorNotice">등록된 미성년자 회원의 정보를 수정할 수 있습니다.</p>
                    <div class="infoRow">
                        <label for="minorName">이름</label>
                        <input class="mediumInput" id="minorName" name="minorName" value="${minorInfo.minorName}" required>
                    </div>
                    <div class="infoRow">
                        <label for="minorBirthYear">생년월일</label>
                        <div>
                            <select class="birthSelect" id="minorBirthYear" name="minorBirthYear" required>
                                <option value="">연도</option>
                                <%
                                for(int year = currentYear; year >= 1920; year--){
                                %>
                                    <option value="<%= year %>"
                                        <%= String.valueOf(year).equals(pageContext.getAttribute("minorBirthYear")) ? "selected" : "" %>>
                                        <%= year %>
                                    </option>
                                <%
                                }
                                %>
                            </select>
                            <span class="birthSeparator">-</span>
                            <select class="birthSelect" id="minorBirthMonth" name="minorBirthMonth" required>
                                <option value="">월</option>
                                <%
                                for(int month = 1; month <= 12; month++){
                                	String monthValue = month < 10 ? "0" + month : String.valueOf(month);
                                %>
                                    <option value="<%= monthValue %>"
                                        <%= monthValue.equals(pageContext.getAttribute("minorBirthMonth")) ? "selected" : "" %>>
                                        <%= monthValue %>
                                    </option>
                                <%
                                }
                                %>
                            </select>
                            <span class="birthSeparator">-</span>
                            <select class="birthSelect"
                                    id="minorBirthDay"
                                    name="minorBirthDay"
                                    data-selected-day="${minorBirthDay}"
                                    required>
                                <option value="">일</option>
                            </select>
                        </div>
                    </div>
                    <div class="infoRow">
                        <label for="relationship">관계</label>
                        <select class="mediumInput" id="relationship" name="relationship" required>
                            <option value="부" ${minorInfo.relationship eq '부' or minorInfo.relationship eq '부모' ? 'selected' : ''}>부</option>
                            <option value="모" ${minorInfo.relationship eq '모' ? 'selected' : ''}>모</option>
                            <option value="기타" ${minorInfo.relationship eq '기타' or minorInfo.relationship eq 'etc' ? 'selected' : ''}>기타</option>
                        </select>
                    </div>
                    <div class="infoButtons">
                        <a class="cancelInfoBtn" href="myPage.jsp">취소</a>
                        <button class="saveInfoBtn" type="submit">저장하기</button>
                    </div>
                </div>
            </form>
        </c:if>
    </section>
</main>

<jsp:include page="../common/userFooter.jsp" />
<script src="<c:url value='/resources/js/user-layout.js?v=20260623-menu-hover-guard' />"></script>
<script src="<c:url value='/resources/js/mypage.js' />"></script>
</body>
</html>
