<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 회원 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css">
</head>
<body>

<%@ include file="/views/common/adminHeader.jsp" %>
<%@ include file="/views/common/adminSidebar.jsp" %>

<div class="admin-content">
    <h2>회원 목록</h2>

    <!-- 검색 영역 -->
    <form method="get" action="">
        <select name="searchType">
            <option value="">전체</option>
            <option value="loginId">아이디</option>
            <option value="memberName">회원명</option>
            <option value="email">이메일</option>
        </select>

        <input type="text" name="searchKeyword" placeholder="검색어 입력">

        <select name="status">
            <option value="">전체 상태</option>
            <option value="ACTIVE">정상</option>
            <option value="INACTIVE">비활성</option>
        </select>

        <button type="submit">검색</button>
    </form>

    <br>

    <!-- 목록 테이블 -->
    <table border="1" width="100%">
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
    <tr>
        <td>1001</td>
        <td>hong01</td>
        <td>
       <a href="${pageContext.request.contextPath}/admin/member/detail?memberNo=1001">홍길동</a>

        
        </td>
        <td>hong@test.com</td>
        <td>010-1111-2222</td>
        <td>ACTIVE</td>
        <td>2026-06-18</td>
    </tr>
</tbody>

    </table>
</div>

</body>
</html>
