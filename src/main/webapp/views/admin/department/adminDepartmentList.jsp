<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>진료과 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css?v=${initParam.assetVersion}">
    <script>
        function openDepartmentForm(url) {
            window.open(url, "dept_modal", "width=560,height=620");
            return false;
        }
    </script>
</head>
<body>
    <%@ include file="/views/common/adminHeader.jsp" %>


    <div class="admin-layout">
        <%@ include file="/views/common/adminSidebar.jsp" %>

        <main class="admin-content">
            <div class="admin-page-title">
                <h2>진료과 관리</h2>
                <p>진료과명, 위치, 사용 여부를 기준으로 진료과 목록을 확인합니다.</p>
            </div>

            <%@ include file="/views/common/message.jsp" %>

            <section class="admin-card">
                <form class="admin-search-area" action="<c:url value='${baseUrl}' />" method="get">
                    <label>
                        검색
                        <select name="field">
                            <option value="all" ${searchDTO.field eq 'all' ? 'selected' : ''}>전체</option>
                            <option value="deptName" ${searchDTO.field eq 'deptName' ? 'selected' : ''}>진료과명</option>
                            <option value="description" ${searchDTO.field eq 'description' ? 'selected' : ''}>설명</option>
                            <option value="deptLoc" ${searchDTO.field eq 'deptLoc' ? 'selected' : ''}>위치</option>
                        </select>
                    </label>
                    <input type="text" name="keyword" value="${searchDTO.keyword}" placeholder="검색어">
                    <label>
                        사용 여부
                        <select name="isActiveYn">
                            <option value="" ${empty searchDTO.isActiveYn ? 'selected' : ''}>전체</option>
                            <option value="Y" ${searchDTO.isActiveYn eq 'Y' ? 'selected' : ''}>사용</option>
                            <option value="N" ${searchDTO.isActiveYn eq 'N' ? 'selected' : ''}>미사용</option>
                        </select>
                    </label>
                    <button type="submit">검색</button>
                    <c:url var="departmentCreateUrl" value="/admin/department/form.do">
                        <c:param name="modify" value="N" />
                    </c:url>
                    <a href="${departmentCreateUrl}" class="admin-action-link" onclick="return openDepartmentForm(this.href);">등록</a>
                </form>

                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>진료과 번호</th>
                            <th>진료과명</th>
                            <th>설명</th>
                            <th>위치</th>
                            <th>사용 여부</th>
                            <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="department" items="${departmentList}">
                            <c:url var="departmentModifyUrl" value="/admin/department/form.do">
                                <c:param name="modify" value="Y" />
                                <c:param name="deptNo" value="${department.deptNo}" />
                            </c:url>
                            <tr>
                                <td><c:out value="${department.deptNo}" /></td>
                                <td class="text-left">
                                    <a href="${departmentModifyUrl}" onclick="return openDepartmentForm(this.href);">
                                        <c:out value="${department.deptName}" />
                                    </a>
                                </td>
                                <td class="text-left"><c:out value="${department.description}" /></td>
                                <td><c:out value="${department.deptLoc}" /></td>
                                <td class="admin-table-actions">
                                    <form action="<c:url value='/admin/department/list.do' />" method="post">
                                        <input type="hidden" name="deptNo" value="${department.deptNo}">
                                        <input type="hidden" name="field" value="${searchDTO.field}">
                                        <input type="hidden" name="keyword" value="${searchDTO.keyword}">
                                        <input type="hidden" name="isActiveYn" value="${searchDTO.isActiveYn}">
                                        <input type="hidden" name="currentPage" value="${searchDTO.currentPage}">
                                        <select name="rowIsActiveYn" aria-label="진료과 사용 여부">
                                            <option value="Y" ${department.isActiveYn eq 'Y' ? 'selected' : ''}>사용</option>
                                            <option value="N" ${department.isActiveYn eq 'N' ? 'selected' : ''}>미사용</option>
                                        </select>
                                        <button type="submit">변경</button>
                                    </form>
                                </td>
                                <td class="admin-table-actions">
                                    <a href="${departmentModifyUrl}" onclick="return openDepartmentForm(this.href);">수정</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty departmentList}">
                            <tr>
                                <td colspan="6" class="empty-cell">등록된 진료과가 없습니다.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>

                <%@ include file="/views/common/pagination.jsp" %>
            </section>
        </main>
    </div>
</body>
</html>
