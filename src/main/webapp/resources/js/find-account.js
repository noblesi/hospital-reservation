/**
 * 아이디/비밀번호 찾기 공통 JS
 * - 인증 방식 선택 모달 열기/닫기
 * - 휴대전화/이메일 입력값 검증
 * - 비밀번호 재설정 입력값 검증
 */
$(function() {

    // 생년월일 숫자 검증 정규식
    var birthNumCheck = /^[0-9]*$/;

    /**
     * 회원정보 확인 성공 후 비밀번호 재설정 모달을 자동으로 연다.
     */
    if($(".resetPasswordLayer").data("autoOpen") === true){
        $(".layerWrap").removeClass("on");
        $(".layerDim").addClass("on");
        $(".resetPasswordLayer").addClass("on");
    }

    /**
     * 인증 방식 버튼 클릭 시 해당 모달을 연다.
     */
    $(".layerBtn").on("click", function(event) {
        event.preventDefault();

        var layerClass = $(this).data("layer");

        $(".layerDim").addClass("on");
        $("." + layerClass).addClass("on");
    });

    /**
     * 닫기 버튼 또는 배경 클릭 시 모든 모달을 닫는다.
     */
    $(".layerCloseBtn, .layerDim").on("click", function() {
        closeLayer();
    });

    /**
     * 휴대전화 인증 폼을 검증한 후 전송한다.
     */
    $("#confirmHpBtn").on("click", function() {
        var loginId = $("#hpLoginId");
        var name = $("#hpName");
        var phoneNumber = $("#hpPhoneNumber");
        var birthDate = $("#hpBirthDate");
        var loginIdRequired = loginId.length > 0;

        if((loginIdRequired && loginId.val().trim() === "") ||
           name.val().trim() === "" ||
           phoneNumber.val().trim() === "" ||
           birthDate.val().trim() === ""){
            alert("모든 사항은 필수 입력입니다.");
            return;
        }

        if(!checkBirthDate(birthDate)){
            return;
        }

        // 회원가입 시 저장한 형식과 동일하게 숫자만 전송한다.
        phoneNumber.val(phoneNumber.val().replace(/[^0-9]/g, ""));
        birthDate.val(formatBirthDate(birthDate.val()));

        $("#hForm").submit();
    });

    /**
     * 이메일 인증 폼을 검증한 후 전송한다.
     */
    $("#confirmMailBtn").on("click", function() {
        var loginId = $("#mailLoginId");
        var name = $("#mailName");
        var email = $("#mailEmail");
        var birthDate = $("#mailBirthDate");
        var loginIdRequired = loginId.length > 0;

        if((loginIdRequired && loginId.val().trim() === "") ||
           name.val().trim() === "" ||
           email.val().trim() === "" ||
           birthDate.val().trim() === ""){
            alert("모든 사항은 필수 입력입니다.");
            return;
        }

        if(!checkBirthDate(birthDate)){
            return;
        }

        birthDate.val(formatBirthDate(birthDate.val()));

        $("#mForm").submit();
    });

    /**
     * 새 비밀번호와 확인값이 일치할 때 재설정 폼을 전송한다.
     */
    $(document).on("click", "#resetPasswordBtn", function() {
        var newPassword = $("#newPassword");
        var confirmPassword = $("#confirmPassword");

        if(newPassword.val().trim() === ""){
            alert("새 비밀번호를 입력해주세요.");
            newPassword.focus();
            return;
        }

        if(confirmPassword.val().trim() === ""){
            alert("새 비밀번호 확인을 입력해주세요.");
            confirmPassword.focus();
            return;
        }

        if(newPassword.val() !== confirmPassword.val()){
            alert("비밀번호가 일치하지 않습니다.");
            confirmPassword.focus();
            return;
        }

        $("#resetForm").submit();
    });

    /**
     * 생년월일 형식과 실제 날짜를 검증한다.
     *
     * @param {jQuery} birthDate 생년월일 입력 요소
     * @returns {boolean} 올바른 날짜이면 true
     */
    function checkBirthDate(birthDate) {
        var birth = birthDate.val().replace(/-/g, "");

        if(!birthNumCheck.test(birth) || birth.length !== 8){
            alert("생년월일 정보를 한번 더 확인해 주십시오.");
            birthDate.focus();
            return false;
        }

        var year = Number(birth.substring(0, 4));
        var month = Number(birth.substring(4, 6));
        var day = Number(birth.substring(6, 8));
        var date = new Date(year, month - 1, day);

        if(date.getFullYear() !== year ||
           date.getMonth() !== month - 1 ||
           date.getDate() !== day){
            alert("올바른 생년월일을 입력해 주십시오.");
            birthDate.focus();
            return false;
        }

        return true;
    }

    /**
     * YYYYMMDD 또는 YYYY-MM-DD 값을 YYYY-MM-DD 형식으로 변환한다.
     */
    function formatBirthDate(value) {
        var birth = value.replace(/-/g, "");

        return birth.substring(0, 4) + "-" +
               birth.substring(4, 6) + "-" +
               birth.substring(6, 8);
    }

    /**
     * 모든 인증 모달을 닫는다.
     */
    function closeLayer() {
        $(".layerDim").removeClass("on");
        $(".layerWrap").removeClass("on");
    }

});
