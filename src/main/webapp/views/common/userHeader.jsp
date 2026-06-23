<%-- /views/common/userHeader.jsp --%>

<header class="user-header">
    <div class="user-header-top">
        <div class="user-header-inner">
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

                <button type="button" class="search-btn" aria-label="검색" aria-controls="userSearchPanel" aria-expanded="false">
                    🔍
                </button>
            </div>
        </div>
    </div>

    <div id="userSearchPanel" class="user-search-panel" hidden>
        <form class="user-search-form" action="<c:url value='/search.do' />" method="get">
            <label for="userHeaderKeyword">통합검색</label>
            <input type="text" id="userHeaderKeyword" name="keyword" placeholder="검색어를 입력하세요">
            <button type="submit">검색</button>
            <button type="button" class="search-close-btn" aria-label="검색 닫기">×</button>
        </form>
    </div>

    <div class="user-menu-area">
        <div class="user-gnb-inner">
            <button type="button" class="menu-btn" aria-label="전체 메뉴" aria-controls="userAllMenu" aria-expanded="false">
                ☰
            </button>

            <nav class="user-gnb" aria-label="사용자 주요 메뉴">
                <ul>
                    <li class="${activeMenu eq 'treatment' ? 'active' : ''}">
                        <a href="<c:url value='/department/list.do' />">진료안내</a>
                        <div class="user-gnb-submenu">
                            <a href="<c:url value='/department/list.do' />">진료과 안내</a>
                            <a href="<c:url value='/doctor/list.do' />">의료진 소개</a>
                        </div>
                    </li>
                    <li class="${activeMenu eq 'guide' ? 'active' : ''}">
                        <a href="<c:url value='/reservation/main.do' />">이용안내</a>
                        <div class="user-gnb-submenu">
                            <a href="<c:url value='/reservation/main.do' />">진료 예약</a>
                            <a href="<c:url value='/reservation/history.do' />">예약 내역</a>
                        </div>
                    </li>
                    <li class="${activeMenu eq 'hospital' ? 'active' : ''}">
                        <a href="<c:url value='/hospital/intro.do' />">병원소개</a>
                        <div class="user-gnb-submenu">
                            <a href="<c:url value='/hospital/intro.do' />">병원 소개</a>
                            <a href="<c:url value='/hospital/location.do' />">오시는 길</a>
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
                        <a href="<c:url value='/department/list.do' />">진료과 안내</a>
                        <a href="<c:url value='/doctor/list.do' />">의료진 소개</a>
                    </section>
                    <section>
                        <a href="<c:url value='/reservation/main.do' />">진료 예약</a>
                        <a href="<c:url value='/reservation/history.do' />">예약 내역</a>
                    </section>
                    <section>
                        <a href="<c:url value='/hospital/intro.do' />">병원 소개</a>
                        <a href="<c:url value='/hospital/location.do' />">오시는 길</a>
                        <a href="<c:url value='/board/notice/list.do' />">공지사항</a>
                        <a href="<c:url value='/board/faq/list.do' />">FAQ</a>
                    </section>
                </div>

                <button type="button" class="menu-close-btn" aria-label="전체 메뉴 닫기">×</button>
            </div>
        </div>
    </div>
</header>
