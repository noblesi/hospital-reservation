<%@page import="com.hospital.admin.department.dto.AdminDepartmentSearchDTO"%>
<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
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
        }/* deptInven */

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
        }/* admin-view-area */
    </style>
    
    <script type="text/javascript">
        
        var tempDeptArr;//진료과 데이터 담는 변수

        $(function(){
           
			<% 
				//JSON 데이터 저장
				//진료과 데이터
				List <DepartmentDTO> list = new ArrayList <DepartmentDTO>();
				list.add(new DepartmentDTO("DP001","치과","치아를 관리하는 과","Y","본관1층"));
				list.add(new DepartmentDTO("DP002","산부인과","임산부를 관리해주는 과","Y","본관2층"));
				list.add(new DepartmentDTO("DP003","안과","눈을 관리해주는 과","Y","별관2층"));
				list.add(new DepartmentDTO("DP004","외과","외과인데 사용 안한다요","N",""));
			%>
           $("#btnAddDept").click(addDeptModal);
           $("#btnModify").click(modifyModal);
        });//ready

        function addDeptModal(){
        	<% request.setAttribute("sendDeptList", list); %>
        	//alert("들어오는데");
        	window.open("modal-addDept.jsp","dept_modal","width=474,height=374,top="+window.screenY*5+",left="+window.screenX*2); 
        }//addDeptModal
        function modifyModal(){
        	<% 
        	request.setAttribute("sendDeptList", list);
        	%>
        	window.open("modal-addDept.jsp","dept_modal","width=474,height=374,top="+window.screenY*5+",left="+window.screenX*2);
        }//modifyModal
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
                    <table>
                        <thead>
                            <tr>
                                <th colspan="5" style="width: 1018px;">
                                    <input type="button" value="진료과 등록" id="btnAddDept" class="logout-btn">
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <div id="deptDiv">
                                        <table id="tabDeptInven">
                                            <thead>
                                                <tr>
                                                    <th style="width: 50px;">선택</th>
                                                    <th style="width: 50px;">번호</th>
                                                    <th style="width: 200px;">진료과 명</th>
                                                    <th style="width: 518px;">위치</th>
                                                    <th style="width: 200px;">사용여부</th>
                                                </tr>
                                            </thead>
                                            <tbody id="deptData">
	<c:if test="${ not empty deptList }">
		<c:forEach var="dept" items="${ deptList }" varStatus="i">
			<tr>
				<td>
					<input type="radio" name="deptChoice[]" />
					<input type="hidden" name="deptNo[]" value="${ dept.deptNo }"/>
				</td>
				<td>
					<c:out value="${ i.count }"/>
				</td>
				<td>
					<c:out value="${ dept.deptName }"/>
					<input type="hidden" name="deptName[]" value="${ dept.deptName }"/>
				</td>
				<c:choose>
					<c:when test="${ not empty dept.deptLoc }">
						<td>
							<c:out value="${ dept.deptLoc }"/>
							<input type="hidden" name="deptLoc[]" value="${ dept.deptLoc }"/>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							<c:out value="[위치가 입력 되지 않았습니다.]"/>
							<input type="hidden" name="deptLoc[]" value="${ dept.deptLoc }"/>
						</td>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${ dept.isActiveYn eq 'Y' or dept.isActiveYn eq 'y' }">
						<td>
							<c:out value="사용 중"/>
							<input type="hidden" name="isActiveYn[]" value="${ dept.isActiveYn }"/>
						</td>
					</c:when>
					<c:otherwise>
						<td>
							<c:out value="비활성화"/>
							<input type="hidden" name="isActiveYn[]" value="${ dept.isActiveYn }"/>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</c:if>
	<c:if test="${ empty deptList }">
		<tr>
			<td colspan="5" style="text-align: center;">
				데이터가 없습니다.
			</td>
		</tr>
	</c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5"><input type="button" value="수정"  class="logout-btn" id="btnModify"><input type="button" id="isActiveChange" value="사용/비활성화"  class="logout-btn"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </form>
        </section>
    </main>
</div>

<script src="<c:url value='/resources/js/admin-layout.js' />"></script>
</body>
</html>
