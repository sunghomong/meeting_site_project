'use strict';

// HTML 요소 가져오기
var usernamePage = document.querySelector('#username-page'); // 사용자 입력란 페이지
var chatPage = document.querySelector('#chat-page'); // 채팅 페이지
var usernameForm = document.querySelector('#usernameForm'); // uesrId (input=text)
var messageForm = document.querySelector('#messageForm'); // message폼 (form)
var messageInput = document.querySelector('#message'); // messageContent (input=text)
var messageArea = document.querySelector('#messageArea'); // message 위치
var connectingElement = document.querySelector('.connecting'); // message...

// WebSocket 클라이언트와 사용자 이름 관련 변수
var stompClient = null;
var username = null;

// 사용자 이름 색상 배열
var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// WebSocket 서버에 연결하는 함수
function connect(event) {
    username = document.querySelector('#name').value.trim(); // userName 값 저장 --> userId 값 저장

    if(username) {
        // 사용자 이름 입력 페이지 숨기고 채팅 페이지 표시
        usernamePage.classList.add('hidden'); // 사용자 입력란 숨김
        chatPage.classList.remove('hidden'); // 채팅 페이지 hidden 삭제
        // --> 채팅방 url 로 이동?? (chatId값 가지고)

        // SockJS를 통해 WebSocket 연결 설정
        // WebSocket 서버에 연결을 시도
        var socket = new SockJS('/websocket');
        stompClient = Stomp.over(socket);

        // 연결에 성공하면 onConnected 함수를 호출하고, 오류 발생 시 onError 함수를 호출
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

// WebSocket에 연결한 후 호출되는 함수
function onConnected() { // 연결 성공 후
    // Public Topic ("/topic/public")에 구독
    stompClient.subscribe('/topic/public', onMessageReceived);

    // 서버에 사용자 이름 전송 (JOIN 타입으로)
    stompClient.send("/app/chat.register",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}

// WebSocket 연결 오류 발생 시 호출되는 함수
function onError(error) {
    connectingElement.textContent = 'WebSocket에 연결할 수 없습니다. 페이지를 새로 고치거나 관리자에게 문의하십시오.';
    connectingElement.style.color = 'red';
}

// 채팅 메시지를 서버로 전송하는 함수
function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

// 수신한 채팅 메시지를 처리하는 함수
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        // 사용자가 입장한 메시지
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        // 사용자가 퇴장한 메시지
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        // 일반 채팅 메시지
        messageElement.classList.add('chat-message');

        // 메시지 발신자의 아바타와 사용자 이름
        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    // 메시지 내용
    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    // 메시지를 화면에 추가하고 스크롤을 아래로 이동
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

// 사용자 이름에 기반한 아바타 색상을 반환하는 함수
function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

// 사용자 이름 입력 폼과 메시지 입력 폼에 이벤트 리스너 추가
usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', send, true)