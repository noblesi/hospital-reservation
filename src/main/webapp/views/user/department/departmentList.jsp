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
	text-align: center;
	vertical-align: center;
}

#departmentListContent{
	margin-top: 40px;
	text-align: center;
	vertical-align: center;
}

td{
	height: 120px;
	text-align: center;
	margin: 10px;
}

table {
	margin-left: auto;
	margin-right: auto;
}

img{
	position: absolute;
	left: 43px;
	top: 0px;
	margin-top: 20px;
}

.hoverImg{
	opacity: 0;
}

.rowWrap:hover .nomalImg {
	opacity: 0;
}

.rowWrap:hover .hoverImg {
	opacity: 1;
}

.rowWrap:hover .spanCls {
	color: #2677BB;
}

.spanCls{
	color: #9F9F9F;
}

.departmentListRow {
	position: relative;
	width: 140px;
	height: 80px;
}

.rowWrap{
	background-color: #f7fbff;
	border-radius: 20px;
	border: 1px solid #9F9F9F;
}

.rowWrap:hover {
	border: 1px solid #2677BB;
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
			<table>
				<c:forEach var="deptDTO" items="${ departmentList }" varStatus="i">
					<c:if test="${ i.count%5==1 }">
						<tr>
					</c:if>
							<td>
								<a href="<c:url value='/doctor/doctorList.do?deptNo=${ deptDTO.deptNo }'/>" > 
									<div class="rowWrap">
										<div name="departmentListRow[]" class="departmentListRow" >
											<img name="deptImg[]" class="nomalImg" src="../resources/images/department/${ deptDTO.deptNo }.png"/><br>
											<img name="deptImgHover[]" class="hoverImg" src="../resources/images/department/${ deptDTO.deptNo }_hover.png"/><br>
										</div>
										<div>
											<span class="spanCls"><c:out value="${ deptDTO.deptName }"/></span>
										</div>
									</div>
								</a>
							</td>
					<c:if test="${ i.count%5==0 }">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</main>

	<c:import url="/views/common/userFooter.jsp" />
	<script src="${pageContext.request.contextPath}/resources/js/user-layout.js?v=${initParam.assetVersion}"></script>
</body>

</html>
