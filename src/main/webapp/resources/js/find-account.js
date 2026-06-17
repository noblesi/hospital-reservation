/**
 * 아이디/비밀번호 찾기
 * 모달 열기/ 닫기 및 입력값 검증
 */
$(function(){

    var birthNumCheck = /^[0-9]*$/;

    $(".layerBtn").click(function(e){
        e.preventDefault();

        var layerClass = $(this).data("layer");

        $(".layerDim").addClass("on");
        $("." + layerClass).addClass("on");
    });

    $(".layerCloseBtn, .layerDim").click(function(){
        closeLayer();
    });

    $("#confirmHpBtn").click(function(){

        var name = $("#hpName");
        var phoneNumber = $("#hpPhoneNumber");
        var birthDate = $("#hpBirthDate");

        if(name.val().trim() == "" ||
           phoneNumber.val().trim() == "" ||
           birthDate.val().trim() == ""){

            alert("모든 사항은 필수 입력입니다.");
            return;
        }

        var birth = birthDate.val().replace(/-/g, "");

        if(!birthNumCheck.test(birth) || birth.length != 8){
            alert("생년월일 정보를 한번 더 확인해 주십시오.");
            birthDate.focus();
            return;
        }

        $("#hForm").submit();
    });

    $("#confirmMailBtn").click(function(){

        var name = $("#mailName");
        var email = $("#mailEmail");
        var birthDate = $("#mailBirthDate");

        if(name.val().trim() == "" ||
           email.val().trim() == "" ||
           birthDate.val().trim() == ""){

            alert("모든 사항은 필수 입력입니다.");
            return;
        }

        var birth = birthDate.val().replace(/-/g, "");

        if(!birthNumCheck.test(birth) || birth.length != 8){
            alert("생년월일 정보를 한번 더 확인해 주십시오.");
            birthDate.focus();
            return;
        }

        $("#mForm").submit();
    });

    function closeLayer(){
        $(".layerDim").removeClass("on");
        $(".layerWrap").removeClass("on");
    }

});

