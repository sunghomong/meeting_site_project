<script>

    var isIdChecked = false;
    var isNickNameChecked = false;
    var isEmailChecked = false;
   // 폼을 유효성 검사하는 함수
function validateForm() {
    // 이메일 아이디와 도메인을 가져옵니다.
    var emailId = document.getElementById("emailId").value; // 이메일 아이디 입력란
    var emailDomain = document.getElementById("emailDomain").value; // 이메일 도메인 입력란
    var mail = emailId + "@" + emailDomain; // 조합된 이메일 주소

    // 이메일 형식을 검사하는 정규식입니다.
    var email_rule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

    // 이메일 필드가 비어있거나 도메인이 비어있다면 알림을 띄우고 false를 반환합니다.
    if (!emailId || !emailDomain) {
        alert("이메일을 입력해주세요");
        return false;
    }

    // 정규식 패턴에 맞지 않으면 알림을 띄우고 false를 반환합니다.
    if (!email_rule.test(mail)) {
        alert("이메일을 형식에 맞게 입력해주세요.");
        return false;
    }

    // 비밀번호와 비밀번호 확인 필드를 가져옵니다.
    var userPassword = document.getElementById("userPassword").value; // 비밀번호 입력란
    var confirmUserPassword = document.getElementById("confirmUserPassword").value; // 비밀번호 확인 입력란

    // 비밀번호가 일치하지 않으면 알림을 띄우고 false를 반환합니다.
    if (userPassword !== confirmUserPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    // MBTI를 대문자로 변환합니다.
    var inputElement = document.getElementById("userMbti"); // MBTI 입력란
    var inputValue = inputElement.value.toUpperCase(); // 입력된 MBTI 값을 대문자로 변환

    // MBTI의 유효성을 검사하는 정규식입니다.
    var validPattern = /^[EI][NS][FT][PJ]$/;

    // MBTI가 올바르지 않으면 알림을 띄우고 false를 반환합니다.
    if (inputValue.length !== 4 || !validPattern.test(inputValue)) {
        alert("올바른 MBTI를 입력해주세요.");
        return false;
    }
    // 모든 중복확인이 완료되어야만 회원가입 버튼이 활성화됨
    if (isIdChecked && isNickNameChecked && isEmailChecked) {
            return true;
        } else if(!isIdChecked) {
            alert("아이디 중복확인을 해주세요.");
            document.getElementById("userId").focus();
            return false;
        } else if(!isNickNameChecked) {
            alert("닉네임 중복확인을 해주세요.");
            document.getElementById("nickName").focus();
            return false;
        } else if(!isEmailChecked) {
            alert("이메일 중복확인을 해주세요.");
            document.getElementById("emailId").focus();
            return false;
        }

    // 모든 검사를 통과하면 true를 반환합니다.
    return true;
}

// 이메일 도메인 선택 시 호출되는 함수
function setEmailDomain(domain) {
    document.getElementById("emailDomain").value = domain; // 이메일 도메인 입력란에 선택된 도메인 값을 설정합니다.
}




// 아이디 중복 체크 함수
function checkId() {

    // 입력된 아이디를 가져옵니다.
    var userId = document.getElementById("userId").value;

    // 만약 아이디가 비어있다면 알림을 띄우고 함수를 종료합니다.
    if (!userId) {
        var checkIdMessage = document.getElementById('checkIdMessage'); // 아이디 중복 메시지를 표시하는 엘리먼트
        checkIdMessage.innerText = '아이디를 입력해주세요';

        // 아이디 입력란에 포커스를 줍니다.
        document.getElementById("userId").focus();
        return;
    }

    // 서버로 POST 요청을 보냅니다.
    fetch('/idCheck', {
        method: 'POST', // 요청 방식은 POST입니다.
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // 요청 헤더의 Content-Type을 설정합니다.
        },
        body: 'userId=' + encodeURIComponent(userId), // 요청 본문에 아이디를 포함시켜 전송합니다.
    })
    .then(response => response.json()) // 서버로부터 받은 응답을 JSON 형식으로 변환합니다.
    .then(data => {
        // 중복 여부에 대한 처리
        var checkIdMessage = document.getElementById('checkIdMessage'); // 아이디 중복 메시지를 표시하는 엘리먼트
        if (checkIdMessage) {
            // 서버로부터 받은 데이터를 메시지로 표시합니다.
            if (data) {
                checkIdMessage.innerText = '사용 가능한 아이디입니다.';
                isIdChecked = true;
            } else {
                checkIdMessage.innerText = '이미 존재하는 아이디입니다.';
            }
        }
    })
    .catch((error) => {
        console.error('Error:', error); // 오류가 발생하면 콘솔에 오류 메시지를 출력합니다.
    });
}

// 닉네임 중복 체크 함수
function checkNickName() {
    // 입력된 닉네임을 가져옵니다.
    var nickName = document.getElementById("nickName").value;

    // 만약 닉네임이 비어있다면 알림을 띄우고 함수를 종료합니다.
    if (!nickName) {
        var checkNickNameMessage = document.getElementById('checkNickNameMessage'); // 아이디 중복 메시지를 표시하는 엘리먼트
        checkNickNameMessage.innerText = '닉네임을 입력해주세요';

        // 닉네임 입력란에 포커스를 줍니다.
        document.getElementById("nickName").focus();
        return;
    }

    // 서버로 POST 요청을 보냅니다.
    fetch('/nickNameCheck', {
        method: 'POST', // 요청 방식은 POST입니다.
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // 요청 헤더의 Content-Type을 설정합니다.
        },
        body: 'nickName=' + encodeURIComponent(nickName), // 요청 본문에 닉네임을 포함시켜 전송합니다.
    })
    .then(response => response.json()) // 서버로부터 받은 응답을 JSON 형식으로 변환합니다.
    .then(data => {
        // 중복 여부에 대한 처리
        var checkNickNameMessage = document.getElementById('checkNickNameMessage'); // 닉네임 중복 메시지를 표시하는 엘리먼트
        if (checkNickNameMessage) {
            // 서버로부터 받은 데이터를 메시지로 표시합니다.
            if (data) {
                checkNickNameMessage.innerText = '사용 가능한 닉네임입니다.';
                isNickNameChecked = true;
            } else {
                checkNickNameMessage.innerText = '이미 존재하는 닉네임입니다.';
            }
        }
    })
    .catch((error) => {
        console.error('Error:', error); // 오류가 발생하면 콘솔에 오류 메시지를 출력합니다.
    });
}

// 이메일 중복 체크 함수
function checkEmail() {
    // 입력된 이메일 아이디와 도메인을 가져옵니다.
    var emailId = document.getElementById("emailId").value; // 이메일 아이디 입력란
    var emailDomain = document.getElementById("emailDomain").value; // 이메일 도메인 입력란

    // 만약 닉네임이 비어있다면 알림을 띄우고 함수를 종료합니다.
    if (!emailId || !emailDomain) {
        var checkEmailMessage = document.getElementById('checkEmailMessage'); // 아이디 중복 메시지를 표시하는 엘리먼트
        checkEmailMessage.innerText = '이메일을 입력해주세요';

        // 이메일 입력란에 포커스를 줍니다.
        document.getElementById("emailId").focus();
        return;
    }

    // 서버로 POST 요청을 보냅니다.
    fetch('/emailCheck', {
        method: 'POST', // 요청 방식은 POST입니다.
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded', // 요청 헤더의 Content-Type을 설정합니다.
        },
        body: 'emailId=' + encodeURIComponent(emailId) + '&emailDomain=' + encodeURIComponent(emailDomain), // 이메일 아이디와 도메인을 포함시켜 전송합니다.
    })
    .then(response => response.json()) // 서버로부터 받은 응답을 JSON 형식으로 변환합니다.
    .then(data => {
        // 중복 여부에 대한 처리
        var checkEmailMessage = document.getElementById('checkEmailMessage'); // 이메일 중복 메시지를 표시하는 엘리먼트
        if (checkEmailMessage) {
            // 서버로부터 받은 데이터를 메시지로 표시합니다.
            if (data) {
                checkEmailMessage.innerText = '사용 가능한 이메일입니다.';
                isEmailChecked = true;
            } else {
                checkEmailMessage.innerText = '이미 존재하는 이메일입니다.';
            }
        }
    })
    .catch((error) => {
        console.error('Error:', error); // 오류가 발생하면 콘솔에 오류 메시지를 출력합니다.
    });
}



    $('#userPicture').change(function(){
setImageFromFile(this, '#preview');
});

function setImageFromFile(input, expression) {
    if (input.files && input.files[0])
    {
        var reader = new FileReader();

            reader.onload = function (e) {
                $(expression).attr('src', e.target.result);
           }
           reader.readAsDataURL(input.files[0]);
     }
}
</script>
