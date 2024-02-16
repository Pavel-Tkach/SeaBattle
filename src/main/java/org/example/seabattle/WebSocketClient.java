package org.example.seabattle;

import javax.websocket.*;
import java.io.IOException;

@ClientEndpoint
public class WebSocketClient {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("WebSocket connection established");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("WebSocket connection closed: " + closeReason.getReasonPhrase());
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
}
