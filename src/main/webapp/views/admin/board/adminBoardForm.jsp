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
                            <option value="NOTICE" ${(category eq 'NOTICE' or boardPost.category eq 'NOTICE') ? 'selected' : ''}>공지사항</option>
                            <option value="FAQ" ${(category eq 'FAQ' or boardPost.category eq 'FAQ') ? 'selected' : ''}>FAQ</option>
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

                    <div class="admin-form-row">
                        <label>
                            작성자 ID
                            <input type="text" name="writerId" value="${empty boardPost.writerId ? 'admin' : boardPost.writerId}">
                        </label>
                        <label>
                            작성자명
                            <input type="text" name="writerName" value="${empty boardPost.writerName ? '관리자' : boardPost.writerName}">
                        </label>
                    </div>

                    <div class="admin-form-row">
                        <label>
                            상단 고정
                            <select name="noticeYn">
                                <option value="N" ${boardPost.noticeYn ne 'Y' ? 'selected' : ''}>아니오</option>
                                <option value="Y" ${boardPost.noticeYn eq 'Y' ? 'selected' : ''}>예</option>
                            </select>
                        </label>
                        <label>
                            공개 여부
                            <select name="displayYn">
                                <option value="Y" ${boardPost.displayYn ne 'N' ? 'selected' : ''}>공개</option>
                                <option value="N" ${boardPost.displayYn eq 'N' ? 'selected' : ''}>비공개</option>
                            </select>
                        </label>
                    </div>

                    <div class="admin-form-actions">
                        <button type="submit">저장</button>
                        <c:choose>
                            <c:when test="${category eq 'FAQ' or boardPost.category eq 'FAQ'}">
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
