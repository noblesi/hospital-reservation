<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.loginAdmin}">
	<c:redirect url="/admin/dashboard.do" />
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 로그인</title>
<style type="text/css">
*{
	box-sizing:border-box;
}

body{
	min-height:100vh;
	margin:0;
	display:flex;
	align-items:center;
	justify-content:center;
	background:#f4f6f8;
	color:#1f2933;
	font-family:Arial, "Malgun Gothic", sans-serif;
}

.admin-login{
	width:min(920px, calc(100% - 40px));
	min-height:520px;
	display:grid;
	grid-template-columns:1fr 1fr;
	background:#fff;
	border:1px solid #dfe5ec;
	border-radius:8px;
	box-shadow:0 12px 35px rgba(15, 23, 42, .12);
	overflow:hidden;
}

.admin-login-info{
	padding:64px 54px;
	background:#12324a;
	color:#fff;
	display:flex;
	flex-direction:column;
	justify-content:center;
}

.admin-login-info h1{
	margin:0 0 18px;
	font-size:32px;
	font-weight:700;
	letter-spacing:0;
}

.admin-login-info p{
	margin:0;
	color:#d7e2ea;
	font-size:16px;
	line-height:1.8;
}

.admin-login-form-area{
	padding:64px 54px;
	display:flex;
	flex-direction:column;
	justify-content:center;
}

.admin-login-form-area h2{
	margin:0 0 28px;
	font-size:26px;
	color:#111827;
	letter-spacing:0;
}

.admin-login-message{
	margin:0 0 18px;
	padding:12px 14px;
	border:1px solid #f2b8b5;
	background:#fff2f1;
	color:#b42318;
	font-size:14px;
}

.admin-login-form label{
	display:block;
	margin:0 0 8px;
	font-size:14px;
	font-weight:700;
	color:#374151;
}

.admin-login-form input{
	width:100%;
	height:48px;
	margin:0 0 18px;
	padding:0 14px;
	border:1px solid #cfd8e3;
	border-radius:5px;
	font-size:15px;
}

.admin-login-form input:focus{
	outline:none;
	border-color:#1e6fb8;
	box-shadow:0 0 0 3px rgba(30, 111, 184, .14);
}

.admin-login-button{
	width:100%;
	height:50px;
	margin-top:6px;
	border:0;
	border-radius:5px;
	background:#1e6fb8;
	color:#fff;
	font-size:16px;
	font-weight:700;
	cursor:pointer;
}

.admin-login-button:hover{
	background:#15598f;
}

.admin-login-link{
	margin-top:20px;
	text-align:center;
}

.admin-login-link a{
	color:#1e6fb8;
	font-size:14px;
	text-decoration:none;
}

.admin-login-link a:hover{
	text-decoration:underline;
}

@media (max-width: 720px){
	body{
		align-items:flex-start;
		padding:24px 0;
	}

	.admin-login{
		grid-template-columns:1fr;
	}

	.admin-login-info,
	.admin-login-form-area{
		padding:36px 28px;
	}

	.admin-login-info h1{
		font-size:27px;
	}
}
</style>
</head>
<body>
	<main class="admin-login">
		<section class="admin-login-info">
			<h1>관리자 시스템</h1>
			<p>
				병원 예약, 회원, 의료진, 게시판 정보를 관리하는 내부 업무 화면입니다.
			</p>
		</section>

		<section class="admin-login-form-area">
			<h2>관리자 로그인</h2>

			<c:if test="${not empty sessionScope.adminLoginMessage}">
				<p class="admin-login-message" role="alert">
					<c:out value="${sessionScope.adminLoginMessage}" />
				</p>
				<c:remove var="adminLoginMessage" scope="session" />
			</c:if>

			<form class="admin-login-form" action="${pageContext.request.contextPath}/admin/login/process.do" method="post">
				<label for="adminId">아이디</label>
				<input type="text" id="adminId" name="adminId" autocomplete="username" autofocus>

				<label for="password">비밀번호</label>
				<input type="password" id="password" name="password" autocomplete="current-password">

				<button type="submit" class="admin-login-button">로그인</button>
			</form>

			<div class="admin-login-link">
				<a href="<c:url value='/main.do' />">사용자 메인으로 이동</a>
			</div>
		</section>
	</main>
</body>
</html>
