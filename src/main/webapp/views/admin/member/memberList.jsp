<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 회원 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css">
</head>
<body>

<%@ include file="/views/common/adminHeader.jsp" %>

<div class="admin-layout">
    <%@ include file="/views/common/adminSidebar.jsp" %>

    <main class="admin-content">
        <div class="admin-page-title">
            <h2>회원 목록</h2>
            <p>회원 정보와 상태를 검색하고 상세 화면으로 이동합니다.</p>
        </div>

        <section class="admin-card">
            <form class="admin-search-area" method="get" action="">
                <label>
                    검색
                    <select name="searchType">
                        <option value="">전체</option>
                        <option value="loginId">아이디</option>
                        <option value="memberName">회원명</option>
                        <option value="email">이메일</option>
                    </select>
                </label>

                <input type="text" name="searchKeyword" placeholder="검색어 입력">

                <label>
                    상태
                    <select name="status">
                        <option value="">전체 상태</option>
                        <option value="ACTIVE">정상</option>
                        <option value="INACTIVE">비활성</option>
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
                    <tr>
                        <td>1001</td>
                        <td>hong01</td>
                        <td>
                            <button type="button"
                                    class="link-btn"
                                    onclick="window.location.href='${pageContext.request.contextPath}/admin/member/detail?memberNo=1001'">
                                홍길동-테스트
                            </button>
                        </td>
                        <td>hong@test.com</td>
                        <td>010-1111-2222</td>
                        <td>ACTIVE</td>
                        <td>2026-06-18</td>
                    </tr>
                </tbody>
            </table>
        </section>
    </main>
</div>

</body>
</html>
