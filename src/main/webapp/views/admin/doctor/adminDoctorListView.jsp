<%@page import="com.hospital.admin.doctor.dto.AdminDoctorSearchDTO"%>
<%@page import="com.hospital.common.dto.DoctorDTO"%>
<%@page import="com.hospital.common.dto.DoctorStatusDTO"%>
<%@page import="com.hospital.admin.doctor.dto.AdminDoctorFormOptionDTO"%>
<%@page import="com.hospital.common.dto.DoctorPositionDTO"%>
<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
<%@page import="com.hospital.admin.doctor.AdminDoctorService"%>
<%@page import="com.hospital.admin.doctor.controller.AdminDoctorListServlet"%>
<%@page import="com.hospital.admin.doctor.controller.AdminDoctorListViewServlet"%>
<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="adminMenu" value="reservation" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 공통 레이아웃 테스트</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>

<!-- jQuery google API -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<style type="text/css">
    #tabDeptInven {
        border: 1px solid #000;
    }

    #tabDeptInven > tbody > tr > td {
        text-align: left;
        border: 1px solid #333;
    }

    #tabDeptInven > thead > tr > th, #tabDeptInven > tbody > tr > th {
        border-bottom: 1px solid #000;
        text-align: center;
    }

    .admin-view-area {
        margin: 20px;
        position: relative;
    }

    /* 의료진 관리 화면 스타일 */
    .doctor-search-box {
        background: #d9d9e3;
        padding: 18px 20px;
        border-radius: 4px;
        display: flex;
        align-items: center;
        gap: 12px;
        flex-wrap: wrap;
    }

    .doctor-search-box label {
        font-weight: 600;
        color: #333;
        margin-right: 4px;
    }

    .doctor-search-box select,
    .doctor-search-box input {
        height: 38px;
        border: 1px solid #cfcfcf;
        border-radius: 8px;
        padding: 0 12px;
        font-size: 14px;
        background: #fff;
        box-sizing: border-box;
    }

    .doctor-search-box input {
        width: 220px;
    }

    .doctor-btn {
        height: 38px;
        padding: 0 22px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        font-weight: 600;
        color: #fff;
        background: #5b5ce2;
    }

    .doctor-btn:hover {
        background: #4a4bd1;
    }

    .doctor-btn-register {
        margin-left: auto;
    }

    .doctor-list-box {
        margin-top: 24px;
        background: #d9d9e3;
        border-radius: 4px;
        padding: 20px;
        min-height: 480px;
        box-sizing: border-box;
    }

    .doctor-list-title {
        font-size: 22px;
        font-weight: 700;
        margin-bottom: 20px;
    }

    .doctor-table {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0 10px;
    }

    .doctor-table thead th {
        background: #fff;
        border: 1px solid #cfcfcf;
        border-radius: 8px;
        padding: 10px;
        font-size: 14px;
        font-weight: 600;
        text-align: center;
    }

    .doctor-table tbody td {
        text-align: center;
        padding: 6px 8px;
        font-size: 14px;
        color: #333;
    }

    .status-select {
        height: 34px;
        border: 1px solid #cfcfcf;
        border-radius: 8px;
        padding: 0 10px;
        background: #fff;
    }

    .pagination-wrap {
        margin-top: 24px;
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 8px;
    }

    .pagination-wrap button {
        min-width: 34px;
        height: 30px;
        border: 1px solid #cfcfcf;
        border-radius: 8px;
        background: #fff;
        cursor: pointer;
    }

    .pagination-wrap button.active {
        background: #5b5ce2;
        color: #fff;
        border-color: #5b5ce2;
    }

    .pagination-wrap .arrow {
        font-weight: bold;
        border: none;
        background: transparent;
        font-size: 18px;
    }
</style>

<script type="text/javascript">
    $(function(){
		<%
		String deptNo = (String)request.getParameter("deptNo");
		String status =(String) request.getParameter("status");
		String name = (String)request.getParameter("name");
		
		AdminDoctorService adminDoctorService = new AdminDoctorService();
		AdminDoctorFormOptionDTO adminDoctorFormOptionDTO = adminDoctorService.getDoctorFormOptions();
		List<DepartmentDTO> deptList = adminDoctorFormOptionDTO.getDepartmentList();
		List<DoctorPositionDTO> positionList = adminDoctorFormOptionDTO.getPositionList();
		List<DoctorStatusDTO> statusList = adminDoctorFormOptionDTO.getStatusList();
		pageContext.setAttribute("deptList", deptList);
		pageContext.setAttribute("statusList", statusList);
		pageContext.setAttribute("positionList", positionList);
		//log(deptNo + status + name);
		
		if((deptNo==null && status == null && name == null) ||("".equals(deptNo) && "".equals(status) && "".equals(name)) ){
			List<DoctorDTO> doctorList = adminDoctorService.searchDoctorList();
			pageContext.setAttribute("doctorList", doctorList);
		} else {
			AdminDoctorSearchDTO adminDoctorSearchDTO = new AdminDoctorSearchDTO(); 
			adminDoctorSearchDTO.setDeptNo(deptNo);
			adminDoctorSearchDTO.setName(name);
			adminDoctorSearchDTO.setStatusCode(status);
			
			List<DoctorDTO> doctorList = adminDoctorService.searchDoctorList(adminDoctorSearchDTO);
			pageContext.setAttribute("doctorList", doctorList);
			
			
		}
		
		%>
    	
        $("#searchBtn").click(function () {
            const deptNo = $("#dept").val();
            const status = $("#status").val();
            const name = $("#name").val();
            
            if((deptNo == "" && status == "" && name == "") || (dept == null && status == null && name == null)){
            	alert("검색 조건은 입력해주세요");
            	return;
            }//end if
            var queryString = "";
            if("" != deptNo && null != deptNo){
            	queryString = "deptNo="+deptNo;
            }
            if("" != status && null != status){
            	queryString += "&status="+status;
            }
            if("" != name && null != name){
            	queryString += "&name="+name;
            }
             location.href = "<c:url value='adminDoctorListView.jsp?"+queryString+"' />";
            //alert("검색 조건\n진료과: " + dept + "\n상태: " + status + "\n이름: " + name);
        });

        $("#registerBtn").click(function () {
            alert("의료진 등록 페이지로 이동");
             location.href = "<c:url value='adminDoctorDetail.jsp' />";
        });

        $(".status-select").on("change", function () {
            const newStatus = $(this).val();
            const doctorName = $(this).closest("tr").find("td:eq(1)").text();

            console.log(doctorName + " 상태 변경: " + newStatus);
        });

        $(".pagination-wrap button").not(".arrow").click(function () {
            $(".pagination-wrap button").removeClass("active");
            $(this).addClass("active");
        });

    }); //ready
</script>

<link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css' />">
</head>
<body>

<jsp:include page="/views/common/adminHeader.jsp" />

<div class="admin-layout">
    <jsp:include page="/views/common/adminSidebar.jsp" />

    <main class="admin-content">
        <div class="admin-page-title">
            <h2>진료과 관리</h2>
        </div>

        <section class="admin-card">
            <form class="admin-search-area">
                <div class="admin-view-area">

                    <!-- 검색 영역 -->
                    <div class="doctor-search-box">
                        <label for="dept">진료과</label>
                        <select id="dept" name="dept">
                            <option value="">진료과 선택</option>
                           	<c:forEach var="dept" items="${ deptList }">
                           		<option value="${ dept.deptNo }"><c:out value="${ dept.deptName }"/></option>
                           	</c:forEach>
                        </select>

                        <select id="status" name="status">
                            <option value="">상태</option>
                            <c:forEach var="status" items="${ statusList }">
	                            <option value="${ status.statusCode }"><c:out value="${ status.statusName }"/></option>
                            </c:forEach>
                            
                        </select>

                        <label for="name">이름</label>
                        <input type="text" id="name" name="name" placeholder="의료진 이름 입력">

                        <button type="button" class="doctor-btn" id="searchBtn">검색</button>
                        <button type="button" class="doctor-btn doctor-btn-register" id="registerBtn">의료진 등록하기</button>
                    </div>

                    <!-- 목록 영역 -->
                    <div class="doctor-list-box">
                        <div class="doctor-list-title">의료진 목록</div>

                        <table class="doctor-table">
                            <thead>
                                <tr>
                                    <th>번호</th>
                                    <th>이름</th>
                                    <th>진료과</th>
                                    <th>직급</th>
                                    <th>상태</th>
                                </tr>
                            </thead>
                            <tbody id="doctorTable">
								<c:choose>
								<c:when test="${ not empty doctorList }">
								<c:forEach var="doctor" items="${doctorList}" varStatus="st">
									<tr>
										<td>${st.count}</td>
										<td><a href="http://localhost/hospital-reservation/views/admin/doctor/adminDoctorDetail.jsp?doctorLicenseNo=${doctor.doctorLicenseNo}">${doctor.name}</a></td>
										<c:forEach var="dept" items="${ deptList }">
											<c:if test="${ doctor.deptNo eq dept.deptNo }">
												<td><c:out value="${ dept.deptName }"/></td>
											</c:if>
										</c:forEach>
										<c:forEach var="positions" items="${ positionList }">
											<c:if test="${ doctor.positionCode eq positions.positionCode }">
												<td><c:out value="${ positions.positionName }"/></td>
											</c:if>
										</c:forEach>
										<c:forEach var="docstatus" items="${ statusList }">
											<c:if test="${ doctor.statusCode eq docstatus.statusCode }">
												<td><c:out value="${docstatus.statusName}"/></td>
											</c:if>
										</c:forEach>
									</tr>
								</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="5"> 검색된 데이터가 없습니다. </td>
									</tr>
								</c:otherwise>
								</c:choose>
							</tbody>
                        </table>

                       <!-- 페이지네이션 -->
                        <!-- <div class="pagination-wrap">
                            <button class="arrow">&lt;</button>
                            <button class="active">1</button>
                            <button>2</button>
                            <button>3</button>
                            <button>4</button>
                            <button>5</button>
                            <button>6</button>
                            <button class="arrow">&gt;</button>
                        </div>  -->
                    </div>

                </div>
            </form>
        </section>
    </main>
</div>

<script src="<c:url value='/resources/js/admin-layout.js' />"></script>
</body>
</html>
