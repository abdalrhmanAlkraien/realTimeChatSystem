package com.chat.caht.Registry;

import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public Boolean isUserSessionExist(String name) {

        if (mapWebSocketUserName.containsKey(name))
            return true ;
        else
            return false ;

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


    public List<String> getAllUserSessions(){
      List<String >users=  mapWebSocketSession.values().stream().map(value->value.getUserName()).collect(Collectors.toList());
       return users;
    }

}
