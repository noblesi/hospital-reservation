<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 회원 상세</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css">
</head>
<body>

<%@ include file="/views/common/adminHeader.jsp" %>
<%@ include file="/views/common/adminSidebar.jsp" %>

<div class="admin-content">
    <h2>회원 상세</h2>

    <!-- 회원 상세 정보 -->
    <table border="1" width="100%">
        <tr>
            <th>회원번호</th>
            <td>${member.memberNo}</td>
        </tr>
        <tr>
            <th>아이디</th>
            <td>${member.loginId}</td>
        </tr>
        <tr>
            <th>회원명</th>
            <td>${member.memberName}</td>
        </tr>
        <tr>
            <th>이메일</th>
            <td>${member.email}</td>
        </tr>
        <tr>
            <th>전화번호</th>
            <td>${member.tel}</td>
        </tr>
        <tr>
            <th>상태</th>
            <td>${member.status}</td>
        </tr>
        <tr>
            <th>가입일</th>
            <td>${member.createDate}</td>
        </tr>
    </table>

    <br>

    <!-- 메모 등록 영역 -->
    <h3>회원 메모</h3>

    <form method="post" action="">
        <input type="hidden" name="memberNo" value="${member.memberNo}">

        <table border="1" width="100%">
            <tr>
                <th>작성자</th>
                <td>
                    <input type="text" name="adminId" value="${sessionScope.loginAdminId}" readonly>
                </td>
            </tr>
            <tr>
                <th>메모 내용</th>
                <td>
                    <textarea name="memoContent" rows="4" cols="80"></textarea>
                </td>
            </tr>
        </table>

        <br>
        <button type="submit">메모 등록</button>
    </form>

    <br>

    <!-- 메모 목록 -->
    <table border="1" width="100%">
        <thead>
            <tr>
                <th>메모번호</th>
                <th>작성자</th>
                <th>메모내용</th>
                <th>작성일</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty memoList}">
                    <tr>
                        <td colspan="5">등록된 메모가 없습니다.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="memo" items="${memoList}">
                        <tr>
                            <td>${memo.memoNo}</td>
                            <td>${memo.adminId}</td>
                            <td>${memo.memoContent}</td>
                            <td>${memo.createDate}</td>
                            <td>
                                <form method="post" action="">
                                    <input type="hidden" name="memoNo" value="${memo.memoNo}">
                                    <button type="submit">삭제</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <br>

    <!-- 목록으로 버튼 -->
    <button type="button"
            onclick="location.href='${pageContext.request.contextPath}/views/admin/member/memberList.jsp'">
        목록으로
    </button>
</div>

</body>
</html>
