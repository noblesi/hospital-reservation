<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 회원 상세</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css?v=20260623-admin-fluid">
</head>
<body>
    <%@ include file="/views/common/adminHeader.jsp" %>

    <div class="admin-layout">
        <%@ include file="/views/common/adminSidebar.jsp" %>

        <main class="admin-content">
            <div class="admin-page-title">
                <h2>회원 상세</h2>
                <p>회원 기본 정보와 관리자 메모를 확인합니다.</p>
            </div>

            <section class="admin-card">
                <table class="admin-table">
                    <tbody>
                        <tr>
                            <th>회원번호</th>
                            <td><c:out value="${member.patientNo}" /></td>
                        </tr>
                        <tr>
                            <th>아이디</th>
                            <td><c:out value="${member.loginId}" /></td>
                        </tr>
                        <tr>
                            <th>회원명</th>
                            <td><c:out value="${member.name}" /></td>
                        </tr>
                        <tr>
                            <th>이메일</th>
                            <td><c:out value="${member.email}" /></td>
                        </tr>
                        <tr>
                            <th>전화번호</th>
                            <td><c:out value="${member.phoneNumber}" /></td>
                        </tr>
                        <tr>
                            <th>상태</th>
                            <td>
                                <c:choose>
                                    <c:when test="${member.isWithdrawnYn eq 'Y'}">탈퇴</c:when>
                                    <c:otherwise>정상</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>가입일</th>
                            <td><c:out value="${member.registeredAt}" /></td>
                        </tr>
                    </tbody>
                </table>

                <h3>회원 메모</h3>

                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>메모번호</th>
                            <th>작성자</th>
                            <th>메모내용</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="memo" items="${memoList}">
                            <tr>
                                <td><c:out value="${memo.memoNo}" /></td>
                                <td><c:out value="${memo.adminId}" /></td>
                                <td class="text-left"><c:out value="${memo.memoContent}" /></td>
                                <td><c:out value="${memo.createDate}" /></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty memoList}">
                            <tr>
                                <td colspan="4" class="empty-cell">등록된 메모가 없습니다.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>

                <div class="admin-table-actions">
                    <a href="<c:url value='/admin/member/list.do' />">목록</a>
                </div>
            </section>
        </main>
    </div>

</body>
</html>
