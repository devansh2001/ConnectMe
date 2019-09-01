package controller;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Account;
import model.MessageToServer;
import model.RefreshRequestToServer;
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
@RequestMapping("/connect-me")
public class Manager {

    @PutMapping("/send-message")
    public ResponseEntity sendMessage(@RequestBody MessageToServer messageToServer) {
        ServerService serverService = new ServerService();
        serverService.processRequest(messageToServer);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/create-account")
    public ResponseEntity createAccount(@RequestBody Account account) {
        ServerService serverService = new ServerService();
        return serverService.newUser(account);

    }

    @GetMapping("/refresh")
    public ResponseEntity refresh(@RequestBody RefreshRequestToServer refreshRequestToServer) {
        ServerService serverService = new ServerService();
        return serverService.refresh(refreshRequestToServer.getUsername());
    }

}
