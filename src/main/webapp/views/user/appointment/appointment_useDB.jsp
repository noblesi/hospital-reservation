<%@page import="java.util.Comparator"%>
<%@page import="com.hospital.user.appointment.UserAppointmentService"%>
<%@page import="com.hospital.common.DepartmentDTO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String sortType = request.getParameter("sortType");
    if (sortType == null) sortType = "default"; // 기본값
    
	UserAppointmentService uas = new UserAppointmentService();
	List<DepartmentDTO> deptList = uas.searchDepartmentList();
	
	if(sortType.equals("ascending")) {
		deptList.sort(Comparator.comparing(DepartmentDTO :: getDeptName));
	}
	
	int totalCnt = deptList.size();
	DepartmentDTO deptDTO = null;
%>
<% request.setAttribute("activeMenu", "hospital" ); request.setAttribute("depth1", "공통 레이아웃" );
	request.setAttribute("depth2", "사용자 화면 테스트" ); %>
	<!DOCTYPE html>
	<html lang="ko">

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>한국중앙병원 - 진료예약</title>

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
									<input type="radio" name="sortType" value="default" id="deRadio" class="form-check-input" <%= sortType.equals("default") ? "checked='checked'" : "" %>> 
									<label for="deRadio" class="form-check-label">기본</label>
										
									<input type="radio" name="sortType" value="ascending" id="ascRadio" class="form-check-input" <%= sortType.equals("ascending") ? "checked='checked'" : "" %>>
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
									<%
										for(int i = 0; i < totalCnt; i++) {
											if(i % 9 == 0) {
									%>
												<div class="sliderPage">
													<table class="slTab">							
									<%
											} // end if
											
											if(i % 3 == 0) {
									%>
												<tr class="slRow">
									<%
											} // end if
											deptDTO = deptList.get(i);
									%>
											<td class="slCol">
												<input 	type="radio" 
														title="<%= deptDTO.getDeptName() %>" 
														value="<%= deptDTO.getDeptNo() %>" 
														class="deptRadio" 
														id="deptx<%= i %>" 
														style="display: none;"> 
												<label for="deptx<%= i %>"><%= deptDTO.getDeptName() %></label>
											</td>										
									<%
											if(i % 3 == 2 || i == totalCnt - 1) {
												out.println("</tr>");
											}
									
											if(i % 9 == 8 || i == totalCnt - 1) {
												out.println("</table>");
												out.println("</div>");
											}
									
										} // end for
									%>
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
							<p class="noResult"">
								위에서 <strong>진료과 선택</strong> 또는 <strong>질병명/의료진</strong> 검색을 먼저 해주세요.
							</p>
							
						</div>
					</div>

					<div class="scheduleDiv">
						<div class="scheduleCalDiv">
							<h2 class="scheduleCalTitle">진료일정</h2>
							<p class="result" style="display: none;">
								의료진을 선택하시면<br> 진료일정을 확인 하실 수<br> 있습니다.
							</p>
							<div class="scheduleCal">
								<div class="moveMonthBar">
									<button class="prevMonthBtn">
										<i class="bi bi-arrow-left-circle"></i>
									</button>
									<h4 class="nowMonthTitle">2026년 6월</h4>
									<button class="nextMonthBtn">
										<i class="bi bi-arrow-right-circle"></i>
									</button>
								</div>
								<table class="calTab">
									<thead>
										<tr class="weekTr">
											<th style="color: #ee1c24">일</th>
											<th>월</th>
											<th>화</th>
											<th>수</th>
											<th>목</th>
											<th>금</th>
											<th style="color: #02348b">토</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>1</td>
											<td>2</td>
											<td>3</td>
											<td>4</td>
											<td>5</td>
											<td>6</td>
											<td>7</td>
										</tr>
										<tr>
											<td>8</td>
											<td>9</td>
											<td>10</td>
											<td>11</td>
											<td>12</td>
											<td>13</td>
											<td>14</td>
										</tr>
										<tr>
											<td>15</td>
											<td>16</td>
											<td>17</td>
											<td>18</td>
											<td>19</td>
											<td>20</td>
											<td>21</td>
										</tr>
										<tr>
											<td>22</td>
											<td>23</td>
											<td>24</td>
											<td>25</td>
											<td>26</td>
											<td>27</td>
											<td>28</td>
										</tr>
										<tr>
											<td>29</td>
											<td>30</td>
											<td>1</td>
											<td>2</td>
											<td>3</td>
											<td>4</td>
											<td>5</td>
										</tr>
									</tbody>
								</table>
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