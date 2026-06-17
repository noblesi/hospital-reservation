/**
 * 회원가입 STEP02
 * 약관 동의 및 다음 단계 처리
 */
$(function() {

    // 전체 동의
    $("#checkbox03").on("click", function() {

        var checked = $(this).is(":checked");

        $("#checkbox01").prop("checked", checked);
        $("#checkbox02").prop("checked", checked);
        $("#checkbox04").prop("checked", checked);
    });

    // 일반 회원 다음 단계
    $("#gNextBtn").on("click", function() {

        if(!$("#checkbox01").is(":checked")){
            alert("이용약관 동의에 체크해주세요.");
            $("#checkbox01").focus();
            return;
        }

        if(!$("#checkbox02").is(":checked")){
            alert("개인정보처리방침 동의에 체크해주세요.");
            $("#checkbox02").focus();
            return;
        }

        var snsAgreeYn = $("#checkbox04").is(":checked");

        $("#snsAgreeYn").val(
            snsAgreeYn ? "Y" : "N"
        );

        $("#gForm").submit();
    });

    // SNS 회원가입 버튼
    $("#snsBtn").on("click", function() {

        if(!$("#checkbox01").is(":checked")){
            alert("이용약관 동의에 체크해주세요.");
            return;
        }

        if(!$("#checkbox02").is(":checked")){
            alert("개인정보처리방침 동의에 체크해주세요.");
            return;
        }

        $("#snsForm").submit();
    });

});