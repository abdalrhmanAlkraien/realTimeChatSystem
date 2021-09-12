package com.chat.caht.Registry;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class UserSession {
    private String id;
    private String userName;
    private String sdpOffer;
    private final WebSocketSession session;
    private List<RoomSession> roomSessions;


    public UserSession(String id, String userName, WebSocketSession session) {
        this.session = session;
        this.id = id;
        this.userName = userName;
        this.roomSessions=new ArrayList<>();
        this.sdpOffer=null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void addRoomSession(RoomSession roomSession){
        roomSessions.add(roomSession);
    }

    public String getSdpOffer() {
        return sdpOffer;
    }

    public void setSdpOffer(String sdpOffer) {
        this.sdpOffer = sdpOffer;
    }

    public List<RoomSession> getRoomSessions() {
        return roomSessions;
    }

    public void setRoomSessions(List<RoomSession> roomSessions) {
        this.roomSessions = roomSessions;
    }

    public Boolean getRoomIfExist(Long roomId)
    {
        AtomicReference<Boolean> exist= new AtomicReference<>(false);
        if(roomSessions.size()<=0)
        {
            // this room is empty
        }
        else
        {
            // we want use search algorithm here
            roomSessions.stream().forEach(x-> {
                if (x.equals(roomId))
                {
                    exist.set(true);
                }

            });

        }
        return exist.get();
    }

    public RoomSession getRoomById(Long roomId) throws Exception {
        AtomicReference<RoomSession> roomSession=null;
        roomSessions.stream().forEach(x->{
            if (x.getRoomId()==roomId)
            {
                roomSession.set(x);
            }
        });
        if(roomSession.get().equals(null))
        {
            cannotFindRoom();
        }
        return roomSession.get();
    }

    public void cannotFindRoom() throws Exception {
        throw new Exception("the room is not register");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSession that = (UserSession) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, session);
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", session=" + session +
                '}';
    }
}
