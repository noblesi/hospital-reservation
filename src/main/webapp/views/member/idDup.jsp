<%@page import="com.hospital.member.MemberRegisterService"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
request.setCharacterEncoding("UTF-8");

String loginId = request.getParameter("id");
boolean checked = false;
boolean idAvailable = false;
boolean validPattern = false;

if(loginId != null){
    loginId = loginId.trim();
}

if(loginId != null && !"".equals(loginId)){
    // 아이디는 한글 3자 이상 또는 영문+숫자 혼용 6~12자만 허용한다.
    validPattern = loginId.matches("^[가-힣]{3,}$|^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,12}$");

    if(validPattern){
        MemberRegisterService service = new MemberRegisterService();

        // 입력한 아이디가 이미 사용 중인지 DB에서 확인한다.
        idAvailable = !service.checkLoginIdDuplicate(loginId);
    }
    checked = true;
}

pageContext.setAttribute("loginId", loginId);
pageContext.setAttribute("checked", checked);
pageContext.setAttribute("idAvailable", idAvailable);
pageContext.setAttribute("validPattern", validPattern);
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>아이디 중복확인</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<style>
body {
    margin: 0;
    background: #fff;
    font-family: "Noto Sans KR", Arial, sans-serif;
    color: #333;
}

.idDupWrap {
    width: 100%;
    min-height: 100vh;
    padding: 36px 30px 28px;
    border: 1px solid #ddd;
    background: #fff;
    box-sizing: border-box;
    text-align: center;
}

.idDupWrap h2 {
    margin: 0 0 16px;
    color: #222;
    font-size: 25px;
    font-weight: 400;
    letter-spacing: -1.5px;
}

.idDupWrap h2 strong {
    font-size: 24px;
    font-weight: 800;
    letter-spacing: -2px;
}

.idDesc {
    margin: 0 0 18px;
    color: #777;
    font-size: 12px;
    line-height: 1.6;
}

.idInputRow {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 6px;
    color: #222;
    font-size: 14px;
}

.idInputRow input {
    width: 145px;
    height: 34px;
    padding: 0 8px;
    border: 1px solid #777;
    box-sizing: border-box;
    font-size: 13px;
}

.idInputRow button,
.useIdBtn {
    height: 34px;
    padding: 0 14px;
    margin-left: 8px;
    border: 1px solid #2763ba;
    background: #fff;
    color: #2763ba;
    font-size: 12px;
    font-weight: 600;
    cursor: pointer;
}

.idInputRow button:hover,
.useIdBtn:hover {
    background: #2763ba;
    color: #fff;
}

.resultBox {
    min-height: 54px;
    margin-top: 24px;
    color: #555;
    font-size: 15px;
    line-height: 1.6;
}

.resultBox strong {
    color: #222;
    font-size: 20px;
}

.resultBox .ok {
    color: #1769e0;
    font-weight: 700;
}

.resultBox .no {
    color: #e34d4d;
    font-weight: 700;
}

.buttonWrap {
    margin-top: 14px;
    display: flex;
    justify-content: center;
}

.buttonWrap .useIdBtn {
    min-width: 86px;
    height: 38px;
    font-size: 14px;
}
</style>

<script>
$(function() {
    $("#id").focus();

    $("#btnUseId").click(function() {
        checkId();
    });

    $("#id").keyup(function(evt) {
        if (evt.which == 13) {
            checkId();
        }
    });

    $(".useIdBtn").click(function() {
        sendId($(this).data("id"));
    });
});

function checkId() {
    var id = $("#id").val();
    var idRegex = /^(?:[가-힣]{3,}|(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{6,12})$/;

    if (id.replace(/\s/g, "") == "") {
        alert("아이디를 입력해주세요.");
        $("#id").val("").focus();
        return;
    }

    if (!idRegex.test(id.replace(/\s/g, ""))) {
        alert("아이디는 한글 3자 이상 또는 영문+숫자 혼용 6~12자로 입력해주세요.");
        $("#id").focus();
        return;
    }

    $("#dupFrm").submit();
}

function sendId(id) {
    if (!opener || opener.closed) {
        alert("회원가입 창을 찾을 수 없습니다.");
        return;
    }

    opener.document.getElementById("id").value = id;
    opener.document.getElementById("idChecked").value = "Y";
    self.close();
}
</script>
</head>

<body>
<div class="idDupWrap">
    <h2><strong>ID CHECK</strong> 아이디 중복확인</h2>

    <p class="idDesc">
        사용하고자 하는 아이디를 입력해주세요.<br>
        아이디 중복확인 후 사용 가능한 아이디로 선택하시면 됩니다.
    </p>

    <form name="dupFrm" id="dupFrm" action="idDup.jsp" method="get">
        <div class="idInputRow">
            <label for="id">아이디</label>
            <input type="text"
                   id="id"
                   name="id"
                   maxlength="12"
                   value="<c:out value='${loginId}'/>"
                   placeholder="아이디를 입력해주세요">
            <button type="button" id="btnUseId">중복확인</button>
        </div>
    </form>

    <div class="resultBox">
        <c:choose>
            <c:when test="${checked}">
                <strong><c:out value="${loginId}"/></strong> 는
                <c:choose>
                    <c:when test="${not validPattern}">
                        <span class="no">사용할 수 없는 아이디 형식</span>입니다.<br>
                        한글 3자 이상 또는 영문+숫자 혼용 6~12자로 입력해주세요.
                    </c:when>
                    <c:when test="${idAvailable}">
                        <span class="ok">사용 가능한 아이디</span>입니다.
                    </c:when>
                    <c:otherwise>
                        <span class="no">이미 사용 중인 아이디</span>입니다.
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                사용할 아이디를 입력한 뒤 중복확인을 눌러주세요.
            </c:otherwise>
        </c:choose>
    </div>

    <div class="buttonWrap">
        <c:if test="${checked and idAvailable}">
            <button type="button" class="useIdBtn" data-id="<c:out value='${loginId}'/>">
                사용하기
            </button>
        </c:if>
    </div>
</div>
</body>
</html>
