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
	<c:if test="${modifyFlag}">
		$("#deptName").prop("readonly", true);
	</c:if>
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

	var hasDuplicate = $(".existingDeptName").toArray().some(function(input){
		return input.value === deptName;
	});

	if(hasDuplicate){
		alert(deptName+"는 존재하는 진료과입니다.");
		$("#deptName").focus();
		return;
	}//end if

	if(confirm("저장하시겠습니까?")){
		$("#deptFrm").submit();
	}// end if

}// saveDept

function modifyDept(){
	if(confirm("수정하시겠습니까?")){
		$("#deptFrm").submit();

		//self.close();
	}// end if
}// modifyDept

function selfClose(){

	self.close();
}// selfClose

</script>
    <link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css?v=${initParam.assetVersion}' />">

</head>
<body>
	<div id="wrap">
        <section class="admin-card">
            <form id="deptFrm" class="admin-search-area" action="<c:url value='/admin/department/form.do' />" method="post">
	<c:forEach var="dept" items="${departmentList}">
		<input type="hidden" class="existingDeptName" value="<c:out value='${dept.deptName}' />">
	</c:forEach>
                <div id="frmWrap" >
                    <div id="textDiv">
	<table>
		<tr>
			<td>
			<label for="deptName">진료과 이름 : </label>
		</td>
		<td>
			<input type="text" id="deptName" name="deptName" value="<c:out value='${department.deptName}' />" />
			<c:if test="${ modifyFlag }">
				<input type="hidden" id="deptNo" name="deptNo" value="<c:out value='${deptNo}' />"/>
			</c:if>
			<c:if test="${ not modifyFlag }">
				<input type="hidden" id="deptNo" name="deptNo" value=""/>
			</c:if>
			<c:choose>
				<c:when test="${ not empty isActiveYn  }">
					<input type="hidden" id="isActiveYn" name="isActiveYn" value="<c:out value='${isActiveYn}' />"/>
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
			<input type="text" id="deptLoc" name="deptLoc" value="<c:out value='${department.deptLoc}' />" />
		</td>
	</tr>

	<tr>
			<td>
			<label for="description">진료과 설명 : </label>
		</td>
		<td>
			<textarea id="description" name="description" cols="32" rows="2"><c:out value="${department.description}" /></textarea>
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
<script src="<c:url value='/resources/js/admin-layout.js?v=${initParam.assetVersion}' />"></script>
</body>
</html>
