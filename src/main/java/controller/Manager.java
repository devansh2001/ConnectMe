package controller;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@ComponentScan
@RestController
@EnableAutoConfiguration
public class Manager {
    @RequestMapping("/")
    public String mytest() {
        return "I hope this works...";
    }

}
