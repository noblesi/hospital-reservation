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
<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=${initParam.assetVersion}' />">
<link rel="stylesheet" href="<c:url value='/resources/css/appointment/appointment.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/appointment/appointmentList.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/appointment/appointmentSidebar.css' />">

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript">
	$(function() {
		/* 예약 변경 버튼 */
		$(".apptChangeBtn").on("click", function() {
			if(confirm("예약을 변경하시겠습니까?")) {
				location.href = "${pageContext.request.contextPath}/appointment/reserve.do?appointmentNo=" + $(this).val();
			}
		});

		/* 예약 취소 버튼 */
		$(".apptCancelBtn").on("click", function() {
			if(confirm("예약을 취소하시겠습니까?")) {
				location.href = "${pageContext.request.contextPath}/appointment/cancel.do?appointmentNo=" + $(this).val();
			}
		});
	});
</script>
</head>

<body>
	<c:import url="/views/common/userHeader.jsp" />
	<c:import url="/views/common/userBreadcrumb.jsp" />
	<!-- 사이드바 -->
	<jsp:include page="/views/user/appointment/appointmentSidebar.jsp" />

	<div class="mainWrap">

		<!-- 유저의 진료 목록 -->
		<h1 class="title">진료 예약 확인</h1>
		<ul class="apptListUl">

			<c:if test="${ not empty uasDTOList }">
				<c:forEach var="uasDTO" items="${ uasDTOList }" varStatus="i">
				<li class="apptListLi">
					<img class="docImg" src='<c:url value="../resources/images/doctors/${ uasDTO.thumbnailUrl }"/>'>
					<p class="docInfoText">
						<span class="deptName"><c:out value="${ uasDTO.deptName }"/></span>
						<span class="docName"><c:out value="${ uasDTO.doctorName }"/></span>
					</p>
					<table class="apptInfoTable">
						<tr>
							<th>인터넷예약</th>
							<td>신청일 : <span><c:out value="${ uasDTO.createdAt }"/></span></td>
						</tr>
						<tr>
							<th>진료 일정</th>
							<td><c:out value="${ uasDTO.appointmentDate } ${ uasDTO.appointmentTime }"/></td>
						</tr>
						<tr>
							<th>위치</th>
							<td><c:out value="${ uasDTO.deptLoc }"/></td>
						</tr>
					</table>
					<button class="apptChangeBtn" value="${ uasDTO.appointmentNo }">예약변경</button>
					<button class="apptCancelBtn" value="${ uasDTO.appointmentNo }">예약취소</button>
				</li>
				</c:forEach>
			</c:if>

			<c:if test="${ empty uasDTOList }">
				<li class="apptListLi">
					<p class="noResultP">
						조회 가능한 진료 예약이 없습니다.
					</p>
				</li>
			</c:if>

		</ul>

		<!-- 준비, 주의사항등 공지 -->
		<div class="warnNotiDiv">
			<h2 class="warnNotiTitle"><i class="bi bi-exclamation-triangle warnIcon"></i>주의</h2>
			<p class="warnNotiP">
				진료 예약 취소는 <span class="blue">진료일 이전(자정)</span>까지 가능합니다. (수납기록 및 검사예약이 없는 진료만 변경/취소 가능)<br>
				예약 후, <span class="blue">해당 일정</span>으로 <span class="blue">타인</span>으로의 변경 요청은 불가합니다.<br>
				예약이 조회되지 않은 경우에는 <span class="blue">예약센터(1577-0000)</span>나 해당 진료과로 문의해 주십시오.
			</p>
		</div>

		<div class="requireNotiDiv">
			<h2 class="requireNotiTitle">준비사항</h2>
			<strong>요양급여의뢰서(진료의뢰서)를 반드시 지참하십시오</strong>
			<p class="requireNotiP">
				본원은 2단계 요양급여를 제공하는 상급종합병원입니다.<br>
				<span class="blue">건강보험 환자는</span> 1단계 요양급여를 제공하는 의료기관(의원·병원급-한방포함)에서 발급한 요양급여의뢰서(진료의뢰서)를<br>
				제출해야 하며, <span class="blue">의료급여 환자</span>는 2차, 3차 의료급여기관(병원급 이상)에서 발급한 의료급여의뢰서를 제출해야만 요양급여를<br>
				받을 수 있습니다.
			</p>
			<p class="requireNotiP">
				건강보험 환자 중 <span class="blue">가정의학과 진료, 분만 시, 혈우병환자</span>의 경우 요양급여의뢰서는 없어도 됩니다.<br>
				또한, <span>장애인복지법에 의한 등록 장애인 또는 단순 물리치료가 아닌 작업치료·운동치료 등의 재활치료가 필요하다고 인정되는<br>
				자</span>가 재활의학과 진료를 볼 경우도 요양급여의뢰서는 없어도 됩니다.
			</p>
		</div>

		<div class="useNotiDiv">
			<h2 class="useNotiTitle">이용안내</h2>
			<strong>1. 진료비 수납</strong>
			<p>
				진료·검사·치료 먼저 받으신 후 귀가 전 한번만 가까운 수납창구에 방문해 주시면 됩니다.<br>
				<span class="noticeMuted">※ 진료 예약 현황의 상세 내용 중 진료비를 미리 납부하신 경우는 수납/미수납 중 수납으로 표시됩니다.</span>
			</p>
			<strong>2. 진료 예약 취소</strong>
			<p>
				수납 이력이나 검사 예약이 없는 진료만 취소가 가능합니다.<br>
				<span class="noticeMuted">- 진료 예약 현황의 예약 신청일 아래 정보는 예약을 신청한 곳을 의미하며, 진료일 전 자정(12시)까지 취소가 가능합니다.<br>
				-예약 취소가 불가능할 경우, 예약센터(T.1588-0000)에서 취소해 주시기 바랍니다.<br></span>
				예약 변경/취소 없이 진료를 받지 않을 경우 홈페이지 진료 예약 서비스가 제한됩니다.
			</p>
			<strong>3. 동일 여러 진료과에서 진료를 보는 경우</strong>
			<p>
				- 진료 예약 시간의 간격은 최소 30분~1시간 이상을 두고 예약해 주시기 바랍니다.<br>
				-진료시간 간격이 좁은 경우는 진료과마다 진료대기시간이 길어 질 수 있으므로 이후 진료과 담당간호사에게 미리 양해를 구해 놓아 주시기 바랍니다.
			</p>
		</div>
	</div>

	<c:import url="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>

</html>
