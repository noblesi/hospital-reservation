<<<<<<< HEAD
<%@page import="com.hospital.common.dto.DoctorDTO"%>
<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.hospital.user.doctor.dto.UserDoctorDTO"%>
<%@page import="com.hospital.user.doctor.UserDoctorService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglib.jsp"%>
<%
	List<DoctorDTO> list = (List<DoctorDTO>) request.getAttribute("doctorList");
	DepartmentDTO department = (DepartmentDTO) request.getAttribute("departmentDTO");
	pageContext.setAttribute("doctorList", list);
	pageContext.setAttribute("departmentDTO", department);
%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>한국중앙병원 | 진료과 소개 및 의료진</title>

<!-- Bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>

<!-- Bootstrap Icons CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

<!-- 외부 CSS -->
<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css?v=${initParam.assetVersion}' />">
<link rel="stylesheet" href="<c:url value='/resources/css/doctor/doctorInfo.css?v=${initParam.assetVersion}' />">

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript">
	$(function() {
		
	});
</script>
</head>
<style>
#doctorListHeader{
	margin-top: 30px;
}

#deparmentWrap{
	margin-left: 450px;
}

#doctorListContent, #doctorListHeader{
	margin-left: 450px;
}

.doctorListRow{
	width: 600px;
	height: 281px; 
	border: 2px solid #fdfdff;
	margin-top: 10px;
	background-color: #f7fbff;
	border-radius: 50px;
}

.doctorListRow:hover{
	border: 1px solid #aaa;
}

.thumbnail{
	width: 197px;
	height: 275px;
	border-radius: 50px;
}

.aDoctorList{
	width: 200px;
	height: 400px; 
}

.loc{
	margin-top: 30px;
	margin-left: 200px;
}

</style>
<body>
	<c:import url="/views/common/userHeader.jsp" />
	<c:import url="/views/common/userBreadcrumb.jsp" />

	<main id="mainWrap">
		<div id="deparmentWrap">
			<div><h3>${ departmentDTO.deptName } 소개</h3></div>
			<div id="deparmentDescription">
				<c:out value="${ departmentDTO.description }"/>
			</div>
		</div>
		<div id="doctorListHeader">
			<h3><span>의료진 목록</span></h3>
		</div>
		<div id="doctorListContent">
			<c:forEach var="docDTO" items="${ doctorList }">
				<a href="<c:url value='/doctor/doctorInfo.do?dln=${ docDTO.doctorLicenseNo }'/>" class="aDoctorList" >
					<div class="doctorListRow" >
						<table>
						<tr>
							<td rowspan="3">
									<img name="thumbnail[]"  style="margin-right: 15px;" class="thumbnail" src="../resources/images/doctors/${ docDTO.thumbnailUrl }"/>
							</td>
							<td>
								<strong><span style="font-size: 40px;"><c:out value="${ docDTO.name }"/></span></strong><br>
							</td>
						</tr>
						<tr>
							<td style="height: 40px; color: #9F9F9F">
								<strong><span><c:out value="전문분야"/></span></strong>
							</td>
						</tr>
						<tr>
							<td style="text-align: left; vertical-align: top;">
								<span><c:out value="${ docDTO.specialty }"/></span>
							</td>
						</tr>
						</table>
					</div>
				</a>
			</c:forEach>
		</div>
		<div id="deptLoc">
			<div style="text-align: center; font-size: 30px; margin-top: 20px;"><strong>위치 안내</strong></div>
			<%-- <img class="loc" src="../resources/images/department/loc/${ departmentDTO.deptNo }_loc.jpg"/> --%>
			<img class="loc" src="<c:out value='../resources/images/department/loc/${ departmentDTO.deptNo }_loc.jpg'/>"/>
		</div>
	</main>

	<c:import url="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>

</html>
=======
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>진료과 소개 및 의료진 | KMCH 중앙병원</title>

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
					<c:set var="doctorThumbnail" value="${empty docDTO.thumbnailUrl ? 'doctor_default.png' : docDTO.thumbnailUrl}" />
					<a href="${doctorInfoUrl}" class="doctor-card">
						<img class="doctor-card-image" src="<c:url value='/resources/images/doctors/${doctorThumbnail}' />" alt="${docDTO.name} 의료진 사진">
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
>>>>>>> branch 'dev' of https://github.com/noblesi/hospital-reservation.git
