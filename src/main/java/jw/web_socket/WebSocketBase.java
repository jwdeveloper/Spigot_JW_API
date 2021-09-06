package jw.web_socket;

import jw.InitializerAPI;
import jw.dependency_injection.InjectionManager;
import org.bukkit.Bukkit;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

public class WebSocketBase extends WebSocketServer {
    public HashMap<Integer, WebSocketPacket> webSocketEvents = new HashMap<>();

    public WebSocketBase(int port) {
        super(new InetSocketAddress(port));

        List<WebSocketPacket> events = InjectionManager.getObjectByType(WebSocketPacket.class);
        for (int i = 0; i < events.size(); i++) {
            webSocketEvents.put(events.get(i).getPacketId(), events.get(i));
        }
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        InitializerAPI.infoLog(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        InitializerAPI.infoLog(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " leave the room!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {

    }

    @Override
    public void onMessage(WebSocket webSocket, ByteBuffer buffer)
    {
        int id = buffer.getInt(0);
        if (!webSocketEvents.containsKey(id)) {
            return;
        }
        WebSocketPacket webSocketEvent = webSocketEvents.get(id);
        if (!webSocketEvent.resolvePacket(buffer)) {
            return;
        }
        webSocketEvent.onPacketTriggered(webSocket);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        InitializerAPI.errorLog(webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + "Error " + e.getMessage());
    }

    @Override
    public void onStart() {
        InitializerAPI.successLog("Socket Start " + this.getAddress());
        setConnectionLostTimeout(100);
    }
}