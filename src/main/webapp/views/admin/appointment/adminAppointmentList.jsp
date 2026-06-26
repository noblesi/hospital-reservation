<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 예약 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css?v=20260623-admin-fluid">
</head>
<body>
    <%@ include file="/views/common/adminHeader.jsp" %>

    <div class="admin-layout">
        <%@ include file="/views/common/adminSidebar.jsp" %>

        <main class="admin-content">
            <div class="admin-page-title">
                <h2>예약 관리</h2>
                <p>예약일, 예약 상태, 환자번호를 기준으로 예약 목록을 확인하고 상태를 변경합니다.</p>
            </div>

            <%@ include file="/views/common/message.jsp" %>

            <section class="admin-card">
                <form class="admin-search-area" action="<c:url value='${baseUrl}' />" method="get">
                    <label>
                        검색
                        <select name="searchType">
                            <option value="appointmentNo" ${empty searchDTO.searchType or searchDTO.searchType eq 'appointmentNo' ? 'selected' : ''}>예약번호</option>
                            <option value="patientNo" ${searchDTO.searchType eq 'patientNo' ? 'selected' : ''}>환자번호</option>
                            <option value="doctorLicenseNo" ${searchDTO.searchType eq 'doctorLicenseNo' ? 'selected' : ''}>의사면허번호</option>
                        </select>
                    </label>
                    <input type="text" name="searchKeyword" value="${searchDTO.searchKeyword}" placeholder="검색어">
                    <label>
                        상태
                        <select name="status">
                            <option value="" ${empty searchDTO.status ? 'selected' : ''}>전체</option>
                            <option value="승인 대기" ${searchDTO.status eq '승인 대기' ? 'selected' : ''}>승인 대기</option>
                            <option value="승인 완료" ${searchDTO.status eq '승인 완료' ? 'selected' : ''}>승인 완료</option>
                            <option value="예약취소" ${searchDTO.status eq '예약취소' ? 'selected' : ''}>예약취소</option>
                        </select>
                    </label>
                    <label>
                        시작일
                        <input type="date" name="startDate" value="${searchDTO.startDate}">
                    </label>
                    <label>
                        종료일
                        <input type="date" name="endDate" value="${searchDTO.endDate}">
                    </label>
                    <button type="submit">검색</button>
                </form>

                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>예약번호</th>
                            <th>환자번호</th>
                            <th>의사면허번호</th>
                            <th>예약일</th>
                            <th>예약시간</th>
                            <th>상태</th>
                            <th>등록일</th>
                            <th>관리</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="appointment" items="${appointmentList}">
                            <tr>
                                <td><c:out value="${appointment.appointmentNo}" /></td>
                                <td><c:out value="${appointment.patientNo}" /></td>
                                <td><c:out value="${appointment.doctorLicenseNo}" /></td>
                                <td><c:out value="${appointment.appointmentDate}" /></td>
                                <td><c:out value="${appointment.appointmentTime}" /></td>
                                <td><c:out value="${appointment.status}" /></td>
                                <td><c:out value="${appointment.createDate}" /></td>
                                <td class="admin-table-actions">
                                    <form action="<c:url value='/admin/reservation/status.do' />" method="post">
                                        <input type="hidden" name="appointmentNo" value="${appointment.appointmentNo}">
                                        <select name="status">
                                            <option value="승인 대기" ${appointment.status eq '승인 대기' ? 'selected' : ''}>승인 대기</option>
                                            <option value="승인 완료" ${appointment.status eq '승인 완료' ? 'selected' : ''}>승인 완료</option>
                                            <option value="예약취소" ${appointment.status eq '예약취소' ? 'selected' : ''}>예약취소</option>
                                        </select>
                                        <button type="submit">변경</button>
                                    </form>
                                    <form action="<c:url value='/admin/reservation/status.do' />" method="post">
                                        <input type="hidden" name="appointmentNo" value="${appointment.appointmentNo}">
                                        <input type="hidden" name="action" value="approve">
                                        <button type="submit">승인</button>
                                    </form>
                                    <form action="<c:url value='/admin/reservation/status.do' />" method="post">
                                        <input type="hidden" name="appointmentNo" value="${appointment.appointmentNo}">
                                        <input type="hidden" name="action" value="cancel">
                                        <button type="submit">취소</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty appointmentList}">
                            <tr>
                                <td colspan="8" class="empty-cell">조회된 예약이 없습니다.</td>
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
