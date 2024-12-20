package com.horrorcoresoftware.inventory.controller;

import com.horrorcoresoftware.inventory.dto.InventoryUpdateDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class InventoryUpdateWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public InventoryUpdateWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/inventory/update")
    public void handleInventoryUpdate(InventoryUpdateDTO update) {
        // Process the incoming message
        // ...

        // Send a response to clients subscribed to /topic/inventory
        messagingTemplate.convertAndSend("/topic/inventory", update);
    }
}
