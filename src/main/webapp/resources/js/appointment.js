/**
 * 
 */
var pageLength = $(".slTab").length - 1;
var curPage = 0;

var deptName;
var dln;
var specialty;
var appointmentDate;
var appointmentTime;

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

    /* 날짜 선택 시 */
    $(".scheduleDiv").on("click", ".available", selectDate);
	
	/* 시간 선택 시 */
	$(".scheduleDiv").on("click", ".timeTableLi", selectTime);
	
	/* 예약 확정하기 버튼 클릭 시 */
	$("#appointBtn").on("click", appointHandler)
	
	$(".modalXBtn").on("click", closeModal)
	
	/* modal 창 확인 버튼 클릭 시 */
	$("#confirmBtn").on("click", confirmAppointment)
	
	/* 마지막 confirm 창 */
	$("#lastConfrimCancelBtn").on("click", closeModal);
	$("#lastConfrimBtn").on("click", sendRequestAppointment)
});

function removeFocusBorder() {
	$(".deptWrap").removeClass("focusBorder");
	$(".doctorListDiv").removeClass("focusBorder");
	$(".scheduleCalDiv").removeClass("focusBorder");
	$(".timeTableDiv").removeClass("focusBorder");
}

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
        data: { action: "sort", sort: selectedSort },
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
	
    removeFocusBorder();
	
	$(".doctorListDiv").addClass("focusBorder");

    /* 기존에 있던 진료과 선택 문구 태그를 지운다. */
    $(".noResult").remove();

    /* 선택한 진료과 코드를 가져온다. */
    var deptNo = $(this).val();
    deptName = $("label[for='" + deptNo + "']").text();

    /*예약 정보 확인에 선택한 과 출력*/
    $(".rsInfoDept").html(deptName);

    /* HTML 태그를 생성한다 */
    $.ajax({
        url: "appointment_ajax.jsp",
        type: "GET",
        data: { action: "doctorList", deptNo: deptNo, deptName: deptName },
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
	
    removeFocusBorder();

    $(".scheduleCalDiv").addClass("focusBorder");
    $(this).addClass("selectedBtn");
    $(this).closest(".doctorLi").find(".doctorThumnail").attr("style", "border: 2px solid #2763ba");

	dln = $(".selectedBtn").val();
	$("#apptDln").val(dln);
	
	specialty = $(this).closest(".doctorLi").find(".specialty").text();
    renderCal();

    var doctorName = $(this).closest(".doctorLi").find(".doctorName").text().trim();
    $(".rsInfoDoctor").text(doctorName);
}

/* 달력 출력 */
function renderCal(year, month) {

    $.ajax({
        url: "appointment_ajax.jsp",
        type: "GET",
        data: { action: "schedule", dln: dln, year: year, month: month },
        success: function(recivedHtml) {
            $(".scheduleCal").html(recivedHtml);
        }
    });
}

function nextMonth() {
    var year = Number($(".year").text());
    var month = Number($(".month").text());

    month = month + 1;

    if (month == 13) {
        month = 1;
        year = year + 1;
    }

    renderCal(year, month);
}

function prevMonth() {
    var year = Number($(".year").text());
    var month = Number($(".month").text());

    month = month - 1;

    if (month == 0) {
        month = 12;
        year = year - 1;
    }

    renderCal(year, month);
}

/* 날짜 선택 */
function selectDate() {
    /* 클릭한 날짜에 달력 css 추가 */
    $(".available").removeClass("selectedDay");
    $(this).addClass("selectedDay");
	

	appointmentDate = $(this).data("date");
	$("#apptDate").val(appointmentDate);
	
    renderTimeTable(appointmentDate);
	
	removeFocusBorder();
	$(".timeTableDiv").addClass("focusBorder");
}

/* 시간 테이블 출력 */
function renderTimeTable(selectedDate) {
    $.ajax({
        url: "appointment_ajax.jsp",
        type: "GET",
        data: { action: "timeTable", date: selectedDate, dln: dln},
        success: function(recivedHtml) {
           $(".timeTableDiv").html(recivedHtml);
        }
    });
}

/* 시간 선택 */
function selectTime() {
	$(".timeTableLi").removeClass("selectedTime");
	$(this).addClass("selectedTime");
	
	appointmentTime = $(this).text();
	$("#apptTime").val(appointmentTime);
	
	$(".rsInfoDate").html(appointmentDate + "<br>" + appointmentTime);
}

function appointHandler() {
	/* 의료진과 예약 날짜가 정상적으로 선택되어있으면 모달창을 보여준다. */
	if(dln == null) {
		alert("의료진을 선택해 주세요");
		return;
	}
	
	if(appointmentDate == null) {
		alert("예약 날짜를 선택해 주세요")
		return;
	}
	
	if(appointmentTime == null) {
		alert("예약 시간을 선택해 주세요")
		return;
	}
	
	$(".modalDept").text(deptName);
	$(".madalDoctorName").text($(".rsInfoDoctor").text());
	$(".specialty").text(specialty);
	$("#modalContainer").addClass("show");
	$(".modalContent").addClass("show");
}

/* modal창 숨기기 */
function closeModal() {
	$(".modalOverlay").removeClass("show");
	$(".modalContent").removeClass("show");
	$(".lastConfirmDiv").removeClass("show");
}

/* 요구사항 입력 확인 */
function confirmAppointment() {
	var isCheck = $(".checkInfo").is(":checked");
	
	if(isCheck) {
		$(".modalContent").removeClass("show");
		$(".lastConfirmDiv").addClass("show");
		
		$("#apptRequire").val($("#requireTa").val());
		
		$(".confirmDate").text(appointmentDate + " " + appointmentTime);
		$(".confirmDept").text(deptName);
		$(".confirmDoctor").text($(".rsInfoDoctor").text());
	} else {
		alert("체크박스를 확인해 주세요.")
	}
	
}

function sendRequestAppointment() {
	/* 
	1. 사용자가 입력한 값을 검증한다.
	2. 입력받은 값에 문제 없으면 back-end로 값을 전달한다.
	3. DB에 예약정보가 저장되면 예약완료 페이지로 이동한다.
	*/
	/*location.href = "appointmentSuccess.jsp";*/
	
	$("#apptFrm").submit();
}