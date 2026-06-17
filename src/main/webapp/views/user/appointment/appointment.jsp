<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<% request.setAttribute("activeMenu", "hospital" ); request.setAttribute("depth1", "공통 레이아웃" );
		request.setAttribute("depth2", "사용자 화면 테스트" ); %>
		<!DOCTYPE html>
		<html lang="ko">

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>한국중앙병원 - 진료예약</title>

			<!-- Bootstrap CDN -->
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
				integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
				crossorigin="anonymous">
			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
				integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
				crossorigin="anonymous"></script>

			<!-- 외부 CSS -->
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user-layout.css">
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/appointment.css">

			<!-- jQuery CDN -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
			<script type="text/javascript">
				$(function () {
					/* 진료과 리스트 넘기는 기능 */
					var pageLength = $(".slTab").length - 1;
					var curPage = 0;

					$(".btnNext").click(function () {
						if (curPage < pageLength) {
							curPage = moveRightPage(curPage);
						}
					});

					$(".btnPrev").click(function () {
						if (curPage > 0) {
							curPage = moveLeftPage(curPage);
						}
					});

					/* 진료과 선택 시 의료진 보여주는 기능 */
					$(".deptRadio").click(function () {
						/* 선택한 진료과에 CSS 적용 */
						$(".slTab label").removeClass("selectDept");
					
						var deptNode = $(this).next("label");
						deptNode.addClass("selectDept");
						
						/* 기존에 있던 진료과 선택 문구 태그를 지운다. */
						$(".noResult").remove();
						
						/* 선택한 진료과 코드를 가져온다. */
						var deptNo = $(this).val();
						
						/* HTML 태그를 생성한다 */
						var listTag = "";
						
						/* 생성한 태그를 div 박스 안으로 넣는다. */
						$(".doctorListMain").html("HTML 태그들...");
						
						
					});

				});

				function moveRightPage(curPage) {
					curPage++;
					var amount = -700 / curPage;

					$(".sliderTrack").animate({
						left: amount + "px"
					}, 400);

					return curPage;
				}

				function moveLeftPage(curPage) {
					curPage--;
					if (curPage == 0) {
						var amount = 0;
					} else {
						var amount = 700 / curPage;
					}

					$(".sliderTrack").animate({
						left: amount + "px"
					}, 400);

					return curPage;
				}
				
			</script>
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
								<input type="radio" name="sort" value="default" id="deRadio" class="form-check-input"
									checked="checked"> <label for="deRadio" class="form-check-label">기본</label> <input
									type="radio" name="sort" value="ascending" id="ascRadio" class="form-check-input">
								<label for="ascRadio" class="form-check-label">가나다순</label>
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
							<!-- java class 구현 후 JSP로 생성하는 코드로 추후 변경 예정 -->
							<button type="button" class="btnPrev">
								<img class="arrowIcon"
									src="${pageContext.request.contextPath}/resources/images/appointment/left.png">
							</button>
							<div class="sliderWindow">
								<div class="sliderTrack">
									<div class="sliderPage">
										<table class="slTab">
											<tr class="slRow">
												<td class="slCol">
													<input type="radio" value="DP001" class="deptRadio" id="deptx1"
														style="display: none;"> <label for="deptx1">내과0</label>
												</td>
												<td class="slCol">
													<input type="radio" value="DP002" class="deptRadio" id="deptx2"
														style="display: none;"> <label for="deptx2">외과0</label>
												</td>
												<td class="slCol">
													<input type="radio" value="DP003" class="deptRadio" id="deptx3"
														style="display: none;"> <label for="deptx3">소아과0</label>
												</td>
											</tr>
											<tr class="slRow">
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">내과</label>
												</td>
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">외과</label>
												</td>
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">소아과</label>
												</td>
											</tr>
											<tr class="slRow">
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">내과</label>
												</td>
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">외과</label>
												</td>
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">소아과</label>
												</td>
											</tr>
										</table>
									</div>

									<div class="sliderPage">
										<table class="slTab">
											<tr class="slRow">
												<td class="slCol">
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">내과1</label>
												</td>
												<td class="slCol">
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">외과1</label>
												</td>
												<td class="slCol">
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">소아과1</label>
												</td>
											</tr>
											<tr class="slRow">
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">내과</label>
												</td>
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">외과</label>
												</td>
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">소아과</label>
												</td>
											</tr>
											<tr class="slRow">
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">내과</label>
												</td>
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">외과</label>
												</td>
												<td>
													<input type="radio" value="진료과코드" class="deptRadio"
														style="display: none;"> <label for="dept1">소아과</label>
												</td>
											</tr>
										</table>
									</div>
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
							진료일시 : <span class="rsInfoDate"></span>
						</p>
					</div>

					<div class="doctorListDiv">
						<h2 class="doctorListTitle">의료진 목록</h2>
						<div class="doctorListMain">
							<p class="noResult" style="display: none;">
								위에서 <strong>진료과 선택</strong> 또는 <strong>질병명/의료진</strong> 검색을 먼저 해주세요.
							</p>
							<ul class="doctorUl">
								<li class="doctorLi">
									<img class="doctorThumnail" src="https://www.snuh.org/upload/med/dr/1029516_01017_01.jpg">
									<div class="doctorInfoDiv">
										<h4 class="doctorName">구본권 <img></h4>
										<p class="detail">
											<strong class="deptName">순환기내과</strong><br>
											세부전공: 협십증, 흉통, 관상동맥, 심근경색, 심장
										</p>
									</div>
									<button class="selectDoctorBtn">선택</button>
								</li>
								<li class="doctorLi"></li>
							</ul>
						</div>
					</div>

					<div class="scheduleDiv">
						<div class="scheduleCalDiv">
							<h2 class="schduleCalTitle">진료일정</h2>
							<div class="scheduleCal">
								<p class="result">
									의료진을 선택하시면<br> 진료일정을 확인 하실 수<br> 있습니다.
								</p>
							</div>
						</div>
						<button id="appointBtn">예약확정하기</button>
					</div>
				</div>
			</div>

			<!-- footer -->
			<jsp:include page="/views/common/userFooter.jsp"></jsp:include>

			<!-- 외부 JS -->
			<script src="${pageContext.request.contextPath}/resources/js/user-layout.js"></script>
		</body>

		</html>