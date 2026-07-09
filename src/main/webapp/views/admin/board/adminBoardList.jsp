<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${searchDTO.categoryName} 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css?v=${initParam.assetVersion}">
</head>
<body>
    <%@ include file="/views/common/adminHeader.jsp" %>

    <div class="admin-layout">
        <%@ include file="/views/common/adminSidebar.jsp" %>

        <main class="admin-content">
            <div class="admin-page-title">
                <h2><c:out value="${searchDTO.categoryName}" /> 관리</h2>
                <p>사용자 화면에 노출할 게시글을 등록하고 공개 상태를 관리합니다.</p>
            </div>

            <%@ include file="/views/common/message.jsp" %>

            <section class="admin-card">
                <form class="admin-search-area" action="<c:url value='${baseUrl}' />" method="get">
                    <label>
                        검색
                        <select name="searchType">
                            <option value="titleContent" ${searchDTO.searchType eq 'titleContent' ? 'selected' : ''}>제목+내용</option>
                            <option value="title" ${searchDTO.searchType eq 'title' ? 'selected' : ''}>제목</option>
                            <option value="content" ${searchDTO.searchType eq 'content' ? 'selected' : ''}>내용</option>
                        </select>
                    </label>
                    <input type="text" name="keyword" value="${searchDTO.keyword}" placeholder="검색어">
                    <button type="submit">검색</button>
                    <c:url var="boardFormCreateUrl" value="/admin/board/form.do">
                        <c:param name="category" value="${searchDTO.category}" />
                    </c:url>
                    <a href="${boardFormCreateUrl}" class="admin-action-link">등록</a>
                </form>

                <table class="admin-table board-table">
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>등록일</th>
                            <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="boardPost" items="${boardPostList}">
                            <tr>
                                <td>${boardPost.postId}</td>
                                <td class="text-left"><c:out value="${boardPost.title}" /></td>
                                <td><c:out value="${boardPost.writerName}" /></td>
                                <td><fmt:formatDate value="${boardPost.createdAt}" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td class="admin-table-actions">
                                    <c:url var="boardFormEditUrl" value="/admin/board/form.do">
                                        <c:param name="postId" value="${boardPost.postId}" />
                                    </c:url>
                                    <a href="${boardFormEditUrl}">수정</a>
                                    <form action="<c:url value='/admin/board/delete.do' />" method="post">
                                        <input type="hidden" name="postId" value="${boardPost.postId}">
                                        <button type="submit">삭제</button>
                                    </form>
                                </td>
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
    </div>
</body>
</html>
