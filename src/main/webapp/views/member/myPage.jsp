<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="mypage" scope="request" />
<c:set var="depth1" value="마이페이지" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지 | KMCH 한국중앙병원</title>
<link rel="stylesheet" href="<c:url value='/resources/css/sideBar.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=${initParam.assetVersion}' />">
<link rel="stylesheet" href="<c:url value='/resources/css/mypage.css?v=${initParam.assetVersion}' />">
</head>

<body>

<jsp:include page="/views/common/userHeader.jsp" />
<jsp:include page="/views/common/userBreadcrumb.jsp" />

<c:if test="${not empty sessionScope.mypageMessage}">
    <script>
    alert("<c:out value='${sessionScope.mypageMessage}' />");
    </script>
    <c:remove var="mypageMessage" scope="session" />
</c:if>

<%-- 마이페이지 본문 --%>
<main id="content" class="mypageLayout">
    <jsp:include page="/views/member/userSideGuide2.jsp" />

    <section class="mypageContent">
        <%-- 페이지 제목 영역 --%>
        <div class="mypageVisual">
            <div class="contHeadingWrap">
                <h2>마이페이지</h2>
                <p>회원님의 진료와 예약 정보를 확인하고 관리할 수 있습니다.</p>
            </div>

            <div class="mypageVisualGif">
                 <img src="<c:url value='/resources/images/myPage/mypage_visual.gif' />"
         alt="마이페이지 안내 이미지">
            </div>
        </div>

        <%-- 예약 및 진료 기록 요약 카드 --%>
        <div class="mypageSummary">
            <a href="#reservationModal"
               class="summaryCard"
               id="openReservationModal"
               role="button"
               aria-controls="reservationModal"
               aria-expanded="false">
                <div class="summaryIcon">
                    <img src="<c:url value='/resources/images/myPage/myPage_AppBtn.png' />" alt="예약 내역">
                </div>
                <div>
                    <strong>예약 내역</strong>
                    <em><c:out value="${appointmentCount}" />건</em>
                    <span>예정된 예약 확인</span>
                </div>
            </a>

            <a href="#medicalRecordModal"
               class="summaryCard"
               id="openMedicalRecordModal"
               role="button"
               aria-controls="medicalRecordModal"
               aria-expanded="false">
                <div class="summaryIcon green">
                    <img src="<c:url value='/resources/images/myPage/medical.png' />" alt="진료 기록">
                </div>
                <div>
                    <strong>진료 기록</strong>
                    <em class="greenText"><c:out value="${medicalCount}" />건</em>
                    <span>진료 및 검사 내역 확인</span>
                </div>
            </a>
        </div>

        <%-- 예약 취소 및 변경 목록 --%>
        <div class="reservationBox">
            <div class="boxTitle">
                <h3>예약 취소 및 변경</h3>
                <a href="<c:url value='/appointment/list.do' />">예약 전체보기</a>
            </div>
                <span class="boxTitle2">*3개월 전 예약내역 부터 조회됩니다.</span>

            <table class="reservationTable">
                <tbody>
                    <c:forEach var="app" items="${manageAppList}">
                        <c:set var="appointmentStatusClass" value="blue" />
                        <c:choose>
                            <c:when test="${app.status eq '예약취소'}">
                                <c:set var="appointmentStatusClass" value="gray" />
                            </c:when>
                            <c:when test="${app.status eq '예약완료' or app.status eq '진료완료'}">
                                <c:set var="appointmentStatusClass" value="green" />
                            </c:when>
                        </c:choose>
                        <tr>
                            <td>
                                <span class="state ${appointmentStatusClass}">
                                    ${app.status}
                                </span>
                            </td>
                            <td>${app.departmentName}</td>
                            <td>
                                <c:out value="${app.appointmentDate}" />
                                <c:out value="${app.appointmentTime}" />
                            </td>
                            <td><c:out value="${app.doctorName}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${app.cancelable}">
                                        <form action="<c:url value='/member/mypage/appointment/cancel.do' />"
                                              method="post"
                                              class="reservationCancelForm">
                                            <input type="hidden"
                                                   name="appointmentNo"
                                                   value="<c:out value='${app.appointmentNo}' />">
                                            <button type="submit" class="cancelBtn">예약 취소</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${app.status eq '예약취소'}">
                                        <span class="state gray">취소 완료</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="state gray">지난 예약</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty manageAppList}">
                        <tr>
                            <td colspan="5" class="reservationEmpty">
                                최근 3개월 예약 내역이 없습니다.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>

        </div>
    </section>
</main>

<%-- 전체 예약 현황 모달 --%>
<div class="reservationModal"
     id="reservationModal"
     role="dialog"
     aria-modal="true"
     aria-labelledby="reservationModalTitle"
     aria-hidden="true">
    <div class="reservationModalContent">
        <div class="reservationModalHeader">
            <h3 id="reservationModalTitle">예약 현황</h3>
            <button type="button" class="modalCloseIcon" data-modal-close aria-label="예약 현황 닫기">&times;</button>
        </div>

        <div class="reservationModalTabs" role="tablist" aria-label="예약 상태">
            <button type="button" class="reservationTab active" data-status-filter="all">전체</button>
            <button type="button" class="reservationTab" data-status-filter="예약대기">예약대기</button>
            <button type="button" class="reservationTab" data-status-filter="예약완료">예약완료</button>
            <button type="button" class="reservationTab" data-status-filter="예약취소">예약취소</button>
            <button type="button" class="reservationTab" data-status-filter="진료완료">진료완료</button>
        </div>

        <div class="reservationModalBody">
            <c:choose>
                <c:when test="${empty appList}">
                    <div class="reservationEmpty">예약 내역이 없습니다.</div>
                </c:when>
                <c:otherwise>
                    <table class="reservationModalTable">
                        <thead>
                            <tr>
                                <th>예약일</th>
                                <th>시간</th>
                                <th>진료과</th>
                                <th>의료진</th>
                                <th>상태</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="app" items="${appList}">
                                <c:set var="modalAppointmentStatusClass" value="blue" />
                                <c:choose>
                                    <c:when test="${app.status eq '예약취소'}">
                                        <c:set var="modalAppointmentStatusClass" value="gray" />
                                    </c:when>
                                    <c:when test="${app.status eq '예약완료' or app.status eq '진료완료'}">
                                        <c:set var="modalAppointmentStatusClass" value="green" />
                                    </c:when>
                                </c:choose>
                                <tr data-reservation-status="${app.status}">
                                    <td>${app.appointmentDate}</td>
                                    <td>${app.appointmentTime}</td>
                                    <td>${app.departmentName}</td>
                                    <td>${app.doctorName}</td>
                                    <td>
                                        <span class="state ${modalAppointmentStatusClass}">
                                            ${app.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr id="filteredReservationEmpty" hidden>
                                <td colspan="5" class="reservationEmpty">해당 상태의 예약 내역이 없습니다.</td>
                            </tr>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="reservationModalFooter">
            <button type="button" class="modalCloseBtn" data-modal-close>닫기</button>
        </div>
    </div>
</div>

<%-- 전체 진료 기록 모달 --%>
<div class="reservationModal"
     id="medicalRecordModal"
     role="dialog"
     aria-modal="true"
     aria-labelledby="medicalRecordModalTitle"
     aria-hidden="true">
    <div class="reservationModalContent">
        <div class="reservationModalHeader">
            <h3 id="medicalRecordModalTitle">진료 기록</h3>
            <button type="button" class="modalCloseIcon" data-medical-modal-close aria-label="진료 기록 닫기">&times;</button>
        </div>

        <div class="reservationModalTabs" role="tablist" aria-label="진료 상태">
            <button type="button" class="reservationTab active" data-medical-status-filter="all">전체</button>
        </div>

        <div class="reservationModalBody">
            <c:choose>
                <c:when test="${empty medicalList}">
                    <div class="reservationEmpty">진료 기록이 없습니다.</div>
                </c:when>
                <c:otherwise>
                    <table class="reservationModalTable">
                        <thead>
                            <tr>
                                <th>진료일</th>
                                <th>진료과</th>
                                <th>의료진</th>
                                <th>상태</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="medical" items="${medicalList}">
                                <tr data-medical-status="<c:out value='${medical.status}' />">
                                    <td><c:out value="${medical.treatmentDate}" /></td>
                                    <td><c:out value="${medical.deptName}" /></td>
                                    <td><c:out value="${empty medical.doctorName ? '-' : medical.doctorName}" /></td>
                                    <td>
                                        <span class="state green"><c:out value="${medical.status}" /></span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="reservationModalFooter">
            <button type="button" class="modalCloseBtn" data-medical-modal-close>닫기</button>
        </div>
    </div>
</div>

<jsp:include page="/views/common/userFooter.jsp" />

<script src="<c:url value='/resources/js/user-layout.js?v=${initParam.assetVersion}' />"></script>
<script src="<c:url value='/resources/js/mypage.js' />"></script>

</body>
</html>
