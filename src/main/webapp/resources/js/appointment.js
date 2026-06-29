$(function() {
    var pageLength = $(".slTab").length;
    var curPage = 0;
    
    var deptName;
    var dln;
    var specialty;
    var appointmentDate;
    var appointmentTime;
    
    /* [이벤트 바인딩] */
    $(".btnNext").click(function() {
        if (curPage < pageLength) curPage = moveToNextPage(curPage);
    });
    $(".btnPrev").click(function() {
        if (curPage > 0) curPage = moveToPrevPage(curPage);
    });

    $("input[name='sortType']").on("change", handleDeptSort);
    $("input[name='sortType']:checked").trigger("change");
    $(".deptWrap").on("click", ".deptRadio", handleDeptSelect);
        
    $("#searchBtn").on("click", handleDoctorSearch);
    $("#dNameInput").on("keydown", function(e) {
        if (e.keyCode == 13) $("#searchBtn").trigger("click");
    });
    
    $(".doctorListMain").on("click", ".selectDoctorBtn", handleDoctorSelect);

    $(".scheduleCal").on("click", ".nextMonthBtn", changeToNextMonth);
    $(".scheduleCal").on("click", ".prevMonthBtn", changeToPrevMonth);
    $(".scheduleDiv").on("click", ".available", handleDateSelect);
    $(".scheduleDiv").on("click", ".timeTableLi", handleTimeSelect);
    
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

/* [HTML 생성 함수] */

// 진료과 목록 HTML 생성
function buildDepartmentHtml(data) {
    var html = "";
    $.each(data, function(i, dept) {
        if (i % 9 === 0) html += "<div class='sliderPage'><table class='slTab'>";
        if (i % 3 === 0) html += "<tr class='slRow'>";

        html += "<td class='slCol'>"
              + "<input class='deptRadio' style='display:none;' type='radio' name='dept'"
              + " value='" + dept.deptNo + "' id='" + dept.deptNo + "'>"
              + "<label for='" + dept.deptNo + "'>" + dept.deptName + "</label>"
              + "</td>";

        if (i % 3 === 2 || i === data.length - 1) html += "</tr>";
        if (i % 9 === 8 || i === data.length - 1) html += "</table></div>";
    });
    return html;
}

// 의사 카드 HTML 생성
function buildDoctorHtml(data) {
    if (data.doctors.length === 0) {
        return "<p class='noResult'>조건에 일치하는 의료진이 없습니다.</p>";
    }

    var html = "<ul class='doctorUl'>";
    $.each(data.doctors, function(i, doctor) {
        if (i % 2 === 0) html += "<div class='col'>";

        html += "<li class='doctorLi'>"
              + "<img class='doctorThumnail' src='" + doctor.thumbnailUrl + "'>"
              + "<div class='doctorInfoDiv'>"
              + "<h4 class='doctorName'>" + doctor.name
              + "<a href='#void'><i class='bi bi-search blueSearchIcon'></i></a></h4>"
              + "<p class='detail'>"
              + (data.deptName ? "<strong class='deptName'>" + data.deptName + "</strong><br>" : "")
              + "세부전공: <span class='specialty'>" + doctor.specialty + "</span>"
              + "</p></div>"
              + "<button class='selectDoctorBtn' value='" + doctor.doctorLicenseNo + "'>"
              + "<i class='bi bi-check-circle checkIcon'></i> 선택"
              + "</button></li>";

        if (i % 2 === 1 || i === data.doctors.length - 1) html += "</div>";
    });
    html += "</ul>";
    return html;
}

// 달력 HTML 생성
function buildCalendarHtml(data) {
    var prevYear  = data.month === 1  ? data.year - 1 : data.year;
    var prevMonth = data.month === 1  ? 12 : data.month - 1;
    var nextYear  = data.month === 12 ? data.year + 1 : data.year;
    var nextMonth = data.month === 12 ? 1  : data.month + 1;

    var html = "<div class='moveMonthBar'>"
             + "<button class='prevMonthBtn'><i class='bi bi-arrow-left-circle'></i></button>"
             + "<h4 class='nowMonthTitle'>"
             + "<span class='year'>" + data.year + "</span>년 "
             + "<span class='month'>" + data.month + "</span>월"
             + "</h4>"
             + "<button class='nextMonthBtn'><i class='bi bi-arrow-right-circle'></i></button>"
             + "</div>"
             + "<table class='calTab'><thead><tr class='weekTr'>"
             + "<th style='color:#ee1c24'>일</th>"
             + "<th>월</th><th>화</th><th>수</th><th>목</th><th>금</th>"
             + "<th style='color:#02348b'>토</th>"
             + "</tr></thead><tbody><tr>";

    for (var i = 0; i < data.blankCount; i++) {
        html += "<td><span></span></td>";
    }

    $.each(data.days, function(i, d) {
        var attr = d.status
            ? " class='available " + d.status + "' data-date='" + d.date + "'"
            : "";
        html += "<td><span" + attr + ">" + d.day + "</span></td>";
        if (d.isSaturday) html += "</tr><tr>";
    });

    html += "</tr></tbody></table>"
          + "<div class='infoCal'>"
          + "<span class='am ex'></span> <span>오전</span> "
          + "<span class='pm ex'></span> <span>오후</span> "
          + "<span class='allDay ex'></span> <span>종일</span>"
          + "</div>";
    return html;
}

// 시간표 HTML 생성
function buildTimeTableHtml(data) {
    if (data.times.length === 0) {
        return "<p style='text-align:center; font-size:18px;'>"
             + "해당 일자의 예약이<br>모두 완료되었습니다.<br>다른 날짜를 선택해주세요.</p>";
    }

    var html = "<ul class='timeTableUl'>";
    $.each(data.times, function(i, time) {
        html += "<li class='timeTableLi'>" + time + "</li>";
    });
    html += "</ul>";
    return html;
}

/* [Ajax 렌더링 함수] */
function renderCalendar(year, month) {
    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        dataType: "json",
        data: { action: "schedule", dln: dln, year: year, month: month },
        success: function(data) {
            $(".scheduleCal").html(buildCalendarHtml(data));
        },
        error: function() { alert("달력 로딩 실패"); }
    });
}

function renderTimeTable(selectedDate) {
    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        dataType: "json",
        data: { action: "timeTable", date: selectedDate, dln: dln },
        success: function(data) {
            $(".timeTableDiv").html(buildTimeTableHtml(data));
        },
        error: function() { alert("시간표 로딩 실패"); }
    });
}

/* [이벤트 핸들러 함수] */
function handleDeptSort() {
    var selectedSort = $(this).val();
    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        dataType: "json",
        data: { action: "sort", sort: selectedSort },
        success: function(data) {
            $(".sliderTrack").html(buildDepartmentHtml(data));
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
    $(".rsInfoDept").text(deptName);

    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        dataType: "json",
        data: { action: "doctorList", deptNo: deptNo, deptName: deptName },
        success: function(data) {
            $(".doctorListMain").html(buildDoctorHtml(data));
        },
        error: function() { alert("의사 목록 로딩 실패"); }
    });
}

function handleDoctorSearch() {
    var keyword = $("#dNameInput").val();
    if (keyword.trim() == "") {
        alert("검색하실 단어를 입력해주세요.");
        return;
    }
    $.ajax({
        url: getAppointmentAjaxUrl(),
        type: "GET",
        dataType: "json",
        data: { action: "searchDoctor", keyword: keyword },
        success: function(data) {
            $(".doctorListMain").html(buildDoctorHtml(data));
        },
        error: function() { alert("의사 검색 실패"); }
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
    var year  = Number($(".year").text());
    var month = Number($(".month").text());
    month++;
    if (month == 13) { month = 1; year++; }
    renderCalendar(year, month);
}

function changeToPrevMonth() {
    var year  = Number($(".year").text());
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
    if (dln == null)             { alert("의료진을 선택해 주세요");   return; }
    if (appointmentDate == null) { alert("예약 날짜를 선택해 주세요"); return; }
    if (appointmentTime == null) { alert("예약 시간을 선택해 주세요"); return; }

    $(".modalDept").text(deptName);
    $(".madalDoctorName").text($(".rsInfoDoctor").text());
    $(".specialty").text(specialty);
    $("#modalContainer").addClass("show");
    $(".modalContent").addClass("show");
}

function handleRequirementConfirm() {
    var isCheck     = $(".checkInfo").is(":checked");
    var requirement = $("#requireTa").val();

    if (requirement.trim() == "") {
        alert("아프신 곳을 입력해 주세요.");
        return;
    }

    if (isCheck) {
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
    var form = $("<form>", {
        method: "POST",
        action: getAppointmentProcessUrl()
    });

    form.append($("<input>", { type: "hidden", name: "doctorLicenseNo",  value: dln }));
    form.append($("<input>", { type: "hidden", name: "appointmentDate",  value: appointmentDate }));
    form.append($("<input>", { type: "hidden", name: "appointmentTime",  value: $.trim(appointmentTime) }));
    form.append($("<input>", { type: "hidden", name: "requirement",      value: $("#requireTa").val() }));

    $("body").append(form);
    form.submit();
}