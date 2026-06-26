<%-- /WEB-INF/views/common/userHeader.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>

<header class="user-header">
    <div class="user-header-top">
        <div class="user-header-inner">
            <button type="button" class="menu-btn" aria-label="전체 메뉴">
                ☰
            </button>

            <h1 class="logo">
                <a href="<c:url value='/main.do' />">
                    KMCH 한국중앙병원
                </a>
            </h1>

            <div class="user-util">
                <c:choose>
                    <c:when test="${empty sessionScope.loginUser}">
                        <a href="<c:url value='/login.do' />">로그인</a>
                        <a href="<c:url value='/join.do' />">회원가입</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/mypage.do' />">마이페이지</a>
                        <a href="<c:url value='/logout.do' />">로그아웃</a>
                    </c:otherwise>
                </c:choose>

                <button type="button" class="search-btn" aria-label="검색">
                    🔍
                </button>
            </div>
        </div>
    </div>

    <nav class="user-gnb" aria-label="사용자 주요 메뉴">
        <ul>
            <li class="${activeMenu eq 'treatment' ? 'active' : ''}">
                <a href="<c:url value='/department/list.do' />">진료안내</a>
            </li>
            <li class="${activeMenu eq 'guide' ? 'active' : ''}">
                <a href="<c:url value='/reservation/main.do' />">이용안내</a>
            </li>
            <li class="${activeMenu eq 'hospital' ? 'active' : ''}">
                <a href="<c:url value='/hospital/intro.do' />">병원소개</a>
            </li>
        </ul>
    </nav>
</header>
