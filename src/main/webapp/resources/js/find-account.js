/**
 * 아이디/비밀번호 찾기
 * - 인증 방식 선택 모달 열기/닫기
 * - 휴대전화/이메일 입력값 검증
 * - 비밀번호 재설정 입력값 검증
 */
$(function(){

    // 생년월일 숫자 검증 정규식
    var birthNumCheck = /^[0-9]*$/;

    /**
     * 인증 방식 버튼 클릭 시 해당 모달 열기
     * data-layer 값과 같은 class명을 가진 레이어를 연다.
     */
    $(".layerBtn").on("click", function(e){
        e.preventDefault();

        var layerClass = $(this).data("layer");

        $(".layerDim").addClass("on");
        $("." + layerClass).addClass("on");
    });

    /**
     * 닫기 버튼 또는 배경 클릭 시 모달 닫기
     */
    $(".layerCloseBtn, .layerDim").on("click", function(){
        closeLayer();
    });

    /**
     * 휴대전화 인증 확인 버튼
     */
    $("#confirmHpBtn").on("click", function(){

        var loginId = $("#hpLoginId");
        var name = $("#hpName");
        var phoneNumber = $("#hpPhoneNumber");
        var birthDate = $("#hpBirthDate");

        if(loginId.val().trim() === "" ||
           name.val().trim() === "" ||
           phoneNumber.val().trim() === "" ||
           birthDate.val().trim() === ""){

            alert("모든 사항은 필수 입력입니다.");
            return;
        }

        if(!checkBirthDate(birthDate)){
            return;
        }

        $("#hForm").submit();
    });

    /**
     * 이메일 인증 확인 버튼
     */
    $("#confirmMailBtn").on("click", function(){

        var loginId = $("#mailLoginId");
        var name = $("#mailName");
        var email = $("#mailEmail");
        var birthDate = $("#mailBirthDate");

        if(loginId.val().trim() === "" ||
           name.val().trim() === "" ||
           email.val().trim() === "" ||
           birthDate.val().trim() === ""){

            alert("모든 사항은 필수 입력입니다.");
            return;
        }

        if(!checkBirthDate(birthDate)){
            return;
        }

        $("#mForm").submit();
    });

    /**
     * 비밀번호 재설정 모달 확인 버튼
     * 새 비밀번호와 확인 비밀번호가 일치할 때만 submit 한다.
     */
    $(document).on("click", "#resetPasswordBtn", function(){

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
     * 생년월일 형식 검증
     * 입력 예: 1970-01-01 또는 19700101
     */
    function checkBirthDate(birthDate){

        var birth = birthDate.val().replace(/-/g, "");

        if(!birthNumCheck.test(birth) || birth.length !== 8){
            alert("생년월일 정보를 한번 더 확인해 주십시오.");
            birthDate.focus();
            return false;
        }

        return true;
    }

    /**
     * 모든 모달 닫기
     */
    function closeLayer(){
        $(".layerDim").removeClass("on");
        $(".layerWrap").removeClass("on");
    }

});