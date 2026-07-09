<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>KMCH 중앙병원 | 진료과 소개 및 의료진</title>

<!-- Bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>

<!-- Bootstrap Icons CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

<!-- 공통 CSS -->
<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=${initParam.assetVersion}' />">
<link rel="stylesheet" href="<c:url value='/resources/css/doctor/doctorInfo.css?v=${initParam.assetVersion}' />">

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<c:import url="/views/common/userHeader.jsp" />
	<c:import url="/views/common/userBreadcrumb.jsp" />

	<main id="mainWrap" class="doctor-list-main">
		<section class="doctor-department-section">
			<div class="doctor-department-copy">
				<h3><c:out value="${departmentDTO.deptName}" /> 소개</h3>
				<p><c:out value="${departmentDTO.description}" /></p>
			</div>
		</section>

		<section class="doctor-list-section">
			<div class="doctor-list-head">
				<h3><span>의료진 목록</span></h3>
			</div>
			<div class="doctor-list-content">
				<c:forEach var="docDTO" items="${doctorList}">
					<c:url var="doctorInfoUrl" value="/doctor/doctorInfo.do">
						<c:param name="dln" value="${docDTO.doctorLicenseNo}" />
					</c:url>
					<a href="${doctorInfoUrl}" class="doctor-card">
						<img class="doctor-card-image" src="<c:url value='/resources/images/doctors/${docDTO.thumbnailUrl}' />" alt="${docDTO.name} 의료진 사진">
						<span class="doctor-card-body">
							<strong class="doctor-card-name"><c:out value="${docDTO.name}" /></strong>
							<span class="doctor-card-label">전문분야</span>
							<span class="doctor-card-specialty"><c:out value="${docDTO.specialty}" /></span>
						</span>
					</a>
				</c:forEach>
				<c:if test="${empty doctorList}">
					<p class="doctor-list-empty">등록된 의료진이 없습니다.</p>
				</c:if>
			</div>
		</section>

		<section class="doctor-location-section">
			<h3>위치 안내</h3>
			<c:choose>
				<c:when test="${not empty departmentDTO.deptNo}">
					<img class="doctor-location-image" src="<c:url value='/resources/images/department/loc/${departmentDTO.deptNo}_loc.jpg' />" alt="${departmentDTO.deptName} 위치 안내">
				</c:when>
				<c:otherwise>
					<p class="doctor-list-empty">진료과 위치 정보를 불러올 수 없습니다.</p>
				</c:otherwise>
			</c:choose>
		</section>
	</main>

	<c:import url="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>

</html>
