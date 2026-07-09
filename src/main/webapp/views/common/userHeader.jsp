<%-- /views/common/userHeader.jsp --%>
<%@ include file="/views/common/taglib.jsp" %>

<header class="user-header">
    <div class="user-header-top">
        <div class="user-header-inner">
            <h1 class="logo">
                <a href="<c:url value='/main.do' />">KMCH 한국중앙병원</a>
            </h1>

            <div class="user-util">
                <c:choose>
                    <c:when test="${empty sessionScope.loginUser}">
                        <a href="<c:url value='/views/member/login.jsp' />">로그인</a>
                        <a href="<c:url value='/views/member/joinType.jsp' />">회원가입</a>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/member/mypage.do' />">마이페이지</a>
                        <a href="<c:url value='/member/logout.do' />">로그아웃</a>
                    </c:otherwise>
                </c:choose>

                <button type="button" class="search-btn" aria-label="검색" aria-controls="userSearchPanel" aria-expanded="false">
                    <span aria-hidden="true">&#128269;</span>
                </button>
            </div>
        </div>
    </div>

    <div id="userSearchPanel" class="user-search-panel" hidden>
        <form class="user-search-form" action="<c:url value='/board/notice/list.do' />" method="get">
            <label for="userHeaderKeyword">통합검색</label>
            <input type="text" id="userHeaderKeyword" name="keyword" placeholder="검색어를 입력하세요">
            <button type="submit">검색</button>
            <button type="button" class="search-close-btn" aria-label="검색 닫기">
                <span aria-hidden="true">&times;</span>
            </button>
        </form>
    </div>

    <div class="user-menu-area">
        <div class="user-gnb-inner">
            <button type="button" class="menu-btn" aria-label="전체 메뉴" aria-controls="userAllMenu" aria-expanded="false">
                <span aria-hidden="true">&#9776;</span>
            </button>

            <nav class="user-gnb" aria-label="사용자 주요 메뉴">
                <ul>
                    <li class="${activeMenu eq 'treatment' ? 'active' : ''}">
                        <a href="<c:url value='/department/departmentList.do' />">의료진소개</a>
                    </li>
                    <li class="${activeMenu eq 'guide' ? 'active' : ''}">
                        <a href="<c:url value='/appointment/reserve.do' />">진료 예약</a>
                    </li>
                    <li class="${activeMenu eq 'hospital' ? 'active' : ''}">
                        <a href="<c:url value='/views/user/hospital/intro.jsp' />">병원소개</a>
                        <div class="user-gnb-submenu">
                            <a href="<c:url value='/views/user/hospital/intro.jsp' />">병원 소개</a>
                            <a href="<c:url value='/views/user/hospital/location.jsp' />">오시는 길</a>
                            <a href="<c:url value='/board/notice/list.do' />">공지사항</a>
                            <a href="<c:url value='/board/faq/list.do' />">FAQ</a>
                        </div>
                    </li>
                </ul>
            </nav>
        </div>

        <div id="userAllMenu" class="user-all-menu" hidden>
            <div class="user-all-menu-inner">
                <strong class="user-all-menu-label">전체 메뉴</strong>

                <div class="user-all-menu-grid">
                    <section>
                        <a href="<c:url value='/department/departmentList.do' />">의료진소개</a>
                    </section>
                    <section>
                        <a href="<c:url value='/appointment/reserve.do' />">진료 예약</a>
                    </section>
                    <section>
                        <a href="<c:url value='/views/user/hospital/intro.jsp' />">병원 소개</a>
                        <a href="<c:url value='/views/user/hospital/location.jsp' />">오시는 길</a>
                        <a href="<c:url value='/board/notice/list.do' />">공지사항</a>
                        <a href="<c:url value='/board/faq/list.do' />">FAQ</a>
                    </section>
                </div>

                <button type="button" class="menu-close-btn" aria-label="전체 메뉴 닫기">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
    </div>
</header>
