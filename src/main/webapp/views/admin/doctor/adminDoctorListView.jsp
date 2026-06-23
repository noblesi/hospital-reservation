<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="adminMenu" value="doctor" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>관리자 의료진 관리</title>

<link rel="stylesheet" href="<c:url value='/resources/css/admin-layout.css?v=20260623-admin-fluid' />">
</head>
<body>

<jsp:include page="/views/common/adminHeader.jsp" />

<div class="admin-layout">
    <jsp:include page="/views/common/adminSidebar.jsp" />

    <main class="admin-content">
        <div class="admin-page-title">
            <h2>의료진 관리</h2>
            <p>진료과, 이름, 근무 상태를 기준으로 의료진 목록을 확인합니다.</p>
        </div>

        <section class="admin-card">
            <form class="admin-search-area doctor-search-area">
                <label for="dept">
                    진료과
                    <select id="dept" name="dept">
                        <option value="">전체</option>
                        <option value="일반외과">일반외과</option>
                        <option value="정형외과">정형외과</option>
                        <option value="신경외과">신경외과</option>
                    </select>
                </label>

                <label for="status">
                    상태
                    <select id="status" name="status">
                        <option value="">전체</option>
                        <option value="수술중">수술중</option>
                        <option value="휴진">휴진</option>
                        <option value="진료중">진료중</option>
                    </select>
                </label>

                <label for="name" class="doctor-search-name">
                    이름
                    <input type="text" id="name" name="name" placeholder="의료진 이름 입력">
                </label>

                <button type="button" id="searchBtn">검색</button>
                <button type="button" class="admin-action-link doctor-register-btn" id="registerBtn">의료진 등록</button>
            </form>

            <div class="admin-section-title doctor-list-title">
                <h3>의료진 목록</h3>
                <span>총 3명</span>
            </div>

            <table class="admin-table doctor-table">
                <thead>
                    <tr>
                        <th class="doctor-col-no">번호</th>
                        <th>이름</th>
                        <th>진료과</th>
                        <th>직급</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody id="doctorTable">
                    <tr>
                        <td>1</td>
                        <td>박진영</td>
                        <td>일반외과</td>
                        <td>전임의</td>
                        <td>
                            <select class="status-select">
                                <option selected>수술중</option>
                                <option>휴진</option>
                                <option>진료중</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>강백호</td>
                        <td>정형외과</td>
                        <td>전임의</td>
                        <td>
                            <select class="status-select">
                                <option>수술중</option>
                                <option selected>휴진</option>
                                <option>진료중</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>이수영</td>
                        <td>신경외과</td>
                        <td>레지던트</td>
                        <td>
                            <select class="status-select">
                                <option>수술중</option>
                                <option>휴진</option>
                                <option selected>진료중</option>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>

            <div class="common-pagination doctor-pagination">
                <button type="button" class="pagination-control disabled">&lt;</button>
                <button type="button" class="pagination-page active">1</button>
                <button type="button" class="pagination-page">2</button>
                <button type="button" class="pagination-page">3</button>
                <button type="button" class="pagination-page">4</button>
                <button type="button" class="pagination-page">5</button>
                <button type="button" class="pagination-page">6</button>
                <button type="button" class="pagination-control">&gt;</button>
            </div>
        </section>
    </main>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("searchBtn").addEventListener("click", function () {
        const dept = document.getElementById("dept").value;
        const status = document.getElementById("status").value;
        const name = document.getElementById("name").value;

        console.log("검색 조건:", dept, status, name);
        alert("검색 조건\n진료과: " + dept + "\n상태: " + status + "\n이름: " + name);
    });

    document.getElementById("registerBtn").addEventListener("click", function () {
        location.href = "<c:url value='/views/admin/doctor/adminDoctorDetail.jsp' />";
    });

    document.querySelectorAll(".status-select").forEach(function (select) {
        select.addEventListener("change", function () {
            const newStatus = select.value;
            const doctorName = select.closest("tr").children[1].textContent;

            console.log(doctorName + " 상태 변경: " + newStatus);
        });
    });

    document.querySelectorAll(".doctor-pagination .pagination-page").forEach(function (button) {
        button.addEventListener("click", function () {
            document.querySelectorAll(".doctor-pagination .pagination-page").forEach(function (pageButton) {
                pageButton.classList.remove("active");
            });
            button.classList.add("active");
        });
    });
});
</script>
<script src="<c:url value='/resources/js/admin-layout.js' />"></script>
</body>
</html>
