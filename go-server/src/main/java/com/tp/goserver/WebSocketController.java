package com.tp.goserver;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class WebSocketController {

    @MessageMapping("/session")
    @SendTo("/topic/session")
    public String processSessionId(String message) {
        // Process session ID message
        return "Session ID: " + message;
    }

    @MessageMapping("/status")
    @SendTo("/topic/status")
    public String processStatus(String message) {
        // Process status message (logged, game created, joined game)
        return "Status: " + message;
    }

    @MessageMapping("/arraylist")
    @SendTo("/topic/arraylist")
    public ArrayList<ArrayList<Integer>> processArrayList(ArrayList<ArrayList<Integer>> list) {
        return list;
    }
}
