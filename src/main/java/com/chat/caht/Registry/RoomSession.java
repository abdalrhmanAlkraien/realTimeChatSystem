package com.chat.caht.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RoomSession {

    public Long roomId;
    public String roomName;
    private Map<String, UserSession> userRoomMap = new ConcurrentHashMap<>();

    public RoomSession(){}
    public RoomSession(Long roomId, String roomName, List<UserSession> userSession) {
        this.roomId = roomId;
        this.roomName = roomName;
        userSession.stream().forEach(x -> {
                    userRoomMap.put(x.getUserName(), x);
                }
        );
    }



    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Map<String, UserSession> getUserRoomMap() {
        return userRoomMap;
    }

    public void setUserRoomMap(Map<String, UserSession> userRoomMap) {
        this.userRoomMap = userRoomMap;
    }

    public void addUserToRoom(UserSession userSession)
    {
        userRoomMap.put(userSession.getUserName(),userSession);
    }

    public void removeUserFromRoom(UserSession userSession){
        userRoomMap.remove(userSession.getUserName());
    }

    public List<UserSession> getAllUserSession()
    {
        List<UserSession> userSessions=new ArrayList<>();
        userRoomMap.values().forEach(x->userSessions.add(x));
        return userSessions;

    }

    public RoomSession getRoomById(String roomId){
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomSession that = (RoomSession) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(roomName, that.roomName) &&
                Objects.equals(userRoomMap, that.userRoomMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, roomName, userRoomMap);
    }

    @Override
    public String toString() {
        return "RoomSession{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", userRoomMap=" + userRoomMap +
                '}';
    }
}
