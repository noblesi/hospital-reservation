<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>KMCH 중앙병원 | 진료과 목록</title>

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

	<main id="mainWrap" class="department-list-main">
		<div class="department-list-head">
			<h3><span>진료과 목록</span></h3>
			<p>진료과를 선택하면 해당 진료과 소개와 의료진 목록을 확인할 수 있습니다.</p>
		</div>

		<div class="department-list-grid">
			<c:forEach var="deptDTO" items="${departmentList}">
				<c:url var="doctorListUrl" value="/doctor/doctorList.do">
					<c:param name="deptNo" value="${deptDTO.deptNo}" />
				</c:url>
				<a href="${doctorListUrl}" class="department-card">
					<span class="department-card-icon">
						<img class="department-icon department-icon-default" src="<c:url value='/resources/images/department/${deptDTO.deptNo}.png' />" alt="">
						<img class="department-icon department-icon-hover" src="<c:url value='/resources/images/department/${deptDTO.deptNo}_hover.png' />" alt="">
					</span>
					<span class="department-card-name"><c:out value="${deptDTO.deptName}" /></span>
				</a>
			</c:forEach>
			<c:if test="${empty departmentList}">
				<p class="department-list-empty">조회 가능한 진료과가 없습니다.</p>
			</c:if>
		</div>
	</main>

	<c:import url="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>

</html>
