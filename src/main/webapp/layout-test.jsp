<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("activeMenu", "hospital");
    request.setAttribute("depth1", "공통 레이아웃");
    request.setAttribute("depth2", "사용자 화면 테스트");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공통 레이아웃 테스트</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css">
</head>
<body>
    <%@ include file="/views/common/userHeader.jsp" %>
    <%@ include file="/views/common/userBreadcrumb.jsp" %>

    <main class="user-container">
        <div class="page-title-area">
            <h2>공통 레이아웃 테스트</h2>
            <p>사용자 공통 헤더, breadcrumb, 콘텐츠 영역, footer를 확인하는 페이지입니다.</p>
        </div>

        <section class="content-card">
            <div class="search-area">
                <select aria-label="검색 구분">
                    <option>전체</option>
                    <option>진료과</option>
                    <option>의료진</option>
                </select>
                <input type="text" value="레이아웃 확인" aria-label="검색어">
                <button type="button">검색</button>
            </div>

            <table class="basic-table">
                <thead>
                    <tr>
                        <th>구분</th>
                        <th>확인 항목</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Header</td>
                        <td>로고, 메뉴, 로그인 영역</td>
                        <td>확인</td>
                    </tr>
                    <tr>
                        <td>Breadcrumb</td>
                        <td>현재 위치 표시 영역</td>
                        <td>확인</td>
                    </tr>
                    <tr>
                        <td>Footer</td>
                        <td>병원 정보 및 하단 영역</td>
                        <td>확인</td>
                    </tr>
                </tbody>
            </table>
        </section>
    </main>

    <%@ include file="/views/common/userFooter.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/js/user-layout.js"></script>
</body>
</html>
