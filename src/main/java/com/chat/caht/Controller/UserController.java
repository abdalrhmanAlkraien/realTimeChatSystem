package com.chat.caht.Controller;

import com.chat.caht.Registry.UserRegistry;
import com.chat.caht.Registry.UserSession;
import com.chat.caht.handling.HandlingMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {
//    private final UserRegistry userRegistry ;
    private  final HandlingMessage handlingMessage ;

    public UserController( HandlingMessage handlingMessage) {
        this.handlingMessage = handlingMessage;
    }


    @GetMapping("getAllUser")
    public ResponseEntity<List<String>> getUsers(){
       List<String> users= handlingMessage.getAllUser();
       return ResponseEntity.ok().body(users);
    }
}
