<%@page import="com.hospital.user.doctor.dto.UserDoctorDTO"%>
<%@page import="com.hospital.user.doctor.UserDoctorService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglib.jsp"%>
<%
int dln = Integer.parseInt(request.getParameter("dln"));

UserDoctorService uds = new UserDoctorService();

UserDoctorDTO udDTO = uds.searchDoctorDetail(dln);
pageContext.setAttribute("udDTO", udDTO);
%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>한국중앙병원 | 진료예약</title>

<!-- Bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>

<!-- Bootstrap Icons CND -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

<!-- 외부 CSS -->
<link rel="stylesheet" href="<c:url value='/resources/css/user-layout.css' />">
<link rel="stylesheet" href="<c:url value='/resources/css/doctor/doctorInfo.css' />">

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript">
	$(function() {
		$(".introBtn").on("click", function() {
			$(".careerBtn").removeClass("checked");
			$(".introBtn").addClass("checked");
			
			$(".introDiv").removeClass("hidden");
			$(".careerDiv").addClass("hidden");
		});
		
		$(".careerBtn").on("click", function() {
			$(".introBtn").removeClass("checked");
			$(".careerBtn").addClass("checked");
			
			$(".introDiv").addClass("hidden");
			$(".careerDiv").removeClass("hidden");
		});
	});
</script>
</head>

<body>
	<c:import url="/views/common/userHeader.jsp" />
	<c:import url="/views/common/userBreadcrumb.jsp" />

	<div id="mainWrap">
		<div id="content" style="background-image: url('${ udDTO.thumbnailUrl }');">
			<div class="contentTop">
				<h2 class="name"><c:out value="${ udDTO.name }"/></h2>
				<strong class="deptName"><c:out value="${ udDTO.deptName }"/></strong>
			</div>
			<div class="contentMain">
				<strong class="position">${ udDTO.position }</strong><br> <span class="major">${ udDTO.specialty }</span>
				<div class="timeTableDiv">
					<strong class="tableTitle">진료 시간표</strong>
					<table class="timeTable">
						<thead>
							<tr>
								<th class="typeTd">시간</th>
								<th>월</th>
								<th>화</th>
								<th>수</th>
								<th>목</th>
								<th>금</th>
								<th>토</th>
								<th>일</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="typeTd ampm">오전</td>
								
								<c:forEach var="i" begin="1" end="7" step="1">
									<c:set var="flag" value="false"/>
									
									<c:forEach items="${ udDTO.dsList }" var="dsDTO">
										<c:if test="${ dsDTO.dayOfWeek eq i and dsDTO.status ne '오후'}">
											<c:set var="flag" value="true"/>
										</c:if>
									</c:forEach>
									
									<c:choose>
										<c:when test="${ flag }"><td><span class="treatTime"></span></td></c:when>
										<c:otherwise><td></td></c:otherwise>
									</c:choose>
								</c:forEach>
								
							</tr>
							<tr>
								<td class="typeTd ampm">오후</td>
							
								<c:forEach var="i" begin="1" end="7" step="1">
									<c:set var="flag" value="false"/>
									
									<c:forEach items="${ udDTO.dsList }" var="dsDTO">
										<c:if test="${ dsDTO.dayOfWeek eq i and dsDTO.status ne '오전'}">
											<c:set var="flag" value="true"/>
										</c:if>
									</c:forEach>
									
									<c:choose>
										<c:when test="${ flag }"><td><span class="treatTime"></span></td></c:when>
										<c:otherwise><td></td></c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<!-- 의료진 소개글 시작 -->
		<div id="content2">
			<div style="display: inline-block;">
				<button class="introBtn checked">의료진 소개</button>
				<button class="careerBtn">학력/경력</button>
			</div>
			
			<div class="introDiv">
				<strong class="introTitle"><i class="bi bi-stop-circle-fill"></i> <span class="intTitleTxt"><c:out value="${ udDTO.introTitle }" /></span></strong>
				<p class="introContent">
					<c:out value="${ udDTO.introContent }"/>
				</p>
			</div>
			
			<div class="careerDiv hidden">
				<strong class="introTitle"><i class="bi bi-stop-circle-fill"></i> <span class="intTitleTxt">직위</span></strong>
				<ul>
					<li>한국중앙병원 <c:out value="${ udDTO.position }"/></li>
				</ul>
				
				<strong class="introTitle"><i class="bi bi-stop-circle-fill"></i> <span class="intTitleTxt">학력</span></strong>
				<ul>
					<c:forEach var="deDTO" items="${ udDTO.deList }">
						<li><span class="year"><c:out value="${ deDTO.educationYear }" /></span> : <span class="school"><c:out value="${ deDTO.educationContent }" /></span></li>
					</c:forEach>
				</ul>
				
				<strong class="introTitle"><i class="bi bi-stop-circle-fill"></i> <span class="intTitleTxt">경력</span></strong>
				<ul>
					<c:forEach var="dcDTO" items="${ udDTO.dcList }">
						<li><span class="year"><c:out value="${ dcDTO.careerYear }" /></span> : <span class="school"><c:out value="${ dcDTO.careerContent }" /></span></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		
	</div>

	<c:import url="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=20260623-menu-hover-guard"></script>
</body>

</html>
