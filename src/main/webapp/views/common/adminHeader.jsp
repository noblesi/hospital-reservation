<%-- /WEB-INF/views/common/adminHeader.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglib.jsp" %>

<header class="admin-header">
    <div class="admin-header-inner">
        <a href="<c:url value='/main.do' />" class="go-user-page">
            병원 홈페이지
        </a>

        <div class="admin-util">
            <span class="admin-name">
                ${empty sessionScope.loginAdmin ? '관리자' : sessionScope.loginAdmin.adminName}
            </span>
            <a href="<c:url value='/admin/logout.do' />" class="logout-btn">로그아웃</a>
        </div>
    </div>
</header>
