<%-- /WEB-INF/views/common/userBreadcrumb.jsp --%>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>

<div class="breadcrumb-bar">
    <div class="breadcrumb-inner">
        <a href="<c:url value='/main.do' />">홈</a>

        <c:if test="${not empty depth1}">
            <span class="breadcrumb-separator">›</span>
            <span>${depth1}</span>
        </c:if>

        <c:if test="${not empty depth2}">
            <span class="breadcrumb-separator">›</span>
            <strong>${depth2}</strong>
        </c:if>
    </div>
</div>
