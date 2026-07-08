<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.hospital.user.doctor.dto.UserDoctorDTO"%>
<%@page import="com.hospital.user.doctor.UserDoctorService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/taglib.jsp"%>
<%
	List<DepartmentDTO> list = (List<DepartmentDTO>) request.getAttribute("departmentList");
	pageContext.setAttribute("departmentList", list);
%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>한국중앙병원 | 진료과목록</title>

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
#departmentListHead{
	background-color: #f00;
	text-align: center;
	vertical-align: center;
}
#departmentListContent{
	background-color: #0f0;
	text-align: center;
	vertical-align: center;
}
.departmentListRow {
	width: 180px;
	height: 180px;
	vertical-align: center;
	text-align: center;
	border-radius: 80px;
	border-color: 1px solid #333;
}
</style>
<body>
	<c:import url="/views/common/userHeader.jsp" />
	<c:import url="/views/common/userBreadcrumb.jsp" />

	<main id="mainWrap">
		<div id="departmentListHead">
			<h3><span>진료과 목록</span></h3>
		</div>
		<div id="departmentListContent">
			<c:forEach var="deptDTO" items="${ departmentList }">
				<div name="departmentListRow[]" class="departmentListRow" >
					<a href="<c:url value='/doctor/doctorList.do?deptNo=${ deptDTO.deptNo }'/>" style="width: 180px; height: 180px;" > 
					<img src="../resources/images/department/${ deptDTO.deptNo }.png"/><br>
					<span><c:out value="${ deptDTO.deptName }"/></span>
					</a>
				</div>
			</c:forEach>
		</div>
	</main>

	<c:import url="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>

</html>
