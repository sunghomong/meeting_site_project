package com.meeting_site_project.YM.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
//
//@Configuration:
//
//이 어노테이션은 이 클래스가 Spring 설정 파일임을 나타냅니다. Spring IoC 컨테이너에 의해 관리되는 빈(bean)을 설정하는 데 사용됩니다.
//@EnableWebSocketMessageBroker:
//
// 이 어노테이션은 WebSocket 메시지 브로커를 활성화하도록 Spring에 지시합니다. WebSocket을 사용하여 실시간 메시지 통신을 구현할 수 있게 해줍니다.
// registerStompEndpoints(StompEndpointRegistry registry) 메서드:
//
// 이 메서드는 WebSocket 엔드포인트를 등록하는 데 사용됩니다.
// registry.addEndpoint("/websocket").withSockJS();는 "/websocket" 엔드포인트를 등록하고 SockJS를 사용하여 WebSocket 연결을 지원합니다. 이렇게 함으로써 다양한 브라우저와 플랫폼에서 WebSocket을 사용할 수 있도록 합니다.
// configureMessageBroker(MessageBrokerRegistry registry) 메서드:
//
// 이 메서드는 메시지 브로커(Message Broker)를 설정하는 데 사용됩니다.
// registry.enableSimpleBroker("/topic");는 "/topic" 프리픽스로 시작하는 주제(destination)를 구독한 클라이언트에게 메시지를 브로드캐스트하는 간단한 메시지 브로커를 활성화합니다.
// registry.setApplicationDestinationPrefixes("/app");은 클라이언트에서 서버로 메시지를 보낼 때 사용할 목적지(destination) 프리픽스를 설정합니다. 클라이언트는 "/app" 프리픽스를 사용하여 서버로 메시지를 전송할 수 있습니다.