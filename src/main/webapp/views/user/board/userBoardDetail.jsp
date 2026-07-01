<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${boardPost.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css?v=${initParam.assetVersion}">
</head>
<body>
    <%@ include file="/views/common/userHeader.jsp" %>
    <%@ include file="/views/common/userBreadcrumb.jsp" %>

    <main class="user-container">
        <div class="page-title-area">
            <h2>
                <c:choose>
                    <c:when test="${boardPost.category eq 'F'}">FAQ</c:when>
                    <c:otherwise>공지사항</c:otherwise>
                </c:choose>
            </h2>
            <p>게시글 상세 내용을 확인합니다.</p>
        </div>

        <article class="content-card board-detail">
            <header class="board-detail-header">
                <h3><c:out value="${boardPost.title}" /></h3>
                <div class="board-detail-meta">
                    <span>작성자 <c:out value="${boardPost.writerName}" /></span>
                    <span>등록일 <c:out value="${boardPost.createdAt}" /></span>
                    <span>조회 <c:out value="${boardPost.viewCount}" /></span>
                </div>
            </header>

            <div class="board-detail-content">
                <c:out value="${boardPost.content}" />
            </div>

            <div class="board-actions">
                <c:choose>
                    <c:when test="${boardPost.category eq 'F'}">
                        <a href="<c:url value='/board/faq/list.do' />" class="outline-link">목록</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/board/notice/list.do' />" class="outline-link">목록</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </article>
    </main>

    <%@ include file="/views/common/userFooter.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>
</html>
