<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="activeMenu" value="mypage" scope="request" />
<c:set var="depth1" value="마이페이지" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<%@page import="java.util.List"%>
<%@page import="com.hospital.common.MemberDTO"%>
<%@page import="com.hospital.member.UserMyPageService"%>
<%@page import="com.hospital.member.dto.UserAppointmentDTO"%>
<%@page import="com.hospital.member.dto.UserMedicalRecordDTO"%>

<%
MemberDTO loginUser = (MemberDTO)session.getAttribute("loginUser");

if(loginUser == null){
    response.sendRedirect(request.getContextPath() + "/views/member/login.jsp");
    return;
}

UserMyPageService umps = new UserMyPageService();

MemberDTO memberInfo = umps.searchMemberInfo(loginUser.getLoginId());

String patientNo = "";
if(memberInfo != null){
    patientNo = memberInfo.getPatientNo();
}

List<UserAppointmentDTO> appList = umps.searchAppointmentList(patientNo);
List<UserMedicalRecordDTO> medicalList = umps.searchMedicalRecordList(patientNo);

int appointmentCount = appList.size();
int medicalCount = medicalList.size();

pageContext.setAttribute("memberInfo", memberInfo);
pageContext.setAttribute("appointmentCount", appointmentCount);
pageContext.setAttribute("medicalCount", medicalCount);
pageContext.setAttribute("appList", appList);
pageContext.setAttribute("medicalList", medicalList);
%>
<link rel="stylesheet" href="<c:url value='/resources/css/sideBar.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/mypage.css' />">
</head>

<body>

<jsp:include page="../common/userHeader.jsp" />
<jsp:include page="../common/userBreadcrumb.jsp" />

<%-- 마이페이지 본문 --%>
<main id="content" class="mypageLayout">
    <jsp:include page="userSideGuide2.jsp" />

    <section class="mypageContent">
        <%-- 페이지 제목 영역 --%>
        <div class="mypageVisual">
            <div class="contHeadingWrap">
                <h2>마이페이지</h2>
                <p>회원님의 진료와 예약 정보를 확인하고 관리할 수 있습니다.</p>
            </div>

            <div class="mypageVisualImg">
                📋
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
                    <em>${appointmentCount}건</em>
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
                    <em class="greenText">${medicalCount}건</em>
                    <span>진료 및 검사 내역 확인</span>
                </div>
            </a>
        </div>

        <%-- 예약 취소 및 변경 목록 --%>
        <div class="reservationBox">
            <div class="boxTitle">
                <h3>예약 취소 및 변경</h3>
                <a href="<c:url value='/views/reservation/main.jsp' />">예약 전체보기</a>
            </div>

            <table class="reservationTable">
                <tbody>
                    <c:forEach var="app" items="${appList}">
                        <tr>
                            <td><span class="state blue">${app.status}</span></td>
                            <td>${app.departmentName}</td>
                            <td>
                                ${app.appointmentDate}
                                ${app.appointmentTime}
                            </td>
                            <td>${app.doctorName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${app.status ne '예약취소'}">
                                        <form action="<c:url value='/views/member/process/cancelAppointmentProcess.jsp' />"
                                              method="post"
                                              class="reservationCancelForm">
                                            <input type="hidden"
                                                   name="appointmentNo"
                                                   value="${app.appointmentNo}">
                                            <button type="submit" class="cancelBtn">예약 취소</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="state gray">취소 완료</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
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
            <button type="button" class="reservationTab" data-status-filter="승인완료">승인완료</button>
            <button type="button" class="reservationTab" data-status-filter="예약취소">예약취소</button>
            <button type="button" class="reservationTab" data-status-filter="승인대기">승인대기</button>
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
                                <tr data-reservation-status="${app.status}">
                                    <td>${app.appointmentDate}</td>
                                    <td>${app.appointmentTime}</td>
                                    <td>${app.departmentName}</td>
                                    <td>${app.doctorName}</td>
                                    <td>
                                        <span class="state ${app.status eq '예약취소' ? 'gray' : app.status eq '진료완료' ? 'green' : app.status eq '승인대기' ? 'yellow' : 'blue'}">
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
                                <tr data-medical-status="${medical.status}">
                                    <td>${medical.treatmentDate}</td>
                                    <td>${medical.deptName}</td>
                                    <td>${empty medical.doctorName ? '-' : medical.doctorName}</td>
                                    <td>
                                        <span class="state green">${medical.status}</span>
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

<jsp:include page="../common/userFooter.jsp" />

<script src="<c:url value='/resources/js/user-layout.js' />"></script>
<script src="<c:url value='/resources/js/mypage.js' />"></script>

</body>
</html>
