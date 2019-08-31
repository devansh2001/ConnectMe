package service;

import lombok.extern.slf4j.Slf4j;
import model.MessageToServer;

import java.sql.*;


import lombok.extern.java.Log;

@Log
@Slf4j
public class ServerService {
    private String url;
    private String user;
    private String password;
    Connection connection;
    Statement statement;

    public ServerService() {
        System.out.println("Hello");
        this.url = "jdbc:mysql://localhost:3306/ConnectMe";
        this.user = "root";
        this.password = "adminadmin";
        try {
            this.connection = DriverManager.getConnection(this.url);
            this.statement = connection.createStatement();
        } catch (SQLException exception) {
            log.info("Connection error while connecting to " + this.url);
        }
    }

    private void processRequest(MessageToServer messageToServer) {
        String from = messageToServer.getFrom();
        String to = messageToServer.getTo();
        if (checkUserValidity(from) && checkUserValidity(to)) {
            log.info("Would have written");
        }
    }

    private boolean checkUserValidity(String username) {
        String query =
                "SELECT * FROM users_list WHERE USERNAME = '" + username + "'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            log.info("Queried to check validity of user: " + username);
        } catch (SQLException exception) {
            log.info("Error while querying users_list for " + username);
            return false;
        }
        return true;
    }
}
