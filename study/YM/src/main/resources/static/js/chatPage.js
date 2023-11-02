'use strict';

$(document).ready(function(){

    // WebSocket 클라이언트와 사용자 이름 관련 변수
    var stompClient = null;
    var username = null;

    // 사용자 이름 색상 배열
    var colors = [
        '#2196F3', '#32c787', '#00BCD4', '#ff5652',
        '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
    ];

    // HTML 요소 가져오기
    var chatPage = document.querySelector('#chat-page'); // 채팅 페이지
    var messageForm = document.querySelector('#messageForm'); // message폼 (form)
    var messageInput = document.querySelector('#message'); // messageContent (input=text)
    var messageArea = document.querySelector('#messageArea'); // message 위치
    var connectingElement = document.querySelector('.connecting'); // message...


    if(username) {
        // SockJS를 통해 WebSocket 연결 설정
        // WebSocket 서 연결을 시도버에
        var socket = new SockJS('/websocket/' + chatRoomId);
         //1. SockJS를 내부에 들고있는 stomp를 내어줌
         stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
}
// WebSocket에 연결한 후 호출되는 함수
function onConnected() { // 연결 성공 후
    // Public Topic ("/topic/public")에 구독
    stompClient.subscribe('/topic/chatRoom/' + chatRoomId , onMessageReceived); // 보낸 사람 이름 가져오기 headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

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
            sender: username, // 보낸이
            content: messageInput.value, // text 창 내용들
            type: 'CHAT' // 타입 chat
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

// 수신한 채팅 메시지를 처리하는 함수
function onMessageReceived(payload) { // stompClient.subscribe('/topic/chatRoom/' + chatRoomId, onMessageReceived); payload 메시지를 받고 판단후 출력
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        // 사용자가 입장한 메시지
        messageElement.classList.add('event-message');
        message.content = message.sender + ' 님이 채팅방을 들어오셨습니다.';
    } else if (message.type === 'LEAVE') {
        // 사용자가 퇴장한 메시지
        messageElement.classList.add('event-message');
        message.content = message.sender + ' 님이 채팅방을 나가셨습니다.';
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
messageForm.addEventListener('submit', send, true)