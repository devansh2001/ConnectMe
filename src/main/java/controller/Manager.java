package controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.MessageToServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ServerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
@ComponentScan
@RestController
@EnableAutoConfiguration
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    @Autowired
    ServerService serverService;
    @GetMapping("/")
    public String mytest() {
        return "I hope this works...";
    }

    @PutMapping("/send-message")
    public ResponseEntity sendMessage(MessageToServer messageToServer) {
        System.out.println(serverService);
        System.out.println(serverService.getUrl());
        serverService.processRequest(messageToServer);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
