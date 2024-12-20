package com.horrorcoresoftware.inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple memory-based message broker to send messages to clients
        // The /topic prefix will be used for sending messages
        config.enableSimpleBroker("/topic");

        // Messages with /app prefix will be routed to message-handling methods
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register /inventory-ws endpoint where clients will connect
        // Allow CORS for development
        registry.addEndpoint("/inventory-ws")
                .setAllowedOrigins("*")
                .withSockJS(); // Fallback options for browsers that don't support WebSocket
    }
}