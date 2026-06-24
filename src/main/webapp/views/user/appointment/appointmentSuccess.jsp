<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@page import="com.hospital.user.appointment.dto.UserAppointmentConfirmDTO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setAttribute("activeMenu", "hospital");
request.setAttribute("depth1", "진료예약");
request.setAttribute("depth2", "예약완료");

String appointmentNo = request.getParameter("appointmentNo");
UserAppointmentConfirmDTO confirmDTO = null;

if (appointmentNo != null && !appointmentNo.trim().isEmpty()) {
	UserAppointmentService service = new UserAppointmentService();
	confirmDTO = service.searchAppointmentConfirm(appointmentNo.trim());
}

if (confirmDTO == null) {
%>
<script>
alert("예약 정보를 확인할 수 없습니다.");
history.back();
</script>
<%
	return;
}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>중앙병원 | 진료예약완료</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css?v=20260623-menu-hover-guard">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/appointment.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/appointmentSuccess.css">
</head>

<body>
	<jsp:include page="/views/common/userHeader.jsp" />
	<jsp:include page="/views/common/userBreadcrumb.jsp" />

	<div id="mainWrap">
		<div id="container">
			<div class="conHeader">
				<h2 class="title">인터넷 진료예약</h2>

				<div class="telDiv">
					<img alt="" src="${pageContext.request.contextPath}/resources/images/appointment/tel_icon.png" class="telIcon">
					<strong class="tel">예약센터 1588-0000</strong>
				</div>
			</div>

			<div class="appointmentInfoDiv">
				<h2 class="appoitmentInfoTitle">예약완료 및 확인</h2>
				<div class="appointmentInfoTop">
					<i class="bi bi-check-circle checkIcon"></i><br>
					<span class="appointmentInfoNoti">인터넷 진료예약 접수가 완료되었습니다.</span>
				</div>
				<h3 class="appoitmentInfoSubTitle">회원정보 및 예약 정보</h3>

				<table class="infoTable">
					<tr>
						<th class="infoTh">예약번호</th>
						<td><%= confirmDTO.getAppointmentNo() %></td>
						<th class="infoTh">예약자</th>
						<td><%= confirmDTO.getPatientName() %></td>
					</tr>
					<tr>
						<th class="infoTh">환자번호</th>
						<td><%= confirmDTO.getPatientNo() %></td>
						<th class="infoTh">연락처</th>
						<td><%= confirmDTO.getPhoneNumber() == null ? "-" : confirmDTO.getPhoneNumber() %></td>
					</tr>
					<tr>
						<th class="infoTh">진료과</th>
						<td><%= confirmDTO.getDeptName() %></td>
						<th class="infoTh">의료진</th>
						<td><%= confirmDTO.getDoctorName() %></td>
					</tr>
					<tr>
						<th class="infoTh">이메일주소</th>
						<td><%= confirmDTO.getEmail() == null ? "-" : confirmDTO.getEmail() %></td>
						<th class="infoTh">예약일시</th>
						<td><%= confirmDTO.getAppointmentDate() %> <%= confirmDTO.getAppointmentTime() %></td>
					</tr>
					<tr>
						<th class="infoTh">예약상태</th>
						<td><%= confirmDTO.getStatus() %></td>
						<th class="infoTh">요청사항</th>
						<td><%= confirmDTO.getRequirement() == null ? "-" : confirmDTO.getRequirement() %></td>
					</tr>
				</table>

				<button type="button" class="cancelAppointBtn" onclick="location.href='${pageContext.request.contextPath}/appointment/reserve.do'">추가 예약</button>
				<button type="button" class="checkAppointListBtn" onclick="location.href='${pageContext.request.contextPath}/member/mypage.do'">예약현황조회</button>
			</div>
			<div class="noticeDiv">
				<h2 class="subTitle">주의사항</h2>
				<p class="warningNoti">
					<span>1. 진료예약 취소는 진료일 이전 일정까지 가능합니다.</span><br>
					<span class="subNoti">- 예약 현황은 마이페이지에서 확인할 수 있습니다.</span><br>
					<span>2. 예약 상태는 담당자 확인 후 변경될 수 있습니다.</span><br>
				</p>
				<h2 class="subTitle">준비사항</h2>
				<img class="requiredDocImg" src="${pageContext.request.contextPath}/resources/images/appointment/requiredDoc.png">
				<p class="requireP">
					<span class="red">초진 진료</span>의 경우 신분증과 필요한 서류를 지참해 주세요.
				</p>
			</div>
		</div>
	</div>

	<jsp:include page="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=20260623-menu-hover-guard"></script>
</body>
</html>
