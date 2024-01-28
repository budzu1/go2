package com.tp.goserver;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class WebSocketMess {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void sendMyObjectOverWebSocket(ArrayList<ArrayList<Integer>> myObject) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(myObject);

            // Replace "/topic/some-topic" with the desired WebSocket topic
            messagingTemplate.convertAndSend("/topic/some-topic", json);
        } catch (JsonProcessingException e) {
            // Handle serialization error
            e.printStackTrace();
        }
    }
}
