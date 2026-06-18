<%-- /views/common/adminSidebar.jsp --%>

<aside class="admin-sidebar">
    <nav aria-label="관리자 메뉴">
        <ul>
            <li class="${adminMenu eq 'dashboard' ? 'active' : ''}">
                <a href="<c:url value='/admin/dashboard.do' />">대시보드</a>
            </li>

            <li class="${adminMenu eq 'reservation' ? 'active' : ''}">
                <a href="<c:url value='/admin/reservation/list.do' />">예약 관리</a>
            </li>

            <li class="${adminMenu eq 'member' ? 'active' : ''}">
                <a href="<c:url value='/admin/member/list.do' />">회원 관리</a>
            </li>

            <li class="${adminMenu eq 'department' ? 'active' : ''}">
                <a href="<c:url value='/admin/department/list.do' />">진료과 관리</a>
            </li>

            <li class="${adminMenu eq 'doctor' ? 'active' : ''}">
                <a href="<c:url value='/admin/doctor/list.do' />">의료진 관리</a>
            </li>

            <li class="${adminMenu eq 'notice' ? 'active' : ''}">
                <a href="<c:url value='/admin/notice/list.do' />">공지사항 관리</a>
            </li>

            <li class="${adminMenu eq 'faq' ? 'active' : ''}">
                <a href="<c:url value='/admin/faq/list.do' />">FAQ 관리</a>
            </li>
        </ul>
    </nav>
</aside>
