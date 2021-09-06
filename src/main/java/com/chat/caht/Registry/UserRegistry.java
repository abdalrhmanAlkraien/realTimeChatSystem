package com.chat.caht.Registry;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRegistry {
    private Map<String,UserSession> mapWebSocketUserName=new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, UserSession> mapWebSocketSession=new ConcurrentHashMap<>();

    public UserRegistry(){

    }

    public UserRegistry(UserSession userSession)
    {
        mapWebSocketUserName.put(userSession.getUserName(),userSession);
        mapWebSocketSession.put(userSession.getSession().getId(),userSession);
    }


    public void destroyRegistry(UserSession userSession){
        mapWebSocketSession.remove(userSession.getSession().getId());
        mapWebSocketUserName.remove(userSession.getUserName());
    }

    public UserSession findUserBySessionId(WebSocketSession session){
        return mapWebSocketSession.get(session.getId());
    }

    public UserSession findUserByUserName(String name)
    {
        return mapWebSocketUserName.get(name);
    }

    public void addUserRegister(UserSession userSession){
        mapWebSocketSession.put(userSession.getSession().getId(),userSession);
        mapWebSocketUserName.put(userSession.getUserName(),userSession);
    }

    public void removeUserRegister(UserSession userSession)
    {
        mapWebSocketUserName.remove(userSession.getUserName());
        mapWebSocketSession.remove(userSession.getSession().getId());

    }


}
