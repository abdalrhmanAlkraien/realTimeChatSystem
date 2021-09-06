package com.chat.caht.Config;

import com.chat.caht.Registry.RoomSession;
import com.chat.caht.Registry.UserRegistry;
import com.chat.caht.handling.HandlingMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocket implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handling(),"socket").setAllowedOrigins("*");
    }
    @Bean
    public HandlingMessage handling(){
        return new HandlingMessage();
    }

    @Bean
    public UserRegistry registry(){
        return new UserRegistry();
    }

    @Bean
    public RoomSession roomRegistry(){
        return new RoomSession();
    }

    @Bean
    public ServletServerContainerFactoryBean factory(){
        ServletServerContainerFactoryBean containerFactoryBean=new ServletServerContainerFactoryBean();
        containerFactoryBean.setMaxTextMessageBufferSize(8912);
        containerFactoryBean.setMaxBinaryMessageBufferSize(8921);
        return containerFactoryBean;
    }
}
