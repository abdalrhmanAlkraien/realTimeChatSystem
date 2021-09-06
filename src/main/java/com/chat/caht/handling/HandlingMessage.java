package com.chat.caht.handling;

import com.chat.caht.Registry.RoomSession;
import com.chat.caht.Registry.UserRegistry;
import com.chat.caht.Registry.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class HandlingMessage extends TextWebSocketHandler {
    private static final Gson gson = new GsonBuilder().create();
    private List<WebSocketSession> webSocketSessions=new ArrayList<>();
    private static final String REGISTER="register";
    private static final String LEAVE="leave";
    private static final String MESSAGE="message";
    private static final String OFFER="offer";
    private static final String ANSWER="answer";
    List<Long> randomRoomNumber =new ArrayList<>();



    @Autowired
    UserRegistry userRegistry;

    @Autowired
    RoomSession roomSession;


    //Todo we dont need this now
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonObject json=gson.fromJson(message.getPayload(),JsonObject.class);

        UserSession userSession=userRegistry.findUserBySessionId(session);
        if(userSession!=null)
        {
            System.out.println("the user is already exist "
                    .concat(userSession.getUserName())
                    .concat(userSession.getSession().getId()));
        }

        switch (json.get("type").getAsString())
        {
            case REGISTER:
                System.out.println("register new Session to System: ".concat(session.getId()));
                registerNewUser(session,json);
                
                break;
            case OFFER:
                System.out.println("create new Offer");
                UserSession caller=userRegistry.findUserByUserName(json.get("from").getAsString());
                if(caller==null)
                {
                    cannotFindUserSession();
                }
                UserSession target=userRegistry.findUserByUserName(json.get("target").getAsString());
                if(target==null)
                {
                    cannotFindUserSession();
                }
                List<UserSession> userSessions=new ArrayList<>();
                userSessions.add(caller);
                userSessions.add(target);
                Long roomId=Long.parseLong(json.get("room").getAsString());
                RoomSession room=createRoom(caller,json,roomId,userSessions);
                room.getAllUserSession().stream().forEach(x->{

                    try {
                        x.getSession().sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });


            case ANSWER:
                System.out.println("create new Offer");
                UserSession callerAnswer=userRegistry.findUserByUserName(json.get("from").getAsString());
                if(callerAnswer==null)
                {
                    cannotFindUserSession();
                }
                UserSession targetAnswer=userRegistry.findUserByUserName(json.get("target").getAsString());
                if(targetAnswer==null)
                {
                    cannotFindUserSession();
                }
                List<UserSession> userSessionsAnswer=new ArrayList<>();
                userSessionsAnswer.add(callerAnswer);
                userSessionsAnswer.add(targetAnswer);
                RoomSession roomSession=callerAnswer.getRoomById(json.get("roomId").getAsLong());

                roomSession.getAllUserSession().stream().forEach(x->{

                    try {
                        x.getSession().sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                break;

            case MESSAGE:
                /*System.out.println("Message From User: ".concat(json.get("from")
                        .getAsString()).concat("To User: ").concat(json.get("target").getAsString())
                        .concat("Message Conant is: ").concat(json.get("text").getAsString()));
                UserSession caller=userRegistry.findUserByUserName(json.get("from").getAsString());
                if(caller==null)
                {
                    cannotFindUserSession();
                }
                UserSession target=userRegistry.findUserByUserName(json.get("target").getAsString());
                if(target==null)
                {
                cannotFindUserSession();
                }
                List<UserSession> userSessions=new ArrayList<>();
                userSessions.add(caller);
                userSessions.add(target);

                //Todo we must set this data from dataBase after this time
                // We must create check here if this user has room or has not
                Long roomId=Long.parseLong(json.get("room").getAsString());
                createRoom(caller,json,roomId,userSessions);*/






                break;
            case LEAVE:
                System.out.println("the session leave from system: ".concat(session.getId()));
                break;
        }


    }

    private void cannotFindUserSession() throws Exception {
        throw new Exception("cannot find User session");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //Todo remove from Registry
        webSocketSessions.remove(session);
    }

    private void registerNewUser(WebSocketSession session,JsonObject object){
        UserSession user=new UserSession(object.get("id").getAsString(),object.get("userName").getAsString(),session);
        userRegistry.addUserRegister(user);
    }

    private void sendMessage(){

    }

    //delete this when add database to this application
    private Long randomRoomId(){
        Random r=new Random();
        Long roomId =r.nextLong();
        randomRoomNumber.add(roomId);
        return roomId;
    }



    private RoomSession createRoom(UserSession caller,JsonObject json, Long roomId,List<UserSession> userSessions){
        if(roomId!=null) {
            if(!caller.getRoomIfExist(roomId))
            {
                RoomSession room=new RoomSession(randomRoomId()
                        ,json.get("from").getAsString().concat(json.get("target").getAsString()),
                        userSessions);
            }
        }
        else
        {
            RoomSession room=new RoomSession(randomRoomId()
                    ,json.get("from").getAsString().concat(json.get("target").getAsString()),
                    userSessions);
        }
        return roomSession;
    }

    public void createOffer(){

    }
}
