<%-- /views/common/pagination.jsp --%>

<c:set var="pagingBaseUrl" value="${empty baseUrl ? pageContext.request.requestURI : baseUrl}" />
<c:set var="pagingParamName" value="${empty pageParamName ? 'currentPage' : pageParamName}" />
<c:set var="pagingQueryString" value="${empty paginationQueryString ? '' : paginationQueryString}" />

<c:set var="pagingCurrentPage" value="${not empty currentPage ? currentPage : pagination.currentPage}" />
<c:set var="pagingTotalPage" value="${not empty totalPage ? totalPage : pagination.totalPage}" />
<c:set var="pagingStartPage" value="${not empty startPage ? startPage : pagination.startPage}" />
<c:set var="pagingEndPage" value="${not empty endPage ? endPage : pagination.endPage}" />

<c:if test="${empty pagingCurrentPage}">
    <c:set var="pagingCurrentPage" value="1" />
</c:if>
<c:if test="${empty pagingTotalPage}">
    <c:set var="pagingTotalPage" value="1" />
</c:if>
<c:if test="${empty pagingStartPage}">
    <c:set var="pagingStartPage" value="1" />
</c:if>
<c:if test="${empty pagingEndPage}">
    <c:set var="pagingEndPage" value="${pagingTotalPage}" />
</c:if>

<c:if test="${pagingTotalPage > 1}">
    <nav class="common-pagination" aria-label="페이지 이동">
        <c:choose>
            <c:when test="${pagingCurrentPage > 1}">
                <c:url var="firstPageUrl" value="${pagingBaseUrl}">
                    <c:param name="${pagingParamName}" value="1" />
                </c:url>
                <c:url var="previousPageUrl" value="${pagingBaseUrl}">
                    <c:param name="${pagingParamName}" value="${pagingCurrentPage - 1}" />
                </c:url>
                <a href="${firstPageUrl}${pagingQueryString}" class="pagination-control" aria-label="첫 페이지">«</a>
                <a href="${previousPageUrl}${pagingQueryString}" class="pagination-control" aria-label="이전 페이지">‹</a>
            </c:when>
            <c:otherwise>
                <span class="pagination-control disabled" aria-hidden="true">«</span>
                <span class="pagination-control disabled" aria-hidden="true">‹</span>
            </c:otherwise>
        </c:choose>

        <c:forEach var="pageNo" begin="${pagingStartPage}" end="${pagingEndPage}">
            <c:choose>
                <c:when test="${pageNo == pagingCurrentPage}">
                    <span class="pagination-page active" aria-current="page">${pageNo}</span>
                </c:when>
                <c:otherwise>
                    <c:url var="pageUrl" value="${pagingBaseUrl}">
                        <c:param name="${pagingParamName}" value="${pageNo}" />
                    </c:url>
                    <a href="${pageUrl}${pagingQueryString}" class="pagination-page">${pageNo}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${pagingCurrentPage < pagingTotalPage}">
                <c:url var="nextPageUrl" value="${pagingBaseUrl}">
                    <c:param name="${pagingParamName}" value="${pagingCurrentPage + 1}" />
                </c:url>
                <c:url var="lastPageUrl" value="${pagingBaseUrl}">
                    <c:param name="${pagingParamName}" value="${pagingTotalPage}" />
                </c:url>
                <a href="${nextPageUrl}${pagingQueryString}" class="pagination-control" aria-label="다음 페이지">›</a>
                <a href="${lastPageUrl}${pagingQueryString}" class="pagination-control" aria-label="마지막 페이지">»</a>
            </c:when>
            <c:otherwise>
                <span class="pagination-control disabled" aria-hidden="true">›</span>
                <span class="pagination-control disabled" aria-hidden="true">»</span>
            </c:otherwise>
        </c:choose>
    </nav>
</c:if>
