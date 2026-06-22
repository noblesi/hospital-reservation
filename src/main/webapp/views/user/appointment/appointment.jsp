<%@page import="java.util.Comparator"%>
<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("activeMenu", "hospital" ); request.setAttribute("depth1", "공통 레이아웃" );
	request.setAttribute("depth2", "사용자 화면 테스트" ); %>
	<!DOCTYPE html>
	<html lang="ko">

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>한국중앙병원 | 진료예약</title>

			<!-- Bootstrap CDN -->
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
			<!-- Bootstrap Icons CND -->
			<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

			<!-- 외부 CSS -->
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css">
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/appointment.css">

			<!-- jQuery CDN -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

			<!-- 외부 JS -->
			<script src="${pageContext.request.contextPath}/resources/js/appointment.js"></script>
		</head>

		<body>
			<!-- header -->
			<jsp:include page="/views/common/userHeader.jsp"></jsp:include>
			<jsp:include page="/views/common/userBreadcrumb.jsp"></jsp:include>

			<!-- main -->
			<div id="mainWrap">
				<div id="container">
					<div class="conHeader">
						<h2 class="title">인터넷 진료예약</h2>

						<div class="telDiv">
							<img alt=""
								src="${pageContext.request.contextPath}/resources/images/appointment/tel_icon.png"
								class="telIcon"> <strong class="tel">예약센터 1588-0000</strong>
						</div>
					</div>

					<!-- 진료과 선택 박스 -->
					<div class="deptWrap focusBorder">
						<!-- 검색, 정렬 바 -->
						<div class="searchBar">
							<div class="sortRadioDiv">
								<form action="appointment_useDB.jsp" method="get" id="sortFrm">
									<input type="radio" name="sortType" value="default" id="deRadio" class="form-check-input" checked="checked"> 
									<label for="deRadio" class="form-check-label">기본</label>
										
									<input type="radio" name="sortType" value="ascending" id="ascRadio" class="form-check-input">
									<label for="ascRadio" class="form-check-label">가나다순</label>
								</form>
							</div>
							<div class="dNameInputDiv">
								<input type="text" placeholder="질병명 또는 의료진명" id="dNameInput">
								<button id="searchBtn">
									<img class="searchImg"
										src="${pageContext.request.contextPath}/resources/images/appointment/search.png">
								</button>
							</div>
						</div>

						<!-- 진료과 목록 -->
						<div class="deptListDiv">
							<button type="button" class="btnPrev">
								<img class="arrowIcon"
									src="${pageContext.request.contextPath}/resources/images/appointment/left.png">
							</button>
							<div class="sliderWindow">
								<div class="sliderTrack">
									
								</div>
							</div>
							<button type="button" class="btnNext">
								<img class="arrowIcon"
									src="${pageContext.request.contextPath}/resources/images/appointment/right.png">
							</button>
						</div>
					</div>

					<div class="rsInfoWrap">
						<h4 class="rsInfoTitle">예약하실 정보확인</h4>
						<p class="rsInfoElm">
							환자명 : <span class="rsInfoName">홍길동(12345678)</span>
						</p>
						<p class="rsInfoElm">
							진료과 : <span class="rsInfoDept"></span>
						</p>
						<p class="rsInfoElm">
							의료진 : <span class="rsInfoDoctor"></span>
						</p>
						<p class="rsInfoElm">
							<span class="infoName">진료일시 : </span> <span class="rsInfoDate"></span>
						</p>
					</div>

					<div class="doctorListDiv">
						<h2 class="doctorListTitle">의료진 목록</h2>
						<div class="doctorListMain">
							<p class="noResult">
								위에서 <strong>진료과 선택</strong> 또는 <strong>질병명/의료진</strong> 검색을 먼저 해주세요.
							</p>
						</div>
					</div>

					<div class="scheduleDiv">
						<div class="scheduleCalDiv">
							<h2 class="scheduleCalTitle">진료일정</h2>
							<p class="result">
								의료진을 선택하시면<br> 진료일정을 확인 하실 수<br> 있습니다.
							</p>
							<div class="scheduleCal">
							</div>
							<div class="timeTableDiv">
							</div>
						</div>
						<button id="appointBtn">예약확정하기</button>
					</div>
				</div>
			</div>
			
			<div id="modalContainer" class="modalOverlay">
				<div class="modalContent">
					<div class="modalHeader">
						<h4 class="modalHeaderTitle">예약 내용 확인</h4>
						<button class="modalXBtn">
							<i class="bi bi-x-lg xIcon"></i>
						</button>
					</div>
					<div class="modalMain">
						<div class="specialtyDiv">
							<div class="specialtyTop">
								<i class="bi bi-exclamation-circle exclamationIcon specialtyBlue"></i>
								<h1 class="specialtyTitle"><span class="specialtyBlue">세부전공</span>을 <span class="specialtyBlue">확인</span> 해 주십시오.</h1>
								<p class="specialtyNotice">
									※ 정확히 선택하기 어려운 경우, 예약센터(1588-0000)로 문의후 예약<br>
									<span class="warning">※ 진료 분야가 맞지 않게 예약된 경우, 진료를 받을 수 없습니다.</span>
								</p>
							</div>
							<div class="specialtyMain">
								<h1 class="specialtyDoctorInfo">
									<span class="modalDept">알레르기면역내과</span>/<span class="madalDoctorName">홍길동</span>
								</h1>
								<p class="specialyInfo">세부전공: <span class="specialty specialtyBlue">약물 알레르기, 만성 기침, 등등등</span></p>
							</div>
							
							<div class="inputRequireDiv">
								<p class="inputDescript">아래 아프거나 불편하신 사항을 적어주세요.</p>
								<textarea id="requireTa" placeholder="아프신 곳을 적어주세요."></textarea>
							</div>
							
							<div class="checkBar">
								<div class="checkDiv">
									<input type="checkbox" class="checkInfo" value="y">
									<span>상기 내용을 확인했습니다.</span>
								</div>
							</div>
							<button id="confirmBtn" class="confirmBtn">확인</button>
						</div>
					</div>
				</div>
				
				<!-- 마지막 확인 창 -->
				<div class="lastConfirmDiv">
					<div class="lastConfirmHeader">
						<h2 class="lastConfirmHeaderTitle"><span class="userName">홍길동</span>님 진료예약하시겠습니까?</h2>
						<button class="modalXBtn">
							<i class="bi bi-x-lg xIcon"></i>
						</button>
					</div>
					<div class="lastConfirmMain">
						<div class="lastConfirmInfo">
							<p class="lastConfirmInfoP">
								<span class="confirmDate">2026년 12월 25일 10시 30분</span><br>
								<span class="confirmDept">알래르기내과</span> <span class="confirmDoctor">홍길동</span>
							</p>
						</div>
						<div class="lastConfirmBtnDiv">
							<button id="lastConfrimCancelBtn" class="lastConfrimCancelBtn">취소</button>
							<button id="lastConfrimBtn" class="lastConfrimBtn">확인</button>
						</div>
					</div>
				</div>
			</div>

			<!-- footer -->
			<jsp:include page="/views/common/userFooter.jsp"></jsp:include>

			<!-- 외부 JS -->
			<script src="${pageContext.request.contextPath}/resources/js/user-layout.js"></script>
		</body>

		</html>