<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<%
    if (request.getAttribute("recentNoticeList") == null || request.getAttribute("recentFaqList") == null) {
        response.sendRedirect(request.getContextPath() + "/main.do");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KMCH 한국중앙병원</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css?v=${initParam.assetVersion}">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bxslider/4.2.17/jquery.bxslider.min.css">
    <style>
        /* =========================
           Main Visual
        ========================= */
        .main-visual{
            width:1200px;
            max-width:var(--user-page-width);
            margin:30px auto 0;
            border-bottom:0;
            background:none;
        }

        .main-visual .bx-wrapper{
            margin:0;
            border:0;
            box-shadow:none;
            background:transparent;
        }

        .main-visual .bxslider{
            margin:0;
            padding:0;
        }

        .main-visual img{
            width:1200px;
            max-width:100%;
            height:500px;
            display:block;
            object-fit:cover;
        }

        /* =========================
           Quick Menu
        ========================= */
        .quick-menu{
            width:1200px;
            max-width:var(--user-page-width);
            margin:70px auto 0;
            display:flex;
            justify-content:space-between;
            gap:20px;
        }

        .quick-card{
            display:block;
            flex:1 1 0;
            height:300px;
            border:1px solid #ddd;
            border-radius:20px;
            overflow:hidden;
            background:#fff;
            color:inherit;
            text-decoration:none;
        }

        .quick-icon{
            width:100%;
            height:180px;
            background:#f3f6fb;
        }

        .quick-card h3{
            text-align:center;
            margin-top:15px;
        }

        .main-board-grid{
            width:1200px;
            max-width:var(--user-page-width);
        }

        @media (max-width: 900px) {
            .quick-menu{
                flex-wrap:wrap;
            }

            .quick-card{
                width:calc(50% - 10px);
                flex:none;
            }
        }

        @media (max-width: 640px) {
            .main-visual img{
                height:320px;
            }

            .quick-card{
                width:100%;
            }
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <!-- bxSlider JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bxslider/4.2.17/jquery.bxslider.min.js"></script>

    <script>
    $(function(){
        $(".bxslider").bxSlider({
            auto:true,
            pager:true,
            controls:true
        });
    });
    </script>
</head>
<body>
    <%@ include file="/views/common/userHeader.jsp" %>

    <main class="main-container">
        <!-- 메인 슬라이드 -->
        <section class="main-visual">
            <ul class="bxslider">
                <li>
                    <img src="<c:url value='/resources/images/common/banner_exam.png' />" alt="메인배너1">
                </li>
                <li>
                    <img src="<c:url value='/resources/images/common/banner_exam.png' />" alt="메인배너2">
                </li>
                <li>
                    <img src="<c:url value='/resources/images/common/banner_exam.png' />" alt="메인배너3">
                </li>
            </ul>
        </section>

        <!-- 퀵메뉴 -->
        <section class="quick-menu">
            <a class="quick-card" href="<c:url value='/appointment/reserve.do' />">
                <div class="quick-icon">
                   	<img src="<c:url value='/resources/images/common/btn1.png' />" alt="진료예약">
           		</div>
                <h3 class="quick-title">진료예약</h3>
            </a>

            <a class="quick-card" href="<c:url value='/appointment/list.do' />">
                <div class="quick-icon">
                	<img src="<c:url value='/resources/images/common/btn2.png' />" alt="예약조회">
                </div>
                <h3 class="quick-title">예약조회</h3>
            </a>

            <a class="quick-card" href="<c:url value='/department/departmentList.do' />">
                <div class="quick-icon">
               		<img src="<c:url value='/resources/images/common/btn3.png' />" alt="진료과목">
                </div>
                <h3 class="quick-title">진료과목</h3>
            </a>

            <a class="quick-card" href="<c:url value='/views/user/hospital/location.jsp' />">
                <div class="quick-icon">
               		<img src="<c:url value='/resources/images/common/btn4.png' />" alt="오시는길">
                </div>
                <h3 class="quick-title">오시는길</h3>
            </a>
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
                            <span><fmt:formatDate value="${notice.createdAt}" pattern="yyyy-MM-dd HH:mm" /></span>
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
                            <span><fmt:formatDate value="${faq.createdAt}" pattern="yyyy-MM-dd HH:mm" /></span>
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

    <script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>
</html>
