<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${searchDTO.categoryName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css?v=20260623-menu-hover-guard">
</head>
<body>
    <%@ include file="/views/common/userHeader.jsp" %>
    <%@ include file="/views/common/userBreadcrumb.jsp" %>

    <main class="user-container">
        <div class="page-title-area">
            <h2><c:out value="${searchDTO.categoryName}" /></h2>
            <p>병원에서 전달하는 주요 안내와 자주 묻는 질문을 확인할 수 있습니다.</p>
        </div>

        <%@ include file="/views/common/message.jsp" %>

        <section class="content-card">
            <form class="search-area" action="<c:url value='${baseUrl}' />" method="get">
                <select name="searchType" aria-label="검색 구분">
                    <option value="titleContent" ${searchDTO.searchType eq 'titleContent' ? 'selected' : ''}>제목+내용</option>
                    <option value="title" ${searchDTO.searchType eq 'title' ? 'selected' : ''}>제목</option>
                    <option value="content" ${searchDTO.searchType eq 'content' ? 'selected' : ''}>내용</option>
                </select>
                <input type="text" name="keyword" value="${searchDTO.keyword}" placeholder="검색어를 입력하세요" aria-label="검색어">
                <button type="submit">검색</button>
            </form>

            <table class="basic-table board-table">
                <thead>
                    <tr>
                        <th class="col-no">번호</th>
                        <th>제목</th>
                        <th class="col-writer">작성자</th>
                        <th class="col-date">등록일</th>
                        <th class="col-hit">조회</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="boardPost" items="${boardPostList}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${boardPost.notice}">공지</c:when>
                                    <c:otherwise><c:out value="${boardPost.postId}" /></c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-left">
                                <c:url var="boardDetailUrl" value="/board/detail.do">
                                    <c:param name="postId" value="${boardPost.postId}" />
                                </c:url>
                                <a href="${boardDetailUrl}">
                                    <c:out value="${boardPost.title}" />
                                </a>
                            </td>
                            <td><c:out value="${boardPost.writerName}" /></td>
                            <td><c:out value="${boardPost.createdAt}" /></td>
                            <td><c:out value="${boardPost.viewCount}" /></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty boardPostList}">
                        <tr>
                            <td colspan="5" class="empty-cell">등록된 게시글이 없습니다.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>

            <%@ include file="/views/common/pagination.jsp" %>
        </section>
    </main>

    <%@ include file="/views/common/userFooter.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=20260623-menu-hover-guard"></script>
</body>
</html>
