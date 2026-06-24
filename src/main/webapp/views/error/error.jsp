<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/views/common/taglib.jsp" %>
<c:set var="statusCode" value="${requestScope['javax.servlet.error.status_code']}" />
<c:set var="requestUri" value="${requestScope['javax.servlet.error.request_uri']}" />
<c:if test="${empty statusCode}">
    <c:set var="statusCode" value="500" />
</c:if>

<c:choose>
    <c:when test="${statusCode eq 400}">
        <c:set var="errorTitle" value="잘못된 요청입니다." />
        <c:set var="errorMessage" value="요청 형식이 올바르지 않습니다. 입력한 주소나 값을 다시 확인해주세요." />
    </c:when>
    <c:when test="${statusCode eq 403}">
        <c:set var="errorTitle" value="접근할 수 없는 페이지입니다." />
        <c:set var="errorMessage" value="현재 계정으로는 이 페이지를 이용할 권한이 없습니다." />
    </c:when>
    <c:when test="${statusCode eq 404}">
        <c:set var="errorTitle" value="페이지를 찾을 수 없습니다." />
        <c:set var="errorMessage" value="주소가 변경되었거나 삭제된 페이지입니다." />
    </c:when>
    <c:otherwise>
        <c:set var="errorTitle" value="서비스 이용에 불편을 드려 죄송합니다." />
        <c:set var="errorMessage" value="일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요." />
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${statusCode}" /> 오류 | KMCH 한국중앙병원</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css' />">
</head>
<body>
    <main class="error-page" role="main">
        <section class="error-panel" aria-labelledby="errorTitle">
            <p class="error-code"><c:out value="${statusCode}" /></p>
            <h1 id="errorTitle"><c:out value="${errorTitle}" /></h1>
            <p class="error-message"><c:out value="${errorMessage}" /></p>

            <c:if test="${not empty requestUri}">
                <p class="error-path">
                    요청 경로
                    <span><c:out value="${requestUri}" /></span>
                </p>
            </c:if>

            <div class="error-actions">
                <a class="main-primary-link" href="<c:url value='/main.do' />">메인으로 이동</a>
                <button type="button" class="outline-link" onclick="history.back()">이전 페이지</button>
            </div>
        </section>
    </main>
</body>
</html>
