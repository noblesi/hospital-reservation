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
        
        .admin-view-area {
            margin: 20px;
            position: relative;
        }/* admin-view-area */
        
        [name='deptChoice[]']{
        	text-align: center;
         	margin-left: 17px;
        	margin-top: 8px; 
        }
        
       	#deptData > tr > td {
        	text-align: left;
        	vertical-align: center;	
        }
    </style>
    
    <script type="text/javascript">
        
        $(function(){
           
        	<% 
      	   	AdminDepartmentService adminDepartmentService = new AdminDepartmentService();
      	  	//pageContext.setAttribute("adminDepartmentService", adminDepartmentService);
      	  	pageContext.setAttribute("deptList", adminDepartmentService.searchDepartmentList());
			%>
			
           $("#btnAddDept").click(addDeptModal);
           $("#btnModify").click(modifyModal);
           
			$("#isActiveChange").click(function(){
				//상태 변환 ajax를 이용 하는게 편할듯
				var isChk = $("[name='deptChoice[]']").is(":checked");
				if(!isChk){
	        		alert("진료과를 선택해주세요");
	        		return;
	        	} else {
	        		var isChkInd = $("[name='deptChoice[]']:checked").index("[name='deptChoice[]']");
	        	    var deptNo = $("[name='deptNo[]']").eq(isChkInd).val();
	        	    var chkYn = $("[name='isActiveYn[]']").eq(isChkInd).val();
	        	    $.ajax({
	        	        url: "http://localhost/hospital-reservation/views/admin/department/adminDepartmentListViewProcess.jsp",
	        	        type: "POST",
	        	        data: {
	        	            deptNo: deptNo,
	        	            isActiveYn: chkYn
	        	        },
	        	        success: function () {
	        	            alert("변경되었습니다.");
	        	            location.reload();
	        	        }
	        	    });
	        			        		    
	        	}// end else if
			});
           
        });//ready

        function addDeptModal(){
        	window.open("adminDepartmentAddModal.jsp?modify=N","dept_modal","width=500,height=480"); 
        }//addDeptModal
        
        function modifyModal(){
        	var isChk = $("[name='deptChoice[]']").is(":checked");
        	if(!isChk){
        		alert("진료과를 선택해주세요");
        		return;
        	} else {
        		
        		//var isChkVal=$("[name='deptChoice[]']:checked").val();
        		var isChkInd = $("[name='deptChoice[]']:checked").index("[name='deptChoice[]']");
        		//alert($("[name='deptNo[]']").eq(isChkInd)+"//"+isChkInd);
        		var chkVal = $("[name='deptNo[]']").eq(isChkInd).val();
        		var queryString = "&deptNo="+chkVal;
	        	window.open("adminDepartmentAddModal.jsp?modify=Y"+queryString,"dept_modal","width=500,height=480");
        	}// end else if
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
            <form class="admin-search-area" >
                <div class="admin-view-area">
                    <table >
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
                                        <table id="tabDeptInven" class="table table-hover">
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