$(function(){
    //주소
    $(document).on("click", "#dimmed", function(){
        if(addBlooean){
            $("#addressLayerPopup").hide();
            $("#dimmed").hide();
        }
    });

    //비밀번호
    $('#password').blur(function(){
        const infoTxt = $(this).parent("p").next(".infoTxt");
        passwordChk($(this).val(),infoTxt);
        passwordEqualChk();
    });

    //비밀번호 확인
    $("#password_chk").keyup(function(){
        passwordEqualChk();
    });
});

//비밀번호 확인 동일 체크
function passwordEqualChk(){
    if($("#password").val() == $("#password_chk").val()) {
        $("#password_chk").parent("p").next(".infoTxt").removeClass("hide red").addClass("green").html("비밀번호가 일치합니다.");
    }else{
        if(!$("#password_chk").parent("p").next(".infoTxt").hasClass("red")){
            $("#password_chk").parent("p").next(".infoTxt").removeClass("hide green").addClass("red").html("비밀번호가 일치하지 않습니다.");
        }
    }
}

//회원가입 form
function submitCheck(){
    var submit = false;
    $(".infoTxt").each(function(index){
        if(!$(".infoTxt").eq(index).hasClass("green")){
            $(".infoTxt").eq(index).prev("p").find("input").focus();
            if($(".infoTxt").eq(index).parent().hasClass("emailD")){
                $(".infoTxt").eq(index).removeClass("hide").addClass("red").html("이메일 인증을 진행해주세요.");
            }
            submit = false;
            return false;
        }else{
            submit = true;
        }
    });

    return submit;
}

//주소검색 카카오 API
function addressApi(e){
    const addpop = document.getElementById("addressLayerPopup");
    const dim = document.getElementById("dimmed");
    let addBlooean = false;
    var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
    new daum.Postcode({
        oncomplete: function(data) { //선택시 입력값 세팅
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById('city').value = extraAddr;
            } else {
                document.getElementById('city').value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById('city').value = addr;
            document.getElementById('street').focus();

            // iframe을 넣은 element를 안보이게 한다.
            // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
            addpop.style.display = 'none';
            dim.style.display = 'none';
            addBlooean = false;

            // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
            document.body.scrollTop = currentScroll;
        },
        onresize : function(size) {
            addpop.style.height = size.height+'px';
        },
        width : '100%',
        height : '100%'
    }).embed(addpop);
    addBlooean = true;
    addpop.style.display = 'block';
    dim.style.display = 'block';

}

//비밀번호 유효성 체크
function passwordChk(pw,infoTxt){
    pw = pw.trim();
    const num = pw.search(/[0-9]/g);
    const eng = pw.search(/[a-z]/ig);
    const spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

    if (pw.length < 8 || pw.length > 15) {
        infoTxt.html("문자,숫자,특수기호를 조합하여 8~15자리로 입력해주세요.");
        if(!infoTxt.hasClass("red")) infoTxt.removeClass("hide green").addClass("red");
    } else if (pw.search(/\s/) != -1) {
        infoTxt.html("비밀번호는 공백 없이 입력해주세요.");
        if(!infoTxt.hasClass("red")) infoTxt.removeClass("hide green").addClass("red");
    } else if (num < 0 || eng < 0 || spe < 0) {
        infoTxt.html("문자,숫자,특수기호를 조합하여 8~15자리로 입력해주세요.");
        if(!infoTxt.hasClass("red")) infoTxt.removeClass("hide green").addClass("red");
    } else {
        infoTxt.html("사용가능한 비밀번호입니다.");
        if(!infoTxt.hasClass("green")) infoTxt.removeClass("hide red").addClass("green");
    }
}

//이메일 인증
function emailAuthentication(e){
    const email = $("#email").val();
    const infoTxt = $(e).parent("p").next(".infoTxt");
    $("#email").attr("data-email",'');
    $("#emailChkNum").val('');
    if(!emailValChk(email)){
        return false;
    }
    emailChk(email,infoTxt);
}

//이메일 중복체크
function emailChk(email,infoTxt){
    $.ajax({
        type:"GET",
        url:"/mail_chk?email="+email,
        success:function(data){
            if(data == 1) {
                infoTxt.removeClass("hide").addClass("red").html("이미 가입된 이메일입니다.");
            }else{
                infoTxt.removeClass("hide red").addClass("green").html("해당 이메일로 인증번호를 발송했습니다.");
                $("#email").attr("data-email",email);
                $.ajax({
                    type:"GET",
                    url:"/mail_verification_code?email="+email,
                    success:function(data){
                        $("#emailChkNum").keyup(function(){
                            if($(this).val() == data){
                                $("#emailChkNum").parent("p").next(".infoTxt").removeClass("hide red").addClass("green")
                                    .html("인증번호 일치");
                            }else{
                                if(!$("#emailChkNum").parent("p").next(".infoTxt").hasClass("red")){
                                    $("#emailChkNum").parent("p").next(".infoTxt").removeClass("hide green").addClass("red")
                                        .html("인증번호가 일치하지 않습니다. 인증번호를 다시 확인해주세요.");
                                }
                            }
                        });
                    }
                });
            }
        }
    });
}

//이메일 유효성 체크
function emailValChk(email){
    const emailPattern= /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const target = $("#email");

    if(!check(emailPattern, target, email, "유효하지 않은 이메일 주소입니다.")) {
        return false;
    }
    return true;
}

function check(pattern, taget, email, message) {
    if(pattern.test(email)) {
        return true;
    }
    taget.siblings(".infoTxt").removeClass("hide").addClass("red").html(message);
    taget.focus();
    return false;
}
