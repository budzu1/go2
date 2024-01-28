package com.TP;

import java.net.URI;
import java.util.ArrayList;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class SimpleWebSocketClient extends WebSocketClient {

    private Gson gson = new Gson();
    Goban goban;

    public SimpleWebSocketClient(URI serverUri, Goban goban) {
        super(serverUri);
        this.goban = goban;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Opened connection");

        send("CONNECT\naccept-version:1.1,1.0\n\n\u0000");
        // Send SUBSCRIBE Frame
        send("SUBSCRIBE\nid:sub-0\ndestination:/topic/123\n\n\u0000");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);

        // Deserialize the JSON string into an ArrayList<ArrayList<Integer>>
        Type listType = new TypeToken<ArrayList<ArrayList<Integer>>>() {
        }.getType();
        ArrayList<ArrayList<Integer>> receivedList = gson.fromJson(message, listType);

        goban.setBoard(receivedList);
        goban.updateGoban();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed connection");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}