<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>

<c:set var="adminMenu" value="department" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>진료과 등록 / 수정</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css?v=${initParam.assetVersion}' />">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            var form = document.getElementById("deptFrm");
            var deptName = document.getElementById("deptName");
            var description = document.getElementById("description");
            var saveButton = document.getElementById("btnAddDept");
            var modifyButton = document.getElementById("btnModify");
            var cancelButton = document.getElementById("btnCancel");

            if (saveButton) {
                saveButton.addEventListener("click", function () {
                    if (!validateRequired()) {
                        return;
                    }

                    if (hasDuplicateDepartment()) {
                        alert(deptName.value + "는 존재하는 진료과입니다.");
                        deptName.focus();
                        return;
                    }

                    if (confirm("저장하시겠습니까?")) {
                        form.submit();
                    }
                });
            }

            if (modifyButton) {
                modifyButton.addEventListener("click", function () {
                    if (!validateRequired()) {
                        return;
                    }

                    if (confirm("수정하시겠습니까?")) {
                        form.submit();
                    }
                });
            }

            if (cancelButton) {
                cancelButton.addEventListener("click", function () {
                    window.close();
                });
            }

            function validateRequired() {
                if (deptName.value.trim() === "") {
                    alert("진료과 이름을 입력해 주세요.");
                    deptName.focus();
                    return false;
                }

                if (description.value.trim() === "") {
                    alert("진료과 설명을 입력해 주세요.");
                    description.focus();
                    return false;
                }

                return true;
            }

            function hasDuplicateDepartment() {
                var existingNames = document.querySelectorAll(".existingDeptName");
                var currentName = deptName.value.trim();

                for (var i = 0; i < existingNames.length; i++) {
                    if (existingNames[i].value === currentName) {
                        return true;
                    }
                }

                return false;
            }
        });
    </script>
</head>
<body class="admin-popup-body">
    <section class="admin-card admin-popup-card">
        <h1 class="admin-popup-title">
            <c:choose>
                <c:when test="${modifyFlag}">진료과 수정</c:when>
                <c:otherwise>진료과 등록</c:otherwise>
            </c:choose>
        </h1>

        <form id="deptFrm" class="admin-compact-form" action="<c:url value='/admin/department/form.do' />" method="post">
            <c:forEach var="dept" items="${departmentList}">
                <input type="hidden" class="existingDeptName" value="<c:out value='${dept.deptName}' />">
            </c:forEach>

            <c:choose>
                <c:when test="${modifyFlag}">
                    <input type="hidden" id="deptNo" name="deptNo" value="<c:out value='${deptNo}' />">
                </c:when>
                <c:otherwise>
                    <input type="hidden" id="deptNo" name="deptNo" value="">
                </c:otherwise>
            </c:choose>

            <label for="deptName">
                진료과 이름
                <input type="text" id="deptName" name="deptName" value="<c:out value='${department.deptName}' />" ${modifyFlag ? 'readonly' : ''}>
            </label>

            <label for="deptLoc">
                진료과 위치
                <input type="text" id="deptLoc" name="deptLoc" value="<c:out value='${department.deptLoc}' />">
            </label>

            <label for="description">
                진료과 설명
                <textarea id="description" name="description"><c:out value="${department.description}" /></textarea>
            </label>

            <label for="isActiveYn">
                사용 여부
                <select id="isActiveYn" name="isActiveYn">
                    <option value="Y" ${empty department.isActiveYn or department.isActiveYn eq 'Y' ? 'selected' : ''}>사용</option>
                    <option value="N" ${department.isActiveYn eq 'N' ? 'selected' : ''}>미사용</option>
                </select>
            </label>

            <div class="admin-button-row">
                <c:choose>
                    <c:when test="${modifyFlag}">
                        <button type="button" class="admin-button" id="btnModify">수정</button>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="admin-button" id="btnAddDept">저장</button>
                    </c:otherwise>
                </c:choose>
                <button type="button" class="admin-button secondary" id="btnCancel">취소</button>
            </div>
        </form>
    </section>
    <script src="<c:url value='/resources/js/admin-layout.js?v=${initParam.assetVersion}' />"></script>
</body>
</html>
