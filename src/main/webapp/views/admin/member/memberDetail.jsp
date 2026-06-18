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
    <p>전달받은 memberNo : ${param.memberNo}</p>

    <table border="1" width="100%">
        <tr>
            <th>회원번호</th>
            <td>${param.memberNo}</td>
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

    <br><br>

    <h3>회원 메모</h3>

    <!-- 메모 입력 영역 -->
    <form method="post" action="">
        <input type="hidden" name="memberNo" value="${param.memberNo}">

        <table border="1" width="100%">
            <tr>
                <th width="20%">메모 내용</th>
                <td>
                    <textarea name="memoContent" rows="4" cols="100"
                              placeholder="회원 관련 메모를 입력하세요."></textarea>
                </td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>
                    <input type="text" name="adminId" value="${sessionScope.loginAdminId}" readonly>
                </td>
            </tr>
        </table>

        <br>

        <div>
            <button type="submit">메모 등록</button>
        </div>
    </form>

    <br>

    <!-- 메모 목록 영역 -->
    <table border="1" width="100%">
        <thead>
            <tr>
                <th width="10%">메모번호</th>
                <th width="15%">작성자</th>
                <th width="45%">메모내용</th>
                <th width="20%">작성일</th>
                <th width="10%">삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty memoList}">
                    <c:forEach var="memo" items="${memoList}">
                        <tr>
                            <td>${memo.memoNo}</td>
                            <td>${memo.adminId}</td>
                            <td>${memo.memoContent}</td>
                            <td>${memo.createDate}</td>
                            <td>
                                <form method="post" action="" style="margin:0;">
                                    <input type="hidden" name="memoNo" value="${memo.memoNo}">
                                    <button type="submit">삭제</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="5">등록된 메모가 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <div>
        <button type="button" onclick="location.href='memberList.jsp'">목록으로</button>
    </div>
</div>

</body>
</html>

