<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 의료진 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css?v=${initParam.assetVersion}">
</head>
<body>
    <%@ include file="/views/common/adminHeader.jsp" %>

    <div class="admin-layout">
        <%@ include file="/views/common/adminSidebar.jsp" %>

        <main class="admin-content">
            <div class="admin-page-title">
                <h2>의료진 관리</h2>
                <p>진료과, 직급, 상태, 이름 기준으로 의료진 목록을 확인하고 상태를 변경합니다.</p>
            </div>

            <%@ include file="/views/common/message.jsp" %>

            <section class="admin-card">
                <form class="admin-search-area" action="<c:url value='${baseUrl}' />" method="get">
                    <label>
                        진료과
                        <select name="deptNo">
                            <option value="" ${empty searchDTO.deptNo ? 'selected' : ''}>전체</option>
                            <c:forEach var="dept" items="${deptList}">
                                <option value="${dept.deptNo}" ${searchDTO.deptNo eq dept.deptNo ? 'selected' : ''}>
                                    <c:out value="${dept.deptName}" />
                                </option>
                            </c:forEach>
                        </select>
                    </label>
                    <label>
                        직급
                        <select name="positionCode">
                            <option value="" ${empty searchDTO.positionCode ? 'selected' : ''}>전체</option>
                            <c:forEach var="position" items="${positionList}">
                                <option value="${position.positionCode}" ${searchDTO.positionCode eq position.positionCode ? 'selected' : ''}>
                                    <c:out value="${position.positionName}" />
                                </option>
                            </c:forEach>
                        </select>
                    </label>
                    <label>
                        상태
                        <select name="statusCode">
                            <option value="" ${empty searchDTO.statusCode ? 'selected' : ''}>전체</option>
                            <c:forEach var="status" items="${statusList}">
                                <option value="${status.statusCode}" ${searchDTO.statusCode eq status.statusCode ? 'selected' : ''}>
                                    <c:out value="${status.statusName}" />
                                </option>
                            </c:forEach>
                        </select>
                    </label>
                    <input type="text" name="name" value="${searchDTO.name}" placeholder="의료진 이름">
                    <button type="submit">검색</button>
                    <a href="<c:url value='/admin/doctor/form.do' />" class="admin-action-link">등록</a>
                </form>

                <table class="admin-table doctor-table">
                    <thead>
                        <tr>
                            <th class="doctor-col-no">번호</th>
                            <th>이름</th>
                            <th>진료과</th>
                            <th>직급</th>
                            <th>상태</th>
                            <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="doctor" items="${doctorList}" varStatus="st">
                            <c:set var="departmentName" value="-" />
                            <c:forEach var="dept" items="${deptList}">
                                <c:if test="${doctor.deptNo eq dept.deptNo}">
                                    <c:set var="departmentName" value="${dept.deptName}" />
                                </c:if>
                            </c:forEach>

                            <c:set var="positionName" value="-" />
                            <c:forEach var="position" items="${positionList}">
                                <c:if test="${doctor.positionCode eq position.positionCode}">
                                    <c:set var="positionName" value="${position.positionName}" />
                                </c:if>
                            </c:forEach>

                            <tr>
                                <td><c:out value="${st.count}" /></td>
                                <td class="text-left">
                                    <c:url var="doctorFormUrl" value="/admin/doctor/form.do">
                                        <c:param name="doctorLicenseNo" value="${doctor.doctorLicenseNo}" />
                                    </c:url>
                                    <a href="${doctorFormUrl}"><c:out value="${doctor.name}" /></a>
                                </td>
                                <td><c:out value="${departmentName}" /></td>
                                <td><c:out value="${positionName}" /></td>
                                <td class="admin-table-actions">
                                    <form action="<c:url value='/admin/doctor/list.do' />" method="post" class="doctor-status-form">
                                        <input type="hidden" name="doctorLicenseNo" value="${doctor.doctorLicenseNo}">
                                        <input type="hidden" name="deptNo" value="${searchDTO.deptNo}">
                                        <input type="hidden" name="name" value="${searchDTO.name}">
                                        <input type="hidden" name="positionCode" value="${searchDTO.positionCode}">
                                        <input type="hidden" name="statusCode" value="${searchDTO.statusCode}">
                                        <select name="rowStatusCode" aria-label="의료진 상태">
                                            <c:forEach var="status" items="${statusList}">
                                                <option value="${status.statusCode}" ${doctor.statusCode eq status.statusCode ? 'selected' : ''}>
                                                    <c:out value="${status.statusName}" />
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <button type="submit">변경</button>
                                    </form>
                                </td>
                                <td class="admin-table-actions">
                                    <a href="${doctorFormUrl}">수정</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty doctorList}">
                            <tr>
                                <td colspan="6" class="empty-cell">조회된 의료진이 없습니다.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </section>
        </main>
    </div>

    <script src="<c:url value='/resources/js/admin-layout.js?v=${initParam.assetVersion}' />"></script>
</body>
</html>
