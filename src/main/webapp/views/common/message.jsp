<%-- /WEB-INF/views/common/message.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp" %>

<c:set var="successMessage" value="${not empty requestScope.message ? requestScope.message : sessionScope.message}" />
<c:set var="failMessage" value="${not empty requestScope.errorMessage ? requestScope.errorMessage : sessionScope.errorMessage}" />

<c:if test="${not empty successMessage or not empty failMessage}">
    <div class="common-message-area" aria-live="polite">
        <c:if test="${not empty successMessage}">
            <div class="common-message success" role="status">
                <c:out value="${successMessage}" />
            </div>
        </c:if>

        <c:if test="${not empty failMessage}">
            <div class="common-message error" role="alert">
                <c:out value="${failMessage}" />
            </div>
        </c:if>
    </div>
</c:if>

<c:remove var="message" scope="session" />
<c:remove var="errorMessage" scope="session" />
