$(function() {
    var pageLength = $(".slTab").length;
    var curPage = 0;
    
    var deptName;
    var dln;
    var specialty;
    var appointmentDate;
    var appointmentTime;
    
    /* [이벤트 바인딩] */
    // 페이지 이동
    $(".btnNext").click(function() {
        if (curPage < pageLength) curPage = moveToNextPage(curPage);
    });
    $(".btnPrev").click(function() {
        if (curPage > 0) curPage = moveToPrevPage(curPage);
    });

    // 진료과 정렬 및 선택
    $("input[name='sortType']").on("change", handleDeptSort);
    $("input[name='sortType']:checked").trigger("change");
    $(".deptWrap").on("click", ".deptRadio", handleDeptSelect);
        
    // 의료진 검색
    $("#searchBtn").on("click", handleDoctorSearch);
    $("#dNameInput").on("keydown", function(e) {
        if(e.keyCode == 13) $("#searchBtn").trigger("click");
    });
    
    // 의료진 선택
    $(".doctorListMain").on("click", ".selectDoctorBtn", handleDoctorSelect);

    // 달력 월 변경 및 날짜/시간 선택
    $(".scheduleCal").on("click", ".nextMonthBtn", changeToNextMonth);
    $(".scheduleCal").on("click", ".prevMonthBtn", changeToPrevMonth);
    $(".scheduleDiv").on("click", ".available", handleDateSelect);
    $(".scheduleDiv").on("click", ".timeTableLi", handleTimeSelect);
    
    // 모달 및 예약 완료
    $("#appointBtn").on("click", handleAppointSubmit);
    $(".modalXBtn").on("click", closeModal);
    $("#confirmBtn").on("click", handleRequirementConfirm);
    $("#lastConfrimCancelBtn").on("click", closeModal);
    $("#lastConfrimBtn").on("click", sendRequestAppointment);
});

function getAppointmentConfig() {
    return window.hospitalAppointmentConfig || {};
}

function getAppointmentAjaxUrl() {
    return getAppointmentConfig().ajaxUrl || "ajax.do";
}

function getAppointmentProcessUrl() {
    return getAppointmentConfig().processUrl || "process.do";
}

/* [UI 및 포커스 제어 함수] */
function removeFocusBorder() {
    $(".deptWrap").removeClass("focusBorder");
    $(".doctorListDiv").removeClass("focusBorder");
    $(".scheduleCalDiv").removeClass("focusBorder");
    $(".timeTableDiv").removeClass("focusBorder");
}

function closeModal() {
    $(".modalOverlay").removeClass("show");
    $(".modalContent").removeClass("show");
    $(".lastConfirmDiv").removeClass("show");
}

/* [페이지 슬라이더 관련 함수] */
function moveToNextPage(curPage) {
    curPage++;
    var amount = -700 / curPage;
    $(".sliderTrack").animate({ left: amount + "px" }, 400);
    return curPage;
}

function moveToPrevPage(curPage) {
    curPage--;
    var amount = (curPage == 0) ? 0 : 700 / curPage;
    $(".sliderTrack").animate({ left: amount + "px" }, 400);
    return curPage;
}

/* [비즈니스 로직 및 Ajax 렌더링 함수] */
function renderCalendar(year, month) {
    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        data: { action: "schedule", dln: dln, year: year, month: month },
        success: function(recivedHtml) {
            $(".scheduleCal").html(recivedHtml);
        }
    });
}

function renderTimeTable(selectedDate) {
    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        data: { action: "timeTable", date: selectedDate, dln: dln},
        success: function(recivedHtml) {
           $(".timeTableDiv").html(recivedHtml);
        }
    });
}

/* [이벤트 핸들러 함수 (handle...)] */
function handleDeptSort() {
    var selectedSort = $(this).val();
    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        data: { action: "sort", sort: selectedSort },
        success: function(receivedHtml) {
            $(".sliderTrack").html(receivedHtml);
            pageLength = $(".slTab").length;
            curPage = 0;
            $(".sliderTrack").css("left", "0px");
        },
        error: function() { alert("통신 실패!"); }
    });
}

function handleDeptSelect() {
    $(".slTab label").removeClass("selectDept");
    $(this).next("label").addClass("selectDept");
    
    removeFocusBorder();
    $(".doctorListDiv").addClass("focusBorder");

    $(".noResult").attr("style", "display: none;");
    $(".scheduleCal").attr("style", "display: none;");
    $(".timeTableDiv").attr("style", "display: none;");
    $(".rsInfoDoctor").text("");
    $(".rsInfoDate").text("");
    
    var deptNo = $(this).val();
    deptName = $("label[for='" + deptNo + "']").text();
    $(".rsInfoDept").html(deptName);

    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        data: { action: "doctorList", deptNo: deptNo, deptName: deptName },
        success: function(receivedHtml) {
            $(".doctorListMain").html(receivedHtml);
        }
    });
}

function handleDoctorSearch() {
    var keyword = $("#dNameInput").val();
    if(keyword.trim() == "") {
        alert("검색하실 단어를 입력해주세요.");
        return;
    }
    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        data: { action: "searchDoctor", keyword: keyword },
        success: function(receivedHtml) {
            $(".doctorListMain").html(receivedHtml);
        }
    });
}

function handleDoctorSelect() {
    $(".selectDoctorBtn").removeClass("selectedBtn");
    $(".doctorThumnail").removeAttr("style");
    $(".result").attr("style", "display: none;");
    $(".timeTableDiv").attr("style", "display: none;");
    
    removeFocusBorder();
    $(".scheduleCal").removeAttr("style");
    $(".scheduleCalDiv").addClass("focusBorder");
    
    $(this).addClass("selectedBtn");
    $(this).closest(".doctorLi").find(".doctorThumnail").attr("style", "border: 2px solid #2763ba");
    
    dln = $(".selectedBtn").val();
    $("#apptDln").val(dln);
    specialty = $(this).closest(".doctorLi").find(".specialty").text();
    
    renderCalendar();

    var doctorName = $(this).closest(".doctorLi").find(".doctorName").text().trim();
    $(".rsInfoDoctor").text(doctorName);
}

function changeToNextMonth() {
    var year = Number($(".year").text());
    var month = Number($(".month").text());
    month++;
    if (month == 13) { month = 1; year++; }
    renderCalendar(year, month);
}

function changeToPrevMonth() {
    var year = Number($(".year").text());
    var month = Number($(".month").text());
    month--;
    if (month == 0) { month = 12; year--; }
    renderCalendar(year, month);
}

function handleDateSelect() {
    $(".available").removeClass("selectedDay");
    $(this).addClass("selectedDay");
    $(".timeTableDiv").removeAttr("style");
    
    appointmentDate = $(this).data("date");
    $("#apptDate").val(appointmentDate);
    
    renderTimeTable(appointmentDate);
    removeFocusBorder();
    $(".timeTableDiv").addClass("focusBorder");
}

function handleTimeSelect() {
    $(".timeTableLi").removeClass("selectedTime");
    $(this).addClass("selectedTime");
    
    appointmentTime = $(this).text();
    $("#apptTime").val(appointmentTime);
    $(".rsInfoDate").html(appointmentDate + "<br>" + appointmentTime);
}

function handleAppointSubmit() {
    if(dln == null) { alert("의료진을 선택해 주세요"); return; }
    if(appointmentDate == null) { alert("예약 날짜를 선택해 주세요"); return; }
    if(appointmentTime == null) { alert("예약 시간을 선택해 주세요"); return; }
    
    $(".modalDept").text(deptName);
    $(".madalDoctorName").text($(".rsInfoDoctor").text());
    $(".specialty").text(specialty);
    $("#modalContainer").addClass("show");
    $(".modalContent").addClass("show");
}

function handleRequirementConfirm() {
    var isCheck = $(".checkInfo").is(":checked");
    var requirement = $("#requireTa").val();
    
    if(requirement.trim() == "") {
        alert("아프신 곳을 입력해 주세요.");
        return;
    }
    
    if(isCheck) {
        $(".modalContent").removeClass("show");
        $(".lastConfirmDiv").addClass("show");
        $("#apptRequire").val(requirement);
        
        $(".confirmDate").text(appointmentDate + " " + appointmentTime);
        $(".confirmDept").text(deptName);
        $(".confirmDoctor").text($(".rsInfoDoctor").text());
    } else {
        alert("체크박스를 확인해 주세요.");
    }
}

function sendRequestAppointment() {
	/* 
	1. 사용자가 입력한 값을 검증한다.
	2. 입력받은 값에 문제 없으면 back-end로 값을 전달한다.
	3. DB에 예약정보가 저장되면 예약완료 페이지로 이동한다.
	*/
	var form = $("<form>", {
		method: "POST",
		action: getAppointmentProcessUrl()
	});

	form.append($("<input>", { type: "hidden", name: "doctorLicenseNo", value: dln }));
	form.append($("<input>", { type: "hidden", name: "appointmentDate", value: appointmentDate }));
	form.append($("<input>", { type: "hidden", name: "appointmentTime", value: $.trim(appointmentTime) }));
	form.append($("<input>", { type: "hidden", name: "requirement", value: $("#requireTa").val() }));

	$("body").append(form);
	form.submit();
}
