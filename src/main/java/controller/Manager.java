package controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.MessageToServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ServerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
@ComponentScan
@RestController
@EnableAutoConfiguration
@AllArgsConstructor
@RequestMapping("connect-me")
public class Manager {
//    ServerService serverService;
    @GetMapping("/")
    public String mytest() {
        return "I hope this works...";
    }

    @PutMapping("/send-message")
    public ResponseEntity sendMessage(@RequestBody MessageToServer messageToServer) {
        messageToServer.getFrom();
        ServerService serverService = new ServerService();
        System.out.println(serverService);
//        System.out.println(serverService.getUrl());
//        System.out.println(serverService.getConnection());
//        System.out.println();
        serverService.processRequest(messageToServer);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
