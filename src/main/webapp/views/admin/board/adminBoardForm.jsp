<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css">
</head>
<body>
    <%@ include file="/views/common/adminHeader.jsp" %>

    <div class="admin-layout">
        <%@ include file="/views/common/adminSidebar.jsp" %>

        <main class="admin-content">
            <div class="admin-page-title">
                <h2>게시글 관리</h2>
                <p>공지사항과 FAQ에 노출할 내용을 작성합니다.</p>
            </div>

            <section class="admin-card">
                <form class="admin-form" action="<c:url value='/admin/board/save.do' />" method="post">
                    <input type="hidden" name="postId" value="${boardPost.postId}">

                    <label>
                        게시판
                        <select name="category">
                            <option value="N" ${(category eq 'N' or boardPost.category eq 'N') ? 'selected' : ''}>공지사항</option>
                            <option value="F" ${(category eq 'F' or boardPost.category eq 'F') ? 'selected' : ''}>FAQ</option>
                        </select>
                    </label>

                    <label>
                        제목
                        <input type="text" name="title" value="${boardPost.title}" required>
                    </label>

                    <label>
                        내용
                        <textarea name="content" rows="12" required>${boardPost.content}</textarea>
                    </label>

                    <div class="admin-form-actions">
                        <button type="submit">저장</button>
                        <c:choose>
                            <c:when test="${category eq 'F' or boardPost.category eq 'F'}">
                                <a href="<c:url value='/admin/faq/list.do' />">취소</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/admin/notice/list.do' />">취소</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </form>
            </section>
        </main>
    </div>
</body>
</html>
