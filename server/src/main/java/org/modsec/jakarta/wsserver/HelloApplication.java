package org.modsec.jakarta.wsserver;

import jakarta.websocket.server.ServerEndpoint;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;
import jakarta.websocket.*;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.websocket.CloseReason;

import java.util.Set;
import java.util.HashSet;

import java.util.logging.Logger;

import java.io.IOException;


@ServerEndpoint(
        value= "/echo",
decoders = {DeviceReportDecoder.class})
public class HelloApplication {

    private static Logger logger = Logger.getLogger(HelloApplication.class.getName());

    // Might need to change to something async
    private static Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        // Should add some sort of authentication if we even allow any device to connect
        // Specifically, each hardware device should generate a unique hash for itself
        logger.info("Server connected to session " + session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, JsonObject msg) {
        try {
            // Use msg.getInt to grab the date and time fields
            // and msg.getString to grab other data from the JSON sent by the device
            session.getBasicRemote().sendText(String.valueOf(msg.getString("name")));

        } catch (IOException e) {

        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {

        try {
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()) {
                    ModSecServer ser = new ModSecServer();
                    s.getBasicRemote().sendText("USER DOWN");
                }
            }
        } catch (IOException e){}

        sessions.remove(session);


    }



}
