<%@page import="com.hospital.admin.department.AdminDepartmentService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.hospital.common.dto.DepartmentDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="adminMenu" value="reservation" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>진료과 등록</title>

<!-- bootstrap CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>

<!-- jQuery google API -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style type="text/css">
		#wrap { width: 470px; height: 370px; margin: 0px auto; }
		
		#frmWrap {
			position: relative;
			width: 412px;
			height: 340px;
			/* background: #fff url(../common/images/id_background.png) no-repeat ; */
            /* background-color: #888; */
		}

		#textDiv {
			position: absolute;
            margin: 0px;
            width: 372px;
            height: 200px;
            text-align: center;
			top: 0px;
			left: 0px;
            /* border: 1px solid #333; */
		}

        #btnDiv {
            position: absolute;
            margin: 0px;
            width: 372px;
            height: 140px;
            text-align: center;
			top: 200px;
			left: 0px;
            /* border: 1px solid #333; */
        } 
        
        [type='text'] {
        	width: 250px;
        	height: 80px;
        }
        #description {
        	font-size: 14px;
        }
	</style>
<script type="text/javascript">
        
$(function(){
	<%
	/*   List <DepartmentDTO> list = new ArrayList <DepartmentDTO>();
	list.add(new DepartmentDTO("DP001","치과","치아를 관리하는 과","Y","본관1층"));
	list.add(new DepartmentDTO("DP002","산부인과","임산부를 관리해주는 과","Y","본관2층"));
	list.add(new DepartmentDTO("DP003","안과","눈을 관리해주는 과","Y","별관2층"));
	list.add(new DepartmentDTO("DP004","외과","외과인데 사용 안한다요","N","")); */

	AdminDepartmentService adminDepartmentService = new AdminDepartmentService();

	List <DepartmentDTO> list = null;
	list = adminDepartmentService.searchDepartmentList();

	String modify = request.getParameter("modify");
	String deptNo="";
	boolean modifyFlag = "Y".equals(modify);
	pageContext.setAttribute("modifyFlag", modifyFlag);
		if(modifyFlag){
			deptNo = request.getParameter("deptNo");
			request.setAttribute("deptNo", deptNo);
			%>
			$("#deptNo").val('<%=deptNo%>');
			<%
			log(deptNo);
			for(int i = 0 ; i < list.size(); i++){
				if(list.get(i).getDeptNo().equals(deptNo)){%>
					$("#deptName").val('<%= list.get(i).getDeptName() %>');
					$("#deptName").prop("readonly",true);
					$("#description").val('<%= list.get(i).getDescription() %>');
					
					<%if(list.get(i).getDeptLoc()!=null){%>
						$("#deptLoc").val('<%= list.get(i).getDeptLoc() %>');
					<%}//end if
				}// end if
			}// end for
		}// end if%>
<%-- if(<%= list %>==null){
	alert("데이터가 안들어온다");
}// end if --%>
	$("#btnAddDept").click(saveDept);
	$("#btnModify").click(modifyDept);
	$("#btnCancle").click(selfClose);
	
});// ready

function saveDept(){
	var deptName=$("#deptName").val();
	//alert(deptName);
	var description=$("#description").val();
	
	if(deptName==""){
		alert("진료과 이름을 입력해 주세요.");
		$("#deptName").focus();
		return;
	}//end if
	
	if(description==""){
		alert("진료과 설명을 입력해 주세요.");
		$("#description").focus();
		return;
	}//end if
	
	<% 
	if(list != null){ %>
		var deptDTOName="";
		<%for(int i=0; i < list.size(); i++){ %>
			deptDTOName = "<%= list.get(i).getDeptName() %>";
			if(deptDTOName == deptName){
				alert(deptName+"는 존재하는 진료과입니다.");
				$("#deptName").focus();
				return;
			}//end if
		<%}// end for
	}// end if %>
      	
	if(confirm("저장하시겠습니까?")){
		$("#deptFrm").submit();
	}// end if

}// saveDept

function modifyDept(){
	<%request.setCharacterEncoding("UTF-8");%>
	
	if(confirm("수정하시겠습니까?")){
		$("#deptFrm").submit();
		
		//self.close();
	}// end if
}// modifyDept

function selfClose(){
	
	self.close();
}// selfClose

</script>
    <link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css' />">
    
</head>
<body>
	<div id="wrap">
        <section class="admin-card">
            <form id="deptFrm" class="admin-search-area" action="adminDepartmentAddModalProcess.jsp" method="post">
                <div id="frmWrap" >
                    <div id="textDiv">
                    	<table>
                    		<tr>
                    			<td>
                        			<label for="deptName">진료과 이름 : </label>
                        		</td>
                        		<td>
                        			<input type="text" id="deptName" name="deptName" />
                        			<c:if test="${ modifyFlag }">
                        				<input type="hidden" id="deptNo" name="deptNo" value="${ deptNo }"/>
                        			</c:if>
                        			<c:if test="${ not modifyFlag }">
                        				<input type="hidden" id="deptNo" name="deptNo" value=""/>
                        			</c:if>
                        			<c:choose>
                        				<c:when test="${ not empty isActiveYn  }">
                        					<input type="hidden" id="isActiveYn" name="isActiveYn" value="${ isActiveYn }"/>
                        				</c:when>
                        				<c:otherwise>
                        					<input type="hidden" id="isActiveYn" name="isActiveYn" value="N"/>
                        				</c:otherwise>
                        			</c:choose>
                        		</td>
                        	</tr>
                        	<tr>
                    			<td>
                        			<label for="deptLoc">진료과 위치 : </label>
                        		</td>
                        		<td>
                        			<input type="text" id="deptLoc" name="deptLoc" />
                        		</td>
                        	</tr>
                        	
                        	<tr>
                    			<td>
                        			<label for="description">진료과 설명 : </label>
                        		</td>
                        		<td>
                        			<textarea id="description" name="description" cols="32" rows="2"></textarea>
                        		</td>
                        	</tr>
                        	
                        </table>
                    </div>
                    <div id="btnDiv">
                    	<c:choose>
                    		<c:when test="${ modifyFlag }">
                    			<input type="button" value="수정" class="btn btn-sm btn-success" id="btnModify"/>
                    		</c:when>
                    		<c:otherwise>
                    			<input type="button" value="저장" class="btn btn-sm btn-success" id="btnAddDept">
                    		</c:otherwise>
                    	</c:choose>
                        <input type="button" value="취소" class="btn btn-cancle btn-sm" id="btnCancle">
                    </div>
                </div>
            </form>
        </section>
	</div>
<script src="<c:url value='/resources/js/admin-layout.js' />"></script>
</body>
</html>