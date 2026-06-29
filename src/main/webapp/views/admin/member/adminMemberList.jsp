<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 회원 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css">
</head>
<body>
    <%@ include file="/views/common/adminHeader.jsp" %>

    <div class="admin-layout">
        <%@ include file="/views/common/adminSidebar.jsp" %>

        <main class="admin-content">
            <div class="admin-page-title">
                <h2>회원 관리</h2>
                <p>회원번호, 아이디, 이름, 이메일을 기준으로 회원 목록을 확인합니다.</p>
            </div>

            <%@ include file="/views/common/message.jsp" %>

            <section class="admin-card">
                <form class="admin-search-area" action="<c:url value='${baseUrl}' />" method="get">
                    <label>
                        검색
                        <select name="searchType">
                            <option value="" ${empty searchDTO.searchType ? 'selected' : ''}>전체</option>
                            <option value="patientNo" ${searchDTO.searchType eq 'patientNo' ? 'selected' : ''}>회원번호</option>
                            <option value="loginId" ${searchDTO.searchType eq 'loginId' ? 'selected' : ''}>아이디</option>
                            <option value="memberName" ${searchDTO.searchType eq 'memberName' ? 'selected' : ''}>회원명</option>
                            <option value="email" ${searchDTO.searchType eq 'email' ? 'selected' : ''}>이메일</option>
                        </select>
                    </label>
                    <input type="text" name="searchKeyword" value="${searchDTO.searchKeyword}" placeholder="검색어">
                    <label>
                        상태
                        <select name="status">
                            <option value="" ${empty searchDTO.status ? 'selected' : ''}>전체</option>
                            <option value="N" ${searchDTO.status eq 'N' ? 'selected' : ''}>정상</option>
                            <option value="Y" ${searchDTO.status eq 'Y' ? 'selected' : ''}>탈퇴</option>
                        </select>
                    </label>
                    <button type="submit">검색</button>
                </form>

                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>회원번호</th>
                            <th>아이디</th>
                            <th>회원명</th>
                            <th>이메일</th>
                            <th>전화번호</th>
                            <th>상태</th>
                            <th>가입일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="member" items="${memberList}">
                            <tr>
                                <td><c:out value="${member.patientNo}" /></td>
                                <td><c:out value="${member.loginId}" /></td>
                                <td class="text-left">
                                    <c:url var="memberDetailUrl" value="/admin/member/detail">
                                        <c:param name="patientNo" value="${member.patientNo}" />
                                    </c:url>
                                    <a href="${memberDetailUrl}"><c:out value="${member.name}" /></a>
                                </td>
                                <td><c:out value="${member.email}" /></td>
                                <td><c:out value="${member.phoneNumber}" /></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${member.isWithdrawnYn eq 'Y'}">탈퇴</c:when>
                                        <c:otherwise>정상</c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:out value="${member.registeredAt}" /></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty memberList}">
                            <tr>
                                <td colspan="7" class="empty-cell">조회된 회원이 없습니다.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>

                <%@ include file="/views/common/pagination.jsp" %>
            </section>
        </main>
    </div>
</body>
</html>
