<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>
<%
    if (request.getAttribute("dashboardSummary") == null) {
        response.sendRedirect(request.getContextPath() + "/admin/dashboard.do");
        return;
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>관리자 대시보드</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin-layout.css?v=20260623-admin-fluid">
</head>
<body>
    <%@ include file="/views/common/adminHeader.jsp" %>

    <div class="admin-layout">
        <%@ include file="/views/common/adminSidebar.jsp" %>

        <main class="admin-content">
            <div class="admin-page-title">
                <h2>대시보드</h2>
                <p>오늘 예약과 누적 예약 처리 현황을 확인합니다.</p>
            </div>

            <section class="dashboard-summary-grid" aria-label="예약 요약">
                <article class="dashboard-summary-card">
                    <strong>오늘 예약건수</strong>
                    <span>${dashboardSummary.todayAppointmentCount}</span>
                    <em>취소를 제외한 오늘 진료 예약</em>
                </article>
                <article class="dashboard-summary-card">
                    <strong>진행 예약건수</strong>
                    <span>${dashboardSummary.pendingAppointmentCount}</span>
                    <em>진료완료/예약취소 전 예약</em>
                </article>
                <article class="dashboard-summary-card">
                    <strong>진료완료건수</strong>
                    <span>${dashboardSummary.completedTreatmentCount}</span>
                    <em>완료율 ${dashboardSummary.completionRate}%</em>
                </article>
                <article class="dashboard-summary-card">
                    <strong>예약취소건수</strong>
                    <span>${dashboardSummary.cancelledAppointmentCount}</span>
                    <em>전체 예약 중 취소 누적 건수</em>
                </article>
            </section>

            <section class="dashboard-chart-grid" aria-label="예약 현황 그래프">
                <article class="admin-card dashboard-chart-card">
                    <div class="admin-section-title">
                        <h3>월별 예약현황</h3>
                        <span>올해 기준</span>
                    </div>

                    <div class="monthly-chart" aria-label="월별 예약현황 막대 그래프">
                        <c:forEach var="month" items="${monthlyAppointmentStatus}">
                            <div class="monthly-chart-item">
                                <div class="monthly-chart-bar-area">
                                    <span class="monthly-chart-count">${month.count}</span>
                                    <span class="monthly-chart-bar" style="height: ${month.rate}%;"></span>
                                </div>
                                <strong>${month.label}</strong>
                            </div>
                        </c:forEach>
                    </div>
                </article>

                <article class="admin-card dashboard-chart-card">
                    <div class="admin-section-title">
                        <h3>요일별 예약현황</h3>
                        <span>올해 기준</span>
                    </div>

                    <div class="weekday-chart" aria-label="요일별 예약현황 막대 그래프">
                        <c:forEach var="weekday" items="${weekdayAppointmentStatus}">
                            <div class="weekday-chart-item">
                                <strong>${weekday.label}</strong>
                                <div class="weekday-chart-track">
                                    <span class="weekday-chart-bar" style="width: ${weekday.rate}%;"></span>
                                </div>
                                <span>${weekday.count}</span>
                            </div>
                        </c:forEach>
                    </div>
                </article>
            </section>
        </main>
    </div>
</body>
</html>
