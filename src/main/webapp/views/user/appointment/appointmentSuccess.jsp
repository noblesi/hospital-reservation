<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>한국중앙병원 | 진료 예약</title>

<!-- Bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>

<!-- Bootstrap Icons CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

<!-- 외부 CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css?v=${initParam.assetVersion}">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/appointment/appointment.css?v=${initParam.assetVersion}">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/appointment/appointmentSuccess.css">

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<!-- JS -->
<script type="text/javascript">
	
	$(function() {
		/* 예약목록확인 페이지로 이동 */
		$(".checkAppointListBtn").on("click", function() {
			location.href = "${pageContext.request.contextPath}/appointment/list.do";
		});
		
		/* 예약 취소 */
		$(".cancelAppointBtn").on("click", function() {
			location.href = "${pageContext.request.contextPath}/appointment/cancel.do?appointmentNo=${uacDTO.appointmentNo}";
		});
	});
</script>
</head>

<body>
	<c:import url="/views/common/userHeader.jsp" />
	<c:import url="/views/common/userBreadcrumb.jsp" />

	<div id="mainWrap">
		<div id="container">
			<div class="conHeader">
				<h2 class="title">인터넷 진료 예약</h2>

				<div class="telDiv">
					<img alt="" src="${pageContext.request.contextPath}/resources/images/appointment/tel_icon.png" class="telIcon"> <strong class="tel">예약센터 1588-0000</strong>
				</div>
			</div>

			<div class="appointmentInfoDiv">
				<h2 class="appoitmentInfoTitle">예약 완료 및 확인</h2>
				<div class="appointmentInfoTop">
					<i class="bi bi-check-circle checkIcon"></i><br> <span class="appointmentInfoNoti">인터넷 진료 예약 접수가 완료되었습니다.</span>
				</div>
				<h3 class="appoitmentInfoSubTitle">회원 정보 및 예약 정보</h3>

				<table class="infoTable">
					<tr>
						<th class="infoTh">예약자</th>
						<td>
							<c:out value="${ uacDTO.patientName }" />
						</td>
						<th class="infoTh">환자번호</th>
						<td>
							<c:out value="${ uacDTO.patientNo }" />
						</td>
					</tr>
					<tr>
						<th class="infoTh">연락처</th>
						<td>
							<c:out value="${ uacDTO.phoneNumber }" />
						<td>
					</tr>
					<tr>
						<th class="infoTh">진료과</th>
						<td>
							<c:out value="${ uacDTO.deptName }" />
						</td>
						<th class="infoTh">의료진</th>
						<td>
							<c:out value="${ uacDTO.doctorName }" />
						</td>
					</tr>
					<tr>
						<th class="infoTh">이메일주소</th>
						<td>
							<c:out value="${ uacDTO.email }" />
						</td>
						<th class="infoTh">예약일</th>
						<td>
							<c:out value="${ uacDTO.appointmentDate} ${ uacDTO.appointmentTime }" />
						</td>
					</tr>
				</table>

				<button class="cancelAppointBtn">예약 취소</button>
				<button class="checkAppointListBtn">예약 현황 조회</button>
			</div>
			<div class="noticeDiv">
				<h2 class="subTitle">주의사항</h2>
				<p class="warningNoti">
					<span>1. 진료 예약 취소는 진료일 이전 자정(12시)까지 가능합니다.</span><br> <span class="subNoti">- 수납기록 및 검사예약이 없는 진료의 변경/취소가 가능합니다.</span><br> <span>2. 진료 예약 제한 안내</span><br> <span class="subNoti">- 예약 변경/취소 없이 진료를 받지 않을 경우 홈페이지 진료 예약 서비스가 제한됩니다.</span><br>
				</p>
				<h2 class="subTitle">준비사항</h2>
				<img class="requiredDocImg" src="${pageContext.request.contextPath}/resources/images/appointment/requiredDoc.png">
				<p class="requireP">
					<span class="red">신환, 초진 진료</span>인 경우, <span class="red">요양급여의뢰서(진료의뢰서)</span>를 반드시 지참해야 합니다.<br> 본원은 2단계 요양급여를 제공하는 상급종합병원입니다.<br> 건강보험 환자는 1단계 요양급여를 제공하는 의료기관(의원급·병원급-한방포함)에서 발급한 요양급여의뢰서(진료의뢰서)를 제출해야 하며,<br> 의료급여 환자는 2차, 3차 의료급여기관(병원급 이상)에서 발급한 의료급여의뢰서를 제출해야만 요양급여를 받을 수 있습니다.<br>
				</p>
				<p>
					<span class="blue">건강보험 환자 중 가정의학과 진료, 분만 시, 혈우병환자의 경우 요양급여의뢰서가 없어도 됩니다.</span><br> 또한, 장애인복지법에 의한 등록 장애인 또는 단순 물리치료가 아닌 작업치료·운동치료 등의 재활치료가 필요하다고 인정되는 자가<br> 재활의학과 진료를 볼 경우도 요양급여의뢰서가 없어도 됩니다.
				</p>
			</div>
		</div>
	</div>
	
	<c:import url="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>

</html>
