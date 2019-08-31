package config;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import service.ServerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log
@Slf4j
@Configuration
public class Config {
    public Config() {
        System.out.println("Check");
    }
    @Bean
    public ServerService server() {
        return new ServerService();
    }
}
