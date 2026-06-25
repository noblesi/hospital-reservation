/**
 * 회원가입 공통 JS
 * - STEP02 약관동의
 * - STEP03 회원정보 입력 보조
 * - 아이디 중복확인
 * - 회원가입 유효성 검사
 */
$(function() {

    /* =========================================================
       공통 변수 / 정규식
    ========================================================= */

    // 비밀번호: 영문, 숫자, 특수문자를 포함한 9~16자
    var passRegex = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*(),.;])[A-Za-z0-9!@#$%^&*(),.;]{9,16}$/;
    // 아이디: 한글 3자 이상 또는 영문+숫자 혼용 6~12자
    var idRegex = /^(?:[가-힣]{3,}|(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,12})$/;

    /* =========================================================
       카카오 우편번호 검색
    ========================================================= */

    /**
     * 카카오 우편번호 검색 결과를 회원가입 주소 입력란에 반영한다.
     */
    $("#addressSearchButton").on("click", function() {
        if(typeof daum === "undefined" || !daum.Postcode){
            alert("우편번호 서비스를 불러오지 못했습니다.");
            return;
        }

        new daum.Postcode({
            oncomplete: function(data) {
                var address = data.userSelectedType === "R"
                    ? data.roadAddress
                    : data.jibunAddress;
                var extraAddress = "";

                if(data.userSelectedType === "R"){
                    if(data.bname !== "" && /[동로가]$/.test(data.bname)){
                        extraAddress += data.bname;
                    }

                    if(data.buildingName !== "" && data.apartment === "Y"){
                        extraAddress += extraAddress !== ""
                            ? ", " + data.buildingName
                            : data.buildingName;
                    }

                    if(extraAddress !== ""){
                        extraAddress = " (" + extraAddress + ")";
                    }
                }

                $("#sample6_postcode").val(data.zonecode);
                $("#sample6_address").val(address);
                $("#sample6_extraAddress").val(extraAddress);
                $("#sample6_detailAddress").focus();
            }
        }).open();
    });


    /* =========================================================
       STEP02 약관동의
    ========================================================= */

    // 전체 동의 체크
    $("#checkboxAll").on("click", function() {
        var checked = $(this).is(":checked");

        $("#checkbox01").prop("checked", checked); // 서비스 이용약관
        $("#checkbox02").prop("checked", checked); // 개인정보 수집 이용
        $("#checkbox03").prop("checked", checked); // 민감정보 수집 이용
        $("#checkbox04").prop("checked", checked); // 법정대리인 동의
    });

    // STEP02 이전 단계
    $("#gBeforeBtn").on("click", function() {
        location.href = "joinType.jsp";
    });

    // STEP02 다음 단계
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

        // 미성년자 회원가입 화면에만 존재하는 항목
        if($("#checkbox04").length > 0 && !$("#checkbox04").is(":checked")){
            alert("법정대리인 동의에 체크해주세요.");
            $("#checkbox04").focus();
            return;
        }

        $("#gForm").submit();
    });


    /* =========================================================
       STEP03 입력 보조 기능
    ========================================================= */

    // 아이디가 변경되면 중복확인을 다시 해야 함
    $("#id").on("input", function() {
        $("#idChecked").val("N");
    });

    // 이메일 도메인 선택 시 도메인 입력칸에 값 설정
    $("#emailDomain").on("change", function() {
        $("#email2").val($(this).val());
    });

    /**
     * 연도, 월 선택 시 해당 월의 마지막 날짜까지 일자 생성
     * ex) 2024년 02월 -> 29일, 2025년 04월 -> 30일
     */
    function setDateOption(yearId, monthId, dateId) {
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

    // 일반회원 또는 보호자 생년월일
    $("#year, #month").on("change", function() {
        setDateOption("year", "month", "date");
    });

    // 미성년자 환자 생년월일
    $("#childYear, #childMonth").on("change", function() {
        setDateOption("childYear", "childMonth", "childDate");
    });

    // 휴대전화 중간/끝자리는 숫자만 입력
    $("#hp2, #hp3").on("input", function() {
        $(this).val(
            $(this).val().replace(/[^0-9]/g, "")
        );
    });

    // 비밀번호 조건 실시간 검사
    $("#pass").on("keyup", function() {
        var pass = $(this).val();

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

    // 비밀번호 확인 실시간 검사
    $("#passConfirm").on("keyup", function() {
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


    /* =========================================================
       STEP03 이전 단계
    ========================================================= */

    $("#gFormBeforeBtn").on("click", function() {
        var joinType = $("#join_type").val();

        if(joinType == "TG"){
            location.href = "joinAgreeCommon.jsp?join_type=TG";
            return;
        }

        if(joinType == "TC"){
            location.href = "joinAgreeChild.jsp?join_type=TC";
            return;
        }

        // join_type 값이 비어있는 경우 화면 요소 기준으로 판단
        if($("#childName").length > 0){
            location.href = "joinAgreeChild.jsp?join_type=TC";
        }else{
            location.href = "joinAgreeCommon.jsp?join_type=TG";
        }
    });


    /* =========================================================
       아이디 중복확인
       팝업 JSP에서 DB 중복 여부를 확인한 뒤 부모창에 결과를 반영한다.
    ========================================================= */

    $("#idChkBtn").on("click", function() {
        var loginId = $("#id").val().trim();

        if(loginId == ""){
            alert("아이디를 입력해주세요.");
            $("#id").focus();
            return;
        }

        if(!idRegex.test(loginId)){
            alert("아이디는 한글 3자 이상 또는 영문+숫자 혼용 6~12자로 입력해주세요.");
            $("#id").focus();
            return;
        }

        window.open(
            "idDup.jsp?id=" + encodeURIComponent(loginId),
            "idDup",
            "width=380,height=360,top=150,left=500"
        );
    });


    /* =========================================================
       STEP03 다음 단계 유효성 검사
    ========================================================= */

    $("#gFormNextBtn").on("click", function() {

        /* ------------------------------
           공통 회원정보 검사
        ------------------------------ */

        if($("#id").val().trim() == ""){
            alert("아이디를 입력해주세요.");
            $("#id").focus();
            return;
        }

        if(!idRegex.test($("#id").val().trim())){
            alert("아이디는 한글 3자 이상 또는 영문+숫자 혼용 6~12자로 입력해주세요.");
            $("#id").focus();
            return;
        }

        if($("#idChecked").val() != "Y"){
            alert("아이디 중복확인을 해주세요.");
            $("#idChkBtn").focus();
            return;
        }

        if($("#pass").val().trim() == ""){
            alert("비밀번호를 입력해주세요.");
            $("#pass").focus();
            return;
        }

        if(!passRegex.test($("#pass").val())){
            alert("비밀번호는 영문, 숫자, 특수문자를 포함한 9~16자로 입력해주세요.");
            $("#pass").focus();
            return;
        }

        if($("#passConfirm").val().trim() == ""){
            alert("비밀번호 확인을 입력해주세요.");
            $("#passConfirm").focus();
            return;
        }

        if($("#pass").val() != $("#passConfirm").val()){
            alert("비밀번호가 일치하지 않습니다.");
            $("#passConfirm").focus();
            return;
        }

        if($("#name").val().trim() == ""){
            alert("이름을 입력해주세요.");
            $("#name").focus();
            return;
        }

        if($("#year").val() == "" || $("#month").val() == "" || $("#date").val() == ""){
            alert("생년월일을 선택해주세요.");
            $("#year").focus();
            return;
        }

        if($("#hp1").val() == "" || $("#hp2").val() == "" || $("#hp3").val() == ""){
            alert("휴대전화번호를 입력해주세요.");
            $("#hp1").focus();
            return;
        }

        if($("#hp2").val().length < 3 || $("#hp3").val().length < 4){
            alert("휴대전화번호를 정확히 입력해주세요.");
            $("#hp2").focus();
            return;
        }

        if($("#email1").val().trim() == "" || $("#email2").val().trim() == ""){
            alert("이메일을 입력해주세요.");
            $("#email1").focus();
            return;
        }

        if($("#sample6_postcode").val().trim() == "" ||
           $("#sample6_address").val().trim() == "" ||
           $("#sample6_detailAddress").val().trim() == ""){

            alert("주소를 입력해주세요.");
            $("#sample6_postcode").focus();
            return;
        }

        if(!$("input[name='gender']:checked").length){
            alert("성별을 선택해주세요.");
            return;
        }


        /* ------------------------------
           미성년자 환자정보 검사
        ------------------------------ */

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

            if(!$("input[name='childGender']:checked").length){
                alert("환자 성별을 선택해주세요.");
                return;
            }

            if($("#relationshipType").val() == ""){
                alert("보호자와의 관계를 선택해주세요.");
                $("#relationshipType").focus();
                return;
            }
        }


        /* ------------------------------
           전송 전 hidden 값 조합
        ------------------------------ */

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

        // 휴대전화번호 조합
        if($("#hp_no").length){
            $("#hp_no").val(
                $("#hp1").val() +
                $("#hp2").val() +
                $("#hp3").val()
            );
        }

        // 모든 검사 통과 후 회원가입 form 전송
        $("#memberVo").submit();
    });

});
