<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KMCH 한국중앙병원</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css?v=20260623-menu-hover-guard">
</head>
<body>
    <%@ include file="/views/common/userHeader.jsp" %>

    <main class="main-container">
        <section class="main-visual">
            <div class="main-visual-inner">
                <p>환자 중심 진료 예약 서비스</p>
                <h2>빠르고 편리한 병원 예약</h2>
                <a href="<c:url value='/views/user/appointment/appointment.jsp' />" class="main-primary-link">진료 예약하기</a>
            </div>
        </section>

        <section class="main-board-grid" aria-label="병원 게시판">
            <article class="main-board-panel">
                <div class="main-board-title">
                    <h3>공지사항</h3>
                    <a href="<c:url value='/board/notice/list.do' />">더보기</a>
                </div>
                <ul class="main-board-list">
                    <c:forEach var="notice" items="${recentNoticeList}">
                        <li>
                            <c:url var="noticeDetailUrl" value="/board/detail.do">
                                <c:param name="postId" value="${notice.postId}" />
                            </c:url>
                            <a href="${noticeDetailUrl}">
                                <c:out value="${notice.title}" />
                            </a>
                            <span><c:out value="${notice.createdAt}" /></span>
                        </li>
                    </c:forEach>
                    <c:if test="${empty recentNoticeList}">
                        <li class="empty-row">등록된 공지사항이 없습니다.</li>
                    </c:if>
                </ul>
            </article>

            <article class="main-board-panel">
                <div class="main-board-title">
                    <h3>FAQ</h3>
                    <a href="<c:url value='/board/faq/list.do' />">더보기</a>
                </div>
                <ul class="main-board-list">
                    <c:forEach var="faq" items="${recentFaqList}">
                        <li>
                            <c:url var="faqDetailUrl" value="/board/detail.do">
                                <c:param name="postId" value="${faq.postId}" />
                            </c:url>
                            <a href="${faqDetailUrl}">
                                <c:out value="${faq.title}" />
                            </a>
                            <span><c:out value="${faq.createdAt}" /></span>
                        </li>
                    </c:forEach>
                    <c:if test="${empty recentFaqList}">
                        <li class="empty-row">등록된 FAQ가 없습니다.</li>
                    </c:if>
                </ul>
            </article>
        </section>
    </main>

    <%@ include file="/views/common/userFooter.jsp" %>

    <script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=20260623-menu-hover-guard"></script>
</body>
</html>
