/**
 * 회원가입 공통 JS
 * STEP02 약관동의, STEP03 회원정보 입력 처리
 */
$(function() {

    /* ==============================
       STEP02 약관 동의
    ============================== */

    $("#checkboxAll").on("click", function() {
        var checked = $(this).is(":checked");

        $("#checkbox01").prop("checked", checked);
        $("#checkbox02").prop("checked", checked);
        $("#checkbox03").prop("checked", checked);
        $("#checkbox04").prop("checked", checked);
    });

    $("#gBeforeBtn").on("click", function() {
        location.href = "joinType.jsp";
    });

    $("#gNextBtn").on("click", function() {

        if(!$("#checkbox01").is(":checked")){
            alert("서비스 이용약관에 동의해주세요.");
            $("#checkbox01").focus();
            return;
        }

        if(!$("#checkbox02").is(":checked")){
            alert("개인정보 수집 및 이용 동의에 체크해주세요.");
            $("#checkbox02").focus();
            return;
        }

        if(!$("#checkbox03").is(":checked")){
            alert("민감정보 수집 및 이용 동의에 체크해주세요.");
            $("#checkbox03").focus();
            return;
        }

        if($("#checkbox04").length > 0 && !$("#checkbox04").is(":checked")){
            alert("법정대리인 동의에 체크해주세요.");
            $("#checkbox04").focus();
            return;
        }

        $("#gForm").submit();
    });

    /* ==============================
       STEP03 회원정보 입력
    ============================== */

    $("#emailDomain").on("change", function(){
        var domain = $(this).val();
        $("#email2").val(domain);
    });

    function setDateOption(yearId, monthId, dateId){

        var year = $("#" + yearId).val();
        var month = $("#" + monthId).val();

        $("#" + dateId).empty();
        $("#" + dateId).append("<option value=''>일</option>");

        if(year == "" || month == ""){
            return;
        }

        var lastDay = new Date(year, month, 0).getDate();

        for(var i = 1; i <= lastDay; i++){
            var day = i < 10 ? "0" + i : String(i);

            $("#" + dateId).append(
                "<option value='" + day + "'>" + day + "</option>"
            );
        }
    }

    $("#year, #month").on("change", function(){
        setDateOption("year", "month", "date");
    });

    $("#childYear, #childMonth").on("change", function(){
        setDateOption("childYear", "childMonth", "childDate");
    });

    $("#hp2, #hp3").on("input", function(){
        $(this).val(
            $(this).val().replace(/[^0-9]/g, "")
        );
    });

    // 비밀번호 조건 검사
    $("#pass").on("keyup", function(){

        var pass = $(this).val();

        var passRegex = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*(),.;])[A-Za-z0-9!@#$%^&*(),.;]{9,16}$/;

        if(pass == ""){
            $(".errorPass").text("");
            return;
        }

        if(passRegex.test(pass)){
            $(".errorPass")
                .text("사용 가능한 비밀번호입니다.")
                .css("color", "blue");
        }else{
            $(".errorPass")
                .text("영문, 숫자, 특수문자를 포함한 9~16자로 입력해주세요.")
                .css("color", "red");
        }

    });

    // 비밀번호 확인
    $("#passConfirm").on("keyup", function(){

        var pass = $("#pass").val();
        var passConfirm = $(this).val();

        if(passConfirm == ""){
            $(".error").text("");
            return;
        }

        if(pass === passConfirm){
            $(".error")
                .text("비밀번호가 일치합니다.")
                .css("color", "blue");
        }else{
            $(".error")
                .text("비밀번호가 일치하지 않습니다.")
                .css("color", "red");
        }

    });
	/* ==============================
	   STEP03 이전 단계
	============================== */

	$("#gFormBeforeBtn").on("click", function(){

	    var joinType = $("#join_type").val();

	    if(joinType == "TG"){
	        location.href = "joinAgreeCommon.jsp?join_type=TG";
	        return;
	    }

	    if(joinType == "TC"){
	        location.href = "joinAgreeChild.jsp?join_type=TC";
	        return;
	    }

	});
	
	/* ==============================
	   STEP03 다음 단계
	============================== */

	$("#gFormNextBtn").on("click", function(){

	    // 아이디
	    if($("#id").val().trim() == ""){
	        alert("아이디를 입력해주세요.");
	        $("#id").focus();
	        return;
	    }

	    // 비밀번호
	    if($("#pass").val().trim() == ""){
	        alert("비밀번호를 입력해주세요.");
	        $("#pass").focus();
	        return;
	    }

	    // 비밀번호 확인
	    if($("#passConfirm").val().trim() == ""){
	        alert("비밀번호 확인을 입력해주세요.");
	        $("#passConfirm").focus();
	        return;
	    }

	    // 비밀번호 일치 여부
	    if($("#pass").val() != $("#passConfirm").val()){
	        alert("비밀번호가 일치하지 않습니다.");
	        $("#passConfirm").focus();
	        return;
	    }

	    // 이름
	    if($("#name").val().trim() == ""){
	        alert("이름을 입력해주세요.");
	        $("#name").focus();
	        return;
	    }

	    // 생년월일
	    if($("#year").val() == "" ||
	       $("#month").val() == "" ||
	       $("#date").val() == ""){

	        alert("생년월일을 선택해주세요.");
	        $("#year").focus();
	        return;
	    }

	    // 휴대폰
	    if($("#hp1").val() == "" ||
	       $("#hp2").val() == "" ||
	       $("#hp3").val() == ""){

	        alert("휴대전화번호를 입력해주세요.");
	        $("#hp1").focus();
	        return;
	    }

	    // 이메일
	    if($("#email1").val().trim() == "" ||
	       $("#email2").val().trim() == ""){

	        alert("이메일을 입력해주세요.");
	        $("#email1").focus();
	        return;
	    }

	    // 주소
	    if($("#sample6_postcode").val().trim() == "" ||
	       $("#sample6_address").val().trim() == "" ||
	       $("#sample6_detailAddress").val().trim() == ""){

	        alert("주소를 입력해주세요.");
	        return;
	    }

	    // 성별
	    if(!$("input[name='sex']:checked").length){
	        alert("성별을 선택해주세요.");
	        return;
	    }

	    /* ==========================
	       미성년자 전용
	    ========================== */

	    if($("#childName").length > 0){

	        if($("#childName").val().trim() == ""){
	            alert("환자 이름을 입력해주세요.");
	            $("#childName").focus();
	            return;
	        }

	        if($("#childYear").val() == "" ||
	           $("#childMonth").val() == "" ||
	           $("#childDate").val() == ""){

	            alert("환자 생년월일을 선택해주세요.");
	            $("#childYear").focus();
	            return;
	        }

	        if(!$("input[name='childSex']:checked").length){
	            alert("환자 성별을 선택해주세요.");
	            return;
	        }

	        if($("#relationshipType").val() == ""){
	            alert("보호자와의 관계를 선택해주세요.");
	            $("#relationshipType").focus();
	            return;
	        }
	    }

	    // 이메일 조합
	    if($("#email").length){
	        $("#email").val(
	            $("#email1").val() + "@" + $("#email2").val()
	        );
	    }

	    // 생년월일 조합
	    if($("#birth").length){
	        $("#birth").val(
	            $("#year").val() + "-" +
	            $("#month").val() + "-" +
	            $("#date").val()
	        );
	    }

	    // 휴대폰 조합
	    if($("#hp_no").length){
	        $("#hp_no").val(
	            $("#hp1").val() +
	            $("#hp2").val() +
	            $("#hp3").val()
	        );
	    }

	    $("#memberVo").submit();

	});
});