/**
 * 
 */
var pageLength = $(".slTab").length - 1;
var curPage = 0;

$(function() {
    /* 진료과 리스트 넘기는 기능 */

    $(".btnNext").click(function() {
        if (curPage < pageLength) {
            curPage = moveRightPage(curPage);
        }
    });

    $(".btnPrev").click(function() {
        if (curPage > 0) {
            curPage = moveLeftPage(curPage);
        }
    });


    /* 정렬 기능 */
    $("input[name='sortType']").on("change", sortDept);
    $("input[name='sortType']:checked").trigger("change");

    /* 진료과 선택 시 */
    $(".deptWrap").on("click", ".deptRadio", deptHandler);

    /*의료진 선택 시*/
    $(".doctorListMain").on("click", ".selectDoctorBtn", handleDoctorSelect);

	/* 달력 버튼 클릭 시 */
	$(".scheduleCal").on("click", ".nextMonthBtn", nextMonth);
	$(".scheduleCal").on("click", ".prevMonthBtn", prevMonth);
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

/* 진료과를 정렬하는 업무 */
function sortDept() {
    var selectedSort = $(this).val(); // "default" 또는 "ascending"

    $.ajax({
        url: "appointment_ajax.jsp",    
        type: "GET",                   
        data: {action: "sort", sort: selectedSort },   
        success: function(receivedHtml) {
            $(".sliderTrack").html(receivedHtml);

            curPage = 0;
            $(".sliderTrack").css("left", "0px");
        },
        error: function() {
            alert("통신 실패!");
        }
    });
	
}

/* 진료과를 선택한 후 업무 */
function deptHandler() {
    /* 선택한 진료과에 CSS 적용 */
    $(".slTab label").removeClass("selectDept");
    $(this).next("label").addClass("selectDept");
	
    $(".deptWrap").removeClass("focusBorder");
    $(".doctorListDiv").addClass("focusBorder");

    /* 기존에 있던 진료과 선택 문구 태그를 지운다. */
    $(".noResult").remove();

    /* 선택한 진료과 코드를 가져온다. */
    var deptNo = $(this).val();
	var deptName = $("label[for='" + deptNo +"']").text();

	/*예약 정보 확인에 선택한 과 출력*/
	$(".rsInfoDept").html(deptName);
	
    /* HTML 태그를 생성한다 */
	$.ajax({
		url: "appointment_ajax.jsp",
		type: "GET",
		data: {action: "doctorList", deptNo: deptNo, deptName: deptName},
		success: function(receivedHtml) {
			/* 생성된 HTML 태그를 보여준다 */
			$(".doctorListMain").html(receivedHtml)
		}
		
	});
	
}

/* 의료진 선택 후 업무 */
function handleDoctorSelect() {
	/* CSS 강조 설정 */
    $(".selectDoctorBtn").removeClass("selectedBtn");
    $(".doctorThumnail").removeAttr("style");
    $(".result").attr("style", "display: none;");
    $(".doctorListDiv").removeClass("focusBorder");
	
    $(".scheduleCalDiv").addClass("focusBorder");
    $(this).addClass("selectedBtn");
    $(this).closest(".doctorLi").find(".doctorThumnail").attr("style", "border: 2px solid #2763ba");
	
	renderCal();
}

/* 달력 출력 */
function renderCal(year, month) {
	var dln = $(".selectedBtn").val();
	
	$.ajax({
			url: "appointment_ajax.jsp",
			type: "GET",
			data: {action: "schedule", dln: dln, year: year, month: month},
			success: function(recivedHtml) {
				$(".scheduleCal").html(recivedHtml);
			}
		});
}

function nextMonth() {
	var year = Number($(".year").text());
	var month = Number($(".month").text());
	
	month = month + 1;
	
	if(month == 13) {
		month = 1;
		year = year + 1;
	}
	
	renderCal(year, month);
}

function prevMonth() {
	var year = Number($(".year").text());
	var month = Number($(".month").text());
	
	month = month - 1;
	
	if(month == 0) {
		month = 12;
		year = year - 1;
	}
	
	renderCal(year, month);
}

