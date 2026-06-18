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
	</style>
	<script type="text/javascript">
        
		$(function(){
            <%
            List<DepartmentDTO> list = null;
            if(list == null){%>
            	<%list = (List<DepartmentDTO>) request.getSession().getAttribute("sendDeptList");
            }// end if
            //add=N&deptNo=DP003&name=치과&description=치아를%20관리하는%20과&deptLoc=본관1층&isActive=Y
            String add = request.getParameter("add");
            String deptNo = request.getParameter("deptNo");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String deptLoc = request.getParameter("deptLoc");
            String isActive = request.getParameter("isActive");
            if("N".equals(add)){%>
            	$("#dept_name").val("<%= name %>");
            	$("#description").val("<%= description %>");
            	$("#dept_loc").val("<%= deptLoc %>");
            	
            <%}// end if %>
			<%-- if(<%= list %>==null){
				alert("데이터가 안들어온다");
			}// end if --%>
			$("#btnAddDept").click(saveDept);
			$("#btnCancle").click(selfClose);
			
		});// ready

        function selfClose(){
			<% session.removeAttribute("modalDeptList"); %>
            self.close();
        }// selfClose

		function saveDept(){
        	var deptName=$("#dept_name").val();
        	//alert(deptName);
        	var description=$("#description").val();
        	
        	if(deptName==""){
        		alert("진료과 이름을 입력해 주세요.");
        		$("#dept_name").focus();
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
			<%	for(int i=0; i < list.size(); i++){ %>
						deptDTOName = "<%= list.get(i).getDeptName() %>";
		           		if(deptDTOName == deptName){
		           			alert(deptName+"는 존재하는 진료과입니다.");
		           			$("#dept_name").focus();
		           			return;
		           		}//end if
	         <% }// end for
         	} else { %>
         	 	alert("데이터 없음");
         	<% }//end else if %>
        	
            if(confirm("저장하시겠습니까?")){
                
            }// end if
         	
		}// sendId
	</script>
    <link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css' />">
</head>
<body>
	<div id="wrap">
        <section class="admin-card">
            <form id="dupFrm" class="admin-search-area">
                <div id="frmWrap" >
                    <div id="textDiv">
                        <label for="dept_name">진료과 이름 : </label><input type="text" id="dept_name" name="dept_name" ><br>
                        <label for="description">진료과 설명 : </label><input type="text" id="description" name="description" ><br>
                        <label for="dept_loc">진료과 위치 : </label><input type="text" id="dept_loc" name="dept_loc" ><br>
                    </div>
                    <div id="btnDiv">
                        <input type="button" value="저장" class="btn link-btn btn-sm" id="btnAddDept">
                        <input type="button" value="취소" class="btn btn-cancle btn-sm" id="btnCancle">
                    </div>
                </div>
            </form>
        </section>
	</div>
<script src="<c:url value='/resources/js/admin-layout.js' />"></script>
</body>
</html>