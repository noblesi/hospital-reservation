/**
 * 
 */
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

    /* 진료과 선택 시 */
	$(".deptWrap").on("click", ".deptRadio", deptHandler)
	
	/*의료진 선택 시*/
	$(".doctorListMain").on("click", ".selectDoctorBtn", handleDoctorSelect);
	
	$("input[name='sortType']").on("change", function() {
	            var selectedSort = $(this).val(); // "default" 또는 "ascending"
				console.log(selectedSort);
	            
	            $.ajax({
	                url: "appointment_ajax.jsp",     // [어디로?] 호출할 자바 파일 주소 (Model 1이므로 JSP 지정)
	                type: "GET",                    // [어떻게?] 데이터 전송 방식
	                data: { sort: selectedSort },   // [뭘 들고?] 자바 파일로 보낼 파라미터 (sort=값)
	                success: function(receivedHtml) { // [성공하면?] 자바가 out.print로 뱉어낸 HTML이 담김
	                    
	                    $(".sliderTrack").html(receivedHtml);
	                    
	                    curPage = 0;
	                    $(".sliderTrack").css("left", "0px");
	                },
	                error: function() {
	                    alert("통신 실패!");
	                }
	            });
	        });
	        
	        $("input[name='sortType']:checked").trigger("change");
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

function deptHandler() {
	console.log("진료과 선택");
	        /* 선택한 진료과에 CSS 적용 */
	        $(".slTab label").removeClass("selectDept");
			$(".deptWrap").removeClass("focusBorder");
			
			$(".doctorListDiv").addClass("focusBorder");

	        var deptNode = $(this).next("label");
	        deptNode.addClass("selectDept");
			
			/*예약 정보 확인에 선택한 과 출력*/
			$(".rsInfoDept").html($(this).attr("title"));

	        /* 기존에 있던 진료과 선택 문구 태그를 지운다. */
	        $(".noResult").remove();

	        /* 선택한 진료과 코드를 가져온다. */
	        var deptNo = $(this).val();

	        /* HTML 태그를 생성한다 */
	        var listTag = `<ul class="doctorUl">
									<div class="col">
										<li class="doctorLi">
											<img class="doctorThumnail"
												src="http://localhost/hospital-reservation/resources/images/appointment/tempdoctor.jpg">
											<div class="doctorInfoDiv">
												<h4 class="doctorName">구본권 <a href="#void"><img class="searchBlueIcon" src="http://localhost/hospital-reservation/resources/images/appointment/search_blue.png"></a></h4>
												<p class="detail">
													<strong class="deptName">순환기내과</strong><br>
													세부전공: 협십증, 흉통, 관상동맥, 심근경색, 심장
												</p>
											</div>
											<button class="selectDoctorBtn">
												<i class="bi bi-check-circle checkIcon"></i>
												선택
											</button>
	                                        </li>
	                                        <li class="doctorLi" style="margin-left: 20px;	">
											<img class="doctorThumnail"
	                                        src="http://localhost/hospital-reservation/resources/images/appointment/tempdoctor.jpg">
											<div class="doctorInfoDiv">
	                                        <h4 class="doctorName">구본권 <a href="#void"><img class="searchBlueIcon" src="http://localhost/hospital-reservation/resources/images/appointment/search_blue.png"></a></h4>
	                                        <p class="detail">
	                                        <strong class="deptName">순환기내과</strong><br>
	                                        세부전공: 제2형당뇨병, 제1형당뇨병, 가족성당뇨병, 임신성당뇨병, 고지혈증, 비만, 대사증후군
	                                        </p>
											</div>
											<button class="selectDoctorBtn">
												<img class="checkIcon" src="http://localhost/hospital-reservation/resources/images/appointment/check.png">
												선택
											</button>
										</li>
									</div>
									<div class="col">
										<li class="doctorLi">
											<img class="doctorThumnail"
												src="http://localhost/hospital-reservation/resources/images/appointment/tempdoctor.jpg">
											<div class="doctorInfoDiv">
												<h4 class="doctorName">구본권 <img></h4>
												<p class="detail">
													<strong class="deptName">순환기내과</strong><br>
													세부전공: 협십증, 흉통, 관상동맥, 심근경색, 심장
												</p>
											</div>
											<button class="selectDoctorBtn">선택</button>
										</li>
										<li class="doctorLi" style="margin-left: 20px;	">
											<img class="doctorThumnail"
												src="http://localhost/hospital-reservation/resources/images/appointment/tempdoctor.jpg">
											<div class="doctorInfoDiv">
												<h4 class="doctorName">구본권 <img></h4>
												<p class="detail">
													<strong class="deptName">순환기내과</strong><br>
													세부전공: 제2형당뇨병, 제1형당뇨병, 가족성당뇨병, 임신성당뇨병, 고지혈증, 비만, 대사증후군
												</p>
											</div>
											<button class="selectDoctorBtn">선택</button>
										</li>
									</div>
									<div class="col">
										<li class="doctorLi">
											<img class="doctorThumnail"
												src="http://localhost/hospital-reservation/resources/images/appointment/tempdoctor.jpg">
											<div class="doctorInfoDiv">
												<h4 class="doctorName">구본권 <img></h4>
												<p class="detail">
													<strong class="deptName">순환기내과</strong><br>
													세부전공: 협십증, 흉통, 관상동맥, 심근경색, 심장
												</p>
											</div>
											<button class="selectDoctorBtn">선택</button>
										</li>
										<li class="doctorLi" style="margin-left: 20px;	">
											<img class="doctorThumnail"
												src="http://localhost/hospital-reservation/resources/images/appointment/tempdoctor.jpg">
											<div class="doctorInfoDiv">
												<h4 class="doctorName">구본권 <img></h4>
												<p class="detail">
													<strong class="deptName">순환기내과</strong><br>
													세부전공: 제2형당뇨병, 제1형당뇨병, 가족성당뇨병, 임신성당뇨병, 고지혈증, 비만, 대사증후군
												</p>
											</div>
											<button class="selectDoctorBtn">선택</button>
										</li>
									</div>
									<div class="col">
										<li class="doctorLi">
											<img class="doctorThumnail"
												src="https://www.snuh.org/upload/med/dr/1029516_01017_01.jpg">
											<div class="doctorInfoDiv">
												<h4 class="doctorName">구본권 <img></h4>
												<p class="detail">
													<strong class="deptName">순환기내과</strong><br>
													세부전공: 협십증, 흉통, 관상동맥, 심근경색, 심장
												</p>
											</div>
											<button class="selectDoctorBtn">선택</button>
										</li>
									</div>
								</ul>`;

	        /* 생성한 태그를 div 박스 안으로 넣는다. */
	        $(".doctorListMain").html(listTag);
}

function handleDoctorSelect () {
	$(".selectDoctorBtn").removeClass("selectedBtn");
	$(".doctorThumnail").removeAttr("style");
	$(".doctorListDiv").removeClass("focusBorder");
	$(".result").remove();
	
	$(".scheduleCalDiv").addClass("focusBorder");
	
	
	$(this).addClass("selectedBtn");
	$(this).closest(".doctorLi").find(".doctorThumnail").attr("style", "border: 2px solid #2763ba");
	
	/*진료 일정 달력*/
}